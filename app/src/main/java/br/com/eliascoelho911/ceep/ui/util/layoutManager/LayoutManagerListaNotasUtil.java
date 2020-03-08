package br.com.eliascoelho911.ceep.ui.util.layoutManager;

import android.content.Context;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

public class LayoutManagerListaNotasUtil {
    private final Context context;
    private final RecyclerView listaNotas;
    private static final int QUANTIDADE_COLUNAS_STAGGERED_GRID_LAYOUT = 2;

    public LayoutManagerListaNotasUtil(Context context, RecyclerView listaNotas) {
        this.context = context;
        this.listaNotas = listaNotas;
    }

    public void colocaListaComoLinearLayout() {
        LinearLayoutManager layoutLista = new LinearLayoutManager(context,
                LinearLayoutManager.VERTICAL, false);
        listaNotas.setLayoutManager(layoutLista);
    }

    public void colocaListaComoStaggeredGridLayout() {
        StaggeredGridLayoutManager layoutGrade = new StaggeredGridLayoutManager(QUANTIDADE_COLUNAS_STAGGERED_GRID_LAYOUT,
                StaggeredGridLayoutManager.VERTICAL);
        listaNotas.setLayoutManager(layoutGrade);
    }
}
