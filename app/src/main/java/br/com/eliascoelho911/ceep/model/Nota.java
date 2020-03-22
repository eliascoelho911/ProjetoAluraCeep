package br.com.eliascoelho911.ceep.model;

import java.io.Serializable;

import androidx.annotation.ColorInt;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity
public class Nota implements Serializable {

    private String titulo;
    private String descricao;
    @ColorInt private int corDeFundo;
    @PrimaryKey(autoGenerate = true) private int id;
    private int idAdapter;

    @Ignore
    public Nota(String titulo, String descricao, int corDeFundo, int idAdapter) {
        this.titulo = titulo;
        this.descricao = descricao;
        this.corDeFundo = corDeFundo;
        this.idAdapter = idAdapter;
    }

    public Nota() {
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getCorDeFundo() {
        return corDeFundo;
    }

    public void setCorDeFundo(int corDeFundo) {
        this.corDeFundo = corDeFundo;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdAdapter() {
        return idAdapter;
    }

    public void setIdAdapter(int idAdapter) {
        this.idAdapter = idAdapter;
    }
}