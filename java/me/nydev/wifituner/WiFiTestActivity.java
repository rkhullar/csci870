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

import me.nydev.wifituner.support.Toaster;

public class WiFiTestActivity extends Activity
{
    protected Context  context;
    protected Toaster  toaster;
    protected Vibrator vibrator;

    protected WifiManager wifi;
    protected WifiScanReceiver wifi_rcvr;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_wifi);
        context = getApplicationContext();
        toaster = new Toaster(context);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        wifi=(WifiManager) getSystemService(Context.WIFI_SERVICE);
        wifi_rcvr = new WifiScanReceiver();
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
                if(sr.SSID.equals("NYIT"))
                {
                    //String s = api.persist_scan(sr.BSSID, sr.level, "ANY", 0, "ANY");
                    String s = String.format("%s => %d", sr.BSSID, sr.level);
                    toaster.toast(s);
                }
            }
            System.out.println("stop");
        }
    }
}
