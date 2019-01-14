package com.example.kratirastogi.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kratirastogi.myapplication.dbutil.EventConstants;
import com.example.kratirastogi.myapplication.dbutil.EventManager;

public class EditShop extends AppCompatActivity implements View.OnClickListener,TextWatcher {
    EditText itm, quant, price, unit;
    TextView tp;
    Button savebtn;
    EventManager eventManager;
    SQLiteDatabase sqLiteDatabase;
    String pr, un,d,stuff,stuff1,stuff2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_shop);
        eventManager=new EventManager(this);
        sqLiteDatabase=eventManager.opendb();
        itm=findViewById(R.id.itm);
        price=findViewById(R.id.price);
        unit=findViewById(R.id.unit);
        tp=findViewById(R.id.tp);
        savebtn=findViewById(R.id.savebtn);
        savebtn.setOnClickListener(this);
        price.addTextChangedListener(this);
        unit.addTextChangedListener(this);

        Intent intent=getIntent();
         stuff=intent.getStringExtra("abc");
        // stuff1=intent.getStringExtra("lk");
        stuff2=intent.getStringExtra("lk1");
        String val[]={stuff,stuff2};
        String col[]={EventConstants.COL_SHOPNM,EventConstants.COL_UNIT,EventConstants.COL_PRICE,EventConstants.COL_TOTPRICE};
        Cursor c1=sqLiteDatabase.query(EventConstants.ShoppingTable,col,EventConstants.COL_SHOPNM + "=? and "+EventConstants.COL_EVNTID+"=?",val,null,null,null);
        if(c1!=null && c1.moveToFirst())
        {

            String nm=c1.getString(c1.getColumnIndex(EventConstants.COL_SHOPNM));
            itm.setText(nm);
            String un=c1.getString(c1.getColumnIndex(EventConstants.COL_UNIT));
            unit.setText(un);
            String p=c1.getString(c1.getColumnIndex(EventConstants.COL_PRICE));
            price.setText(p);
            String tot=c1.getString(c1.getColumnIndex(EventConstants.COL_TOTPRICE));
            tp.setText(tot);


        }

    }

    @Override
    public void onClick(View v) {
        pr = price.getText().toString();
        un = unit.getText().toString();


        if (v.getId() == R.id.savebtn) {
            String itmnm = itm.getText().toString();
            pr = price.getText().toString();
            un = unit.getText().toString();
            String totalprice = tp.getText().toString();
            if (TextUtils.isEmpty(pr)) {
                price.setError("please enter price");
            } else if (TextUtils.isEmpty(un)) {
                unit.setError("please enter unit");
            } else {
                ContentValues contentValues = new ContentValues();
                contentValues.put(EventConstants.COL_EVNTID, stuff2);
               // contentValues.put(EventConstants.COL_EVENM, stuff1);
                contentValues.put(EventConstants.COL_SHOPNM, itmnm);
                contentValues.put(EventConstants.COL_PRICE, pr);
                contentValues.put(EventConstants.COL_UNIT, un);
                contentValues.put(EventConstants.COL_TOTPRICE, totalprice);
                ContentValues contentValues1 = new ContentValues();
                contentValues1.put(EventConstants.COL_EVNTID, stuff2);
               // contentValues1.put(EventConstants.COL_EVENM, stuff1);
                contentValues1.put(EventConstants.COL_SHOPNM, itmnm);
                contentValues1.put(EventConstants.COL_TOTPRICE, totalprice);
                String values[]={stuff,stuff2};
                int r = sqLiteDatabase.update(EventConstants.ShoppingTable, contentValues, EventConstants.COL_SHOPNM + "=? and "+EventConstants.COL_EVNTID+"=?", values);
                int r1 = sqLiteDatabase.update(EventConstants.TotalPrice, contentValues1, EventConstants.COL_SHOPNM + "=? and "+EventConstants.COL_EVNTID+"=?", values);
                if (r > 0 && r1>0) {
                    Toast.makeText(this, "data updated", Toast.LENGTH_SHORT).show();
                    Intent intent=new Intent(this,Main3Activity.class);
                            startActivity(intent);
                            EditShop.this.finish();

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
