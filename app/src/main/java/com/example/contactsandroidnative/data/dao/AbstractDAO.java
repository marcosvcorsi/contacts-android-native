package com.example.contactsandroidnative.data.dao;

import android.content.Context;

import com.example.contactsandroidnative.data.helper.DBHelper;

public class AbstractDAO {

    protected DBHelper dbHelper;

    protected Context context;

    public AbstractDAO(Context context){
        this.context = context;
        this.dbHelper = new DBHelper(context);
    }
}
