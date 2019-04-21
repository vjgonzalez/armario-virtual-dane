package ar.uba.fi.armariovirtual.activities;

import java.util.List;

import ar.uba.fi.armariovirtual.modelo.Conjunto;
import ar.uba.fi.armariovirtual.modelo.adaptadores.AdaptadorSeleccionarConjunto;
import ar.uba.fi.utilidadesdane.persistencia.ObjetoPersistente;

public class SeleccionarConjuntoActivity extends SeleccionarVestuarioActivity {

    @Override
    protected String getTituloPantalla() {
        return "SELECCIONAR CONJUNTO";
    }

    @Override
    protected void inicializarAdaptador() {
        List<Conjunto> conjuntos = ObjetoPersistente.listarTodos(Conjunto.class);
        _adaptador = new AdaptadorSeleccionarConjunto(this, conjuntos, this);
    }
}
