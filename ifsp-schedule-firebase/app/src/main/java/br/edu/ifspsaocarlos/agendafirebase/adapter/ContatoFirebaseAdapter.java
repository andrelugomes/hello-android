package br.edu.ifspsaocarlos.agendafirebase.adapter;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.firebase.client.Firebase;
import com.firebase.client.Query;
import com.firebase.ui.FirebaseListAdapter;

import br.edu.ifspsaocarlos.agendafirebase.R;
import br.edu.ifspsaocarlos.agendafirebase.model.Contato;


public class ContatoFirebaseAdapter extends FirebaseListAdapter<Contato> {
    static final Class<Contato> modelClass = Contato.class;
    static final int modelLayout = R.layout.contato_celula;

    public ContatoFirebaseAdapter(Activity activity, Firebase ref) {
        super(activity, modelClass, modelLayout, ref);
    }

    public ContatoFirebaseAdapter(Activity activity, Query ref) {
        super(activity, modelClass, modelLayout, ref);
    }

    @Override
    protected void populateView(View view, Contato contato, int position) {
        ((TextView) view.findViewById(R.id.nome)).setText(contato.getNome());
        ((TextView) view.findViewById(R.id.fone)).setText(contato.getFone());

    }

}
