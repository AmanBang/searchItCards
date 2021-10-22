package com.animasium.searchitcards;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;
import androidx.core.app.NotificationManagerCompat;

import java.util.Random;

import static com.parse.Parse.getApplicationContext;

public class AlarmBroadCustReciver extends BroadcastReceiver {
    public static String NOTIFICATION_ID = "notification";
    public static String NOTIFICATION = "Notification";
    public static String SHOW_ID ="Show_ID";
    private NotificationManagerCompat notificationManager;

    @SuppressLint("ServiceCast")
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void onReceive(Context context, Intent intent) {
        notificationManager = NotificationManagerCompat.from(getApplicationContext());
      //  notificationManager = (NotificationManagerCompat) context.getSystemService(Context. NOTIFICATION_SERVICE );
//        int id = intent.getIntExtra(String.valueOf(NOTIFICATION_ID), 0 );
        Notification notification = intent.getParcelableExtra( NOTIFICATION );
//        Log.i("idRecived",String.valueOf(id));
        int dummyuniqueInt = new Random().nextInt(5430254);
//        notificationManager.notify(dummyuniqueInt, notification);
        Log.i("idRecived","Notification Called");


}
    }

