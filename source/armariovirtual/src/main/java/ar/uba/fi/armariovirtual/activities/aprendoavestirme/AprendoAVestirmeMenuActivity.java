package ar.uba.fi.armariovirtual.activities.aprendoavestirme;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.activities.BarraBaseActivity;
import ar.uba.fi.armariovirtual.activities.MenuPrincipalActivity;
import ar.uba.fi.armariovirtual.activities.VistaConjuntoActivity;
import ar.uba.fi.armariovirtual.activities.quemepongo.QueMePongoIntroduccionActivity;
import ar.uba.fi.armariovirtual.modelo.Conjunto;
import ar.uba.fi.armariovirtual.modelo.Leccion;
import ar.uba.fi.armariovirtual.modelo.adaptadores.AdaptadorConjuntoQueMePongo;
import ar.uba.fi.armariovirtual.modelo.adaptadores.AdaptadorLeccion;
import ar.uba.fi.utilidadesdane.audio.AudioFondo;
import ar.uba.fi.utilidadesdane.persistencia.ObjetoPersistente;

public class AprendoAVestirmeMenuActivity extends BarraBaseActivity {

    private GridView _leccionesGridView;
    private AdaptadorLeccion _adaptadorLecciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.aprendo_a_vestirme_menu);

        _leccionesGridView = findViewById(R.id.grilla_lecciones);

        _adaptadorLecciones = new AdaptadorLeccion(this);
        _leccionesGridView.setAdapter(_adaptadorLecciones);

        _leccionesGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id){

                Leccion leccion = (Leccion) _adaptadorLecciones.getItem(position);
                Log.d("msg", "Tap en Lección " + leccion.getNombre() + " - " + leccion.getDescripcion());

                Intent myIntent = new Intent(getApplicationContext(), AprendoAVestirmeLeccionActivity.class);
                myIntent.putExtra(AprendoAVestirmeLeccionActivity.PARAMETRO_INTENT_LECCION, leccion);
                startActivity(myIntent);
            }
        });

        setUpBarraConBotonDeAtras(false, false, new Intent(AprendoAVestirmeMenuActivity.this, MenuPrincipalActivity.class));

        try {
            AudioFondo.start(this, 2, false);
        } catch (Exception e) {
            AudioFondo.setSilencio(true);
        }
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AprendoAVestirmeMenuActivity.this, MenuPrincipalActivity.class);
        startActivity(intent);
    }
}
