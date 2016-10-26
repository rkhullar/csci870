package me.nydev.wifituner.support;

import android.content.Context;

import org.json.*;
import com.loopj.android.http.*;

import cz.msebera.android.httpclient.Header;

import cz.msebera.android.httpclient.entity.StringEntity;
import me.nydev.wifituner.model.Auth;
import me.nydev.wifituner.model.Scan;
import me.nydev.wifituner.model.User;

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

    public void authenticate(Auth auth, JsonHttpResponseHandler handler)
    {
        BaseRestClient.auth(auth);
        BaseRestClient.get("authenticate", null, handler);
    }

    public void register(User user, JsonHttpResponseHandler handler)
    {
        JSONObject json = new JSONObject();
        StringEntity entity = null;
        try {
            json.put("fname", user.fname());
            json.put("lname", user.lname());
            json.put("email", user.email());
            json.put("pswd", user.token());
            entity = new StringEntity(json.toString());
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        AsyncHttpClient client = BaseRestClient.getClient();
        client.post(context, "register", entity, "application/json", handler);
    }

    public void persist_scan(Auth auth, Scan scan)
    {
        String m = String.format("NYIT: %s => %d", scan.getBSSID(), scan.getLevel());
        toaster.toast(m+ "hello");
    }
}
