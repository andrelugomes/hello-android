package br.edu.ifspsaocarlos.sdm.what_ifsp_zap.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.Response;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.IdComparator;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.R;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.adapter.MensagemArrayAdapter;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.model.Contato;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.model.Mensagem;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.repositoty.ContatoRepository;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.repositoty.ContatoRepositoryFactory;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.service.UserService;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.service.message.MessageRestService;
import br.edu.ifspsaocarlos.sdm.what_ifsp_zap.service.message.MessageRestServiceFactory;

public class MessageActivity extends AppCompatActivity {

    private Contato c;
    private ContatoRepository repository = ContatoRepositoryFactory.getRepository(this);
    private MessageRestService service;
    private ListView list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menssage);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        service = MessageRestServiceFactory.getService(this);
        list = (ListView) findViewById(R.id.listMessages);

        Button btnSignup = (Button) findViewById(R.id.sendMessage);
        btnSignup.setOnClickListener(sendMessage());

        if (getIntent().hasExtra("contact")) {
            this.c = (Contato) getIntent().getSerializableExtra("contact");

            Contato contato = repository.buscaPorId(c.getId());
            if (contato.getId() == null){
                repository.createContact(new Contato(c.getId(),c.getNomeCompleto(),c.getApelido()));
            }


            historicoDeMensagens(c.getId(), UserService.getInstance().getId(), 1);
        }

    }

    private View.OnClickListener sendMessage() {
        return null;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mensagem, menu);
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
                //repository.deleteContact(c);
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

    }

    public void historicoDeMensagens(final Integer idRemetemte, final Integer idDestino, final Integer lastMessageId) {
        final Activity messageActivity = this;
        final List<Mensagem> mensagensEnviadas = new ArrayList<>();
        final List<Mensagem> mensagensJuntas =  new ArrayList<>();

        service.getMensagens(lastMessageId, idRemetemte, idDestino,
                new Response.Listener<List<Mensagem>>() {
                    @Override
                    public void onResponse(List<Mensagem> mensagens) {
                        mensagensEnviadas.addAll(mensagens);

                        service.getMensagens(lastMessageId, idDestino, idRemetemte,
                                new Response.Listener<List<Mensagem>>() {
                                    @Override
                                    public void onResponse(List<Mensagem> mensagens) {
                                        mensagensJuntas.addAll(mensagens);
                                        mensagensJuntas.addAll(mensagensEnviadas);

                                        Collections.sort(mensagensJuntas, new IdComparator());
                                        MensagemArrayAdapter arrayAdapter = new MensagemArrayAdapter(messageActivity, mensagensJuntas);
                                        list.setAdapter(arrayAdapter);
                                        list.smoothScrollToPosition(arrayAdapter.getCount());
                                    }
                                }
                        );
                    }
                }
        );
    }

}
