package ar.uba.fi.armariovirtual.modelo;

import java.util.List;
import ar.uba.fi.utilidadesdane.persistencia.ParObjetoPersistente;
import ar.uba.fi.utilidadesdane.persistencia.ObjetoPersistente;

public class DefinicionClasificacion extends ObjetoPersistente {

    protected String nombreAMostrar;
    protected String preguntaAsociada;

    public DefinicionClasificacion() {
        super();
    }

    public DefinicionClasificacion(String nombreAMostrar, String preguntaAsociada) {
        super();
        this.nombreAMostrar = nombreAMostrar;
        this.preguntaAsociada = preguntaAsociada;
        save();
    }

    public String getNombreAMostrar() {
        return nombreAMostrar;
    }

    public void setNombreAMostrar(String nombreAMostrar) {
        this.nombreAMostrar = nombreAMostrar;
    }

    public String getPreguntaAsociada() {
        return preguntaAsociada;
    }

    public void setPreguntaAsociada(String preguntaAsociada) {
        this.preguntaAsociada = preguntaAsociada;
    }

    public void agregarOpcion(OpcionClasificacion opcionClasificacion){
        this.agregarRelacionConObjeto(opcionClasificacion);
    }

    public List<OpcionClasificacion> obtenerOpciones(){
        return this.obtenerRelaciones(OpcionClasificacion.class);
    }

    private void quitarYEliminarOpcion(OpcionClasificacion opcionClasificacion){
        if(opcionClasificacion == null)
            return;
        ParObjetoPersistente.eliminarPar(this,opcionClasificacion);
        opcionClasificacion.delete();
    }

    public static DefinicionClasificacion obtener(String nombreAMostrar)
    {
        return ObjetoPersistente.encontrarPrimero(DefinicionClasificacion.class, "nombre_a_mostrar = ?", nombreAMostrar);
    }

    public static DefinicionClasificacion obtenerOCrear(String nombreAMostrar, String preguntaAsociada) {

        DefinicionClasificacion definicionClasificacion = obtener(nombreAMostrar);
        if(definicionClasificacion == null)
        {
            definicionClasificacion = new DefinicionClasificacion(nombreAMostrar, preguntaAsociada);
        }
        return  definicionClasificacion;
    }

    public static List<DefinicionClasificacion> obtenerTodas(){
        return listarTodos(DefinicionClasificacion.class);
    }

    @Override
    public boolean delete() {
        List<OpcionClasificacion> opciones = obtenerOpciones();

        for (OpcionClasificacion opcionActual : opciones) {
            quitarYEliminarOpcion(opcionActual);
        }

        Clasificacion.eliminarClasificaciones(this);

        return super.delete();
    }

}