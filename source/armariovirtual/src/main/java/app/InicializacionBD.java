package app;

import android.content.Context;
import android.util.Log;

import java.util.Calendar;
import java.util.List;

import ar.uba.fi.armariovirtual.R;
import ar.uba.fi.armariovirtual.configuracion.FuncionalidadProtegible;
import ar.uba.fi.armariovirtual.modelo.Clasificacion;
import ar.uba.fi.armariovirtual.modelo.Conjunto;
import ar.uba.fi.armariovirtual.modelo.DefinicionClasificacion;
import ar.uba.fi.armariovirtual.modelo.EventoCalendario;
import ar.uba.fi.armariovirtual.modelo.OpcionClasificacion;
import ar.uba.fi.armariovirtual.modelo.Prenda;
import ar.uba.fi.armariovirtual.utils.CodigoEmojis;
import ar.uba.fi.armariovirtual.utils.ICallback;
import ar.uba.fi.utilidadesdane.persistencia.ObjetoPersistente;

public class InicializacionBD {

    private static String opcionDia;
    private static String opcionNoche;
    private static String opcionCalor;
    private static String opcionFrio;
    private static String opcionTemplado;
    private static String opcionLluvia;
    private static String opcionAdentro;
    private static String opcionAfuera;
    private static String opcionCasa;
    private static String opcionEscuela;
    private static String opcionDeporte;
    private static String opcionTrabajo;
    private static String opcionFiesta;

    public static void setUpConfiguracion(Context contexto) {

        FuncionalidadProtegible.obtenerOCrear(contexto.getResources().getString(R.string.funcionalidad_editar_clasificaciones), true, false);

        FuncionalidadProtegible.obtenerOCrear(contexto.getResources().getString(R.string.funcionalidad_editar_prendas),true, true);
        FuncionalidadProtegible.obtenerOCrear(contexto.getResources().getString(R.string.funcionalidad_eliminar_prendas),true, true);
        FuncionalidadProtegible.obtenerOCrear(contexto.getResources().getString(R.string.funcionalidad_editar_conjuntos),true, true);
        FuncionalidadProtegible.obtenerOCrear(contexto.getResources().getString(R.string.funcionalidad_eliminar_conjuntos),true, true);

    }

    public static void setUpModelo(Context contexto) {

        opcionDia = contexto.getResources().getString(R.string.clasificacion_momento_del_dia_opcion_dia) + CodigoEmojis.DIA_EMOJI;
        opcionNoche = contexto.getResources().getString(R.string.clasificacion_momento_del_dia_opcion_noche) + CodigoEmojis.NOCHE_EMOJI;

        DefinicionClasificacion clasificacionMomentoDelDia = DefinicionClasificacion.obtenerOCrear(
                contexto.getResources().getString(R.string.clasificacion_momento_del_dia_nombre),
                contexto.getResources().getString(R.string.clasificacion_momento_del_dia_pregunta));
        OpcionClasificacion opcionMomentoDelDia_Dia = OpcionClasificacion.obtenerOCrear(opcionDia);
        OpcionClasificacion opcionMomentoDelDia_Noche = OpcionClasificacion.obtenerOCrear(opcionNoche);
        clasificacionMomentoDelDia.agregarOpcion(opcionMomentoDelDia_Dia);
        clasificacionMomentoDelDia.agregarOpcion(opcionMomentoDelDia_Noche);

        opcionCalor = contexto.getResources().getString(R.string.clasificacion_temperatura_opcion_calor);
        opcionFrio = contexto.getResources().getString(R.string.clasificacion_temperatura_opcion_frio);
        opcionTemplado = contexto.getResources().getString(R.string.clasificacion_temperatura_opcion_templado);
        opcionLluvia = contexto.getResources().getString(R.string.clasificacion_temperatura_opcion_lluvia) + CodigoEmojis.LLUVIA_EMOJI;

        DefinicionClasificacion clasificacionTemperatura = DefinicionClasificacion.obtenerOCrear(
                contexto.getResources().getString(R.string.clasificacion_temperatura_nombre),
                contexto.getResources().getString(R.string.clasificacion_temperatura_pregunta));
        OpcionClasificacion opcionTemperatura_Calor = OpcionClasificacion.obtenerOCrear(opcionCalor);
        OpcionClasificacion opcionTemperatura_Frio = OpcionClasificacion.obtenerOCrear(opcionFrio);
        OpcionClasificacion opcionTemperatura_Templado = OpcionClasificacion.obtenerOCrear(opcionTemplado);
        OpcionClasificacion opcionTemperatura_Lluvia = OpcionClasificacion.obtenerOCrear(opcionLluvia);
        clasificacionTemperatura.agregarOpcion(opcionTemperatura_Calor);
        clasificacionTemperatura.agregarOpcion(opcionTemperatura_Frio);
        clasificacionTemperatura.agregarOpcion(opcionTemperatura_Templado);
        clasificacionTemperatura.agregarOpcion(opcionTemperatura_Lluvia);

        opcionAdentro = contexto.getResources().getString(R.string.clasificacion_lugar_opcion_adentro);
        opcionAfuera = contexto.getResources().getString(R.string.clasificacion_lugar_opcion_afuera);

        DefinicionClasificacion clasificacionLugar = DefinicionClasificacion.obtenerOCrear(
                contexto.getResources().getString(R.string.clasificacion_lugar_nombre),
                contexto.getResources().getString(R.string.clasificacion_lugar_pregunta));
        OpcionClasificacion opcionLugar_Adentro = OpcionClasificacion.obtenerOCrear(opcionAdentro);
        OpcionClasificacion opcionLugar_Afuera = OpcionClasificacion.obtenerOCrear(opcionAfuera);
        clasificacionLugar.agregarOpcion(opcionLugar_Adentro);
        clasificacionLugar.agregarOpcion(opcionLugar_Afuera);

        opcionCasa = contexto.getResources().getString(R.string.clasificacion_utilidad_opcion_casa) + CodigoEmojis.CASA_EMOJI;
        opcionEscuela = contexto.getResources().getString(R.string.clasificacion_utilidad_opcion_escuela) + CodigoEmojis.ESCUELA_EMOJI_1 + CodigoEmojis.ESCUELA_EMOJI_2;
        opcionDeporte = contexto.getResources().getString(R.string.clasificacion_utilidad_opcion_deporte) + CodigoEmojis.DEPORTE_EMOJI_1 + CodigoEmojis.DEPORTE_EMOJI_2;
        opcionTrabajo = contexto.getResources().getString(R.string.clasificacion_utilidad_opcion_trabajo);
        opcionFiesta = contexto.getResources().getString(R.string.clasificacion_utilidad_opcion_fiesta);

        DefinicionClasificacion clasificacionUtilidad = DefinicionClasificacion.obtenerOCrear(
                contexto.getResources().getString(R.string.clasificacion_utilidad_nombre),
                contexto.getResources().getString(R.string.clasificacion_utilidad_pregunta));
        OpcionClasificacion opcionLugar_Casa = OpcionClasificacion.obtenerOCrear(opcionCasa);
        OpcionClasificacion opcionLugar_Escuela = OpcionClasificacion.obtenerOCrear(opcionEscuela);
        OpcionClasificacion opcionLugar_Deporte = OpcionClasificacion.obtenerOCrear(opcionDeporte);
        OpcionClasificacion opcionLugar_Trabajo = OpcionClasificacion.obtenerOCrear(opcionTrabajo);
        OpcionClasificacion opcionLugar_Fiesta = OpcionClasificacion.obtenerOCrear(opcionFiesta);
        clasificacionUtilidad.agregarOpcion(opcionLugar_Casa);
        clasificacionUtilidad.agregarOpcion(opcionLugar_Escuela);
        clasificacionUtilidad.agregarOpcion(opcionLugar_Deporte);
        clasificacionUtilidad.agregarOpcion(opcionLugar_Trabajo);
        clasificacionUtilidad.agregarOpcion(opcionLugar_Fiesta);

        // Crear opciones especiales para Qué me pongo
        OpcionClasificacion.obtenerOCrear(contexto.getResources().getString(R.string.clasificacion_qmp_indistinto)).save();
        OpcionClasificacion.obtenerOCrear(contexto.getResources().getString(R.string.clasificacion_qmp_no_se)).save();
    }

    /**
     * Eliminar registros "zombie" que hayan quedado inaccesibles para el modelo
     *
     * @param contexto
     */
    public static void sanitizarBD(Context contexto) {
//TODO validar si se puede eliminar este metodo
        // Clasificaciones no asociadas a una Prenda
        List<Clasificacion> clasificaciones = ObjetoPersistente.listarTodos(Clasificacion.class);
        for (Clasificacion clasificacion : clasificaciones) {
            boolean esZombie = false;
            List<Prenda> prendasAsociadas = clasificacion.obtenerRelaciones(Prenda.class);
            List<Conjunto> conjuntosAsociados = clasificacion.obtenerRelaciones(Conjunto.class);
            if ( (prendasAsociadas == null || prendasAsociadas.size() == 0) && (conjuntosAsociados == null || conjuntosAsociados.size() == 0) ) {
                // Clasificación "zombie". Eliminarla junto con todas sus opciones
                clasificacion.quitarTodasLasOpcionesElegidas();
                clasificacion.delete();
            }
        }
    }

    /**
     * Dar de alta prendas y conjuntos por defecto
     */
    public static void crearDatosPorDefecto(Context contexto) {
        // TODO: Agregar imágenes

        // =================================
        //              PRENDAS
        // =================================

        // Remera
        Prenda remera = new Prenda("REMERA", "");
        remera.setRutaImagen("file:///android_asset/prendas_precargadas/remera.png");

        Clasificacion clasificacionRemeraMomentoDelDia = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_momento_del_dia_nombre)));
        clasificacionRemeraMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionDia));
        remera.agregarClasificacion(clasificacionRemeraMomentoDelDia);

        Clasificacion clasificacionRemeraTemperatura = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_temperatura_nombre)));
        clasificacionRemeraTemperatura.agregarOpcionElegida(OpcionClasificacion.obtener(opcionCalor));
        remera.agregarClasificacion(clasificacionRemeraTemperatura);

        remera.save();

        // Bermuda
        Prenda bermuda = new Prenda("BERMUDA", "");
        bermuda.setRutaImagen("file:///android_asset/prendas_precargadas/bermuda.png");

        Clasificacion clasificacionBermudaMomentoDelDia = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_momento_del_dia_nombre)));
        clasificacionBermudaMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionDia));
        bermuda.agregarClasificacion(clasificacionBermudaMomentoDelDia);

        Clasificacion clasificacionBermudaTemperatura = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_temperatura_nombre)));
        clasificacionBermudaTemperatura.agregarOpcionElegida(OpcionClasificacion.obtener(opcionCalor));
        bermuda.agregarClasificacion(clasificacionBermudaTemperatura);

        bermuda.save();

        // Pantalon
        Prenda pantalon = new Prenda("PANTALON", "");
        pantalon.setRutaImagen("file:///android_asset/prendas_precargadas/pantalon.png");

        Clasificacion clasificacionPantalonMomentoDelDia = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_momento_del_dia_nombre)));
        clasificacionPantalonMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionDia));
        clasificacionPantalonMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionNoche));
        pantalon.agregarClasificacion(clasificacionPantalonMomentoDelDia);

        Clasificacion clasificacionPantalonTemperatura = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_temperatura_nombre)));
        clasificacionPantalonTemperatura.agregarOpcionElegida(OpcionClasificacion.obtener(opcionFrio));
        pantalon.agregarClasificacion(clasificacionPantalonTemperatura);

        pantalon.save();

        // Campera
        Prenda campera = new Prenda("CAMPERA", "");
        campera.setRutaImagen("file:///android_asset/prendas_precargadas/campera.png");

        Clasificacion clasificacionCamperaMomentoDelDia = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_momento_del_dia_nombre)));
        clasificacionCamperaMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionDia));
        clasificacionCamperaMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionNoche));
        campera.agregarClasificacion(clasificacionCamperaMomentoDelDia);

        Clasificacion clasificacionCamperaTemperatura = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_temperatura_nombre)));
        clasificacionCamperaTemperatura.agregarOpcionElegida(OpcionClasificacion.obtener(opcionFrio));
        campera.agregarClasificacion(clasificacionCamperaTemperatura);

        campera.save();

        // Corbata
        Prenda corbata = new Prenda("CORBATA", "");
        corbata.setRutaImagen("file:///android_asset/prendas_precargadas/corbata.png");

        Clasificacion clasificacionCorbataMomentoDelDia = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_momento_del_dia_nombre)));
        clasificacionCorbataMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionDia));
        clasificacionCorbataMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionNoche));
        corbata.agregarClasificacion(clasificacionCorbataMomentoDelDia);

        Clasificacion clasificacionCorbataTemperatura = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_temperatura_nombre)));
        clasificacionCorbataTemperatura.agregarOpcionElegida(OpcionClasificacion.obtener(opcionFrio));
        corbata.agregarClasificacion(clasificacionCorbataTemperatura);

        corbata.save();

        // Cinturon
        Prenda cinturon = new Prenda("CINTURON", "");
        cinturon.setRutaImagen("file:///android_asset/prendas_precargadas/cinturon.png");

        Clasificacion clasificacionCinturonMomentoDelDia = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_momento_del_dia_nombre)));
        clasificacionCinturonMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionDia));
        clasificacionCinturonMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionNoche));
        cinturon.agregarClasificacion(clasificacionCinturonMomentoDelDia);

        Clasificacion clasificacionCinturonTemperatura = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_temperatura_nombre)));
        clasificacionCinturonTemperatura.agregarOpcionElegida(OpcionClasificacion.obtener(opcionFrio));
        cinturon.agregarClasificacion(clasificacionCinturonTemperatura);

        cinturon.save();

        Prenda vestido = new Prenda("VESTIDO", "");
        vestido.setRutaImagen("file:///android_asset/prendas_precargadas/vestido.png");

        Clasificacion clasificacionVestidoMomentoDelDia = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_momento_del_dia_nombre)));
        clasificacionVestidoMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionDia));
        clasificacionVestidoMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionNoche));
        vestido.agregarClasificacion(clasificacionVestidoMomentoDelDia);

        Clasificacion clasificacionVestidoTemperatura = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_temperatura_nombre)));
        clasificacionVestidoTemperatura.agregarOpcionElegida(OpcionClasificacion.obtener(opcionCalor));
        vestido.agregarClasificacion(clasificacionVestidoTemperatura);

        vestido.save();


        // =================================
        //          CONJUNTOS
        // =================================

        // Verano
        Conjunto conjuntoVerano = new Conjunto("VERANO");
        conjuntoVerano.agregarPrenda(remera);
        conjuntoVerano.agregarPrenda(bermuda);

        Clasificacion clasificacionConjuntoVeranoTemperatura = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_temperatura_nombre)));
        clasificacionConjuntoVeranoTemperatura.agregarOpcionElegida(OpcionClasificacion.obtener(opcionCalor));
        conjuntoVerano.agregarClasificacion(clasificacionConjuntoVeranoTemperatura);

        conjuntoVerano.save();

        // Invierno
        Conjunto conjuntoInvierno = new Conjunto("INVIERNO");
        conjuntoInvierno.agregarPrenda(pantalon);
        conjuntoInvierno.agregarPrenda(campera);

        Clasificacion clasificacionConjuntoInviernoTemperatura = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_temperatura_nombre)));
        clasificacionConjuntoInviernoTemperatura.agregarOpcionElegida(OpcionClasificacion.obtener(opcionFrio));
        conjuntoInvierno.agregarClasificacion(clasificacionConjuntoInviernoTemperatura);

        conjuntoInvierno.save();

        // Completo
        Conjunto conjuntoCompleto = new Conjunto("COMPLETO");
        conjuntoCompleto.agregarPrenda(remera);
        conjuntoCompleto.agregarPrenda(bermuda);
        conjuntoCompleto.agregarPrenda(pantalon);
        conjuntoCompleto.agregarPrenda(campera);

        Clasificacion clasificacionConjuntoCompletoTemperatura = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_temperatura_nombre)));
        clasificacionConjuntoCompletoTemperatura.agregarOpcionElegida(OpcionClasificacion.obtener(opcionFrio));
        clasificacionConjuntoCompletoTemperatura.agregarOpcionElegida(OpcionClasificacion.obtener(opcionCalor));
        conjuntoCompleto.agregarClasificacion(clasificacionConjuntoCompletoTemperatura);

        Clasificacion clasificacionConjuntoCompletoMomentoDelDia = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_momento_del_dia_nombre)));
        clasificacionConjuntoCompletoMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionDia));
        clasificacionConjuntoCompletoMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionNoche));
        conjuntoCompleto.agregarClasificacion(clasificacionConjuntoCompletoMomentoDelDia);

        conjuntoCompleto.save();

        for (int i = 0; i < 10; i++) {
            // Conjunto placeholder
            Conjunto conjuntoPlaceholder = new Conjunto("CONJUNTO " + (i + 1));
            conjuntoPlaceholder.agregarPrenda(remera);
            conjuntoPlaceholder.agregarPrenda(bermuda);

            Clasificacion clasificacionConjuntoPlaceholderemperatura = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_temperatura_nombre)));
            clasificacionConjuntoPlaceholderemperatura.agregarOpcionElegida(OpcionClasificacion.obtener(opcionCalor));
            conjuntoPlaceholder.agregarClasificacion(clasificacionConjuntoPlaceholderemperatura);

            conjuntoPlaceholder.save();
        }


        actualizarImagenesConjuntos(); // En un método aparte porque tiene que ejecutarse asíncrono y en serie


        // =================================
        //          EVENTOS
        // =================================

        // Navidad
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 23);
        calendar.set(Calendar.MINUTE, 59);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.YEAR, 2018);
        calendar.set(Calendar.MONTH, Calendar.DECEMBER);
        calendar.set(Calendar.DATE, 24);
        EventoCalendario navidad = new EventoCalendario(calendar.getTime(), conjuntoVerano, "NAVIDAD");
        navidad.save();
    }

    private static void actualizarImagenesConjuntos() {

        Log.d("[CONJUNTO]", "InicializacionDB - actualizarImagenesConjuntos");
        List<Conjunto> conjuntos = ObjetoPersistente.listarTodos(Conjunto.class);

        for (int i = 0; i < conjuntos.size(); i++) {
            Conjunto conjunto = conjuntos.get(i);
            Log.d("[CONJUNTO]", "InicializacionDB - Conjunto [" + conjunto.getId() + "].rutaImagen: " + conjunto.getRutaImagen());
            if (conjunto.getRutaImagen() == null) {
                // Imagen no generada
                conjunto.actualizarImagen(new ICallback() {
                    @Override
                    public void onSuccess() {
                        Log.d("[CONJUNTO]", "actualizarImagen.onSuccess callback");
                        actualizarImagenesConjuntos();
                    }
                });
                return;
            }
        }
    }
}
