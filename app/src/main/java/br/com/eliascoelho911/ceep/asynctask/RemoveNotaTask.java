package br.com.eliascoelho911.ceep.asynctask;

import android.os.AsyncTask;

import br.com.eliascoelho911.ceep.database.RoomNotaDAO;
import br.com.eliascoelho911.ceep.model.Nota;

public class RemoveNotaTask extends AsyncTask<Void, Void, Void> {
    private final RoomNotaDAO notaDAO;
    private final Nota nota;

    public RemoveNotaTask(RoomNotaDAO notaDAO, Nota nota) {
        this.notaDAO = notaDAO;
        this.nota = nota;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        notaDAO.remove(nota);
        return null;
    }
}
