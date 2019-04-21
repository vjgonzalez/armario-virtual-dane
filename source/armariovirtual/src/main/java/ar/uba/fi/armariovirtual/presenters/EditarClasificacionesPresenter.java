package ar.uba.fi.armariovirtual.presenters;

import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.armariovirtual.activities.EditarClasificacionesActivity;
import ar.uba.fi.armariovirtual.modelo.DefinicionClasificacion;
import ar.uba.fi.armariovirtual.modelo.OpcionClasificacion;

public class EditarClasificacionesPresenter {

    private EditarClasificacionesActivity view;
    private List<DefinicionClasificacion> clasificaciones;
    private int posicionItemActual;
    private static final int POSICION_NUEVA_CLASIFICACION = 999999;

    public EditarClasificacionesPresenter(EditarClasificacionesActivity view) {
        this.view = view;
        clasificaciones = DefinicionClasificacion.obtenerTodas();
        posicionItemActual = 0;
    }

    public void mostrarClasificacionActual() {
        if (clasificaciones.size() == 0)
            return;
        DefinicionClasificacion clasificacionActual = clasificaciones.get(posicionItemActual);
        String nombre = clasificacionActual.getNombreAMostrar();
        String pregunta = clasificacionActual.getPreguntaAsociada();
        List<OpcionClasificacion> opcionesActuales = clasificacionActual.obtenerOpciones();

        view.actualizarClasificacionActual(nombre, pregunta, opcionesActuales);

    }

    public void clasificacionAnterior() {
        posicionItemActual--;
        if (posicionItemActual == -1 || posicionItemActual >= clasificaciones.size()) { //si era la primera, o una nueva
            posicionItemActual = clasificaciones.size() - 1;
        }
        mostrarClasificacionActual();
    }

    public void clasificacionSiguiente() {
        posicionItemActual++;
        if (posicionItemActual >= clasificaciones.size()) { //si era la última, o una nueva
            posicionItemActual = 0;
        }
        mostrarClasificacionActual();
    }


    public void nuevaClasificacion() {
        posicionItemActual = POSICION_NUEVA_CLASIFICACION;
        OpcionClasificacion nueva = new OpcionClasificacion();

        view.mostrarNuevaClasificacion(new ArrayList<OpcionClasificacion>(), nueva);
    }

    public void nuevaOpcion() {
        OpcionClasificacion nueva = new OpcionClasificacion();
        view.mostrarNuevaOpcion(nueva);
    }

    public void guardarCambiosClasificacionActual(String nombre, String pregunta, List<OpcionClasificacion> opcionesActuales) {
        DefinicionClasificacion actual;

        if (posicionItemActual == POSICION_NUEVA_CLASIFICACION) {
            actual = new DefinicionClasificacion();
            clasificaciones.add(actual);
            posicionItemActual = clasificaciones.size() - 1;
            actual.save();
        }

        actual = clasificaciones.get(posicionItemActual);
        actual.setNombreAMostrar(nombre);
        actual.setPreguntaAsociada(pregunta);

        view.guardarCambiosOpciones();

        for (OpcionClasificacion opcion : opcionesActuales) {
            opcion.save();
            actual.agregarOpcion(opcion);
        }
        actual.save();
    }

    public boolean hayCambiosSinGuardar(String nombreEscrito, String preguntaEscrita) {
        return posicionItemActual == POSICION_NUEVA_CLASIFICACION || !nombreEscrito.equals(clasificaciones.get(posicionItemActual).getNombreAMostrar())
                || !preguntaEscrita.equals(clasificaciones.get(posicionItemActual).getPreguntaAsociada());
    }

    public void borrarClasificacion() {
        if (posicionItemActual != POSICION_NUEVA_CLASIFICACION) {
            clasificaciones.get(posicionItemActual).delete();
            clasificaciones.remove(posicionItemActual);
        }
        clasificacionSiguiente();
    }
}
