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

import android.view.ContextMenu;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private ListView listViewContato;
    private List<Contato> contatoList;
    private ContatoDAO dao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.init();
    }

    private void init(){
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openContato(null);
            }
        });

       this.dao = new ContatoDAO(this);
       this.contatoList = dao.listContatos();

       this.listViewContato = findViewById(R.id.list_view_contato);
       this.listViewContato.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Contato contato = contatoList.get(i);

               openContato(contato);
           }
       });

       registerForContextMenu(listViewContato);
    }

    private void openContato(Contato contato){
        Intent intent = new Intent(MainActivity.this, ContatoActivity.class);

        if(contato != null){
            intent.putExtra(ContatoActivity.PARAM, contato);
        }

        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();

        this.updateListView();
    }

    private void updateListView(){
        this.listViewContato.setAdapter(new ListaContatoAdapter(this, this.contatoList));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        final Contato contato = (Contato) listViewContato.getItemAtPosition(info.position);

        MenuItem del = menu.add("Delete");

        del.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                ContatoDAO dao = new ContatoDAO(MainActivity.this);
                dao.deletar(contato);

                updateListView();

                return false;
            }
        });
    }

}
