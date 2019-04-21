package ar.uba.fi.armariovirtual.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.modelo.adaptadores.AdaptadorFiltro;
import ar.uba.fi.armariovirtual.modelo.adaptadores.AdaptadorVestuario;
import ar.uba.fi.armariovirtual.modelo.filtros.FiltroClasificaciones;
import ar.uba.fi.armariovirtual.modelo.filtros.FiltrosListView;
import ar.uba.fi.utilidadesdane.audio.AudioFondo;

public abstract class ListadoVestuarioActivity extends BarraBaseActivity {


    protected GridView _gridView;
    protected AdaptadorVestuario _adaptadorVestuario;
    protected FiltroClasificaciones _filtroClasificaciones;
    protected Button conjuntosBtn;
    protected Button prendasBtn;
    protected PopupWindow popupWindow;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.vestuario);

        _gridView = findViewById(R.id.grilla_vestuario);

        inicializarAdaptador();
        _gridView.setAdapter(_adaptadorVestuario);

        conjuntosBtn = findViewById(R.id.conjuntos_btn);
        prendasBtn = findViewById(R.id.prendas_btn);

        inicializarBotones();

        findViewById(R.id.nuevo_item).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {

                Intent myIntent = new Intent(ListadoVestuarioActivity.this, DetallePrendaActivity.class);
                myIntent.putExtra(DetallePrendaActivity.PARAMETRO_INTENT_ID_PRENDA, Long.valueOf(-1));
                startActivity(myIntent);
            }
        });

        findViewById(R.id.btn_filtros).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                onButtonShowPopupWindowClick(_gridView);
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
        popupWindow = new PopupWindow(popupView, width, height, focusable);

        // Call requires API level 21
        if (Build.VERSION.SDK_INT >= 21) {
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
        _adaptadorVestuario.actualizarFiltro(_filtroClasificaciones);


        // Mostrar las clasificaciones de filtros en la barra lateral
        inicializarPanelFiltros(popupView);
        setUpBarraConBotonDeAtras(false, false, new Intent(ListadoVestuarioActivity.this, MenuPrincipalActivity.class));

        try {
            AudioFondo.start(this, 3, false);
        } catch (Exception e) {
            AudioFondo.setSilencio(true);
        }
    }

    private void inicializarPanelFiltros(View panelFiltros) {
        final AdaptadorFiltro adaptadorFiltro = new AdaptadorFiltro(this, _filtroClasificaciones.obtenerClasificaciones());
        ExpandableListView filtrosListView = panelFiltros.findViewById(R.id.lista_filtros_clasificaciones);
        filtrosListView.setAdapter(adaptadorFiltro);
        filtrosListView.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
            @Override
            public boolean onChildClick(ExpandableListView parent, View v,
                                        int groupPosition, int childPosition, long id) {
                _adaptadorVestuario.actualizarFiltro(_filtroClasificaciones);
                return false;
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        _adaptadorVestuario.actualizarFiltro(_filtroClasificaciones);
    }


    public void onButtonShowPopupWindowClick(View view) {

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

    protected abstract void inicializarAdaptador();

    protected abstract void inicializarBotones();
}
