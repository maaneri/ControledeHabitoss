package com.example.controledehabitos;

public class Habits {
    public int id;
    public String nome;
    public String descricao;
    public boolean feitoHoje;

    public Habits(int id, String nome, String descricao, boolean feitoHoje) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.feitoHoje = feitoHoje;
    }
}
