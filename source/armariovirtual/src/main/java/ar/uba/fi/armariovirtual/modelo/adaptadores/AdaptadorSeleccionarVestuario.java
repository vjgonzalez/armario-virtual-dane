package ar.uba.fi.armariovirtual.modelo.adaptadores;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.modelo.Clasificable;
import ar.uba.fi.armariovirtual.utils.IReceptorData;
import ar.uba.fi.armariovirtual.utils.ImageUtils;
import ar.uba.fi.utilidadesdane.persistencia.ObjetoPersistente;

public abstract class AdaptadorSeleccionarVestuario extends ArrayAdapter<Clasificable> implements AdaptadorFiltrable {


    protected Activity _context;
    protected List _items;
    protected String nombreStr;
    protected String rutaImagen;
    protected IReceptorData<Long> _receptorId;

    public AdaptadorSeleccionarVestuario(Activity context, List items, IReceptorData<Long> receptorId) {
        super(context, 0, items);
        _context = context;
        _items = items;
        _receptorId = receptorId;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View item = convertView;
        if (item == null)
            item = LayoutInflater.from(_context).inflate(R.layout.seleccionar_vestuario_item, parent, false);

        TextView nombre = item.findViewById(R.id.nombre);
        nombre.setText(nombreStr);

        ImageView vistaImagen = item.findViewById(R.id.imagen);

        if (rutaImagen != null && !rutaImagen.isEmpty()) {
            ImageUtils.ajustarImagenEnRoundedImageView(_context, vistaImagen, rutaImagen);
        }

        Button seleccionarBtn = item.findViewById(R.id.seleccionar_btn);
        seleccionarBtn.setTag(position);

        seleccionarBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int posicionASeleccionar = (int) v.getTag();
                seleccionarItem(posicionASeleccionar);
            }
        });

        return item;
    }

    protected void seleccionarItem(int posicionAEditar) {
        ObjetoPersistente objetoAEditar = (ObjetoPersistente) _items.get(posicionAEditar);
        Long id = objetoAEditar.getId();
        _receptorId.recibirData(id);
    }
}
