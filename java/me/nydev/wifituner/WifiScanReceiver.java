package me.nydev.wifituner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.util.Log;

import java.util.List;

import me.nydev.wifituner.model.Location;
import me.nydev.wifituner.model.Scan;
import me.nydev.wifituner.support.DatabaseAdapter;

public class WifiScanReceiver extends BroadcastReceiver
{
    private static final String TAG = "WifiScanReceiver";

    public static WifiManager WifiManager;
    public static Location Location;
    private static boolean ENABLED = true;

    public void onReceive(Context context, Intent intent)
    {
        Log.i(TAG, "broadcast received");
        if(WifiManager == null || Location == null || !ENABLED) return;
        List<ScanResult> wl = WifiManager.getScanResults();
        long uxt = System.currentTimeMillis() / 1000;
        Scan[] scans = Scan.parseScanResults(wl, Location, uxt);
        new DatabaseAdapter(context).addScans(scans);
    }

    public static void enable()
    {
        ENABLED = true;
    }

    public static void disable()
    {
        ENABLED = false;
    }
}