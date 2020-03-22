package br.com.eliascoelho911.ceep.util;

import br.com.eliascoelho911.ceep.model.Nota;

public class NotaUtil {
    public static void alternaIdAdapter(Nota nota1, Nota nota2) {
        int nota1IdAdapter = nota1.getIdAdapter();
        int nota2IdAdapter = nota2.getIdAdapter();
        nota1.setIdAdapter(nota2IdAdapter);
        nota2.setIdAdapter(nota1IdAdapter);
    }
}
