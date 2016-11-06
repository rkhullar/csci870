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
    private ListView lv;

    public void setup(WifiManager wm, ListView lv)
    {
        this.wm = wm;
        this.lv = lv;
    }

    public void onReceive(Context context, Intent intent)
    {
        List<ScanResult> wl = wm.getScanResults();
        Scan[] scans = Scan.parseScanResults(wl, null);
        Toaster toaster = new Toaster(context);
        toaster.toast("updating " + scans.length);
        lv.setAdapter(new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, scans));
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