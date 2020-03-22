package br.com.eliascoelho911.ceep.util.sharedpreferences;

import android.content.Context;

public class SharedPreferencesSplashScreenUtil extends SharedPreferencesUtil {

    private static final String CHAVE_APP_JA_FOI_ABERTO = "appJaFoiAberto";

    public SharedPreferencesSplashScreenUtil(Context context) {
        super(context);
    }

    public boolean appJaFoiAberto() {
        return super.preferenciasDoUsuario.getBoolean(CHAVE_APP_JA_FOI_ABERTO, false);
    }

    public void appFoiAberto() {
        super.editarPreferenciasDoUsuario.putBoolean(CHAVE_APP_JA_FOI_ABERTO, true);
        super.editarPreferenciasDoUsuario.commit();
    }
}
