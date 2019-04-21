package ar.uba.fi.armariovirtual.modelo.adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.modelo.Prenda;
import ar.uba.fi.armariovirtual.utils.ImageUtils;

public class AdaptadorPrendaVistaConjunto extends ArrayAdapter<Prenda>
{
    private Context _context;
    private List<Prenda> _prendas;

    /**
     * Constructor
     * @param context Contexto donde se utiliza la lista de prendas
     * @param prendas Lista de prendas a mostras en la vista actual
     */
    public AdaptadorPrendaVistaConjunto(Context context, List<Prenda> prendas) {
        super(context, 0 , prendas);
        _context = context;
        _prendas = prendas;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemPrenda = convertView;
        if(itemPrenda == null)
            itemPrenda = LayoutInflater.from(_context).inflate(R.layout.vista_conjunto_listado_prendas_item, parent, false);

        Prenda prenda = _prendas.get(position);

        ImageView vistaImagen = itemPrenda.findViewById(R.id.imagen);
        if(!prenda.getRutaImagen().isEmpty())
        {
            ImageUtils.ajustarImagenEnRoundedImageView(_context, vistaImagen, prenda.getRutaImagen());
        }
        else
        {
            vistaImagen.setVisibility(View.INVISIBLE);
        }

        TextView nombre = itemPrenda.findViewById(R.id.nombre);
        nombre.setText(prenda.getNombre());


        return itemPrenda;
    }
}
