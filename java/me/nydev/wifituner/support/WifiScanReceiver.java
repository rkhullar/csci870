package me.nydev.wifituner.support;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import me.nydev.wifituner.R;
import me.nydev.wifituner.model.Scan;

public class WifiScanReceiver extends BroadcastReceiver
{
    private WifiManager wm;

    public WifiScanReceiver(WifiManager wm)
    {
        super();
        this.wm = wm;
    }

    public void onReceive(Context context, Intent intent)
    {
        List<ScanResult> wl = wm.getScanResults();
        Scan[] scans = Scan.parseScanResults(wl, null);
    }

}

/*
for(int x=0; x<n; x++)
    ScanResult sr = wifiScanList.get(x);
    if(sr.SSID.equals("NYIT"))
    {
        Scan s = new ScanBuilder()
                .setScanResult(sr)
                .setBuilding("ANY")
                .setFloor(0)
                .setRoom("ANY")
                .build();
        api.setup(context);
        api.persist_scan(null, s);
    }
*/