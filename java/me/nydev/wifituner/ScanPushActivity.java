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
        lv.setAdapter(new ArrayAdapter<>(context, R.layout.support_simple_spinner_dropdown_item, scans));
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
        for(Scan scan: scans)
            persistScan(scan);
    }

    private void fetchTime()
    {
        api.fetch_time(new JsonHttpResponseHandler()
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
                serverTime = api.extract_time(response);
                deltaTime = serverTime - localTime;
            }
        });
    }

    private void persistScan(Scan scan)
    {
        scan.addUnixTime(deltaTime);
        api.persist_scan(auth, scan, new JsonHttpResponseHandler()
        {
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse)
            {
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

}