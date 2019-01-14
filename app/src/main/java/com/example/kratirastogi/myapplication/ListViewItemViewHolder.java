package com.example.kratirastogi.myapplication;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.CheckBox;
import android.widget.TextView;

public class ListViewItemViewHolder extends RecyclerView.ViewHolder{
    private CheckBox itemCheckbox;

    private TextView itemTextView;
    private TextView itemTextView1;
    private TextView itemTextView2;
    private TextView itemTextView3;




    public ListViewItemViewHolder(View itemView) {
        super(itemView);
    }

    public CheckBox getItemCheckbox() {
        return itemCheckbox;
    }

    public void setItemCheckbox(CheckBox itemCheckbox) {
        this.itemCheckbox = itemCheckbox;
    }

    public TextView getItemTextView() {
        return itemTextView;
    }

    public void setItemTextView(TextView itemTextView) {
        this.itemTextView = itemTextView;
    }
    public TextView getItemTextView1() {
        return itemTextView1;
    }

    public void setItemTextView1(TextView itemTextView) {
        this.itemTextView1 = itemTextView;
    }
    public TextView getItemTextView2() {
        return itemTextView2;
    }

    public void setItemTextView2(TextView itemTextView) {
        this.itemTextView2 = itemTextView;
    }
    public TextView getItemTextView3() {
        return itemTextView3;
    }

    public void setItemTextView3(TextView itemTextView) {
        this.itemTextView3 = itemTextView;
    }
}
