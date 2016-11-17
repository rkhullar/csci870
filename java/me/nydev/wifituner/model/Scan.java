package me.nydev.wifituner.model;

import android.net.wifi.ScanResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

    public void addUnixTime(long t)
    {
        uxt += t;
    }

    public void delUnixTime(long t)
    {
        uxt -= t;
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

    public String getBuilding()
    {
        return location.getBuilding();
    }

    public int getFloor()
    {
        return location.getFloor();
    }

    public String getRoom()
    {
        return location.getRoom();
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

    public static JSONObject jsonify(Scan x)
    {
        JSONObject json = new JSONObject();
        try {
            json.put("uxt", x.getUnixTime());
            json.put("bssid", x.getBSSID());
            json.put("level", x.getLevel());
            json.put("building" , x.getBuilding());
            json.put("floor", x.getFloor());
            json.put("room", x.getRoom());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static Scan parseObject(JSONObject json)
    {
        Location l; Scan s = null;
        try {
            l = new LocationBuilder()
                    .setBuilding(json.getString("building"))
                    .setFloor(json.getInt("floor"))
                    .setRoom(json.getString("room"))
                    .build();
            s = new ScanBuilder()
                    .setUnixTime(json.getLong("uxt"))
                    .setBSSID(json.getString("bssid"))
                    .setLevel(json.getInt("level"))
                    .setLocation(l)
                    .build();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return s;
    }

    public static JSONObject jsonify(Scan[] a, int l, int h)
    {
        JSONObject json = new JSONObject();
        JSONArray vt=new JSONArray(), vw=new JSONArray(), vl=new JSONArray(), vb=new JSONArray(), vf=new JSONArray(), vr=new JSONArray();
        try {
            json.put("size", h-l+1);
            for(int x = l; x <= h; x++)
            {
                vt.put(a[x].getUnixTime());
                vw.put(a[x].getBSSID());
                vl.put(a[x].getLevel());
                vb.put(a[x].getBuilding());
                vf.put(a[x].getFloor());
                vr.put(a[x].getRoom());
            }
            json.put("uxt", vt);
            json.put("bssid", vw);
            json.put("level", vl);
            json.put("building" , vb);
            json.put("floor", vf);
            json.put("room", vr);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return json;
    }

    public static JSONObject jsonify(Scan[] a)
    {
        return jsonify(a, 0, a.length-1);
    }

    public static Scan[] parseArray(JSONObject json)
    {
        Scan[] a = new Scan[0];
        JSONArray vt, vw, vl, vb, vf, vr;
        ScanBuilder sb = new ScanBuilder();
        LocationBuilder lb = new LocationBuilder();
        Location location;
        try {
            int n = json.getInt("size");
            a = new Scan[n];
            vt = json.getJSONArray("uxt");
            vw = json.getJSONArray("bssid");
            vl = json.getJSONArray("level");
            vb = json.getJSONArray("building");
            vf = json.getJSONArray("floor");
            vr = json.getJSONArray("room");
            for(int x = 0; x < n; x++)
            {
                location = lb
                        .setBuilding(vb.getString(x))
                        .setFloor(vf.getInt(x))
                        .setRoom(vr.getString(x))
                        .build();
                a[x] = sb
                        .setUnixTime(vt.getLong(x))
                        .setBSSID(vw.getString(x))
                        .setLevel(vl.getInt(x))
                        .setLocation(location)
                        .build();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return a;
    }

}
