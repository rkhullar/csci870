package me.nydev.wifituner;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.view.View;

import com.loopj.android.http.*;

import me.nydev.wifituner.support.RestClientUsage;
import me.nydev.wifituner.support.Toaster;

public class APITestActivity extends Activity
{
    protected Context  context;
    protected Toaster  toaster;
    protected Vibrator vibrator;

    protected RestClientUsage api;

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_api);
        context = getApplicationContext();
        toaster = new Toaster(context);
        vibrator = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        api = new RestClientUsage(toaster);
    }

    public void test_api_default(View view)
    {
        try {
            api.test();
        } catch (Exception e) {
            toaster.toast("error");
        }
    }

    /*
    public void test_api_default(View view)
    {
        URL url; HttpsURLConnection c;
        BufferedReader br; StringBuilder sb; String line;
        String stage = "null";
        try {
            url = new URL(endpoint);
            stage = "url";
            c = (HttpsURLConnection) url.openConnection();
            stage = "con";
            try {
                stage = "inner try";
                br = new BufferedReader(new InputStreamReader(c.getInputStream()));
                stage = "buffered reader";
                sb = new StringBuilder();
                stage = "string builder";
                while( (line = br.readLine()) != null)
                    sb.append(line).append('\n');
                br.close();
                toaster.toast(sb.toString());
            } finally {
                c.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            //Log.e("ERROR", e.getMessage(), e);
            //Log.d(MY_APP_TAG, "Error: " + e.getMessage());
            toaster.toast(stage);
        }
    }
    */

}
