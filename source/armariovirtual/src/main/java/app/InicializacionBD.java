package app;

import android.content.Context;

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

    static void setUpConfiguracion(Context contexto) {

        FuncionalidadProtegible.obtenerOCrear(contexto.getResources().getString(R.string.funcionalidad_editar_clasificaciones), true, false);

        FuncionalidadProtegible.obtenerOCrear(contexto.getResources().getString(R.string.funcionalidad_editar_prendas), true, true);
        FuncionalidadProtegible.obtenerOCrear(contexto.getResources().getString(R.string.funcionalidad_eliminar_prendas), true, true);
        FuncionalidadProtegible.obtenerOCrear(contexto.getResources().getString(R.string.funcionalidad_editar_conjuntos), true, true);
        FuncionalidadProtegible.obtenerOCrear(contexto.getResources().getString(R.string.funcionalidad_eliminar_conjuntos), true, true);

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
            if ((prendasAsociadas == null || prendasAsociadas.size() == 0) && (conjuntosAsociados == null || conjuntosAsociados.size() == 0)) {
                // Clasificación "zombie". Eliminarla junto con todas sus opciones
                clasificacion.quitarTodasLasOpcionesElegidas();
                clasificacion.delete();
            }
        }
    }

    /**
     * Dar de alta prendas y conjuntos por defecto
     */
    static void crearDatosPorDefecto(Context contexto) {

        // Si ya existen prendas no creamos prendas por defecto
        if (!Prenda.vacia())
            return;

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
        clasificacionRemeraTemperatura.agregarOpcionElegida(OpcionClasificacion.obtener(opcionTemplado));
        remera.agregarClasificacion(clasificacionRemeraTemperatura);

        Clasificacion clasificacionRemeraLugar = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_lugar_nombre)));
        clasificacionRemeraLugar.agregarOpcionElegida(OpcionClasificacion.obtener(opcionAfuera));
        clasificacionRemeraLugar.agregarOpcionElegida(OpcionClasificacion.obtener(opcionAdentro));
        remera.agregarClasificacion(clasificacionRemeraLugar);

        Clasificacion clasificacionRemeraUtilidad = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_utilidad_nombre)));
        clasificacionRemeraUtilidad.agregarOpcionElegida(OpcionClasificacion.obtener(opcionCasa));
        clasificacionRemeraUtilidad.agregarOpcionElegida(OpcionClasificacion.obtener(opcionEscuela));
        clasificacionRemeraUtilidad.agregarOpcionElegida(OpcionClasificacion.obtener(opcionDeporte));
        remera.agregarClasificacion(clasificacionRemeraUtilidad);

        remera.setFavorito(true);
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

        Clasificacion clasificacionBermudaLugar = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_lugar_nombre)));
        clasificacionBermudaLugar.agregarOpcionElegida(OpcionClasificacion.obtener(opcionAfuera));
        clasificacionBermudaLugar.agregarOpcionElegida(OpcionClasificacion.obtener(opcionAdentro));
        bermuda.agregarClasificacion(clasificacionBermudaLugar);

        Clasificacion clasificacionBermudaUtilidad = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_utilidad_nombre)));
        clasificacionBermudaUtilidad.agregarOpcionElegida(OpcionClasificacion.obtener(opcionCasa));
        bermuda.agregarClasificacion(clasificacionBermudaUtilidad);

        bermuda.save();

        // Buzo
        Prenda buzo = new Prenda("BUZO", "");
        buzo.setRutaImagen("file:///android_asset/prendas_precargadas/buzo.png");

        Clasificacion clasificacionBuzoMomentoDelDia = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_momento_del_dia_nombre)));
        clasificacionBuzoMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionDia));
        clasificacionBuzoMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionNoche));
        buzo.agregarClasificacion(clasificacionBuzoMomentoDelDia);

        Clasificacion clasificacionBuzoTemperatura = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_temperatura_nombre)));
        clasificacionBuzoTemperatura.agregarOpcionElegida(OpcionClasificacion.obtener(opcionTemplado));
        clasificacionBuzoTemperatura.agregarOpcionElegida(OpcionClasificacion.obtener(opcionFrio));
        buzo.agregarClasificacion(clasificacionBuzoTemperatura);

        Clasificacion clasificacionBuzoLugar = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_lugar_nombre)));
        clasificacionBuzoLugar.agregarOpcionElegida(OpcionClasificacion.obtener(opcionAfuera));
        clasificacionBuzoLugar.agregarOpcionElegida(OpcionClasificacion.obtener(opcionAdentro));
        buzo.agregarClasificacion(clasificacionBuzoLugar);

        Clasificacion clasificacionBuzoUtilidad = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_utilidad_nombre)));
        clasificacionBuzoUtilidad.agregarOpcionElegida(OpcionClasificacion.obtener(opcionCasa));
        clasificacionBuzoUtilidad.agregarOpcionElegida(OpcionClasificacion.obtener(opcionEscuela));
        clasificacionBuzoUtilidad.agregarOpcionElegida(OpcionClasificacion.obtener(opcionDeporte));
        buzo.agregarClasificacion(clasificacionBuzoUtilidad);

        buzo.save();


        // Paraguas
        Prenda paraguas = new Prenda("PARAGUAS", "");
        paraguas.setRutaImagen("file:///android_asset/prendas_precargadas/paraguas.png");

        Clasificacion clasificacionParaguasMomentoDelDia = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_momento_del_dia_nombre)));
        clasificacionParaguasMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionDia));
        clasificacionParaguasMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionNoche));
        paraguas.agregarClasificacion(clasificacionParaguasMomentoDelDia);

        Clasificacion clasificacionParaguasTemperatura = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_temperatura_nombre)));
        clasificacionParaguasTemperatura.agregarOpcionElegida(OpcionClasificacion.obtener(opcionLluvia));
        paraguas.agregarClasificacion(clasificacionParaguasTemperatura);

        Clasificacion clasificacionParaguasLugar = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_lugar_nombre)));
        clasificacionParaguasLugar.agregarOpcionElegida(OpcionClasificacion.obtener(opcionAfuera));
        paraguas.agregarClasificacion(clasificacionParaguasLugar);

        paraguas.save();

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

        Clasificacion clasificacionCamperaLugar = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_lugar_nombre)));
        clasificacionCamperaLugar.agregarOpcionElegida(OpcionClasificacion.obtener(opcionAfuera));
        campera.agregarClasificacion(clasificacionCamperaLugar);

        Clasificacion clasificacionCamperaUtilidad = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_utilidad_nombre)));
        clasificacionCamperaUtilidad.agregarOpcionElegida(OpcionClasificacion.obtener(opcionEscuela));
        campera.agregarClasificacion(clasificacionCamperaUtilidad);

        campera.save();

        // Ojotas
        Prenda ojotas = new Prenda("OJOTAS", "");
        ojotas.setRutaImagen("file:///android_asset/prendas_precargadas/ojotas.png");

        Clasificacion clasificacionOjotasMomentoDelDia = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_momento_del_dia_nombre)));
        clasificacionOjotasMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionDia));
        ojotas.agregarClasificacion(clasificacionOjotasMomentoDelDia);

        Clasificacion clasificacionOjotasTemperatura = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_temperatura_nombre)));
        clasificacionOjotasTemperatura.agregarOpcionElegida(OpcionClasificacion.obtener(opcionCalor));
        ojotas.agregarClasificacion(clasificacionOjotasTemperatura);

        Clasificacion clasificacionOjotasLugar = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_lugar_nombre)));
        clasificacionOjotasLugar.agregarOpcionElegida(OpcionClasificacion.obtener(opcionAfuera));
        clasificacionOjotasLugar.agregarOpcionElegida(OpcionClasificacion.obtener(opcionAdentro));
        ojotas.agregarClasificacion(clasificacionOjotasLugar);

        Clasificacion clasificacionOjotasUtilidad = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_utilidad_nombre)));
        clasificacionOjotasUtilidad.agregarOpcionElegida(OpcionClasificacion.obtener(opcionCasa));
        ojotas.agregarClasificacion(clasificacionOjotasUtilidad);

        ojotas.save();

        // Chomba
        Prenda chomba = new Prenda("CHOMBA", "");
        chomba.setRutaImagen("file:///android_asset/prendas_precargadas/chomba.png");

        Clasificacion clasificacionChombaMomentoDelDia = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_momento_del_dia_nombre)));
        clasificacionChombaMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionDia));
        clasificacionChombaMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionNoche));
        chomba.agregarClasificacion(clasificacionChombaMomentoDelDia);

        Clasificacion clasificacionChombaTemperatura = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_temperatura_nombre)));
        clasificacionChombaTemperatura.agregarOpcionElegida(OpcionClasificacion.obtener(opcionFrio));
        chomba.agregarClasificacion(clasificacionChombaTemperatura);

        Clasificacion clasificacionChombaLugar = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_lugar_nombre)));
        clasificacionChombaLugar.agregarOpcionElegida(OpcionClasificacion.obtener(opcionAfuera));
        clasificacionChombaLugar.agregarOpcionElegida(OpcionClasificacion.obtener(opcionAdentro));
        chomba.agregarClasificacion(clasificacionChombaLugar);

        Clasificacion clasificacionChombaUtilidad = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_utilidad_nombre)));
        clasificacionChombaUtilidad.agregarOpcionElegida(OpcionClasificacion.obtener(opcionCasa));
        clasificacionChombaUtilidad.agregarOpcionElegida(OpcionClasificacion.obtener(opcionEscuela));
        chomba.agregarClasificacion(clasificacionChombaUtilidad);

        chomba.save();

        // Jean
        Prenda jean = new Prenda("JEAN", "");
        jean.setRutaImagen("file:///android_asset/prendas_precargadas/jean.jpeg");

        Clasificacion clasificacionJeanMomentoDelDia = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_momento_del_dia_nombre)));
        clasificacionJeanMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionDia));
        clasificacionJeanMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionNoche));
        jean.agregarClasificacion(clasificacionJeanMomentoDelDia);

        Clasificacion clasificacionJeanTemperatura = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_temperatura_nombre)));
        clasificacionJeanTemperatura.agregarOpcionElegida(OpcionClasificacion.obtener(opcionFrio));
        clasificacionJeanTemperatura.agregarOpcionElegida(OpcionClasificacion.obtener(opcionTemplado));
        jean.agregarClasificacion(clasificacionJeanTemperatura);

        Clasificacion clasificacionJeanLugar = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_lugar_nombre)));
        clasificacionJeanLugar.agregarOpcionElegida(OpcionClasificacion.obtener(opcionAfuera));
        clasificacionJeanLugar.agregarOpcionElegida(OpcionClasificacion.obtener(opcionAdentro));
        jean.agregarClasificacion(clasificacionJeanLugar);

        Clasificacion clasificacionJeanUtilidad = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_utilidad_nombre)));
        clasificacionJeanUtilidad.agregarOpcionElegida(OpcionClasificacion.obtener(opcionCasa));
        clasificacionJeanUtilidad.agregarOpcionElegida(OpcionClasificacion.obtener(opcionEscuela));
        jean.agregarClasificacion(clasificacionJeanUtilidad);

        jean.save();

        // Medias
        Prenda medias = new Prenda("MEDIAS", "");
        medias.setRutaImagen("file:///android_asset/prendas_precargadas/medias.png");

        Clasificacion clasificacionMediasMomentoDelDia = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_momento_del_dia_nombre)));
        clasificacionMediasMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionDia));
        clasificacionMediasMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionNoche));
        medias.agregarClasificacion(clasificacionMediasMomentoDelDia);

        Clasificacion clasificacionMediasTemperatura = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_temperatura_nombre)));
        clasificacionMediasTemperatura.agregarOpcionElegida(OpcionClasificacion.obtener(opcionFrio));
        medias.agregarClasificacion(clasificacionMediasTemperatura);

        Clasificacion clasificacionMediasLugar = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_lugar_nombre)));
        clasificacionMediasLugar.agregarOpcionElegida(OpcionClasificacion.obtener(opcionAfuera));
        clasificacionMediasLugar.agregarOpcionElegida(OpcionClasificacion.obtener(opcionAdentro));
        medias.agregarClasificacion(clasificacionMediasLugar);

        Clasificacion clasificacionMediasUtilidad = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_utilidad_nombre)));
        clasificacionMediasUtilidad.agregarOpcionElegida(OpcionClasificacion.obtener(opcionCasa));
        clasificacionMediasUtilidad.agregarOpcionElegida(OpcionClasificacion.obtener(opcionEscuela));
        clasificacionMediasUtilidad.agregarOpcionElegida(OpcionClasificacion.obtener(opcionDeporte));
        medias.agregarClasificacion(clasificacionMediasUtilidad);

        medias.save();

        // Pantalon
        Prenda pantalon = new Prenda("PANTALON", "");
        pantalon.setRutaImagen("file:///android_asset/prendas_precargadas/pantalon.png");

        Clasificacion clasificacionPantalonMomentoDelDia = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_momento_del_dia_nombre)));
        clasificacionPantalonMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionDia));
        clasificacionPantalonMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionNoche));
        pantalon.agregarClasificacion(clasificacionPantalonMomentoDelDia);

        Clasificacion clasificacionPantalonTemperatura = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_temperatura_nombre)));
        clasificacionPantalonTemperatura.agregarOpcionElegida(OpcionClasificacion.obtener(opcionTemplado));
        pantalon.agregarClasificacion(clasificacionPantalonTemperatura);

        Clasificacion clasificacionPantalonLugar = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_lugar_nombre)));
        clasificacionPantalonLugar.agregarOpcionElegida(OpcionClasificacion.obtener(opcionAfuera));
        clasificacionPantalonLugar.agregarOpcionElegida(OpcionClasificacion.obtener(opcionAdentro));
        pantalon.agregarClasificacion(clasificacionPantalonLugar);

        Clasificacion clasificacionPantalonUtilidad = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_utilidad_nombre)));
        clasificacionPantalonUtilidad.agregarOpcionElegida(OpcionClasificacion.obtener(opcionCasa));
        clasificacionPantalonUtilidad.agregarOpcionElegida(OpcionClasificacion.obtener(opcionEscuela));
        pantalon.agregarClasificacion(clasificacionPantalonUtilidad);

        pantalon.save();


        // Remera manga larga
        Prenda remeraLarga = new Prenda("REMERA MANGA LARGA", "");
        remeraLarga.setRutaImagen("file:///android_asset/prendas_precargadas/remera_larga.png");

        Clasificacion clasificacionRemeraLargaMomentoDelDia = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_momento_del_dia_nombre)));
        clasificacionRemeraLargaMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionDia));
        clasificacionRemeraLargaMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionNoche));
        remeraLarga.agregarClasificacion(clasificacionRemeraLargaMomentoDelDia);

        Clasificacion clasificacionRemeraLargaTemperatura = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_temperatura_nombre)));
        clasificacionRemeraLargaTemperatura.agregarOpcionElegida(OpcionClasificacion.obtener(opcionFrio));
        remeraLarga.agregarClasificacion(clasificacionRemeraLargaTemperatura);

        Clasificacion clasificacionRemeraLargaLugar = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_lugar_nombre)));
        clasificacionRemeraLargaLugar.agregarOpcionElegida(OpcionClasificacion.obtener(opcionAfuera));
        clasificacionRemeraLargaLugar.agregarOpcionElegida(OpcionClasificacion.obtener(opcionAdentro));
        remeraLarga.agregarClasificacion(clasificacionRemeraLargaLugar);

        Clasificacion clasificacionRemeraLargaUtilidad = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_utilidad_nombre)));
        clasificacionRemeraLargaUtilidad.agregarOpcionElegida(OpcionClasificacion.obtener(opcionCasa));
        clasificacionRemeraLargaUtilidad.agregarOpcionElegida(OpcionClasificacion.obtener(opcionEscuela));
        clasificacionRemeraLargaUtilidad.agregarOpcionElegida(OpcionClasificacion.obtener(opcionDeporte));
        remeraLarga.agregarClasificacion(clasificacionRemeraLargaUtilidad);

        remeraLarga.save();

        // Zapatillas
        Prenda zapatillas = new Prenda("ZAPATILLAS", "");
        zapatillas.setRutaImagen("file:///android_asset/prendas_precargadas/zapatillas.png");

        Clasificacion clasificacionZapatillasMomentoDelDia = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_momento_del_dia_nombre)));
        clasificacionZapatillasMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionDia));
        clasificacionZapatillasMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionNoche));
        zapatillas.agregarClasificacion(clasificacionZapatillasMomentoDelDia);

        Clasificacion clasificacionZapatillasTemperatura = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_temperatura_nombre)));
        clasificacionZapatillasTemperatura.agregarOpcionElegida(OpcionClasificacion.obtener(opcionFrio));
        zapatillas.agregarClasificacion(clasificacionZapatillasTemperatura);

        Clasificacion clasificacionZapatillasLugar = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_lugar_nombre)));
        clasificacionZapatillasLugar.agregarOpcionElegida(OpcionClasificacion.obtener(opcionAfuera));
        zapatillas.agregarClasificacion(clasificacionZapatillasLugar);

        Clasificacion clasificacionZapatillasUtilidad = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_utilidad_nombre)));
        clasificacionZapatillasUtilidad.agregarOpcionElegida(OpcionClasificacion.obtener(opcionEscuela));
        zapatillas.agregarClasificacion(clasificacionZapatillasUtilidad);

        zapatillas.setFavorito(true);
        zapatillas.save();

        // =================================
        //          CONJUNTOS
        // =================================

        // Verano
        Conjunto conjuntoVerano = new Conjunto("VERANO");
        conjuntoVerano.agregarPrenda(ojotas);
        conjuntoVerano.agregarPrenda(bermuda);
        conjuntoVerano.agregarPrenda(remera);

        Clasificacion clasificacionConjuntoVeranoMomentoDia = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_momento_del_dia_nombre)));
        clasificacionConjuntoVeranoMomentoDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionDia));
        conjuntoVerano.agregarClasificacion(clasificacionConjuntoVeranoMomentoDia);

        Clasificacion clasificacionConjuntoVeranoTemperatura = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_temperatura_nombre)));
        clasificacionConjuntoVeranoTemperatura.agregarOpcionElegida(OpcionClasificacion.obtener(opcionCalor));
        conjuntoVerano.agregarClasificacion(clasificacionConjuntoVeranoTemperatura);

        conjuntoVerano.save();

        // Invierno
        Conjunto conjuntoInvierno = new Conjunto("INVIERNO");
        conjuntoInvierno.agregarPrenda(zapatillas);
        conjuntoInvierno.agregarPrenda(buzo);
        conjuntoInvierno.agregarPrenda(jean);
        conjuntoInvierno.agregarPrenda(medias);
        conjuntoInvierno.agregarPrenda(campera);
        conjuntoInvierno.agregarPrenda(remeraLarga);

        Clasificacion clasificacionConjuntoInviernoTemperatura = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_temperatura_nombre)));
        clasificacionConjuntoInviernoTemperatura.agregarOpcionElegida(OpcionClasificacion.obtener(opcionFrio));
        conjuntoInvierno.agregarClasificacion(clasificacionConjuntoInviernoTemperatura);

        Clasificacion clasificacionConjuntoInviernoMomentoDelDia = new Clasificacion(DefinicionClasificacion.obtener(contexto.getResources().getString(R.string.clasificacion_momento_del_dia_nombre)));
        clasificacionConjuntoInviernoMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionDia));
        clasificacionConjuntoInviernoMomentoDelDia.agregarOpcionElegida(OpcionClasificacion.obtener(opcionNoche));
        conjuntoInvierno.agregarClasificacion(clasificacionConjuntoInviernoMomentoDelDia);

        conjuntoInvierno.save();

        actualizarImagenesConjuntos(); // En un método aparte porque tiene que ejecutarse asíncrono y en serie


        // =================================
        //          EVENTOS
        // =================================

        // Cumpleaños
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 15);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.YEAR, 2019);
        calendar.set(Calendar.MONTH, Calendar.MAY);
        calendar.set(Calendar.DATE, 25);
        EventoCalendario cumple = new EventoCalendario(calendar.getTime(), conjuntoVerano, "CUMPLEAÑOS ALFREDO");
        cumple.save();
    }

    private static void actualizarImagenesConjuntos() {

        List<Conjunto> conjuntos = ObjetoPersistente.listarTodos(Conjunto.class);

        for (int i = 0; i < conjuntos.size(); i++) {
            Conjunto conjunto = conjuntos.get(i);
            if (conjunto.getRutaImagen() == null) {
                // Imagen no generada
                conjunto.actualizarImagen(new ICallback() {
                    @Override
                    public void onSuccess() {
                        actualizarImagenesConjuntos();
                    }
                });
                return;
            }
        }
    }
}
