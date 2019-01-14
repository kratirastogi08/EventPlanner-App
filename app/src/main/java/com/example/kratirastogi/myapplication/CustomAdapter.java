package com.example.kratirastogi.myapplication;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.RecyclerView;
import android.util.SparseBooleanArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kratirastogi.myapplication.bean.ListObj;
import com.example.kratirastogi.myapplication.dbutil.EventConstants;
import com.example.kratirastogi.myapplication.dbutil.EventManager;

import java.time.LocalDateTime;
import java.util.List;

public class CustomAdapter extends BaseAdapter {
    private List<ListObj> list;
    private LayoutInflater inflater=null;
    Context context=null;
    ListObj l,listObj;
    ListView listv;
    EventManager eventManager;
    SQLiteDatabase sqLiteDatabase;
    private SparseBooleanArray mSelectedItemsIds;

    public CustomAdapter(Activity activity, List<ListObj>list)
    {
        context=activity.getApplicationContext();
        this.list=list;
        inflater=LayoutInflater.from(activity);
        eventManager=new EventManager(context);
        sqLiteDatabase=eventManager.opendb();


    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    /*public int getViewTypeCount() {
        return getCount();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }*/

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
       ViewHolder viewHolder=null;

        if(convertView==null) {
            viewHolder = new ViewHolder();
            convertView = View.inflate(context, R.layout.custom, null);
            viewHolder.text = convertView.findViewById(R.id.txtname);
            listv = parent.findViewById(R.id.listv);
            viewHolder.img = convertView.findViewById(R.id.imageview);
            viewHolder.img1 = convertView.findViewById(R.id.imageview1);
            convertView.setTag(viewHolder);
        }
            else
            {
                viewHolder = (ViewHolder) convertView.getTag();
            }
            viewHolder.img.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Toast.makeText(context, "msg", Toast.LENGTH_SHORT).show();
                    listObj = (ListObj) listv.getItemAtPosition(position);
                    String name = listObj.getName();

                    final String[] value = {name};
                    String args[] = {EventConstants.COL_PHONE};

                    Cursor c3 = sqLiteDatabase.query(EventConstants.GuestTable, args, EventConstants.COL_GUESTNM + "=?", value, null, null, null);
                    if (c3 != null && c3.moveToFirst()) {
                        Toast.makeText(context, "ll", Toast.LENGTH_SHORT).show();
                        String ph = c3.getString(c3.getColumnIndex(EventConstants.COL_PHONE));
                        Intent obj = new Intent(Intent.ACTION_CALL);
                        Uri uri = Uri.parse("tel:" + ph);
                        obj.setData(uri);
                        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                            // TODO: Consider calling
                            //    ActivityCompat#requestPermissions
                            // here to request the missing permissions, and then overriding
                            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                            //                                          int[] grantResults)
                            // to handle the case where the user grants the permission. See the documentation
                            // for ActivityCompat#requestPermissions for more details.
                            return;
                        }
                        obj.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.getApplicationContext().startActivity(obj);

                    }
                    c3.close();

                }
            });
            viewHolder.img1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    listObj = (ListObj) listv.getItemAtPosition(position);
                    String name = listObj.getName();

                    final String[] value = {name};
                    String args[] = {EventConstants.COL_PHONE};
                    Cursor c4 = sqLiteDatabase.query(EventConstants.GuestTable, args, EventConstants.COL_GUESTNM + "=?", value, null, null, null);
                    if (c4 != null && c4.moveToFirst()) {
                        Toast.makeText(context, "ll", Toast.LENGTH_SHORT).show();
                        String ph = c4.getString(c4.getColumnIndex(EventConstants.COL_PHONE));
                        Intent i = new Intent(context, SmsAct.class);
                        i.putExtra("key", ph);
                        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.getApplicationContext().startActivity(i);
                    }
                    c4.close();
                }
            });


            l = list.get(position);
            viewHolder.text.setText(l.getName());
            viewHolder.img.setImageResource(l.getId());
            viewHolder.img1.setImageResource(l.getId1());





        return convertView;

    }
    private static class ViewHolder {
        TextView text;
        ImageView img,img1;
    }

}
