package br.com.eliascoelho911.ceep.asynctask;

import android.os.AsyncTask;

import java.util.List;

import br.com.eliascoelho911.ceep.database.RoomNotaDAO;
import br.com.eliascoelho911.ceep.model.Nota;

public abstract class TemplateNotaDAOExtNotaTask extends AsyncTask<Void, Void, List<Nota>> {

    final RoomNotaDAO notaDAO;
    private final JaFinalizouListener listener;

    TemplateNotaDAOExtNotaTask(RoomNotaDAO notaDAO, JaFinalizouListener listener) {
        this.notaDAO = notaDAO;
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(List<Nota> notas) {
        super.onPostExecute(notas);
        if (listener != null) {
            listener.jaFinalizou(notas);
        }
    }

    public interface JaFinalizouListener {
        void jaFinalizou(List<Nota> notas);
    }
}
