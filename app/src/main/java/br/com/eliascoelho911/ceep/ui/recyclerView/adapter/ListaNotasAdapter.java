package br.com.eliascoelho911.ceep.ui.recyclerView.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;
import br.com.eliascoelho911.ceep.R;
import br.com.eliascoelho911.ceep.model.Nota;
import butterknife.BindView;
import butterknife.ButterKnife;

public class ListaNotasAdapter extends RecyclerView.Adapter<ListaNotasAdapter.NotaViewHolder> {
    private final Context context;
    private final List<Nota> notas = new ArrayList<>();
    private OnItemClickListener onItemClickListener;

    public ListaNotasAdapter(Context context) {
        this.context = context;
    }

    @NonNull
    @Override
    public NotaViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return criaViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull NotaViewHolder holder, int position) {
        Nota nota = notas.get(position);
        holder.vincula(nota);
    }

    private NotaViewHolder criaViewHolder(@NonNull ViewGroup parent) {
        View viewCriada = LayoutInflater.from(context).
                inflate(R.layout.item_nota, parent, false);

        return new NotaViewHolder(viewCriada);
    }

    @Override
    public int getItemCount() {
        return notas.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public void remove(int posicao) {
        notas.remove(posicao);
        notifyItemRemoved(posicao);
    }

    public void troca(int posicaoInicial, int posicaoFinal) {
        Collections.swap(notas, posicaoInicial, posicaoFinal);
        notifyItemMoved(posicaoInicial, posicaoFinal);
    }

    public void atualiza(List<Nota> notas) {
        this.notas.clear();
        this.notas.addAll(notas);
        notifyDataSetChanged();
    }

    public Nota getItem(int posicao) {
        return notas.get(posicao);
    }

    class NotaViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.item_nota_titulo)
        TextView titulo;
        @BindView(R.id.item_nota_descricao)
        TextView descricao;
        @BindView(R.id.cardView)
        CardView cardView;
        private Nota nota;

        private NotaViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> onItemClickListener.onItemClick(nota));
        }

        private void vincula(Nota nota) {
            preencheCampos(nota);
            ajustaCorDeFundo(nota);
            this.nota = nota;
        }

        private void ajustaCorDeFundo(Nota nota) {
            cardView.setCardBackgroundColor(nota.getCorDeFundo());
        }

        private void preencheCampos(Nota nota) {
            titulo.setText(nota.getTitulo());
            descricao.setText(nota.getDescricao());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(Nota nota);
    }
}
