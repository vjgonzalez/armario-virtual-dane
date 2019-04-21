package ar.uba.fi.armariovirtual.configuracion;

import java.util.List;

import ar.uba.fi.utilidadesdane.persistencia.ObjetoPersistente;

public class FuncionalidadProtegible extends ObjetoPersistente {

    private String nombre;
    private boolean protegida;
    private boolean modificable;

    public FuncionalidadProtegible() {
        super();
    }

    private FuncionalidadProtegible(String nombre, boolean protegida, boolean modificable) {
        this.nombre = nombre;
        this.protegida = protegida;
        this.modificable = modificable;
        save();
    }

    public static List<FuncionalidadProtegible> obtenerTodos() {
        return listarTodos(FuncionalidadProtegible.class);
    }

    public void setProtegida(boolean protegida) {
        this.protegida = protegida;
    }

    public String getNombre() {
        return this.nombre;
    }

    public boolean estaProtegida() {
        return this.protegida;
    }

    public static FuncionalidadProtegible obtenerOCrear(String nombre, boolean protegida, boolean modificable) {

        FuncionalidadProtegible funcionalidadProtegible = ObjetoPersistente.encontrarPrimero(FuncionalidadProtegible.class, "nombre = ?", nombre);
        if (funcionalidadProtegible == null) {
            funcionalidadProtegible = new FuncionalidadProtegible(nombre, protegida, modificable);
        }
        return funcionalidadProtegible;
    }

    public boolean esModificable() {
        return this.modificable;
    }


    public static boolean estaProtegida(String nombreFuncionalidad){
        FuncionalidadProtegible funcionalidadProtegible = encontrarPrimero(FuncionalidadProtegible.class,"nombre = ?", nombreFuncionalidad);

        if (funcionalidadProtegible != null)
            return funcionalidadProtegible.protegida;
        else return false;

    }
}
