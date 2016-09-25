package me.nydev.wifituner;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

public class Toaster
{
    protected Activity activity;
    protected Context context;
    protected Toast toast;

    public Toaster(Activity a)
    {
        activity = a;
        context = activity.getApplicationContext();
    }

    public Toaster(Context c)
    {
        context = c;
    }

    public void toast(String message)
    {
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT);
        toast.show();
    }
}