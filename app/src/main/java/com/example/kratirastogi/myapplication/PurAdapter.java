package com.example.kratirastogi.myapplication;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.kratirastogi.myapplication.bean.Purchase;

import java.util.ArrayList;

public class PurAdapter extends BaseAdapter {
    private Context mContext;
    ArrayList<Purchase> mylist=new ArrayList<>();
    public PurAdapter(ArrayList<Purchase> itemArray,Context mContext) {
        super();
        this.mContext = mContext;
        mylist=itemArray;
    }
    @Override
    public int getCount() {
        return mylist.size();
    }
    @Override
    public String getItem(int position) {
        return mylist.get(position).toString();
    }
    @Override
    public long getItemId(int position) {
        return position;
    }
    public void onItemSelected(int position) {
    }
    public class ViewHolder {
        public TextView nametext;
        public CheckBox tick;
    }
    @Override
    public View getView(final int position, View convertView,
                        ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder view = null;
        LayoutInflater inflator = ((Activity) mContext).getLayoutInflater();
        if (view == null) {
            view = new ViewHolder();
            convertView = inflator.inflate(  R.layout.purchaseitem, null);
            view.nametext = (TextView) convertView.findViewById(R.id.text);
            view.tick=(CheckBox)convertView.findViewById(R.id.checkbox1);


            convertView.setTag(view);
        } else {
            view = (ViewHolder) convertView.getTag();
        }
        view.tick.setTag(position);
        view.nametext.setText("" + mylist.get(position).getName());
        view.tick.setChecked(mylist.get(position).isChecked());
        return convertView;
    }
}
