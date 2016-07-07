package br.edu.ifspsaocarlos.sdm.what_ifsp_zap.util;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;

/**
 * Created by agostta on 06/07/2016.
 */
public class MessageUtil {

    public static void dialog(Activity activity, CharSequence message, CharSequence title, DialogInterface.OnClickListener clickCallback){
        AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(activity);
        dlgAlert.setMessage(message);
        dlgAlert.setTitle(title);
        dlgAlert.setPositiveButton("OK", clickCallback);
        dlgAlert.setCancelable(true);
        dlgAlert.create().show();
    }

}
