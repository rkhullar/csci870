package me.nydev.wifituner.support;

import org.json.*;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

import me.nydev.wifituner.model.Auth;
import me.nydev.wifituner.model.Scan;

public class RestClientUsage
{
    private Toaster toaster;

    public void setToaster(Toaster toaster)
    {
        this.toaster = toaster;
    }

    public void echo()
    {
        RestClient.get("echo", null, new TextHttpResponseHandler()
        {
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
            {
                toaster.toast(responseString);
            }
            public void onSuccess(int statusCode, Header[] headers, String responseString)
            {
                toaster.toast(responseString);
            }
        });
    }

    public void test_auth()
    {
        RestClient.auth("rkhullar@nyit.edu", "aaaaaa");
        RestClient.get("authenticate", null, new TextHttpResponseHandler()
        {
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
            {
                toaster.toast(responseString);
            }
            public void onSuccess(int statusCode, Header[] headers, String responseString)
            {
                toaster.toast(responseString);
            }
        });
    }

    public void persist_scan(Auth auth, Scan scan)
    {
        String m = String.format("NYIT: %s => %d", scan.getBSSID(), scan.getLevel());
        toaster.toast(m+ "hello");
    }
}
