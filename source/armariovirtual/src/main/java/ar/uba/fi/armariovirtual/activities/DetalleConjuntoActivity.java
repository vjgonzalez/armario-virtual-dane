package ar.uba.fi.armariovirtual.activities;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.modelo.Clasificacion;
import ar.uba.fi.armariovirtual.modelo.Prenda;
import ar.uba.fi.armariovirtual.modelo.adaptadores.AdaptadorClasificacion;
import ar.uba.fi.armariovirtual.modelo.adaptadores.AdaptadorPrendaConjunto;
import ar.uba.fi.armariovirtual.presenters.DetalleConjuntoPresenter;
import ar.uba.fi.armariovirtual.utils.IReceptorData;
import ar.uba.fi.armariovirtual.utils.ParAccionId;
import ar.uba.fi.utilidadesdane.audio.AudioFondo;

public class DetalleConjuntoActivity extends BarraBaseActivity implements IReceptorData<ParAccionId> {

    static final int SELECCIONAR_PRENDA_REQUEST = 1;

    public static final String PARAMETRO_INTENT_ID_CONJUNTO = "id_conjunto";

    private Long idConjunto = (long) -1; // ID = -1 -> Nuevo con junto - ID >= 0 -> Editar conjunto

    private ListView _clasificacionesListView = null;
    private AdaptadorClasificacion _adaptadorClasificacion;

    private GridView _prendasGridView;
    private AdaptadorPrendaConjunto _adaptadorPrenda;

    private PopupWindow popupWindow;

    private DetalleConjuntoPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_conjunto);

        idConjunto = getIntent().getLongExtra(PARAMETRO_INTENT_ID_CONJUNTO, -1);
        presenter = new DetalleConjuntoPresenter(this, idConjunto);

        // Inicializar elementos particulares de Nuevo Conjunto o Editar Conjunto
        if (idConjunto > -1) {
            inicializarActivityParaEditarConjunto();
        } else {
            inicializarActivityParaNuevoConjunto();
        }

        // Inicializar elementos comunes a Nuevo Conjunto y Editar Conjunto
        inicializarVistasComunes();

        setUpBarraConBotonDeAtras(false, false, new Intent(DetalleConjuntoActivity.this, ListadoConjuntosActivity.class));

        try {
            AudioFondo.start(this, 5, false);
        } catch (Exception e) {
            AudioFondo.setSilencio(true);
        }
    }

    private void inicializarVistasComunes(){
        _prendasGridView = findViewById(R.id.grilla_vestuario);

        findViewById(R.id.agregar_prenda_btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(DetalleConjuntoActivity.this, SeleccionarPrendaActivity.class);
                startActivityForResult(myIntent, SELECCIONAR_PRENDA_REQUEST);
            }
        });

        findViewById(R.id.volver_btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                salir();
            }
        });

        findViewById(R.id.clasificaciones_btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                onButtonShowPopupWindowClick(_prendasGridView);
            }
        });


        findViewById(R.id.guardar_cambios).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                actualizarConjunto();
            }
        });

        // layout de popup
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.popup_clasificaciones, null);

        // crear ventana popup
        int width = LinearLayout.LayoutParams.MATCH_PARENT;
        int height = LinearLayout.LayoutParams.MATCH_PARENT;
        boolean focusable = true; // permite que tapear fuera del popup también lo cierre
        popupWindow = new PopupWindow(popupView, width, height, focusable);

        if (Build.VERSION.SDK_INT >= 21) {
            popupWindow.setElevation(5.0f);
        }

        // Cierre de popup
        ImageButton cerrarBtn = popupView.findViewById(R.id.btn_cerrar);
        cerrarBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                popupWindow.dismiss();
            }
        });

        // Inicializar Clasificaciones
        _clasificacionesListView = popupView.findViewById(R.id.lista_clasificaciones);

        presenter.obtenerOCrearClasificaciones(null);
        presenter.obtenerOCrearPrendas(null);
    }

    public void inicializarClasificaciones(List<Clasificacion> clasificaciones) {
        if (_adaptadorClasificacion == null) {
            _adaptadorClasificacion = new AdaptadorClasificacion(this, clasificaciones);
            _clasificacionesListView.setAdapter(_adaptadorClasificacion);
        } else {
            _adaptadorClasificacion.inicializarClasificaciones(clasificaciones);
        }
    }

    public void inicializarPrendas(List<Prenda> prendas) {
        if (_adaptadorPrenda == null) {
            _adaptadorPrenda = new AdaptadorPrendaConjunto(this, prendas, this);
            _prendasGridView.setAdapter(_adaptadorPrenda);
        } else {
            _adaptadorPrenda.actualizarPrendas(prendas);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        presenter.obtenerOCrearClasificaciones(_adaptadorClasificacion.getClasificaciones());
        presenter.obtenerOCrearPrendas(_adaptadorPrenda.getPrendas());
    }

    public void salir() {
        finish();
    }

    private void inicializarActivityParaEditarConjunto() {
        presenter.cargarDatosEnVista();
    }

    public void cargarDatosConjunto(String nombre, boolean favorito) {
        ((EditText) findViewById(R.id.nombre_conjunto)).setText(nombre);
        ((ToggleButton) findViewById(R.id.btn_favorito)).setChecked(favorito);
    }

    private void inicializarActivityParaNuevoConjunto() {
        ((TextView) findViewById(R.id.titulo_detalle_conjunto)).setText(getResources().getString(R.string.titulo_detalle_conjunto_nuevo));
        ((Button)findViewById(R.id.guardar_cambios)).setText(getResources().getString(R.string.boton_guardar_nuevo));
    }

    private void actualizarConjunto() {
        String nombre = ((EditText) findViewById(R.id.nombre_conjunto)).getText().toString();

        ToggleButton btnFavorito = findViewById(R.id.btn_favorito);
        boolean favorito = btnFavorito.isChecked();

        presenter.guardarCambiosConjunto(nombre,favorito, _adaptadorClasificacion.getClasificaciones(), _adaptadorPrenda.getPrendas());
    }

    public void actualizarYGuardarClasificaciones() {
        _adaptadorClasificacion.actualizarYGuardarClasificaciones();
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == SELECCIONAR_PRENDA_REQUEST) {
            if (resultCode == RESULT_OK && data != null) {
                Long idPrenda = data.getExtras().getLong(SeleccionarPrendaActivity.PARAMETRO_INTENT_ID_VESTUARIO_RESULTADO);
                presenter.agregarPrenda(idPrenda, _adaptadorPrenda.getPrendas());
            }
        }
    }

    @Override
    public void recibirData(ParAccionId data) {
        if (data.codigoAccion == AdaptadorPrendaConjunto.CODIGO_ACCION_QUITAR) {
            presenter.quitarPrenda(data.id, _adaptadorPrenda.getPrendas());
        }
    }

    public void onButtonShowPopupWindowClick(View view) {

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);
    }

}
