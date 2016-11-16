package me.nydev.wifituner.support;

import android.content.Context;
import android.util.Log;

import org.json.*;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

import me.nydev.wifituner.model.Auth;
import me.nydev.wifituner.model.Scan;
import me.nydev.wifituner.model.User;

public class RestClientAdapter
{
    private static final String TAG = "RestClientAdapter";

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

    public void authenticate(Auth auth, JsonHttpResponseHandler handler)
    {
        BaseRestClient.auth(auth);
        BaseRestClient.get("authenticate", null, handler);
    }

    public void register(User user, JsonHttpResponseHandler handler)
    {
        JSONObject json = User.jsonify(user);
        BaseRestClient.post("register", json, handler);
    }

    public void fetch_locations(Auth auth, JsonHttpResponseHandler handler)
    {
        BaseRestClient.auth(auth);
        BaseRestClient.get("locations", null, handler);
    }

    public void fetch_time(JsonHttpResponseHandler handler)
    {
        BaseRestClient.get("time", null, handler);
    }

    public long extract_time(JSONObject json)
    {
        try {
            return json.getLong("time");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return -1;
    }

    public void persist_scan(Auth auth, Scan scan, JsonHttpResponseHandler handler)
    {
        BaseRestClient.auth(auth);
        JSONObject json = Scan.jsonify(scan);
        BaseRestClient.post("scan", json, handler);
    }
}
