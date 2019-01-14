package com.example.kratirastogi.myapplication.dbutil;

public class EventConstants
{
    public static final String DBNAME="EVNNTTTT5";
    public static final int DBVERSION=1;
    public static final String TotalPrice="Tot";
    public static final String EventTable="Event";
    public static final String GuestTable="Guest";
    public static final String Todolist="Todolist";
    public static final String ShoppingTable="Shopping";
    public static final String COL_EVENM="Eventname";
    public static final String COL_EVEPLACE="Eventplace";
    public static final String COL_EVEDATE="Eventdate";
    public static final String COL_EVETIME="Eventtime";
    public static final String COL_GUESTNM="Guestname";
    public static final String COL_GUESTGEN="Guestgender";
    public static final String COL_GUESTAGE="Guestage";
    public static final String COL_EMAIL="Email";
    public static final String COL_PHONE="Phno";
    public static final String COL_TASK="Task";
    public static final String COL_SHOPNM="Shoppingname";
    public static final String COL_UNIT="Unit";
    public static final String COL_PRICE="Price";
    public static final String COL_NAME="Name";
    public static final String COL_PASS="Password";
    public static final String COL_EVNTID="Id";
    public static final String COL_STATUS="status";
    public static final String COL_AMNT="amnt";
    public static final String COL_TOTPRICE="totalprice";
    public static final String COL_QUANT="quant";
  //  public static final String COL_ALL="All";



    public static final String STQUERY="create table Event(Id text primary key,Eventname text,Eventplace text,Eventdate date,Eventtime long,amnt text)";
    public static final String STQUERY5="create table Guest(Id text,Eventname text,Guestname text,Guestgender text,Guestage text,status text,Phno text,Email text)";
    public static final String STQUERY2="create table Todolist(Id text,Eventname text,Task text,totalprice text)";
    public static final String STQUERY3="create table Shopping(Id text,Eventname text,Shoppingname text,quant text,Unit text,Price text,totalprice text)";
    public static final String STQUERY4="create table Tot(Id text,Eventname text,Shoppingname text,totalprice text)";


}
