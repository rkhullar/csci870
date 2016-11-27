package me.nydev.wifituner;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.NumberPicker;
import android.widget.Spinner;

import java.util.ArrayList;

import me.nydev.wifituner.model.LocationBuilder;
import me.nydev.wifituner.support.BaseActivity;
import me.nydev.wifituner.support.PermissionManager;

public class ScanConfActivity extends BaseActivity implements AdapterView.OnItemSelectedListener
{
    private static final String TAG = "ScanConfActivity";
    private static final int[] SPINIDS = {R.id.scan_conf_building, R.id.scan_conf_floor, R.id.scan_conf_room};

    private Spinner[] spinners;
    private NumberPicker hourPicker, minutePicker;

    private LocationBuilder location;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState, R.layout.activity_scan_conf);
        location = new LocationBuilder();
        initSpinners();
        initNumberPickers();
    }

    private void initSpinners()
    {
        String[] buildings = dba.buildings();
        spinners = new Spinner[SPINIDS.length];
        for(int x = 0; x < spinners.length; x++)
        {
            spinners[x] = (Spinner) findViewById(SPINIDS[x]);
            spinners[x].setOnItemSelectedListener(this);
        }
        fillSpinner(0, buildings);
        showSpinner(0);
    }

    private void fillSpinner(int x, Object[] objects)
    {
        ArrayList<String> al = new ArrayList<>();
        al.add(Constants.VAR.PROMPT);
        for(Object o: objects)
            al.add(o.toString());
        ArrayAdapter<String> aa = new ArrayAdapter<>(context, Constants.VAR.SPINNER_ITEM, al);
        aa.setDropDownViewResource(Constants.VAR.SPINNER_DROPDOWN);
        spinners[x].setAdapter(aa);
    }

    private void hideSpinner(int x)
    {
        spinners[x].setVisibility(View.INVISIBLE);
    }

    private void showSpinner(int x)
    {
        spinners[x].setVisibility(View.VISIBLE);
    }

    private void initNumberPickers()
    {
        hourPicker = (NumberPicker) findViewById(R.id.scan_conf_hour);
        minutePicker = (NumberPicker) findViewById(R.id.scan_conf_minute);
        hourPicker.setMinValue(0); hourPicker.setMaxValue(3);
        minutePicker.setMinValue(0); minutePicker.setMaxValue(59);
        hourPicker.setWrapSelectorWheel(true);
        minutePicker.setWrapSelectorWheel(true);
    }

    private int duration()
    {
        return 60 * (hourPicker.getValue() * 60 + minutePicker.getValue());
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
    {
        if (pos == 0) return;
        Object x = parent.getSelectedItem();
        switch(parent.getId())
        {
            case R.id.scan_conf_building:
                location.setBuilding(x.toString());
                Integer[] floors = dba.floors(location.getBuilding());
                fillSpinner(1, floors);
                showSpinner(1);
                hideSpinner(2);
                break;
            case R.id.scan_conf_floor:
                location.setFloor((Integer.parseInt(x.toString())));
                String[] rooms = dba.rooms(location.getBuilding(), location.getFloor());
                fillSpinner(2, rooms);
                showSpinner(2);
                break;
            case R.id.scan_conf_room:
                location.setRoom(x.toString());
                break;
        }
    }

    public void onNothingSelected(AdapterView<?> parent)
    {
        toaster.toast("nothing selected");
    }

    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults)
    {
        switch (requestCode)
        {
            case Constants.REQUEST.LOCATION:
                if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                    onStartScan();
                else
                    toaster.toast("cannot scan without permission");
        }
    }

    public void start_scan(View view)
    {
        if(!location.isValid())
        {
            toaster.toast("invalid settings");
            return;
        }
        if(PermissionManager.check(this, Manifest.permission.ACCESS_COARSE_LOCATION, Constants.REQUEST.LOCATION))
            onStartScan();
    }

    private void onStartScan()
    {
        vibrator.vibrate(200);
        Log.i(TAG, location.toString());
        Intent intent = new Intent(this, WifiScanService.class);
        intent.putExtra(Constants.DATA.DURATION, duration());
        intent.putExtra(Constants.DATA.BUILDING, location.getBuilding());
        intent.putExtra(Constants.DATA.FLOOR, location.getFloor());
        intent.putExtra(Constants.DATA.ROOM, location.getRoom());
        startService(intent);
        handleNewIntent(HomeActivity.class);
    }
}