package me.nydev.wifituner;

import android.app.Service;
import android.content.Intent;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;

public class WifiScanService extends Service
{
    private LocalBroadcastManager lbm;
    private CountDownTimer cdt;
    private long duration;

    public WifiScanService()
    {
        lbm = LocalBroadcastManager.getInstance(this);
    }

    public void onDestroy()
    {
        cdt.cancel();
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int flags, int startId)
    {
        /*
        new Thread(new Runnable()
        {
            public void run()
            {
            }
        }).start();
        */
        duration = intent.getIntExtra(Constants.DURATION, 0) * 1000;
        initCountdownTimer();
        cdt.start();
        return super.onStartCommand(intent, flags, startId);
    }

    public IBinder onBind(Intent intent)
    {
        return null;
    }


    public void initCountdownTimer()
    {
        super.onCreate();
        cdt = new CountDownTimer(duration, Constants.INTERVAL*1000)
        {
            public void onTick(long n)
            {
                broadcastTimeLeft((int) n / 1000);
            }
            public void onFinish() {}
        };
    }

    public void broadcastTimeLeft(int seconds)
    {
        Intent intent = new Intent(Constants.BROADCAST_ACTION);
        intent.putExtra(Constants.TIMELEFT, seconds);
        lbm.sendBroadcast(intent);
    }
}
