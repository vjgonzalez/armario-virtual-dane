package ar.uba.fi.armariovirtual.presenters;

import java.util.Date;
import java.util.List;

import ar.uba.fi.armariovirtual.activities.calendario.DetalleFechaActivity;
import ar.uba.fi.armariovirtual.modelo.EventoCalendario;
import ar.uba.fi.utilidadesdane.calendario.ObjetoCalendarizado;

public class DetalleFechaPresenter {

    private DetalleFechaActivity view;
    private Date fecha;
    private List<EventoCalendario> eventos;


    public DetalleFechaPresenter(DetalleFechaActivity view, long fechaLong) {
        this.view = view;
        this.fecha = new Date(fechaLong);
    }

    public void listarEventos() {
        eventos = ObjetoCalendarizado.obtenerPorFecha(EventoCalendario.class, fecha);
        if (eventos.size() > 0) {
            view.mostrarEventos(eventos, fecha);
        } else {
            view.mostrarFechaSinEventos(fecha);
        }
    }

}
