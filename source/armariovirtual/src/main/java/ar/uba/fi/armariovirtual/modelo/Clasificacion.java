package ar.uba.fi.armariovirtual.modelo;

import java.util.List;

import ar.uba.fi.utilidadesdane.persistencia.ParObjetoPersistente;
import ar.uba.fi.utilidadesdane.persistencia.ObjetoPersistente;

public class Clasificacion extends ObjetoPersistente {

    protected DefinicionClasificacion definicionClasificacion;


    public Clasificacion() {
        super();
    }

    public Clasificacion(DefinicionClasificacion definicionClasificacion){
        this.setDefinicionClasificacion(definicionClasificacion);
        save();
    }

    public DefinicionClasificacion getDefinicionClasificacion(){
        return definicionClasificacion;
    }

    public void setDefinicionClasificacion(DefinicionClasificacion definicionClasificacion){
        this.definicionClasificacion = definicionClasificacion;
    }

    public void agregarOpcionElegida(OpcionClasificacion opcionClasificacion){
        this.agregarRelacionConObjeto(opcionClasificacion);
    }

    public List<OpcionClasificacion> obtenerOpcionesElegidas(){
        return this.obtenerRelaciones(OpcionClasificacion.class);
    }

    public void quitarOpcionElegida(OpcionClasificacion opcionClasificacion){
        if(opcionClasificacion == null)
            return;
        ParObjetoPersistente.eliminarPar(this,opcionClasificacion);
    }

    public void quitarTodasLasOpcionesElegidas() {
        List<OpcionClasificacion> opcionesElegidas = obtenerOpcionesElegidas();
        for(OpcionClasificacion opcionElegida : opcionesElegidas)
        {
            quitarOpcionElegida(opcionElegida);
        }
    }

    public static Clasificacion obtenerClasificacion(Clasificable objetoClasificado, DefinicionClasificacion definicionClasificacion) {
        for(Clasificacion clasificacion : objetoClasificado.obtenerClasificaciones())
        {
            if(clasificacion.getDefinicionClasificacion().getId().equals(definicionClasificacion.getId()))
            {
                return  clasificacion;
            }
        }
        return  null;
    }

    public static Clasificacion obtenerClasificacion(Clasificable objetoClasificado, Long idDefinicionClasificacion) {
        for(Clasificacion clasificacion : objetoClasificado.obtenerClasificaciones())
        {
            if(clasificacion.getDefinicionClasificacion().getId().equals(idDefinicionClasificacion))
            {
                return  clasificacion;
            }
        }
        return  null;
    }

    public static void eliminarClasificaciones(DefinicionClasificacion definicionClasificacion){
        List<Clasificacion> clasificaciones = encontrar(Clasificacion.class, "definicion_clasificacion=?",  new String[]{String.valueOf(definicionClasificacion.getId())}, null, null, null);
        for (Clasificacion clasificacion : clasificaciones) {
            clasificacion.delete();
        }
    }

    public static List<Clasificacion> obtenerTodas(){
        return listarTodos(Clasificacion.class);
    }
}
