package com.example.soumya.to_do;

import java.io.Serializable;

/**
 * Created by Soumya on 23-06-2017.
 */

public class Todo implements Serializable
{
    int  id;
    String title;
    String  category;
    String date;
    public Todo() {
        this.id = id;
        this.title = title;
        this.date = date;
        this.category = category;

    }


    public Todo(int id, String title, String category, String date) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.category = category;

    }


    // String description;
}
