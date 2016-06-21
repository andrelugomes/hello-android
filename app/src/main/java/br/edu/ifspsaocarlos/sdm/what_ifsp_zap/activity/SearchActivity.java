package br.edu.ifspsaocarlos.sdm.what_ifsp_zap.activity;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;

import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.R;


public class SearchActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab);
        myFab.hide();

        //Intent é a abstração de uma ação
        Intent intent = getIntent();
        handleIntent(intent);
    }
    @Override
    protected void onNewIntent(Intent intent) {
        setIntent(intent);
        handleIntent(intent);
    }

    //Intent implícito. Que é chamado quando confirma a busca
    private void handleIntent(Intent intent) {
        //ACTION_SEARCH sempre é enviado quando a activitu é Searchable
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            buildSearchListView(query);
        }
    }

}
