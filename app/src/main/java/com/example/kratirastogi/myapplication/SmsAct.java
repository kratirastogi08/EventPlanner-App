package com.example.kratirastogi.myapplication;

import android.app.PendingIntent;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SmsAct extends AppCompatActivity implements View.OnClickListener{
Intent in;
String number,msg;
EditText phn,sms;
Button send;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sms);
        in=getIntent();
        number=in.getStringExtra("key");
        phn=findViewById(R.id.phn);
        phn.setText(number);
        sms=findViewById(R.id.sms);
        send=findViewById(R.id.send);
        send.setOnClickListener(this);

    }




    @Override
    public void onClick(View v) {
        msg=sms.getText().toString();
        SmsManager sms1= SmsManager.getDefault();
        // Intent obj=new Intent(this,SmsAct.class);
        PendingIntent pi=PendingIntent.getActivity(this,1,in,PendingIntent.FLAG_ONE_SHOT);
        sms1.sendTextMessage(number,"",msg,pi,null);
        Toast.makeText(this, "messagesent", Toast.LENGTH_SHORT).show();

        SmsAct.this.finish();
    }
}
