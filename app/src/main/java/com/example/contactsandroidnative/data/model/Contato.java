package com.example.contactsandroidnative.data.model;

import java.io.Serializable;


public class Contato implements Serializable {

    private Integer id;

    private String nome;

    private String email;

    private String cel;

    private String imagem;

    private Integer excluido;

    public Contato(){}

    public Contato(String nome, String email){
        this.nome = nome;
        this.email = email;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCel() {
        return cel;
    }

    public void setCel(String cel) {
        this.cel = cel;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public Integer getExcluido() {
        return excluido;
    }

    public void setExcluido(Integer excluido) {
        this.excluido = excluido;
    }
}
