package com.example.kratirastogi.myapplication.bean;

public class ListViewItemDTO {
    private boolean checked = false;

    private String itemText = "";
    private String text1="";

    private String text3="";

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText3() {
        return text3;
    }

    public void setText3(String text3) {
        this.text3 = text3;
    }

    public String getText5() {
        return text5;
    }

    public void setText5(String text5) {
        this.text5 = text5;
    }

    private String text5="";

    public boolean isChecked() {
        return checked;
    }

    public void setChecked(boolean checked) {
        this.checked = checked;
    }

    public String getItemText() {
        return itemText;
    }

    public void setItemText(String itemText) {
        this.itemText = itemText;
    }

}
