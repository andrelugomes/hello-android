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

        Response.Listener sucess = new Response.Listener<JSONObject>() {
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

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null, sucess, error);
        queue.add(jsonObjectRequest);
    }

    private Contato fillContato(JSONObject jsonobject) throws JSONException {
        String name_completo = jsonobject.getString("nome_completo");
        String apelido = jsonobject.getString("apelido");
        String id = jsonobject.getString("id");
        return new Contato(Integer.valueOf(id), name_completo, apelido);
    }
}
