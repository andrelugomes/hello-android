package br.edu.ifspsaocarlos.sdm.what_ifsp_zap.service;

import com.android.volley.Response;

import java.util.List;

import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.model.Contato;


public interface RestService {

    void getAllContatos(final Response.Listener<List<Contato>> callback);

}
