package br.com.eliascoelho911.ceep.ui.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import br.com.eliascoelho911.ceep.R;

public class FeedbackActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_feedback, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (clicouParaEnviar(item)) {
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean clicouParaEnviar(@NonNull MenuItem item) {
        return item.getItemId() == R.id.menu_feedback_enviar;
    }
}
