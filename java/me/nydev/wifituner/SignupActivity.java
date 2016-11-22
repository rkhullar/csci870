package me.nydev.wifituner;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import com.loopj.android.http.JsonHttpResponseHandler;
import org.json.JSONObject;
import java.util.regex.Pattern;

import cz.msebera.android.httpclient.Header;
import me.nydev.wifituner.model.User;
import me.nydev.wifituner.model.UserBuilder;
import me.nydev.wifituner.support.BaseActivity;

public class SignupActivity extends BaseActivity
{
    private static final String TAG = "SignupActivity";

    private EditText[] eta;

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

    private void setETA(int x, int id)
    {
        eta[x] = (EditText) findViewById(id);
    }

    private String getETA(int x)
    {
        return eta[x].getText().toString();
    }

    private boolean areAllFieldsFilled()
    {
        boolean t = true;
        for(EditText et: eta)
            t &= et.getText().toString().length() > 0;
        return t;
    }

    private boolean doPswdsMatch()
    {
        String p, q;
        p = eta[3].getText().toString();
        q = eta[4].getText().toString();
        return p.equals(q);
    }

    private boolean isCollegeEmail() {
        String e = eta[2].getText().toString();
        String[] a = e.split("@");
        if (a.length < 2) return false;
        a = a[1].split(Pattern.quote("."));
        return a.length > 1 && a[a.length - 1].equals("edu");
    }

    public void signup_default(View view)
    {
        if(!areAllFieldsFilled())
        {
            toaster.toast("empty fields");
            return;
        }
        if(!doPswdsMatch())
        {
            toaster.toast("passwords do not match");
            return;
        }
        if(!isCollegeEmail())
        {
            toaster.toast("please use edu email");
            return;
        }
        vibrator.vibrate(200);
        User user = new UserBuilder()
                .setFirstName(getETA(0))
                .setLastName(getETA(1))
                .setEmail(getETA(2))
                .setToken(getETA(3))
                .build();
        api.register(user, new JsonHttpResponseHandler()
        {
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse)
            {
                toaster.toast("signup failed");
            }

            public void onSuccess(int statusCode, Header[] headers, JSONObject response)
            {
                toaster.toast("email sent");
            }
        });
    }

    public void signup_login(View view)
    {
        handleIntent(LoginActivity.class);
    }
}
