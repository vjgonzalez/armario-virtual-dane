package ar.uba.fi.armariovirtual.modelo;

import android.support.annotation.NonNull;
import android.util.Log;

import com.orm.dsl.Ignore;

import java.util.Collections;
import java.util.List;

import ar.uba.fi.utilidadesdane.persistencia.ObjetoPersistente;
import ar.uba.fi.utilidadesdane.imagenes.FileAndImageUtils;

public class Prenda extends Clasificable implements Comparable<Prenda> {

    @Ignore
    public static final String NOMBRE_TABLA = "PRENDA";

    protected String nombre;
    protected String rutaImagen;
    protected boolean favorito;

    public Prenda() {
        super();
    }

    public Prenda(String nombre, String rutaImagen) {
        super();
        this.nombre = nombre;
        this.rutaImagen = rutaImagen;
        favorito = false;
        save();
    }

    public String toString() {
        return "{Prenda(" + getId() + ") - Nombre: " + nombre + " - rutaImagen: " + rutaImagen + "}";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public boolean getFavorito() {
        return favorito;
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {

        if (this.rutaImagen == null)
            this.rutaImagen = "";

        boolean esImagenDistinta = !this.rutaImagen.equals(rutaImagen);

        // Si se va a reemplazar una imagen existenre, eliminarla
        if (!rutaImagen.isEmpty() && esImagenDistinta) {
            FileAndImageUtils.eliminarArchivo(this.rutaImagen);
        }

        this.rutaImagen = rutaImagen;

        if (esImagenDistinta) {
            this.save(); // Llamada a save() necesaria para que los conjuntos "vean" la nueva imagen
            List<Conjunto> conjuntosAsociados = obtenerRelaciones(Conjunto.class);
            for (Conjunto conjunto : conjuntosAsociados) {
                conjunto.actualizarImagen();
            }
        }
    }

    @Override
    public boolean delete() {
        Log.d("DANE", "Borrando Prenda");

        // Obtengo ahora la lista de conjuntos para, después de eliminada la prenda, actualizarlos/eliminarlos
        List<Conjunto> conjuntosAsociados = obtenerRelaciones(Conjunto.class);

        // Eliminar la imagen
        FileAndImageUtils.eliminarArchivo(getRutaImagen());

        boolean res = super.delete();

        for (Conjunto conjunto : conjuntosAsociados) {
            // Si el conjunto queda sin prendas, eliminarlo
            if (conjunto.obtenerPrendas().size() == 0) {
                conjunto.delete();
            } else {
                conjunto.actualizarImagen();
            }
        }

        return res;
    }

    public List<Conjunto> obtenerConjuntos() {
        return this.obtenerRelaciones(Conjunto.class);
    }

    public static List<Prenda> obtenerTodas() {
        return ObjetoPersistente.listarTodos(Prenda.class);
    }

    public static List<Prenda> obtenerTodasFavoritasPrimero() {
        List<Prenda> prendas = ObjetoPersistente.listarTodos(Prenda.class, "favorito");
        Collections.reverse(prendas);
        return prendas;
    }

    @Override
    public int compareTo(@NonNull Prenda prenda) {
        if (favorito == prenda.favorito)
            return nombre.compareTo(prenda.nombre);
        else if (favorito)
            return -1;
        else return 1;
    }
}