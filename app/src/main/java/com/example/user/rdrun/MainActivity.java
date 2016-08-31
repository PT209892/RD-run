package com.example.user.rdrun;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity {
    //Explicit
    private ImageView imageView;
    private EditText userEditText, passEditText;
    private String userString,passString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Bind Widget
        imageView = (ImageView) findViewById(R.id.imageView6);
        userEditText = (EditText) findViewById(R.id.editText5);
        passEditText= (EditText) findViewById(R.id.editText6);
        //Load Image from Server
        Picasso.with(this).load("http://swiftcodingthai.com/rd/Image/rd_logo.png")
                .resize(150,150).into(imageView);

    }   //Main Method นี่คือเมธอด

    //Create Inner Class
    private class SynUser extends AsyncTask<Void, Void, String> {
        //Explicit
        private Context context;
        private String myUserString, myPasswordString;
        private static final String urlJSon = "http://swiftcodingthai.com/rd/get_user_master.php";

        public SynUser(Context context, String myUserString, String myPasswordString) {
            this.context = context;
            this.myUserString = myUserString;
            this.myPasswordString = myPasswordString;

        }

        @Override
        protected String doInBackground(Void... params) {

            try {
                OkHttpClient okHttpClient = new OkHttpClient();
                Request.Builder builder = new Request.Builder();
                Request request = builder.url(urlJSon).build();
                Response response = okHttpClient.newCall(request).execute();
                return response.body().string();
            } catch (Exception e) {
                Log.d("31AugV2", "e doInback ==>" + e.toString());
                return null;
            }

        }//doInBack

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.d("31AugV2", "Json ==>" + s);


        }// OnPost
    }    //SynUser Class

    public void clickSignInMain(View view) {
        userString=userEditText.getText().toString().trim();
        passString = passEditText.getText().toString().trim();
        //Check Space
        if (userString.equals("")||passString.equals("")) {
            //Have space
            MyAlert myAlert = new MyAlert();
            myAlert.myDialog(this, R.drawable.rat48,"Have Space","Please fill All Every Blank");
        } else {
          //No Space
            SynUser synUser = new SynUser(this, userString,passString);
            synUser.execute();
        }

    }


    //Get Event from Click Butoon
    //Shit+Ctrl+Enter คือ complementKey เพื่อใส่รูปแบบให้ถูกต้อง
    public void clickSignUpMain(View view) {
        startActivity(new Intent(MainActivity.this,SignUpActivity.class));

    }

}   //Main Class นี่คือ คลาสหลัก

