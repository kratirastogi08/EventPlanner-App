package com.example.kratirastogi.myapplication;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class Main4Activity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main5);
        getSupportFragmentManager().beginTransaction().add(R.id.frag,new GuestList()).commit();
    }
}
