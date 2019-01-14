package com.example.kratirastogi.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class GuestAct extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest2);
        getSupportFragmentManager().beginTransaction().add(R.id.frag,new GuestList()).commit();





    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
       finish();
    }
}
