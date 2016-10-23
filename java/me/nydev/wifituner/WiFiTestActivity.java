package me.nydev.wifituner;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;

import java.util.List;

import me.nydev.wifituner.model.Scan;
import me.nydev.wifituner.model.ScanBuilder;
import me.nydev.wifituner.support.BaseActivity;
import me.nydev.wifituner.support.RestClientUsage;
import me.nydev.wifituner.support.Toaster;

public class WiFiTestActivity extends BaseActivity
{
    protected WifiManager wifi;
    protected WifiScanReceiver wifi_rcvr;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState, R.layout.activity_test_wifi);
        wifi=(WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifi_rcvr = new WifiScanReceiver();
        api = new RestClientUsage();
    }

    protected void onPause()
    {
        super.onPause();
        unregisterReceiver(wifi_rcvr);
    }

    protected void onResume()
    {
        super.onResume();
        registerReceiver(wifi_rcvr, new IntentFilter(WifiManager.SCAN_RESULTS_AVAILABLE_ACTION));
    }

    public void test_wifi_scan(View view)
    {
        vibrator.vibrate(200);
        wifi.startScan();
    }

    private class WifiScanReceiver extends BroadcastReceiver
    {
        public void onReceive(Context c, Intent intent)
        {
            /** WiFi Scan Results into List View
            List<ScanResult> wifiScanList = wifi.getScanResults();
            wifis = new String[wifiScanList.size()];
            for(int i = 0; i < wifiScanList.size(); i++){
                wifis[i] = ((wifiScanList.get(i)).toString());
            }
            lv.setAdapter(new ArrayAdapter<String>(getApplicationContext(),android.R.layout.simple_list_item_1,wifis));
            */
            List<ScanResult> wifiScanList = wifi.getScanResults();
            int n = wifiScanList.size();
            System.out.println("start");
            for(int x=0; x<n; x++)
            {
                ScanResult sr = wifiScanList.get(x);
                if(sr.SSID.equals("NYIT") || true)
                {
                    //String s = api.persist_scan(sr.BSSID, sr.level, "ANY", 0, "ANY");
                    //String s = String.format("%s => %d", sr.BSSID, sr.level);
                    //toaster.toast(s);
                    ///*
                    Scan s = new ScanBuilder()
                            .setScanResult(sr)
                            .setBuilding("ANY")
                            .setFloor(0)
                            .setRoom("ANY")
                            .build();
                    api.setToaster(toaster);
                    api.persist_scan(null, s);
                    //*/
                }
            }
            System.out.println("stop");
        }
    }
}
