package br.edu.ifspsaocarlos.sdm.what_ifsp_zap.repositoty;

import android.content.Context;


public class LastMessageRepositoryFactory {

    public static LastMessageRepository getRepository(Context c){
        return new LastMessageRepositoryImpl(c);
    }
}
