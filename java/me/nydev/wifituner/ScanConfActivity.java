package me.nydev.wifituner;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import me.nydev.wifituner.model.Location;
import me.nydev.wifituner.model.LocationBuilder;
import me.nydev.wifituner.support.BaseActivity;
import me.nydev.wifituner.support.DatabaseAdapter;

public class ScanConfActivity extends BaseActivity implements AdapterView.OnItemClickListener, AdapterView.OnItemSelectedListener
{
    private static int SPINLAYOUT = R.layout.support_simple_spinner_dropdown_item;

    protected boolean userInteracted = false;

    protected DatabaseAdapter da;
    protected Spinner s1, s2, s3;

    protected String[] buildings, rooms; int[] floors;
    LocationBuilder location;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState, R.layout.activity_scan_conf);
        da = new DatabaseAdapter(context);
    }

    protected void onResume()
    {
        super.onResume();
        initSpinners();
    }

    private void initSpinners()
    {
        location = new LocationBuilder();
        buildings = da.buildings();
        s1 = (Spinner) findViewById(R.id.scan_conf_building);
        s2 = (Spinner) findViewById(R.id.scan_conf_floor);
        s3 = (Spinner) findViewById(R.id.scan_conf_room);
        //s1.setOnItemClickListener(this);
        //s2.setOnItemClickListener(this);
        //s3.setOnItemClickListener(this);
        s1.setOnItemSelectedListener(this);
        s2.setOnItemSelectedListener(this);
        s3.setOnItemSelectedListener(this);
        s1.setAdapter(new ArrayAdapter<>(context, SPINLAYOUT, buildings));
        s1.setVisibility(View.VISIBLE);
        s2.setVisibility(View.VISIBLE);
        s3.setVisibility(View.VISIBLE);
    }

    public void onUserInteraction()
    {
        super.onUserInteraction();
        userInteracted = true;
    }

    public void onItemClick(AdapterView<?> parent, View view, int pos, long id)
    {
        if(!userInteracted) return;
        /*
        Object x = parent.getSelectedItem();
        switch(parent.getId())
        {
            case R.id.scan_conf_building:
                toaster.toast("building selected");
                //location.setBuilding((String) x);
                //floors = da.floors(location.getBuilding());
                //s2.setAdapter(new ArrayAdapter<>(context, SPINLAYOUT, buildings));
                //s2.setVisibility(View.VISIBLE);
                //s3.setVisibility(View.INVISIBLE);
                break;
            case R.id.scan_conf_floor:
                toaster.toast("floor selected");
                //location.setFloor((Integer) x);
                //rooms = da.rooms(location.getBuilding(), location.getFloor());
                //s3.setAdapter(new ArrayAdapter<>(context, SPINLAYOUT, rooms));
                //s3.setVisibility(View.VISIBLE);
                break;
            case R.id.scan_conf_room:
                toaster.toast("floor selected");
                //location.setRoom((String) x);
                break;
        }
        */
        //toaster.toast(location.build());
        toaster.toast(pos);
    }

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
    {
        onItemClick(parent, view, pos, id);
    }

    public void onNothingSelected(AdapterView<?> parent)
    {
        toaster.toast("nothing selected");
    }

    public void start_scan(View view)
    {
        toaster.toast("starting scan");
    }
}