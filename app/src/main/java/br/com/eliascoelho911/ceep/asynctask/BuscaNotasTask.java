package br.com.eliascoelho911.ceep.asynctask;

import java.util.List;

import br.com.eliascoelho911.ceep.database.RoomNotaDAO;
import br.com.eliascoelho911.ceep.model.Nota;

public class BuscaNotasTask extends TemplateNotaDAOExtNotaTask {

    public BuscaNotasTask(RoomNotaDAO notaDAO, JaFinalizouListener listener) {
        super(notaDAO, listener);
    }

    @Override
    protected List<Nota> doInBackground(Void... voids) {
        return notaDAO.todos();
    }

}
