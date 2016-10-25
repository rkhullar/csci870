/**
 * https://github.com/obaro/SimpleWebAPI/blob/master/app/src/main/java/com/sample/foo/simplewebapi/MainActivity.java
 */

package me.nydev.wifituner;

import android.os.Bundle;
import android.view.View;

import me.nydev.wifituner.support.BaseActivity;

public class HomeActivity extends BaseActivity
{
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState, R.layout.activity_home);
    }

    public void home_test_wifi(View view)
    {
        handleIntent(WiFiTestActivity.class);
    }

    public void home_test_api(View view)
    {
        handleIntent(APITestActivity.class);
    }

    public void home_test_db(View view)
    {
        handleIntent(DBTestActivity.class);
    }
}