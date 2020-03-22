package br.com.eliascoelho911.ceep.ui.recyclerView.helper.callback;

import android.content.Context;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;
import br.com.eliascoelho911.ceep.asynctask.CorrigeIdAdapterTask;
import br.com.eliascoelho911.ceep.asynctask.RemoveNotaTask;
import br.com.eliascoelho911.ceep.asynctask.TrocaNotaTask;
import br.com.eliascoelho911.ceep.database.NotaDatabase;
import br.com.eliascoelho911.ceep.database.RoomNotaDAO;
import br.com.eliascoelho911.ceep.model.Nota;
import br.com.eliascoelho911.ceep.ui.recyclerView.adapter.ListaNotasAdapter;

import static br.com.eliascoelho911.ceep.MensagensToast.ERRO_MOVER_NOTA;

public class NotaItemTouchHelperCallBack extends ItemTouchHelper.Callback {
    private final ListaNotasAdapter adapter;
    private RoomNotaDAO notaDAO;
    private Context context;

    public NotaItemTouchHelperCallBack(ListaNotasAdapter adapter, Context context) {
        this.adapter = adapter;
        notaDAO = NotaDatabase.getInstance(context)
                .getRoomNotaDAO();
        this.context = context;
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
        int notaSelecionadaIdAdapter = viewHolder.getAdapterPosition();
        int notaSubstituidaIdAdapter = target.getAdapterPosition();
        Nota notaSelecionada = adapter.getItem(notaSelecionadaIdAdapter);
        Nota notaSubstituida = adapter.getItem(notaSubstituidaIdAdapter);

        if (notaSelecionada != null && notaSubstituida != null) {
            trocaNota(notaSelecionadaIdAdapter, notaSubstituidaIdAdapter, notaSelecionada, notaSubstituida);
        } else {
            Toast.makeText(context, ERRO_MOVER_NOTA, Toast.LENGTH_SHORT).show();
        }

        return true;
    }

    private void trocaNota(int notaSelecionadaIdAdapter, int notaSubstituidaIdAdapter, Nota notaSelecionada, Nota notaSubstituida) {
        adapter.troca(notaSelecionadaIdAdapter, notaSubstituidaIdAdapter);
        new TrocaNotaTask(notaDAO, notaSelecionada, notaSubstituida).execute();
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        Nota nota = adapter.getItem(viewHolder.getAdapterPosition());
        remove(nota);
    }

    private void remove(Nota nota) {
        new RemoveNotaTask(notaDAO, nota).execute();
        adapter.remove(nota.getIdAdapter());
        new CorrigeIdAdapterTask(notaDAO, adapter::atualiza).execute();
    }
}
