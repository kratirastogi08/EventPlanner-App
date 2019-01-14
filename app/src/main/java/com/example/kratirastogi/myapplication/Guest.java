package com.example.kratirastogi.myapplication;

import android.app.Activity;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kratirastogi.myapplication.bean.SelEvent;
import com.example.kratirastogi.myapplication.dbutil.EventConstants;
import com.example.kratirastogi.myapplication.dbutil.EventManager;

import java.util.ArrayList;

public class Guest extends AppCompatActivity implements View.OnClickListener{

    EditText name,phno,email;
Button savebtn;
RadioButton male,female,child,adult,sen,Attending,Decline;
EventManager eventManager;
SQLiteDatabase sqLiteDatabase;
String gender,cat,item,status;
RadioGroup rd,group,rd1;
Spinner spinner;
SelEvent selEvent;
String data,d,data1,d1;
String val[];

ArrayList<SelEvent> selEvents;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guest);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        name=findViewById(R.id.name);
        phno=findViewById(R.id.phno);
        email=findViewById(R.id.email);
        savebtn=findViewById(R.id.savebtn);
        male=findViewById(R.id.male);
        female=findViewById(R.id.female);
        child=findViewById(R.id.child);
        adult=findViewById(R.id.adult);
        sen=findViewById(R.id.sen);
        rd=findViewById(R.id.rd);
        Attending=findViewById(R.id.Attending);
        Decline=findViewById(R.id.Decline);
        rd1=findViewById(R.id.rd1);
        group=findViewById(R.id.group);
        savebtn.setOnClickListener(this);
        eventManager=new EventManager(this);
        sqLiteDatabase=eventManager.opendb();
       // spinner=findViewById(R.id.spinner);
        selEvents=new ArrayList<>();
        data=Home.getActivityInstance().getData();
        data1=Home.getActivityInstance().getData1();
        Intent intent=getIntent();
      //d=   intent.getStringExtra("keyy");
        d1=   intent.getStringExtra("keyy1");

        //val= new String[]{data};

       /* Cursor c = sqLiteDatabase.query(EventConstants.EventTable, null, null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            do {
                String evnm = c.getString(c.getColumnIndex(EventConstants.COL_EVENM));
              selEvent  = new SelEvent(evnm);
                selEvents.add(selEvent);
            } while (c.moveToNext());
        }
        c.close();
        final ArrayAdapter<SelEvent> arrayAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, selEvents);
        spinner.setAdapter(arrayAdapter);
        spinner.setOnItemSelectedListener(this);*/

    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.savebtn) {
            if (male.isChecked())
                gender = male.getText().toString();
            else if (female.isChecked())
                gender = female.getText().toString();
            else
                gender = null;
            if (child.isChecked())
                cat = child.getText().toString();
            else if (adult.isChecked())
                cat = adult.getText().toString();
            else if (sen.isChecked())
                cat = sen.getText().toString();
            else
                cat = null;
            if(Attending.isChecked())
            {
               status=Attending.getText().toString();
            }
            else if(Decline.isChecked())
            {
                status=Decline.getText().toString();
            }
            else
            {
                status="null";
            }

            String nm = name.getText().toString();
            String ph = phno.getText().toString();
            String em = email.getText().toString();
            if(em.length()==0)
            {
                em="null";
            }
            int st=rd1.getCheckedRadioButtonId();
            int id = rd.getCheckedRadioButtonId();
            int id1 = group.getCheckedRadioButtonId();
            if (TextUtils.isEmpty(nm)) {
                name.setError("please enter name");
            }  else if (TextUtils.isEmpty(ph)) {
                phno.setError("please enter phone number");
            }


             else {
                ContentValues contentValues = new ContentValues();
                contentValues.put(EventConstants.COL_EVNTID, d1);
               // contentValues.put(EventConstants.COL_EVENM, d);
                contentValues.put(EventConstants.COL_GUESTNM, nm);
                contentValues.put(EventConstants.COL_GUESTGEN, gender);
                contentValues.put(EventConstants.COL_GUESTAGE, cat);
                contentValues.put(EventConstants.COL_PHONE, ph);
                contentValues.put(EventConstants.COL_EMAIL, em);
                contentValues.put(EventConstants.COL_STATUS,status);
               // contentValues.put(EventConstants.COL_ALL,"All");
                long rownum = sqLiteDatabase.insert(EventConstants.GuestTable, null, contentValues);
                if (rownum > 0) {
                    Toast.makeText(Guest.this, "data saved", Toast.LENGTH_SHORT).show();
                   Intent intent=new Intent(this,GuestAct.class);
                   startActivity(intent);
                   Guest.this.finish();



                }

            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.setting, menu);

        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

            case R.id.set:
                openSettings();
                return true;


        }
        return super.onOptionsItemSelected(item);
    }

    private void openSettings() {
        Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
        startActivityForResult(intent, 1);
    }
    public void onActivityResult(int reqCode, int resultCode, Intent data) {
        super.onActivityResult(reqCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            Uri contactData = data.getData();
            Cursor c = managedQuery(contactData, null, null, null, null);
            if (c.moveToFirst()) {
                String nm = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                name.setText(nm);

                ContentResolver cr = getContentResolver();
                Cursor cursor = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
                        "DISPLAY_NAME = '" + nm + "'", null, null);
                if (cursor.moveToFirst()) {
                    String contactId =
                            cursor.getString(cursor.getColumnIndex(ContactsContract.Contacts._ID));
                    //
                    //  Get all phone numbers.
                    //
                    Cursor phones = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                    while (phones.moveToNext()) {
                        String number = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        phno.setText(number);
                    }
                   // phones.close();
                }
               // cursor.close();
                Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null, "DISPLAY_NAME = '" + nm + "'", null, null);
                if (cur.getCount() > 0) {
                    while (cur.moveToNext()) {
                        String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                        Cursor cur1 = cr.query(
                                ContactsContract.CommonDataKinds.Email.CONTENT_URI, null,
                                ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                                new String[]{id}, null);
                        while (cur1.moveToNext()) {
                            //to get the contact names

                            String email1 = cur1.getString(cur1.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));

                            if (email1 != null)
                               email.setText(email1);

                        }
                       // cur1.close();
                    }
                   // cur.close();

                   // c.close();
                }
            }

        }
    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }



   /* @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        item=spinner.getSelectedItem().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        item=null;

    }*/
}
