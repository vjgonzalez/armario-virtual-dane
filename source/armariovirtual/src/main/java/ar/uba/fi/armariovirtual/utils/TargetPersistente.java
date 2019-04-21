package ar.uba.fi.armariovirtual.utils;

import com.squareup.picasso.Target;

import java.util.ArrayList;
import java.util.List;

/**
 * Mantiene la referencia a un Target al hacer una carga asíncrona, ya que Picasso no guarda referencias fuertes a los targets y son garbage collected.
 *
 * Al terminar de usar el target, llamar a finalizar()
 */
public abstract class TargetPersistente implements Target {

    private static List<TargetPersistente> targetsActivos = new ArrayList();

    public TargetPersistente()
    {
        targetsActivos.add(this);
    }

    public void finalizar()
    {
        targetsActivos.remove(this);
    }
}
