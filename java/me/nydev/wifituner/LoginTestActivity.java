package me.nydev.wifituner;

import android.os.Bundle;
import android.view.View;

import me.nydev.wifituner.support.BaseActivity;

public class LoginTestActivity extends BaseActivity
{
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState, R.layout.activity_test_login);
    }

    public void test_login_default(View view)
    {
        vibrator.vibrate(200);
        toaster.toast("Hello World");
    }
}
