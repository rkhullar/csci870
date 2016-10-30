package me.nydev.wifituner.support;

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

    public void toast(Object object)
    {
        toast = Toast.makeText(context, object.toString(), Toast.LENGTH_SHORT);
        toast.show();
    }
}