package com.example.contactsandroidnative;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;

import com.example.contactsandroidnative.activity.ContatoActivity;
import com.example.contactsandroidnative.data.dao.ContatoDAO;
import com.example.contactsandroidnative.data.model.Contato;
import com.example.contactsandroidnative.ui.adapter.ListaContatoAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.telephony.SmsManager;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE_PERMISSION = 540;

    private ListView listViewContato;
    private List<Contato> contatoList;
    private ContatoDAO dao;

    private String[] permissions = new String[]{
            Manifest.permission.SEND_SMS
    };

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

       this.listViewContato = findViewById(R.id.list_view_contato);
       this.listViewContato.setOnItemClickListener(new AdapterView.OnItemClickListener() {
           @Override
           public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
               Contato contato = contatoList.get(i);

               openContato(contato);
           }
       });

       registerForContextMenu(listViewContato);
       checkPermissions(permissions);
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
        this.contatoList = dao.listContatos();

        this.listViewContato.setAdapter(new ListaContatoAdapter(this, this.contatoList));
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        final AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) menuInfo;

        final Contato contato = (Contato) listViewContato.getItemAtPosition(info.position);

        MenuItem sms = menu.add("Enviar SMS");


        sms.setOnMenuItemClickListener(new MenuItem.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem menuItem) {
                LayoutInflater inflater = LayoutInflater.from(MainActivity.this);
                View view = inflater.inflate(R.layout.dialog_envia_sms, null);

                TextView dialogCel = view.findViewById(R.id.dialog_sms_cel);
                final EditText dialogMsg = view.findViewById(R.id.dialog_sms_edit);

                String[] split = contato.getNome().split(" ");
                String strContato = "Para: "+ split[0] + " " + contato.getCel();

                dialogCel.setText(strContato);

                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle(getString(R.string.app_name));
                builder.setView(view);

                builder.setPositiveButton("Enviar", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        String msg = dialogMsg.getText().toString();

                        if(msg != null && !msg.isEmpty()){
                            enviarSMS(contato.getCel(), msg);
                        }
                    }
                });

                builder.setNegativeButton("Cancelar", null);

                AlertDialog alertDialog = builder.create();
                alertDialog.show();

                return false;
            }
        });

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

    private void enviarSMS(String cel, String msg) {
        try {
            String celNumber = cel.replace("(", "")
                                  .replace(")", "")
                                  .replace(" ", "")
                                  .replace("-", "");

            celNumber = "+55".concat(celNumber);

            SmsManager smsManager = SmsManager.getDefault();
            smsManager.sendTextMessage(celNumber, null, msg, null, null);
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        for(int result: grantResults){
            if(result == PackageManager.PERMISSION_DENIED){
                alertPermission();
            }
        }
    }

    private void alertPermission() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);

        builder.setTitle("Permissões negadas");
        builder.setMessage("Para utilizar este aplicativo, é necessário aceitar as permissões");
        builder.setPositiveButton("Tentar novamente", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                checkPermissions(permissions);
            }
        });

        builder.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                finish();
            }
        });

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    private void checkPermissions(String[] permissions){
        if(Build.VERSION.SDK_INT >= 23){
            List<String> permissionList = new ArrayList<>();

            for(String permission : permissions){
                if(ContextCompat.checkSelfPermission(this, permission) != PackageManager.PERMISSION_GRANTED){
                    permissionList.add(permission);
                }
            }

            if(!permissionList.isEmpty()){
                String[] newPermissions = new String[permissionList.size()];

                permissionList.toArray(newPermissions);

                ActivityCompat.requestPermissions(this, newPermissions, REQUEST_CODE_PERMISSION);
            }
        }
    }
}
