package br.edu.ifspsaocarlos.sdm.what_ifsp_zap.service.message;

import android.content.Context;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.model.Contato;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.model.Mensagem;

/**
 * Created by agomes on 07/07/16.
 */
public class MessageRestServiceImpl implements MessageRestService {

    private static final String SERVICE = "http://www.nobile.pro.br/sdm/mensageiro";

    private RequestQueue queue;
    private Context context;
    private Response.ErrorListener error;

    public MessageRestServiceImpl(Context context) {
        this.queue = Volley.newRequestQueue(context);
        this.context = context;
        this.error = new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println(volleyError.toString());
            }
        };
    }

    @Override
    public void getMensagens(final Integer deslocamentoMensagem, final Integer remetente, final Integer destinatario, final Response.Listener<List<Mensagem>> callback) {
        final String url = SERVICE + String.format("/mensagem/%s/%s/%s", deslocamentoMensagem, remetente, destinatario);

        Response.Listener success = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("mensagens");

                    List<Mensagem> list = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++)
                        list.add(fillMensagem(jsonArray.getJSONObject(i)));

                    callback.onResponse(list);
                }
                catch (JSONException e) {
                    error.onErrorResponse(new VolleyError(e));
                }
            }
        };
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, success, error);
        queue.add(jsonObjectRequest);
    }

    @Override
    public void sendMensagem(final Integer remetente, final Integer destinatario,final String assunto, final String corpo, final Response.Listener<Mensagem> callback){
        final String url = SERVICE + "/mensagem";

        Response.Listener success = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Mensagem mensagem = fillMensagem(response);
                    callback.onResponse(mensagem);
                }
                catch (JSONException e) {
                    error.onErrorResponse(new VolleyError(e));
                }
            }
        };

        String body = String.format("{\"origem_id\":\"%s\",\"destino_id\":\"%s\",\"assunto\":\"%s\",\"corpo\":\"%s\"}",
                remetente,destinatario,assunto,corpo);
        try {
            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, url, new JSONObject(body), success, error);
            queue.add(jsonObjectRequest);
        }
        catch (JSONException e){
            error.onErrorResponse(new VolleyError(e));
        }
    }


    private Contato fillContato(JSONObject jsonobject) throws JSONException {
        String name_completo = jsonobject.getString("nome_completo");
        String apelido = jsonobject.getString("apelido");
        String id = jsonobject.getString("id");
        return new Contato(Integer.valueOf(id), name_completo, apelido);
    }

    private Mensagem fillMensagem(JSONObject jsonobject) throws JSONException {

        String id = jsonobject.getString("id");
        String origem_id = jsonobject.getString("origem_id");
        String destino_id = jsonobject.getString("destino_id");
        String assunto = jsonobject.getString("assunto");
        String corpo = jsonobject.getString("corpo");
        Contato destino = null;
        Contato origem = null;
        try {
            final JSONObject destinoJson = jsonobject.getJSONObject("destino");
            destino = fillContato(destinoJson);
            final JSONObject origenJson = jsonobject.getJSONObject("origem");
            origem = fillContato(origenJson);
        }catch (Exception e){ }


        return new Mensagem(Integer.valueOf(id),Integer.valueOf(origem_id), Integer.valueOf(destino_id), assunto, corpo, destino, origem);
    }
}
