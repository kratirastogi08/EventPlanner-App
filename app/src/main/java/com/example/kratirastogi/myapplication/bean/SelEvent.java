package com.example.kratirastogi.myapplication.bean;

public class SelEvent
{
    public SelEvent(String name,String id) {
        this.name = name;
        this.id=id;
    }

    String name;
    String id;



    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return name;
    }
}
