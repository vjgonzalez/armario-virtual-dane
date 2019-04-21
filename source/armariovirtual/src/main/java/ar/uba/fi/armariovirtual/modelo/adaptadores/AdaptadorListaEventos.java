package ar.uba.fi.armariovirtual.modelo.adaptadores;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.text.ParseException;
import java.util.List;

import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.activities.calendario.DetalleEventoActivity;
import ar.uba.fi.armariovirtual.modelo.EventoCalendario;
import ar.uba.fi.armariovirtual.utils.ImageUtils;

public class AdaptadorListaEventos extends BaseAdapter {
    private List<EventoCalendario> eventos;
    private Context context;

    public AdaptadorListaEventos(List<EventoCalendario> eventos, Context context)
    {
        this.eventos = eventos;
        this.context = context;
    }


    @Override
    public int getCount() {
        return eventos.size();
    }

    @Override
    public Object getItem(int position) {
        return eventos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return eventos.get(position).getId();
    }


    static class ViewHolder{
        ImageView   conjuntoEvento;
        TextView    nombreEvento;
        TextView    horaEvento;
        ImageButton btnEditar;
        ImageButton btnEliminar;
        EventoCalendario item;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listado_eventos_item, null);

            holder.item = eventos.get(position);
            holder.conjuntoEvento = convertView.findViewById(R.id.conjunto_evento);
            holder.nombreEvento = convertView.findViewById(R.id.nombre_evento);
            holder.horaEvento = convertView.findViewById(R.id.hora_evento);
            holder.btnEditar = convertView.findViewById(R.id.btn_editar);
            holder.btnEliminar = convertView.findViewById(R.id.btn_eliminar);

            holder.btnEditar.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Log.d("DANE","click en EDITAR de item " + position + " con nombre de evento \'" + holder.item.getNombreEvento() + "\'");
                    //AddEditItemActivity.launch(mContext, viewHolder.mItem, viewHolder.mItem.getmCategory());

                    Intent intent = new Intent(context, DetalleEventoActivity.class);
                    try {
                        intent.putExtra(DetalleEventoActivity.PARAMETRO_INTENT_FECHA, holder.item.getDate().getTime());
                    }
                    catch(ParseException e){
                        //TODO
                    }
                    Long idEvento = holder.item.getId();
                    intent.putExtra(DetalleEventoActivity.PARAMETRO_INTENT_ID_EVENTO, idEvento.toString());
                    context.startActivity(intent);
                }
            });

            holder.btnEliminar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Log.d("DANE","click en ELIMINAR de item " + position + " con nombre de evento \'" + holder.item.getNombreEvento() + "\'");
                    new AlertDialog.Builder(context)
                            .setTitle(context.getResources().getString(R.string.dialogo_borrar_evento_titulo))
                            .setMessage(context.getResources().getString(R.string.dialogo_borrar_evento_texto))
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    holder.item.delete();
                                    eventos.remove(holder.item);
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                }
            });

            convertView.setTag(holder);
        }
        else{
            holder = (ViewHolder) convertView.getTag();
            holder.item = eventos.get(position);
        }
        holder.nombreEvento.setText(holder.item.getNombreEvento());
        holder.horaEvento.setText(holder.item.getHoraSinSegundos());

        String rutaImagen = holder.item.getConjunto().getRutaImagen();
        ImageUtils.ajustarImagenEnImageView(context, holder.conjuntoEvento, rutaImagen);

        return convertView;
    }
}
