package ar.uba.fi.armariovirtual.presenters;

import android.net.Uri;

import java.util.ArrayList;
import java.util.List;

import app.ArmarioVirtualApplication;
import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.activities.DetallePrendaActivity;
import ar.uba.fi.armariovirtual.configuracion.FuncionalidadProtegible;
import ar.uba.fi.armariovirtual.modelo.Clasificacion;
import ar.uba.fi.armariovirtual.modelo.DefinicionClasificacion;
import ar.uba.fi.armariovirtual.modelo.Prenda;
import ar.uba.fi.utilidadesdane.autenticacion.Autenticacion;
import ar.uba.fi.utilidadesdane.autenticacion.ComandoAdministrador;
import ar.uba.fi.utilidadesdane.persistencia.ObjetoPersistente;
import ar.uba.fi.utilidadesdane.imagenes.FileAndImageUtils;

public class DetallePrendaPresenter {

    private DetallePrendaActivity view;
    private Prenda prendaEditada;
    private Uri imagenPrendaUri;

    public DetallePrendaPresenter(DetallePrendaActivity view, Long idPrenda) {
        this.view = view;
        if (idPrenda > -1) {
            prendaEditada = ObjetoPersistente.encontrarPorId(Prenda.class, idPrenda);
        }

    }

    public void descartarCambiosEnImagen() {
        if (imagenPrendaUri != null && (prendaEditada == null || !prendaEditada.getRutaImagen().equals(imagenPrendaUri.toString()))) {
            FileAndImageUtils.eliminarArchivo(imagenPrendaUri.toString());
        }
    }

    public void cargarDatosEnVista() {
        if (prendaEditada != null) {
            view.cargarDatosPrenda(prendaEditada.getNombre(), prendaEditada.getFavorito(), prendaEditada.getRutaImagen());
        }
    }

    public void guardarCambiosPrenda(final String nombre, final boolean favorito, final List<Clasificacion> _clasificaciones) {
        if (FuncionalidadProtegible.estaProtegida(view.getResources().getString(R.string.funcionalidad_editar_prendas))) {
            ComandoAdministrador ejecutarSiAdmin = new ComandoAdministrador() {
                @Override
                public void ejecutar() {
                    guardar(nombre, favorito, _clasificaciones);
                    view.salir();
                }
            };
            Autenticacion.instancia().autenticarYEjecutar(ejecutarSiAdmin, view.getFragmentManager());
        } else {
            guardar(nombre, favorito, _clasificaciones);
            view.salir();
        }
    }

    private void guardar(String nombre, boolean favorito, List<Clasificacion> _clasificaciones) {
        String rutaImagen = (imagenPrendaUri == null) ? "" : imagenPrendaUri.toString();

        if (prendaEditada == null)
            prendaEditada = new Prenda(nombre, rutaImagen);
        else {
            prendaEditada.setNombre(nombre);
            prendaEditada.setRutaImagen(rutaImagen);
        }

        prendaEditada.setFavorito(favorito);

        if (_clasificaciones != null) {
            for (Clasificacion clasificacion : _clasificaciones) {
                prendaEditada.agregarClasificacion(clasificacion);
            }
        }

        view.actualizarYGuardarClasificaciones();

        prendaEditada.save();

    }

    public void obtenerOCrearClasificaciones(List<Clasificacion> clasificaciones) {
        // Si la prenda existe, usar sus clasificaciones (y crear las que falten)
        if (!esNuevaPrenda()) {
            clasificaciones = prendaEditada.obtenerOCrearClasificaciones();
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

    private boolean esNuevaPrenda() {
        return prendaEditada == null;
    }


    public void setUriImagen(Uri contentUri) {
        // Si se está "pisando" una ruta de imagen temporal (no usada por la Prenda), eliminar la imagen anterior
        if (prendaEditada != null && imagenPrendaUri != null && !prendaEditada.getRutaImagen().equals(imagenPrendaUri.toString()) && !imagenPrendaUri.equals(contentUri)) {
            FileAndImageUtils.eliminarArchivo(contentUri.toString());
        }

        imagenPrendaUri = contentUri;
    }
}
