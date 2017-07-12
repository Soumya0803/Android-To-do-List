package com.example.soumya.to_do;

import android.app.DatePickerDialog;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import java.util.Calendar;

import static com.example.soumya.to_do.IntentConstants.TODO_CATEGORY;
import static com.example.soumya.to_do.R.id.submitButton;

public class DetailsActivity extends AppCompatActivity {




    String title;
    String category;
    String date;
    long date2;
    EditText titleEditText;
    EditText categoryEditText;
    EditText dateEditText;
   int todoid;
    int r;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        titleEditText = (EditText) findViewById(R.id.titleEditText);
        categoryEditText=(EditText)findViewById(R.id.categoryEditText);
        dateEditText=(EditText)findViewById(R.id.dateEditText);
        Button alarm =(Button)findViewById(R.id.alarm);
        Button submitButton = (Button) findViewById(R.id.submitButton);

        final Intent i = getIntent();

        title   = i.getStringExtra(IntentConstants.TODO_TITLE);
        category=i.getStringExtra(IntentConstants.TODO_CATEGORY);
        date=i.getStringExtra(IntentConstants.TODO_DATE);

        titleEditText.setText(title);
        dateEditText.setText(date);
        categoryEditText.setText(category);

        todoid= i.getIntExtra("id",-1);
        r=i.getIntExtra("r",0);

        submitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String newTitle = titleEditText.getText().toString();
                String newCategory = categoryEditText.getText().toString();
                String newDate = dateEditText.getText().toString();

                TodoOpenHelper todoOpenHelper = new TodoOpenHelper(DetailsActivity.this);

                SQLiteDatabase database = todoOpenHelper.getWritableDatabase();


                ContentValues cv = new ContentValues();
                cv.put(TodoOpenHelper.TODO_TITLE, newTitle);
                cv.put(TodoOpenHelper.TODO_CATEGORY, newCategory);
                cv.put(TodoOpenHelper.TODO_DATE, newDate);
                if(r==2)

                { database.insert(TodoOpenHelper.TODO_TABLE_NAME, null, cv);}
                else if(r==1) {

                    database.update(TodoOpenHelper.TODO_TABLE_NAME,cv, TodoOpenHelper.TODO_ID + "=" +todoid,null);
                }
                //Intent i = new Intent();
                //  i.putExtra(IntentConstants.TODO_TITLE, newTitle);


                Intent i = new Intent();
                if(newTitle!=null)
                i.putExtra(IntentConstants.TODO_TITLE, newTitle);
                if(newCategory!=null)
                i.putExtra(IntentConstants.TODO_CATEGORY, newCategory);
                if(newDate!=null)
                i.putExtra(IntentConstants.TODO_DATE, newDate);
                setResult(RESULT_OK, i);
                finish();




                }


        });


        // Steps for Date picker
        // Will show Date picker dialog on clicking edit text
        dateEditText = (EditText) findViewById(R.id.dateEditText);
        dateEditText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar newCalendar = Calendar.getInstance();
                int month = newCalendar.get(Calendar.MONTH);  // Current month
                int year = newCalendar.get(Calendar.YEAR);   // Current year


                showDatePicker(DetailsActivity.this, year, month, 1);

            }
        });
                 alarm.setOnClickListener(new View.OnClickListener() {
                     @Override
                     public void onClick(View view) {
                         Intent i=new Intent(DetailsActivity.this,Alarms.class);

                         startActivityForResult(i,4);
                     }
                 });


    }
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (resultCode == RESULT_OK) {


            Toast.makeText(DetailsActivity.this, "Alarm Set", Toast.LENGTH_SHORT).show();

            //Log.i("MainActivityTag", "New Title " + newTitle);
        } else if (resultCode == RESULT_CANCELED) {

        }
    }






    public void showDatePicker(Context context, int initialYear, int initialMonth, int initialDay) {

        // Creating datePicker dialog object
        // It requires context and listener that is used when a date is selected by the user.

        DatePickerDialog datePickerDialog = new DatePickerDialog(context,
                new DatePickerDialog.OnDateSetListener() {

                    //This method is called when the user has finished selecting a date.
                    // Arguments passed are selected year, month and day
                    @Override
                    public void onDateSet(DatePicker datepicker, int year, int month, int day) {

                        // To get epoch, You can store this date(in epoch) in database
                        Calendar calendar = Calendar.getInstance();
                        calendar.set(year, month, day);
                        date2 = calendar.getTime().getTime();
                        // Setting date selected in the edit text
                        dateEditText.setText(day + "/" + (month + 1) + "/" + year);
                    }
                }, initialYear, initialMonth, initialDay);

        //Call show() to simply show the dialog
        datePickerDialog.show();

    }

    @Override
    public void onBackPressed() {
        setResult(RESULT_CANCELED);
        super.onBackPressed();
    }







}





