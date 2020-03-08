package br.com.eliascoelho911.ceep.ui.recyclerView.adapter;

import android.content.Context;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import br.com.eliascoelho911.ceep.R;
import butterknife.BindView;
import butterknife.ButterKnife;

public class PaletaDeCoresAdapter extends RecyclerView.Adapter<PaletaDeCoresAdapter.CorViewHolder> {

    private final List<Integer> cores = new ArrayList<>();
    private final Context context;
    private OnItemClickListener onItemClickListener;

    public PaletaDeCoresAdapter(Context context) {
        this.context = context;
        adicionaCores();
    }

    @NonNull
    @Override
    public CorViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return criaViewHolder(parent);
    }

    @Override
    public void onBindViewHolder(@NonNull CorViewHolder holder, int position) {
        holder.setCor(cores.get(position));
    }

    @Override
    public int getItemCount() {
        return cores.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    private CorViewHolder criaViewHolder(@NonNull ViewGroup parent) {
        View viewCriada = LayoutInflater.from(context)
                .inflate(R.layout.item_paleta_de_cor, parent, false);
        return new CorViewHolder(viewCriada);
    }

    private void adicionaCores() {
        for (CoresDaPaleta corEnum :
                CoresDaPaleta.values()) {
            cores.add(corEnum.codigoHexadecimal);
        }
    }

    class CorViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.item_paleta_de_cor_view)
        View viewBola;
        @ColorInt private Integer cor;

        private CorViewHolder(@NonNull View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(v -> onItemClickListener.onItemClick(cor));
        }

        private void setCor(@ColorInt Integer cor) {
            viewBola.setBackgroundTintList(ColorStateList.valueOf(cor));
            this.cor = cor;
        }
    }

    public interface OnItemClickListener {
        void onItemClick(@ColorInt int cor);
    }

    enum CoresDaPaleta {
        AZUL ("#408EC9"), BRANCO("#FFFFFF"),
        VERMELHO("#EC2F4B"), VERDE("#9ACD32"),
        AMARELO("#F9F256"), LILAS("#F1CBFF"),
        CINZA("#D2D4DC"), MARROM("#A47C48"),
        ROXO("#BE29EC");

        private int codigoHexadecimal;

        CoresDaPaleta(String codigoHexadecimal) {
            this.codigoHexadecimal = Color.parseColor(codigoHexadecimal);
        }
    }
}
