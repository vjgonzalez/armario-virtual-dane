package ar.uba.fi.armariovirtual.activities.quemepongo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import java.util.List;

import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.activities.BarraBaseActivity;
import ar.uba.fi.armariovirtual.modelo.OpcionClasificacion;
import ar.uba.fi.armariovirtual.modelo.adaptadores.AdaptadorOpcionesQueMePongo;
import ar.uba.fi.armariovirtual.presenters.QueMePongoPreguntasPresenter;
import ar.uba.fi.armariovirtual.utils.IReceptorData;

public class QueMePongoPreguntasActivity extends BarraBaseActivity implements IReceptorData<Integer> {

    private GridView _opcionesGridView;
    private AdaptadorOpcionesQueMePongo _adaptadorOpciones;
    private TextView _preguntaTV;
    private QueMePongoPreguntasPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.que_me_pongo_preguntas);

        presenter = new QueMePongoPreguntasPresenter(this);

        _opcionesGridView = findViewById(R.id.grilla_opciones);
        _preguntaTV = findViewById(R.id.pregunta);

        presenter.inicializarPreguntas();

        setUpBarraConBotonDeAtras(true, false, null);

        this.btnAtrasConfig.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                presenter.irAPreguntaAnterior();
            }
        });
    }

    public void cargarDatosPregunta(String pregunta, List<OpcionClasificacion> opciones) {
        _preguntaTV.setText(pregunta);
        _adaptadorOpciones = new AdaptadorOpcionesQueMePongo(opciones, this, this);
        _opcionesGridView.setAdapter(_adaptadorOpciones);
    }

    @Override
    public void recibirData(Integer data) {
        int posicion = data;

        presenter.setOpcionElegida((OpcionClasificacion) _adaptadorOpciones.getItem(posicion));
    }

    @Override
    public void onBackPressed() {
        presenter.irAPreguntaAnterior();
    }

    public void finalizarCuestionario(long[] idsConjuntosResultado) {
        Bundle b = new Bundle();
        b.putLongArray(QueMePongoResultadoActivity.PARAMETRO_INTENT_IDS_CONJUNTOS_RESULTADO, idsConjuntosResultado);
        Intent myIntent = new Intent(QueMePongoPreguntasActivity.this, QueMePongoResultadoActivity.class);
        myIntent.putExtras(b);

        startActivity(myIntent);
    }

    public void volverAIntroduccion() {
        Intent intent = new Intent(QueMePongoPreguntasActivity.this, QueMePongoIntroduccionActivity.class);
        startActivity(intent);
    }
}
