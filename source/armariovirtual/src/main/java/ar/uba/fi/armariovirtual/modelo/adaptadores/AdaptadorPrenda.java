package ar.uba.fi.armariovirtual.modelo.adaptadores;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import java.util.Collections;
import java.util.List;

import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.activities.DetallePrendaActivity;
import ar.uba.fi.armariovirtual.modelo.Clasificable;
import ar.uba.fi.armariovirtual.modelo.Prenda;
import ar.uba.fi.armariovirtual.modelo.filtros.FiltroClasificaciones;
import ar.uba.fi.armariovirtual.utils.ImageUtils;

public class AdaptadorPrenda extends AdaptadorVestuario {

    /**
     * Constructor
     *
     * @param context Contexto donde se utiliza la lista de prendas
     * @param prendas Lista de prendas a mostras en la vista actual
     */
    public AdaptadorPrenda(Activity context, List<Prenda> prendas) {
        super(context, prendas);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Prenda prenda = (Prenda) _items.get(position);
        nombreStr = prenda.getNombre();
        rutaImagen = prenda.getRutaImagen();

        return super.getView(position, convertView, parent);
    }

    protected void mostrarImagen(ImageView vistaImagen) {
        if (!rutaImagen.isEmpty()) {
            ImageUtils.ajustarImagenEnRoundedImageView(_context, vistaImagen, rutaImagen);
            vistaImagen.setVisibility(View.VISIBLE);
        } else {
            vistaImagen.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    protected void eliminarItem(final int posicionAEliminar) {
        funcionalidadProtegida = _context.getResources().getString(R.string.funcionalidad_eliminar_prendas);
        super.eliminarItem(posicionAEliminar);
    }

    protected void editarItem(int posicionAEditar) {
        Prenda prendaAEditar = (Prenda) _items.get(posicionAEditar);
        Intent myIntent = new Intent(_context, DetallePrendaActivity.class);
        Long idPrenda = prendaAEditar.getId();
        myIntent.putExtra(DetallePrendaActivity.PARAMETRO_INTENT_ID_PRENDA, idPrenda);
        _context.startActivity(myIntent);
    }

    public void actualizarFiltro(FiltroClasificaciones filtroClasificaciones) {
        List<Clasificable> resQuery = filtroClasificaciones.obtenerResultadosFiltrados(Prenda.class, Prenda.NOMBRE_TABLA, Prenda.class.getCanonicalName());
        _items.clear();
        _items.addAll(resQuery);
        Collections.sort(_items);
        notifyDataSetChanged();
    }
}
