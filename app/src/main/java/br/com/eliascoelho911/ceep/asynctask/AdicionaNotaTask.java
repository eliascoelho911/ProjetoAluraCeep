package br.com.eliascoelho911.ceep.asynctask;

import br.com.eliascoelho911.ceep.database.RoomNotaDAO;
import br.com.eliascoelho911.ceep.model.Nota;

public class AdicionaNotaTask extends TemplateNotaDAOTask {

    public AdicionaNotaTask(RoomNotaDAO notaDAO, Nota nota, FinalizouListener listener) {
        super(notaDAO, nota, listener);
    }

    @Override
    protected Void doInBackground(Void... voids) {
        notaDAO.insere(nota);
        return null;
    }

}
