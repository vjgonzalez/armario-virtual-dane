package ar.uba.fi.armariovirtual.modelo.adaptadores;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.List;

import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.modelo.OpcionClasificacion;
import ar.uba.fi.armariovirtual.utils.BotonSombra;
import ar.uba.fi.armariovirtual.utils.IReceptorData;

public class AdaptadorOpcionesQueMePongo extends BaseAdapter {

    private List<OpcionClasificacion> _opciones;
    private Context _context;
    private IReceptorData<Integer> _receptorPositionOpcion;

    public AdaptadorOpcionesQueMePongo(List<OpcionClasificacion> opciones, Context context, IReceptorData<Integer> receptorPositionOpcion)
    {
        this._opciones = opciones;
        this._context = context;
        this._receptorPositionOpcion = receptorPositionOpcion;
    }


    @Override
    public int getCount() {
        return _opciones.size();
    }

    @Override
    public Object getItem(int position) {
        return _opciones.get(position);
    }

    @Override
    public long getItemId(int position) {
        return _opciones.get(position).getId();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        View itemOpcion = convertView;
        if(itemOpcion == null)
            itemOpcion = LayoutInflater.from(_context).inflate(R.layout.listado_qmp_opciones_item, parent, false);

        final OpcionClasificacion opcion = _opciones.get(position);

        BotonSombra opcionBtn = itemOpcion.findViewById(R.id.opcion);
        opcionBtn.setText(opcion.getValor());


        opcionBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                _receptorPositionOpcion.recibirData(position);
            }
        });

        return itemOpcion;
    }
}