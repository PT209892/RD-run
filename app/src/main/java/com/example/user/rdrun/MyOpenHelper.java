package com.example.user.rdrun;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by user on 9/2/2016.
 */
public class MyOpenHelper extends SQLiteOpenHelper{
    //Explicit
    public static final String database_name = "rdRun.db";
    private static final int database_version = 1;
    private static final String create_user_table = "create table userTable ("+
            "_id integer primary key, "+
            "User text,"+
            "Password text,"+
            "Name text,"+
            "SurName text,"+
            "Avata text,"+
            "idUser text);";

    public MyOpenHelper(Context context) {
        super(context,database_name,null,database_version);
    }//Constructor

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(create_user_table);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}//Main Class
