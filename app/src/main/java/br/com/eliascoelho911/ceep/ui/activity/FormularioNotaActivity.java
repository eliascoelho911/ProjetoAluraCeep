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
import br.com.eliascoelho911.ceep.asynctask.AdicionaNotaTask;
import br.com.eliascoelho911.ceep.asynctask.AlterarNotaTask;
import br.com.eliascoelho911.ceep.asynctask.CorrigeIdAdapterTask;
import br.com.eliascoelho911.ceep.database.NotaDatabase;
import br.com.eliascoelho911.ceep.database.RoomNotaDAO;
import br.com.eliascoelho911.ceep.model.Nota;
import br.com.eliascoelho911.ceep.ui.recyclerView.adapter.PaletaDeCoresAdapter;
import butterknife.BindView;
import butterknife.ButterKnife;

import static br.com.eliascoelho911.ceep.ConstantesNotas.CHAVE_NOTA;
import static br.com.eliascoelho911.ceep.MensagensToast.ERRO_ALTERAR_NOTA;
import static br.com.eliascoelho911.ceep.MensagensToast.ERRO_CAMPOS_VAZIOS;
import static br.com.eliascoelho911.ceep.MensagensToast.INSERINDO_NOTA;

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
    private Nota notaRecebida = null;
    private RoomNotaDAO notaDAO;
    private boolean jaApertouOBotao = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_formulario_nota);

        ButterKnife.bind(this);

        configuraPaletaDeCores();

        notaDAO = NotaDatabase.getInstance(this).getRoomNotaDAO();

        Intent dadosRecebidos = getIntent();
        if (dadosRecebidos.hasExtra(CHAVE_NOTA)) {
            setTitle(TITULO_APPBAR_ALTERAR);
            notaRecebida = (Nota) dadosRecebidos.getSerializableExtra(CHAVE_NOTA);
            if (notaRecebida != null) {
                preencheCampos(notaRecebida);
                alteraCorDeFundo(notaRecebida.getCorDeFundo());
            } else {
                Toast.makeText(this, ERRO_ALTERAR_NOTA, Toast.LENGTH_SHORT).show();
            }
        } else {
            setTitle(TITULO_APPBAR_INSERIR);
        }
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.menu_formulario_salvar) {
            if (!jaApertouOBotao) {
                if (validaCampos()) {
                    jaApertouOBotao = true;
                    if (notaRecebida == null) {
                        adicionaNota();
                    } else {
                        alteraNota();
                    }
                } else {
                    Toast.makeText(this, ERRO_CAMPOS_VAZIOS, Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(this, INSERINDO_NOTA, Toast.LENGTH_SHORT).show();
            }
        }
        return super.onOptionsItemSelected(item);
    }

    public boolean validaCampos() {
        return !titulo.getText().toString().isEmpty() ||
                !descricao.getText().toString().isEmpty();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_formulario_nota, menu);
        return super.onCreateOptionsMenu(menu);
    }

    private void alteraNota() {
        notaRecebida.setTitulo(titulo.getText().toString());
        notaRecebida.setDescricao(descricao.getText().toString());
        notaRecebida.setCorDeFundo(pegaCorDeFundo());
        new AlterarNotaTask(notaDAO, notaRecebida, this::finish).execute();
    }

    private void adicionaNota() {
        Nota notaCriada = new Nota(titulo.getText().toString(),
                descricao.getText().toString(),
                pegaCorDeFundo(),
                0);
        new AdicionaNotaTask(notaDAO, notaCriada,
                () -> new CorrigeIdAdapterTask(notaDAO, notas -> finish())
                        .execute())
                .execute();
    }

    private void configuraPaletaDeCores() {
        PaletaDeCoresAdapter paletaDeCoresAdapter = new PaletaDeCoresAdapter(FormularioNotaActivity.this);
        paletaDeCoresAdapter.setOnItemClickListener(this::alteraCorDeFundo);
        paletaDeCores.setAdapter(paletaDeCoresAdapter);
    }

    private void alteraCorDeFundo(int cor) {
        layout.setBackgroundColor(cor);
    }

    private int pegaCorDeFundo() {
        return ((ColorDrawable) layout.getBackground()).getColor();
    }

    private void preencheCampos(Nota nota) {
        titulo.setText(nota.getTitulo());
        descricao.setText(nota.getDescricao());
    }
}
