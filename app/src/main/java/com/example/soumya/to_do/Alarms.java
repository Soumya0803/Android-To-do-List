package com.example.soumya.to_do;

import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import java.util.Calendar;
import java.util.GregorianCalendar;

import static com.example.soumya.to_do.R.id.dateEditText;

public class Alarms extends AppCompatActivity {
    EditText alarmDate;
    EditText alarmTime;
    long date2;
    int year,month,hour,minute,day;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarms);
        Button alarmsetbutton=(Button)findViewById(R.id.alarmsetbutton);
         Intent it=getIntent();


                // Steps for Date picker
                // Will show Date picker dialog on clicking edit text
                alarmDate = (EditText) findViewById(R.id.alarmdate);

                alarmDate.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Calendar newCalendar = Calendar.getInstance();
                        month = newCalendar.get(Calendar.MONTH);  // Current month
                        year = newCalendar.get(Calendar.YEAR);   // Current year
                        day= newCalendar.get(Calendar.DAY_OF_MONTH);

                        showDatePicker(Alarms.this, year, month, 1);
                    }
                });

           //Timepicker
            alarmTime=(EditText)findViewById(R.id.alarmtime) ;
              alarmTime.setOnClickListener(new View.OnClickListener() {
                  @Override
                  public void onClick(View view) {
                      // TODO Auto-generated method stub
                      Calendar mcurrentTime = Calendar.getInstance();
                       hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
                       minute = mcurrentTime.get(Calendar.MINUTE);
                      TimePickerDialog mTimePicker;
                      mTimePicker = new TimePickerDialog(Alarms.this, new TimePickerDialog.OnTimeSetListener() {
                          @Override
                          public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                              alarmTime.setText( selectedHour + ":" + selectedMinute);
                          }
                      }, hour, minute, true);//Yes 24 hour time
                      mTimePicker.setTitle("Select Time");
                      mTimePicker.show();




                  }
              });

        alarmsetbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent it=new Intent();
                setResult(RESULT_OK, it);
                finish();



                AlarmManager am=(AlarmManager)Alarms.this.getSystemService(Context.ALARM_SERVICE);
                Intent i= new Intent(Alarms.this,AlarmReciever.class);
                PendingIntent pendingIntent= PendingIntent.getBroadcast(Alarms.this,1,i,0);
                //am.set(AlarmManager.RTC,System.currentTimeMillis() + 2000,pendingIntent);

//                GregorianCalendar cal=new GregorianCalendar(year,month,day,hour,minute);
                Calendar cal= Calendar.getInstance();
                cal.set(year,month,day,hour,minute);
                long alarmtime=cal.getTimeInMillis();

                am.set(AlarmManager.RTC_WAKEUP,alarmtime,pendingIntent);

            }
        });
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
                        alarmDate.setText(day + "/" + (month + 1) + "/" + year);
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

   /* public void showTimePicker(Context context,int inihour,int iniminute)
    {
        TimePickerDialog timePickerDialog = new TimePickerDialog.OnTimeSetListener(context,new TimePickerDialog.OnTimeSetListener(){

            @Override
            public void onTimeSet(TimePicker timePicker, int i, int i1) {
                Calendar cal=Calendar.getInstance();
                cal.set(i,i1);



        },inihour,iniminute,true);
        timePickerDialog.show();


    }*/
}
