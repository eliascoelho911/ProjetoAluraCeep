package br.com.eliascoelho911.ceep.util.sharedpreferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

abstract class SharedPreferencesUtil {
    SharedPreferences preferenciasDoUsuario;
    SharedPreferences.Editor editarPreferenciasDoUsuario;
    private static final String CHAVE_USER_PREFERENCES = "user_preferences";

    @SuppressLint("CommitPrefEdits")
    SharedPreferencesUtil(Context context) {
        preferenciasDoUsuario = context.getSharedPreferences(CHAVE_USER_PREFERENCES, Context.MODE_PRIVATE);
        editarPreferenciasDoUsuario = preferenciasDoUsuario.edit();
    }
}
