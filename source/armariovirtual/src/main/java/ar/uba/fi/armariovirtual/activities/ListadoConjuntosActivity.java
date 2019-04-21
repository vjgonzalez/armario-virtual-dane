package ar.uba.fi.armariovirtual.activities;

import android.content.Intent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.armariovirtual.modelo.Conjunto;
import ar.uba.fi.armariovirtual.modelo.adaptadores.AdaptadorConjunto;

public class ListadoConjuntosActivity extends ListadoVestuarioActivity {

    @Override
    protected void inicializarAdaptador() {
        List<Conjunto> conjuntos = new ArrayList<>();
        _adaptadorVestuario = new AdaptadorConjunto(this, conjuntos);
    }

    @Override
    protected void inicializarBotones() {
        prendasBtn.setSelected(false);

        prendasBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(ListadoConjuntosActivity.this, ListadoPrendasActivity.class);
                startActivity(myIntent);
            }
        });

        conjuntosBtn.setSelected(true);
    }
}
