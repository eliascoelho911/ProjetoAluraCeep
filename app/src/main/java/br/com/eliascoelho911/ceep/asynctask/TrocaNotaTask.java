package br.com.eliascoelho911.ceep.asynctask;

import android.os.AsyncTask;

import br.com.eliascoelho911.ceep.database.RoomNotaDAO;
import br.com.eliascoelho911.ceep.model.Nota;
import br.com.eliascoelho911.ceep.util.NotaUtil;

public class TrocaNotaTask extends AsyncTask<Void, Void, Void> {
    private final RoomNotaDAO notaDAO;
    private final Nota notaSelecionada;
    private final Nota notaSubstituida;

    public TrocaNotaTask(RoomNotaDAO notaDAO,
                         Nota notaSelecionada,
                         Nota notaSubstituida) {
        this.notaDAO = notaDAO;
        this.notaSelecionada = notaSelecionada;
        this.notaSubstituida = notaSubstituida;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        NotaUtil.alternaIdAdapter(notaSelecionada, notaSubstituida);
        notaDAO.altera(notaSelecionada, notaSubstituida);
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
    }
}
