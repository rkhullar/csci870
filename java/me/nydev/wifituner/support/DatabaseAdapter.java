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

    public void login(Auth auth)
    {
        logout();
        SQLiteDatabase db = getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(KEY_EMAIL, auth.getUsername());
        cv.put(KEY_TOKEN, auth.getSecret());
        db.insert(TABLE_USER, null, cv);
        db.close();
    }

    public void logout()
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_USER, null, null);
        db.close();
    }

    public boolean login()
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select id from user", null);
        int x = c.getCount();
        c.close();
        db.close();
        return x > 0;
    }

    public Auth getAuth()
    {
        SQLiteDatabase db = getReadableDatabase();
        Cursor c = db.rawQuery("select email, token from user", null);
        if(c.getCount() < 1)
            return null;
        c.moveToFirst();
        Auth a = new Auth(c.getString(0), c.getString(1));
        c.close();
        db.close();
        return a;
    }


    public String[] test()
    {
        SQLiteDatabase db = getReadableDatabase();
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
