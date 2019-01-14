package com.example.kratirastogi.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Welcome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        Handler handler=new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sharedPreferences = getSharedPreferences("details", MODE_PRIVATE);
                String id=sharedPreferences.getString("name","");
                if(id.equals(""))

                {
                    Intent obj=new Intent(Welcome.this,Registration.class);
                    startActivity(obj);
                    Welcome.this.finish();
                }
                else {
                    Intent ob=new Intent(Welcome.this,Login.class);
                    startActivity(ob);
                    Welcome.this.finish();
                }
            }
        },4000);
    }
}
