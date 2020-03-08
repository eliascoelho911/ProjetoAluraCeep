package br.com.eliascoelho911.ceep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;
import br.com.eliascoelho911.ceep.R;
import br.com.eliascoelho911.ceep.ui.util.sharedpreferences.SharedPreferencesSplashScreenUtil;

public class SplashScreenActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

        SharedPreferencesSplashScreenUtil preferenciasSplashScreen =
                new SharedPreferencesSplashScreenUtil(this);

        int delay;
        if (preferenciasSplashScreen.appJaFoiAberto()) {
            delay = 500;
        } else {
            preferenciasSplashScreen.appFoiAberto();
            delay = 2000;
        }
        configuraHandler(delay);
    }

    private void configuraHandler(int delay) {
        Handler handler = new Handler();
        handler.postDelayed(this::vaiParaListaNotasActivity, delay);
    }

    private void vaiParaListaNotasActivity() {
        Intent vaiParaListaNotasActivity = new Intent(SplashScreenActivity.this,
                ListaNotasActivity.class);
        startActivity(vaiParaListaNotasActivity);
        finish();
    }
}
