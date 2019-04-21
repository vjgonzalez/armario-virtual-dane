package ar.uba.fi.armariovirtual.modelo;

import ar.uba.fi.utilidadesdane.persistencia.ObjetoPersistente;

public class OpcionClasificacion extends ObjetoPersistente {

    protected String valor;

    public OpcionClasificacion() {
        super();
    }

    public OpcionClasificacion(String valor){
        this.valor = valor;
        save();
    }

    public String getValor() {
        return valor;
    }

    public void setValor(String valor) {
        this.valor = valor;
    }

    public static OpcionClasificacion obtener(String valor)
    {
        return ObjetoPersistente.encontrarPrimero(OpcionClasificacion.class, "valor = ?", valor);
    }

    public static OpcionClasificacion obtenerOCrear(String valor) {

        OpcionClasificacion opcionClasificacion = obtener(valor);
        if(opcionClasificacion == null)
        {
            opcionClasificacion = new OpcionClasificacion(valor);
        }
        return  opcionClasificacion;
    }


}


