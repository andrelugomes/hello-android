package br.edu.ifspsaocarlos.sdm.what_ifsp_zap.service;

import android.app.Activity;


public abstract class RestServiceFactory {


    public static RestService getRestService(Activity activity){
        return new RestServiceImpl(activity);
    }

}
