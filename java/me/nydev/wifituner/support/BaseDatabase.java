package me.nydev.wifituner.support;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class BaseDatabase extends SQLiteOpenHelper
{
    protected static final int DATABASE_VERSION = 1;
    protected static final String DATABASE_NAME = "wifituner.db";

    protected static final String TABLE_USER     = "user";
    protected static final String TABLE_BUILDING = "building";
    protected static final String TABLE_LOCATION = "location";
    protected static final String TABLE_WPA      = "wpa";
    protected static final String TABLE_SCAN     = "scan";

    protected static final String INT = "integer";
    protected static final String STR = "text";
    protected static final String TIME = "datetime";
    protected static final String PK  = INT + "primary key";

    protected static final String KEY_ID   = "id";
    protected static final String KEY_TIME = "timestamp";

    protected static final String KEY_FNAME = "fname";
    protected static final String KEY_LNAME = "lname";
    protected static final String KEY_EMAIL = "email";
    protected static final String KEY_TOKEN = "token";

    protected static final String KEY_ABBR  = "abbr";
    protected static final String KEY_FLOOR = "floor";
    protected static final String KEY_ROOM  = "room";

    protected static final String KEY_BSSID = "bssid";
    protected static final String KEY_LEVEL = "level";

    protected static final String KEY_WPA_ID      = "wpaID";
    protected static final String KEY_BUILDING_ID = "buildingID";
    protected static final String KEY_LOCATION_ID = "locationID";

    BaseDatabase(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db)
    {
        createTable(db, TABLE_USER, KEY_ID, PK, KEY_FNAME, STR, KEY_LNAME, STR, KEY_EMAIL, STR, KEY_TOKEN, STR);
        createTable(db, TABLE_BUILDING, KEY_ID, PK, KEY_ABBR, STR);
        createTable(db, TABLE_LOCATION, KEY_ID, PK, KEY_BUILDING_ID, INT, KEY_FLOOR, INT, KEY_ROOM, STR);
        createTable(db, TABLE_WPA, KEY_ID, PK, KEY_BSSID, STR);
        createTable(db, TABLE_SCAN, KEY_TIME, TIME, KEY_WPA_ID, INT, KEY_LEVEL, INT, KEY_LOCATION_ID, INT);
    }

    public void onUpgrade(SQLiteDatabase db, int v, int u)
    {
        dropTable(db, TABLE_USER);
        dropTable(db, TABLE_BUILDING);
        dropTable(db, TABLE_LOCATION);
        dropTable(db, TABLE_WPA);
        dropTable(db, TABLE_SCAN);
        onCreate(db);
    }

    private void createTable(SQLiteDatabase db, String table, String... kwargs)
    {
        String fmt = "";
        int n = kwargs.length / 2;
        for(int x = 0; x < n; x++)
        {
            fmt = fmt.concat("%s %s");
            if(x != n-1)
                fmt = fmt.concat(",");
        }
        String body = String.format(fmt, (Object[])kwargs);
        String sql = String.format("create table %s(%s)", table, body);
        db.execSQL(sql);
    }

    private void dropTable(SQLiteDatabase db, String table)
    {
        db.execSQL(String.format("drop table if exists %s", table));
    }
}
