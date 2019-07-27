package com.example.contactsandroidnative.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.contactsandroidnative.R;
import com.example.contactsandroidnative.data.dao.ContatoDAO;
import com.example.contactsandroidnative.data.model.Contato;

public class ContatoActivity extends AppCompatActivity {

    public static final String PARAM = "contato";

    private Contato contato;

    private EditText txtNome;
    private EditText txtEmail;
    private EditText txtCel;
    private ImageView imgContato;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contato);

        txtNome = findViewById(R.id.txt_nome);
        txtEmail = findViewById(R.id.txt_email);
        txtCel = findViewById(R.id.txt_cel);
        imgContato = findViewById(R.id.img_contato);

        Intent intent = getIntent();

        if(intent.hasExtra(PARAM)){
            contato = (Contato) intent.getSerializableExtra(PARAM);
        } else {
            contato = new Contato();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_contato, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if(item.getItemId() == R.id.menu_contato_save){
            salvarContato();
        }


        return super.onOptionsItemSelected(item);
    }

    private void salvarContato(){
        contato.setNome(txtNome.getText().toString());
        contato.setEmail(txtEmail.getText().toString());
        contato.setCel(txtCel.getText().toString());

        ContatoDAO dao = new ContatoDAO(this);

        if(contato.getId() != null){
            dao.atualizar(contato);
        } else {
            dao.salvar(contato);
        }
    }

}
