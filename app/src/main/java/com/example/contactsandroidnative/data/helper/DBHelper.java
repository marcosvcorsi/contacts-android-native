package com.example.contactsandroidnative.data.helper;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {


    public static final String DB_NAME = "agenda_db";
    public static final int DB_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        this.createTableContato(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    private void createTableContato(SQLiteDatabase sqLiteDatabase){
        StringBuffer createTableContato = new StringBuffer();

        createTableContato.append("CREATE TABLE contato (");
        createTableContato.append("id INTEGER PRIMARY KEY AUTO INCREMENT, ");
        createTableContato.append("nome TEXT, ");
        createTableContato.append("email TEXT, ");
        createTableContato.append("cel TEXT, ");
        createTableContato.append("imagem TEXT, ");
        createTableContato.append("situacao INT DEFAULT 1)");

        sqLiteDatabase.execSQL(createTableContato.toString());
    }
}
