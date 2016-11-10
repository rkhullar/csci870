package me.nydev.wifituner;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import me.nydev.wifituner.model.LocationBuilder;
import me.nydev.wifituner.support.BaseActivity;
import me.nydev.wifituner.support.DatabaseAdapter;

public class ScanConfActivity extends BaseActivity implements AdapterView.OnItemSelectedListener
{
    private static final int SPINLAYOUT = R.layout.support_simple_spinner_dropdown_item;
    private static final int[] SPINIDS = {R.id.scan_conf_building, R.id.scan_conf_floor, R.id.scan_conf_room};
    private static final String PROMPT = "<select>";

    protected DatabaseAdapter da;
    protected Spinner[] spinners;

    protected String[] buildings, rooms; Integer[] floors;
    LocationBuilder location;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState, R.layout.activity_scan_conf);
        da = new DatabaseAdapter(context);
        location = new LocationBuilder();
    }

    protected void onResume()
    {
        super.onResume();
        initSpinners();
    }

    private void initSpinners()
    {
        buildings = da.buildings();
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
        al.add(PROMPT);
        for(Object o: objects)
            al.add(o.toString());
        ArrayAdapter<String> aa = new ArrayAdapter<>(context, SPINLAYOUT, al);
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

    public void onItemSelected(AdapterView<?> parent, View view, int pos, long id)
    {
        if (pos == 0) return;
        Object x = parent.getSelectedItem();
        switch(parent.getId())
        {
            case R.id.scan_conf_building:
                location.setBuilding(x.toString());
                floors = da.floors(location.getBuilding());
                fillSpinner(1, floors);
                showSpinner(1);
                hideSpinner(2);
                break;
            case R.id.scan_conf_floor:
                location.setFloor((Integer.parseInt(x.toString())));
                rooms = da.rooms(location.getBuilding(), location.getFloor());
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

    public void start_scan(View view)
    {
        toaster.toast(location);
        //toaster.toast("starting scan");
    }
}