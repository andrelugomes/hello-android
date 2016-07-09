package br.edu.ifspsaocarlos.sdm.what_ifsp_zap.service;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.BitmapFactory;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.android.volley.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.R;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.model.Contato;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.model.Mensagem;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.repositoty.LastMessageRepository;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.repositoty.LastMessageRepositoryFactory;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.service.message.MessageRestService;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.service.message.MessageRestServiceFactory;

/**
 * Seviço que roda em background verificando novas mensagens
 */
public class LoadMessagesService extends Service implements Runnable{

    private MessageRestService serviceMessages;
    private RestService service;
    private LastMessageRepository lastMessage;

    public LoadMessagesService(){
        serviceMessages = MessageRestServiceFactory.getService(this);
        service = RestServiceFactory.getRestService(this);
        lastMessage = LastMessageRepositoryFactory.getRepository(this);
    }

    @Override
    public void run() {
        //recupera todos os contatos
        service.getAllContatos(contatosLoaded());
    }

    public Response.Listener<List<Contato>> contatosLoaded(){
        final Response.Listener<List<Contato>>  callback = new Response.Listener<List<Contato>>() {
            @Override
            public void onResponse(List<Contato> contatos) {

                while(true){
                    try {
                        checkForNewMessages(contatos);
                        //tempo de espera entre os bombardeios de GETS
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        break;
                    }
                }
            }
        };
        return callback;
    }

    /**
     * Verifica se os contatos enviaram novas mensagems
     * @param contatos - contatos que serão verificados
     */
    public void checkForNewMessages(List<Contato> contatos){
        System.out.println("Checking for new messages.");

        //Recupera o id do usuário.
        final int idUser = getUserIdFromSharedPreference();

        //Verifica se o usuário ja se cadastrou
        if(idUser > 0){

            if(contatos != null) {
                for (final Contato contato : contatos) {

                    if(contato.getId().equals(idUser))
                        continue;

                    //recupera do sqlite a ultima mensagem recebida
                    int lastMessageReceved = lastMessage.getLastMessage(contato.getId(), idUser);

                    //busca por mensagens
                    serviceMessages.getMensagens(lastMessageReceved, contato.getId(), idUser, new Response.Listener<List<Mensagem>>() {
                        @Override
                        public void onResponse(List<Mensagem> mensagens) {

                            //verifica se há mensagens
                            if(!mensagens.isEmpty()){
                                //notificação
                                notification(contato.getApelido());

                                //atualiza a tabela com a ultima mensagem recebida
                                lastMessage.insertOrUpdate(contato.getId(), idUser, mensagens.get(mensagens.size()-1).getId());
                            }
                        }
                    });

                }
            }
        }
    }

    /**
     * Exibe uma notificação
     * @param userName
     */
    private void notification(String userName){
        NotificationManager nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Intent intent = new Intent(this, null);

        intent.putExtra("mensagem_da_notificacao", "Você tem uma nova mensagem de: " + userName);

        PendingIntent p = PendingIntent.getActivity(this, 0, intent, 0);

        Notification.Builder builder = new Notification.Builder(this);
        builder.setSmallIcon(R.drawable.ic_contato);
        builder.setTicker("Você tem uma nova mensagem de: " + userName);
        builder.setContentTitle("Mensagem");
        builder.setContentText("Clique aqui.");

        builder.setWhen(System.currentTimeMillis());
        builder.setContentIntent(p);
        builder.setLargeIcon(BitmapFactory.decodeResource(getResources(), R.drawable.ic_mensageiro));
        Notification notification = builder.build();
        notification.vibrate = new long[] {100, 250};
        nm.notify(R.mipmap.ic_launcher, notification);
    }

    @NonNull
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
