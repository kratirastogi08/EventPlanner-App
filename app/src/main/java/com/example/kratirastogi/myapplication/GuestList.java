package com.example.kratirastogi.myapplication;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.DataSetObserver;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.SparseBooleanArray;
import android.view.ActionMode;
import android.view.ContextMenu;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.ScrollView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.kratirastogi.myapplication.bean.EventList;
import com.example.kratirastogi.myapplication.bean.Guestclass;
import com.example.kratirastogi.myapplication.bean.ListObj;
import com.example.kratirastogi.myapplication.bean.SelEvent;
import com.example.kratirastogi.myapplication.dbutil.EventConstants;
import com.example.kratirastogi.myapplication.dbutil.EventManager;

import java.util.ArrayList;

import static android.content.Context.MODE_PRIVATE;

public class GuestList extends Fragment {
    EventManager eventManager;
    SQLiteDatabase sqLiteDatabase;
    ArrayList<Guestclass> guestlist;
    ArrayList<SelEvent> selEvents;
    Guestclass guestclass;
    ListView listv;
    Spinner sp;
    SelEvent selEvent;
    String item, place, time, date, pref, data,data1;
    ArrayList<String> stringArrayList;
    ArrayList<ListObj> listObjs;
    ListObj listObj;
    ImageView imageview;
    CustomAdapter ad;
    private boolean multiSelect = false;
    String val[],name;



    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);
        eventManager = new EventManager(getContext());
        sqLiteDatabase = eventManager.opendb();
        guestlist = new ArrayList<>();
        selEvents = new ArrayList<>();


        listObjs = new ArrayList<>();
        data = Home.getActivityInstance().getData();
        data1 = Home.getActivityInstance().getData1();
        val = new String[]{data1};

      SharedPreferences preferences=getActivity().getSharedPreferences("details",MODE_PRIVATE);
        name=preferences.getString("name","");



    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.guestlist, container, false);
        listv = view.findViewById(R.id.listv);



        /*sp = view.findViewById(R.id.sp);


        Cursor c1 = sqLiteDatabase.query(EventConstants.EventTable, null, null, null, null, null, null);
        if (c1 != null && c1.moveToFirst()) {
            do {
                String evnm = c1.getString(c1.getColumnIndex(EventConstants.COL_EVENM));
                selEvent = new SelEvent(evnm);
                selEvents.add(selEvent);
            } while (c1.moveToNext());
        }
        c1.close();
        final ArrayAdapter<SelEvent> arrayAdapter1 = new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1, selEvents);
        sp.setAdapter(arrayAdapter1);

        sp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                item = parent.getItemAtPosition(position).toString();
                Log.d("item", item);
                String value[] = {item};*/
        Cursor c = sqLiteDatabase.query(EventConstants.GuestTable, null, EventConstants.COL_EVNTID+"=?", val, null, null, null);
        if (c != null && c.moveToFirst()) {
            // listv.setAdapter(null);
            listObjs = new ArrayList<>();
            do {

                String name = c.getString(c.getColumnIndex(EventConstants.COL_GUESTNM));
                listObj = new ListObj(name, R.mipmap.sss, R.mipmap.phh);
                listObjs.add(listObj);

            } while (c.moveToNext());


            ad = new CustomAdapter(getActivity(), listObjs);

            listv.setAdapter(ad);


            ad.notifyDataSetChanged();


            listv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
                @Override
                public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {

                    listObj = listObjs.get(position);

                    AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("What do you want to do?");
                    builder.setNegativeButton("Delete", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            AlertDialog.Builder builder1=new AlertDialog.Builder(getContext());
                            builder1.setMessage("Are you sure you want to delete?");
                            builder1.setNegativeButton("No", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.cancel();
                                }
                            });
                            builder1.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    String name = listObj.getName();
                                    listObjs.remove(listObj);
                                    ad = new CustomAdapter(getActivity(), listObjs);
                                    listv.setAdapter(ad);
                                    ad.notifyDataSetChanged();
                                    String[] v = {name};
                                    int r = sqLiteDatabase.delete(EventConstants.GuestTable, EventConstants.COL_GUESTNM + "=?", v);
                                    if (r > 0) {
                                        Toast.makeText(getContext(), "data deleted successfully", Toast.LENGTH_SHORT).show();
                                    }
                                }
                            });
                         builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                             @Override
                             public void onClick(DialogInterface dialog, int which) {
                                 dialog.cancel();
                             }
                         });
                            AlertDialog b1 = builder1.create();
                            b1.show();

                        }
                    });
                    builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            listObj=listObjs.get(position);
                            String id1=listObj.getName();
                            Log.d("hdgd",id1);
                            Toast.makeText(getContext(), ""+id1, Toast.LENGTH_SHORT).show();
                            Intent intent=new Intent(getActivity().getBaseContext(),EditGuestList.class);
                            intent.putExtra("abcd",id1);
                            getActivity().startActivity(intent);
                           //
                            getActivity().onBackPressed();
                            getActivity().finish();

                        }
                    });

                    AlertDialog b = builder.create();
                    b.show();

                    return false;
                }
            });


        }


       // c.close();



           /* @Override
            public void onNothingSelected(AdapterView<?> parent) {
                item = null;

            }
        });*/


        return view;
    }





    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id) {
            case R.id.send:
                String val2[]={EventConstants.COL_EVEPLACE,EventConstants.COL_EVEDATE,EventConstants.COL_EVETIME};
                Cursor c2=sqLiteDatabase.query(EventConstants.EventTable,val2,EventConstants.COL_EVNTID+"=?", val,null,null,null);
                if(c2 !=null && c2.moveToFirst())
                {
                    place=c2.getString(c2.getColumnIndex(EventConstants.COL_EVEPLACE));
                    date=c2.getString(c2.getColumnIndex(EventConstants.COL_EVEDATE));
                    time=c2.getString(c2.getColumnIndex(EventConstants.COL_EVETIME));
                }
                //c2.close();
                String args[] = {EventConstants.COL_EMAIL};
                String value[] = {data1,"null"};
                Cursor cursor = sqLiteDatabase.query(EventConstants.GuestTable, args, EventConstants.COL_EVNTID+"=? and "+EventConstants.COL_EMAIL+"!=?", value, null, null, null);
                if (cursor != null && cursor.moveToFirst()) {

                  do {


                    String  emailid = cursor.getString(cursor.getColumnIndex(EventConstants.COL_EMAIL));
                      Intent intent = new Intent(Intent.ACTION_SEND);
                      intent.putExtra(Intent.EXTRA_EMAIL, new String[]{emailid});
                      intent.putExtra(Intent.EXTRA_SUBJECT,"Regarding  "+this.data);
                      intent.putExtra(Intent.EXTRA_TEXT, "We are pleased to invite you for "+this.data+" Please be present on "+date+" at "+time+"\n"+" Venue "+place+"\n"+"From- "+name);
                      intent.putExtra(Intent.EXTRA_CC, "mailcc@gmail.com");
                      intent.setType("text/html");
                      intent.setPackage("com.google.android.gm");
                      startActivity(Intent.createChooser(intent, "Send mail"));

                      }while (cursor.moveToNext());



                }
                else
                {
                    AlertDialog.Builder builder;

                    builder = new AlertDialog.Builder(getContext());
                    builder.setMessage("No emailid is provided");
                    builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    AlertDialog b=builder.create();
                    b.show();
                }
               // cursor.close();


               // String[] recipients={"mailto@gmail.com"};

                return true;
            case R.id.add:
                Intent intent1=new Intent(getContext(),Guest.class);
                //intent1.putExtra("keyy",data);
                intent1.putExtra("keyy1",data1);
                startActivity(intent1);
               // getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();

                getActivity().onBackPressed();
                getActivity().finish();

                return true;
        }
        return super.onOptionsItemSelected(item);
    }



}
