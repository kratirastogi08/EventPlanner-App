package com.example.kratirastogi.myapplication;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Registration extends AppCompatActivity implements View.OnClickListener {
EditText pass,name;
Button regbtn;
SharedPreferences sharedPreferences;
SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        name=findViewById(R.id.name);
        pass=findViewById(R.id.pass);
        regbtn=findViewById(R.id.regbtn);
        regbtn.setOnClickListener(this);
        sharedPreferences=getSharedPreferences("details",MODE_PRIVATE);
        editor=sharedPreferences.edit();

    }

    @Override
    public void onClick(View v) {
     String nm=name.getText().toString();
     String pswd=pass.getText().toString();
     if(TextUtils.isEmpty(nm))
     {
         name.setError("please enter your name");
     }
       else if(TextUtils.isEmpty(pswd))
        {
            pass.setError("please enter password");
        }
        else if(pswd.length()<8)
     {
         Toast.makeText(this, "password should be greater than 8 characters", Toast.LENGTH_SHORT).show();
     }
     else
     {
         editor.putString("name",nm);
         editor.putString("pass",pswd);
         editor.commit();
         Toast.makeText(Registration.this, "datawritten", Toast.LENGTH_SHORT).show();
         Intent intent = new Intent(this, Login.class);
         startActivity(intent);
     }
    }
}
