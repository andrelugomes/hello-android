package br.edu.ifspsaocarlos.sdm.agenda.activity;

import android.content.ContentUris;
import android.content.ContentValues;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import br.edu.ifspsaocarlos.sdm.agenda.R;
import br.edu.ifspsaocarlos.sdm.agenda.model.Contato;
import br.edu.ifspsaocarlos.sdm.agenda.provider.ContatoProvider;

public class DetalheActivity extends AppCompatActivity {

    private Contato c;
    //private ContatoDAO cDAO;
    private Uri uriContatos = ContatoProvider.Contatos.CONTENT_URI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        //Faz a ToolBar se comportar como uma ActionBAr
        setSupportActionBar(toolbar);
        /**
         * verifica se no Intente (abstração que representauma ação)
         * tem alguma coisa extra, que no caso é um objeto Contato
         *
         * se sim? Abre para edição ou deleção
         */
        if (getIntent().hasExtra("contact")) {
            this.c = (Contato) getIntent().getSerializableExtra("contact");
            EditText nameText = (EditText) findViewById(R.id.editText1);
            nameText.setText(c.getNome());
            EditText foneText = (EditText) findViewById(R.id.editText2);
            foneText.setText(c.getFone());
            EditText emailText = (EditText) findViewById(R.id.editText3);
            emailText.setText(c.getEmail());
            EditText fone2Text = (EditText) findViewById(R.id.editText4);
            fone2Text.setText(c.getFone2());
            EditText niverText = (EditText) findViewById(R.id.editText5);
            niverText.setText(c.getNiver());
        }
        //cDAO = new ContatoDAO(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_detalhe, menu);
        if (!getIntent().hasExtra("contact")) {
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
                //cDAO.deleteContact(c);
                getContentResolver().delete(ContentUris.withAppendedId(uriContatos, c.getId()), null,null);
                Toast.makeText(getApplicationContext(), "Removido com sucesso", Toast.LENGTH_SHORT).show();
                Intent resultIntent = new Intent();
                setResult(RESULT_OK, resultIntent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void salvar() {
        String name = ((EditText) findViewById(R.id.editText1)).getText().toString();
        String fone = ((EditText) findViewById(R.id.editText2)).getText().toString();
        String fone2 = ((EditText) findViewById(R.id.editText4)).getText().toString();
        String email = ((EditText) findViewById(R.id.editText3)).getText().toString();
        String niver = ((EditText) findViewById(R.id.editText5)).getText().toString();

        ContentValues valores=new ContentValues();
        valores.put(ContatoProvider.Contatos.KEY_NAME, name);
        valores.put(ContatoProvider.Contatos.KEY_EMAIL, email);
        valores.put(ContatoProvider.Contatos.KEY_FONE, fone);
        valores.put(ContatoProvider.Contatos.KEY_FONE_2, fone2);
        valores.put(ContatoProvider.Contatos.KEY_NIVER, niver);

        if (c == null) {
            /*c = new Contato();
            c.setNome(name);
            c.setFone(fone);
            c.setEmail(email);
            c.setFone2(fone2);
            c.setNiver(niver);
            cDAO.createContact(c);*/
            getContentResolver().insert(uriContatos,valores);
            Toast.makeText(this, "Incluído com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            /*c.setNome(name);
            c.setFone(fone);
            c.setEmail(email);
            c.setFone2(fone2);
            c.setNiver(niver);
            cDAO.updateContact(c);*/
            getContentResolver().update(ContentUris.withAppendedId(uriContatos, c.getId()), valores, null, null);
            Toast.makeText(this, "Alterado com sucesso", Toast.LENGTH_SHORT).show();
        }
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}