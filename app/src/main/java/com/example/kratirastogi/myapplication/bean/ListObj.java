package com.example.kratirastogi.myapplication.bean;

public class ListObj
{
    String name;
    int id1,id;

    public ListObj(String name, int id1,int id) {
        this.name = name;
        this.id1=id1;
        this.id = id;

    }

    public int getId() {
        return id;
    }

    public int getId1() {
        return id1;
    }

    public void setId1(int id1) {
        this.id1 = id1;
    }

    public void setId(int id) {
        this.id = id;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name+"    "+id1+"     "+id;
    }
}
