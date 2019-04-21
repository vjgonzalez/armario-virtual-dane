package ar.uba.fi.armariovirtual.modelo.filtros;

import ar.uba.fi.armariovirtual.modelo.OpcionClasificacion;

public class OpcionFiltro {private Long _idOpcion;
    private boolean _seleccionada;
    private String _nombre;

    public OpcionFiltro(OpcionClasificacion opcionClasificacion)
    {
        this._idOpcion = opcionClasificacion.getId();
        this._seleccionada = false;
        this._nombre = opcionClasificacion.getValor();
    }

    public Long getIdOpcion() {
        return _idOpcion;
    }

    public boolean isSeleccionada() {
        return _seleccionada;
    }

    public void setSeleccionada(boolean _seleccionada) {
        this._seleccionada = _seleccionada;
    }

    public String getNombre() {
        return _nombre;
    }
}
