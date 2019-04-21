package ar.uba.fi.armariovirtual.modelo.filtros;

import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.armariovirtual.modelo.DefinicionClasificacion;
import ar.uba.fi.armariovirtual.modelo.OpcionClasificacion;

public class ClasificacionFiltro {
    private Long _idDefinicion;
    private List<OpcionFiltro> _opcionesFiltro;
    private String _nombre;

    public ClasificacionFiltro(DefinicionClasificacion definicionClasificacion)
    {
        this._idDefinicion = definicionClasificacion.getId();
        this._nombre = definicionClasificacion.getNombreAMostrar();
        _opcionesFiltro = new ArrayList<>();
        for(OpcionClasificacion opcionClasificacion : definicionClasificacion.obtenerOpciones()) {
            _opcionesFiltro.add(new OpcionFiltro(opcionClasificacion));
        }
    }

    public List<OpcionFiltro> getOpcionesFiltro()
    {
        return  _opcionesFiltro;
    }

    public String getNombre() {
        return _nombre;
    }

    public Long getIdDefinicion() {
        return _idDefinicion;
    }

    public List<OpcionFiltro> getOpcionesFiltroSeleccionadas() {
        List<OpcionFiltro> opcionesFiltroSeleccionadas = new ArrayList<>();
        for(OpcionFiltro opcionFiltro : _opcionesFiltro) {
            if(opcionFiltro.isSeleccionada())
            {
                opcionesFiltroSeleccionadas.add(opcionFiltro);
            }
        }
        return opcionesFiltroSeleccionadas;
    }
}
