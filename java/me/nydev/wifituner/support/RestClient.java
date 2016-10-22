package me.nydev.wifituner.support;

import com.loopj.android.http.*;

import me.nydev.wifituner.model.Auth;

public class RestClient
{
    private static final String BASE_URL = "https://csci870.nydev.me/api/";

    private static AsyncHttpClient client = new AsyncHttpClient();

    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler)
    {
        client.get(getAbsoluteUrl(url), params, responseHandler);
    }

    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler)
    {
        client.post(getAbsoluteUrl(url), params, responseHandler);
    }

    private static String getAbsoluteUrl(String relativeUrl)
    {
        return BASE_URL + relativeUrl;
    }

    public static void auth(String username, String secret)
    {
        client.setBasicAuth(username, secret);
    }

    public static void auth(Auth a)
    {
        client.setBasicAuth(a.getUsername(), a.getSecret());
    }
}
