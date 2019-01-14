package com.example.kratirastogi.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kratirastogi.myapplication.dbutil.EventManager;

public class Login extends AppCompatActivity implements View.OnClickListener {
EditText passwrd,nm;
Button loginbtn;
SQLiteDatabase sqLiteDatabase;
EventManager eventManager;
String name,pass;
SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login2);
        passwrd=findViewById(R.id.passwrd);
        nm=findViewById(R.id.nm);
        loginbtn=findViewById(R.id.loginbtn);
        loginbtn.setOnClickListener(this);
         eventManager=new EventManager(this);
         sqLiteDatabase=eventManager.opendb();
         sharedPreferences=getSharedPreferences("details",MODE_PRIVATE);
         name=sharedPreferences.getString("name","");
         pass=sharedPreferences.getString("pass","");


    }

    @Override
    public void onClick(View v) {
        String n=nm.getText().toString();
        String p=passwrd.getText().toString();
        if(TextUtils.isEmpty(n))
        {
            nm.setError("please enter name");

        }
       else if(TextUtils.isEmpty(p))
        {
            passwrd.setError("please enter password");

        }
        else if(n.equals(name) && p.equals(pass))
        {
            Intent intent=new Intent(this,MainActivity.class);
            startActivity(intent);
            Login.this.finish();
        }
        else
        {
            Toast.makeText(this, "invalid username/password", Toast.LENGTH_SHORT).show();
        }

    }
}
