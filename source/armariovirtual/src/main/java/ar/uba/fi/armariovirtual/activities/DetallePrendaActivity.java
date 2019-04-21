package ar.uba.fi.armariovirtual.activities;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ToggleButton;

import java.util.List;

import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.modelo.Clasificacion;
import ar.uba.fi.armariovirtual.modelo.adaptadores.AdaptadorClasificacion;
import ar.uba.fi.armariovirtual.presenters.DetallePrendaPresenter;
import ar.uba.fi.armariovirtual.utils.ImageUtils;
import ar.uba.fi.utilidadesdane.audio.AudioFondo;
import ar.uba.fi.utilidadesdane.imagenes.AdministrarFotosActivity;
import ar.uba.fi.utilidadesdane.imagenes.SeleccionarFotoActivity;
import ar.uba.fi.utilidadesdane.imagenes.TomarFotoActivity;

public class DetallePrendaActivity extends BarraBaseActivity {

    private DetallePrendaPresenter presenter;

    public static final String PARAMETRO_INTENT_ID_PRENDA = "id_prenda";

    private Long idPrenda = (long) -1; // ID = -1 -> Nueva prenda - ID >= 0 -> Editar prenda

    private ListView _clasificacionesListView = null;
    private AdaptadorClasificacion _adaptadorClasificacion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_prenda);

        idPrenda = getIntent().getLongExtra(PARAMETRO_INTENT_ID_PRENDA, -1);
        presenter = new DetallePrendaPresenter(this, idPrenda);

        // Inicializar elementos comunes a Nueva Prenda y Editar Prenda
        inicializarVistasComunes();

        // Inicializar elementos particulares de Nueva Prenda o Editar Prenda
        if (idPrenda > -1) {
            inicializarActivityParaEditarPrenda();
        } else {
            inicializarActivityParaNuevaPrenda();
        }

        setUpBarraConBotonDeAtras(false, false, new Intent(DetallePrendaActivity.this, ListadoPrendasActivity.class));

        try {
            AudioFondo.start(this, 4, false);
        } catch (Exception e) {
            AudioFondo.setSilencio(true);
        }
    }

    private void inicializarVistasComunes() {
        findViewById(R.id.volver_btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                volver();
            }
        });

        findViewById(R.id.seleccionar_imagen).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                seleccionarImagenGaleria();
            }
        });

        if (!this.getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA)) {
            findViewById(R.id.tomar_fotografia).setVisibility(View.GONE);
        } else {
            findViewById(R.id.tomar_fotografia).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    tomarFotografia();
                }
            });
        }

        findViewById(R.id.guardar_cambios).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                actualizarPrenda();
            }
        });


        // Inicializar Clasificaciones
        _clasificacionesListView = findViewById(R.id.lista_clasificaciones);

        presenter.obtenerOCrearClasificaciones(null);

    }

    public void inicializarClasificaciones(List<Clasificacion> clasificaciones) {
        if (_adaptadorClasificacion == null) {
            _adaptadorClasificacion = new AdaptadorClasificacion(this, clasificaciones);
            _clasificacionesListView.setAdapter(_adaptadorClasificacion);
        } else {
            _adaptadorClasificacion.inicializarClasificaciones(clasificaciones);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.obtenerOCrearClasificaciones(_adaptadorClasificacion.getClasificaciones());
    }

    private void volver() {
        presenter.descartarCambiosEnImagen();
        finish();
    }

    private void inicializarActivityParaEditarPrenda() {
        presenter.cargarDatosEnVista();
    }

    public void cargarDatosPrenda(String nombre, boolean favorito, String rutaImagen) {
        ((EditText) findViewById(R.id.nombre)).setText(nombre);
        ((ToggleButton) findViewById(R.id.btn_favorito)).setChecked(favorito);

        Uri uri = Uri.parse(rutaImagen);
        setImagen(uri);
    }

    private void inicializarActivityParaNuevaPrenda() {
        ((TextView) findViewById(R.id.titulo_detalle_prenda)).setText(getResources().getString(R.string.titulo_detalle_prenda_nueva));
        ((Button) findViewById(R.id.guardar_cambios)).setText(getResources().getString(R.string.boton_guardar_nuevo));
    }

    private void tomarFotografia() {
        Intent i = new Intent(this, TomarFotoActivity.class);
        i.putExtra(AdministrarFotosActivity.PARAM_SUBCARPETA, "ImagenesTomadas");
        startActivityForResult(i, 0);
    }

    private void seleccionarImagenGaleria() {
        Intent i = new Intent(this, SeleccionarFotoActivity.class);
        i.putExtra(AdministrarFotosActivity.PARAM_SUBCARPETA, "ImagenesGaleria");
        startActivityForResult(i, 0);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK && data != null) {
            Uri uri = Uri.parse(data.getExtras().getString(AdministrarFotosActivity.PARAM_URI_RESULTADO));
            setImagen(uri);
        }
    }

    private void setImagen(Uri contentUri) {
        ImageView vistaImagen = findViewById(R.id.imagen);

        ImageUtils.ajustarImagenEnRoundedImageView(this, vistaImagen, contentUri);

        presenter.setUriImagen(contentUri);
    }

    private void actualizarPrenda() {

        EditText nombreTxt = findViewById(R.id.nombre);
        String nombre = nombreTxt.getText().toString();

        ToggleButton btnFavorito = findViewById(R.id.btn_favorito);

        boolean favorito = btnFavorito.isChecked();

        presenter.guardarCambiosPrenda(nombre, favorito, _adaptadorClasificacion.getClasificaciones());
    }

    public void salir() {
        finish();
    }

    public void actualizarYGuardarClasificaciones() {
        _adaptadorClasificacion.actualizarYGuardarClasificaciones();
    }

}
