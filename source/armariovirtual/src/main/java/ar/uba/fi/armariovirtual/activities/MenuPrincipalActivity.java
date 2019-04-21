package ar.uba.fi.armariovirtual.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.activities.aprendoavestirme.AprendoAVestirmeMenuActivity;
import ar.uba.fi.armariovirtual.activities.calendario.CalendarioActivity;
import ar.uba.fi.armariovirtual.activities.quemepongo.QueMePongoIntroduccionActivity;
import ar.uba.fi.utilidadesdane.audio.AudioFondo;

public class MenuPrincipalActivity extends BarraBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu_principal);

        findViewById(R.id.vestuario_btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(MenuPrincipalActivity.this, ListadoPrendasActivity.class);
                startActivity(myIntent);
            }
        });

        findViewById(R.id.calendario_btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(MenuPrincipalActivity.this, CalendarioActivity.class);
                startActivity(myIntent);
            }
        });

        findViewById(R.id.que_me_pongo_btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(MenuPrincipalActivity.this, QueMePongoIntroduccionActivity.class);
                startActivity(myIntent);
            }
        });

        findViewById(R.id.aprendo_a_vestirme_btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(MenuPrincipalActivity.this, AprendoAVestirmeMenuActivity.class);
                startActivity(myIntent);
            }
        });

        try {
            AudioFondo.start(this, 0, false);
        } catch (Exception e) {
            AudioFondo.setSilencio(true);
        }

        setUpBarraConBotonDeConfiguracion(false);

    }
}
