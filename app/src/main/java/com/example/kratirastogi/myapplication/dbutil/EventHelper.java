package com.example.kratirastogi.myapplication.dbutil;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

public class EventHelper extends SQLiteOpenHelper
{
Context context;
    public EventHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context=context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
             db.execSQL(EventConstants.STQUERY4);
        db.execSQL(EventConstants.STQUERY);
        db.execSQL(EventConstants.STQUERY5);
        db.execSQL(EventConstants.STQUERY3);
        db.execSQL(EventConstants.STQUERY2);
        Toast.makeText(context, "table created", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
