package br.com.eliascoelho911.ceep.ui.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import br.com.eliascoelho911.ceep.R;
import br.com.eliascoelho911.ceep.dao.NotaDAO;
import br.com.eliascoelho911.ceep.model.Nota;
import br.com.eliascoelho911.ceep.ui.recyclerView.adapter.ListaNotasAdapter;
import br.com.eliascoelho911.ceep.ui.recyclerView.helper.callback.NotaItemTouchHelperCallBack;
import br.com.eliascoelho911.ceep.ui.util.layoutManager.LayoutManagerListaNotasUtil;
import br.com.eliascoelho911.ceep.ui.util.sharedpreferences.SharedPreferencesListaNotasUtil;
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
    @BindView(R.id.lista_notas_recycleView) RecyclerView listaNotas;
    private final NotaDAO dao = new NotaDAO();
    private ListaNotasAdapter adapter;
    private MenuItem botaoAlterarParaGrade;
    private MenuItem botaoAlterarParaLista;
    private SharedPreferencesListaNotasUtil preferenciaDoUsuarioLayout;
    private LayoutManagerListaNotasUtil layoutManagerListaNotasUtil;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_notas);
        setTitle(TITULO_APPBAR);
        ButterKnife.bind(this);
        insereNotasDeTeste();
        configuraListaDeNotas(dao.todos());

        configuraPreferenciasDoUsuario();
        configuraListaNotasLayoutManagerUtil();
        if (preferenciaDoUsuarioLayout.naoEncontrouConfiguracaoDeLayoutInicial()) {
            preferenciaDoUsuarioLayout.configuraLinearLayoutComoLayoutInicial();
        }
        if (preferenciaDoUsuarioLayout.linearLayoutEhInicial()) {
            layoutManagerListaNotasUtil.colocaListaComoLinearLayout();
        } else if (preferenciaDoUsuarioLayout.staggeredGridLayoutEhInicial()) {
            layoutManagerListaNotasUtil.colocaListaComoStaggeredGridLayout();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_lista_notas, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (menuClicadoEhAlterarParaLista(item)) {
            alteraIconeParaGrade();
            layoutManagerListaNotasUtil.colocaListaComoLinearLayout();
            preferenciaDoUsuarioLayout.configuraLinearLayoutComoLayoutInicial();
        } else if (menuClicadoForAlterarParaGrade(item)) {
            alteraIconeParaLinear();
            layoutManagerListaNotasUtil.colocaListaComoStaggeredGridLayout();
            preferenciaDoUsuarioLayout.configuraStaggeredGridLayoutComoInicial();
        }
        return super.onOptionsItemSelected(item);
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

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        buscaBotoesDoMenu(menu);
        if (preferenciaDoUsuarioLayout.linearLayoutEhInicial()) {
            alteraIconeParaGrade();
        } else if (preferenciaDoUsuarioLayout.staggeredGridLayoutEhInicial()) {
            alteraIconeParaLinear();
        }
        return super.onPrepareOptionsMenu(menu);
    }

    @OnClick(R.id.lista_notas_insere_nota)
    void vaiParaFormularioNotaParaInserir() {
        Intent vaiParaFormularioNotaActivity = new Intent(ListaNotasActivity.this,
                FormularioNotaActivity.class);
        startActivityForResult(vaiParaFormularioNotaActivity, CODIGO_PEDE_NOTA);
    }

    private void buscaBotoesDoMenu(Menu menu) {
        botaoAlterarParaGrade = menu.findItem(R.id.menu_lista_notas_alterar_para_grade);
        botaoAlterarParaLista = menu.findItem(R.id.menu_lista_notas_alterar_para_lista);
    }

    private void configuraPreferenciasDoUsuario() {
        preferenciaDoUsuarioLayout = new SharedPreferencesListaNotasUtil(this);
    }

    private void insereNotasDeTeste() {
        dao.insere(new Nota("Lista de compras", "Pão\nMostarda\nPresunto"));
        dao.insere(new Nota("Comprar novo jogo de video-game", ""));
    }

    private boolean menuClicadoForAlterarParaGrade(@NonNull MenuItem item) {
        return item.getItemId() == R.id.menu_lista_notas_alterar_para_grade;
    }

    private void alteraIconeParaLinear() {
        botaoAlterarParaGrade.setVisible(false);
        botaoAlterarParaLista.setVisible(true);
    }

    private void alteraIconeParaGrade() {
        botaoAlterarParaGrade.setVisible(true);
        botaoAlterarParaLista.setVisible(false);
    }

    private boolean menuClicadoEhAlterarParaLista(@NonNull MenuItem item) {
        return item.getItemId() == R.id.menu_lista_notas_alterar_para_lista;
    }

    private void configuraListaDeNotas(List<Nota> notas) {
        adapter = new ListaNotasAdapter(this, notas);
        listaNotas.setAdapter(adapter);
        adapter.setOnItemClickListener(this::vaiParaFormularioNotaParaAlterar);
        configuraNotaItemTouchHelper();
    }

    private void configuraListaNotasLayoutManagerUtil() {
        layoutManagerListaNotasUtil = new LayoutManagerListaNotasUtil(this, listaNotas);
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
