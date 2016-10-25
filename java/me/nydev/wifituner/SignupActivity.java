package me.nydev.wifituner;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import me.nydev.wifituner.model.Auth;
import me.nydev.wifituner.support.BaseActivity;

public class SignupActivity extends BaseActivity
{
    protected EditText et1, et2;
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState, R.layout.activity_signup);
        et1 = (EditText) findViewById(R.id.signup_email);
        et2 = (EditText) findViewById(R.id.signup_pswd);
        api.setup(context);
    }

    public void signup_default(View view)
    {
        String email = et1.getText().toString();
        String pswd = et2.getText().toString();
        if(email.length() > 0 && pswd.length() > 0)
        {
            vibrator.vibrate(200);
            api.authenticate(new Auth(email, pswd));
        } else {
            toaster.toast("empty email or password");
        }
    }
}
