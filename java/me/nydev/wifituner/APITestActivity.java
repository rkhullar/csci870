package me.nydev.wifituner;

import android.os.Bundle;
import android.view.View;

import me.nydev.wifituner.support.BaseActivity;

public class APITestActivity extends BaseActivity
{

    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState, R.layout.activity_test_api);
        api.setup(context);
    }

    public void test_api_default(View view)
    {
        api.echo();
    }

    public void test_api_auth(View view)
    {
        // obsolete
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
