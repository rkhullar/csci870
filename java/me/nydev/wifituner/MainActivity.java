package me.nydev.wifituner;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;

import me.nydev.wifituner.support.Toaster;

public class MainActivity extends Activity
{
    protected Context  context;
    protected Intent   intent;
    protected Toaster  toaster;
    protected Vibrator vibrator;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = getApplicationContext();
        toaster = new Toaster(context);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void main_test_wifi(View view)
    {
        vibrator.vibrate(200);
        intent = new Intent(this, me.nydev.wifituner.WiFiTestActivity.class);
        startActivity(intent);
    }

    public void main_test_api(View view)
    {
        vibrator.vibrate(200);
        intent = new Intent(this, me.nydev.wifituner.APITestActivity.class);
        startActivity(intent);
    }

}
