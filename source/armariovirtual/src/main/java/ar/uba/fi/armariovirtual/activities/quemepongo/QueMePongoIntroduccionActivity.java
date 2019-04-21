package ar.uba.fi.armariovirtual.activities.quemepongo;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.activities.BarraBaseActivity;
import ar.uba.fi.armariovirtual.activities.MenuPrincipalActivity;
import ar.uba.fi.armariovirtual.utils.BotonSombra;
import ar.uba.fi.utilidadesdane.audio.AudioFondo;

public class QueMePongoIntroduccionActivity extends BarraBaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.que_me_pongo_introduccion);

        findViewById(R.id.comenzar_btn).setOnClickListener(new View.OnClickListener() {
            public void onClick(View arg0) {
                Intent myIntent = new Intent(QueMePongoIntroduccionActivity.this, QueMePongoPreguntasActivity.class);
                startActivity(myIntent);
            }
        });

        setUpBarraConBotonDeAtras(false, false, new Intent(QueMePongoIntroduccionActivity.this, MenuPrincipalActivity.class));

        try {
            AudioFondo.start(this, 6, false);
        } catch (Exception e) {
            AudioFondo.setSilencio(true);
        }
    }
}
