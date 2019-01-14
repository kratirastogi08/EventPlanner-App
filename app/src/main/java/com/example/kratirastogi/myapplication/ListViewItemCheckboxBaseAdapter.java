package com.example.kratirastogi.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.kratirastogi.myapplication.bean.ListViewItemDTO;

import java.util.List;

public class ListViewItemCheckboxBaseAdapter extends BaseAdapter {
    private List<ListViewItemDTO> listViewItemDtoList = null;

    private Context ctx = null;

    public ListViewItemCheckboxBaseAdapter(Context ctx, List<ListViewItemDTO> listViewItemDtoList) {
        this.ctx = ctx;
        this.listViewItemDtoList = listViewItemDtoList;
    }
    @Override
    public int getCount() {
        int ret = 0;
        if(listViewItemDtoList!=null)
        {
            ret = listViewItemDtoList.size();
        }
        return ret;
    }

    @Override
    public Object getItem(int itemIndex) {
        Object ret = null;
        if(listViewItemDtoList!=null) {
            ret = listViewItemDtoList.get(itemIndex);
        }
        return ret;
    }

    @Override
    public long getItemId(int itemIndex) {
        return itemIndex;
    }

    @Override
    public View getView(int itemIndex, View convertView, ViewGroup viewGroup) {

        ListViewItemViewHolder viewHolder = null;

        if(convertView!=null)
        {
            viewHolder = (ListViewItemViewHolder) convertView.getTag();
        }else
        {
            convertView = View.inflate(ctx, R.layout.shoplist, null);

            final CheckBox listItemCheckbox = (CheckBox) convertView.findViewById(R.id.list_view_item_checkbox);

            TextView listItemText = (TextView) convertView.findViewById(R.id.list_view_item_text);
            TextView listItemText1 = (TextView) convertView.findViewById(R.id.text1);
            TextView listItemText2 = (TextView) convertView.findViewById(R.id.text3);
            TextView listItemText3 = (TextView) convertView.findViewById(R.id.text5);
            viewHolder = new ListViewItemViewHolder(convertView);

            viewHolder.setItemCheckbox(listItemCheckbox);

            viewHolder.setItemTextView(listItemText);
            viewHolder.setItemTextView1(listItemText1);
            viewHolder.setItemTextView2(listItemText2);
            viewHolder.setItemTextView3(listItemText3);




            convertView.setTag(viewHolder);
        }

        ListViewItemDTO listViewItemDto = listViewItemDtoList.get(itemIndex);

       viewHolder.getItemCheckbox().setChecked(listViewItemDto.isChecked());

        viewHolder.getItemTextView().setText(listViewItemDto.getItemText());
        viewHolder.getItemTextView1().setText(listViewItemDto.getText1());
        viewHolder.getItemTextView2().setText(listViewItemDto.getText3());
        viewHolder.getItemTextView3().setText(listViewItemDto.getText5());

        return convertView;
    }
}
