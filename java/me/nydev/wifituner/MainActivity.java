/**
 * https://github.com/obaro/SimpleWebAPI/blob/master/app/src/main/java/com/sample/foo/simplewebapi/MainActivity.java
 */

package me.nydev.wifituner;

import android.os.Bundle;
import android.view.View;

import me.nydev.wifituner.support.BaseActivity;

public class MainActivity extends BaseActivity
{
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState, R.layout.activity_main);
    }

    public void main_test_wifi(View view)
    {
        handleIntent(me.nydev.wifituner.WiFiTestActivity.class);
    }

    public void main_test_api(View view)
    {
        handleIntent(me.nydev.wifituner.APITestActivity.class);
    }

    public void main_test_login(View view)
    {
        handleIntent(me.nydev.wifituner.LoginTestActivity.class);
    }

}