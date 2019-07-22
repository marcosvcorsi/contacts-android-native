package com.example.contactsandroidnative.data.dao;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.contactsandroidnative.data.model.Contato;

import java.util.ArrayList;
import java.util.List;

public class ContatoDAO extends AbstractDAO{

    public ContatoDAO(Context context) {
        super(context);
    }

    public List<Contato> listContatos(){
        SQLiteDatabase db = dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM contato WHERE situacao = ?", new String[]{"1"});

        List<Contato> contatoList = new ArrayList<>();

        if(cursor != null){
            while (cursor.moveToNext()){
                Contato contato = new Contato();

                contato.setId(cursor.getInt(cursor.getColumnIndex("id")));
                contato.setNome(cursor.getString(cursor.getColumnIndex("nome")));
                contato.setEmail(cursor.getString(cursor.getColumnIndex("email")));
                contato.setCel(cursor.getString(cursor.getColumnIndex("cel")));
                contato.setImagem(cursor.getString(cursor.getColumnIndex("imagem")));
                contato.setExcluido(1);

                contatoList.add(contato);
            }

            cursor.close();
            db.close();
        }

        return contatoList;
    }

}
