package ar.uba.fi.armariovirtual.presenters;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.activities.quemepongo.QueMePongoPreguntasActivity;
import ar.uba.fi.armariovirtual.modelo.Clasificable;
import ar.uba.fi.armariovirtual.modelo.Conjunto;
import ar.uba.fi.armariovirtual.modelo.DefinicionClasificacion;
import ar.uba.fi.armariovirtual.modelo.OpcionClasificacion;
import ar.uba.fi.armariovirtual.modelo.filtros.ClasificacionFiltro;
import ar.uba.fi.armariovirtual.modelo.filtros.FiltroClasificaciones;
import ar.uba.fi.armariovirtual.modelo.filtros.OpcionFiltro;

public class QueMePongoPreguntasPresenter {

    private QueMePongoPreguntasActivity view;
    private List<DefinicionClasificacion> _definicionesClasificacion;
    private int _idxDefinicionClasificacion;
    private List<OpcionClasificacion> _opcionesElegidas;
    private List<OpcionClasificacion> _opciones;


    public QueMePongoPreguntasPresenter(QueMePongoPreguntasActivity view) {
        this.view = view;
    }

    public void inicializarPreguntas() {
        _definicionesClasificacion = DefinicionClasificacion.obtenerTodas();
        _idxDefinicionClasificacion = 0;

        // Inicializar todas en "Indistinto"
        _opcionesElegidas = new ArrayList<>();
        OpcionClasificacion opcionIndistinto = OpcionClasificacion.obtenerOCrear(view.getResources().getString(R.string.clasificacion_qmp_indistinto));
        int i = _definicionesClasificacion.size();
        while (i-- > 0) {
            _opcionesElegidas.add(opcionIndistinto);
        }

        listarOpciones();
    }

    private void listarOpciones() {
        if (_idxDefinicionClasificacion < _definicionesClasificacion.size()) {
            DefinicionClasificacion definicionClasificacion = _definicionesClasificacion.get(_idxDefinicionClasificacion);

            _opciones = definicionClasificacion.obtenerOpciones();
            _opciones.add(OpcionClasificacion.obtenerOCrear(view.getResources().getString(R.string.clasificacion_qmp_indistinto)));
            _opciones.add(OpcionClasificacion.obtenerOCrear(view.getResources().getString(R.string.clasificacion_qmp_no_se)));

            view.cargarDatosPregunta(definicionClasificacion.getPreguntaAsociada(), _opciones);

        }
    }

    public void setOpcionElegida(OpcionClasificacion opcionElegida) {
        _opcionesElegidas.set(_idxDefinicionClasificacion, opcionElegida);

        if (_idxDefinicionClasificacion < _definicionesClasificacion.size() - 1) {
            _idxDefinicionClasificacion++;
            listarOpciones();
        } else {
            // Completó todas las preguntas. Procesar y mostrar resultado
            List<Conjunto> conjuntosResultado = obtenerConjuntosResultado();
            long[] idsConjuntosResultado = new long[conjuntosResultado.size()];
            for (int i = 0; i < conjuntosResultado.size(); i++) {
                idsConjuntosResultado[i] = conjuntosResultado.get(i).getId();
            }

            view.finalizarCuestionario(idsConjuntosResultado);
        }
    }

    private List<Conjunto> obtenerConjuntosResultado() {

        // Buscar todos los conjuntos que cumplan con las opciones elegidas (AND)
        FiltroClasificaciones filtro = new FiltroClasificaciones(_definicionesClasificacion); // Pasado por parámetro mantiene el orden en las clasificaciones
        List<ClasificacionFiltro> clasificacionesFiltro = filtro.obtenerClasificaciones();
        Long idOpcionIndistinto = OpcionClasificacion.obtenerOCrear(view.getResources().getString(R.string.clasificacion_qmp_indistinto)).getId();
        Long idOpcionNoSe = OpcionClasificacion.obtenerOCrear(view.getResources().getString(R.string.clasificacion_qmp_no_se)).getId();
        for (int i = 0; i < _opcionesElegidas.size(); i++) {
            // Si seleccionó unan opción distinta a "NO SÉ" o "INDISTINTO", actualizar el filtro
            OpcionClasificacion opcionElegida = _opcionesElegidas.get(i);
            if (!opcionElegida.getId().equals(idOpcionIndistinto) && !opcionElegida.getId().equals(idOpcionNoSe)) {
                ClasificacionFiltro clasificacionFiltro = clasificacionesFiltro.get(i);
                List<OpcionFiltro> opcionesFiltro = clasificacionFiltro.getOpcionesFiltro();
                for (int idxOpcion = 0; idxOpcion < opcionesFiltro.size(); idxOpcion++) {
                    OpcionFiltro opcionFiltro = opcionesFiltro.get(idxOpcion);
                    opcionFiltro.setSeleccionada(opcionFiltro.getIdOpcion().equals(opcionElegida.getId()));
                }
            }
        }

        List<? extends Clasificable> conjuntosResultadoQuery = filtro.obtenerResultadosFiltrados(Conjunto.class, Conjunto.NOMBRE_TABLA, Conjunto.class.getCanonicalName());
        List <Conjunto> resultadosConjuntos = (List<Conjunto>) conjuntosResultadoQuery;
        Collections.sort(resultadosConjuntos);
        return resultadosConjuntos;
    }

    public void irAPreguntaAnterior() {
        _idxDefinicionClasificacion--;
        if (_idxDefinicionClasificacion >= 0)
            listarOpciones();
        else {
            view.volverAIntroduccion();
        }
    }

}
