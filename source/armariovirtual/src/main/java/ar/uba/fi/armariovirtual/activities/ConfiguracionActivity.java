package ar.uba.fi.armariovirtual.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.utilidadesdane.audio.AudioFondo;
import ar.uba.fi.utilidadesdane.autenticacion.Autenticacion;
import ar.uba.fi.utilidadesdane.autenticacion.ComandoAdministrador;

public class ConfiguracionActivity extends BarraBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.configuracion);

        findViewById(R.id.clasificaciones_btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                ComandoAdministrador ejecutarSiAdmin = new ComandoAdministrador() {
                    @Override
                    public void ejecutar() {
                        Intent myIntent = new Intent(ConfiguracionActivity.this, EditarClasificacionesActivity.class);
                        startActivity(myIntent);                    }
                };
                Autenticacion.instancia().autenticarYEjecutar(ejecutarSiAdmin, getFragmentManager());
            }
        });

        findViewById(R.id.cambio_pin_btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Autenticacion.instancia().solicitarNuevoCodigoAdmin(getFragmentManager());
            }
        });

        findViewById(R.id.seguridad_btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                ComandoAdministrador ejecutarSiAdmin = new ComandoAdministrador() {
                    @Override
                    public void ejecutar() {
                        Intent myIntent = new Intent(ConfiguracionActivity.this, EditarProteccionActivity.class);
                        startActivity(myIntent);                    }
                };
                Autenticacion.instancia().autenticarYEjecutar(ejecutarSiAdmin, getFragmentManager());
            }
        });

        try {
            AudioFondo.start(this, 1, false);
        } catch (Exception e) {
            AudioFondo.setSilencio(true);
        }

        /**TODO Para usar durante el desarrollo, borrar**/
        findViewById(R.id.ajustes_btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(ConfiguracionActivity.this, AjustesDesarrolloActivity.class);
                startActivity(myIntent);
            }
        });
        /****/

        setUpBarraConBotonDeAtras(false, false, new Intent(ConfiguracionActivity.this, MenuPrincipalActivity.class));
    }

}
