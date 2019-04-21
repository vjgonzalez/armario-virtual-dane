package ar.uba.fi.armariovirtual.modelo.adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.List;

import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.configuracion.FuncionalidadProtegible;

public class AdaptadorFuncionalidadProtegible extends ArrayAdapter<FuncionalidadProtegible> {
    private Context context;
    private List<FuncionalidadProtegible> funcionalidadesProtegibles;

    public AdaptadorFuncionalidadProtegible(Context context, List<FuncionalidadProtegible> funcionalidadesProtegibles) {
        super(context, R.layout.listado_proteccion_item, funcionalidadesProtegibles);
        this.context = context;
        this.funcionalidadesProtegibles = funcionalidadesProtegibles;
    }

    @Override
    public int getCount() {
        return funcionalidadesProtegibles.size();
    }


    @Override
    public FuncionalidadProtegible getItem(int position) {
        return funcionalidadesProtegibles.get(position);
    }

    static class ViewHolder {
        TextView nombreFuncionalidad;
        CheckBox checkBoxProtegida;
        FuncionalidadProtegible item;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        final AdaptadorFuncionalidadProtegible.ViewHolder holder;
        if (convertView == null) {
            holder = new AdaptadorFuncionalidadProtegible.ViewHolder();
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.listado_proteccion_item, null);

            holder.item = funcionalidadesProtegibles.get(position);
            holder.nombreFuncionalidad = convertView.findViewById(R.id.nombre_funcionalidad);
            holder.checkBoxProtegida = convertView.findViewById(R.id.protegida);

            holder.checkBoxProtegida.setOnClickListener(new View.OnClickListener() {
                public void onClick(View arg0) {
                    holder.item.setProtegida(holder.checkBoxProtegida.isChecked());
                }
            });
            convertView.setTag(holder);
        } else {
            holder = (AdaptadorFuncionalidadProtegible.ViewHolder) convertView.getTag();
            holder.item = funcionalidadesProtegibles.get(position);
        }
        holder.nombreFuncionalidad.setText(holder.item.getNombre());
        holder.checkBoxProtegida.setChecked(holder.item.estaProtegida());
        holder.checkBoxProtegida.setEnabled(holder.item.esModificable());

        return convertView;
    }

    public List<FuncionalidadProtegible> getList() {
        return funcionalidadesProtegibles;
    }

}
