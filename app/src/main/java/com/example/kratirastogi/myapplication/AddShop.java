package com.example.kratirastogi.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.service.autofill.ImageTransformation;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kratirastogi.myapplication.dbutil.EventConstants;
import com.example.kratirastogi.myapplication.dbutil.EventManager;

import java.util.ArrayList;

public  class AddShop extends AppCompatActivity implements View.OnClickListener,TextWatcher {
    EditText itm, quant, price, unit;
    TextView tp;
    Button savebtn;
    EventManager eventManager;
    SQLiteDatabase sqLiteDatabase;
    String pr, un,d,d1;
    boolean keyDel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_shop);
        eventManager = new EventManager(this);
        sqLiteDatabase = eventManager.opendb();
        itm = findViewById(R.id.itm);
        quant = findViewById(R.id.quant);
        price = findViewById(R.id.price);
        unit = findViewById(R.id.unit);
        tp = findViewById(R.id.tp);
        savebtn = findViewById(R.id.savebtn);
        savebtn.setOnClickListener(this);
        price.addTextChangedListener(this);
        unit.addTextChangedListener(this);
        Intent intent=getIntent();
        // d= intent.getStringExtra("key");
        d1= intent.getStringExtra("key1");



    }

    @Override
    public void onClick(View v) {


        pr = price.getText().toString();
        un = unit.getText().toString();


        if (v.getId() == R.id.savebtn) {
            String itmnm = itm.getText().toString();
            String quantity = quant.getText().toString();
            pr = price.getText().toString();
            un = unit.getText().toString();
            String totalprice = tp.getText().toString();
            if (TextUtils.isEmpty(itmnm)) {
                itm.setError("please enter item name");
            } else if (TextUtils.isEmpty(pr)) {
                price.setError("please enter price");
            } else if (TextUtils.isEmpty(un)) {
                unit.setError("please enter unit");
            } else {
                ContentValues contentValues = new ContentValues();
                contentValues.put(EventConstants.COL_EVNTID, d1);
               // contentValues.put(EventConstants.COL_EVENM, d);
                contentValues.put(EventConstants.COL_SHOPNM, itmnm);
                contentValues.put(EventConstants.COL_PRICE, pr);
                contentValues.put(EventConstants.COL_UNIT, un);
                contentValues.put(EventConstants.COL_QUANT, quantity);
                contentValues.put(EventConstants.COL_TOTPRICE, totalprice);
                ContentValues contentValues1 = new ContentValues();
                contentValues1.put(EventConstants.COL_EVNTID, d1);
               // contentValues1.put(EventConstants.COL_EVENM, d);
                contentValues1.put(EventConstants.COL_SHOPNM, itmnm);
                contentValues1.put(EventConstants.COL_TOTPRICE, totalprice);

                long rownum1 = sqLiteDatabase.insert(EventConstants.TotalPrice, null, contentValues1);
                long rownum = sqLiteDatabase.insert(EventConstants.ShoppingTable, null, contentValues);
                if (rownum > 0 && rownum1>0) {
                    Toast.makeText(AddShop.this, "data saved", Toast.LENGTH_SHORT).show();
                 Intent intent=new Intent(this,ShopAct.class);
                 startActivity(intent);
                 AddShop.this.finish();
                }



            }
        }
    }


    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {

            if (price.getText().hashCode() == s.hashCode()) {
                if(s.toString().trim().length()>0) {

                    if (unit.getText().toString().length() > 0) {
                        String p = s.toString();
                        int val = Integer.parseInt(p);
                        String u = unit.getText().toString();
                        int val1 = Integer.parseInt(u);

                        tp.setText(String.valueOf(val * val1));

                        //myEditText1_afterTextChanged(s);
                    } else {
                        unit.setError("enter value");
                    }
                }
            }
            if (unit.getText().hashCode() == s.hashCode()) {
                if(s.toString().trim().length()>0) {
                    if (price.getText().toString().length() > 0) {
                        String p = s.toString();
                        int val = Integer.parseInt(p);
                        String u = price.getText().toString();
                        int val1 = Integer.parseInt(u);

                        tp.setText(String.valueOf(val * val1));

                        //myEditText1_afterTextChanged(s);
                    } else {


                    }
                }
            }
        }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

    }
