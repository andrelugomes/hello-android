package br.edu.ifspsaocarlos.sdm.agenda.model;

import java.io.Serializable;

/**
 * Created by agomes on 30/10/15.
 *
 * Pela especificação.Um BEAN deve:
 *  Implementar Serializable
 *  Ter get e sets
 *  Ter Construtor Vazio
 *
 *
 *  serialVersionUID = serializa o objeto para mensageria com um hash
 */
public class Contato implements Serializable{
    private static final long serialVersionUID = 1L;
    private long id;
    private String nome;
    private String fone;
    private String fone2;
    private String email;
    private String niver;
    public Contato() {
    }
    public Contato(long id, String nome, String fone, String fone2, String email, String niver) {
        this.id = id;
        this.nome = nome;
        this.fone = fone;
        this.fone2 = fone2;
        this.email = email;
        this.niver = niver;
    }
    public long getId() {
        return id;
    }
    public void setId(long id) {
        this.id = id;
    }
    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getFone() {
        return fone;
    }
    public void setFone(String fone) {
        this.fone = fone;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }

    public void setFone2(String fone2) {
        this.fone2 = fone2;
    }

    public String getFone2() {
        return fone2;
    }

    public void setNiver(String niver) {
        this.niver = niver;
    }

    public String getNiver() {
        return niver;
    }
}