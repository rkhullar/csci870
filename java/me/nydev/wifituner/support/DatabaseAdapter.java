package me.nydev.wifituner.support;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import me.nydev.wifituner.model.Auth;

public class DatabaseAdapter extends BaseDatabase
{
    public DatabaseAdapter(Context context)
    {
        super(context);
    }

    public void login()
    {
        logout();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_EMAIL, "test@nyit.edu");
        db.insert(TABLE_USER, null, cv);
        db.close();
    }

    public void logout()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_USER, null, null);
        db.close();
    }

    public String[] test()
    {
        SQLiteDatabase db = getWritableDatabase();
        Cursor c = db.rawQuery("select email from user", null);
        int n = c.getCount(), x = 0;
        String[] out = new String[n];
        if(n > 0)
        {
            c.moveToFirst();
            do {
                out[x] = c.getString(0);
            } while (c.moveToNext());
        }
        c.close();
        db.close();
        return out;
    }
}
