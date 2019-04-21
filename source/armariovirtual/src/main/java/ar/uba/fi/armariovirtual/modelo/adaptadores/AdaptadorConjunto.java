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
import ar.uba.fi.armariovirtual.activities.DetalleConjuntoActivity;
import ar.uba.fi.armariovirtual.modelo.Clasificable;
import ar.uba.fi.armariovirtual.modelo.Conjunto;
import ar.uba.fi.armariovirtual.modelo.filtros.FiltroClasificaciones;
import ar.uba.fi.armariovirtual.utils.ImageUtils;

public class AdaptadorConjunto extends AdaptadorVestuario {

    /**
     * Constructor
     *
     * @param context   Contexto donde se utiliza la lista de conjuntos
     * @param conjuntos Lista de conjuntos que debe mostrar la vista actual
     */
    public AdaptadorConjunto(Activity context, List<Conjunto> conjuntos) {
        super(context, conjuntos);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        Conjunto conjunto = (Conjunto) _items.get(position);
        nombreStr = conjunto.getNombre();
        rutaImagen = conjunto.getRutaImagen();

        return super.getView(position, convertView, parent);
    }

    protected void editarItem(int posicionAEditar) {
        Conjunto conjuntoAEditar = (Conjunto) _items.get(posicionAEditar);
        Intent myIntent = new Intent(_context, DetalleConjuntoActivity.class);
        Long idConjunto = conjuntoAEditar.getId();
        myIntent.putExtra(DetalleConjuntoActivity.PARAMETRO_INTENT_ID_CONJUNTO, idConjunto);
        _context.startActivity(myIntent);
    }

    public void actualizarFiltro(FiltroClasificaciones filtroClasificaciones) {
        List<Clasificable> conjuntosResultadoQuery = filtroClasificaciones.obtenerResultadosFiltrados(Conjunto.class, Conjunto.NOMBRE_TABLA, Conjunto.class.getCanonicalName());
        _items.clear();
        _items.addAll(conjuntosResultadoQuery);
        Collections.sort(_items);
        notifyDataSetChanged();
    }

    @Override
    protected void eliminarItem(final int posicionAEliminar) {
        funcionalidadProtegida = _context.getResources().getString(R.string.funcionalidad_eliminar_conjuntos);
        super.eliminarItem(posicionAEliminar);
    }

    protected void mostrarImagen(ImageView vistaImagen){
        if (rutaImagen != null) {
            ImageUtils.ajustarImagenEnImageView(_context, vistaImagen, rutaImagen);
        } else {
            vistaImagen.setVisibility(View.INVISIBLE);
        }
    }
}
