package me.nydev.wifituner.support;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;

public class PermissionManager
{
    public static boolean check(Activity activity, String permission, int requestCode)
    {
        boolean x = ContextCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED;
        if(!x) ActivityCompat.requestPermissions(activity, new String[] {permission}, requestCode);
        return x;
    }
}
