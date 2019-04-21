package ar.uba.fi.armariovirtual.activities;

import android.app.Activity;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;


import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.modelo.adaptadores.AdaptadorFiltro;
import ar.uba.fi.armariovirtual.modelo.adaptadores.AdaptadorSeleccionarVestuario;
import ar.uba.fi.armariovirtual.modelo.filtros.FiltroClasificaciones;
import ar.uba.fi.armariovirtual.modelo.filtros.FiltrosListView;
import ar.uba.fi.armariovirtual.utils.IReceptorData;

public abstract class SeleccionarVestuarioActivity extends Activity implements IReceptorData<Long> {

    public static final String PARAMETRO_INTENT_ID_VESTUARIO_RESULTADO = "idVestuarioResultado";

    protected GridView _vestuarioGridView;
    protected AdaptadorSeleccionarVestuario _adaptador;
    protected FiltroClasificaciones _filtroClasificaciones;
    protected PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.seleccionar_vestuario);

        _vestuarioGridView = findViewById(R.id.grilla_vestuario);

        inicializarAdaptador();

        _vestuarioGridView.setAdapter(_adaptador);

        TextView tituloTextView = findViewById(R.id.textView);
        String titulo = getTituloPantalla();
        if (titulo != null)
            tituloTextView.setText(titulo);

        findViewById(R.id.volver_btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                finish();
            }
        });

        findViewById(R.id.btn_filtros).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                onButtonShowPopupWindowClick(_vestuarioGridView);
            }
        });


        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_filtros, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        popupWindow = new PopupWindow(popupView,width, height, focusable);

        // Call requires API level 21
        if(Build.VERSION.SDK_INT>=21){
            popupWindow.setElevation(5.0f);
        }

        // Get a reference for the custom view close button
        ImageButton cerrarBtn = popupView.findViewById(R.id.btn_cerrar);

        // Set a click listener for the popup window close button
        cerrarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });


        _filtroClasificaciones = new FiltroClasificaciones();
        _adaptador.actualizarFiltro(_filtroClasificaciones);


        // Mostrar las clasificaciones de filtros en la barra lateral
        inicializarPanelFiltros(popupView, _filtroClasificaciones);
    }

    protected abstract void inicializarAdaptador();
    protected abstract String getTituloPantalla();

    private void inicializarPanelFiltros(View panelFiltros, FiltroClasificaciones filtroClasificaciones) {
        final AdaptadorFiltro adaptadorFiltro = new AdaptadorFiltro(this, _filtroClasificaciones.obtenerClasificaciones());
        ExpandableListView filtrosListView = panelFiltros.findViewById(R.id.lista_filtros_clasificaciones);
        filtrosListView.setAdapter(adaptadorFiltro);
        filtrosListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {

            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                _adaptador.actualizarFiltro(_filtroClasificaciones);
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        _adaptador.actualizarFiltro(_filtroClasificaciones);
    }

    @Override
    public void recibirData(Long data) {
        getIntent().putExtra(PARAMETRO_INTENT_ID_VESTUARIO_RESULTADO, data);
        setResult(RESULT_OK, getIntent());
        finish();
    }


    public void onButtonShowPopupWindowClick(View view) {
        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }
}
