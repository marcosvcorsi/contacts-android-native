package com.example.contactsandroidnative.data.dao;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import com.example.contactsandroidnative.data.helper.DBHelper;

public class AbstractDAO {

    protected DBHelper dbHelper;

    protected Context context;

    public AbstractDAO(Context context){
        this.context = context;
        this.dbHelper = new DBHelper(context);
    }

    protected Long insert(String table, ContentValues cv){
        SQLiteDatabase db = dbHelper.getWritableDatabase();

        Long id = db.insert(table, null, cv);

        db.close();

        return id;
    }

}
