package me.nydev.wifituner;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

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
        api.authenticate(new Auth(email, pswd));
    }

    public void login_home(View view)
    {
        handleIntent(HomeActivity.class);
    }
}
