package me.nydev.wifituner;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import me.nydev.wifituner.model.Auth;
import me.nydev.wifituner.model.Scan;
import me.nydev.wifituner.support.BaseActivity;

public class ScanPushActivity extends BaseActivity
{
    private static final String TAG = "ScanPushActivity";

    private boolean fetchTime = false;
    private long localTime, serverTime, deltaTime;
    private ListView lv;
    private Scan[] scans;
    private Auth auth;
    private int count;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState, R.layout.activity_scan_push);
        fetchTime();
        scans = dba.scans();
        auth = dba.getAuth();
        lv = (ListView) findViewById(R.id.scan_push_list);
        updateView();
    }

    private void updateView()
    {
        lv.setAdapter(new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, dba.scans()));
    }

    public void push_scans(View view)
    {
        if(!fetchTime)
        {
            fetchTime();
            toaster.toast("try again");
        }
        vibrator.vibrate(200);
        count = 0;
        int l = 0, h, n = scans.length, b = Constants.VAR.BUFFER;
        if(n == 0)
            toaster.toast("no scan results available");
        else
            toaster.toast("pushing "+n+"results");
        while(n >= b)
        {
            h = l + b - 1;
            pushScans(scans, l, h);
            n -= b;
            l += b;
        }
        if(n > 0)
        {
            h = l + n - 1;
            pushScans(scans, l, h);
        }
    }

    private void fetchTime()
    {
        api.fetchTime(new JsonHttpResponseHandler()
        {
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse)
            {
                fetchTime = false;
                toaster.toast("time sync failed");
            }
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                fetchTime = true;
                localTime = System.currentTimeMillis() / 1000;
                serverTime = api.extractTime(response);
                deltaTime = serverTime - localTime;
            }
        });
    }

    private void pushScan(Scan scan)
    {
        scan.addUnixTime(deltaTime);
        api.pushScan(auth, scan, new JsonHttpResponseHandler()
        {
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse)
            {
                count++;
            }
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                Scan x = Scan.parseObject(response);
                x.delUnixTime(deltaTime);
                dba.delScan(x);
                count++;
                Log.i(TAG, String.format("progress = %d/%d", count, scans.length));
            }
        });
    }

    private void pushScans(Scan[] scanz, int low, int high)
    {
        for(int x = low; x <= high; x++)
            scanz[x].addUnixTime(deltaTime);
        api.pushScans(auth, scanz, low, high, new JsonHttpResponseHandler()
        {
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse)
            {
            }
            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                Scan[] a = Scan.parseArray(response);
                for(int x = 0; x < a.length; x++)
                    a[x].delUnixTime(deltaTime);
                dba.delScans(a);
                count += a.length;
                Log.i(TAG, String.format("progress = %d/%d", count, scans.length));
                updateView();
            }
        });
    }

}