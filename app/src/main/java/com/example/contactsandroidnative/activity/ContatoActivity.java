package com.example.contactsandroidnative.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.FileProvider;

import com.example.contactsandroidnative.BuildConfig;
import com.example.contactsandroidnative.R;
import com.example.contactsandroidnative.data.dao.ContatoDAO;
import com.example.contactsandroidnative.data.model.Contato;
import com.example.contactsandroidnative.util.ImageUtils;
import com.example.contactsandroidnative.util.MaskUtil;

import java.io.File;

public class ContatoActivity extends AppCompatActivity {

    public static final String PARAM = "contato";
    private static final int CAMERA_REQUEST_CODE = 495;

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

        MaskUtil.putMask(txtCel, "(NN)NNNNN-NNNN");

        imgContato = findViewById(R.id.img_contato);

        imgContato.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openCamera();
            }
        });

        Intent intent = getIntent();

        if(intent.hasExtra(PARAM)){
            contato = (Contato) intent.getSerializableExtra(PARAM);

            ImageUtils.setImage(imgContato, contato.getImagem());
        } else {
            contato = new Contato();
        }
    }

    private void openCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        String pathImage = getExternalFilesDir(null) + "/" + System.currentTimeMillis()+".jpg";
        contato.setImagem(pathImage);

        File picture = new File(pathImage);
        intent.putExtra(
                MediaStore.EXTRA_OUTPUT,
                FileProvider.getUriForFile(
                        this, BuildConfig.APPLICATION_ID + ".fileprovider",
                        picture
                )
        );

        startActivityForResult(intent, CAMERA_REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode == CAMERA_REQUEST_CODE && resultCode == Activity.RESULT_OK){
            ImageUtils.setImage(imgContato, contato.getImagem());
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

        try {
            contato.setNome(txtNome.getText().toString());
            contato.setEmail(txtEmail.getText().toString());
            contato.setCel(txtCel.getText().toString());

            ContatoDAO dao = new ContatoDAO(this);

            if(contato.getId() != null){
                dao.atualizar(contato);
            } else {
                dao.salvar(contato);
            }

            setResult(Activity.RESULT_OK);
        } catch (Exception e){
            e.printStackTrace();

            setResult(Activity.RESULT_CANCELED);
        } finally {
            finish();
        }

    }
}
