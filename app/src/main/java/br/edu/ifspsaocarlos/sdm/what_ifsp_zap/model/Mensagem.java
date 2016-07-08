package br.edu.ifspsaocarlos.sdm.what_ifsp_zap.model;

import java.io.Serializable;

/**
 * Created by agomes on 16/06/16.
 */
public class Mensagem implements Serializable{
    private static final long serialVersionUID = 2L;

    private  Integer id;

    private Integer idOrigem;
    private Integer idDestino;
    private String assunto;
    private String corpo;

    private Contato destino;
    private Contato origem;

    public Mensagem(Integer id, Integer idOrigem, Integer idDestino, String assunto, String corpo, Contato destino, Contato origem) {
        this.id = id;
        this.idOrigem = idOrigem;
        this.idDestino = idDestino;
        this.assunto = assunto;
        this.corpo = corpo;
        this.destino = destino;
        this.origem = origem;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public Integer getIdOrigem() {
        return idOrigem;
    }

    public void setIdOrigem(Integer idOrigem) {
        this.idOrigem = idOrigem;
    }

    public Integer getIdDestino() {
        return idDestino;
    }

    public void setIdDestino(Integer idDestino) {
        this.idDestino = idDestino;
    }

    public String getAssunto() {
        return assunto;
    }

    public void setAssunto(String assunto) {
        this.assunto = assunto;
    }

    public String getCorpo() {
        return corpo;
    }

    public void setCorpo(String corpo) {
        this.corpo = corpo;
    }

    public Contato getDestino() {
        return destino;
    }

    public void setDestino(Contato destino) {
        this.destino = destino;
    }

    public Contato getOrigem() {
        return origem;
    }

    public void setOrigem(Contato origem) {
        this.origem = origem;
    }
}
