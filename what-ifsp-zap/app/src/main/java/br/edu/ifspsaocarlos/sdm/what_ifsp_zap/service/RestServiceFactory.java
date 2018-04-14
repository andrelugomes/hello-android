package br.edu.ifspsaocarlos.sdm.what_ifsp_zap.service;

import android.app.Activity;
import android.content.Context;


public abstract class RestServiceFactory {


    public static RestService getRestService(Context context){
        return new RestServiceImpl(context);
    }

}
