package me.nydev.wifituner.support;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;

public class BaseActivity extends Activity
{
    protected Context  context;
    protected Toaster  toaster;
    protected Vibrator vibrator;
    protected Intent   intent;

    protected RestClientAdapter api;

    protected void onCreate(Bundle savedInstanceState, int layoutResID)
    {
        super.onCreate(savedInstanceState);
        setContentView(layoutResID);
        context = getApplicationContext();
        toaster = new Toaster(context);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        api = new RestClientAdapter();
    }

    protected void handleIntent(Class<?> cls)
    {
        vibrator.vibrate(200);
        intent = new Intent(this, cls);
        startActivity(intent);
    }
}
