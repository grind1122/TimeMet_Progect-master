package com.example;

import android.app.IntentService;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;


public class NotificationService extends IntentService {


    public NotificationService() {
        super("NotificationService");
    }


    @Override
    protected void onHandleIntent(@Nullable Intent intent) {
        synchronized (this) {
            try {
                wait(10000);
                showNotification("Лучшие цены, спеши!", "Хочешь быть в курсе актуальных цен на металлолом? Заходи и проверь! Лучшие цены у нас!");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    private void showNotification(String title, String text){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                                                .setSmallIcon(R.drawable.ic_chat_orange_24dp)
                                                .setContentTitle(title)
                                                .setContentText(text)
                                                .setPriority(NotificationCompat.PRIORITY_MAX)
                                                .setVibrate(new long[]{0, 1000})
                                                .setAutoCancel(true)
                                                .setVisibility(NotificationCompat.VISIBILITY_PUBLIC);
        Intent actionIntent = new Intent(this, MainActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(this,0,actionIntent,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(777, builder.build());
    }
}
