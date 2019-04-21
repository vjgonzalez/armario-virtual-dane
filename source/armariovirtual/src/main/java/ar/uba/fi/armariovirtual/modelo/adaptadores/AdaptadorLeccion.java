package ar.uba.fi.armariovirtual.modelo.adaptadores;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.XmlResourceParser;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Vector;

import app.InicializacionBD;
import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.modelo.Leccion;
import ar.uba.fi.armariovirtual.utils.ImageUtils;
import ar.uba.fi.utilidadesdane.cuestionario.Cuestionario;

public class AdaptadorLeccion extends BaseAdapter
{
    private Context _context;
    private List<Leccion> _lecciones;

    /**
     * Constructor
     * @param context Contexto donde se utiliza la lista de lecciones
     */
    public AdaptadorLeccion(Context context) {
        super();
        _context = context;

        _lecciones = CrearLecciones();

//        _lecciones = new ArrayList<Leccion>();
//        _lecciones.add(new Leccion("LECCIÓN 1", "file:///android_asset/iconos_lecciones/icono_leccion_por_defecto.png", "ME VISTO EN ORDEN"));
//        _lecciones.add(new Leccion("LECCIÓN 2", "file:///android_asset/iconos_lecciones/icono_leccion_por_defecto.png", "COMBINO COLORES"));
//        _lecciones.add(new Leccion("LECCIÓN 3", "file:///android_asset/iconos_lecciones/icono_leccion_por_defecto.png", "PREPARO LA ROPA"));
//        _lecciones.add(new Leccion("LECCIÓN 4", "file:///android_asset/iconos_lecciones/icono_leccion_por_defecto.png", "ME VISTO PARA LA OCASIÓN"));
    }

    private List<Leccion> CrearLecciones() {

        ArrayList<Leccion> lecciones = new ArrayList<>();

        XmlResourceParser xrp = _context.getResources().getXml(R.xml.aprendo_a_vestirme_lecciones);
        try {
            int eventType = xrp.getEventType();
            Leccion leccion = null;
            while (eventType != XmlResourceParser.END_DOCUMENT)
            {
                switch (eventType)
                {
                    case XmlResourceParser.START_DOCUMENT:
                        // Log.d("DANE"," CUESTION - Start document");
                        break;
                    case XmlResourceParser.START_TAG:
                        // Log.d("DANE"," CUESTION - Start tag " + xrp.getName());
                        switch (xrp.getName())
                        {
                            case "leccion":
                                Log.d("DANE"," LECCIONES - Leyendo nueva leccion:: " + xrp.getAttributeValue(0));
                                String  nombre = xrp.getAttributeValue(null, "nombre");
                                String  descripcion = xrp.getAttributeValue(null, "descripcion");
                                String  icono = "file:///android_asset/" + xrp.getAttributeValue(null, "icono");
                                String  nombreCuestionario = xrp.getAttributeValue(null, "cuestionario");
                                int     idCuestionario = _context.getResources().getIdentifier(nombreCuestionario,"xml", _context.getPackageName());
                                leccion = new Leccion(nombre, descripcion, icono, idCuestionario);
                                break;
                            case "diapositiva":
                                leccion.agregarDiapositiva("file:///android_asset/" + xrp.getAttributeValue(null, "rutaImagen"));
                                break;
                        }
                        break;
                    case XmlResourceParser.END_TAG:
                        if(xrp.getName().equals("leccion"))
                        {
                            lecciones.add(leccion);
                        }
                        break;
                    case XmlResourceParser.TEXT:
                        break;
                }
                eventType = xrp.next();
            }
        }
        catch (Exception e)
        {
            Log.d("DANE"," LECCIONES - Error: " + e.getMessage());
        }

        return lecciones;
    }

    @Override
    public int getCount() {
        return _lecciones.size();
    }

    @Override
    public Object getItem(int position) {
        return _lecciones.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View itemLeccion = convertView;
        if(itemLeccion == null)
            itemLeccion = LayoutInflater.from(_context).inflate(R.layout.aav_lecciones_item, parent, false);

        Leccion leccion = _lecciones.get(position);

        TextView nombre = itemLeccion.findViewById(R.id.nombre);
        nombre.setText(leccion.getNombre());

        TextView descripcion = itemLeccion.findViewById(R.id.descripcion);
        descripcion.setText(leccion.getDescripcion());

        ImageView imagenLeccion = itemLeccion.findViewById(R.id.imagen);
        String rutaImagen = leccion.getRutaImagen();
        if (rutaImagen != null) {
            ImageUtils.ajustarImagenEnRoundedImageView(_context, imagenLeccion, rutaImagen);
        }
        else
        {
            imagenLeccion.setVisibility(View.INVISIBLE);
        }
        return itemLeccion;
    }
}
