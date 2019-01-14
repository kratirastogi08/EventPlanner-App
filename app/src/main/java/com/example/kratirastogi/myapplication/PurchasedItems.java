package com.example.kratirastogi.myapplication;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;

import com.example.kratirastogi.myapplication.bean.Purchase;
import com.example.kratirastogi.myapplication.dbutil.EventConstants;
import com.example.kratirastogi.myapplication.dbutil.EventManager;

import java.util.ArrayList;
import java.util.List;

public class PurchasedItems extends AppCompatActivity {
    EventManager eventManager;
    SQLiteDatabase sqLiteDatabase;
    ListView listView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_purchased_items);
        eventManager = new EventManager(this);
        sqLiteDatabase = eventManager.opendb();
        listView = findViewById(R.id.listview);
        Intent intent=getIntent();
       //String d= intent.getStringExtra("k");
        String d1= intent.getStringExtra("k1");
       String val[]={d1};

        listView.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);
        ArrayList<Purchase> ret = new ArrayList<Purchase>();

        Cursor c = sqLiteDatabase.query(EventConstants.Todolist, null, EventConstants.COL_EVNTID+"=?", val, null, null, null);
        if (c != null && c.moveToFirst()) {


            do {

                String name = c.getString(c.getColumnIndex(EventConstants.COL_TASK));

                Purchase purchase = new Purchase();
                purchase.setChecked(true);
                purchase.setName(name);

                ret.add(purchase);

            } while (c.moveToNext());


        }
        final PurAdapter listViewDataAdapter = new PurAdapter(ret,PurchasedItems.this );

        listViewDataAdapter.notifyDataSetChanged();

        listView.setAdapter(listViewDataAdapter);

    }
}




