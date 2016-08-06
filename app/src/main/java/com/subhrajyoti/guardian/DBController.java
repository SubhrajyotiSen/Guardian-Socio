package com.subhrajyoti.guardian;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

public class DBController {
    // Database fields
    private DBHelper DBHelper;
    private SQLiteDatabase database;

    public DBController(Context context) {
        DBHelper = new DBHelper(context);
    }

    public void close() {
        DBHelper.close();
    }

    public void addContact(ContactModel contactModel) {

        database = DBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();


        values.put(com.subhrajyoti.guardian.DBHelper.COL_CONTACT_NAME,contactModel.getName());
        values.put(com.subhrajyoti.guardian.DBHelper.COL_CONTACT_NUMBER,contactModel.getPhone());

        database.insert(com.subhrajyoti.guardian.DBHelper.TABLE_NAME, null, values);
        database.close();
    }

    // Getting All contacts
    public List<ContactModel> getAllContacts() {
        SQLiteDatabase db = DBHelper.getWritableDatabase();

        List<ContactModel> contactModels = new ArrayList<>();
        // Select All Query
        String selectQuery = "SELECT  * FROM " + com.subhrajyoti.guardian.DBHelper.TABLE_NAME;

        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            do {
                ContactModel contactModel = new ContactModel();
                contactModel.setId(Integer.parseInt(cursor.getString(0)));
                contactModel.setName(cursor.getString(1));
                contactModel.setPhone(cursor.getString(2));
                // Adding note to list
                contactModels.add(contactModel);
            } while (cursor.moveToNext());
        }
        cursor.close();

        // return note list
        return contactModels;
    }

    // Updating single note
    public int updateContact(ContactModel contactModel) {
        SQLiteDatabase db = DBHelper.getWritableDatabase();

        ContentValues values = new ContentValues();

        values.put(com.subhrajyoti.guardian.DBHelper.COL_CONTACT_NAME, contactModel.getName());
        values.put(com.subhrajyoti.guardian.DBHelper.COL_CONTACT_NUMBER, contactModel.getPhone());

        // updating row
        return db.update(com.subhrajyoti.guardian.DBHelper.TABLE_NAME, values, com.subhrajyoti.guardian.DBHelper.COL_ID + " = ?",
                new String[]{String.valueOf(contactModel.getId())});
    }

    // Deleting single note
    public void deleteContact(ContactModel contactModel) {
        SQLiteDatabase db = DBHelper.getWritableDatabase();

        db.delete(com.subhrajyoti.guardian.DBHelper.TABLE_NAME, com.subhrajyoti.guardian.DBHelper.COL_ID + " = ?",
                new String[]{String.valueOf(contactModel.getId())});
        db.close();
    }

}