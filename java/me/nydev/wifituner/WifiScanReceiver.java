package me.nydev.wifituner;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;

import java.util.List;

import me.nydev.wifituner.model.Location;
import me.nydev.wifituner.model.Scan;
import me.nydev.wifituner.support.DatabaseAdapter;


public class WifiScanReceiver extends BroadcastReceiver
{
    public static WifiManager WifiManager;
    public static Location Location;

    public void onReceive(Context context, Intent intent)
    {
        if(WifiManager == null || Location == null) return;
        List<ScanResult> wl = WifiManager.getScanResults();
        Scan[] scans = Scan.parseScanResults(wl, Location);
        DatabaseAdapter dba = new DatabaseAdapter(context);
        for(Scan scan: scans) {
            dba.addScan(scan);
        }
    }

    private void test()
    {
        if(WifiManager == null)
            System.out.println("wtf");
        System.out.println(Location);
    }
}