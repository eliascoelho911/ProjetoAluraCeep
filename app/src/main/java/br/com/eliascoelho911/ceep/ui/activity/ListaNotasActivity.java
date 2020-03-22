package br.com.eliascoelho911.ceep.ui.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import br.com.eliascoelho911.ceep.R;
import br.com.eliascoelho911.ceep.asynctask.BuscaNotasTask;
import br.com.eliascoelho911.ceep.database.NotaDatabase;
import br.com.eliascoelho911.ceep.database.RoomNotaDAO;
import br.com.eliascoelho911.ceep.model.Nota;
import br.com.eliascoelho911.ceep.ui.recyclerView.adapter.ListaNotasAdapter;
import br.com.eliascoelho911.ceep.ui.recyclerView.helper.callback.NotaItemTouchHelperCallBack;
import br.com.eliascoelho911.ceep.util.layoutManager.LayoutManagerListaNotasUtil;
import br.com.eliascoelho911.ceep.util.sharedpreferences.SharedPreferencesListaNotasUtil;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static br.com.eliascoelho911.ceep.ConstantesNotas.CHAVE_NOTA;

public class ListaNotasActivity extends AppCompatActivity {
    public static final String TITULO_APPBAR = "Notas";
    @BindView(R.id.lista_notas_recycleView)
    RecyclerView listaNotas;
    private RoomNotaDAO notaDAO;
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

        notaDAO = NotaDatabase.getInstance(this)
                .getRoomNotaDAO();

        configuraListaDeNotas();

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
    protected void onResume() {
        super.onResume();
        atualizaAdapter();
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
        } else if (menuClicadoEhFeedback(item)) {
            vaiParaFeedbackActivity();
        }
        return super.onOptionsItemSelected(item);
    }

    private boolean menuClicadoEhFeedback(MenuItem item) {
        return item.getItemId() == R.id.menu_lista_notas_feedback;
    }

    public void vaiParaFeedbackActivity() {
        Intent vaiParaFeedback = new Intent(this, FeedbackActivity.class);
        startActivity(vaiParaFeedback);
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
        startActivity(vaiParaFormularioNotaActivity);
    }

    private void buscaBotoesDoMenu(Menu menu) {
        botaoAlterarParaGrade = menu.findItem(R.id.menu_lista_notas_alterar_para_grade);
        botaoAlterarParaLista = menu.findItem(R.id.menu_lista_notas_alterar_para_lista);
    }

    private void configuraPreferenciasDoUsuario() {
        preferenciaDoUsuarioLayout = new SharedPreferencesListaNotasUtil(this);
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

    private void configuraListaDeNotas() {
        configuraAdapter();
        listaNotas.setAdapter(adapter);
        adapter.setOnItemClickListener(this::vaiParaFormularioNotaParaAlterar);
        configuraNotaItemTouchHelper();
    }

    private void configuraAdapter() {
        adapter = new ListaNotasAdapter(this);
        atualizaAdapter();
    }

    private void atualizaAdapter() {
        new BuscaNotasTask(notaDAO, notas -> adapter.atualiza(notas)).execute();
    }

    private void configuraListaNotasLayoutManagerUtil() {
        layoutManagerListaNotasUtil = new LayoutManagerListaNotasUtil(this, listaNotas);
    }

    private void configuraNotaItemTouchHelper() {
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new NotaItemTouchHelperCallBack(adapter, this));
        itemTouchHelper.attachToRecyclerView(listaNotas);
    }

    private void vaiParaFormularioNotaParaAlterar(Nota nota) {
        Intent vaiParaFormularioNota = new Intent(ListaNotasActivity.this,
                FormularioNotaActivity.class);
        vaiParaFormularioNota.putExtra(CHAVE_NOTA, nota);
        startActivity(vaiParaFormularioNota);
    }
}
