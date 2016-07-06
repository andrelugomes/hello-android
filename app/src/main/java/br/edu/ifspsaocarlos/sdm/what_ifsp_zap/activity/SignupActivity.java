package br.edu.ifspsaocarlos.sdm.what_ifsp_zap.activity;


import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;

import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.R;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.model.Contato;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.service.RestService;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.service.RestServiceFactory;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.util.MessageUtil;

public class SignupActivity extends BaseActivity {

    private RestService rest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        rest = RestServiceFactory.getRestService(this);

        Button btnSignup = (Button) findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(signup());
    }



    private View.OnClickListener signup(){
        final Activity activity = this;

        final Response.Listener<Contato> success = new Response.Listener<Contato>() {
            @Override
            public void onResponse(Contato response) {
                MessageUtil.dialog(activity, "Id criado: " + response.getId(), "Success!", null);
            }
        };

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    validateForm();
                }catch (Exception e){
                    MessageUtil.dialog(activity, e.getMessage(), "Error!", null);
                }

                EditText txtName = (EditText) findViewById(R.id.txtCompleteName);
                EditText txtNickname = (EditText) findViewById(R.id.txtNickname);

                rest.newContato(txtName.getText().toString(), txtNickname.getText().toString(), success);
            }
        };
    }

    private void validateForm(){
        EditText txtName = (EditText) findViewById(R.id.txtCompleteName);
        EditText txtNickname = (EditText) findViewById(R.id.txtNickname);

        if(txtName.getText().toString().isEmpty() || txtNickname.getText().toString().isEmpty())
            throw new RuntimeException("All fields are required.");
    }
}
