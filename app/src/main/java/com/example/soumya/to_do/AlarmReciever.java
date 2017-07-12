package com.example.soumya.to_do;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

public class AlarmReciever extends BroadcastReceiver {
    public AlarmReciever() {
    }
     static int i;
    @Override
    public void onReceive(Context context, Intent intent) {
        // TODO: This method is called when the BroadcastReceiver is receiving
        // an Intent broadcast.
        //throw new UnsupportedOperationException("Not yet implemented");

            // Toast.makeText(context,"Alarm!!",Toast.LENGTH_SHORT).show();
            NotificationCompat.Builder mbuilder =new NotificationCompat.Builder(context)
                    .setSmallIcon(android.R.drawable.ic_menu_report_image)
                    .setContentTitle("Notification")
                    .setAutoCancel(true)
                    .setContentText("TodoAlarm");
            Intent resultIntent = new Intent(context,MainActivity.class);
            //resultIntent.putExtra("id",i);
            PendingIntent resultPendingIntent=PendingIntent.getActivity(context,i++,resultIntent,PendingIntent.FLAG_UPDATE_CURRENT);
            mbuilder.setContentIntent(resultPendingIntent);
            NotificationManager notificationManager= (NotificationManager)context.getSystemService(context.NOTIFICATION_SERVICE);

            notificationManager.notify(i++,mbuilder.build());
    }
}
