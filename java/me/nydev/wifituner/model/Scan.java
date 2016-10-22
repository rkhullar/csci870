package me.nydev.wifituner.model;

public class Scan
{
    protected String bssid;
    protected int level;
    protected Location location;

    public Scan() {}

    public Scan(String bssid, int level, Location location)
    {
        this.bssid = bssid;
        this.level = level;
        this.location = location;
    }

    public String getBSSID()
    {
        return bssid;
    }

    public int getLevel()
    {
        return level;
    }

    public Location getLocation()
    {
        return location;
    }
}
