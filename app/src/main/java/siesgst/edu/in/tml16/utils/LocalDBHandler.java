package siesgst.edu.in.tml16.utils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.renderscript.ScriptIntrinsicYuvToRGB;
import android.util.Log;

/**
 * Created by vishal on 1/1/16.
 */
public class LocalDBHandler extends SQLiteOpenHelper {

    final String U_KEY = "uID";
    final String U_NAME = "uName";
    final String U_EMAIL = "uEmail";
    final String U_PHONE= "uPhone";
    final String YEAR = "Year";
    final String BRANCH = "Branch";
    final String DIVISION = "Division";
    final String COLLEGE = "College";
    final String U_CREATED = "uCreated";
    final String U_MODIFIED = "uModified";

    final String E_KEY = "eID";
    final String E_NAME = "eName";
    final String E_DAY = "eDay";
    final String E_TIME = "eTime";
    final String E_VENUE = "eVenue";
    final String E_CATEGORY = "eCategory";
    final String EVENT_HEAD_1 = "eHead1";
    final String EVENT_HEAD_2 = "eHead2";
    final String E_PHONE_1 = "ePhone1";
    final String E_PHONE_2 = "ePhone2";

    final String PAYMENT_STATUS = "pStatus";

    final int DB_VERSION = 1;

    final String USER_TABLE_NAME = "user_table";
    final String EVENT_TABLE_NAME = "event_table";
    final String REG_TABLE_NAME = "reg_table";

    final String CREATE_USER_TABLE  = "CREATE TABLE IF NOT EXISTS user_table(uID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "uName VARCHAR DEFAULT NULL," +
            "uEmail VARCHAR DEFAULT NULL," +
            "uPhone VARCHAR DEFAULT NULL," +
            "Year VARCHAR DEFAULT NULL," +
            "Branch VARCHAR DEFAULT NULL," +
            "Division VARCHAR DEFAULT NULL," +
            "College VARCHAR DEFAULT NULL," +
            "uCreated VARCHAR DEFAULT NULL," +
            "uModified VARCHAR DEFAULT NULL)";

    final String CREATE_EVENT_TABLE = "CREATE TABLE IF NOT EXISTS event_table(eID INTEGER PRIMARY KEY AUTOINCREMENT," +
            "eName VARCHAR DEFAULT NULL," +
            "eDay VARCHAR DEFAULT NULL," +
            "eTime VARCHAR DEFAULT NULL," +
            "eVenue VARCHAR DEFAULT NULL," +
            "eCategory VARCHAR DEFAULT NULL," +
            "eHead1 VARCHAR DEFAULT NULL," +
            "ePhone1 VARCHAR DEFAULT NULL," +
            "eHead2 VARCHAR DEFAULT NULL," +
            "ePhone2 VARCHAR DEFAULT NULL)";

    final String CREATE_REG_TABLE = "CREATE TABLE IF NOT EXISTS reg_table(uID VARCHAR DEFAULT NULL," +
            "eID VARCHAR DEFAULT NULL," +
            "pStatus VARCHAR DEFAULT NULL)";


    public LocalDBHandler(Context context) {
        super(context, "tml_event_details", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_USER_TABLE);
        database.execSQL(CREATE_EVENT_TABLE);
        database.execSQL(CREATE_REG_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

    public void wapasTableBana() {
        SQLiteDatabase db = getWritableDatabase();
        db.execSQL("DROP TABLE IF EXISTS " + USER_TABLE_NAME);
        db.execSQL(CREATE_USER_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + EVENT_TABLE_NAME);
        db.execSQL(CREATE_EVENT_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + REG_TABLE_NAME);
        db.execSQL(CREATE_REG_TABLE);
        db.close();
    }

    public boolean doesExists()///TO CHECK IF DB EXISTS
    {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(USER_TABLE_NAME, new String[]{U_NAME}, null, null, null, null, null);
        if (cursor != null && cursor.getCount() > 0) {
            Log.d("TML", "DATABASE ENTRIES: " + cursor.getCount());
            db.close();
            return true;
        }
        Log.d("TML", "NO DATABASE;WILL CREATE ACCORDINGLY");
        db.close();
        return false;
    }

    public void insertUserData(String[] data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(U_NAME, data[0]);
        values.put(U_EMAIL, data[1]);
        values.put(U_PHONE, data[2]);
        values.put(YEAR, data[3]);
        values.put(BRANCH, data[4]);
        values.put(DIVISION, data[5]);
        values.put(COLLEGE, data[6]);
        values.put(U_CREATED, data[7]);
        values.put(U_MODIFIED, data[8]);
        db.insert(USER_TABLE_NAME, null, values);
        db.close();
    }

    public void insertEventData(String[] data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(E_NAME, data[0]);
        values.put(E_DAY, data[1]);
        values.put(E_TIME, data[2]);
        values.put(E_VENUE, data[3]);
        values.put(E_CATEGORY, data[4]);
        values.put(EVENT_HEAD_1, data[5]);
        values.put(E_PHONE_1, data[6]);
        values.put(EVENT_HEAD_2, data[7]);
        values.put(E_PHONE_2, data[8]);
        db.insert(EVENT_TABLE_NAME, null, values);
        db.close();
    }

    public void insertRegData(String[] data) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(U_KEY, data[0]);
        values.put(E_KEY, data[1]);
        values.put(PAYMENT_STATUS, data[2]);
        db.insert(REG_TABLE_NAME, null, values);
        db.close();
    }

}