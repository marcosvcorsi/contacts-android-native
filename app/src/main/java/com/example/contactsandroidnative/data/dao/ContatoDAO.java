package com.example.contactsandroidnative.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.contactsandroidnative.data.model.Contato;

import java.util.ArrayList;
import java.util.List;

public class ContatoDAO extends AbstractDAO{

    public ContatoDAO(Context context) {
        super(context);
    }

    public Long salvar(Contato contato){
        return insert("contato", getContentValues(contato));
    }

    public void atualizar(Contato contato){
        update("contato", getContentValues(contato), contato.getId());
    }

    private ContentValues getContentValues(Contato contato){
        ContentValues cv = new ContentValues();
        cv.put("nome", contato.getNome());
        cv.put("email", contato.getEmail());
        cv.put("cel", contato.getCel());
        cv.put("imagem", contato.getImagem());
        cv.put("situacao", contato.getExcluido() != null ? contato.getExcluido() : 1);

        return cv;
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

    public void deletar(Contato contato) {
        contato.setExcluido(0);

        this.atualizar(contato);
    }
}
