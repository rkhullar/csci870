package me.nydev.wifituner;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.wifi.ScanResult;
import android.net.wifi.WifiManager;
import android.os.Bundle;
import android.view.View;

import java.util.List;

public class MainActivity extends Activity
{
    protected Context context;
    //protected Intent intent;
    protected Toaster toaster;
    //protected Vibrator vibrator;
    protected WifiManager wifi;
    WifiScanReceiver wifi_rcvr;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        toaster = new Toaster(context);
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

    public void work_hello(View view)
    {
        wifi.startScan();
        //toaster.toast("hello world");
    }

    private class WifiScanReceiver extends BroadcastReceiver
    {
        public void onReceive(Context c, Intent intent)
        {
            /*
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
                    System.out.printf("%s => %d\n", sr.BSSID, sr.level);
                if(x == n-1)
                    toaster.toast(sr.BSSID + " => " +sr.level);
            }
            System.out.println("stop");
        }
    }
}
