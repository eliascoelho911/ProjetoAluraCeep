package br.com.eliascoelho911.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import java.util.List;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import br.com.eliascoelho911.ceep.R;
import br.com.eliascoelho911.ceep.dao.NotaDAO;
import br.com.eliascoelho911.ceep.model.Nota;
import br.com.eliascoelho911.ceep.ui.recyclerView.adapter.ListaNotasAdapter;
import br.com.eliascoelho911.ceep.ui.recyclerView.helper.callback.NotaItemTouchHelperCallBack;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static br.com.eliascoelho911.ceep.ConstantesNotas.CHAVE_NOTA;
import static br.com.eliascoelho911.ceep.ConstantesNotas.CHAVE_POSICAO;
import static br.com.eliascoelho911.ceep.ConstantesNotas.CODIGO_ALTERA_NOTA;
import static br.com.eliascoelho911.ceep.ConstantesNotas.CODIGO_PEDE_NOTA;
import static br.com.eliascoelho911.ceep.ConstantesNotas.POSICAO_NAO_ENCONTRADA;
import static br.com.eliascoelho911.ceep.MensagensToast.ERRO_ALTERAR_NOTA;

public class ListaNotasActivity extends AppCompatActivity {

    public static final String TITULO_APPBAR = "Notas";
    @BindView(R.id.lista_notas_recycleView)
    RecyclerView listaNotas;
    private final NotaDAO dao = new NotaDAO();
    private ListaNotasAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);
        setTitle(TITULO_APPBAR);
        ButterKnife.bind(this);
        configuraListaDeNotas(dao.todos());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (ehResultadoParaInserir(requestCode, data)) {
            if (ehCodigoRecebeNota(resultCode)) {
                Nota notaRetornada = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                if (notaRetornada != null) {
                    adiciona(notaRetornada);
                }
            }
        } else if (ehResultadoParaAlterar(requestCode, data)) {
            if (ehCodigoRecebeNota(resultCode)) {
                Nota notaRetornada = (Nota) data.getSerializableExtra(CHAVE_NOTA);
                if (notaRetornada != null) {
                    int posicao = data.getIntExtra(CHAVE_POSICAO, POSICAO_NAO_ENCONTRADA);
                    if (ehUmaPosicaoValida(posicao)) {
                        altera(notaRetornada, posicao);
                    } else {
                        Toast.makeText(this, ERRO_ALTERAR_NOTA, Toast.LENGTH_SHORT).show();
                    }
                }
            }
        }
    }

    @OnClick(R.id.lista_notas_insere_nota)
    void vaiParaFormularioNotaParaInserir() {
        Intent vaiParaFormularioNotaActivity = new Intent(ListaNotasActivity.this,
                FormularioNotaActivity.class);
        startActivityForResult(vaiParaFormularioNotaActivity, CODIGO_PEDE_NOTA);
    }

    private void configuraListaDeNotas(List<Nota> notas) {
        adapter = new ListaNotasAdapter(this, notas);
        listaNotas.setAdapter(adapter);
        adapter.setOnItemClickListener(this::vaiParaFormularioNotaParaAlterar);
        configuraNotaItemTouchHelper();
    }

    private void configuraNotaItemTouchHelper() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new NotaItemTouchHelperCallBack(adapter));
        itemTouchHelper.attachToRecyclerView(listaNotas);
    }

    private void vaiParaFormularioNotaParaAlterar(Nota nota, int posicao) {
        Intent vaiParaFormularioNota = new Intent(ListaNotasActivity.this,
                FormularioNotaActivity.class);
        vaiParaFormularioNota.putExtra(CHAVE_NOTA, nota);
        vaiParaFormularioNota.putExtra("posicao", posicao);
        startActivityForResult(vaiParaFormularioNota, CODIGO_ALTERA_NOTA);
    }

    private void altera(Nota nota, int posicao) {
        dao.altera(posicao, nota);
        adapter.altera(posicao, nota);
    }

    private boolean ehResultadoParaAlterar(int requestCode, Intent data) {
        return ehCodigoAlteraNota(requestCode) && temNota(data);
    }

    private boolean ehCodigoAlteraNota(int requestCode) {
        return requestCode == CODIGO_ALTERA_NOTA;
    }

    private void adiciona(Nota nota) {
        dao.insere(nota);
        adapter.adiciona(nota);
    }

    private boolean ehResultadoParaInserir(int requestCode, Intent data) {
        return ehCodigoPedeNota(requestCode) && temNota(data);
    }

    private boolean temNota(Intent data) {
        return data != null && data.hasExtra("nota");
    }

    private boolean ehCodigoRecebeNota(int resultCode) {
        return resultCode == Activity.RESULT_OK;
    }

    private boolean ehCodigoPedeNota(int requestCode) {
        return requestCode == CODIGO_PEDE_NOTA;
    }

    private boolean ehUmaPosicaoValida(int posicao) {
        return posicao > POSICAO_NAO_ENCONTRADA;
    }
}
