package ar.uba.fi.armariovirtual.modelo;

import java.util.List;

import ar.uba.fi.utilidadesdane.persistencia.ParObjetoPersistente;
import ar.uba.fi.utilidadesdane.persistencia.ObjetoPersistente;

public class Clasificable extends ObjetoPersistente {

    public List<Clasificacion> obtenerClasificaciones() {
        return this.obtenerRelaciones(Clasificacion.class);
    }

    public void quitarClasificacion(Clasificacion clasificacion) {
        if (clasificacion == null)
            return;
        ParObjetoPersistente.eliminarPar(this, clasificacion);
    }

    public void agregarClasificacion(Clasificacion clasificacion) {
        this.agregarRelacionConObjeto(clasificacion, true);
    }

    public List<Clasificacion> obtenerOCrearClasificaciones() {

        // Si el Clasificable existe, usar sus clasificaciones (y crear las que falten)
        List<Clasificacion> clasificaciones = this.obtenerClasificaciones();
        // Ver si faltan clasificaciones basándonos en la lista de definiciones
        List<DefinicionClasificacion> definicionClasificaciones = ObjetoPersistente.listarTodos(DefinicionClasificacion.class);
        for (DefinicionClasificacion definicion : definicionClasificaciones) {
            boolean existe = false;
            for (Clasificacion clasificacion : clasificaciones) {
                if (clasificacion.getDefinicionClasificacion().getId().equals(definicion.getId())) {
                    existe = true;
                }
            }
            if (!existe) {
                // No existe Clasificacion para esta DefinicionClasificacion para este Clasificable. Crearla
                Clasificacion nuevaClasificacion = new Clasificacion(definicion);
                this.agregarClasificacion(nuevaClasificacion);
                clasificaciones.add(nuevaClasificacion);
            }
        }

        return clasificaciones;
    }


}
