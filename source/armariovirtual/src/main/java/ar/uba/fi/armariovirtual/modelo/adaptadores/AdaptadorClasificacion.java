package ar.uba.fi.armariovirtual.modelo.adaptadores;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.modelo.Clasificacion;
import ar.uba.fi.armariovirtual.modelo.OpcionClasificacion;
import ar.uba.fi.armariovirtual.utils.MultiSelectSpinner;

public class AdaptadorClasificacion extends ArrayAdapter<Clasificacion>
{
    private Context _context;
//    private List<Clasificacion>         _clasificaciones            = new ArrayList<Clasificacion>();
    private List<Clasificacion>         _clasificaciones;
    private Map<Clasificacion,List<String>> _clasificacionesElegidas    = new HashMap<>();

    /**
     * Constructor
     * @param context Contexto donde se utiliza la lista de clasificaciones
     * @param clasificaciones Lista de clasificaciones que tiene que mostrar la vista actual
     */
    public AdaptadorClasificacion(Context context, List<Clasificacion> clasificaciones) {
        super(context, 0 , clasificaciones);
        _context = context;
//        _clasificaciones.addAll(clasificaciones);
        _clasificaciones = clasificaciones;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemClasificacion = convertView;
        if(itemClasificacion == null)
            itemClasificacion = LayoutInflater.from(_context).inflate(R.layout.listado_clasificaciones_item, parent, false);

        final Clasificacion clasificacion = _clasificaciones.get(position);

        TextView nombre = itemClasificacion.findViewById(R.id.clasificacion_nombre);
        nombre.setText(clasificacion.getDefinicionClasificacion().getNombreAMostrar());

        // Multi spinner
        final ArrayList<String> opcionesClasificacionDefinicionesStr = new ArrayList<>();
        for(OpcionClasificacion opcionClasificacionDefinida : clasificacion.getDefinicionClasificacion().obtenerOpciones()) {
            opcionesClasificacionDefinicionesStr.add(opcionClasificacionDefinida.getValor());
        }
        final MultiSelectSpinner spinnerClasif = itemClasificacion.findViewById(R.id.clasificacion_valores);
        spinnerClasif.setItems(opcionesClasificacionDefinicionesStr);
        spinnerClasif.setTextoVacio(_context.getString(R.string.texto_clasificacion_vacia));


        if(_clasificacionesElegidas.containsKey(clasificacion))
        {
            // Tomo los valores de los temporales
            spinnerClasif.setSelection(_clasificacionesElegidas.get(clasificacion));
        }
        else
        {
            // Tomo los valores de la BD
            ArrayList<String> opcionesClasificacionElegidasStr = new ArrayList<>();
            for(OpcionClasificacion opcionClasificacionSeleccionada : clasificacion.obtenerOpcionesElegidas()) {
                opcionesClasificacionElegidasStr.add(opcionClasificacionSeleccionada.getValor());
            }
            spinnerClasif.setSelection(opcionesClasificacionElegidasStr);
        }

        spinnerClasif.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                Log.d("msg", "onItemSelected: " + opcionesClasificacionDefinicionesStr.get(position) + " -> " + spinnerClasif.getSelectedIndicies().contains(position));
                _clasificacionesElegidas.put(clasificacion, spinnerClasif.getSelectedStrings());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
                Log.d("msg", "onNothingSelected");
            }

        });

        _clasificacionesElegidas.put(clasificacion, spinnerClasif.getSelectedStrings());
        return itemClasificacion;
    }

    public List<Clasificacion> getClasificaciones() {
        return _clasificaciones;
    }

    public void inicializarClasificaciones(List<Clasificacion> clasificaciones) {
        _clasificaciones = clasificaciones;

        // Inicializo las clasificaciones elegidas desde la BD
        for(int i = 0; i < _clasificaciones.size(); i++)
        {
            Clasificacion clasificacion    = _clasificaciones.get(i);
            ArrayList<String> opcionesClasificacionElegidasStr = new ArrayList<>();
            for(OpcionClasificacion opcionClasificacionSeleccionada : clasificacion.obtenerOpcionesElegidas()) {
                opcionesClasificacionElegidasStr.add(opcionClasificacionSeleccionada.getValor());
            }
            _clasificacionesElegidas.put(clasificacion, opcionesClasificacionElegidasStr);
        }


        notifyDataSetChanged();
    }

    public void actualizarYGuardarClasificaciones()
    {
        for(int i = 0; i < _clasificaciones.size(); i++)
        {
            Clasificacion clasificacion    = _clasificaciones.get(i);
            clasificacion.quitarTodasLasOpcionesElegidas();
            List<String> clasificacionesElegidas = _clasificacionesElegidas.get(clasificacion);
            for(OpcionClasificacion opcionClasificacion : clasificacion.getDefinicionClasificacion().obtenerOpciones())
            {
                if(clasificacionesElegidas.contains(opcionClasificacion.getValor()))
                {
                    clasificacion.agregarOpcionElegida(opcionClasificacion);
                }
            }
        }
    }
}
