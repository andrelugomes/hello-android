package br.edu.ifspsaocarlos.sdm.what_ifsp_zap.service;

import com.android.volley.Response;

import java.util.List;

import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.model.Contato;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.model.Mensagem;


public interface RestService {

    void getAllContatos(final Response.Listener<List<Contato>> callback);

    void getContato(final Integer id, final Response.Listener<Contato> callback);

    void newContato(final String nomeCompleto, final String apelido, final Response.Listener<Contato> callback);

    void getMensagens(final Integer deslocamentoMensagem, final Integer remetente, final Integer destinatario, final Response.Listener<List<Mensagem>> callback);

    void sendMensagem(final Integer remetente, final Integer destinatario,
                     final String assunto, final String corpo, final Response.Listener<Mensagem> callback);
}
