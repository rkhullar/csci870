package me.nydev.wifituner.support;

import org.json.*;
import com.loopj.android.http.*;

public class RestClientUsage
{
    private Toaster toaster;

    public RestClientUsage(Toaster toaster)
    {
        this.toaster = toaster;
    }

    public void test() throws JSONException
    {
        this.toaster.toast("Hell World");
        //RestClient.get("test", null, new JsonHttpResponseHandler());
    }

    public String persist_scan(String bssid, int level, String building, int floor, String room)
    {
        return String.format("NYIT: %s => %d", bssid, level);
    }
}
