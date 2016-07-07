package br.edu.ifspsaocarlos.sdm.what_ifsp_zap.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.TextView;

import java.util.List;

import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.R;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.model.Contato;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.model.Mensagem;

/**
 * Created by agomes on 07/07/16.
 */
public class MensagemArrayAdapter extends ArrayAdapter<Mensagem> {

    private LayoutInflater inflater;

    public MensagemArrayAdapter(Activity messageActivity, List<Mensagem> mensagens) {
        super(messageActivity, R.layout.mensagem_celula, mensagens);
        this.inflater = (LayoutInflater) messageActivity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.mensagem_celula, null);
            holder = new ViewHolder();
            holder.corpo = (TextView) convertView.findViewById(R.id.corpo);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        Mensagem m = getItem(position);
        holder.corpo.setText(m.getCorpo());
        return convertView;
    }

    static class ViewHolder {
        public TextView corpo;
    }
}
