package br.edu.ifspsaocarlos.sdm.what_ifsp_zap.model;

import java.io.Serializable;

/**
 * Created by agomes on 16/06/16.
 */
public class Mensagem implements Serializable{
    private static final long serialVersionUID = 2L;

    private Integer idOrigem;
    private Integer idDestino;
    private String assunto;
    private String corpo;

    private Contato destino;
    private Contato origem;

}
