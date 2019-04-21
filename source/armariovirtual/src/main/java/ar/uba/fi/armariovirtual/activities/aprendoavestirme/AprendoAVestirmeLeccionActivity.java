package ar.uba.fi.armariovirtual.activities.aprendoavestirme;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.activities.BarraBaseActivity;
import ar.uba.fi.armariovirtual.modelo.Leccion;
import ar.uba.fi.armariovirtual.utils.ImageUtils;
import ar.uba.fi.utilidadesdane.cuestionario.AdministradorCuestionario;

public class AprendoAVestirmeLeccionActivity extends BarraBaseActivity {

    public static final String PARAMETRO_INTENT_LECCION = "leccion";

    private Leccion _leccionActual;
    private int diapositivaActualIdx;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.aprendo_a_vestirme_leccion);

        _leccionActual = (Leccion) getIntent().getSerializableExtra(PARAMETRO_INTENT_LECCION);

        TextView nombre = findViewById(R.id.nombre);
        nombre.setText(_leccionActual.getNombre());

        TextView descripcion = findViewById(R.id.descripcion);
        descripcion.setText(_leccionActual.getDescripcion());

        diapositivaActualIdx = 0;
        mostrarDiapositivaActual();

        (findViewById(R.id.continuar_btn)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(AprendoAVestirmeLeccionActivity.this, AprendoAVestirmePreguntasActivity.class);
                startActivity(myIntent);
                Intent intent = AdministradorCuestionario.obtenerIntent(getApplicationContext(), _leccionActual.getIdCuestionario(), AprendoAVestirmePreguntasActivity.class, 2,
                        R.layout.aprendo_a_vestirme_preguntas, R.layout.aprendo_a_vestirme_item_opciones);
                startActivity(intent);
            }
        });

        (findViewById(R.id.diapositiva_anterior_btn)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(diapositivaActualIdx > 0)
                {
                    diapositivaActualIdx--;
                    mostrarDiapositivaActual();
                }
            }
        });

        (findViewById(R.id.diapositiva_siguiente_btn)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if(diapositivaActualIdx < _leccionActual.getDiapositivas().size() - 1)
                {
                    diapositivaActualIdx++;
                    mostrarDiapositivaActual();
                }
            }
        });

        setUpBarraConBotonDeAtras(true, false, new Intent(AprendoAVestirmeLeccionActivity.this, AprendoAVestirmeMenuActivity.class));

    }

    private void mostrarDiapositivaActual() {

        if(_leccionActual.getDiapositivas().size() < diapositivaActualIdx)
            return;

        ImageUtils.ajustarImagenEnRoundedImageView( this,
                                                    (ImageView) findViewById(R.id.diapositiva),
                                                    _leccionActual.getDiapositivas().get(diapositivaActualIdx));

        findViewById(R.id.diapositiva_anterior_btn).setVisibility( (diapositivaActualIdx > 0) ? View.VISIBLE : View.INVISIBLE);
        findViewById(R.id.diapositiva_siguiente_btn).setVisibility( (diapositivaActualIdx < _leccionActual.getDiapositivas().size() - 1) ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AprendoAVestirmeLeccionActivity.this, AprendoAVestirmeMenuActivity.class);
        startActivity(intent);
    }
}
