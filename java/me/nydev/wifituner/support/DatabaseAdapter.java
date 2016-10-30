package me.nydev.wifituner.support;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.Locale;

import me.nydev.wifituner.model.Auth;
import me.nydev.wifituner.model.Location;
import me.nydev.wifituner.model.LocationBuilder;

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

    //==============================================================================================

    public void locations(Location[] a)
    {
        SQLiteDatabase db = getWritableDatabase();
        db.delete(TABLE_BUILDING, null, null);
        db.delete(TABLE_LOCATION, null, null);
        ContentValues cv = new ContentValues(); long bid;
        for(Location l: a)
        {
            cv.clear();
            bid = findOrAddBuilding(db, l.getBuilding());
            cv.put(KEY_BUILDING_ID, bid);
            cv.put(KEY_FLOOR, l.getFloor());
            cv.put(KEY_ROOM, l.getRoom());
            System.out.printf("%s(%d) %d %s\n", l.getBuilding(), bid, l.getFloor(), l.getRoom());
            db.insert(TABLE_LOCATION, null, cv);
        }
        db.close();
    }

    public Location[] locations()
    {
        SQLiteDatabase db = getReadableDatabase();
        String project = "abbr, floor, room";
        String table = "location inner join building";
        String cond = "location.buildingID = building.id";
        String sql = String.format(Locale.US, "select %s from %s on %s", project, table, cond);
        Cursor c = db.rawQuery(sql, null);
        int n = c.getCount();
        c.moveToFirst();
        Location[] a = new Location[n];
        for(int x = 0; x < n; x++)
        {
            a[x] = new LocationBuilder()
                    .setBuilding(c.getString(0))
                    .setFloor(c.getInt(1))
                    .setRoom(c.getString(2))
                    .build();
            c.moveToNext();
        }
        c.close();
        return a;
    }

    private long findBuilding(SQLiteDatabase db, String abbr)
    {
        Cursor c = db.rawQuery("select id from building where abbr=?", new String[]{abbr});
        if(c.getCount() < 1)
            return -1;
        c.moveToFirst();
        long x = c.getLong(0);
        c.close();
        return x;
    }

    private long addBuilding(SQLiteDatabase db, String abbr)
    {
        ContentValues cv = new ContentValues();
        cv.put(KEY_ABBR, abbr);
        return db.insert(TABLE_BUILDING, null, cv);
    }

    private long findOrAddBuilding(SQLiteDatabase db, String abbr)
    {
        long x = findBuilding(db, abbr);
        if(x < 0)
            return addBuilding(db, abbr);
        return x;
    }

    //==============================================================================================

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
