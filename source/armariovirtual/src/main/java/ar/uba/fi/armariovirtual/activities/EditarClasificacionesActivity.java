package ar.uba.fi.armariovirtual.activities;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.List;

import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.modelo.OpcionClasificacion;
import ar.uba.fi.armariovirtual.modelo.adaptadores.AdaptadorOpcionClasificacion;
import ar.uba.fi.armariovirtual.presenters.EditarClasificacionesPresenter;

public class EditarClasificacionesActivity extends BarraBaseActivity {

    static final int POSICION_NUEVA_CLASIFICACION = 999999;

    protected AdaptadorOpcionClasificacion adaptador;
    EditText nombreEditText;
    EditText preguntaEditText;
    EditarClasificacionesPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.clasificaciones);
        presenter = new EditarClasificacionesPresenter(this);

        findViewById(R.id.anterior).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                clasificacionAnterior();
            }
        });

        findViewById(R.id.siguiente).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                clasificacionSiguiente();
            }
        });

        findViewById(R.id.borrar_clasificacion).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                borrarClasificacion();
            }
        });

        findViewById(R.id.nueva_clasificacion).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                presenter.nuevaClasificacion();
            }
        });

        findViewById(R.id.boton_guardar).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                guardarCambiosClasificacionActual();
            }
        });

        nombreEditText = findViewById(R.id.nombre);
        preguntaEditText = findViewById(R.id.pregunta);

        int colorFlecha = Color.parseColor("#7f8c8d");
        ImageView flechaAnterior = findViewById(R.id.anterior);
        ImageView flechaSiguiente = findViewById(R.id.siguiente);
        flechaAnterior.setColorFilter(colorFlecha);
        flechaSiguiente.setColorFilter(colorFlecha);

        presenter.mostrarClasificacionActual();
        setUpBarraConBotonDeAtras(true, true, new Intent(EditarClasificacionesActivity.this, ConfiguracionActivity.class));

    }

    private boolean hayCambiosSinGuardar() {
        return adaptador.hayModificacionesSinGuardar() || presenter.hayCambiosSinGuardar(nombreEditText.getText().toString(), preguntaEditText.getText().toString());
    }

    private void clasificacionSiguiente() {
        if (!hayCambiosSinGuardar())
            presenter.clasificacionSiguiente();
        else {
            new AlertDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.dialogo_salir_cambios_titulo))
                    .setMessage(getResources().getString(R.string.dialogo_salir_cambios_texto))
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            presenter.clasificacionSiguiente();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        }
    }

    private void clasificacionAnterior() {
        if (!hayCambiosSinGuardar())
            presenter.clasificacionAnterior();
        else {
            new AlertDialog.Builder(this)
                    .setTitle(getResources().getString(R.string.dialogo_salir_cambios_titulo))
                    .setMessage(getResources().getString(R.string.dialogo_salir_cambios_texto))
                    .setIcon(android.R.drawable.ic_dialog_alert)
                    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int whichButton) {
                            presenter.clasificacionAnterior();
                        }
                    })
                    .setNegativeButton(android.R.string.no, null).show();
        }
    }

    public void mostrarNuevaClasificacion(List<OpcionClasificacion> opcionesActuales, OpcionClasificacion nueva) {
        nombreEditText.setText("");
        preguntaEditText.setText("");

        ListView lista = findViewById(R.id.lista_opciones);
        adaptador = new AdaptadorOpcionClasificacion(this, opcionesActuales);
        lista.setAdapter(adaptador);

        mostrarNuevaOpcion(nueva);
    }

    public void mostrarNuevaOpcion(OpcionClasificacion nueva) {
        adaptador.add(nueva);
        adaptador.notifyDataSetChanged();
        ListView listView = findViewById(R.id.lista_opciones);
        listView.setTranscriptMode(ListView.TRANSCRIPT_MODE_ALWAYS_SCROLL);
    }

    private void borrarClasificacion() {
        new AlertDialog.Builder(this)
                .setTitle(getResources().getString(R.string.dialogo_borrar_clasificacion_titulo))
                .setMessage(getResources().getString(R.string.dialogo_borrar_clasificacion_texto))
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int whichButton) {
                        presenter.borrarClasificacion();
                    }
                })
                .setNegativeButton(android.R.string.no, null).show();
    }

    public void actualizarClasificacionActual(String nombre, String pregunta, List<OpcionClasificacion> opcionesActuales) {
        nombreEditText.setText(nombre);
        preguntaEditText.setText(pregunta);

        ListView lista = findViewById(R.id.lista_opciones);
        adaptador = new AdaptadorOpcionClasificacion(this, opcionesActuales);
        lista.setAdapter(adaptador);


        findViewById(R.id.nueva_opcion).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                presenter.nuevaOpcion();
            }
        });
    }

    private void guardarCambiosClasificacionActual() {
        String nombre = nombreEditText.getText().toString();
        String pregunta = preguntaEditText.getText().toString();
        presenter.guardarCambiosClasificacionActual(nombre, pregunta, adaptador.getOpciones());
    }

    public void guardarCambiosOpciones() {
        adaptador.guardarCambios();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.mostrarClasificacionActual();
    }


}
