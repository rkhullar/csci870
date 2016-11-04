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
import java.util.Locale;

import me.nydev.wifituner.model.Scan;
import me.nydev.wifituner.model.ScanBuilder;
import me.nydev.wifituner.support.BaseActivity;
import me.nydev.wifituner.support.PermissionManager;
import me.nydev.wifituner.support.RestClientAdapter;

public class WiFiTestActivity extends BaseActivity
{
    protected WifiManager wifimngr;
    protected WifiScanReceiver wifircvr;
    protected ListView lv;
    protected String[] a;

    protected static final int ACCESS_COARSE_LOCATION = 1;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState, R.layout.activity_test_wifi);
        wifimngr = (WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifircvr = new WifiScanReceiver();
        api = new RestClientAdapter();
        lv = (ListView) findViewById(R.id.test_wifi_list);
    }

    protected void onPause()
    {
        super.onPause();
        unregisterReceiver(wifircvr);
    }

    protected void onResume()
    {
        super.onResume();
        registerReceiver(wifircvr, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case ACCESS_COARSE_LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                {
                    toaster.toast("ok");
                    wifimngr.startScan();
                }
                else
                    toaster.toast("not ok");
        }
    }

    public void test_wifi_scan(View view)
    {
        vibrator.vibrate(200);
        wifimngr.setWifiEnabled(true);
        wifimngr.disconnect();
        PermissionManager.check(this, Manifest.permission.ACCESS_COARSE_LOCATION, ACCESS_COARSE_LOCATION);
    }

    private class WifiScanReceiver extends BroadcastReceiver
    {
        public void onReceive(Context context, Intent intent)
        {
            List<ScanResult> wifilist = wifimngr.getScanResults();
            int n = wifilist.size(), c = 0;

            for(int x = 0; x < n; x++)
                if(wifilist.get(x).SSID.equals("NYIT") || true)
                    c++;
            a = new String[c]; c = 0;
            for(int x = 0; x < n; x++)
            {
                ScanResult sr = wifilist.get(x);
                if(sr.SSID.equals("NYIT") || true)
                {
                    String s = String.format(Locale.US, "%s => %d", sr.BSSID, sr.level);
                    a[c++] = s;
                }
            }
            toaster.toast("updating");
            lv.setAdapter(new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, a));
            /*
            for(int x=0; x<n; x++)
            {
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
            }*/
        }
    }
}
