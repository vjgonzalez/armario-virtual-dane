package ar.uba.fi.armariovirtual.modelo.filtros;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.armariovirtual.modelo.Clasificable;
import ar.uba.fi.armariovirtual.modelo.DefinicionClasificacion;
import ar.uba.fi.utilidadesdane.persistencia.ObjetoPersistente;

public class FiltroClasificaciones {
    private List<ClasificacionFiltro> _clasificacionesFiltro;

    public FiltroClasificaciones()
    {
        this(ObjetoPersistente.listarTodos(DefinicionClasificacion.class));
    }

    public FiltroClasificaciones(List<DefinicionClasificacion> definicionClasificaciones)
    {
        _clasificacionesFiltro = new ArrayList<>();
        for(DefinicionClasificacion definicion : definicionClasificaciones) {
            _clasificacionesFiltro.add(new ClasificacionFiltro(definicion));
        }
    }

    public List<ClasificacionFiltro> obtenerClasificaciones(){
        return _clasificacionesFiltro;
    }

    public boolean estaActivo() {
        // Sólo si existe al menos una Clasificación con opciones elegidas
        for(ClasificacionFiltro clasificacionFiltro : obtenerClasificaciones())
        {
            List<OpcionFiltro> opcionesSeleccionadas = clasificacionFiltro.getOpcionesFiltroSeleccionadas();
            if(opcionesSeleccionadas.size() > 0)
            {
                return true;
            }
        }
        return false;
    }

    public List<Clasificable> obtenerResultadosFiltrados(Class c, String nombreTabla, String nombreClase) {

        List<Clasificable> res;

        if(estaActivo())
        {
            // Para las condiciones "IN" (DEFINICION y OPCION_CLASIFICACION)
            StringBuffer condicionIdDefinicionIn = new StringBuffer("(");
            StringBuffer condicionIdOpcionClasificacionIn = new StringBuffer("(");
            int numDefinicionesActivas = 0;
            for (int i = 0; i < _clasificacionesFiltro.size(); i++) {
                ClasificacionFiltro clasificacionFiltro = _clasificacionesFiltro.get(i);
                List<OpcionFiltro> opcionesSeleccionadas = clasificacionFiltro.getOpcionesFiltroSeleccionadas();

                if(opcionesSeleccionadas.size() > 0)
                {
                    numDefinicionesActivas++;
                    condicionIdDefinicionIn.append(clasificacionFiltro.getIdDefinicion().toString()).append(","); // Agrego "," siempre. Reemplazo la última por ")"

                    for(int j = 0; j < opcionesSeleccionadas.size(); j++)
                    {
                        OpcionFiltro opcionSeleccionada = opcionesSeleccionadas.get(j);
                        condicionIdOpcionClasificacionIn.append(opcionSeleccionada.getIdOpcion().toString()).append(",");
                    }
                }
            }
            // Reemplazar el último caracter por ")"
            condicionIdDefinicionIn.setLength(Math.max(condicionIdDefinicionIn.length() - 1, 0));
            condicionIdDefinicionIn.append(")");
            condicionIdOpcionClasificacionIn.setLength(Math.max(condicionIdOpcionClasificacionIn.length() - 1, 0));
            condicionIdOpcionClasificacionIn.append(")");

            StringBuilder sb = new StringBuilder();
            sb.append(
                    "SELECT " + nombreTabla + ".* " +
                            "FROM " + nombreTabla + ", PAR_OBJETO_PERSISTENTE as PAR_CLASIFICABLE_CLASIFICACION, CLASIFICACION, DEFINICION_CLASIFICACION, PAR_OBJETO_PERSISTENTE as PAR_CLASIFICACION_OPCION, OPCION_CLASIFICACION " +
                            "WHERE " +
                            "PAR_CLASIFICABLE_CLASIFICACION.OBJETO1 = " + nombreTabla + ".ID " +
                            "AND PAR_CLASIFICABLE_CLASIFICACION.OBJETO1_NOMBRE_TIPO = \"" + nombreClase + "\" " +
                            "AND PAR_CLASIFICABLE_CLASIFICACION.OBJETO2 = CLASIFICACION.ID " +
                            "AND PAR_CLASIFICABLE_CLASIFICACION.OBJETO2_NOMBRE_TIPO = \"ar.uba.fi.armariovirtual.modelo.Clasificacion\" " +
                            "AND DEFINICION_CLASIFICACION.ID = CLASIFICACION.DEFINICION_CLASIFICACION " +
                            "AND DEFINICION_CLASIFICACION.ID IN " + condicionIdDefinicionIn.toString() + " " +
                            "AND PAR_CLASIFICACION_OPCION.OBJETO1 = CLASIFICACION.ID " +
                            "AND PAR_CLASIFICACION_OPCION.OBJETO1_NOMBRE_TIPO = \"ar.uba.fi.armariovirtual.modelo.Clasificacion\" " +
                            "AND PAR_CLASIFICACION_OPCION.OBJETO2 = OPCION_CLASIFICACION.ID " +
                            "AND PAR_CLASIFICACION_OPCION.OBJETO2_NOMBRE_TIPO = \"ar.uba.fi.armariovirtual.modelo.OpcionClasificacion\" " +
                            "AND OPCION_CLASIFICACION.ID IN " + condicionIdOpcionClasificacionIn.toString() + " " +
                            "GROUP BY " + nombreTabla + ".ID " +
                            "HAVING COUNT(DISTINCT DEFINICION_CLASIFICACION) = " + String.valueOf(numDefinicionesActivas) + " " +
                            "ORDER BY " + nombreTabla + ".favorito DESC");

            String text = sb.toString();

            res = ObjetoPersistente.findWithQuery(c, text);
        }
        else
        {
            res = ObjetoPersistente.listarTodos(c);
        }

        return res;
    }
}
