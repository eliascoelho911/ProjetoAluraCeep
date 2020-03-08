package br.com.eliascoelho911.ceep.model;

import android.graphics.Color;

import java.io.Serializable;

import androidx.annotation.ColorInt;

public class Nota implements Serializable {

    private final String titulo;
    private final String descricao;
    @ColorInt private int corDeFundo;

    public Nota(String titulo, String descricao) {
        this.titulo = titulo;
        this.descricao = descricao;
        corDeFundo = Color.WHITE;
    }

    public Nota(String titulo, String descricao, int corDeFundo) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.corDeFundo = corDeFundo;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public int getCorDeFundo() {
        return corDeFundo;
    }
}