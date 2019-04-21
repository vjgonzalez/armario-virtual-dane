package ar.uba.fi.armariovirtual.activities.quemepongo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.activities.BarraBaseActivity;
import ar.uba.fi.armariovirtual.activities.MenuPrincipalActivity;
import ar.uba.fi.armariovirtual.activities.VistaConjuntoActivity;
import ar.uba.fi.armariovirtual.modelo.Conjunto;
import ar.uba.fi.armariovirtual.modelo.adaptadores.AdaptadorConjuntoQueMePongo;
import ar.uba.fi.armariovirtual.utils.CodigoEmojis;
import ar.uba.fi.utilidadesdane.persistencia.ObjetoPersistente;

public class QueMePongoResultadoActivity extends BarraBaseActivity {

    public static final String PARAMETRO_INTENT_IDS_CONJUNTOS_RESULTADO = "idsConjuntosResultado";

    private GridView _conjuntosGridView;
    private AdaptadorConjuntoQueMePongo _adaptadorConjunto;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        long[] idsConjuntosResultado = getIntent().getLongArrayExtra(PARAMETRO_INTENT_IDS_CONJUNTOS_RESULTADO);

        if (idsConjuntosResultado.length > 0) {
            setContentView(R.layout.que_me_pongo_resultado);

            _conjuntosGridView = findViewById(R.id.grilla_conjuntos);

            List<Conjunto> conjuntos = new ArrayList<>();
            for (long idConjunto : idsConjuntosResultado) {
                conjuntos.add(ObjetoPersistente.encontrarPorId(Conjunto.class, idConjunto));
            }
            _adaptadorConjunto = new AdaptadorConjuntoQueMePongo(this, conjuntos);
            _conjuntosGridView.setAdapter(_adaptadorConjunto);

            _conjuntosGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Conjunto conjunto = _adaptadorConjunto.getItem(position);

                    Intent myIntent = new Intent(getApplicationContext(), VistaConjuntoActivity.class);
                    Long idConjunto = conjunto.getId();
                    myIntent.putExtra(VistaConjuntoActivity.PARAMETRO_INTENT_ID_CONJUNTO, idConjunto);
                    startActivityForResult(myIntent, 0);
                }
            });
        } else {
            setContentView(R.layout.que_me_pongo_resultado_vacio);
            TextView tituloVacio = findViewById(R.id.tituloQMPResultadoVacio);
            String nuevoTitulo = tituloVacio.getText() + CodigoEmojis.CARA_TRISTE_EMOJI;
            tituloVacio.setText(nuevoTitulo);
        }

        findViewById(R.id.repetir_btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(QueMePongoResultadoActivity.this, QueMePongoIntroduccionActivity.class);
                startActivity(myIntent);
            }
        });


        findViewById(R.id.salir_btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(QueMePongoResultadoActivity.this, MenuPrincipalActivity.class);
                startActivity(myIntent);
            }
        });

        setUpBarraConBotonDeAtras(true, false, new Intent(QueMePongoResultadoActivity.this, QueMePongoIntroduccionActivity.class));

    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(QueMePongoResultadoActivity.this, QueMePongoIntroduccionActivity.class);
        startActivity(intent);
    }
}
