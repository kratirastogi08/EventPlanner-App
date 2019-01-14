package com.example.kratirastogi.myapplication.dbutil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class EventManager {
    Context context;
    SQLiteDatabase sqLiteDatabase;
    EventHelper eventHelper;
    public EventManager(Context context)
    {
        this.context=context;
        eventHelper=new EventHelper(context,EventConstants.DBNAME,null,EventConstants.DBVERSION);
    }

    public SQLiteDatabase opendb()
    {
        sqLiteDatabase=eventHelper.getWritableDatabase();
        return sqLiteDatabase;
    }
    public void closedb()
    {
        eventHelper.close();
    }
}
