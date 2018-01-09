package com.dkrichards.studentapplication.receivers;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import com.dkrichards.studentapplication.R;
import com.dkrichards.studentapplication.ui.activity.MainActivity;

/**
 * Alarm Receiver
 */
public class AlarmReceiver extends BroadcastReceiver {
    private String CHANNEL_ID = "app_notification_channel";
    private String CHANNEL_DESC = "Student Application Notification Channel";

    @Override
    public void onReceive(Context context, Intent intent) {
        // Get the notification data stored in the intent
        String mNotificationTitle = intent.getStringExtra("mNotificationTitle");
        String mNotificationContent = intent.getStringExtra("mNotificationContent");

        Intent resultIntent = new Intent(context, MainActivity.class);

        // Build an artificial "TaskStack" so we can retain up functionality in the app bar
        TaskStackBuilder stackBuilder = TaskStackBuilder.create(context);
        stackBuilder.addParentStack(MainActivity.class);
        stackBuilder.addNextIntent(resultIntent);

        PendingIntent resultPendingIntent =
                stackBuilder.getPendingIntent(
                        1,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        // Build the notification
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context, CHANNEL_ID);

        mBuilder.setSmallIcon(R.mipmap.ic_notification);
        mBuilder.setContentTitle(mNotificationTitle);
        mBuilder.setContentText(mNotificationContent);
        mBuilder.setContentIntent(resultPendingIntent);

        NotificationManager mNotificationManager =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        // Build a channel for notifications
        NotificationChannel channel = new NotificationChannel(CHANNEL_ID, CHANNEL_DESC, NotificationManager.IMPORTANCE_DEFAULT);
        mNotificationManager.createNotificationChannel(channel);

        // Trigger the notification
        mNotificationManager.notify(0, mBuilder.build());
    }

}