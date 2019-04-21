package ar.uba.fi.armariovirtual.activities.cuestionario;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.TextView;

import ar.uba.fi.armariovirtual.activities.BarraBaseActivity;
import ar.uba.fi.utilidadesdane.cuestionario.AdaptadorOpcionesCuestionario;
import ar.uba.fi.utilidadesdane.cuestionario.AdministradorCuestionario;
import ar.uba.fi.utilidadesdane.cuestionario.Cuestionario;
import ar.uba.fi.utilidadesdane.cuestionario.Opcion;
import ar.uba.fi.utilidadesdane.cuestionario.Pregunta;
import ar.uba.fi.utilidadesdane.utils.Ejecutable;

/**
 * Implementación tomada de CuestionarioBaseActivity, necesaria para setear superclase BarraBaseActivity
 */
public class CuestionarioBaseActivityArmarioVirtual extends BarraBaseActivity {

    private Cuestionario cuestionario;

    private AbsListView _opcionesListView;
    private AdaptadorOpcionesCuestionario _adaptadorOpciones;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        int layoutId = intent.getIntExtra(AdministradorCuestionario.PARAM_CUSTOM_LAYOUT_PREGUNTA_CUESTIONARIO_ID, ar.uba.fi.utilidadesdane.R.layout.pregunta_cuestionario_por_defecto_layout);

        setContentView(layoutId);

        CrearCuestionario();
    }

    private void CrearCuestionario() {
        Intent intent = getIntent();

        int cuestionarioId = intent.getIntExtra(AdministradorCuestionario.PARAM_CUESTIONARIO_ID, 0);
        int numPreguntas = intent.getIntExtra(AdministradorCuestionario.PARAM_NUM_PREGUNTAS, -1);

        try {
            cuestionario = Cuestionario.crear(cuestionarioId, numPreguntas, getApplicationContext(), Boolean.TRUE);
        } catch (Exception e) {
            //TODO
        }

        _opcionesListView = findViewById(ar.uba.fi.utilidadesdane.R.id.opciones);

        MostrarSiguientePregunta();
        for (int i = 0; i < cuestionario.cantidadPreguntas(); i++) {
            Pregunta p = cuestionario.obtenerPregunta(i);
            String respuestasIncorrectasStr = "";
            for (int j = 0; j < p.getRespuestasIncorrectas().size(); j++) {
                respuestasIncorrectasStr = respuestasIncorrectasStr.concat(p.getRespuestasIncorrectas().get(j));
                if (j < p.getRespuestasIncorrectas().size() - 1)
                    respuestasIncorrectasStr = respuestasIncorrectasStr.concat(", ");
            }
        }
    }

    private void MostrarSiguientePregunta() {
        int idxPreguntaActual = cuestionario.cantidadPreguntasEvaluadas();
        int cantPreguntasTotal = cuestionario.cantidadPreguntas();

        ((TextView) findViewById(ar.uba.fi.utilidadesdane.R.id.numero_pregunta)).setText(getString(ar.uba.fi.utilidadesdane.R.string.cuestionario_pregunta_x_de_y, (idxPreguntaActual + 1), cantPreguntasTotal));

        Pregunta p = cuestionario.obtenerPregunta(idxPreguntaActual);
        ((TextView) findViewById(ar.uba.fi.utilidadesdane.R.id.pregunta)).setText(p.getTextoPregunta());

        Intent intent = getIntent();
        int layoutItemRespuestaId = intent.getIntExtra(AdministradorCuestionario.PARAM_CUSTOM_LAYOUT_ITEM_RESPUESTA_ID, ar.uba.fi.utilidadesdane.R.layout.item_respuesta_por_defecto_layout);
        _adaptadorOpciones = new AdaptadorOpcionesCuestionario(p.getOpciones(), this, layoutItemRespuestaId);

        _opcionesListView.setAdapter(_adaptadorOpciones);

        final Pregunta preguntaActual = p;
        _opcionesListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                int resultado = preguntaActual.evaluar(position);
                Opcion opcionElegida = (Opcion) _adaptadorOpciones.getItem(position);
//                Log.d("DANE","OPCION ELEGIDA: - " + opcionElegida.getTextoOpcion() + " - BTN_ID: " + opcionElegidaBtnId + " - IDX: " + opcionIdx);
                if (resultado == Pregunta.RESULTADO_CORRECTO) {
                    OnOpcionCorrecta(view);
                } else {
                    OnOpcionIncorrecta(view);
                }
            }
        });
    }

    private void OnOpcionCorrecta(final View opcionElegidaView) {

        MostrarFeedbackOpcionCorrecta(opcionElegidaView, new Ejecutable() {
            @Override
            public void ejecutar() {
                if (cuestionario.cantidadPreguntasEvaluadas() == cuestionario.cantidadPreguntas()) {
                    CuestionarioCompleto(opcionElegidaView);
                } else {
                    MostrarSiguientePregunta();
                }
            }
        });
    }


    protected void MostrarFeedbackOpcionCorrecta(View opcionElegidaView, final Ejecutable onComplete) {
        AnimarVista(findViewById(ar.uba.fi.utilidadesdane.R.id.resultado_correcto), AnimationUtils.loadAnimation(getApplicationContext(), ar.uba.fi.utilidadesdane.R.anim.fade_in), onComplete);
    }


    private void OnOpcionIncorrecta(View opcionElegidaView) {
        MostrarFeedbackOpcionIncorrecta(opcionElegidaView, null);
    }

    protected void MostrarFeedbackOpcionIncorrecta(View opcionElegidaView, final Ejecutable onComplete) {
        AnimarVista(findViewById(ar.uba.fi.utilidadesdane.R.id.resultado_incorrecto), AnimationUtils.loadAnimation(getApplicationContext(), ar.uba.fi.utilidadesdane.R.anim.fade_in), onComplete);
    }

    /**
     * Esta función debe envargarse de avanzar a la siguiente Activity
     *
     * @param opcionElegidaView
     */
    protected void CuestionarioCompleto(View opcionElegidaView) {
        AnimarVista(findViewById(ar.uba.fi.utilidadesdane.R.id.cuestionario_completo), AnimationUtils.loadAnimation(getApplicationContext(), ar.uba.fi.utilidadesdane.R.anim.fade_in), new Ejecutable() {
            @Override
            public void ejecutar() {
                finish();
            }
        });
    }

    protected void AnimarVista(final View vistaResultado, Animation animacionResultado, final Ejecutable onComplete) {
        findViewById(ar.uba.fi.utilidadesdane.R.id.bloqueador).setVisibility(View.VISIBLE);

        animacionResultado.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation animation) {
                findViewById(ar.uba.fi.utilidadesdane.R.id.bloqueador).setVisibility(View.INVISIBLE);
                vistaResultado.setVisibility(View.INVISIBLE);

                if (onComplete != null) {
                    onComplete.ejecutar();
                }
            }

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });

        vistaResultado.startAnimation(animacionResultado);
        vistaResultado.setVisibility(View.VISIBLE);
    }
}
