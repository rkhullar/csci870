package me.nydev.wifituner.model;

import android.net.wifi.ScanResult;

public class ScanBuilder extends Scan
{
    public ScanBuilder()
    {
        this.location = new Location();
    }

    public ScanBuilder setUnixTime(long x)
    {
        this.uxt = x;
        return this;
    }

    public ScanBuilder setBSSID(String x)
    {
        this.bssid = x;
        return this;
    }

    public ScanBuilder setLevel(int x)
    {
        this.level = x;
        return this;
    }

    public ScanBuilder setScanResult(ScanResult x)
    {
        this.bssid = x.BSSID;
        this.level = x.level;
        return this;
    }

    public ScanBuilder setLocation(Location x)
    {
        this.location = x;
        return this;
    }

    public ScanBuilder setBuilding(String x)
    {
        location = new LocationBuilder(location).setBuilding(x).build();
        return this;
    }

    public ScanBuilder setFloor(int x)
    {
        location = new LocationBuilder(location).setFloor(x).build();
        return this;
    }

    public ScanBuilder setRoom(String x)
    {
        location = new LocationBuilder(location).setRoom(x).build();
        return this;
    }

    public Scan build()
    {
        return new Scan(uxt, bssid, level, location);
    }
}
