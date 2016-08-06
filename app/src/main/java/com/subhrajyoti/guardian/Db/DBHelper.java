package com.subhrajyoti.guardian.Db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

class DBHelper extends SQLiteOpenHelper {

    //Table Name
    static final String TABLE_NAME = "contacts";
    //Column Names
    static final String COL_ID = "_id";
    static final String COL_CONTACT_NAME = "_name";
    static final String COL_CONTACT_NUMBER = "_number";
    static final String[] columns = new String[]{DBHelper.COL_ID,
            DBHelper.COL_CONTACT_NAME, DBHelper.COL_CONTACT_NUMBER
    };
    //Database Information
    private static final String DATABASE_NAME = "contacts.db";
    private static final int DATABASE_VERSION = 1;

    // creation SQLite statement
    private static final String DATABASE_CREATE = "CREATE TABLE " + TABLE_NAME
            + "(" + COL_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COL_CONTACT_NAME + " TEXT NOT NULL, " + COL_CONTACT_NUMBER + " TEXT NOT NULL);";

    DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        System.out.println("DB Created");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);
        System.out.println("Table Created");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
        System.out.println("DB Updated");
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        onUpgrade(db, oldVersion, newVersion);
    }
}