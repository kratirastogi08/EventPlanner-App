package com.example.kratirastogi.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import com.example.kratirastogi.myapplication.dbutil.EventConstants;
import com.example.kratirastogi.myapplication.dbutil.EventManager;

import java.util.ArrayList;

public class Purchase extends AppCompatActivity {
    EventManager eventManager;
    SQLiteDatabase sqLiteDatabase;
    ListView listView;
     String data1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchase);
        eventManager = new EventManager(this);
        sqLiteDatabase = eventManager.opendb();
        listView = findViewById(R.id.listview);
        Intent intent=getIntent();
        //String d= intent.getStringExtra("k");
        data1 = Home.getActivityInstance().getData1();
       String[] val = new String[]{data1};

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayList<com.example.kratirastogi.myapplication.bean.Purchase> ret = new ArrayList<com.example.kratirastogi.myapplication.bean.Purchase>();

        Cursor c = sqLiteDatabase.query(EventConstants.Todolist, null, EventConstants.COL_EVNTID+"=?", val, null, null, null);
        if (c != null && c.moveToFirst()) {


            do {

                String name = c.getString(c.getColumnIndex(EventConstants.COL_TASK));

                com.example.kratirastogi.myapplication.bean.Purchase purchase = new com.example.kratirastogi.myapplication.bean.Purchase();
                purchase.setChecked(true);
                purchase.setName(name);

                ret.add(purchase);

            } while (c.moveToNext());


        }
        final PurAdapter listViewDataAdapter = new PurAdapter(ret,Purchase.this );

        listViewDataAdapter.notifyDataSetChanged();

        listView.setAdapter(listViewDataAdapter);

    }

    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}

