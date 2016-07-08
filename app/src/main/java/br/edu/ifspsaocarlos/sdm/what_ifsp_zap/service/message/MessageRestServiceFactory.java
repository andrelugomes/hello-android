package br.edu.ifspsaocarlos.sdm.what_ifsp_zap.service.message;

import android.content.Context;

/**
 * Created by agomes on 07/07/16.
 */
public abstract class MessageRestServiceFactory {
    public static MessageRestService getService(Context c) {
        return new MessageRestServiceImpl(c);
    }
}
