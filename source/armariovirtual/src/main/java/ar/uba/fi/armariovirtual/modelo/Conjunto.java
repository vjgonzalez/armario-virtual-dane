package ar.uba.fi.armariovirtual.modelo;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.util.Log;

import com.orm.dsl.Ignore;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
import java.util.Collections;
import java.util.List;

import app.ArmarioVirtualApplication;
import ar.uba.fi.armariovirtual.utils.ICallback;
import ar.uba.fi.armariovirtual.utils.ImageUtils;
import ar.uba.fi.armariovirtual.utils.TargetPersistente;
import ar.uba.fi.utilidadesdane.persistencia.ParObjetoPersistente;
import ar.uba.fi.utilidadesdane.persistencia.ObjetoPersistente;
import ar.uba.fi.utilidadesdane.imagenes.FileAndImageUtils;

public class Conjunto extends Clasificable implements Comparable<Conjunto> {

    @Ignore
    public static final String NOMBRE_TABLA = "CONJUNTO";

    private static int ANCHO_IMAGEN = 1200;
    private static int ALTO_IMAGEN = 1200;

    private String nombre;
    protected String rutaImagen;
    protected boolean favorito;

    public Conjunto() {
        super();
    }

    public Conjunto(String nombre) {
        this.nombre = nombre;
        favorito = false;
        save();
    }

    public void agregarPrenda(Prenda prenda) {
        this.agregarRelacionConObjeto(prenda);
    }

    public List<Prenda> obtenerPrendas() {
        return this.obtenerRelaciones(Prenda.class);
    }

    public void setFavorito(boolean favorito) {
        this.favorito = favorito;
    }

    public boolean getFavorito() {
        return favorito;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public void quitarPrenda(Prenda prenda) {
        if (prenda == null)
            return;
        ParObjetoPersistente.eliminarPar(this, prenda);
    }

    public void actualizarImagen() {
        actualizarImagen(null);
    }

    public void actualizarImagen(final ICallback callback) {

        List<Prenda> prendas = obtenerPrendas();

        final Bitmap[] bitmaps = new Bitmap[prendas.size()];
        if (prendas.size() > 0) {
            for (int i = 0; i < prendas.size(); i++) {
                Prenda prenda = prendas.get(i);
                final int bitmapIdx = i;
                Picasso.with(ArmarioVirtualApplication.getAppContext())
                        .load(prenda.getRutaImagen())
                        .into(new TargetPersistente() {
                            @Override
                            public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                                bitmaps[bitmapIdx] = bitmap;
                                // Si es la última imagen cargada, creaer el bmp final y mostrarlo
                                for (Bitmap bitmap1 : bitmaps) {
                                    if (bitmap1 == null) return;
                                }
                                Bitmap imagenFinal = ImageUtils.combinarMosaicos(bitmaps, ANCHO_IMAGEN, ALTO_IMAGEN);
                                guardarImagen(imagenFinal);
                                if (callback != null) {
                                    callback.onSuccess();
                                }
                                finalizar();
                            }

                            @Override
                            public void onBitmapFailed(Drawable errorDrawable) {
                                finalizar();
                            }

                            @Override
                            public void onPrepareLoad(Drawable placeHolderDrawable) {
                            }
                        });
            }
        }
    }

    private void guardarImagen(Bitmap imagen) {

        String subcarpeta = "conjuntos";
        String nombreImagen = "conjunto_" + getId().toString();
        File archivoImagen = FileAndImageUtils.crearArchivoImagen(ArmarioVirtualApplication.getAppContext(), subcarpeta, nombreImagen);

        try {
            FileAndImageUtils.guardarBitmapEnFile(imagen, archivoImagen);
            String rutaImagen = Uri.fromFile(archivoImagen).toString();
            Picasso.with(ArmarioVirtualApplication.getAppContext()).invalidate(rutaImagen);
            setRutaImagen(rutaImagen);
            save();
            archivoImagen = null;
        } catch (IOException err) {
        }
    }

    public String getNombre() {
        return this.nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public boolean delete() {

        // Eliminar clasificaciones relacionadas
        List<Clasificacion> clasificacionesAsociadas = obtenerRelaciones(Clasificacion.class);
        for (Clasificacion clasificacion : clasificacionesAsociadas) {
            quitarClasificacion(clasificacion);
            clasificacion.quitarTodasLasOpcionesElegidas();
            clasificacion.delete();
        }

        // Eliminar la imagen
        FileAndImageUtils.eliminarArchivo(getRutaImagen());

        // Eliminar eventos asociados
        // Eliminar clasificaciones relacionadas
        List<EventoCalendario> eventos = ObjetoPersistente.listarTodos(EventoCalendario.class);
        for (EventoCalendario evento : eventos) {
            if (evento.getConjunto() != null && evento.getConjunto().getId().equals(this.getId())) {
                evento.delete();
            }
        }


        return super.delete();
    }

    public static List<Conjunto> obtenerTodos() {
        return ObjetoPersistente.listarTodos(Conjunto.class);
    }

    public static List<Conjunto> obtenerTodosFavoritosPrimero() {
        List<Conjunto> cojuntos = ObjetoPersistente.listarTodos(Conjunto.class, "favorito");
        Collections.reverse(cojuntos);
        return cojuntos;
    }

    @Override
    public int compareTo(@NonNull Conjunto conjunto) {
        if (favorito == conjunto.favorito)
            return nombre.compareTo(conjunto.nombre);
        else if (favorito)
            return -1;
        else return 1;
    }
}
