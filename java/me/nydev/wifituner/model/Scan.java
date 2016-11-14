package me.nydev.wifituner.model;

import android.net.wifi.ScanResult;

import java.util.List;
import java.util.Locale;

import me.nydev.wifituner.Constants;

public class Scan
{
    private static boolean filter = true;

    protected long uxt;
    protected String bssid;
    protected int level;
    protected Location location;

    public Scan() {}

    public Scan(long uxt, String bssid, int level, Location location)
    {
        this.uxt = uxt;
        this.bssid = bssid;
        this.level = level;
        this.location = location;
    }

    public long getUnixTime()
    {
        return uxt;
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

    public String toString()
    {
        if(location != null)
            return String.format(Locale.US, "%s => %d | %s | %d", bssid, level, location.getBuilding(), location.getFloor());
        return String.format(Locale.US, "%s => %d", bssid, level);
    }

    public static void disableFilter()
    {
        filter = false;
    }
    public static void enableFilter()
    {
        filter = true;
    }

    public static Scan[] parseScanResults(List<ScanResult> scanResults, Location location, long uxt)
    {
        int n = scanResults.size(), c = 0;
        ScanResult sr;
        for(int x = 0; x < n; x++)
            if(scanResults.get(x).SSID.equals(Constants.VAR.SSID) || !filter)
                c++;
        Scan[] a = new Scan[c]; c = 0;
        for(int x = 0; x < n; x++)
        {
            sr = scanResults.get(x);
            if(sr.SSID.equals(Constants.VAR.SSID) || !filter)
                a[c++] = new Scan(uxt, sr.BSSID, sr.level, location);
        }
        return a;
    }

}
