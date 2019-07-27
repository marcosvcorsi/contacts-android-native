package com.example.contactsandroidnative;

import android.content.Intent;
import android.os.Bundle;

import com.example.contactsandroidnative.activity.ContatoActivity;
import com.example.contactsandroidnative.data.dao.ContatoDAO;
import com.example.contactsandroidnative.data.model.Contato;
import com.example.contactsandroidnative.ui.adapter.ListaContatoAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ContatoActivity.class);
                startActivity(intent);
            }
        });

        ListView listViewContato = findViewById(R.id.list_view_contato);
        listViewContato.setAdapter(new ListaContatoAdapter(this, getContatoList()));
    }

    private List<Contato> getContatoList(){
        ContatoDAO dao = new ContatoDAO(this);
        List<Contato> contatoList = dao.listContatos();

        return contatoList;
    }
}
