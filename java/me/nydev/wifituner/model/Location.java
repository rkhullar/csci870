package me.nydev.wifituner.model;

import android.content.Intent;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Locale;

import me.nydev.wifituner.Constants;

public class Location
{
    protected String building, room;
    protected int floor;

    public Location() {}

    public Location(String building, int floor, String room)
    {
        this.building = building;
        this.floor = floor;
        this.room = room;
    }

    public Location(Intent i)
    {
        building = i.getStringExtra(Constants.DATA.BUILDING);
        floor = i.getIntExtra(Constants.DATA.FLOOR, -99);
        room = i.getStringExtra(Constants.DATA.ROOM);
    }

    public String getBuilding() {
        return building;
    }

    public int getFloor()
    {
        return floor;
    }

    public String getRoom()
    {
        return room;
    }

    public boolean isValid()
    {
        return !(building == null || room == null);
    }

    public boolean equals(Location x)
    {
        return x.building.equals(building)
                && x.floor == floor
                && x.room.equals(room);
    }

    public String toString()
    {
        return String.format(Locale.US, "%s %d %s", building, floor, room);
    }

    public static Location[] parseArray(JSONObject json)
    {
        Location[] a = new Location[0];
        JSONArray a1, a2, a3;
        LocationBuilder lb = new LocationBuilder();
        try {
            int n = json.getInt("size");
            a = new Location[n];
            a1 = json.getJSONArray("building");
            a2 = json.getJSONArray("floor");
            a3 = json.getJSONArray("room");
            for(int x = 0; x < n; x++)
            {
                a[x] = lb
                        .setBuilding(a1.getString(x))
                        .setFloor(a2.getInt(x))
                        .setRoom(a3.getString(x))
                        .build();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return a;
    }
}
