package br.edu.ifspsaocarlos.agendafirebase.activity;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;


import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import br.edu.ifspsaocarlos.agendafirebase.R;
import br.edu.ifspsaocarlos.agendafirebase.model.Contato;
import br.edu.ifspsaocarlos.agendafirebase.utils.Constants;

public class DetalheActivity extends AppCompatActivity {
    private Contato c;
    String FirebaseID;
    Firebase myFirebaseRef;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);

        Firebase.setAndroidContext(this);

        myFirebaseRef = new Firebase(Constants.FIREBASE_URL);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().hasExtra("FirebaseID")) {

            FirebaseID = getIntent().getStringExtra("FirebaseID");
            Firebase refContato = myFirebaseRef.child(FirebaseID);


            //Listener para a referencia. Observando se algum dado mudou. Se alterar no Dashboard web, altera na hora no celular
            ValueEventListener refContatoListener = refContato.addValueEventListener(new ValueEventListener() {

                @Override
                public void onDataChange(DataSnapshot snapshot) {
                    //status inicial do banco de dados
                    c = snapshot.getValue(Contato.class);

                    if (c != null) {
                        EditText nameText = (EditText) findViewById(R.id.editText1);
                        nameText.setText(c.getNome());


                        EditText foneText = (EditText) findViewById(R.id.editText2);
                        foneText.setText(c.getFone());


                        EditText emailText = (EditText) findViewById(R.id.editText3);
                        emailText.setText(c.getEmail());

                    }
                }

                @Override
                public void onCancelled(FirebaseError firebaseError) {
                    Log.e("LOG", firebaseError.getMessage());
                }

            });


        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalhe, menu);
        if (FirebaseID == null) {
            MenuItem item = menu.findItem(R.id.delContato);
            item.setVisible(false);
        }

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.salvarContato:
                salvar();
                return true;
            case R.id.delContato:
                myFirebaseRef.child(FirebaseID).removeValue();
                Toast.makeText(getApplicationContext(), "Contato removido", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void salvar() {
        String name = ((EditText) findViewById(R.id.editText1)).getText().toString();
        String fone = ((EditText) findViewById(R.id.editText2)).getText().toString();
        String email = ((EditText) findViewById(R.id.editText3)).getText().toString();

        if (c == null) {
            c = new Contato();
            c.setNome(name);
            c.setFone(fone);
            c.setEmail(email);

            myFirebaseRef.push().setValue(c);
            Toast.makeText(this, "Incluído com sucesso", Toast.LENGTH_SHORT).show();
        } else {

            c.setNome(name);
            c.setFone(fone);
            c.setEmail(email);

            myFirebaseRef.child(FirebaseID).setValue(c);

            Toast.makeText(this, "Alterado com sucesso", Toast.LENGTH_SHORT).show();
        }

        //caso delete o itemno dashboard e estaja na tela de edicção.podemos colocar um listener para monitorar a referencia e tratar na activity - sem concorrencia
        finish();
    }


}
