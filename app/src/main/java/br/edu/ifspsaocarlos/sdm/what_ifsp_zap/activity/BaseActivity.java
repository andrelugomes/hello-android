package br.edu.ifspsaocarlos.sdm.what_ifsp_zap.activity;
import android.app.Activity;
import android.app.SearchManager;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.R;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.adapter.ContatoArrayAdapter;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.model.Contato;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.provider.ContatoProvider;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.service.RestService;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.service.RestServiceFactory;

public class BaseActivity extends AppCompatActivity {

    //protected ContatoDAO cDAO = new ContatoDAO(this);
    protected ListView list;
    protected ContatoArrayAdapter adapter;
    protected SearchView searchView;
    private Uri uriContatos = ContatoProvider.Contatos.CONTENT_URI;

    public RestService service;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        service = RestServiceFactory.getRestService(this);
        list = (ListView) findViewById(R.id.listView);
        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View arg1, int arg2, long arg3) {
                Contato contact = (Contato) adapterView.getAdapter().getItem(arg2);
                //TODO - ir para a conversa ao inves de ir pro Detalhe
                Intent inte = new Intent(getApplicationContext(), MessageActivity.class);
                inte.putExtra("contact", contact);
                startActivityForResult(inte, 0);
            }
        });

        // Carregar todos os usuário pelo GET com Volley
        if (getIntent().hasExtra("findNewUser")) {
            buildListForAllUsers();
        }
        registerForContextMenu(list);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        SearchManager searchManager = (SearchManager) getSystemService(Context.SEARCH_SERVICE);
        searchView = (SearchView) menu.findItem(R.id.pesqContato).getActionView();
        searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        searchView.setIconifiedByDefault(true);
        return true;
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater m = getMenuInflater();
        m.inflate(R.menu.menu_context, menu);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
        ContatoArrayAdapter adapter = (ContatoArrayAdapter) list.getAdapter();
        Contato contact = adapter.getItem(info.position);
        switch (item.getItemId()) {
            case R.id.delete_item:
                getContentResolver().delete(ContentUris.withAppendedId(uriContatos, contact.getId()), null, null);
                Toast.makeText(getApplicationContext(), "Removido com sucesso", Toast.LENGTH_SHORT).show();
                buildListView();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    protected void buildListView() {
        List<Contato> contatos = new ArrayList<>();
        Cursor cursor = getContentResolver().query(uriContatos, null, null, null, null);
        if (cursor!=null) {
            contatos = cursorTolist(cursor);
        }
        adapter = new ContatoArrayAdapter(this, contatos);
        list.setAdapter(adapter);
    }

    protected void buildListForAllUsers() {
        final Activity baseActivity = this;
        service.getAllContatos(
            new Response.Listener<List<Contato>>() {
                @Override
                public void onResponse(List<Contato> contatos) {
                    list.setAdapter(new ContatoArrayAdapter(baseActivity,contatos));
                }
            }
        );
    }

    protected void buildSearchListView(String query) {
        List<Contato> contatos = new ArrayList<>();
        String where = ContatoProvider.Contatos.KEY_NAME + " like ?";
        String[] whereargs = new String[] {query + "%"};
        Cursor cursor = getContentResolver().query(uriContatos,null,where,whereargs,null,null);
        if (cursor!=null){
            contatos = cursorTolist(cursor);
        }
        adapter = new ContatoArrayAdapter(this, contatos);
        list.setAdapter(adapter);
    }

    public List<Contato> cursorTolist(Cursor cursor){
        List<Contato> contatos = new ArrayList<>();
        while (cursor.moveToNext()) {
            Contato contato = new Contato(cursor.getInt(0),cursor.getString(1),cursor.getString(2));
            contatos.add(contato);
        }
        return contatos;
    }
}
