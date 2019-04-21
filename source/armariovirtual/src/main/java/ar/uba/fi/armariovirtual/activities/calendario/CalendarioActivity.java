package ar.uba.fi.armariovirtual.activities.calendario;

import android.content.Intent;
import android.os.Bundle;

import java.util.Date;
import java.util.List;

import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.activities.BarraBaseActivity;
import ar.uba.fi.armariovirtual.activities.MenuPrincipalActivity;
import ar.uba.fi.armariovirtual.modelo.EventoCalendario;
import ar.uba.fi.utilidadesdane.audio.AudioFondo;
import ar.uba.fi.utilidadesdane.calendario.CalendarioView;
import ar.uba.fi.utilidadesdane.calendario.ObjetoCalendarizado;
import ar.uba.fi.utilidadesdane.calendario.OnSeleccionFechaListener;
import ar.uba.fi.utilidadesdane.utils.FechaUtils;

public class CalendarioActivity extends BarraBaseActivity implements OnSeleccionFechaListener {

    protected CalendarioView calendarioView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cargarActivity();
    }

    protected void cargarActivity(){
        setContentView(R.layout.calendario);
        calendarioView = findViewById(R.id.calendario_view);
        calendarioView.setClaseObjetoCalendarizado(EventoCalendario.class);
        calendarioView.setOnSeleccionFechaListener(this);
        setUpBarraConBotonDeAtras(false, false, new Intent(CalendarioActivity.this, MenuPrincipalActivity.class));
        try {
            AudioFondo.start(this, 7, false);
        } catch (Exception e) {
            AudioFondo.setSilencio(true);
        }
    }

    @Override
    public void onSeleccionFechaListener(List<ObjetoCalendarizado> eventos, Date fechaSeleccionada) {
        Intent intent = new Intent(CalendarioActivity.this, DetalleFechaActivity.class);
        intent.putExtra(DetalleFechaActivity.PARAMETRO_INTENT_FECHA, FechaUtils.convertirDateALong(fechaSeleccionada));
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
        cargarActivity();
    }

}
