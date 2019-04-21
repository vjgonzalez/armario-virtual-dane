package ar.uba.fi.armariovirtual.activities;

import java.util.List;

import ar.uba.fi.armariovirtual.modelo.Prenda;
import ar.uba.fi.armariovirtual.modelo.adaptadores.AdaptadorSeleccionarPrenda;
import ar.uba.fi.utilidadesdane.persistencia.ObjetoPersistente;

public class SeleccionarPrendaActivity extends SeleccionarVestuarioActivity {

    @Override
    protected String getTituloPantalla() {
        return "SELECCIONAR PRENDA";
    }

    @Override
    protected void inicializarAdaptador() {
        List<Prenda> prendas = ObjetoPersistente.listarTodos(Prenda.class);
        _adaptador = new AdaptadorSeleccionarPrenda(this, prendas, this);
    }

}
