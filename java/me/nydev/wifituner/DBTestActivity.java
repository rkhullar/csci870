package me.nydev.wifituner;

import android.os.Bundle;
import android.view.View;

import me.nydev.wifituner.model.Auth;
import me.nydev.wifituner.model.Location;
import me.nydev.wifituner.support.BaseActivity;

public class DBTestActivity extends BaseActivity
{

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState, R.layout.activity_test_db);
    }

    public void test_db_default(View view)
    {
        String[] a = dba.test();
        if(a.length < 1)
            toaster.toast("empty set");
        for(String s: a)
            toaster.toast(s);
    }

    public void test_db_login(View view)
    {
        auth = new Auth("test@nyit.edu", "aaaaaa");
        dba.login(auth);
    }

    public void test_db_logout(View view)
    {
        dba.logout();
    }

    public void test_db_location(View view)
    {
        Location[] a = dba.locations();
        //if(a.length > 0)
            //toaster.toast(a[0]);
        toaster.toast(a.length);
        for(Location l: a)
            toaster.toast(l);
    }
}
