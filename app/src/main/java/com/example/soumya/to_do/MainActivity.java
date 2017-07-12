package com.example.soumya.to_do;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import static android.R.attr.id;
import static android.R.attr.title;
import static com.example.soumya.to_do.TodoOpenHelper.TODO_ID;

public class MainActivity extends AppCompatActivity implements OnListButtonClickedListener{


    ListView listView;

    ArrayList<Todo> todoList;
    TodoListAdapter todoListAdapter;
    int pos;
    long delid;
    FloatingActionButton fab;
    CoordinatorLayout coordinatorLayout;
    //Button clearbutton=(Button)findViewById(R.id.clear);

   // CheckBox checkbox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        listView = (ListView) findViewById(R.id.expenseListView);
       // Button button=(Button)findViewById(R.id.clear);
        fab=(FloatingActionButton) findViewById(R.id.fab);
        coordinatorLayout= (CoordinatorLayout) findViewById(R.id.cl);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        todoList=new ArrayList<>();
        todoListAdapter = new TodoListAdapter(this, todoList);
        listView.setAdapter(todoListAdapter);
       // updatetodoList();
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

             /*   Intent i2 = new Intent(MainActivity.this,DetailsActivity.class);
                // i2.putExtra("id", -1);
                i2.putExtra("r",2);
                startActivityForResult(i2,2);
*/


                Intent i2 = new Intent(MainActivity.this,DetailsActivity.class);
                i2.putExtra("r",2);
                startActivityForResult(i2,2);
            }
        });


       /*   clearbutton.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View view) {
                  cleartodo();
              }
          });*/
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long l) {
                pos=position;


                //parent.getAdapter().getItem(position);
                Intent i = new Intent(MainActivity.this, DetailsActivity.class);
                i.putExtra(IntentConstants.TODO_TITLE,todoList.get(position).title);
                i.putExtra(IntentConstants.TODO_CATEGORY, todoList.get(position).category);
                i.putExtra(IntentConstants.TODO_DATE,todoList.get(position).date);
                i.putExtra("id",todoList.get(pos).id);
                i.putExtra("r",1);
                startActivityForResult(i, 1);
                //todoListAdapter.notifyDataSetChanged();



//              ExpenseDetailActivity.title = "abcd";

//                Toast.makeText(MainActivity.this, expenseList.get(position)
//                        + " Clicked ", Toast.LENGTH_SHORT).show();

            }
        });

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int i, long id) {//i=pos
                delid=todoList.get(i).id;
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);


                builder.setTitle("Delete");
               // builder.setMessage("Are you sure want to delete?");
                builder.setCancelable(false);// dialog box does not dissapear when clicked anywhere on the screen
//            builder.setMessage("Are you sure you want to delete ??");

                View v = getLayoutInflater().inflate(R.layout.dialog_view,null);

                final TextView tv = (TextView) v.findViewById(R.id.tv);
                tv.setText("Are you sure you want to delete ??");
                builder.setView(v);

                builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        todoList.remove(i);
                        todoListAdapter.notifyDataSetChanged();
                        deleteEntry();

                        //updatetodoList();
                    }
                });
                builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();




                return true;
            }
        });

         updatetodoList();
    }


    private void updatetodoList() {

        TodoOpenHelper todoOpenHelper = new TodoOpenHelper(this);
        todoList.clear();
        SQLiteDatabase database = todoOpenHelper.getReadableDatabase();
        Cursor cursor = database.query(TodoOpenHelper.TODO_TABLE_NAME,null,null,null,null, null, null);
        while(cursor.moveToNext()){

            int id = cursor.getInt(cursor.getColumnIndex(TODO_ID));
            String title = cursor.getString(cursor.getColumnIndex(TodoOpenHelper.TODO_TITLE));//better to use get column index instead of providing direct value.
            String category = cursor.getString(cursor.getColumnIndex(TodoOpenHelper.TODO_CATEGORY));
            String date = cursor.getString(cursor.getColumnIndex(TodoOpenHelper.TODO_DATE));
            //int id = cursor.getInt(cursor.getColumnIndex(TodoOpenHelper.TODO_ID));

            Todo t = new Todo(id,title,category,date);
            todoList.add(t);

        }

        todoListAdapter.notifyDataSetChanged();

    }

    private void deleteEntry() {
        TodoOpenHelper todoOpenHelper = new TodoOpenHelper(MainActivity.this);
        SQLiteDatabase db = todoOpenHelper.getWritableDatabase();
       // String query="delete from " +TodoOpenHelper.TODO_TABLE_NAME + " where id=" + delid;
        //db.execSQL(query);
       db.delete(TodoOpenHelper.TODO_TABLE_NAME, todoOpenHelper.TODO_ID+"="+delid, null);
        Snackbar.make(coordinatorLayout,"Deleted!",Snackbar.LENGTH_SHORT).show();


    }
    private void cleartodo() {
        TodoOpenHelper todoOpenHelper = new TodoOpenHelper(MainActivity.this);
        SQLiteDatabase db = todoOpenHelper.getWritableDatabase();
     //   String query="delete from " +TodoOpenHelper.TODO_TABLE_NAME + " where id=" + delid;
        //db.execSQL(query);
        db.delete(TodoOpenHelper.TODO_TABLE_NAME, null, null);
        todoList.clear();
        todoListAdapter.notifyDataSetChanged();

        Snackbar snackbar=Snackbar.make(coordinatorLayout,"Cleared!",Snackbar.LENGTH_LONG);
        snackbar
                .show();

    }





    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == 1) { //edit
            if (resultCode == RESULT_OK) {
                String newTitle = data.getStringExtra(IntentConstants.TODO_TITLE);
                todoList.get(pos).title = newTitle;
                String newCategory = data.getStringExtra(IntentConstants.TODO_CATEGORY);
                todoList.get(pos).category = newCategory;
                String newDate = data.getStringExtra(IntentConstants.TODO_DATE);
                todoList.get(pos).date = newDate;
                todoListAdapter.notifyDataSetChanged();

                Snackbar snackbar = Snackbar.make(coordinatorLayout, "Edited!", Snackbar.LENGTH_SHORT);
                snackbar
                        .show();

                //Log.i("MainActivityTag", "New Title " + newTitle);
            } else if (resultCode == RESULT_CANCELED) {

            }

        }
        if (requestCode == 2)  //add
        {

            if (resultCode == RESULT_OK) {

                String newTitle = data.getStringExtra(IntentConstants.TODO_TITLE);
                String newCategory = data.getStringExtra(IntentConstants.TODO_CATEGORY);
                String newDate = data.getStringExtra(IntentConstants.TODO_DATE);
                Todo t = new Todo();
                //if(newTitle!=null)
                t.title = newTitle;
                //if(newCategory!=null)
                t.category = newCategory;
                //if(newDate!=null)
                t.date = newDate;
                todoList.add(t);
                todoListAdapter.notifyDataSetChanged();


                Snackbar snackbar = Snackbar.make(coordinatorLayout, "Added!", Snackbar.LENGTH_SHORT);
                snackbar.show();

            } else if (resultCode == RESULT_CANCELED) {

            }


        }
    }



    @Override

    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.mainmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if(R.id.Clear == id){
/*
            Intent i2 = new Intent(MainActivity.this,DetailsActivity.class);
           // i2.putExtra("id", -1);
            i2.putExtra("r",2);
            startActivityForResult(i2,2);*/
            cleartodo();
        }
        else if(id == R.id.AboutUs){
            Intent i = new Intent();
            i.setAction(Intent.ACTION_VIEW);
            Uri uri = Uri.parse("https://www.codingninjas.in");
            i.setData(uri);

            startActivity(i);
        }else if(id == R.id.ContactUs){
            Intent i = new Intent();
            i.setAction(Intent.ACTION_CALL);
            Uri uri = Uri.parse("tel:123345");
            i.setData(uri);
            startActivity(i);
        }else if(id == R.id.Feedback){
            Intent i = new Intent();
            i.setAction(Intent.ACTION_SENDTO);
            Uri uri = Uri.parse("mailto:soumyachopra@gmail.com");
            i.putExtra(Intent.EXTRA_SUBJECT,"Feedback");
            i.setData(uri);
            if(i.resolveActivity(getPackageManager()) != null) //if action canot be performed so that app does not crash{
                startActivity(i);
        }


/*
        else if(R.id.remove == id){

        AlertDialog.Builder builder = new AlertDialog.Builder(this);


            builder.setTitle("Delete");
            builder.setCancelable(false);
//            builder.setMessage("Are you sure you want to delete ??");

            View v = getLayoutInflater().inflate(R.layout.dialog_view,null);

            final TextView tv = (TextView) v.findViewById(R.id.dialogtextview);
            tv.setText("Are you sure you want to delete ??");
            builder.setView(v);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    todoList.remove(todoList.size() - 1);
                    todoListAdapter.notifyDataSetChanged();
                }
            });
            builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            });

            AlertDialog dialog = builder.create();
            dialog.show();

        }
*/

        return true;

    }


    @Override
    public void listButtonClicked(View v, int pos) {

    }
}