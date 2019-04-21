package ar.uba.fi.armariovirtual.activities;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.utilidadesdane.audio.AudioFondo;

/*Esta clase corresponde a la barra de botones comunes a algunas activities (botón de volver, silenciar, etc)
 * Las activities que utilicen la barra deben extender esta clase, llamar a mappingWidgets() en su onCreate() e incluir base_layout en sus layouts
 * */
public abstract class BarraBaseActivity extends Activity {

    protected ImageButton btnAtrasConfig;
    protected ImageButton btnAyuda;
    protected ImageButton btnInfo;

    protected ImageButton btnSonido;
    protected boolean pantallaSilenciosa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    //Se debe llamar este mtodo o el siguiente al final del onCreate de las subclases
    protected void setUpBarraConBotonDeAtras(boolean pantallaSilenciosa, final boolean pedirConfirmacionAlSalir, final Intent intentAtras) {
        setUpBotonesComunes(pantallaSilenciosa);
        Drawable tempImage = getResources().getDrawable(R.drawable.base_atras);
        btnAtrasConfig.setImageDrawable(tempImage);
        final Context context = this;
        btnAtrasConfig.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (pedirConfirmacionAlSalir) {
                    new AlertDialog.Builder(context)
                            .setTitle(context.getResources().getString(R.string.dialogo_salir_cambios_titulo))
                            .setMessage(context.getResources().getString(R.string.dialogo_salir_cambios_texto))
                            .setIcon(android.R.drawable.ic_dialog_alert)
                            .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int whichButton) {
                                    if (intentAtras == null)
                                        finish();
                                    else
                                        startActivity(intentAtras);
                                }
                            })
                            .setNegativeButton(android.R.string.no, null).show();
                } else {
                    if (intentAtras == null)
                        finish();
                    else
                        startActivity(intentAtras);
                }
            }
        });
    }

    //Se debe llamar al final del onCreate de las subclases
    protected void setUpBarraConBotonDeConfiguracion(boolean pantallaSilenciosa) {
        setUpBotonesComunes(pantallaSilenciosa);
        Drawable tempImage = getResources().getDrawable(R.drawable.base_configuracion);
        btnAtrasConfig.setImageDrawable(tempImage);
        btnAtrasConfig.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(BarraBaseActivity.this, ConfiguracionActivity.class);
                startActivity(intent);
            }
        });
    }

    private void setUpBotonesComunes(boolean esPantallaSilenciosa) {
        btnAtrasConfig = findViewById(R.id.btn_atras_config);

        this.pantallaSilenciosa = esPantallaSilenciosa;

        btnSonido = findViewById(R.id.btn_sonido);
        if (pantallaSilenciosa) {
            AudioFondo.setSilencio(true);
            btnSonido.setImageResource(R.drawable.base_sound_off);
            btnSonido.setClickable(false);
        }

        setUpSonido(AudioFondo.getSilencio());

        btnSonido.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                if (!pantallaSilenciosa)
                    cambioSonido(AudioFondo.getSilencio());
            }
        });

        btnAyuda = findViewById(R.id.btn_ayuda);

        btnAyuda.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(BarraBaseActivity.this, AyudaActivity.class);
                startActivity(intent);
            }
        });

        btnInfo = findViewById(R.id.btn_info);

        btnInfo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent intent = new Intent(BarraBaseActivity.this, InfoActivity.class);
                startActivity(intent);
            }
        });
    }

    private void cambioSonido(boolean silencioActual) {
        AudioFondo.setSilencio(!silencioActual);
        setUpSonido(!silencioActual);
    }

    private void setUpSonido(boolean silencioActual) {
        if (silencioActual)
            btnSonido.setImageResource(R.drawable.base_sound_off);
        else
            btnSonido.setImageResource(R.drawable.base_sound_on);
    }

    @Override
    protected void onPause() {
        super.onPause();
        AudioFondo.pausar();
        setUpSonido(AudioFondo.getSilencio());
    }

    @Override
    protected void onResume() {
        super.onResume();
        AudioFondo.setSilencio(AudioFondo.getSilencio());
        setUpSonido(AudioFondo.getSilencio());
    }
}