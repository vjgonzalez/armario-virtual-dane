package ar.uba.fi.armariovirtual.activities;

import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.TextView;

import java.util.List;

import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.modelo.Conjunto;
import ar.uba.fi.armariovirtual.modelo.Prenda;
import ar.uba.fi.armariovirtual.modelo.adaptadores.AdaptadorPrendaVistaConjunto;
import ar.uba.fi.utilidadesdane.persistencia.ObjetoPersistente;

public class VistaConjuntoActivity extends BarraBaseActivity {

    public static final String PARAMETRO_INTENT_ID_CONJUNTO = "id_conjunto";

    private Conjunto conjunto = null;
    private Long idConjunto = (long) -1; // ID = -1 -> Nuevo con junto - ID >= 0 -> Editar conjunto

    private List<Prenda> _prendas;

    private GridView _prendasGridView;
    private AdaptadorPrendaVistaConjunto _adaptadorPrenda;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        idConjunto = getIntent().getLongExtra(PARAMETRO_INTENT_ID_CONJUNTO, -1);
        if (idConjunto > -1) {
            conjunto = ObjetoPersistente.encontrarPorId(Conjunto.class, idConjunto);
        }

        // Inicializar elementos comunes a Nuevo Conjunto y Editar Conjunto
        setContentView(R.layout.vista_conjunto);

        (findViewById(R.id.volver_btn)).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                finish();
            }
        });

        // Mostrar nombre
        TextView nombreTxt = findViewById(R.id.nombre_conjunto);
        nombreTxt.setText(conjunto.getNombre());



        _prendasGridView = findViewById(R.id.grilla_prendas);
        _prendas = conjunto.obtenerPrendas();
        _adaptadorPrenda = new AdaptadorPrendaVistaConjunto(this,_prendas);
        _prendasGridView.setAdapter(_adaptadorPrenda);

        setUpBarraConBotonDeAtras(true, false, null);

    }

    @Override
    public void onBackPressed() {
        finish();
    }
}
