package com.example.soumya.to_do;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Soumya on 27-06-2017.
 */

public class TodoOpenHelper extends SQLiteOpenHelper
{
    public  final static String TODO_TABLE_NAME="todo";
    public  final static String TODO_TITLE="title";
    public  final static String TODO_ID="id";
    public  final static String TODO_CATEGORY="category";
    public  final static String TODO_DATE="date";

    public TodoOpenHelper(Context context) {
        super(context, "Todo.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase ) {
        String query = "create table " + TODO_TABLE_NAME +" ( " + TODO_ID +
                " integer primary key autoincrement, " + TODO_TITLE +" text, "
                + TODO_CATEGORY + " real, "
                + TODO_DATE + " text);";



      /*  String query="create table"+TODO_TABLE_NAME +"("+TODO_ID +"integer primary key autoincrement" +TODO_CATEGORY + "text"
                +TODO_DATE+ "text);";*/
        sqLiteDatabase.execSQL(query);



    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
