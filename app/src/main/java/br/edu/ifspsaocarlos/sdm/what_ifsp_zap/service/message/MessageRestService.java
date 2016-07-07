package br.edu.ifspsaocarlos.sdm.what_ifsp_zap.service.message;

import com.android.volley.Response;

import java.util.List;

import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.model.Mensagem;

/**
 * Created by agomes on 07/07/16.
 */
public interface MessageRestService {

    void getMensagens(final Integer deslocamentoMensagem, final Integer remetente, final Integer destinatario, final Response.Listener<List<Mensagem>> callback);

    void sendMensagem(final Integer remetente, final Integer destinatario,
                      final String assunto, final String corpo, final Response.Listener<Mensagem> callback);
}
