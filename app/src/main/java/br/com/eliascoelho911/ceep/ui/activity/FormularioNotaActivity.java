package br.com.eliascoelho911.ceep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import br.com.eliascoelho911.ceep.R;
import br.com.eliascoelho911.ceep.model.Nota;
import butterknife.BindView;
import butterknife.ButterKnife;

import static br.com.eliascoelho911.ceep.ConstantesNotas.CHAVE_NOTA;
import static br.com.eliascoelho911.ceep.ConstantesNotas.CHAVE_POSICAO;
import static br.com.eliascoelho911.ceep.ConstantesNotas.POSICAO_NAO_ENCONTRADA;
import static br.com.eliascoelho911.ceep.MensagensToast.ERRO_ALTERAR_NOTA;

public class FormularioNotaActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR_INSERIR = "Inserir nota";
    private static final String TITULO_APPBAR_ALTERAR = "Alterar nota";
    @BindView(R.id.formulario_nota_titulo)
    EditText titulo;
    @BindView(R.id.formulario_nota_descricao)
    EditText descricao;
    private int posicaoRecebida = POSICAO_NAO_ENCONTRADA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);

        setTitle(TITULO_APPBAR_INSERIR);

        ButterKnife.bind(this);

        Intent dadosRecebidos = getIntent();
        if (dadosRecebidos.hasExtra(CHAVE_NOTA)) {
            setTitle(TITULO_APPBAR_ALTERAR);
            Nota notaRecebida = (Nota) dadosRecebidos.getSerializableExtra(CHAVE_NOTA);
            if (notaRecebida != null) {
                preencheCampos(notaRecebida);
                posicaoRecebida = dadosRecebidos.getIntExtra(CHAVE_POSICAO, POSICAO_NAO_ENCONTRADA);
            } else {
                Toast.makeText(this, ERRO_ALTERAR_NOTA, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_formulario_salvar) {
            Nota notaCriada = new Nota(titulo.getText().toString(), descricao.getText().toString());
            retorna(notaCriada);
            finish();
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_nota, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void retorna(Nota nota) {
        Intent retornaNota = new Intent();
        retornaNota.putExtra(CHAVE_NOTA, nota);
        retornaNota.putExtra("posicao", posicaoRecebida);
        setResult(AppCompatActivity.RESULT_OK, retornaNota);
    }

    private void preencheCampos(Nota nota) {
        titulo.setText(nota.getTitulo());
        descricao.setText(nota.getDescricao());
    }
}
