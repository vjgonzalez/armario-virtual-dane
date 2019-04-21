package ar.uba.fi.armariovirtual.modelo.adaptadores;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;

import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.modelo.OpcionClasificacion;

public class AdaptadorOpcionClasificacion extends ArrayAdapter<OpcionClasificacion> {

    private List<OpcionClasificacion> opciones;
    private List<OpcionClasificacion> opcionesAEliminar;
    private Context context;
    private boolean hayModificacionesSinGuardar;

    public AdaptadorOpcionClasificacion(Context context, List<OpcionClasificacion> opciones) {
        super(context, R.layout.listado_clasificaciones_editar_item, opciones);
        this.opciones = opciones;
        this.context = context;
        opcionesAEliminar = new ArrayList<>();
        hayModificacionesSinGuardar = false;
    }

    @Override
    public int getCount() {
        return opciones.size();
    }


    @Override
    public OpcionClasificacion getItem(int position) {
        return opciones.get(position);
    }

    static class ViewHolder {
        EditText opcion;
        OpcionClasificacion item;
        ImageButton btnEliminar;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listado_clasificaciones_editar_item, null);

            holder.item = opciones.get(position);
            holder.opcion = convertView.findViewById(R.id.opcion);
            holder.btnEliminar = convertView.findViewById(R.id.eliminar_btn);
            holder.btnEliminar.setTag(position);

            holder.opcion.addTextChangedListener(new TextWatcher() {
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    OpcionClasificacion opcion = holder.item;
                    if (opcion.getValor() == null || !opcion.getValor().equals(s.toString()))
                        hayModificacionesSinGuardar = true;
                    opcion.setValor(s.toString());
                }

                public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                }

                public void afterTextChanged(Editable s) {
                }
            });

            holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    new AlertDialog.Builder(context)
                            .setTitle(context.getResources().getString(R.string.dialogo_borrar_opcion_clasificacion_titulo))
                            .setMessage(context.getResources().getString(R.string.dialogo_borrar_opcion_clasificacion_texto))
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    OpcionClasificacion opcionAEliminar = holder.item;
                                    prepararOpcionParaEliminar(opcionAEliminar);
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
            holder.item = opciones.get(position);
        }
        holder.opcion.setText(holder.item.getValor());

        return convertView;
    }

    public List<OpcionClasificacion> getOpciones() {
        return opciones;
    }

    private void prepararOpcionParaEliminar(OpcionClasificacion opcionAEliminar) {
        opcionesAEliminar.add(opcionAEliminar);
        opciones.remove(opcionAEliminar);
        hayModificacionesSinGuardar = true;
        notifyDataSetChanged();
    }

    public boolean hayModificacionesSinGuardar() {
        return hayModificacionesSinGuardar;
    }

    private void eliminarOpcionesABorrar() {

        for (OpcionClasificacion actual : opcionesAEliminar) {
            if (actual.getId() != null)
                actual.delete();
        }

        opcionesAEliminar.clear();
    }

    public void guardarCambios(){
        eliminarOpcionesABorrar();
        hayModificacionesSinGuardar = false;
    }

    @Override
    public void add(OpcionClasificacion opcionClasificacion){
        super.add(opcionClasificacion);
        hayModificacionesSinGuardar = true;
    }

}