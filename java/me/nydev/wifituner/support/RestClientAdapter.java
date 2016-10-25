package me.nydev.wifituner.support;

import android.content.Context;

import org.json.*;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

import me.nydev.wifituner.model.Auth;
import me.nydev.wifituner.model.Scan;

public class RestClientAdapter
{

    private Context context;
    private Toaster toaster;

    public void setup(Context context)
    {
        this.context = context;
        this.toaster = new Toaster(context);
    }

    public void echo()
    {
        BaseRestClient.get("echo", null, new TextHttpResponseHandler()
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

    public void authenticate(Auth auth)
    {
        BaseRestClient.auth(auth);

        /*
        BaseRestClient.get("authenticate", null, new TextHttpResponseHandler()
        {
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable)
            {
                //toaster.toast(responseString);
                toaster.toast("login failure");
            }
            public void onSuccess(int statusCode, Header[] headers, String responseString)
            {
                //toaster.toast(responseString);
                toaster.toast("good");
            }
        });
        */

        BaseRestClient.get("authenticate", null, new JsonHttpResponseHandler()
        {
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse)
            {
                toaster.toast("login failed");
            }

            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                toaster.toast("authenticated");
            }
        });
    }

    public void persist_scan(Auth auth, Scan scan)
    {
        String m = String.format("NYIT: %s => %d", scan.getBSSID(), scan.getLevel());
        toaster.toast(m+ "hello");
    }
}
