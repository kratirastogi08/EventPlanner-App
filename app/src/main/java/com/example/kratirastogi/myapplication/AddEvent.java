package com.example.kratirastogi.myapplication;

import android.app.DatePickerDialog;
import android.app.FragmentManager;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TimePicker;
import android.widget.Toast;

import com.example.kratirastogi.myapplication.dbutil.EventConstants;
import com.example.kratirastogi.myapplication.dbutil.EventManager;

import java.util.Calendar;
import java.util.Date;

import static com.example.kratirastogi.myapplication.R.color.colorPrimary;
import static java.util.Calendar.AM_PM;

public class AddEvent extends AppCompatActivity implements View.OnClickListener{
EditText evnm,evloc,evdate,evtime,evid,bdamnt;
Button savebtn;
EventManager eventManager;
SQLiteDatabase sqLiteDatabase;
int day,month,year,hr,min,sec,i;
    View parent;
    java.sql.Date dated;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);
        evnm=findViewById(R.id.evnm);
        evloc=findViewById(R.id.evloc);
        evdate=findViewById(R.id.evdate);
        evtime=findViewById(R.id.evtime);


        bdamnt=findViewById(R.id.bdamnt);
        savebtn=findViewById(R.id.savebtn);
        savebtn.setOnClickListener(this);
        evtime.setOnClickListener(this);
        evdate.setOnClickListener(this);
        eventManager=new EventManager(this);
        sqLiteDatabase=eventManager.opendb();
        SharedPreferences sharedPreferences=getSharedPreferences("num",MODE_PRIVATE);
      String  name=sharedPreferences.getString("n","0");
      i=Integer.parseInt(name)+1;
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
        if (v.getId() == R.id.savebtn) {

                String nme = evnm.getText().toString();
            SharedPreferences sharedPreferences=getSharedPreferences("num",MODE_PRIVATE);
            SharedPreferences.Editor editor=sharedPreferences.edit();
            editor.putString("n",String.valueOf(i));
            editor.commit();
                String loc = evloc.getText().toString();
                String dat = evdate.getText().toString();
                String time = evtime.getText().toString();
                String amnt=bdamnt.getText().toString();
                 if (TextUtils.isEmpty(loc)) {
                    evloc.setError("please enter location");
                } else if (TextUtils.isEmpty(dat)) {
                    evdate.setError("please enter date");
                } else if (TextUtils.isEmpty(time)) {
                    evtime.setError("please enter time");
                } else if (TextUtils.isEmpty(amnt)) {
                    bdamnt.setError("please enter amount");
                }
                else {
                    ContentValues contentValues = new ContentValues();
                    contentValues.put(EventConstants.COL_EVNTID, i);
                    contentValues.put(EventConstants.COL_EVENM,nme );
                    contentValues.put(EventConstants.COL_EVEPLACE, loc);
                    contentValues.put(EventConstants.COL_EVEDATE, dat);
                    contentValues.put(EventConstants.COL_EVETIME, time);
                    contentValues.put(EventConstants.COL_AMNT, amnt);

                long rownum = sqLiteDatabase.insert(EventConstants.EventTable, null, contentValues);
                if (rownum > 0) {
                    Snackbar snackbar = Snackbar
                            .make(v.getRootView(), "Data Saved", Snackbar.LENGTH_LONG);

                    snackbar.show();
                    Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            Intent intent=new Intent(AddEvent.this,Main2Activity.class);

                            startActivity(intent);
                            AddEvent.this.finish();

                        }
                    },3000);




                }
            }
        }
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
    public void onBackPressed() {
        startActivity(new Intent(this,MainActivity.class));
        finish();
    }
}
