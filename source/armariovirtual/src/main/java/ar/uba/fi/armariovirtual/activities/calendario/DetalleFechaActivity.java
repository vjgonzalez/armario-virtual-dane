package ar.uba.fi.armariovirtual.activities.calendario;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.util.Date;
import java.util.List;

import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.activities.BarraBaseActivity;
import ar.uba.fi.armariovirtual.modelo.EventoCalendario;
import ar.uba.fi.armariovirtual.modelo.adaptadores.AdaptadorListaEventos;
import ar.uba.fi.armariovirtual.presenters.DetalleFechaPresenter;
import ar.uba.fi.utilidadesdane.audio.AudioFondo;
import ar.uba.fi.utilidadesdane.utils.FechaUtils;

public class DetalleFechaActivity extends BarraBaseActivity {

    public static final String PARAMETRO_INTENT_FECHA = "fecha";

    private AdaptadorListaEventos adaptador;
    private DetalleFechaPresenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setUpActivity();
    }

    @Override
    public void onResume() {
        super.onResume();
        setUpActivity();
    }

    private void setUpActivity() {
        setContentView(R.layout.detalle_fecha);

        final long fechaLong = getIntent().getLongExtra(PARAMETRO_INTENT_FECHA, 0);

        presenter = new DetalleFechaPresenter(this, fechaLong);

        presenter.listarEventos();

        findViewById(R.id.nuevo_evento).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(DetalleFechaActivity.this, DetalleEventoActivity.class);
                intent.putExtra(DetalleEventoActivity.PARAMETRO_INTENT_FECHA, fechaLong);
                intent.putExtra(DetalleEventoActivity.PARAMETRO_INTENT_ID_EVENTO, DetalleEventoActivity.VALOR_INTENT_ID_EVENTO_NULO);
                startActivity(intent);
            }
        });

        setUpBarraConBotonDeAtras(false, false, new Intent(DetalleFechaActivity.this, CalendarioActivity.class));

        try {
            AudioFondo.start(this, 8, false);
        } catch (Exception e) {
            AudioFondo.setSilencio(true);
        }
    }

    public void mostrarEventos(List<EventoCalendario> eventos, Date fecha) {
        String descripcion = String.format(getResources().getString(R.string.subtitulo_fecha_con_eventos),FechaUtils.convertirDateATexto(fecha));
        ((TextView) findViewById(R.id.descripcion)).setText(descripcion);
        adaptador = new AdaptadorListaEventos(eventos, this);
        ((ListView) findViewById(R.id.lista_eventos)).setAdapter(adaptador);
    }

    public void mostrarFechaSinEventos(Date fecha) {
        String descripcion = String.format(getResources().getString(R.string.subtitulo_fecha_sin_eventos),FechaUtils.convertirDateATexto(fecha));
        ((TextView) findViewById(R.id.descripcion)).setText(descripcion);
    }
}
