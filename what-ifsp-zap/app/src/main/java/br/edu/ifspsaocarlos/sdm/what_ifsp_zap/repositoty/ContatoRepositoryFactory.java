package br.edu.ifspsaocarlos.sdm.what_ifsp_zap.repositoty;

import android.content.Context;

/**
 * Created by agomes on 07/07/16.
 */
public class ContatoRepositoryFactory {

    public static ContatoRepository getRepository(Context c){
        return new ContatoRepositoryImpl(c);
    }
}
