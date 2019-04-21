package ar.uba.fi.armariovirtual.activities.aprendoavestirme;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.activities.BarraBaseActivity;
import ar.uba.fi.armariovirtual.activities.cuestionario.CuestionarioBaseActivityArmarioVirtual;
import ar.uba.fi.armariovirtual.modelo.Leccion;
import ar.uba.fi.utilidadesdane.cuestionario.CuestionarioBaseActivity;
import ar.uba.fi.utilidadesdane.utils.Ejecutable;

public class AprendoAVestirmePreguntasActivity extends CuestionarioBaseActivityArmarioVirtual {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setUpBarraConBotonDeAtras(true, false, new Intent(AprendoAVestirmePreguntasActivity.this, AprendoAVestirmeMenuActivity.class));
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(AprendoAVestirmePreguntasActivity.this, AprendoAVestirmeLeccionActivity.class);
        startActivity(intent);
    }


    @Override
    protected void MostrarFeedbackOpcionCorrecta(View opcionElegidaView, final Ejecutable onComplete)
    {
//        AnimarVista(findViewById(R.id.resultado_correcto), AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in), onComplete);

        Toast toast = Toast.makeText(this, "CUSTOM FEEDBACK OPCION CORRECTA", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();

        // Para continuar con un delay y darle tiempo a la animación
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if(onComplete != null)
                {
                    onComplete.ejecutar();
                }
            }
        }, 3000);
    }

    @Override
    protected void MostrarFeedbackOpcionIncorrecta(View opcionElegidaView, final Ejecutable onComplete)
    {
//        AnimarVista(findViewById(R.id.resultado_incorrecto), AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in), onComplete);

        Toast toast = Toast.makeText(this, "CUSTOM FEEDBACK OPCION INCORRECTA", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();

        // Para continuar con un delay y darle tiempo a la animación
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                if(onComplete != null)
                {
                    onComplete.ejecutar();
                }
            }
        }, 3000);
    }

    @Override
    protected void CuestionarioCompleto(View opcionElegidaView)
    {
        Log.d("DANE","CUESTIONARIO COMPLETO");
        Toast toast = Toast.makeText(this, "CUSTOM FEEDBACK CUESTIONARIO COMPLETO", Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
        toast.show();

        // Para continuar con un delay y darle tiempo a la animación
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            public void run() {
                Intent intent = new Intent(AprendoAVestirmePreguntasActivity.this, AprendoAVestirmeMenuActivity.class);
                startActivity(intent);
            }
        }, 3000);
    }

}
