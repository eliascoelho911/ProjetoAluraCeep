package br.com.eliascoelho911.ceep.ui.recyclerView.helper.callback;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import br.com.eliascoelho911.ceep.dao.NotaDAO;
import br.com.eliascoelho911.ceep.ui.recyclerView.adapter.ListaNotasAdapter;

public class NotaItemTouchHelperCallBack extends ItemTouchHelper.Callback {
    private final ListaNotasAdapter adapter;
    private final NotaDAO dao = new NotaDAO();

    public NotaItemTouchHelperCallBack(ListaNotasAdapter adapter) {
        this.adapter = adapter;
    }

    @Override
    public int getMovementFlags(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        int movimentoDeDeslize = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT;
        int movimentoDeArrastar = ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT |
                ItemTouchHelper.DOWN | ItemTouchHelper.UP;
        return makeMovementFlags(movimentoDeArrastar, movimentoDeDeslize);
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        int posicaoInicial = viewHolder.getAdapterPosition();
        int posicaoFinal = target.getAdapterPosition();
        trocaNotas(posicaoInicial, posicaoFinal);
        return true;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int posicaoDaNotaDeslizada = viewHolder.getAdapterPosition();
        removeNota(posicaoDaNotaDeslizada);
    }

    private void trocaNotas(int posicaoInicial, int posicaoFinal) {
        dao.troca(posicaoInicial, posicaoFinal);
        adapter.troca(posicaoInicial, posicaoFinal);
    }

    private void removeNota(int posicao) {
        dao.remove(posicao);
        adapter.remove(posicao);
    }
}
