package me.nydev.wifituner;

import android.Manifest;
import android.content.Context;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import me.nydev.wifituner.model.Scan;
import me.nydev.wifituner.support.BaseActivity;
import me.nydev.wifituner.support.PermissionManager;
import me.nydev.wifituner.support.WifiScanTestReceiver;

public class WiFiTestActivity extends BaseActivity
{
    protected WifiManager wm;
    protected WifiScanTestReceiver wsr;
    protected ListView lv;
    protected String[] a;

    protected static final int ACCESS_COARSE_LOCATION = 1;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState, R.layout.activity_test_wifi);
        wm = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        lv = (ListView) findViewById(R.id.test_wifi_list);
        wsr = new WifiScanTestReceiver(wm, lv);
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
            case ACCESS_COARSE_LOCATION:
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
        PermissionManager.check(this, Manifest.permission.ACCESS_COARSE_LOCATION, ACCESS_COARSE_LOCATION);
    }
}
