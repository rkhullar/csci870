package me.nydev.wifituner;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;

import me.nydev.wifituner.support.API;
import me.nydev.wifituner.support.Toaster;

public class APITestActivity extends Activity
{
    protected Context  context;
    protected Toaster  toaster;
    protected Vibrator vibrator;

    protected API api;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_api);
        context = getApplicationContext();
        toaster = new Toaster(context);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        api = new API();
    }

    public void test_api_default(View view)
    {
        toaster.toast("calling api");
        try {
            String m = api.test();
            toaster.toast(m);
        } catch (Exception e) {
            //Log.d(MY_APP_TAG, "Error: " + e.getMessage());
            toaster.toast(e.getMessage());
        }
    }
}
