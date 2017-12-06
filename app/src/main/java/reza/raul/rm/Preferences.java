package reza.raul.rm;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Android on 01/11/2017.
 */

public class Preferences {
    public static final String STRING_PREFERENCES = "michattimereal.Mensajes.Mensajeria";
    public static final String PREFERENCE_ESTADO_BUTTON_SESION = "estado.button.sesion";
    public static final String PREFERENCE_USUARIO_LOGIN = "usuario.login";
    public static final String PREFERENCE_USUARIO_TIPO = "usuario.login.tipo";

    public static void savePreferenceBoolean(Context c, boolean b, String key) {
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, c.MODE_PRIVATE);
        preferences.edit().putBoolean(key, b).apply();
    }

    public static void savePreferenceString(Context c, String b, String key) {
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, c.MODE_PRIVATE);
        preferences.edit().putString(key, b).apply();
    }

    public static void savePreferencesTipo(Context c, String k, String key){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, c.MODE_PRIVATE);
        preferences.edit().putString(key, k).apply();
    }

    public static boolean obtenerPreferenceBoolean(Context c, String key) {
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, c.MODE_PRIVATE);
        return preferences.getBoolean(key, false);//Si es que nunca se ha guardado nada en esta key pues retornara false
    }

    public static String obtenerPreferenceString(Context c, String key) {
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, c.MODE_PRIVATE);
        return preferences.getString(key, "");//Si es que nunca se ha guardado nada en esta key pues retornara una cadena vacia
    }

    public static String obtenerPreferenceTipo(Context c, String key){
        SharedPreferences preferences = c.getSharedPreferences(STRING_PREFERENCES, c.MODE_PRIVATE);
        return preferences.getString(key, "");//En caso de no haber sido guardado
    }
}
