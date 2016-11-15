package me.nydev.wifituner;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import me.nydev.wifituner.model.Scan;
import me.nydev.wifituner.support.BaseActivity;

public class ScanPushActivity extends BaseActivity
{
    private static final String TAG = "ScanPushActivity";

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState, R.layout.activity_scan_push);
        for(Scan s: dba.scans()) Log.i(TAG, s.toString());
    }

    public void push_scans(View view)
    {
        toaster.toast("pushing scans");
    }
}