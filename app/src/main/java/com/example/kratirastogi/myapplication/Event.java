package com.example.kratirastogi.myapplication;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kratirastogi.myapplication.bean.EventList;
import com.example.kratirastogi.myapplication.dbutil.EventConstants;
import com.example.kratirastogi.myapplication.dbutil.EventManager;

import java.util.ArrayList;

public class Event extends Fragment implements View.OnClickListener {
    FloatingActionButton fab;
    EventManager eventManager;
    SQLiteDatabase sqLiteDatabase;
    ArrayList<EventList> eventLists;
    EventList eventList;
    ListView lv;
    EditText evid,evnm,evloc,evdate,evtime;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        eventManager=new EventManager(getContext());
        sqLiteDatabase=eventManager.opendb();
        eventLists=new ArrayList<>();

    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.event,container,false);
        fab=view.findViewById(R.id.fab);
        lv=view.findViewById(R.id.lv);

        Cursor c=sqLiteDatabase.query(EventConstants.EventTable,null,null,null,null,null,null);
       if(c!=null && c.moveToFirst())
       {
           do {
               String id=c.getString(c.getColumnIndex(EventConstants.COL_EVNTID));
               String name=c.getString(c.getColumnIndex(EventConstants.COL_EVENM));
               eventList=new EventList(id,name);
               eventLists.add(eventList);
           }while (c.moveToNext());
       }
       c.close();
      final   ArrayAdapter<EventList> arrayAdapter=new ArrayAdapter<>(getContext(), android.R.layout.simple_list_item_1,eventLists);
        lv.setAdapter(arrayAdapter);
    lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
        @Override
        public boolean onItemLongClick(AdapterView<?> parent, View view, final int position, long id) {
            eventList=eventLists.get(position);
            final String id1=eventList.getId();

            final AlertDialog.Builder builder=new AlertDialog.Builder(getContext());
            builder.setMessage("What do you want to do?");
            builder.setPositiveButton("EDIT", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                 Intent intent=new Intent(getActivity(),EditEvent.class);
                 Bundle bundle=new Bundle();
                 bundle.putString("abc",id1);
                 intent.putExtras(bundle);
                 startActivity(intent);
                 getActivity().finish();
                 getActivity().onBackPressed();
                }
            });
            builder.setNegativeButton("DELETE", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                            String val[]={id1};
                            int r=   sqLiteDatabase.delete(EventConstants.EventTable,EventConstants.COL_EVNTID+"=?",val);
                            if(r>0)
                            {
                                Snackbar snackbar = Snackbar
                                        .make(getView(), "Data deleted successfully", Snackbar.LENGTH_LONG);

                                snackbar.show();
                                arrayAdapter.remove(arrayAdapter.getItem(position));
                            }



                }
            });
            AlertDialog b=builder.create();
            b.show();
            return false;
        }
    });
        fab.setOnClickListener(this);
        return view;
    }


    @Override
    public void onClick(View v) {
        Intent intent=new Intent(getContext(),AddEvent.class);
        startActivity(intent);
        getActivity().finish();
     getActivity().onBackPressed();

    }


}
