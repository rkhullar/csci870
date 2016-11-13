package me.nydev.wifituner;

import android.Manifest;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.List;

import me.nydev.wifituner.model.Scan;
import me.nydev.wifituner.support.BaseActivity;
import me.nydev.wifituner.support.PermissionManager;
import me.nydev.wifituner.support.Toaster;

public class WifiTestActivity extends BaseActivity
{
    private WifiManager wm;
    private WifiScanReceiver wsr;
    private ListView lv;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState, R.layout.activity_test_wifi);
        wm = (WifiManager) getSystemService(WIFI_SERVICE);
        lv = (ListView) findViewById(R.id.test_wifi_list);
        wsr = new WifiScanReceiver();
    }

    protected void onPause()
    {
        super.onPause();
        unregisterReceiver(wsr);
    }

    protected void onResume()
    {
        super.onResume();
        registerReceiver(wsr, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case Constants.REQUEST.LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    toaster.toast("ok");
                    wm.startScan();
                }
                else
                    toaster.toast("not ok");
        }
    }

    public void test_wifi_scan(View view)
    {
        vibrator.vibrate(200);
        wm.setWifiEnabled(true);
        wm.disconnect();
        Scan.disableFilter();
        toaster.toast("scanning");
        PermissionManager.check(this, Manifest.permission.ACCESS_COARSE_LOCATION, Constants.REQUEST.LOCATION);
    }

    private class WifiScanReceiver extends BroadcastReceiver
    {
        public void onReceive(Context context, Intent intent)
        {
            List<ScanResult> wl = wm.getScanResults();
            Scan[] scans = Scan.parseScanResults(wl, null);
            Toaster toaster = new Toaster(context);
            toaster.toast("updating " + scans.length);
            lv.setAdapter(new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, scans));
        }

    }
}
