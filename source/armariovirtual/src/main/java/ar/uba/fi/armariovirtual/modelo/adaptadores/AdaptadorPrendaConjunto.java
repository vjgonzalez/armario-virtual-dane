package ar.uba.fi.armariovirtual.modelo.adaptadores;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.modelo.Prenda;
import ar.uba.fi.armariovirtual.utils.ImageUtils;
import ar.uba.fi.armariovirtual.utils.IReceptorData;
import ar.uba.fi.armariovirtual.utils.ParAccionId;

public class AdaptadorPrendaConjunto extends ArrayAdapter<Prenda>
{
    public static final int CODIGO_ACCION_QUITAR = 1;

    private Context _context;
    private List<Prenda> _prendas;

    private IReceptorData<ParAccionId> _receptorIdPrendaConjunto;

    /**
     * Constructor
     * @param context Contexto donde se utiliza la lista de prendas
     * @param prendas Lista de prendas a mostras en la vista actual
     */
    public AdaptadorPrendaConjunto(Context context, List<Prenda> prendas, IReceptorData<ParAccionId> receptorIdPrendaConjunto) {
        super(context, 0 , prendas);
        _context = context;
        _prendas = prendas;
        _receptorIdPrendaConjunto = receptorIdPrendaConjunto;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemPrenda = convertView;
        if(itemPrenda == null)
            itemPrenda = LayoutInflater.from(_context).inflate(R.layout.listado_prendas_conjunto_item, parent, false);

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

        ImageButton quitarBtn = itemPrenda.findViewById(R.id.quitar_btn);
        quitarBtn.setTag(position);

        quitarBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v)
            {
                final View view = v;
                new AlertDialog.Builder(_context)
                        .setTitle(_context.getResources().getString(R.string.dialogo_borrar_prenda_de_conjunto_titulo))
                        .setMessage(_context.getResources().getString(R.string.dialogo_borrar_prenda_de_conjunto_texto))
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                int posicionAQuitar = (int)view.getTag();
                                quitarPrenda(posicionAQuitar);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });

        return itemPrenda;
    }

    private void quitarPrenda(int posicionAQuitar) {
        _receptorIdPrendaConjunto.recibirData(new ParAccionId(CODIGO_ACCION_QUITAR, _prendas.get(posicionAQuitar).getId()));
    }

    public void actualizarPrendas(List<Prenda> prendas) {
        _prendas.clear();
        _prendas.addAll(prendas);
        notifyDataSetChanged();
    }

    public List<Prenda> getPrendas() {
        return _prendas;
    }
}
