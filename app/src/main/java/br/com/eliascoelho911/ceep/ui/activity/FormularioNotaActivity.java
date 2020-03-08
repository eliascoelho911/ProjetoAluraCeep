package br.com.eliascoelho911.ceep.ui.activity;

import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;
import br.com.eliascoelho911.ceep.R;
import br.com.eliascoelho911.ceep.model.Nota;
import br.com.eliascoelho911.ceep.ui.recyclerView.adapter.PaletaDeCoresAdapter;
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
    @BindView(R.id.formulario_nota_paleta_de_cores)
    RecyclerView paletaDeCores;
    @BindView(R.id.formulario_nota_layout)
    ConstraintLayout layout;
    private int posicaoRecebida = POSICAO_NAO_ENCONTRADA;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);

        setTitle(TITULO_APPBAR_INSERIR);

        ButterKnife.bind(this);

        configuraPaletaDeCores();

        Intent dadosRecebidos = getIntent();
        if (dadosRecebidos.hasExtra(CHAVE_NOTA)) {
            setTitle(TITULO_APPBAR_ALTERAR);
            Nota notaRecebida = (Nota) dadosRecebidos.getSerializableExtra(CHAVE_NOTA);
            if (notaRecebida != null) {
                preencheCampos(notaRecebida);
                alteraCorDeFundo(notaRecebida.getCorDeFundo());
                posicaoRecebida = dadosRecebidos.getIntExtra(CHAVE_POSICAO, POSICAO_NAO_ENCONTRADA);
            } else {
                Toast.makeText(this, ERRO_ALTERAR_NOTA, Toast.LENGTH_SHORT).show();
            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_formulario_salvar) {
            Nota notaCriada = new Nota(titulo.getText().toString(), descricao.getText().toString(),
                    pegaCorDeFundo());
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

    private void configuraPaletaDeCores() {
        PaletaDeCoresAdapter paletaDeCoresAdapter = new PaletaDeCoresAdapter(FormularioNotaActivity.this);
        paletaDeCoresAdapter.setOnItemClickListener(this::alteraCorDeFundo);
        paletaDeCores.setAdapter(paletaDeCoresAdapter);
    }

    private void alteraCorDeFundo(int cor) {
        layout.setBackgroundColor(cor);
    }

    private void retorna(Nota nota) {
        Intent retornaNota = new Intent();
        retornaNota.putExtra(CHAVE_NOTA, nota);
        retornaNota.putExtra(CHAVE_POSICAO, posicaoRecebida);
        setResult(AppCompatActivity.RESULT_OK, retornaNota);
    }

    private int pegaCorDeFundo() {
        return ((ColorDrawable) layout.getBackground()).getColor();
    }

    private void preencheCampos(Nota nota) {
        titulo.setText(nota.getTitulo());
        descricao.setText(nota.getDescricao());
    }
}
