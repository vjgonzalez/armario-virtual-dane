package ar.uba.fi.armariovirtual.modelo;

import android.content.Context;
import android.support.v4.util.Pair;

import java.text.ParseException;
import java.util.Date;

import app.ArmarioVirtualApplication;
import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.utilidadesdane.calendario.ObjetoCalendarizado;
import ar.uba.fi.utilidadesdane.notificaciones.AdministradorNotificaciones;
import ar.uba.fi.utilidadesdane.notificaciones.Notificacion;
import ar.uba.fi.utilidadesdane.utils.FechaUtils;

public class EventoCalendario extends ObjetoCalendarizado {

    public static final String[] opcionesTiempoNotificacion = {"3 horas antes", "12 horas antes", "1 día antes", "2 días antes"};
    public static final long[] tiemposARestarMs = {3 * 3600000, 12 * 3600000, 24 * 3600000, 48 * 3600000};
    public static final int INDICE_SIN_NOTIFICACION = -1;
    private Conjunto conjunto;
    private String nombreEvento;
    private int indiceNotificacion;

    public EventoCalendario() {
        super();
    }

    public EventoCalendario(String fecha, String hora, Conjunto conjunto, String nombreEvento) {
        super(fecha, hora);
        this.conjunto = conjunto;
        this.nombreEvento = nombreEvento;
        this.indiceNotificacion = INDICE_SIN_NOTIFICACION;
        save();
    }

    public EventoCalendario(Date fecha, Conjunto conjunto, String nombreEvento) {
        super(fecha);
        this.conjunto = conjunto;
        this.nombreEvento = nombreEvento;
        this.indiceNotificacion = INDICE_SIN_NOTIFICACION;
        save();
    }

    public EventoCalendario(long fecha, Conjunto conjunto, String nombreEvento) {
        super(fecha);
        this.conjunto = conjunto;
        this.nombreEvento = nombreEvento;
        this.indiceNotificacion = INDICE_SIN_NOTIFICACION;
        save();
    }

    public Conjunto getConjunto() {
        return conjunto;
    }

    public void setConjunto(Conjunto conjunto) {
        this.conjunto = conjunto;
        save();
    }

    public String getNombreEvento() {
        return nombreEvento;
    }

    public void setNombreEvento(String nombreEvento) {
        this.nombreEvento = nombreEvento;
        save();
    }

    public boolean crearNotificacion(Context context, Class activityAEjecutar, Pair<String, String> argsParaActivity, int indiceTiempoARestar) {
        AdministradorNotificaciones administradorNotificaciones = new AdministradorNotificaciones();
        Notificacion notificacion = new Notificacion(context, nombreEvento, getFecha() + " a las " + getHoraSinSegundos(), R.drawable.notificacion, activityAEjecutar, argsParaActivity);

        long tiempoARestar = tiemposARestarMs[indiceTiempoARestar];

        try {
            long fechaNotificacion = FechaUtils.restarTiempo( this.getDate(), tiempoARestar);
            boolean notificacionAgendada = administradorNotificaciones.agendarNotificacionConWakeUp(notificacion, fechaNotificacion, context);
            if (!notificacionAgendada)
                return false;
        } catch (ParseException exception) {
            return false;
        }
        setIdNotificacion(notificacion);
        setIndiceNotificacion(indiceTiempoARestar);
        return true;
    }

    public void setIndiceNotificacion(int indiceNotificacion){
        this.indiceNotificacion = indiceNotificacion;
        save();
    }

    public int getIndiceNotificacion(){
        return this.indiceNotificacion;
    }

    public void cancelarNotificacion(Context context) {
        AdministradorNotificaciones administradorNotificaciones = new AdministradorNotificaciones();
        administradorNotificaciones.cancelarNotificacion(context, getIdNotificacion());
    }

    @Override
    public boolean delete() {
        if (tieneNotificacion())
            cancelarNotificacion(ArmarioVirtualApplication.getAppContext());
        return super.delete();
    }

}
