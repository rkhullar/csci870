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

public class WifiScanTestReceiver extends BroadcastReceiver
{
    private WifiManager wm;
    private ListView lv;

    public WifiScanTestReceiver(WifiManager wm, ListView lv)
    {
        super();
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