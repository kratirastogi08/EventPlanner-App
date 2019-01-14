package com.example.kratirastogi.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.kratirastogi.myapplication.dbutil.EventConstants;
import com.example.kratirastogi.myapplication.dbutil.EventManager;

public class EditGuestList extends AppCompatActivity implements View.OnClickListener {
EventManager eventManager;
SQLiteDatabase sqLiteDatabase;
EditText name,pno,email;
RadioGroup rd;
RadioButton at,dec;
Button sav;
String status;
    String stuff;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_guest_list);
        eventManager=new EventManager(this);
        sqLiteDatabase=eventManager.opendb();
        name=findViewById(R.id.name);
        pno=findViewById(R.id.phno);
        email=findViewById(R.id.email);
        rd=findViewById(R.id.rd1);
        at=findViewById(R.id.Attending);
        dec=findViewById(R.id.Decline);
        sav=findViewById(R.id.savebtn);
        sav.setOnClickListener(this);
        Intent i=getIntent();
         stuff=i.getStringExtra("abcd");
        String val[]={stuff};
        String col[]={EventConstants.COL_GUESTNM,EventConstants.COL_STATUS,EventConstants.COL_PHONE,EventConstants.COL_EMAIL};
        Cursor c1=sqLiteDatabase.query(EventConstants.GuestTable,col,EventConstants.COL_GUESTNM+ "=?",val,null,null,null);
        if(c1!=null && c1.moveToFirst())
        {
            Log.d("msg","hhhhhhh");
            String nm=c1.getString(c1.getColumnIndex(EventConstants.COL_GUESTNM));
            name.setText(nm);

            String s=c1.getString(c1.getColumnIndex(EventConstants.COL_STATUS));
            Toast.makeText(this, ""+s, Toast.LENGTH_SHORT).show();
            if(s.equals("Attending"))
            {
                at.setChecked(true);
            }
            if(s.equals("Decline"))
            {
                dec.setChecked(true);
            }


            String p=c1.getString(c1.getColumnIndex(EventConstants.COL_PHONE));
            pno.setText(p);
            String mail=c1.getString(c1.getColumnIndex(EventConstants.COL_EMAIL));
            email.setText(mail);

        }

    }


    @Override
    public void onClick(View v) {
        if(at.isChecked())
        {
            status=at.getText().toString();
        }
        if(dec.isChecked())
        {
            status=dec.getText().toString();
        }
        String nm = name.getText().toString();
        String phone = pno.getText().toString();
        String em = email.getText().toString();

        if (TextUtils.isEmpty(phone)) {
            pno.setError("please enter phone number");
        }  else {
            ContentValues contentValues = new ContentValues();


            contentValues.put(EventConstants.COL_STATUS, status);
            contentValues.put(EventConstants.COL_PHONE, phone);
            contentValues.put(EventConstants.COL_EMAIL, em);

            String[] values = {stuff};
            int r = sqLiteDatabase.update(EventConstants.GuestTable, contentValues, EventConstants.COL_GUESTNM + "=?", values);
            if (r > 0) {
                Toast.makeText(getApplicationContext(), "data updated", Toast.LENGTH_SHORT).show();
                Intent intent=new Intent(this,Main4Activity.class);
                startActivity(intent);

                EditGuestList.this.finish();

            }
        }
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
