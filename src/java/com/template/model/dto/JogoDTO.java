package com.template.model.dto;

public class JogoDTO {
    private int id;
    private String nome;
    private String tipo;
    private String versao;

    // Construtor vazio
    public JogoDTO() {}

    // Getters e Setters (Essenciais para o JavaFX)
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getTipo() { return tipo; }
    public void setTipo(String tipo) { this.tipo = tipo; }

    public String getVersao() { return versao; }
    public void setVersao(String versao) { this.versao = versao; }
}