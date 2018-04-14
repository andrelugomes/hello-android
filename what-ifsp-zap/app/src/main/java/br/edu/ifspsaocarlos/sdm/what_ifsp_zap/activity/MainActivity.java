package br.edu.ifspsaocarlos.sdm.what_ifsp_zap.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;

import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.R;

public class MainActivity extends BaseActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FloatingActionButton myFab = (FloatingActionButton) findViewById(R.id.fab);
        myFab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent i = new Intent(getApplicationContext(), BaseActivity.class);
                i.putExtra("findNewUser", true);
                startActivityForResult(i, 0);
            }
        });
        buildListView();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        buildListView();
        searchView.clearFocus();
    }

}