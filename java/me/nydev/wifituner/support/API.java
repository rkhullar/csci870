package me.nydev.wifituner.support;

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import java.net.URL;
import javax.net.ssl.HttpsURLConnection;


// https://github.com/obaro/SimpleWebAPI/blob/master/app/src/main/java/com/sample/foo/simplewebapi/MainActivity.java

public class API
{
    public String persist_scan(String bssid, int level, String building, int floor, String room)
    {
        return String.format("NYIT: %s => %d", bssid, level);
    }

    /**
     *
     * android.os.NetworkOnMainThreadException
     * thrown since network ops should not run on main thread
     */
    public String test()
    {
        URL url; HttpsURLConnection c;
        BufferedReader br; StringBuilder sb; String line;
        String stage = "null";
        try {
            url = new URL("https://csci870.nydev.me/api/test");
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
                //return sb.toString();
                return "last stage";
            } finally {
                c.disconnect();
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e.getMessage());
            //Log.e("ERROR", e.getMessage(), e);
            //return null;
            //return e.getMessage();
            return stage;
        }
    }

    class RetrieveFeedTask extends AsyncTask<Void, Void, String>
    {

        protected String doInBackground(Void... voids) {

            return null;
        }

    }
}
