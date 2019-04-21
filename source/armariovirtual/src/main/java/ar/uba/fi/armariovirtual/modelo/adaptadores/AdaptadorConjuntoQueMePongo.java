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
import ar.uba.fi.armariovirtual.modelo.Conjunto;
import ar.uba.fi.armariovirtual.utils.ImageUtils;

public class AdaptadorConjuntoQueMePongo extends ArrayAdapter<Conjunto>
{
    private Context _context;
    private List<Conjunto> _conjuntos;

    /**
     * Constructor
     * @param context Contexto donde se utiliza la lista de conjuntos
     * @param conjuntos Lista de conjuntos que debe mostrar la vista actual
     */
    public AdaptadorConjuntoQueMePongo(Context context, List<Conjunto> conjuntos) {
        super(context, 0 , conjuntos);
        _context = context;
        _conjuntos = conjuntos;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemConjunto = convertView;
        if(itemConjunto == null)
            itemConjunto = LayoutInflater.from(_context).inflate(R.layout.listado_conjunto_qmp_item, parent, false);

        Conjunto conjunto = _conjuntos.get(position);

        TextView nombre = itemConjunto.findViewById(R.id.nombre);
        nombre.setText(conjunto.getNombre());

        ImageView imagenConjunto = itemConjunto.findViewById(R.id.imagen);
        String rutaImagen = conjunto.getRutaImagen();
        if (rutaImagen != null) {
            ImageUtils.ajustarImagenEnRoundedImageView(_context, imagenConjunto, rutaImagen);
        }
        else
        {
            imagenConjunto.setVisibility(View.INVISIBLE);
        }


        return itemConjunto;
    }
}
