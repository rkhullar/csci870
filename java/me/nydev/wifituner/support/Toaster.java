package me.nydev.wifituner.support;

import android.content.Context;
import android.widget.Toast;

public class Toaster
{
    protected Context context;
    private static int length = Toast.LENGTH_SHORT;

    public Toaster(Context c)
    {
        context = c;
    }

    public void setLong()
    {
        length = Toast.LENGTH_LONG;
    }

    public void setShort()
    {
        length = Toast.LENGTH_SHORT;
    }

    public void toast(Object object)
    {
        Toast toast = Toast.makeText(context, object.toString(), length);
        toast.show();
    }
}