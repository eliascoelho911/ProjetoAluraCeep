package br.com.eliascoelho911.ceep.ui.util.sharedpreferences;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;

public class SharedPreferencesListaNotasUtil {
    private SharedPreferences preferenciasDoUsuario;
    private SharedPreferences.Editor editarPreferenciasDoUsuario;
    private static final String CHAVE_USER_PREFERENCES = "user_preferences";
    private static final String CHAVE_LAYOUT_LISTA_NOTAS = "layout_lista_notas";
    private int codigoDoLayout;

    @SuppressLint("CommitPrefEdits")
    public SharedPreferencesListaNotasUtil(Context context) {
        preferenciasDoUsuario = context.getSharedPreferences(CHAVE_USER_PREFERENCES, Context.MODE_PRIVATE);
        editarPreferenciasDoUsuario = preferenciasDoUsuario.edit();
        codigoDoLayout = preferenciasDoUsuario.getInt(CHAVE_LAYOUT_LISTA_NOTAS,
                LayoutsListaNotas.getCodigo("layoutInvalido"));
    }

    public boolean linearLayoutEhInicial() {
        return codigoDoLayout == LayoutsListaNotas.getCodigo("linear");
    }

    public boolean staggeredGridLayoutEhInicial() {
        return codigoDoLayout == LayoutsListaNotas.getCodigo("staggeredGrid");
    }

    public boolean naoEncontrouConfiguracaoDeLayoutInicial() {
        return !preferenciasDoUsuario.contains(CHAVE_LAYOUT_LISTA_NOTAS);
    }

    public void configuraLinearLayoutComoLayoutInicial() {
        editarPreferenciasDoUsuario.putInt(CHAVE_LAYOUT_LISTA_NOTAS, LayoutsListaNotas.getCodigo("linear"));
        editarPreferenciasDoUsuario.commit();
    }

    public void configuraStaggeredGridLayoutComoInicial() {
        editarPreferenciasDoUsuario.putInt(CHAVE_LAYOUT_LISTA_NOTAS, LayoutsListaNotas.getCodigo("staggeredGrid"));
        editarPreferenciasDoUsuario.commit();
    }

    public enum LayoutsListaNotas {
        LINEAR_LAYOUT(1), STAGGERED_GRID_LAYOUT(2), LAYOUT_INVALIDO(-1);

        private int codigo;

        LayoutsListaNotas(int layout) {
            this.codigo = layout;
        }

        static int getCodigo(String layout) {
            switch (layout) {
                case "linear":
                    return LINEAR_LAYOUT.codigo;
                case "sttageredGrid":
                    return STAGGERED_GRID_LAYOUT.codigo;
                default:
                    return LAYOUT_INVALIDO.codigo;
            }
        }
    }
}
