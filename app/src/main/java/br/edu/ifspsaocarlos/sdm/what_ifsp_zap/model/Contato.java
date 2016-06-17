package br.edu.ifspsaocarlos.sdm.what_ifsp_zap.model;

import java.io.Serializable;

/**
 * Created by agomes on 16/06/16.
 */
public class Contato implements Serializable {
    private static final long serialVersionUID = 1L;
    private Integer id;
    private String nomeCompleto;
    private String apelido;

    public Contato() {
    }

    public Contato(Integer id, String nome, String apelido) {
        this.id = id;
        this.nomeCompleto = nome;
        this.apelido = apelido;
    }

    public Integer getId() {
        return id;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public String getApelido() {
        return apelido;
    }
}