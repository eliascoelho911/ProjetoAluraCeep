package br.com.eliascoelho911.ceep.asynctask;

import java.util.List;

import br.com.eliascoelho911.ceep.database.RoomNotaDAO;
import br.com.eliascoelho911.ceep.model.Nota;
import br.com.eliascoelho911.ceep.util.RoomNotaDAOUtil;

public class CorrigeIdAdapterTask extends TemplateNotaDAOExtNotaTask {

    public CorrigeIdAdapterTask(RoomNotaDAO notaDAO, JaFinalizouListener listener) {
        super(notaDAO, listener);
    }

    @Override
    protected List<Nota> doInBackground(Void... voids) {
        RoomNotaDAOUtil.corrigeIdAdapter(notaDAO);
        return notaDAO.todos();
    }

}
