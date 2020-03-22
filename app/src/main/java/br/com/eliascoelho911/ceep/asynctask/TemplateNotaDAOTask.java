package br.com.eliascoelho911.ceep.asynctask;

import android.os.AsyncTask;

import br.com.eliascoelho911.ceep.database.RoomNotaDAO;
import br.com.eliascoelho911.ceep.model.Nota;

public abstract class TemplateNotaDAOTask extends AsyncTask<Void, Void, Void> {
    final RoomNotaDAO notaDAO;
    final Nota nota;
    private final FinalizouListener listener;

    TemplateNotaDAOTask(RoomNotaDAO notaDAO, Nota nota, FinalizouListener listener) {
        this.notaDAO = notaDAO;
        this.nota = nota;
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
        if (listener != null) {
            listener.jaFinalizou();
        }
    }

    public interface FinalizouListener {
        void jaFinalizou();
    }
}
