package me.nydev.wifituner.support;

import android.app.Notification;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;

import me.nydev.wifituner.R;

public class Support
{
    public static void toast(Object o, Context c)
    {
        new Toaster(c).toast(o);
    }

    public static Notification notification(Object title, Object ticker, Context c, Class<?> k)
    {
        Intent i = new Intent(c, k);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent p = PendingIntent.getActivity(c, 0, i, 0);
        return new NotificationCompat.Builder(c)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(title.toString())
                .setTicker(ticker.toString())
                .setContentIntent(p)
                .build();
    }
}
