package ar.uba.fi.armariovirtual.presenters;

import android.support.v4.util.Pair;

import java.text.ParseException;
import java.util.Date;

import ar.uba.fi.armariovirtual.activities.calendario.DetalleEventoActivity;
import ar.uba.fi.armariovirtual.modelo.Conjunto;
import ar.uba.fi.armariovirtual.modelo.EventoCalendario;
import ar.uba.fi.utilidadesdane.persistencia.ObjetoPersistente;

public class DetalleEventoPresenter {

    private DetalleEventoActivity view;
    private long fechaLong;
    private String idEvento;
    private EventoCalendario eventoEditado = null;
    private Long idConjuntoSeleccionado = (long) -1;


    public DetalleEventoPresenter(DetalleEventoActivity view, long fechaLong, String idEvento) {
        this.view = view;
        this.fechaLong = fechaLong;
        this.idEvento = idEvento;

    }

    public void setUpEvento() {
        if (!idEvento.equals(DetalleEventoActivity.VALOR_INTENT_ID_EVENTO_NULO)) {
            Long idLong = Long.parseLong(idEvento);
            eventoEditado = ObjetoPersistente.encontrarPorId(EventoCalendario.class, idLong);
        }

        if (eventoEditado != null) {
            try {
                fechaLong = eventoEditado.getDate().getTime();
            } catch (ParseException e) {
                //TODO
            }
            if (eventoEditado.getConjunto() != null) {
                String rutaImagen = eventoEditado.getConjunto() != null ? eventoEditado.getConjunto().getRutaImagen() : null;
                idConjuntoSeleccionado = eventoEditado.getConjunto().getId();
                view.setConjunto(rutaImagen, idConjuntoSeleccionado);
            }
            view.inicializarActivityParaEditarEvento(eventoEditado.getNombreEvento(), eventoEditado.getHoraSinSegundos(), eventoEditado.getIndiceNotificacion(), fechaLong);
        } else {
            view.inicializarActivityParaNuevoEvento();
        }
    }

    public void seleccionarConjunto(Long idConjuntoSeleccionado) {
        this.idConjuntoSeleccionado = idConjuntoSeleccionado;
        Conjunto conjunto = Conjunto.encontrarPorId(Conjunto.class, idConjuntoSeleccionado);
        String rutaImagen = conjunto != null ? conjunto.getRutaImagen() : null;
        view.setConjunto(rutaImagen, idConjuntoSeleccionado);
    }

    public void guardarCambiosEvento(String nombreEvento, Date fechaYHora, int indiceTiempoNotificacion) {
        if (eventoEditado == null)
            eventoEditado = new EventoCalendario();

        eventoEditado.setNombreEvento(nombreEvento);
        eventoEditado.setFecha(fechaYHora);
        eventoEditado.setHora(fechaYHora);

        if (idConjuntoSeleccionado != null && idConjuntoSeleccionado != -1)
            eventoEditado.setConjunto(Conjunto.encontrarPorId(Conjunto.class, idConjuntoSeleccionado));


        if (eventoEditado.tieneNotificacion()) {
            if (indiceTiempoNotificacion == EventoCalendario.INDICE_SIN_NOTIFICACION) {
                eventoEditado.cancelarNotificacion(view);
            } else {
                eventoEditado.cancelarNotificacion(view);
                //TODO cambiar activity a ejecutar
                String id = eventoEditado.getId().toString();
                eventoEditado.crearNotificacion(view, DetalleEventoActivity.class, new Pair<>(DetalleEventoActivity.PARAMETRO_INTENT_ID_EVENTO, id), indiceTiempoNotificacion);
            }
        } else if (indiceTiempoNotificacion != EventoCalendario.INDICE_SIN_NOTIFICACION) {
            String id = eventoEditado.getId().toString();
            eventoEditado.crearNotificacion(view, DetalleEventoActivity.class, new Pair<>(DetalleEventoActivity.PARAMETRO_INTENT_ID_EVENTO, id), indiceTiempoNotificacion);
        }


    }
}
