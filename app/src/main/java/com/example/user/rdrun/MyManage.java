package com.example.user.rdrun;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by user on 9/2/2016.
 */
public class MyManage {
    //Explicit
    private MyOpenHelper myOpenHelper;
    private SQLiteDatabase sqLiteDatabase;

    public MyManage(Context context) {
        myOpenHelper = new MyOpenHelper(context);
        sqLiteDatabase = myOpenHelper.getWritableDatabase();

    }//Constructor
}//MainClass

