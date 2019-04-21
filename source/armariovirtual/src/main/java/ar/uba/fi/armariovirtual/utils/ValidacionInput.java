package ar.uba.fi.armariovirtual.utils;

import android.widget.Button;
import android.widget.EditText;

public class ValidacionInput {

    private static final String CAMPO_INCOMPLETO_MSJ = "POR FAVOR COMPLETA ESTE CAMPO";

    public static boolean tieneTexto(EditText editText) {
        String text = editText.getText().toString().trim();
        editText.setError(null);
        if (text.length() == 0) {
            editText.setError(CAMPO_INCOMPLETO_MSJ);
            return false;
        }
        return true;
    }

    public static void requerir(Button button){
        button.setError("");
    }
}
