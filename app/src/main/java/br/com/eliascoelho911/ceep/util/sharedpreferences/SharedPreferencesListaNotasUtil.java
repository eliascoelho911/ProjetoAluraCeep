package br.com.eliascoelho911.ceep.util.sharedpreferences;

import android.content.Context;

public class SharedPreferencesListaNotasUtil extends SharedPreferencesUtil{
    private final int codigoDoLayout;
    private static final String CHAVE_LAYOUT_LISTA_NOTAS = "layout_lista_notas";

    public SharedPreferencesListaNotasUtil(Context context) {
        super(context);
        codigoDoLayout = preferenciasDoUsuario.getInt(CHAVE_LAYOUT_LISTA_NOTAS,
                LayoutsListaNotas.LINEAR_LAYOUT.codigo);
    }

    public boolean linearLayoutEhInicial() {
        return codigoDoLayout == LayoutsListaNotas.LINEAR_LAYOUT.codigo;
    }

    public boolean staggeredGridLayoutEhInicial() {
        return codigoDoLayout == LayoutsListaNotas.STAGGERED_GRID_LAYOUT.codigo;
    }

    public boolean naoEncontrouConfiguracaoDeLayoutInicial() {
        return !preferenciasDoUsuario.contains(CHAVE_LAYOUT_LISTA_NOTAS);
    }

    public void configuraLinearLayoutComoLayoutInicial() {
        editarPreferenciasDoUsuario.putInt(CHAVE_LAYOUT_LISTA_NOTAS, LayoutsListaNotas.LINEAR_LAYOUT.codigo);
        editarPreferenciasDoUsuario.commit();
    }

    public void configuraStaggeredGridLayoutComoInicial() {
        editarPreferenciasDoUsuario.putInt(CHAVE_LAYOUT_LISTA_NOTAS, LayoutsListaNotas.STAGGERED_GRID_LAYOUT.codigo);
        editarPreferenciasDoUsuario.commit();
    }

    public enum LayoutsListaNotas {
        LINEAR_LAYOUT(1), STAGGERED_GRID_LAYOUT(2);

        private final int codigo;

        LayoutsListaNotas(int layout) {
            this.codigo = layout;
        }

    }
}
