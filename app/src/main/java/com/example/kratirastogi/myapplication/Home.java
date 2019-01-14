package com.example.kratirastogi.myapplication;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kratirastogi.myapplication.bean.SelEvent;
import com.example.kratirastogi.myapplication.dbutil.EventConstants;
import com.example.kratirastogi.myapplication.dbutil.EventManager;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class Home extends Fragment {
    EventManager eventManager;
    SQLiteDatabase sqLiteDatabase;
    Spinner sp,spin,spinn;
    SelEvent selEvent;
    ArrayList<SelEvent> selEvents;
    TextView alltotal,allat,alldec,maletotal,tv1,tv3,maleat,maledec,total1,spent1,remain1,fetotal,feat,fedec,alltotal1,allat1,alldec1,childtotal,childat,childdec,adulttotal,adultat,adultdec,sentotal,senat,sendec;
    ArrayList<String>ages;
    ProgressBar activeProgress;
    ArrayList<String>gen;
     static Home INSTANCE;
     String s,i;
     int sum1=0,item;
     Handler handler;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
       if(savedInstanceState !=null)
       {
          sp.setSelection(savedInstanceState.getInt("yourSpinnerValKey",0));
       }



        eventManager = new EventManager(getContext());
        sqLiteDatabase = eventManager.opendb();
        selEvents = new ArrayList<>();
        ages=new ArrayList<String>();
        gen=new ArrayList<String>();
        INSTANCE=this;
        SharedPreferences sharedPreferences=getActivity().getSharedPreferences("shared",MODE_PRIVATE);
        String f=sharedPreferences.getString("l",null);
        if(f==null)
        {
            s="My Event";

            ContentValues contentValues = new ContentValues();
            contentValues.put(EventConstants.COL_EVNTID, 0);
            contentValues.put(EventConstants.COL_EVENM,s);
            contentValues.put(EventConstants.COL_EVEPLACE, "null");
            contentValues.put(EventConstants.COL_EVEDATE, "null");
            contentValues.put(EventConstants.COL_EVETIME, "null");
            contentValues.put(EventConstants.COL_AMNT, 0);

            long rownum = sqLiteDatabase.insert(EventConstants.EventTable, null, contentValues);
            if (rownum > 0) {
                Toast.makeText(getContext(), "data saved", Toast.LENGTH_SHORT).show();

            }
        }



    }




    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("yourSpinnerValKey", sp.getSelectedItemPosition());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home, container, false);


        sp = view.findViewById(R.id.sp);
        spin = view.findViewById(R.id.spin);
        spinn = view.findViewById(R.id.spinn);
        alltotal=view.findViewById(R.id.alltotal);
        allat=view.findViewById(R.id.allat);
        alldec=view.findViewById(R.id.alldec);
        maletotal=view.findViewById(R.id.maletotal);
        maleat=view.findViewById(R.id.maleat);
        maledec=view.findViewById(R.id.maledec);
        fetotal=view.findViewById(R.id.fetotal);
        feat=view.findViewById(R.id.feat);
        fedec=view.findViewById(R.id.fedec);
        alltotal1=view.findViewById(R.id.alltotal1);
        allat1=view.findViewById(R.id.allat1);
        alldec1=view.findViewById(R.id.alldec1);
        childtotal=view.findViewById(R.id.childtotal);
        childat=view.findViewById(R.id.childat);
        childdec=view.findViewById(R.id.childdec);
        adulttotal=view.findViewById(R.id.adulttotal);
        adultat=view.findViewById(R.id.adultat);
        adultdec=view.findViewById(R.id.adultdec);
        sentotal=view.findViewById(R.id.sentotal);
        senat=view.findViewById(R.id.senat);
        sendec=view.findViewById(R.id.sendec);
        total1=view.findViewById(R.id.total1);
        spent1=view.findViewById(R.id.spent1);
        remain1=view.findViewById(R.id.remain1);
        tv1=view.findViewById(R.id.tv1);
        tv3=view.findViewById(R.id.tv3);
        activeProgress=view.findViewById(R.id.activeProgress);
        SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        ages.add("All");
        ages.add("Children");
        ages.add("Adult");
        ages.add("Senior");
        gen.add("All");
        gen.add("Male");
        gen.add("Female");




        Cursor c = sqLiteDatabase.query(EventConstants.EventTable, null, null, null, null, null, null);
        if (c != null && c.moveToFirst()) {
            do {
                String evnm = c.getString(c.getColumnIndex(EventConstants.COL_EVENM));
                String id = c.getString(c.getColumnIndex(EventConstants.COL_EVNTID));
                selEvent = new SelEvent(evnm,id);
                selEvents.add(selEvent);
            } while (c.moveToNext());
        }
        c.close();

        final ArrayAdapter<SelEvent> arrayAdapter1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, selEvents);
        sp.setAdapter(arrayAdapter1);
       int a= sp.getAdapter().getCount();
       SharedPreferences sharedPreferences=getActivity().getSharedPreferences("shared",MODE_PRIVATE);
      SharedPreferences.Editor  editor1=sharedPreferences.edit();
      editor1.putString("l",String.valueOf(a));
      editor1.apply();
        sp.setSelection(preferences.getInt("spinnerSelection",0));
        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            SharedPreferences preferences = PreferenceManager.getDefaultSharedPreferences(getContext());
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                 item = sp.getSelectedItemPosition();
                //Toast.makeText(getContext(), ""+item, Toast.LENGTH_SHORT).show();

                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt("spinnerSelection", item);
                editor.apply();
          SelEvent  selEvent1= (SelEvent) parent.getItemAtPosition(position);
         s= selEvent1.getName();
         i=selEvent1.getId();

                String mm[]={String.valueOf(item)};

                Cursor cursor=sqLiteDatabase.query(EventConstants.TotalPrice,null,EventConstants.COL_EVNTID+"=?",mm,null,null,null);
                if(cursor!=null && cursor.moveToFirst())
                {  int l=0;
                    tv3.setText("");
                    do {
                        l++;
                    }while (cursor.moveToNext());
                    activeProgress.setMax(l);
                    tv3.setText(String.valueOf(l));
                }
                else
                {
                    tv3.setText("0");
                }

                Cursor cursor1=sqLiteDatabase.query(EventConstants.Todolist,null,EventConstants.COL_EVNTID+"=?",mm,null,null,null);
                if(cursor1!=null && cursor1.moveToFirst())
                {  int l=0;
                    tv1.setText("");
                    do {
                        l++;
                    }while (cursor1.moveToNext());
                    activeProgress.setProgress(l);
                    tv1.setText(String.valueOf(l));
                }
                else
                {
                    activeProgress.setProgress(0);
                    tv1.setText("0");
                }

                String p[]={String.valueOf(item)};
                Cursor c121 = sqLiteDatabase.query(EventConstants.Todolist, null, EventConstants.COL_EVNTID+"=?", p, null, null, null);
                spent1.setText("");
                int  amn1 = 0,sum1=0;
                if (c121 != null && c121.moveToFirst()) {
                    do {
                        String m = c121.getString(c121.getColumnIndex(EventConstants.COL_TOTPRICE));
                        int t = Integer.valueOf(m);
                        sum1 = sum1 + t;



                    } while (c121.moveToNext());
                    spent1.setText(String.valueOf(sum1));

                }
                else
                {
                    spent1.setText("0");
                }

                Cursor c300 = sqLiteDatabase.query(EventConstants.TotalPrice, null, EventConstants.COL_EVNTID+"=?", p, null, null, null);

                int sum = 0, amn = 0;
                if (c300 != null && c300.moveToFirst()) {
                    do {
                        String m = c300.getString(c300.getColumnIndex(EventConstants.COL_TOTPRICE));
                        int t = Integer.valueOf(m);
                        sum = sum + t;


                    } while (c300.moveToNext());
                    total1.setText(String.valueOf(sum));
                    remain1.setText(String.valueOf(sum-sum1));

                }
                else
                {
                    total1.setText("0");
                    remain1.setText("0");
                }

                ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item, ages);
                adapter.setDropDownViewResource(android.R.layout.simple_list_item_1);
                spin.setAdapter(adapter);
                spin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String pp = (String) spin.getItemAtPosition(position);
                        //Toast.makeText(getContext(), "" + pp, Toast.LENGTH_SHORT).show();
                        String[] val = {pp,String.valueOf(item)};
                        Cursor c1 = sqLiteDatabase.query(EventConstants.GuestTable, null, EventConstants.COL_GUESTAGE + "=? and "+EventConstants.COL_EVNTID+"=?", val, null, null, null);
                        if (c1 != null && c1.moveToFirst()) {
                            int t = 0;
                            alltotal.setText("");
                            alldec.setText("");
                            allat.setText("");
                            maledec.setText("");
                            maletotal.setText("");
                            maleat.setText("");
                            fedec.setText("");
                            feat.setText("");
                            fetotal.setText("");

                            do {
                                t++;
                            } while (c1.moveToNext());
                            alltotal.setText(String.valueOf(t));
                            String[] val1 = {pp, "Male",String.valueOf(item)};
                            Cursor c3 = sqLiteDatabase.query(EventConstants.GuestTable, null, EventConstants.COL_GUESTAGE + "=? and " + EventConstants.COL_GUESTGEN + "=? and "+EventConstants.COL_EVNTID+"=?", val1, null, null, null);
                            if (c3 != null && c3.moveToFirst()) {
                                int t1 = 0;
                                do {
                                    t1++;
                                } while (c3.moveToNext());
                                maletotal.setText(String.valueOf(t1));

                            } else {
                                maletotal.setText("0");
                            }
                            c3.close();
                            String[] val2 = {pp, "Female",String.valueOf(item)};
                            Cursor c4 = sqLiteDatabase.query(EventConstants.GuestTable, null, EventConstants.COL_GUESTAGE + "=? and " + EventConstants.COL_GUESTGEN + "=? and "+EventConstants.COL_EVNTID+"=?", val2, null, null, null);
                            if (c4 != null && c4.moveToFirst()) {
                                int t1 = 0;
                                do {
                                    t1++;
                                } while (c4.moveToNext());
                                fetotal.setText(String.valueOf(t1));

                            } else {
                                fetotal.setText("0");
                            }
                            c4.close();
                            String[] val3 = {pp, "Attending",String.valueOf(item)};
                            Cursor c5 = sqLiteDatabase.query(EventConstants.GuestTable, null, EventConstants.COL_GUESTAGE + "=? and " + EventConstants.COL_STATUS + "=? and "+EventConstants.COL_EVNTID+"=?", val3, null, null, null);
                            if (c5 != null && c5.moveToFirst()) {
                                int t1 = 0;
                                do {
                                    t1++;
                                } while (c5.moveToNext());
                                allat.setText(String.valueOf(t1));

                            } else {
                                allat.setText("0");
                            }
                            c5.close();
                            String[] val4 = {pp, "Decline",String.valueOf(item)};
                            Cursor c6 = sqLiteDatabase.query(EventConstants.GuestTable, null, EventConstants.COL_GUESTAGE + "=? and " + EventConstants.COL_STATUS + "=? and "+EventConstants.COL_EVNTID+"=?", val4, null, null, null);
                            if (c6 != null && c6.moveToFirst()) {
                                int t1 = 0;
                                do {
                                    t1++;
                                } while (c6.moveToNext());
                                alldec.setText(String.valueOf(t1));

                            } else {
                                alldec.setText("0");
                            }
                            c6.close();
                            String[] val5 = {pp, "Male", "Attending",String.valueOf(item)};
                            Cursor c7 = sqLiteDatabase.query(EventConstants.GuestTable, null, EventConstants.COL_GUESTAGE + "=? and " + EventConstants.COL_GUESTGEN + "=? and " + EventConstants.COL_STATUS + "=? and "+EventConstants.COL_EVNTID+"=?", val5, null, null, null);
                            if (c7 != null && c7.moveToFirst()) {
                                int t1 = 0;
                                do {
                                    t1++;
                                } while (c7.moveToNext());
                                maleat.setText(String.valueOf(t1));

                            } else {
                                maleat.setText("0");
                            }
                            c7.close();
                            String[] val6 = {pp, "Male", "Decline",String.valueOf(item)};
                            Cursor c8 = sqLiteDatabase.query(EventConstants.GuestTable, null, EventConstants.COL_GUESTAGE + "=? and " + EventConstants.COL_GUESTGEN + "=? and " + EventConstants.COL_STATUS + "=? and "+EventConstants.COL_EVNTID+"=?", val6, null, null, null);
                            if (c8 != null && c8.moveToFirst()) {
                                int t1 = 0;
                                do {
                                    t1++;
                                } while (c8.moveToNext());
                                maledec.setText(String.valueOf(t1));

                            } else {
                                maledec.setText("0");
                            }
                            c8.close();
                            String[] val7 = {pp, "Female", "Attending",String.valueOf(item)};
                            Cursor c9 = sqLiteDatabase.query(EventConstants.GuestTable, null, EventConstants.COL_GUESTAGE + "=? and " + EventConstants.COL_GUESTGEN + "=? and " + EventConstants.COL_STATUS + "=? and "+EventConstants.COL_EVNTID+"=?", val7, null, null, null);
                            if (c9 != null && c9.moveToFirst()) {
                                int t1 = 0;
                                do {
                                    t1++;
                                } while (c9.moveToNext());
                                feat.setText(String.valueOf(t1));

                            } else {
                                feat.setText("0");
                            }
                            c9.close();
                            String[] val8 = {pp, "Female", "Decline",String.valueOf(item)};
                            Cursor c10 = sqLiteDatabase.query(EventConstants.GuestTable, null, EventConstants.COL_GUESTAGE + "=? and " + EventConstants.COL_GUESTGEN + "=? and " + EventConstants.COL_STATUS + "=? and "+EventConstants.COL_EVNTID+"=?", val8, null, null, null);
                            if (c10 != null && c10.moveToFirst()) {
                                int t1 = 0;
                                do {
                                    t1++;
                                } while (c10.moveToNext());
                                fedec.setText(String.valueOf(t1));

                            } else {
                                fedec.setText("0");
                            }
                            c10.close();

                        } else {
                            String l[]={String.valueOf(item)};
                            if (pp.equals("All")) {
                                Cursor m = sqLiteDatabase.query(EventConstants.GuestTable, null, EventConstants.COL_EVNTID+"=?", l, null, null, null);
                                if (m != null && m.moveToFirst()) {
                                    int t = 0;
                                    do {
                                        t++;
                                    } while (m.moveToNext());
                                    alltotal.setText(String.valueOf(t));
                                    String[] val1 = {"Male",String.valueOf(item)};
                                    Cursor c3 = sqLiteDatabase.query(EventConstants.GuestTable, null, EventConstants.COL_GUESTGEN + "=? and "+EventConstants.COL_EVNTID+"=?", val1, null, null, null);
                                    if (c3 != null && c3.moveToFirst()) {
                                        int t1 = 0;
                                        do {
                                            t1++;
                                        } while (c3.moveToNext());
                                        maletotal.setText(String.valueOf(t1));

                                    } else {
                                        maletotal.setText("0");
                                    }
                                    c3.close();
                                    String[] val2 = {"Female",String.valueOf(item)};
                                    Cursor c4 = sqLiteDatabase.query(EventConstants.GuestTable, null, EventConstants.COL_GUESTGEN + "=? and "+EventConstants.COL_EVNTID+"=?", val2, null, null, null);
                                    if (c4 != null && c4.moveToFirst()) {
                                        int t1 = 0;
                                        do {
                                            t1++;
                                        } while (c4.moveToNext());
                                        fetotal.setText(String.valueOf(t1));

                                    } else {
                                        fetotal.setText("0");
                                    }
                                    c4.close();
                                    String[] val3 = {"Attending",String.valueOf(item)};
                                    Cursor c5 = sqLiteDatabase.query(EventConstants.GuestTable, null, EventConstants.COL_STATUS + "=? and "+EventConstants.COL_EVNTID+"=?", val3, null, null, null);
                                    if (c5 != null && c5.moveToFirst()) {
                                        int t1 = 0;
                                        do {
                                            t1++;
                                        } while (c5.moveToNext());
                                        allat.setText(String.valueOf(t1));

                                    } else {
                                        allat.setText("0");
                                    }
                                    c5.close();
                                    String[] val4 = {"Decline",String.valueOf(item)};
                                    Cursor c6 = sqLiteDatabase.query(EventConstants.GuestTable, null, EventConstants.COL_STATUS + "=? and "+EventConstants.COL_EVNTID+"=?", val4, null, null, null);
                                    if (c6 != null && c6.moveToFirst()) {
                                        int t1 = 0;
                                        do {
                                            t1++;
                                        } while (c6.moveToNext());
                                        alldec.setText(String.valueOf(t1));

                                    } else {
                                        alldec.setText("0");
                                    }
                                    c6.close();
                                    String[] val5 = {"Male", "Attending",String.valueOf(item)};
                                    Cursor c7 = sqLiteDatabase.query(EventConstants.GuestTable, null, EventConstants.COL_GUESTGEN + "=? and " + EventConstants.COL_STATUS + "=? and "+EventConstants.COL_EVNTID+"=?", val5, null, null, null);
                                    if (c7 != null && c7.moveToFirst()) {
                                        int t1 = 0;
                                        do {
                                            t1++;
                                        } while (c7.moveToNext());
                                        maleat.setText(String.valueOf(t1));

                                    } else {
                                        maleat.setText("0");
                                    }
                                    c7.close();
                                    String[] val6 = {"Male", "Decline",String.valueOf(item)};
                                    Cursor c8 = sqLiteDatabase.query(EventConstants.GuestTable, null, EventConstants.COL_GUESTGEN + "=? and " + EventConstants.COL_STATUS + "=? and "+EventConstants.COL_EVNTID+"=?", val6, null, null, null);
                                    if (c8 != null && c8.moveToFirst()) {
                                        int t1 = 0;
                                        do {
                                            t1++;
                                        } while (c8.moveToNext());
                                        maledec.setText(String.valueOf(t1));

                                    } else {
                                        maledec.setText("0");
                                    }
                                    c8.close();
                                    String[] val7 = {"Female", "Attending",String.valueOf(item)};
                                    Cursor c9 = sqLiteDatabase.query(EventConstants.GuestTable, null, EventConstants.COL_GUESTGEN + "=? and " + EventConstants.COL_STATUS + "=? and "+EventConstants.COL_EVNTID+"=?", val7, null, null, null);
                                    if (c9 != null && c9.moveToFirst()) {
                                        int t1 = 0;
                                        do {
                                            t1++;
                                        } while (c9.moveToNext());
                                        feat.setText(String.valueOf(t1));

                                    } else {
                                        feat.setText("0");
                                    }
                                    c9.close();
                                    String[] val8 = {"Female", "Decline",String.valueOf(item)};
                                    Cursor c10 = sqLiteDatabase.query(EventConstants.GuestTable, null, EventConstants.COL_GUESTGEN + "=? and " + EventConstants.COL_STATUS + "=? and "+EventConstants.COL_EVNTID+"=?", val8, null, null, null);
                                    if (c10 != null && c10.moveToFirst()) {
                                        int t1 = 0;
                                        do {
                                            t1++;
                                        } while (c10.moveToNext());
                                        fedec.setText(String.valueOf(t1));

                                    } else {
                                        fedec.setText("0");
                                    }
                                    c10.close();
                                }
                            }
                            if(pp.equals("Senior")||pp.equals("Adult")||pp.equals("Children"))
                            {

                                alltotal.setText("0");
                                alldec.setText("0");
                                allat.setText("0");
                                maledec.setText("0");
                                maletotal.setText("0");
                                maleat.setText("0");
                                fedec.setText("0");
                                feat.setText("0");
                                fetotal.setText("0");
                            }

                        }



                    }



                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });




                ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getContext(),
                        android.R.layout.simple_spinner_item, gen);

                spinn.setAdapter(adapter2);
                spinn.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                    @Override
                    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                        String pp= (String) spinn.getItemAtPosition(position);
                        Toast.makeText(getContext(), ""+pp, Toast.LENGTH_SHORT).show();
                        String []val={pp,String.valueOf(item)};
                        Cursor c20=sqLiteDatabase.query(EventConstants.GuestTable,null,EventConstants.COL_GUESTGEN+"=? and "+EventConstants.COL_EVNTID+"=?",val,null,null,null);
                        if(c20!=null && c20.moveToFirst() )
                        {     int t=0;
                            alltotal1.setText("");
                            alldec1.setText("");
                            allat1.setText("");
                            childtotal.setText("");
                            childat.setText("");
                            childdec.setText("");
                            adulttotal.setText("");
                            adultat.setText("");
                            adultdec.setText("");
                            sentotal.setText("");
                            senat.setText("");
                            sendec.setText("");

                            do
                            {
                                t++;
                            }while (c20.moveToNext());
                            alltotal1.setText(String.valueOf(t));
                            String []val8={pp,"Attending",String.valueOf(item)};
                            Cursor c33=sqLiteDatabase.query(EventConstants.GuestTable,null,EventConstants.COL_GUESTGEN+"=? and "+EventConstants.COL_STATUS+"=? and "+EventConstants.COL_EVNTID+"=?",val8,null,null,null);
                            if(c33!=null && c33.moveToFirst() )
                            {  int t1=0;
                                do
                                {
                                    t1++;
                                }while (c33.moveToNext());
                                allat1.setText(String.valueOf(t1));

                            }

                            else
                            {
                                allat1.setText("0");
                            }
                            c33.close();
                            String []val9={pp,"Decline",String.valueOf(item)};
                            Cursor c34=sqLiteDatabase.query(EventConstants.GuestTable,null,EventConstants.COL_GUESTGEN+"=? and "+EventConstants.COL_STATUS+"=? and "+EventConstants.COL_EVNTID+"=?",val9,null,null,null);
                            if(c34!=null && c34.moveToFirst() )
                            {  int t1=0;
                                do
                                {
                                    t1++;
                                }while (c34.moveToNext());
                                alldec1.setText(String.valueOf(t1));

                            }

                            else
                            {
                                alldec1.setText("0");
                            }
                            c34.close();
                            String []val1={pp,"Children",String.valueOf(item)};
                            Cursor c3=sqLiteDatabase.query(EventConstants.GuestTable,null,EventConstants.COL_GUESTGEN+"=? and "+EventConstants.COL_GUESTAGE+"=? and "+EventConstants.COL_EVNTID+"=?",val1,null,null,null);
                            if(c3!=null && c3.moveToFirst() )
                            {  int t1=0;
                                do
                                {
                                    t1++;
                                }while (c3.moveToNext());
                                childtotal.setText(String.valueOf(t1));

                            }

                            else
                            {
                                childtotal.setText("0");
                            }
                            c3.close();
                            String []val2={pp,"Adult",String.valueOf(item)};
                            Cursor c4=sqLiteDatabase.query(EventConstants.GuestTable,null,EventConstants.COL_GUESTGEN+"=? and "+EventConstants.COL_GUESTAGE+"=? and "+EventConstants.COL_EVNTID+"=?",val2,null,null,null);
                            if(c4!=null && c4.moveToFirst() )
                            {  int t1=0;
                                do
                                {
                                    t1++;
                                }while (c4.moveToNext());
                                adulttotal.setText(String.valueOf(t1));

                            }
                            else
                            {
                                adulttotal.setText("0");
                            }
                            c4.close();
                            String []val22={pp,"Senior",String.valueOf(item)};
                            Cursor c44=sqLiteDatabase.query(EventConstants.GuestTable,null,EventConstants.COL_GUESTGEN+"=? and "+EventConstants.COL_GUESTAGE+"=? and "+EventConstants.COL_EVNTID+"=?",val22,null,null,null);
                            if(c44!=null && c44.moveToFirst() )
                            {  int t1=0;
                                do
                                {
                                    t1++;
                                }while (c44.moveToNext());
                                sentotal.setText(String.valueOf(t1));

                            }
                            else
                            {
                                sentotal.setText("0");
                            }
                            c44.close();
                            String []val3={pp,"Attending","Children",String.valueOf(item)};
                            Cursor c5=sqLiteDatabase.query(EventConstants.GuestTable,null,EventConstants.COL_GUESTGEN+"=? and "+EventConstants.COL_STATUS+"=? and "+EventConstants.COL_GUESTAGE+"=? and "+EventConstants.COL_EVNTID+"=?",val3,null,null,null);
                            if(c5!=null && c5.moveToFirst() )
                            {  int t1=0;
                                do
                                {
                                    t1++;
                                }while (c5.moveToNext());
                                childat.setText(String.valueOf(t1));

                            }
                            else
                            {
                                childat.setText("0");
                            }
                            c5.close();
                            String []val4={pp,"Decline","Children",String.valueOf(item)};
                            Cursor c6=sqLiteDatabase.query(EventConstants.GuestTable,null,EventConstants.COL_GUESTGEN+"=? and "+EventConstants.COL_STATUS+"=? and "+EventConstants.COL_GUESTAGE+"=? and "+EventConstants.COL_EVNTID+"=?",val4,null,null,null);
                            if(c6!=null && c6.moveToFirst() )
                            {  int t1=0;
                                do
                                {
                                    t1++;
                                }while (c6.moveToNext());
                                childdec.setText(String.valueOf(t1));

                            }
                            else
                            {
                                childdec.setText("0");
                            }
                            c6.close();
                            String []val5={pp,"Adult","Attending",String.valueOf(item)};
                            Cursor c7=sqLiteDatabase.query(EventConstants.GuestTable,null,EventConstants.COL_GUESTGEN+"=? and "+EventConstants.COL_GUESTAGE+"=? and "+EventConstants.COL_STATUS+"=? and "+EventConstants.COL_EVNTID+"=?",val5,null,null,null);
                            if(c7!=null && c7.moveToFirst() )
                            {  int t1=0;
                                do
                                {
                                    t1++;
                                }while (c7.moveToNext());
                                adultat.setText(String.valueOf(t1));

                            }
                            else
                            {
                                adultat.setText("0");
                            }
                            c7.close();
                            String []val6={pp,"Adult","Decline",String.valueOf(item)};
                            Cursor c8=sqLiteDatabase.query(EventConstants.GuestTable,null,EventConstants.COL_GUESTGEN+"=? and "+EventConstants.COL_GUESTAGE+"=? and "+EventConstants.COL_STATUS+"=? and "+EventConstants.COL_EVNTID+"=?",val6,null,null,null);
                            if(c8!=null && c8.moveToFirst() )
                            {  int t1=0;
                                do
                                {
                                    t1++;
                                }while (c8.moveToNext());
                                adultdec.setText(String.valueOf(t1));

                            }
                            else
                            {
                                adultdec.setText("0");
                            }
                            c8.close();
                            String []val7={pp,"Senior","Attending",String.valueOf(item)};
                            Cursor c9=sqLiteDatabase.query(EventConstants.GuestTable,null,EventConstants.COL_GUESTGEN+"=? and "+EventConstants.COL_GUESTAGE+"=? and "+EventConstants.COL_STATUS+"=? and "+EventConstants.COL_EVNTID+"=?",val7,null,null,null);
                            if(c9!=null && c9.moveToFirst() )
                            {  int t1=0;
                                do
                                {
                                    t1++;
                                }while (c9.moveToNext());
                                senat.setText(String.valueOf(t1));

                            }
                            else
                            {
                                senat.setText("0");
                            }
                            c9.close();
                            String []val14={pp,"Senior","Decline",String.valueOf(item)};
                            Cursor c10=sqLiteDatabase.query(EventConstants.GuestTable,null,EventConstants.COL_GUESTAGE+"=? and "+EventConstants.COL_GUESTGEN+"=? and "+EventConstants.COL_STATUS+"=? and "+EventConstants.COL_EVNTID+"=?",val14,null,null,null);
                            if(c10!=null && c10.moveToFirst() )
                            {  int t1=0;
                                do
                                {
                                    t1++;
                                }while (c10.moveToNext());
                                sendec.setText(String.valueOf(t1));

                            }
                            else
                            {
                                sendec.setText("0");
                            }
                            c10.close();

                        }

                        else {
                            String b[]={String.valueOf(item)};
                            if(pp.equals("All")) {
                                Cursor m = sqLiteDatabase.query(EventConstants.GuestTable, null, EventConstants.COL_EVNTID+"=?", b, null, null, null);
                                if (m != null && m.moveToFirst()) {
                                    int t = 0;
                                    do {
                                        t++;
                                    } while (m.moveToNext());
                                    alltotal1.setText(String.valueOf(t));
                                    String[] val1 = {"Attending",String.valueOf(item)};
                                    Cursor c3 = sqLiteDatabase.query(EventConstants.GuestTable, null, EventConstants.COL_STATUS + "=? and "+EventConstants.COL_EVNTID+"=?", val1, null, null, null);
                                    if (c3 != null && c3.moveToFirst()) {
                                        int t1 = 0;
                                        do {
                                            t1++;
                                        } while (c3.moveToNext());
                                        allat1.setText(String.valueOf(t1));

                                    } else {
                                        allat1.setText("0");
                                    }
                                    c3.close();
                                    String[] val2 = {"Decline",String.valueOf(item)};
                                    Cursor c4 = sqLiteDatabase.query(EventConstants.GuestTable, null, EventConstants.COL_STATUS + "=? and "+EventConstants.COL_EVNTID+"=?", val2, null, null, null);
                                    if (c4 != null && c4.moveToFirst()) {
                                        int t1 = 0;
                                        do {
                                            t1++;
                                        } while (c4.moveToNext());
                                        alldec1.setText(String.valueOf(t1));

                                    } else {
                                        alldec1.setText("0");
                                    }
                                    c4.close();
                                    String[] val3 = {"Children",String.valueOf(item)};
                                    Cursor c5 = sqLiteDatabase.query(EventConstants.GuestTable, null, EventConstants.COL_GUESTAGE + "=? and "+EventConstants.COL_EVNTID+"=?", val3, null, null, null);
                                    if (c5 != null && c5.moveToFirst()) {
                                        int t1 = 0;
                                        do {
                                            t1++;
                                        } while (c5.moveToNext());
                                        childtotal.setText(String.valueOf(t1));

                                    } else {
                                        childtotal.setText("0");
                                    }
                                    c5.close();
                                    String[] val4 = {"Adult",String.valueOf(item)};
                                    Cursor c6 = sqLiteDatabase.query(EventConstants.GuestTable, null, EventConstants.COL_GUESTAGE + "=? and "+EventConstants.COL_EVNTID+"=?", val4, null, null, null);
                                    if (c6 != null && c6.moveToFirst()) {
                                        int t1 = 0;
                                        do {
                                            t1++;
                                        } while (c6.moveToNext());
                                        adulttotal.setText(String.valueOf(t1));

                                    } else {
                                        adulttotal.setText("0");
                                    }
                                    c6.close();
                                    String[] val55 = {"Senior",String.valueOf(item)};
                                    Cursor c66 = sqLiteDatabase.query(EventConstants.GuestTable, null, EventConstants.COL_GUESTAGE + "=? and "+EventConstants.COL_EVNTID+"=?", val55, null, null, null);
                                    if (c66 != null && c66.moveToFirst()) {
                                        int t1 = 0;
                                        do {
                                            t1++;
                                        } while (c66.moveToNext());
                                        sentotal.setText(String.valueOf(t1));

                                    } else {
                                        sentotal.setText("0");
                                    }
                                    c66.close();
                                    String[] val5 = {"Children", "Attending",String.valueOf(item)};
                                    Cursor c7 = sqLiteDatabase.query(EventConstants.GuestTable, null, EventConstants.COL_GUESTAGE + "=? and " + EventConstants.COL_STATUS + "=? and "+EventConstants.COL_EVNTID+"=?", val5, null, null, null);
                                    if (c7 != null && c7.moveToFirst()) {
                                        int t1 = 0;
                                        do {
                                            t1++;
                                        } while (c7.moveToNext());
                                        childat.setText(String.valueOf(t1));

                                    } else {
                                        childat.setText("0");
                                    }
                                    c7.close();
                                    String[] val6 = {"Children", "Decline",String.valueOf(item)};
                                    Cursor c8 = sqLiteDatabase.query(EventConstants.GuestTable, null, EventConstants.COL_GUESTAGE + "=? and " + EventConstants.COL_STATUS + "=? and "+EventConstants.COL_EVNTID+"=?", val6, null, null, null);
                                    if (c8 != null && c8.moveToFirst()) {
                                        int t1 = 0;
                                        do {
                                            t1++;
                                        } while (c8.moveToNext());
                                        childdec.setText(String.valueOf(t1));

                                    } else {
                                        childdec.setText("0");
                                    }
                                    c8.close();
                                    String[] val7 = {"Adult", "Attending",String.valueOf(item)};
                                    Cursor c9 = sqLiteDatabase.query(EventConstants.GuestTable, null, EventConstants.COL_GUESTAGE + "=? and " + EventConstants.COL_STATUS + "=? and "+EventConstants.COL_EVNTID+"=?", val7, null, null, null);
                                    if (c9 != null && c9.moveToFirst()) {
                                        int t1 = 0;
                                        do {
                                            t1++;
                                        } while (c9.moveToNext());
                                        adultat.setText(String.valueOf(t1));

                                    } else {
                                        adultat.setText("0");
                                    }
                                    c9.close();
                                    String[] val8 = {"Adult", "Decline",String.valueOf(item)};
                                    Cursor c10 = sqLiteDatabase.query(EventConstants.GuestTable, null, EventConstants.COL_GUESTAGE + "=? and " + EventConstants.COL_STATUS + "=? and "+EventConstants.COL_EVNTID+"=?", val8, null, null, null);
                                    if (c10 != null && c10.moveToFirst()) {
                                        int t1 = 0;
                                        do {
                                            t1++;
                                        } while (c10.moveToNext());
                                        adultdec.setText(String.valueOf(t1));

                                    } else {
                                        adultdec.setText("0");
                                    }
                                    c10.close();
                                    String[] val12 = {"Senior", "Attending",String.valueOf(item)};
                                    Cursor c11 = sqLiteDatabase.query(EventConstants.GuestTable, null, EventConstants.COL_GUESTAGE + "=? and " + EventConstants.COL_STATUS + "=? and "+EventConstants.COL_EVNTID+"=?", val12, null, null, null);
                                    if (c11 != null && c11.moveToFirst()) {
                                        int t1 = 0;
                                        do {
                                            t1++;
                                        } while (c11.moveToNext());
                                        senat.setText(String.valueOf(t1));

                                    } else {
                                        senat.setText("0");
                                    }
                                    c11.close();
                                    String[] val32 = {"Senior", "Decline",String.valueOf(item)};
                                    Cursor c32 = sqLiteDatabase.query(EventConstants.GuestTable, null, EventConstants.COL_GUESTAGE + "=? and " + EventConstants.COL_STATUS + "=? and "+EventConstants.COL_EVNTID+"=?", val32, null, null, null);
                                    if (c32 != null && c32.moveToFirst()) {
                                        int t1 = 0;
                                        do {
                                            t1++;
                                        } while (c32.moveToNext());
                                        sendec.setText(String.valueOf(t1));

                                    } else {
                                        sendec.setText("0");
                                    }
                                    c32.close();
                                }
                            }
                            if(pp.equals("Male") || pp.equals("Female"))
                            {
                                alltotal1.setText("0");
                                alldec1.setText("0");
                                allat1.setText("0");
                                childtotal.setText("0");
                                childat.setText("0");
                                childdec.setText("0");
                                adulttotal.setText("0");
                                adultat.setText("0");
                                adultdec.setText("0");
                                sentotal.setText("0");
                                senat.setText("0");
                                sendec.setText("0");
                            }


                        }

  String a[]={String.valueOf(item)};
   Cursor cursor2=sqLiteDatabase.query(EventConstants.GuestTable,null,EventConstants.COL_EVNTID+"=?",a,null,null,null);

      if(cursor2 !=null && cursor2.moveToFirst())
      {

      }
   else
      {
          alltotal.setText("0");
          alldec.setText("0");
          allat.setText("0");
          maledec.setText("0");
          maletotal.setText("0");
          maleat.setText("0");
          fedec.setText("0");
          feat.setText("0");
          fetotal.setText("0");
          alltotal1.setText("0");
          alldec1.setText("0");
          allat1.setText("0");
          childtotal.setText("0");
          childat.setText("0");
          childdec.setText("0");
          adulttotal.setText("0");
          adultat.setText("0");
          adultdec.setText("0");
          sentotal.setText("0");
          senat.setText("0");
          sendec.setText("0");
      }


                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> parent) {

                    }
                });


            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {



            }
        });




        return view;
    }
    public static Home getActivityInstance()
    {
        return INSTANCE;
    }
    public String getData()
    {
        return this.s;
    }
    public String getData1()
    {
        return this.i;
    }
}
