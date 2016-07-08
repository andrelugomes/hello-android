package br.edu.ifspsaocarlos.sdm.what_ifsp_zap.activity;


import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.Response;

import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.R;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.model.Contato;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.service.RestService;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.service.RestServiceFactory;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.service.UserService;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.util.MessageUtil;

/**
 *
 * Activity para controlar o cadastro do usuário ou redireciona-lo caso já exista um id criado em Shared Preference.
 *
 */
public class SignupActivity extends BaseActivity {

    private RestService rest;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //Verifica se já existe usuário criado
        int id = getUserIdFromSharedPreference();
        if(id != -1){
            UserService.getInstance().setId(id);
            goToMainActivity();
            return;
        }

        setContentView(R.layout.signin_activity);

        rest = RestServiceFactory.getRestService(this);

        Button btnSignup = (Button) findViewById(R.id.btnSignup);
        btnSignup.setOnClickListener(signup());
    }



    private View.OnClickListener signup(){
        final Activity activity = this;

        final Response.Listener<Contato> success = new Response.Listener<Contato>() {
            @Override
            public void onResponse(Contato response) {
                UserService.getInstance().setId(response.getId());
                setUserIdFromSharedPreference(response.getId());
                goToMainActivity();
            }
        };

        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                try{
                    validateForm();
                }catch (Exception e){
                    MessageUtil.dialog(activity, e.getMessage(), "Error!", null);
                    return;
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

    private void goToMainActivity(){
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
        finish();
    }

    private Integer getUserIdFromSharedPreference(){
        SharedPreferences sp = getSharedPreferences(getResources().getString(R.string.user_shared_preferences), Context.MODE_PRIVATE);
        return sp.getInt(getResources().getString(R.string.user_id), -1);
    }

    private void setUserIdFromSharedPreference(Integer id){
        SharedPreferences sp = getSharedPreferences(getResources().getString(R.string.user_shared_preferences), Context.MODE_PRIVATE);
        sp.edit().putInt(getResources().getString(R.string.user_id), id).commit();
    }
}
