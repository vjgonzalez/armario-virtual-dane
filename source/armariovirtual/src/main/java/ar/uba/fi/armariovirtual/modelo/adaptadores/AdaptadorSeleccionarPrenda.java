package ar.uba.fi.armariovirtual.modelo.adaptadores;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;

import java.util.Collections;
import java.util.List;

import ar.uba.fi.armariovirtual.modelo.Clasificable;
import ar.uba.fi.armariovirtual.modelo.Prenda;
import ar.uba.fi.armariovirtual.modelo.filtros.FiltroClasificaciones;
import ar.uba.fi.armariovirtual.utils.IReceptorData;

public class AdaptadorSeleccionarPrenda extends AdaptadorSeleccionarVestuario {

    public AdaptadorSeleccionarPrenda(Activity context, List<Prenda> prendas, IReceptorData<Long> receptorIdPrenda) {
        super(context, prendas, receptorIdPrenda);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        Prenda prenda = (Prenda) _items.get(position);
        nombreStr = prenda.getNombre();
        rutaImagen = prenda.getRutaImagen();

        return super.getView(position, convertView, parent);
    }

    public void actualizarFiltro(FiltroClasificaciones filtroClasificaciones) {
        List<Clasificable> resQuery = filtroClasificaciones.obtenerResultadosFiltrados(Prenda.class, Prenda.NOMBRE_TABLA, Prenda.class.getCanonicalName());
        _items.clear();
        _items.addAll(resQuery);
        Collections.sort(_items);
        notifyDataSetChanged();
    }
}
