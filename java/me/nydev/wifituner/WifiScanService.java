package me.nydev.wifituner;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.WifiManager;
import android.os.CountDownTimer;
import android.os.IBinder;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import me.nydev.wifituner.model.Location;
import me.nydev.wifituner.model.Scan;
import me.nydev.wifituner.support.Support;

public class WifiScanService extends Service
{
    private static final String TAG = "WifiScanService";

    private Context context;
    private long duration;
    private CountDownTimer cdt;
    private Notification notification;
    private LocalBroadcastManager lbm;
    private NotificationManager nm;
    private WifiManager wm;
    private WifiScanReceiver wsr;

    public void onCreate()
    {
        super.onCreate();
        context = this;
        lbm = LocalBroadcastManager.getInstance(this);
        nm = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        wm = (WifiManager) getSystemService(WIFI_SERVICE);
        WifiScanReceiver.WifiManager = wm;
        Scan.enableFilter();
        WifiScanReceiver.enable();
    }

    public void onDestroy()
    {
        cdt.cancel();
        stopForeground(true);
        lbm.unregisterReceiver(wsr);
        WifiScanReceiver.WifiManager = null;
        WifiScanReceiver.Location = null;
        stopSelf();
        super.onDestroy();
    }

    public int onStartCommand(Intent intent, int flags, int startId)
    {
        duration = intent.getIntExtra(Constants.DATA.DURATION, -1) * 1000;
        wsr = new WifiScanReceiver();
        WifiScanReceiver.Location = new Location(intent);
        lbm.registerReceiver(wsr, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
        initCountdownTimer();
        cdt.start();
        notification = Support.notification("countdown started", "wifi scanning", this, HomeActivity.class);
        startForeground(Constants.NOTIFICATION_ID.COUNTDOWN_BEGIN, notification);
        return START_NOT_STICKY;
    }

    public IBinder onBind(Intent intent)
    {
        return null;
    }


    private void initCountdownTimer()
    {
        super.onCreate();
        cdt = new CountDownTimer(duration, 1000)
        {
            public void onTick(long n)
            {
                int seconds = (int) n / 1000;
                Intent intent = new Intent(Constants.ACTION.MAIN);
                intent.putExtra(Constants.DATA.TIMELEFT, seconds);
                lbm.sendBroadcast(intent);
                if(seconds % Constants.VAR.INTERVAL == 0)
                {
                    Log.i(TAG, "tick");
                    wm.setWifiEnabled(true);
                    wm.disconnect();
                    wm.startScan();
                }
            }
            public void onFinish()
            {
                Log.i(TAG, "countdown complete");
                Intent intent = new Intent(Constants.ACTION.DONE);
                lbm.sendBroadcast(intent);
                notification = Support.notification("countdown complete", "wifi results stored", context, HomeActivity.class);
                nm.notify(Constants.NOTIFICATION_ID.COUNTDOWN_END, notification);
                onDestroy();
            }
        };
    }
}
