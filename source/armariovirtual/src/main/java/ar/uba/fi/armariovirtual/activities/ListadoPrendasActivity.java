package ar.uba.fi.armariovirtual.activities;

import android.content.Intent;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.armariovirtual.modelo.Prenda;
import ar.uba.fi.armariovirtual.modelo.adaptadores.AdaptadorPrenda;

public class ListadoPrendasActivity extends ListadoVestuarioActivity {

    @Override
    protected void inicializarAdaptador() {
        List<Prenda> prendas = new ArrayList<>();
        _adaptadorVestuario = new AdaptadorPrenda(this, prendas);
    }

    @Override
    protected void inicializarBotones() {
        conjuntosBtn.setSelected(false);

        conjuntosBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Intent myIntent = new Intent(ListadoPrendasActivity.this, ListadoConjuntosActivity.class);
                startActivity(myIntent);
            }
        });

        prendasBtn.setSelected(true);

    }
}
