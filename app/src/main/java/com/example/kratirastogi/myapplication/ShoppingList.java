package com.example.kratirastogi.myapplication;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.kratirastogi.myapplication.bean.ListViewItemDTO;
import com.example.kratirastogi.myapplication.dbutil.EventConstants;
import com.example.kratirastogi.myapplication.dbutil.EventManager;

import java.util.ArrayList;
import java.util.List;

import static android.content.Context.MODE_PRIVATE;
import static com.example.kratirastogi.myapplication.R.color.colorAct;
import static com.example.kratirastogi.myapplication.R.color.colorPrimary;

public class ShoppingList extends Fragment  {
    EventManager eventManager;
    SQLiteDatabase sqLiteDatabase;
    Spinner sp;
    ListView list;
    Button show;
    CheckBox check;
    CheckBox itemCheckbox;
    SharedPreferences states;
    String data,data1;
    String val[];
    TextView total,spent,remain;
    int sum1=0;




    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        eventManager = new EventManager(getContext());
        sqLiteDatabase = eventManager.opendb();
         states = requireContext().getSharedPreferences("states",MODE_PRIVATE);
        boolean isChecked = states.getBoolean("holder.chkBox", false);
        data=Home.getActivityInstance().getData();
        data1=Home.getActivityInstance().getData1();
        val= new String[]{data1};
        Toast.makeText(getContext(), ""+data, Toast.LENGTH_SHORT).show();



    }


    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.shopadd, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @SuppressLint("ResourceAsColor")
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.shoppinglist, container, false);

        final Button selectRemoveButton = (Button) view.findViewById(R.id.list_remove_selected_rows);
        show = view.findViewById(R.id.show);
        list = view.findViewById(R.id.listView);
        total = view.findViewById(R.id.total);
        spent = view.findViewById(R.id.spent);
        remain = view.findViewById(R.id.remain);


        list.setChoiceMode(ListView.CHOICE_MODE_MULTIPLE);


        final List<ListViewItemDTO> initItemList = this.getInitViewItemDtoList();

        final ListViewItemCheckboxBaseAdapter listViewDataAdapter = new ListViewItemCheckboxBaseAdapter(getContext(), initItemList);

        listViewDataAdapter.notifyDataSetChanged();

        list.setAdapter(listViewDataAdapter);
        if (listViewDataAdapter.getCount() > 0) {
            selectRemoveButton.setEnabled(true);

            show.setEnabled(true);

        } else {
            selectRemoveButton.setEnabled(false);


        }

        final CheckBox checkBox = view.findViewById(R.id.list_view_item_checkbox);
       list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int itemIndex, long l) {

                Object itemObject = adapterView.getAdapter().getItem(itemIndex);
                ListViewItemDTO itemDto = (ListViewItemDTO) itemObject;
                itemCheckbox = view.findViewById(R.id.list_view_item_checkbox);
                if (itemDto.isChecked()) {
                    itemCheckbox.setChecked(false);
                    itemDto.setChecked(false);

                } else {
                    itemCheckbox.setChecked(true);
                    itemDto.setChecked(true);

                }


            }

        });
       list.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
           @Override
           public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
               Object itemObject = parent.getAdapter().getItem(position);
               final ListViewItemDTO itemDto = (ListViewItemDTO) itemObject;
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
                               String name = itemDto.getItemText();
                               initItemList.remove(itemDto);
                               ListViewItemCheckboxBaseAdapter listViewItemCheckboxBaseAdapter = new ListViewItemCheckboxBaseAdapter(getActivity(), initItemList);
                               list.setAdapter(listViewItemCheckboxBaseAdapter);
                               listViewItemCheckboxBaseAdapter.notifyDataSetChanged();
                               String[] v = {name,data1};
                               int r = sqLiteDatabase.delete(EventConstants.ShoppingTable, EventConstants.COL_SHOPNM + "=? and " +EventConstants.COL_EVNTID+"=?", v);
                               int r1 = sqLiteDatabase.delete(EventConstants.TotalPrice, EventConstants.COL_SHOPNM + "=? and " +EventConstants.COL_EVNTID+"=?", v);
                               if (r > 0 && r1 > 0) {
                                   Toast.makeText(getContext(), "data deleted successfully", Toast.LENGTH_SHORT).show();
                                   Intent mIntent = getActivity().getIntent();
                                   getActivity().finish();
                                   getActivity().startActivity(mIntent);
                               }
                           }
                       });

               builder1.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                      dialog.cancel();
                   }
               });
                       AlertDialog bb=builder1.create();
                       bb.show();




                   }
               });
               builder.setPositiveButton("Edit", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       String name = itemDto.getItemText();
                       Intent intent=new Intent(getActivity().getBaseContext(),EditShop.class);
                       intent.putExtra("abc",name);
                      // intent.putExtra("lk",data);
                       intent.putExtra("lk1",data1);
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


        selectRemoveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View view) {

                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                builder.setMessage("Are you sure to remove selected listview items?");

                builder.setPositiveButton("yes", new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialogInterface, int which) {
                        int size = initItemList.size();

                        for (int i = 0; i < size; i++) {
                            ListViewItemDTO dto = initItemList.get(i);
                            String nam = dto.getItemText();

                            String val[] = {nam,data1};
                            String v = "";
                            if (dto.isChecked()) {
                                String ar[] = {EventConstants.COL_TOTPRICE};
                                Cursor c2 = sqLiteDatabase.query(EventConstants.ShoppingTable, ar, EventConstants.COL_SHOPNM + "=? and "+EventConstants.COL_EVNTID+"=?", val, null, null, null);
                                if (c2 != null && c2.moveToFirst()) {
                                    v = c2.getString(c2.getColumnIndex(EventConstants.COL_TOTPRICE));
                                    sum1 = sum1 + Integer.valueOf(v);
                                }
                                ContentValues contentValues = new ContentValues();
                                contentValues.put(EventConstants.COL_EVNTID, data1);
                               // contentValues.put(EventConstants.COL_EVENM, data);
                                contentValues.put(EventConstants.COL_TASK, nam);
                                contentValues.put(EventConstants.COL_TOTPRICE, v);

                                long c = sqLiteDatabase.insert(EventConstants.Todolist, null, contentValues);
                                if (c > 0) {
                                    Toast.makeText(getContext(), "done", Toast.LENGTH_SHORT).show();
                                    Intent mIntent = getActivity().getIntent();
                                    getActivity().finish();
                                    getActivity().startActivity(mIntent);

                                }



                                int r = sqLiteDatabase.delete(EventConstants.ShoppingTable, EventConstants.COL_SHOPNM + "=? and "+EventConstants.COL_EVNTID+"=?", val);
                                if (r > 0) {
                                    // Toast.makeText(getContext(), "data deleted successfully", Toast.LENGTH_SHORT).show();
                                }

                                initItemList.remove(i);

                                i--;
                                size = initItemList.size();


                            }

                        }

                        if (size == 0) {
                            selectRemoveButton.setEnabled(false);
                        }
                        listViewDataAdapter.notifyDataSetChanged();
                    }
                });

                AlertDialog b = builder.create();
                b.show();


            }

        });


        show.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), PurchasedItems.class);
               // intent.putExtra("k",data);
                intent.putExtra("k1",data1);
                startActivity(intent);
            }
        });


        Cursor c12 = sqLiteDatabase.query(EventConstants.Todolist, null, EventConstants.COL_EVNTID+"=?", val, null, null, null);
        int  amn1 = 0;
        if (c12 != null && c12.moveToFirst()) {
            do {
                String m = c12.getString(c12.getColumnIndex(EventConstants.COL_TOTPRICE));
                int t = Integer.valueOf(m);
                sum1 = sum1 + t;


            } while (c12.moveToNext());
            spent.setText(String.valueOf(sum1));

        }

            Cursor c = sqLiteDatabase.query(EventConstants.TotalPrice, null, EventConstants.COL_EVNTID+"=?", val, null, null, null);
            {
                int sum = 0, amn = 0;
                if (c != null && c.moveToFirst()) {
                    do {
                        String m = c.getString(c.getColumnIndex(EventConstants.COL_TOTPRICE));
                        int t = Integer.valueOf(m);
                        sum = sum + t;


                    } while (c.moveToNext());
                    total.setText(String.valueOf(sum));
                    remain.setText(String.valueOf(sum-sum1));

                }

                String args[] = new String[]{EventConstants.COL_AMNT};
                Cursor c1 = sqLiteDatabase.query(EventConstants.EventTable, args, EventConstants.COL_EVNTID+"=?", val, null, null, null);
                if (c1 != null && c1.moveToFirst()) {
                    String am = c1.getString(c1.getColumnIndex(EventConstants.COL_AMNT));
                    amn = Integer.valueOf(am);
                     //Toast.makeText(getContext(), ""+amn, Toast.LENGTH_SHORT).show();


                if (sum > amn) {
                    AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                    View mView = getLayoutInflater().inflate(R.layout.checkbox, null);
                    builder.setMessage("Your budget has exceeded");
                    CheckBox mCheckBox = mView.findViewById(R.id.checkBox);
                    builder.setView(mView);
                    builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            dialogInterface.cancel();
                        }
                    });

                    AlertDialog b1 = builder.create();
                    b1.show();

                    mCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                            if (compoundButton.isChecked()) {
                                storeDialogStatus(true,String.valueOf(data1));
                            } else {
                                storeDialogStatus(false,String.valueOf(data1));
                            }
                        }
                    });

                    if (getDialogStatus() && getDialogStatus1().equals(String.valueOf(data1))) {
                        b1.hide();
                    } else {
                        b1.show();
                    }

                }
                }
            }


            return view;
        }

        private void storeDialogStatus ( boolean isChecked,String id){
            SharedPreferences mSharedPreferences = this.getActivity().getSharedPreferences("CheckItem", MODE_PRIVATE);
            SharedPreferences.Editor mEditor = mSharedPreferences.edit();
            mEditor.putBoolean("item", isChecked);
            mEditor.putString("item1", id);
            mEditor.apply();
        }

        private boolean getDialogStatus () {
            SharedPreferences mSharedPreferences = this.getActivity().getSharedPreferences("CheckItem", MODE_PRIVATE);
            return mSharedPreferences.getBoolean("item", false);
        }
    private String getDialogStatus1 () {
        SharedPreferences mSharedPreferences = this.getActivity().getSharedPreferences("CheckItem", MODE_PRIVATE);
        return mSharedPreferences.getString("item1","0" );
    }


        public boolean onOptionsItemSelected (MenuItem item){
            int id = item.getItemId();
            switch (id) {

                case R.id.add1:
                    Intent intent1 = new Intent(getContext(), AddShop.class);
                   // intent1.putExtra("key",data);
                    intent1.putExtra("key1",data1);
                    startActivity(intent1);
                    getActivity().getSupportFragmentManager().beginTransaction().remove(this).commit();

                    getActivity().onBackPressed();
                    getActivity().finish();
                    return true;
              //  case R.id.refresh:

                   // return true;

            }

            return super.onOptionsItemSelected(item);
        }




    private List<ListViewItemDTO> getInitViewItemDtoList() {


        List<ListViewItemDTO> ret = new ArrayList<ListViewItemDTO>();

        Cursor c = sqLiteDatabase.query(EventConstants.ShoppingTable, null, EventConstants.COL_EVNTID+"=?", val, null, null, null);
        if (c != null && c.moveToFirst()) {


            do {

                String name = c.getString(c.getColumnIndex(EventConstants.COL_SHOPNM));
                String price=c.getString(c.getColumnIndex(EventConstants.COL_PRICE));
                String unit=c.getString(c.getColumnIndex(EventConstants.COL_UNIT));
                String tp=c.getString(c.getColumnIndex(EventConstants.COL_TOTPRICE));
                ListViewItemDTO dto = new ListViewItemDTO();
                dto.setChecked(false);
                dto.setItemText(name);
                dto.setText1(price);
                dto.setText3(unit);
                dto.setText5(tp);
                ret.add(dto);

            } while (c.moveToNext());




        }

        return ret;
    }

}