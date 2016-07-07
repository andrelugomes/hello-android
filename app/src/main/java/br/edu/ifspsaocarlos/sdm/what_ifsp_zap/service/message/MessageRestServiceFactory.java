package br.edu.ifspsaocarlos.sdm.what_ifsp_zap.service.message;

import android.app.Activity;

/**
 * Created by agomes on 07/07/16.
 */
public abstract class MessageRestServiceFactory {
    public static MessageRestService getService(Activity activity) {
        return new MessageRestServiceImpl(activity);
    }
}
