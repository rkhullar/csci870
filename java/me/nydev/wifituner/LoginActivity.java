package me.nydev.wifituner;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import org.json.JSONObject;
import cz.msebera.android.httpclient.Header;
import com.loopj.android.http.JsonHttpResponseHandler;

import me.nydev.wifituner.model.Auth;
import me.nydev.wifituner.support.BaseActivity;

public class LoginActivity extends BaseActivity
{
    protected EditText et1, et2;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState, R.layout.activity_login);
        et1 = (EditText) findViewById(R.id.login_email);
        et2 = (EditText) findViewById(R.id.login_pswd);
        api.setup(context);
        gate();
    }

    protected void gate()
    {
        if(dba.login())
            handleNewIntent(HomeActivity.class);
    }

    public void login_default(View view)
    {
        String email = et1.getText().toString();
        String pswd = et2.getText().toString();
        if(email.length() < 1 || pswd.length() < 1)
        {
            toaster.toast("empty email or password");
            return;
        }
        vibrator.vibrate(200);
        auth = new Auth(email, pswd);
        api.authenticate(auth, new JsonHttpResponseHandler(){
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse)
            {
                toaster.toast("login failed");
            }

            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                toaster.toast("authenticated");
                dba.login(auth);
                handleNewIntent(HomeActivity.class);
            }
        });
    }

    public void login_signup(View view)
    {
        handleIntent(SignupActivity.class);
    }
}
