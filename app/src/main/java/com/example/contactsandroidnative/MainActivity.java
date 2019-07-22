package com.example.contactsandroidnative;

import android.os.Bundle;

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
                Toast.makeText(MainActivity.this, "Teste", Toast.LENGTH_SHORT).show();
            }
        });

        ListView listViewContato = findViewById(R.id.list_view_contato);
        listViewContato.setAdapter(new ListaContatoAdapter(this, getContatoList()));
    }

    private List<Contato> getContatoList(){
        List<Contato> contatoList = new ArrayList<>();

        for(int i = 1; i <= 10; i++){
            contatoList.add(new Contato("Teste" + i,
                    "teste" + i + "@contato.com.br"));
        }

        return contatoList;
    }
}
