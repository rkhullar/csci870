package me.nydev.wifituner;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import me.nydev.wifituner.model.User;
import me.nydev.wifituner.model.UserBuilder;
import me.nydev.wifituner.support.BaseActivity;

public class SignupActivity extends BaseActivity
{
    protected EditText[] eta;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState, R.layout.activity_signup);
        eta = new EditText[5];
        setETA(0, R.id.signup_fname);
        setETA(1, R.id.signup_lname);
        setETA(2, R.id.signup_email);
        setETA(3, R.id.signup_pswd1);
        setETA(4, R.id.signup_pswd2);
        api.setup(context);
    }

    protected void setETA(int x, int id)
    {
        eta[x] = (EditText) findViewById(id);
    }

    protected String getETA(int x)
    {
        return eta[x].getText().toString();
    }

    protected boolean any_eta_empty()
    {
        boolean t = true;
        for(EditText et: eta)
            t &= et.getText().toString().length() > 0;
        return t;
    }

    protected boolean pswd_match()
    {
        String p, q;
        p = eta[3].getText().toString();
        q = eta[4].getText().toString();
        return p.equals(q);
    }

    public void signup_default(View view)
    {
        if(!any_eta_empty())
        {
            toaster.toast("empty fields");
            return;
        }
        if(!pswd_match())
        {
            toaster.toast("passwords do not match");
            return;
        }
        vibrator.vibrate(200);
        User user = new UserBuilder()
                .setFirstName(getETA(0))
                .setLastName(getETA(1))
                .setEmail(getETA(2))
                .setToken(getETA(3))
                .build();
        api.register(user, new JsonHttpResponseHandler(){
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse)
            {
                toaster.toast("signup failed");
            }

            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                toaster.toast(response.toString());
            }
        });
    }

    public void signup_login(View view)
    {
        handleIntent(LoginActivity.class);
    }
}
