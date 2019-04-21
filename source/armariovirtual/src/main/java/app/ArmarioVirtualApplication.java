package app;

import android.content.Context;
import android.content.SharedPreferences;

import ar.uba.fi.armariovirtual.utils.TypefaceUtil;
import ar.uba.fi.utilidadesdane.app.DaneApplication;
import ar.uba.fi.utilidadesdane.audio.AudioFondo;


public class ArmarioVirtualApplication extends DaneApplication {

    private static final String PREF_INICIALIZACION = "ArmarioSetupPref";
    private static final String CODIGO_INICIALIZADO = "CodigoInicializado";
    private static Context applicationContext;

    @Override
    public void onCreate() {
        super.onCreate();
        SharedPreferences sharedPreferences = obtenerSharedPreferences(PREF_INICIALIZACION);
        InicializacionBD.setUpModelo(getApplicationContext());
        InicializacionBD.setUpConfiguracion(getApplicationContext());
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(CODIGO_INICIALIZADO, true);
        editor.commit();
        applicationContext = this;

        int [] recursosDeAudio = setUpRecursosDeAudio();

        AudioFondo.release();
        AudioFondo.setAudios(recursosDeAudio);
        try {
            AudioFondo.start(this, 0, false);
            AudioFondo.setSilencio(true);
        } catch (Exception e) {
            AudioFondo.setSilencio(true);
        }


        TypefaceUtil.overrideFont(getApplicationContext(), "SANS_SERIF", "fonts/3Dumb.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
        TypefaceUtil.overrideFont(getApplicationContext(), "SERIF", "fonts/Salsa-Regular.ttf"); // font from assets: "assets/fonts/Roboto-Regular.ttf
    }

    private int[] setUpRecursosDeAudio() {
        int audioMenu = getResources().getIdentifier("audio_general", "raw", getPackageName());
        int audioConfiguracion = getResources().getIdentifier("audio_general", "raw", getPackageName());
        int audioAprendoAVestirme = getResources().getIdentifier("audio_general", "raw", getPackageName());
        int audioVestuario = getResources().getIdentifier("audio_general", "raw", getPackageName());
        int audioDetallePrenda = getResources().getIdentifier("audio_general", "raw", getPackageName());
        int audioDetalleConjunto = getResources().getIdentifier("audio_general", "raw", getPackageName());
        int audioQueMePongo = getResources().getIdentifier("audio_general", "raw", getPackageName());
        int audioCalendario = getResources().getIdentifier("audio_general", "raw", getPackageName());
        int audioDetalleFecha = getResources().getIdentifier("audio_general", "raw", getPackageName());
        int audioDetalleEvento = getResources().getIdentifier("audio_general", "raw", getPackageName());

        return new int[]{audioMenu, audioConfiguracion, audioAprendoAVestirme, audioVestuario,
                audioDetallePrenda, audioDetalleConjunto, audioQueMePongo, audioCalendario,
                audioDetalleFecha, audioDetalleEvento};
    }

    public static Context getAppContext() {
        return applicationContext;
    }
}