package ar.uba.fi.armariovirtual.modelo.adaptadores;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import ar.uba.fi.armariovirtual.modelo.Clasificable;
import ar.uba.fi.armariovirtual.modelo.Conjunto;
import ar.uba.fi.armariovirtual.modelo.filtros.FiltroClasificaciones;
import ar.uba.fi.armariovirtual.utils.IReceptorData;

public class AdaptadorSeleccionarConjunto extends AdaptadorSeleccionarVestuario {

    public AdaptadorSeleccionarConjunto(Activity context, List<Conjunto> conjuntos, IReceptorData<Long> receptorId) {
        super(context, conjuntos, receptorId);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Conjunto conjunto = (Conjunto) _items.get(position);
        nombreStr = conjunto.getNombre();
        rutaImagen = conjunto.getRutaImagen();

        return super.getView(position, convertView, parent);
    }

    public void actualizarFiltro(FiltroClasificaciones filtroClasificaciones) {
        List<Clasificable> conjuntosResultadoQuery = filtroClasificaciones.obtenerResultadosFiltrados(Conjunto.class, Conjunto.NOMBRE_TABLA, Conjunto.class.getCanonicalName());
        _items.clear();
        _items.addAll(conjuntosResultadoQuery);
        Collections.sort(_items);
        notifyDataSetChanged();
    }
}
