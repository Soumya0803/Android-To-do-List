package com.example.soumya.to_do;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Soumya on 23-06-2017.
 */


public class TodoListAdapter extends ArrayAdapter<Todo> {//arrayadapter is inbuilt
    ArrayList<Todo> todoarraylist; //todoarrlist
    Context context;
    OnListButtonClickedListener listener;


    void setOnListButtonClickedListener(OnListButtonClickedListener listener){
        this.listener = listener;
    }



    public TodoListAdapter(Context context, ArrayList<Todo> expensearraylist) {
        super(context, 0);//resorce is made 0 because here it needs id to create views but we are going  create views ourselves
        this.context=context;
        this.todoarraylist=expensearraylist;

    }
    @Override
    public int getCount() {
        return todoarraylist.size();  //arrayadapter needs size to create the list give function or mention object in super in constructor
    }

    static class TodoViewHolder {//this class is used to reduce the cost of finding views again n againn is static inner class
        TextView nameTextView;
        TextView categoryTextView;
        TextView dateTextView;

        TodoViewHolder(TextView nameTextView, TextView categoryTextView, TextView dateTextView) {
            this.nameTextView = nameTextView;
            this.categoryTextView = categoryTextView;
            this.dateTextView = dateTextView;
        }
    }


        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {//called when view is needed

            if(convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.list_item, null);

                TextView nameTextView = (TextView) convertView.findViewById(R.id.titeTextView);
                  TextView categoryTextView = (TextView) convertView.findViewById(R.id.categoryTextView);
                 TextView dateeTextView = (TextView) convertView.findViewById(R.id.dateTextView);
                TodoViewHolder expenseViewHolder = new TodoViewHolder(nameTextView, categoryTextView,dateeTextView);
                convertView.setTag(expenseViewHolder);

            }

            Todo e = todoarraylist.get(position);
            TodoViewHolder expenseViewHolder = (TodoViewHolder)convertView.getTag();
            expenseViewHolder.nameTextView.setText(e.title);
            expenseViewHolder.categoryTextView.setText(e.category);
            expenseViewHolder.dateTextView.setText(e.date);

            return  convertView;
        }
    }

interface OnListButtonClickedListener{
    //**Better to create interface so that any activiyty can communicate
    // with this adapter if interface was not created and main activity could comunicate
    // then all other activities would require seperate adapter
    void listButtonClicked(View v, int pos);
}








