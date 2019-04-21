
package ar.uba.fi.armariovirtual.activities.calendario;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.activities.BarraBaseActivity;
import ar.uba.fi.armariovirtual.activities.SeleccionarConjuntoActivity;
import ar.uba.fi.armariovirtual.modelo.EventoCalendario;
import ar.uba.fi.armariovirtual.presenters.DetalleEventoPresenter;
import ar.uba.fi.armariovirtual.utils.ImageUtils;
import ar.uba.fi.armariovirtual.utils.ValidacionInput;
import ar.uba.fi.utilidadesdane.audio.AudioFondo;

public class DetalleEventoActivity extends BarraBaseActivity {

    public static final String PARAMETRO_INTENT_ID_EVENTO = "idEvento";
    public static final String PARAMETRO_INTENT_FECHA = "fecha";
    public static final String VALOR_INTENT_ID_EVENTO_NULO = "-1";

    public static final int SELECCIONAR_CONJUNTO_REQUEST = 1;

    protected Spinner spinner;
    protected ArrayAdapter<CharSequence> adaptador;
    protected CharSequence horaElegida;
    protected Button botonGuardar;
    protected Button botonConjunto;
    protected ImageButton botonNotificacion;
    protected ImageButton botonEliminarNotificacion;
    protected int indiceTiempoNotificacion = -1;
    protected TextView txtNotificacion;

    protected Calendar calendarDatePicker;
    protected EditText editTextFecha;
    protected EditText nombreEventoEditText;
    protected DatePickerDialog.OnDateSetListener listenerDatePicker;

    protected Context context;
    private Long idConjuntoSeleccionado = (long) -1;

    protected long fechaLong;

    private DetalleEventoPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detalle_evento);
        context = this;

        fechaLong = getIntent().getLongExtra(PARAMETRO_INTENT_FECHA, 0);
        String idEvento = getIntent().getStringExtra(PARAMETRO_INTENT_ID_EVENTO);

        presenter = new DetalleEventoPresenter(this, fechaLong, idEvento);

        spinner = findViewById(R.id.hora_spinner);
        nombreEventoEditText = findViewById(R.id.nombre_evento);

        adaptador = ArrayAdapter.createFromResource(this, R.array.array_horas, android.R.layout.simple_spinner_item);
        adaptador.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adaptador);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int position, long id) {
                horaElegida = adaptador.getItem(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapter) {
            }
        });

        botonConjunto = findViewById(R.id.boton_conjunto);
        botonConjunto.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                seleccionarConjunto();
            }
        });

        txtNotificacion = findViewById(R.id.txt_notificacion);

        botonEliminarNotificacion = findViewById(R.id.btn_eliminar_notificacion);
        botonEliminarNotificacion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                indiceTiempoNotificacion = -1;
                txtNotificacion.setText(getResources().getString(R.string.evento_sin_notificacion));
                botonEliminarNotificacion.setVisibility(View.GONE);
            }
        });

        botonNotificacion = findViewById(R.id.btn_notificacion);
        botonNotificacion.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                new AlertDialog.Builder(context)
                        .setTitle(context.getResources().getString(R.string.dialogo_notificacion_titulo))
                        .setItems(EventoCalendario.opcionesTiempoNotificacion, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int indiceElementoSeleccionado) {
                                configurarNotificacion(indiceElementoSeleccionado);
                            }
                        }).show();
            }
        });

        botonGuardar = findViewById(R.id.boton_guardar);

        botonGuardar.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (guardarCambiosEvento())
                    finish();
            }
        });

        findViewById(R.id.boton_cancelar).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                finish();
            }
        });

        setUpDatePicker();
        actualizarDatePicker();

        presenter.setUpEvento();

        setUpBarraConBotonDeAtras(false, false, null);

        try {
            AudioFondo.start(this, 9, false);
        } catch (Exception e) {
            AudioFondo.setSilencio(true);
        }
    }


    public void inicializarActivityParaNuevoEvento() {
        botonGuardar.setText(getResources().getString(R.string.boton_guardar_nuevo));
        ((TextView) findViewById(R.id.titulo_detalle_evento)).setText(getResources().getString(R.string.titulo_detalle_evento_nuevo));
        botonConjunto.setText(getResources().getString(R.string.boton_seleccionar_conjunto));

    }

    private void configurarNotificacion(int indiceElementoSeleccionado) {
        indiceTiempoNotificacion = indiceElementoSeleccionado;
        if (indiceElementoSeleccionado != EventoCalendario.INDICE_SIN_NOTIFICACION) {
            botonEliminarNotificacion.setVisibility(View.VISIBLE);
            txtNotificacion.setText(EventoCalendario.opcionesTiempoNotificacion[indiceElementoSeleccionado]);
        }
    }

    public void inicializarActivityParaEditarEvento(String nombreEvento, String horaSinSegundos, int indiceNotificacion, long fechaLong) {

        this.fechaLong = fechaLong;
        nombreEventoEditText.setText(nombreEvento);
        spinner.setSelection(((ArrayAdapter) spinner.getAdapter()).getPosition(horaSinSegundos));
        configurarNotificacion(indiceNotificacion);

    }

    public void setConjunto(String rutaImagen, Long idConjuntoSeleccionado) {
        if (rutaImagen == null)
            //TODO set imagen default
            return;

        ImageView imagenImageView = findViewById(R.id.imagen_conjunto);
        ImageUtils.ajustarImagenEnRoundedImageView(this, imagenImageView, rutaImagen);
        this.idConjuntoSeleccionado = idConjuntoSeleccionado;
    }

    private void seleccionarConjunto() {
        Intent myIntent = new Intent(DetalleEventoActivity.this, SeleccionarConjuntoActivity.class);
        startActivityForResult(myIntent, SELECCIONAR_CONJUNTO_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == SELECCIONAR_CONJUNTO_REQUEST) {
            if (resultCode == RESULT_OK && data != null) {
                idConjuntoSeleccionado = data.getExtras().getLong(SeleccionarConjuntoActivity.PARAMETRO_INTENT_ID_VESTUARIO_RESULTADO);
                presenter.seleccionarConjunto(idConjuntoSeleccionado);
            }
        }
    }

    protected void setUpDatePicker() {
        calendarDatePicker = Calendar.getInstance();
        calendarDatePicker.setTimeInMillis(fechaLong);
        editTextFecha = findViewById(R.id.fecha_evento);
        listenerDatePicker = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int monthOfYear,
                                  int dayOfMonth) {
                calendarDatePicker.set(Calendar.YEAR, year);
                calendarDatePicker.set(Calendar.MONTH, monthOfYear);
                calendarDatePicker.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                actualizarDatePicker();
            }

        };

        editTextFecha.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(DetalleEventoActivity.this, listenerDatePicker, calendarDatePicker
                        .get(Calendar.YEAR), calendarDatePicker.get(Calendar.MONTH),
                        calendarDatePicker.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
    }

    private void actualizarDatePicker() {
        String myFormat = "dd/MM/yyyy";
        SimpleDateFormat sdf = new SimpleDateFormat(myFormat);

        editTextFecha.setText(sdf.format(calendarDatePicker.getTime()));
    }

    protected boolean guardarCambiosEvento() {
        int hora = Integer.parseInt(horaElegida.subSequence(0, 2).toString());
        int minuto = Integer.parseInt(horaElegida.subSequence(3, 5).toString());
        calendarDatePicker.set(Calendar.HOUR_OF_DAY, hora);
        calendarDatePicker.set(Calendar.MINUTE, minuto);
        calendarDatePicker.set(Calendar.SECOND, 0);

        if (!validarCamposObligatorios())
            return false;

        String nombreEvento = nombreEventoEditText.getText().toString();

        presenter.guardarCambiosEvento(nombreEvento, calendarDatePicker.getTime(), indiceTiempoNotificacion);

        return true;

    }

    private boolean validarCamposObligatorios() {
        boolean nombreEventoVal = ValidacionInput.tieneTexto(nombreEventoEditText);
        boolean conjuntoVal = (idConjuntoSeleccionado != -1);
        if (!conjuntoVal)
            ValidacionInput.requerir(botonConjunto);
        return conjuntoVal && nombreEventoVal;
    }

}