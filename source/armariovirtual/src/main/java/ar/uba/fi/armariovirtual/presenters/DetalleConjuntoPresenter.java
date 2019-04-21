package ar.uba.fi.armariovirtual.presenters;

import java.util.ArrayList;
import java.util.List;

import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.activities.DetalleConjuntoActivity;
import ar.uba.fi.armariovirtual.configuracion.FuncionalidadProtegible;
import ar.uba.fi.armariovirtual.modelo.Clasificacion;
import ar.uba.fi.armariovirtual.modelo.Conjunto;
import ar.uba.fi.armariovirtual.modelo.DefinicionClasificacion;
import ar.uba.fi.armariovirtual.modelo.Prenda;
import ar.uba.fi.armariovirtual.utils.ICallback;
import ar.uba.fi.utilidadesdane.autenticacion.Autenticacion;
import ar.uba.fi.utilidadesdane.autenticacion.ComandoAdministrador;
import ar.uba.fi.utilidadesdane.persistencia.ObjetoPersistente;

public class DetalleConjuntoPresenter {


    private DetalleConjuntoActivity view;
    private Conjunto conjuntoEditado;


    public DetalleConjuntoPresenter(DetalleConjuntoActivity view, Long idConjunto) {
        this.view = view;
        if (idConjunto > -1) {
            conjuntoEditado = ObjetoPersistente.encontrarPorId(Conjunto.class, idConjunto);
        }
    }


    public void cargarDatosEnVista() {
        if (conjuntoEditado != null) {
            view.cargarDatosConjunto(conjuntoEditado.getNombre(), conjuntoEditado.getFavorito());
        }
    }

    private boolean esNuevoConjunto() {
        return conjuntoEditado == null;
    }

    public void obtenerOCrearClasificaciones(List<Clasificacion> clasificaciones) {
        // Si la prenda existe, usar sus clasificaciones (y crear las que falten)
        if (!esNuevoConjunto()) {
            clasificaciones = conjuntoEditado.obtenerOCrearClasificaciones();
        } else {
            // Nueva prenda - en create o resume
            // La prenda no existe: Crear una clasificación por cada elemento en DefinicionClasificacion y dejarlo preparado para asignar a una prenda cuando se cree
            if (clasificaciones == null) {
                clasificaciones = new ArrayList<>();
                List<DefinicionClasificacion> definicionClasificaciones = ObjetoPersistente.listarTodos(DefinicionClasificacion.class);
                for (DefinicionClasificacion definicion : definicionClasificaciones) {
                    Clasificacion nuevaClasificacion = new Clasificacion(definicion);
                    clasificaciones.add(nuevaClasificacion);
                }
            }
        }

        view.inicializarClasificaciones(clasificaciones);
    }

    public void obtenerOCrearPrendas(List<Prenda> _prendas) {
        List<Prenda> prendas;

        if (_prendas != null)
            prendas = new ArrayList<>(_prendas);
        else if (!esNuevoConjunto()) {
            _prendas = conjuntoEditado.obtenerPrendas();
            prendas = new ArrayList<>(_prendas);
        } else {
            prendas = new ArrayList<>();
        }

        view.inicializarPrendas(prendas);
    }

    public void guardarCambiosConjunto(final String nombre, final boolean favorito, final List<Clasificacion> clasificaciones, final List<Prenda> prendas) {
        if (FuncionalidadProtegible.estaProtegida(view.getResources().getString(R.string.funcionalidad_editar_conjuntos))) {
            ComandoAdministrador ejecutarSiAdmin = new ComandoAdministrador() {
                @Override
                public void ejecutar() {
                    guardar(nombre, favorito, clasificaciones, prendas);
                    view.salir();
                }
            };
            Autenticacion.instancia().autenticarYEjecutar(ejecutarSiAdmin, view.getFragmentManager());
        } else {
            guardar(nombre, favorito, clasificaciones, prendas);
            view.salir();
        }
    }

    private void guardar(String nombre, boolean favorito, List<Clasificacion> clasificaciones, List<Prenda> prendas) {
        if (conjuntoEditado == null)
            conjuntoEditado = new Conjunto(nombre);

        conjuntoEditado.setNombre(nombre);
        conjuntoEditado.setFavorito(favorito);

        // Quitar todas las clasificaciones
        for (Clasificacion clasificacion : conjuntoEditado.obtenerClasificaciones()) {
            conjuntoEditado.quitarClasificacion(clasificacion);
        }
        // Guardar clasificaciones
        if (clasificaciones != null) {
            for (Clasificacion clasificacion : clasificaciones) {
                conjuntoEditado.agregarClasificacion(clasificacion);
            }
        }

        view.actualizarYGuardarClasificaciones();

        // Quitar todas las prendas
        for (Prenda prenda : conjuntoEditado.obtenerPrendas()) {
            conjuntoEditado.quitarPrenda(prenda);
        }
        // Guardar prendas
        if (prendas != null) {
            for (Prenda prenda : prendas) {
                conjuntoEditado.agregarPrenda(prenda);
            }
        }

        conjuntoEditado.actualizarImagen(new ICallback() {
            @Override
            public void onSuccess() {
            }
        });
    }

    public void agregarPrenda(Long idPrenda, List<Prenda> prendas) {
        Prenda prenda = ObjetoPersistente.encontrarPorId(Prenda.class, idPrenda);
        if (prenda != null) {

            boolean repetida = false;
            for (Prenda prendaCheck : prendas) {
                if (prenda.getId().equals(prendaCheck.getId())) {
                    repetida = true;
                    break;
                }
            }
            if (!repetida) {
                prendas.add(prenda);
            }
            obtenerOCrearPrendas(prendas);
        }
    }


    public void quitarPrenda(Long idPrenda, List<Prenda> prendas) {
        Prenda prendaAQuitar = ObjetoPersistente.encontrarPorId(Prenda.class, idPrenda);
        Prenda prendaEnConj = null;
        for (Prenda prendaCheck : prendas) {
            if (prendaCheck.getId().equals(prendaAQuitar.getId())) {
                prendaEnConj = prendaCheck;
            }
        }
        if (prendaEnConj != null) {
            prendas.remove(prendaEnConj);
        }

        obtenerOCrearPrendas(prendas);
    }
}
