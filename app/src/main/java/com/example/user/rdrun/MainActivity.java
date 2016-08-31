package com.example.user.rdrun;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }   //Main Method นี่คือเมธอด
    //Get Event from Click Butoon
    //Shit+Ctrl+Enter คือ complementKey เพื่อใส่รูปแบบให้ถูกต้อง
    public void clickSignUpMain(View view) {
        startActivity(new Intent(MainActivity.this,SignUpActivity.class));

    }

}   //Main Class นี่คือ คลาสหลัก

