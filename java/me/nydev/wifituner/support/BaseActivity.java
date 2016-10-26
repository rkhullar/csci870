package me.nydev.wifituner.support;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;

import me.nydev.wifituner.model.Auth;

public class BaseActivity extends Activity
{
    protected Context  context;
    protected Toaster  toaster;
    protected Vibrator vibrator;
    protected Intent   intent;

    protected Auth auth;
    protected RestClientAdapter api;
    protected DatabaseAdapter dba;

    protected void onCreate(Bundle savedInstanceState, int layoutResID)
    {
        super.onCreate(savedInstanceState);
        setContentView(layoutResID);
        context = getApplicationContext();
        toaster = new Toaster(context);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        api = new RestClientAdapter();
        dba = new DatabaseAdapter(this);
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

}
