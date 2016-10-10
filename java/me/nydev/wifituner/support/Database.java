package me.nydev.wifituner.support;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class Database extends SQLiteOpenHelper
{

    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "wifituner";

    private static final String TABLE_USER     = "user";
    private static final String TABLE_BUILDING = "building";
    private static final String TABLE_LOCATION = "location";
    private static final String TABLE_WPA      = "wpa";
    private static final String TABLE_SCAN     = "scan";

    private static final String INT = "integer";
    private static final String STR = "integer";
    private static final String TIME = "datetime";
    private static final String PK  = INT + "primary key";

    private static final String KEY_ID   = "id";
    private static final String KEY_TIME = "timestamp";

    private static final String KEY_FNAME = "fname";
    private static final String KEY_LNAME = "lname";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_TOKEN = "token";

    private static final String KEY_ABBR  = "abbr";
    private static final String KEY_FLOOR = "floor";
    private static final String KEY_ROOM  = "room";

    private static final String KEY_BSSID = "bssid";
    private static final String KEY_LEVEL = "level";

    private static final String KEY_WPA_ID      = "wpaID";
    private static final String KEY_BUILDING_ID = "buildingID";
    private static final String KEY_LOCATION_ID = "locationID";

    public Database(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    public void onCreate(SQLiteDatabase db)
    {
        String body;
        body = String.format("%s %s, %s %s, %s %s, %s %s, %s %s,", KEY_ID, PK, KEY_FNAME, STR, KEY_LNAME, STR, KEY_EMAIL, STR, KEY_TOKEN, STR);
        createTable(db, TABLE_USER, body);

        body = String.format("%s %s, %s %s", KEY_ID, PK, KEY_ABBR, STR);
        createTable(db, TABLE_BUILDING, body);

        body = String.format("%s %s, %s %s, %s %s, %s %s", KEY_ID, PK, KEY_BUILDING_ID, INT, KEY_FLOOR, INT, KEY_ROOM, STR);
        createTable(db, TABLE_LOCATION, body);

        body = String.format("%s %s, %s %s", KEY_ID, PK, KEY_BSSID, STR);
        createTable(db, TABLE_WPA, body);

        body = String.format("%s %s, %s %s, %s %s, %s %s", KEY_TIME, TIME, KEY_WPA_ID, INT, KEY_LEVEL, INT, KEY_LOCATION_ID, INT);
        createTable(db, TABLE_SCAN, body);
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

    private void createTable(SQLiteDatabase db, String name, String body)
    {
        db.execSQL(String.format("create table %s(%s)", name, body));
    }

    private void dropTable(SQLiteDatabase db, String name)
    {
        db.execSQL(String.format("drop table if exists %s", name));
    }
}
