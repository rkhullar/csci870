package me.nydev.wifituner;

import android.os.Bundle;
import android.view.View;

import me.nydev.wifituner.model.Auth;
import me.nydev.wifituner.support.BaseActivity;

public class APITestActivity extends BaseActivity
{

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState, R.layout.activity_test_api);
        api.setup(context);
    }

    public void test_api_default(View view)
    {
        api.echo();
    }

    public void test_api_auth(View view)
    {
        Auth auth = dba.getAuth();
        toaster.toast(auth.getUsername());
        toaster.toast(auth.getSecret());
    }

}
