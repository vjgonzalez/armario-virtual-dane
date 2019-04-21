package ar.uba.fi.armariovirtual.modelo.adaptadores;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.List;

import app.InicializacionBD;
import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.configuracion.FuncionalidadProtegible;
import ar.uba.fi.armariovirtual.modelo.Clasificable;
import ar.uba.fi.armariovirtual.utils.ImageUtils;
import ar.uba.fi.utilidadesdane.autenticacion.Autenticacion;
import ar.uba.fi.utilidadesdane.autenticacion.ComandoAdministrador;

public abstract class AdaptadorVestuario extends ArrayAdapter<Clasificable> implements AdaptadorFiltrable {

    protected Activity _context;
    protected List _items;
    protected String nombreStr;
    protected String rutaImagen;
    protected String funcionalidadProtegida = "";

    public AdaptadorVestuario(Activity context, List items) {
        super(context, 0, items);
        _context = context;
        _items = items;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View item = convertView;
        if (item == null)
            item = LayoutInflater.from(_context).inflate(R.layout.listado_vestuario_item, parent, false);

        TextView nombre = item.findViewById(R.id.nombre);
        nombre.setText(nombreStr);
        ImageView imagenConjunto = item.findViewById(R.id.imagen);

        mostrarImagen(imagenConjunto);

        ImageView editarBtn = item.findViewById(R.id.editar_btn);
        editarBtn.setTag(position);

        editarBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                int posicionAEditar = (int) v.getTag();
                editarItem(posicionAEditar);
            }
        });

        ImageView eliminarBtn = item.findViewById(R.id.eliminar_btn);
        eliminarBtn.setTag(position);

        eliminarBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                final View view = v;
                new AlertDialog.Builder(_context)
                        .setTitle(_context.getResources().getString(R.string.dialogo_borrar_conjunto_titulo))
                        .setMessage(_context.getResources().getString(R.string.dialogo_borrar_conjunto_texto))
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int whichButton) {
                                int posicionAEliminar = (int) view.getTag();
                                eliminarItem(posicionAEliminar);
                            }
                        })
                        .setNegativeButton(android.R.string.no, null).show();
            }
        });
        return item;
    }

    protected void eliminarItem(final int posicionAEliminar) {
        if (FuncionalidadProtegible.estaProtegida(funcionalidadProtegida)) {
            ComandoAdministrador ejecutarSiAdmin = new ComandoAdministrador() {
                @Override
                public void ejecutar() {
                    eliminar(posicionAEliminar);
                }
            };
            Autenticacion.instancia().autenticarYEjecutar(ejecutarSiAdmin, _context.getFragmentManager());
        }
        else {
            eliminar(posicionAEliminar);
        }
    }

    private void eliminar(final int posicionAEliminar) {
        Clasificable itemActual = (Clasificable) _items.get(posicionAEliminar);
        itemActual.delete();
        _items.remove(posicionAEliminar);
        notifyDataSetChanged();
    }

    protected abstract void editarItem(int posicionAEditar);
    protected abstract void mostrarImagen(ImageView vistaImagen);

}
