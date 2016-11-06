package me.nydev.wifituner;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;

import me.nydev.wifituner.support.BaseActivity;

public class HomeActivity extends BaseActivity
{
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState, R.layout.activity_home);
    }

    public void home_logout(View view)
    {
        dba.logout();
        handleNewIntent(LoginActivity.class);
    }

    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.tests, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.menu_test_wifi:
                handleIntent(WiFiTestActivity.class);
                return true;
            case R.id.menu_test_api:
                handleIntent(APITestActivity.class);
                return true;
            case R.id.menu_test_db:
                handleIntent(DBTestActivity.class);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}