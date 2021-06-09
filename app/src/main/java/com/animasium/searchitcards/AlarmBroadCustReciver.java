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
        int id = intent.getIntExtra(String.valueOf(NOTIFICATION_ID), 0 );
        Notification notification = intent.getParcelableExtra( NOTIFICATION );
        Log.i("idRecived",String.valueOf(id));
//        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O ) {
//            int importance = NotificationManager. IMPORTANCE_HIGH;
//            NotificationChannel notificationChannel = new NotificationChannel( String.valueOf(id)  , "NOTIFICATION_CHANNEL_NAME"  , importance);
//            assert notificationManager1 != null;
//            notificationManager1.createNotificationChannel(notificationChannel);
//        }
//
      // String ShowID = intent.getStringExtra(SHOW_ID);
//        Log.i("GetExtra",ShowID+"");
//        assert notificationManager1 != null;
//        notificationManager1.notify(id , notification);
        notificationManager.notify(id, notification);
        Log.i("idRecived","Notification Called");


}
    }

