package ar.uba.fi.armariovirtual.modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Leccion implements Serializable {

    private String nombre;
    private String rutaImagen;
    private String descripcion;
    private int idCuestionario;

    public List<String> getDiapositivas() {
        return diapositivas;
    }

    private List<String> diapositivas;

    private Long id;

    public Leccion(String nombre, String descripcion, String rutaImagen, int idCuestionario) {
        this.nombre = nombre;
        this.rutaImagen = rutaImagen;
        this.descripcion = descripcion;
        this.idCuestionario = idCuestionario;
        this.diapositivas = new ArrayList<>();
    }

    public String toString() {
        return "{Leccion(" + getId() + ") - Nombre: " + nombre + " - rutaImagen: " + rutaImagen + " - descripcion: " + descripcion + " - idCuestionario: " + idCuestionario + "}";
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getRutaImagen() {
        return rutaImagen;
    }

    public void setRutaImagen(String rutaImagen) {
        this.rutaImagen = rutaImagen;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public Long getId() {
        return id;
    }

    public int getIdCuestionario() {
        return idCuestionario;
    }

    public void setIdCuestionario(int idCuestionario) {
        this.idCuestionario = idCuestionario;
    }

    public void agregarDiapositiva(String rutaImagen) {
        diapositivas.add(rutaImagen);
    }
}