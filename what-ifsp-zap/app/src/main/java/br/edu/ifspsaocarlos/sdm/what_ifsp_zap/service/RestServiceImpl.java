package br.edu.ifspsaocarlos.sdm.what_ifsp_zap.service;

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
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.repositoty.LastMessageRepository;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.repositoty.LastMessageRepositoryFactory;


class RestServiceImpl implements RestService{
    private static final String SERVICE = "http://www.nobile.pro.br/sdm/mensageiro";

    private RequestQueue queue;
    private Context context;
    private Response.ErrorListener error;


    RestServiceImpl(Context context){
        this.queue = Volley.newRequestQueue(context);
        this.context = context;

        this.error = new Response.ErrorListener() {
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println(volleyError.toString());
            }
        };
    }

    public void getAllContatos(final Response.Listener<List<Contato>> callback){
        final String url = SERVICE + "/contato";

        Response.Listener success = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONArray jsonArray = response.getJSONArray("contatos");

                    List<Contato> list = new ArrayList<>();

                    for (int i = 0; i < jsonArray.length(); i++)
                        list.add(fillContato(jsonArray.getJSONObject(i)));

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
    public void getContato(final Integer id, final Response.Listener<Contato> callback) {
        final String url = SERVICE + "/contato/" + id;

        Response.Listener success = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Contato contato = fillContato(response);
                    callback.onResponse(contato);
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
    public void newContato(final String nomeCompleto, final String apelido, final Response.Listener<Contato> callback) {
        final String url = SERVICE + "/contato";

        Response.Listener success = new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    Contato contato = fillContato(response);
                    callback.onResponse(contato);
                }
                catch (JSONException e) {
                    error.onErrorResponse(new VolleyError(e));
                }
            }
        };
        String body = String.format("{\"nome_completo\":\"%s\",\"apelido\":\"%s\"}",nomeCompleto, apelido);
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


}

