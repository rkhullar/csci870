package me.nydev.wifituner;

import android.app.FragmentTransaction;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.app.Fragment;
import android.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import me.nydev.wifituner.support.BaseActivity;

public class HomeActivity extends BaseActivity
{
    private static final int[] timeViewIDs = {R.id.home_time_hour, R.id.home_time_minute, R.id.home_time_second};

    private boolean mode;
    private TimeReceiver tr;
    private TextView[] timeViews;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState, R.layout.activity_home);
        updateStatus();
        updateFragment();
        tr = new TimeReceiver();
    }

    protected void onPause()
    {
        super.onPause();
        lbm.unregisterReceiver(tr);
    }

    protected void onResume()
    {
        super.onResume();
        timeViews = new TextView[timeViewIDs.length];
        for(int x = 0; x < timeViewIDs.length; x++)
            timeViews[x] = (TextView) findViewById(timeViewIDs[x]);
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction(Constants.ACTION.MAIN);
        intentFilter.addAction(Constants.ACTION.DONE);
        lbm.registerReceiver(tr, intentFilter);
    }

    private void updateStatus()
    {
        mode = isServiceRunning(WifiScanService.class);
    }

    private void updateFragment()
    {
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment;
        if(mode)
            fragment = new HomeBusyFragment();
        else
            fragment = new HomeIdleFragment();
        fragmentTransaction.replace(R.id.fragment_container, fragment).commit();
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
                handleIntent(WifiTestActivity.class);
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

    public void home_logout(View view)
    {
        dba.logout();
        handleNewIntent(LoginActivity.class);
    }

    public void home_scan_conf(View view)
    {
        handleIntent(ScanConfActivity.class);
    }

    public void home_scan_push(View view)
    {
        handleIntent(ScanPushActivity.class);
    }

    public void home_scan_stop(View view)
    {
        toaster.toast("stopping wifi scan service");
    }

    public static class HomeIdleFragment extends Fragment
    {
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            return inflater.inflate(R.layout.fragment_home_idle, container, false);
        }
    }

    public static class HomeBusyFragment extends Fragment
    {
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
        {
            return inflater.inflate(R.layout.fragment_home_busy, container, false);
        }
    }

    private class TimeReceiver extends BroadcastReceiver
    {
        public void onReceive(Context context, Intent intent)
        {
            switch (intent.getAction())
            {
                case Constants.ACTION.MAIN:
                    int x = intent.getIntExtra(Constants.DATA.TIMELEFT, 0);
                    int h = x / 3600; x = x % 3600;
                    int m = x / 60; int s = x % 60;
                    timeViews[0].setText(h+"");
                    timeViews[1].setText(m+"");
                    timeViews[2].setText(s+"");
                    break;
                case Constants.ACTION.DONE:
                    toaster.toast("complete");
                    break;
            }
        }
    }
}