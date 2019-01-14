package com.example.kratirastogi.myapplication;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.kratirastogi.myapplication.dbutil.EventConstants;
import com.example.kratirastogi.myapplication.dbutil.EventManager;

import java.util.Calendar;
import java.util.Date;

public class EditEvent extends AppCompatActivity implements View.OnClickListener {
    EventManager eventManager;
    SQLiteDatabase sqLiteDatabase;
    EditText evid,evnm,evloc,evdate,evtime,amnt;
    Button savebtn;
    int day,month,year,hr,min,sec;
    java.sql.Date dated;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_event);
        evnm = findViewById(R.id.evnm);
        evloc = findViewById(R.id.evloc);
        evdate = findViewById(R.id.evdate);
        evtime = findViewById(R.id.evtime);
        amnt = findViewById(R.id.amnt);

        savebtn = findViewById(R.id.savebtn);
        evdate.setOnClickListener(this);
        evtime.setOnClickListener(this);
        eventManager = new EventManager(this);
        sqLiteDatabase = eventManager.opendb();
        Bundle bundle = getIntent().getExtras();
        final String stuff = bundle.getString("abc");
        String val[] = {stuff};
        String col[] = { EventConstants.COL_EVENM, EventConstants.COL_EVEPLACE, EventConstants.COL_EVEDATE, EventConstants.COL_EVETIME,EventConstants.COL_AMNT};
        Cursor c1 = sqLiteDatabase.query(EventConstants.EventTable, col, EventConstants.COL_EVNTID + "=?", val, null, null, null);
        if (c1 != null && c1.moveToFirst()) {
            Log.d("msg", "hhhhhhh");

            String nm = c1.getString(c1.getColumnIndex(EventConstants.COL_EVENM));
            evnm.setText(nm);
            String pl = c1.getString(c1.getColumnIndex(EventConstants.COL_EVEPLACE));
            evloc.setText(pl);
            String dat = c1.getString(c1.getColumnIndex(EventConstants.COL_EVEDATE));
            evdate.setText(dat);
            String time = c1.getString(c1.getColumnIndex(EventConstants.COL_EVETIME));
            evtime.setText(time);
            String a = c1.getString(c1.getColumnIndex(EventConstants.COL_AMNT));
            amnt.setText(a);

        }
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String nme = evnm.getText().toString();
                String loc = evloc.getText().toString();
                String dat = evdate.getText().toString();
                String time = evtime.getText().toString();
                String am = amnt.getText().toString();
                if (TextUtils.isEmpty(loc)) {
                    evloc.setError("please enter location");
                } else if (TextUtils.isEmpty(dat)) {
                    evdate.setError("please enter date");
                } else if (TextUtils.isEmpty(time)) {
                    evtime.setError("please enter time");
                }
                else if (TextUtils.isEmpty(am)) {
                    amnt.setError("please enter budget amount");
                }
                else {
                    ContentValues contentValues = new ContentValues();

                    contentValues.put(EventConstants.COL_EVENM, nme);
                    contentValues.put(EventConstants.COL_EVEPLACE, loc);
                    contentValues.put(EventConstants.COL_EVEDATE, dat);
                    contentValues.put(EventConstants.COL_EVETIME, time);
                    contentValues.put(EventConstants.COL_AMNT, am);

                    String[] values = {stuff};
                    int r = sqLiteDatabase.update(EventConstants.EventTable, contentValues, EventConstants.COL_EVNTID + "=?", values);
                    if (r > 0) {
                        Toast.makeText(getApplicationContext(), "data updated", Toast.LENGTH_SHORT).show();
                        Intent intent=new Intent(EditEvent.this,Main2Activity.class);
                        startActivity(intent);
                        EditEvent.this.finish();

                    }
                }
            }
        });
    }



    public String getDate()
    {
        StringBuilder builder=new StringBuilder();


        builder.append(day+"-");
        builder.append((month+1)+"-");
        builder.append(year);
        return builder.toString();
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.evdate) {
            final Calendar mcurrentDate = Calendar.getInstance();
            int mYear = mcurrentDate.get(Calendar.YEAR);
            int mMonth = mcurrentDate.get(Calendar.MONTH);
            int mDay = mcurrentDate.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog mDatePicker = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int yeard, int monthd, int dayOfMonth) {
                    day = dayOfMonth;
                    month = monthd;
                    year = yeard;
                    mcurrentDate.set(year, month, day);


                    evdate.setText(getDate());
                    java.util.Date d = mcurrentDate.getTime();
                    dated = new java.sql.Date(d.getTime());
                }
            }, mYear, mMonth, mDay);
            mDatePicker.getDatePicker().setMinDate(new Date().getTime());
            mDatePicker.setTitle("Select date");
            mDatePicker.show();
        }
        if (v.getId() == R.id.evtime) {
            final Calendar myCalendar = Calendar.getInstance();
            TimePickerDialog.OnTimeSetListener mTimeSetListener = new TimePickerDialog.OnTimeSetListener() {
                public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                    String am_pm = "";
                    myCalendar.set(Calendar.HOUR_OF_DAY, hourOfDay);
                    myCalendar.set(Calendar.MINUTE, minute);
                    if (myCalendar.get(Calendar.AM_PM) == Calendar.AM)
                        am_pm = "AM";
                    else if (myCalendar.get(Calendar.AM_PM) == Calendar.PM)
                        am_pm = "PM";
                    String strHrsToShow = (myCalendar.get(Calendar.HOUR) == 0) ? "12" : myCalendar.get(Calendar.HOUR) + "";
                    //UIHelper.showLongToastInCenter(context, strHrsToShow + ":" + myCalendar.get(Calendar.MINUTE) + " " + am_pm);
                    evtime.setText(strHrsToShow + ":" + myCalendar.get(Calendar.MINUTE) + " " + am_pm);
                }
            };
            new TimePickerDialog(this, mTimeSetListener, myCalendar.get(Calendar.HOUR), myCalendar.get(Calendar.MINUTE), false).show();
        }

    }
    @Override
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }

}


