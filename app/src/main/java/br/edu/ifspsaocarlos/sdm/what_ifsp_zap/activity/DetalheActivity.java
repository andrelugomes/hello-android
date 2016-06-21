package br.edu.ifspsaocarlos.sdm.what_ifsp_zap.activity;

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

import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.R;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.model.Contato;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.provider.ContatoProvider;

public class DetalheActivity extends AppCompatActivity {

    private Contato c;
    private Uri uriContatos = ContatoProvider.Contatos.CONTENT_URI;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        if (getIntent().hasExtra("contact")) {
            this.c = (Contato) getIntent().getSerializableExtra("contact");
            EditText nameText = (EditText) findViewById(R.id.editText1);
            nameText.setText(c.getNomeCompleto());
            EditText nickNameText = (EditText) findViewById(R.id.editText2);
            nickNameText.setText(c.getApelido());

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
        String nickname = ((EditText) findViewById(R.id.editText2)).getText().toString();

        ContentValues valores = new ContentValues();
        valores.put(ContatoProvider.Contatos.KEY_NAME, name);
        valores.put(ContatoProvider.Contatos.KEY_NICKNAME, nickname);

        if (c == null) {
            getContentResolver().insert(uriContatos, valores);
            Toast.makeText(this, "Incluído com sucesso", Toast.LENGTH_SHORT).show();
        } else {
            getContentResolver().update(ContentUris.withAppendedId(uriContatos, c.getId()), valores, null, null);
            Toast.makeText(this, "Alterado com sucesso", Toast.LENGTH_SHORT).show();
        }
        Intent resultIntent = new Intent();
        setResult(RESULT_OK, resultIntent);
        finish();
    }
}
