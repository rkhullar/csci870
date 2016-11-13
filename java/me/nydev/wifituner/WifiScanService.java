package me.nydev.wifituner;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.LocalBroadcastManager;

public class WifiScanService extends Service
{
    private long duration;
    private CountDownTimer cdt;
    private Notification notification;
    private LocalBroadcastManager lbm;

    public WifiScanService()
    {
        lbm = LocalBroadcastManager.getInstance(this);
    }

    public void onDestroy()
    {
        cdt.cancel();
        stopForeground(true);
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int flags, int startId)
    {
        duration = intent.getIntExtra(Constants.DATA.DURATION, 0) * 1000;
        initCountdownTimer();
        cdt.start();
        initNotification();
        startForeground(Constants.NOTIFICATION_ID.WIFI_SCAN_SERVICE, notification);
        return START_NOT_STICKY;
    }

    public IBinder onBind(Intent intent)
    {
        return null;
    }


    private void initCountdownTimer()
    {
        super.onCreate();
        cdt = new CountDownTimer(duration, Constants.VAR.INTERVAL*1000)
        {
            public void onTick(long n)
            {
                broadcastTimeLeft((int) n / 1000);
            }
            public void onFinish()
            {
                broadcastDone();
                onDestroy();
            }
        };
    }

    private void broadcastTimeLeft(int seconds)
    {
        Intent intent = new Intent(Constants.ACTION.MAIN);
        intent.putExtra(Constants.DATA.TIMELEFT, seconds);
        lbm.sendBroadcast(intent);
    }

    private void broadcastDone()
    {
        Intent intent = new Intent(Constants.ACTION.DONE);
        lbm.sendBroadcast(intent);
    }

    private void initNotification()
    {
        Intent i = new Intent(this, HomeActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        PendingIntent p = PendingIntent.getActivity(this, 0, i, 0);
        notification = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("service started")
                .setTicker("wifi scanning")
                .setContentIntent(p)
                .build();
    }

}
