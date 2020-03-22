package br.com.eliascoelho911.ceep.util;

import java.util.List;

import br.com.eliascoelho911.ceep.database.RoomNotaDAO;
import br.com.eliascoelho911.ceep.model.Nota;

public class RoomNotaDAOUtil {

    public static void corrigeIdAdapter(RoomNotaDAO notaDAO) {
        List<Nota> notas = notaDAO.todos();

        for (int i = 0; i < notas.size(); i++) {
            notas.get(i).setIdAdapter(i);
        }

        notaDAO.altera(notas);
    }
}
