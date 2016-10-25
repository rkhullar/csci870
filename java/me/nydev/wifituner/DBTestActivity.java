package me.nydev.wifituner;

import android.os.Bundle;
import android.view.View;

import me.nydev.wifituner.support.BaseActivity;
import me.nydev.wifituner.support.DatabaseAdapter;

public class DBTestActivity extends BaseActivity
{
    protected DatabaseAdapter dba;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState, R.layout.activity_db_api);
        dba = new DatabaseAdapter(this);
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
        dba.login();
    }

    public void test_db_logout(View view)
    {
        dba.logout();
    }
}
