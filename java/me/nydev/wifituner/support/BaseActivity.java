package me.nydev.wifituner.support;

import android.app.ActivityManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;

import me.nydev.wifituner.model.Auth;

public class BaseActivity extends AppCompatActivity
{
    protected Context  context;
    protected Toaster  toaster;
    protected Vibrator vibrator;
    protected Intent   intent;

    protected Auth auth;
    protected RestClientAdapter api;
    protected DatabaseAdapter dba;

    protected LocalBroadcastManager lbm;
    protected ActivityManager am;

    protected void onCreate(Bundle savedInstanceState, int layoutResID)
    {
        super.onCreate(savedInstanceState);
        setContentView(layoutResID);
        context = getApplicationContext();
        toaster = new Toaster(context);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        api = new RestClientAdapter();
        dba = new DatabaseAdapter(this);
        lbm = LocalBroadcastManager.getInstance(this);
        am = (ActivityManager) getSystemService(Context.ACTIVITY_SERVICE);
    }

    protected void handleIntent(Class<?> cls)
    {
        vibrator.vibrate(200);
        intent = new Intent(this, cls);
        startActivity(intent);
    }

    protected void handleNewIntent(Class<?> cls)
    {
        vibrator.vibrate(200);
        intent = new Intent(this, cls);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    protected boolean isServiceRunning(Class<?> x)
    {
        for(ActivityManager.RunningServiceInfo rsi: am.getRunningServices(Integer.MAX_VALUE))
            if(rsi.service.getClassName().equals(x.getName()))
                return true;
        return false;
    }

}
