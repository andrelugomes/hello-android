package br.edu.ifspsaocarlos.sdm.what_ifsp_zap.service;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.IBinder;
import android.support.annotation.Nullable;

import com.android.volley.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.R;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.model.Contato;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.model.Mensagem;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.service.message.MessageRestService;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.service.message.MessageRestServiceFactory;

/**
 * Seviço que roda em background verificando novas mensagens
 */
public class LoadMessagesService extends Service implements Runnable{

    private MessageRestService service;

    public LoadMessagesService(){
        service = MessageRestServiceFactory.getService(this);
    }

    @Override
    public void run() {
        try {
            while(true){
                checkForNewMessages();
                Thread.sleep(10000);
            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void checkForNewMessages(){

        //Recupera o id do usuário.
        int idUser = getUserIdFromSharedPreference();

        //Verifica se o usuário ja se cadastrou
        if(idUser > 0){

            List<Contato> contatos = null; // TODO: Recuperar a lista de contatos

            if(contatos != null) {
                for (Contato contato : contatos) {

                    int lastMessageReceved = 1; //TODO: Recupera o id da última mensagem recebida deste usuário. Ex: detLastMessageRecevedFrom( contato.getId() );

                    //busca por mensagens
                    service.getMensagens(lastMessageReceved, contato.getId(), idUser, new Response.Listener<List<Mensagem>>() {
                        @Override
                        public void onResponse(List<Mensagem> mensagens) {

                            //verifica se há mensagens
                            if(!mensagens.isEmpty()){
                                //TODO: Exibe a notificação: "Você tem uma nova mensagem de: " + user.getName()
                                //TODO: Fazer com que ao clicar na notificação redirecione para tela de conversação
                            }
                        }
                    });

                }
            }
        }
    }

    private Integer getUserIdFromSharedPreference(){
        SharedPreferences sp = getSharedPreferences(getResources().getString(R.string.user_shared_preferences), Context.MODE_PRIVATE);
        return sp.getInt(getResources().getString(R.string.user_id), -1);
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

}
