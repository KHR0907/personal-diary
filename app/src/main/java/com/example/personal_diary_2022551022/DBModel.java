package com.example.personal_diary_2022551022;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


// ERROR CODE
//-1: nessesary argument is empty
//-2: invailed database condition
//-3: query execution failed

public class DBModel extends SQLiteOpenHelper {

    // Database Information
    private static final String DATABASE_NAME = "contacts.db";
    private static final int DATABASE_VERSION = 1;

    // Table Name
    public static final String TABLE_CONTACTS = "contacts";

    // Table Columns
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PHONE = "phone";
    public static final String COLUMN_COMP = "company";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_TITLE = "title";


    // Creating table query
    private static final String CREATE_TABLE_CONTACTS = "CREATE TABLE " + TABLE_CONTACTS + "("
            + COLUMN_PHONE + " TEXT PRIMARY KEY ,"
            + COLUMN_NAME + " TEXT,"
            + COLUMN_COMP + " TEXT,"
            + COLUMN_EMAIL + " TEXT,"
            + COLUMN_TITLE + " TEXT" + ")";

    public DBModel(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_TABLE_CONTACTS);
    }

    public String printData(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CONTACTS;
        Cursor cursor = db.rawQuery(query, null);
        String result = "";
        while(cursor.moveToNext()) {
            result += cursor.getString(0) + " " + cursor.getString(1) + " " + cursor.getString(2) + " " + cursor.getString(3) + " " + cursor.getString(4) + "\n";
        }
        cursor.close();
        db.close();
        return result;
    }

    public ArrayList<ContactItem> getContactList(){
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CONTACTS;
        Cursor cursor = db.rawQuery(query, null);
        ArrayList<ContactItem> contactList = new ArrayList<>();
        while(cursor.moveToNext()) {
            contactList.add(new ContactItem(cursor.getString(1), cursor.getString(0)));
        }
        cursor.close();
        db.close();
        return contactList;
    }

    public ContactVO getContact(String phone){
        if(phone.isEmpty()) {
            return null;
        }

        ContactVO contact = new ContactVO();
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CONTACTS + " WHERE phone = '" + phone + "'";
        try {
            Cursor cursor = db.rawQuery(query, null);
            if(cursor.getCount() == 0) {
                cursor.close();
                db.close();
                return null;
            }
            cursor.moveToFirst();
            contact.setName(cursor.getString(1));
            contact.setPhone(cursor.getString(0));
            contact.setEmail(cursor.getString(3));
            contact.setCompany(cursor.getString(2));
            contact.setTitle(cursor.getString(4));
            cursor.close();
        } catch (Exception e) {
            db.close();
            return null;
        }
        db.close();
        return contact;
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACTS);
        onCreate(db);
    }

    public int addContact(String name, String phone, String email, String company, String title) {
        if(phone.isEmpty() || name.isEmpty()) {
            return -1;
        }
        if(checkContactExists(phone)==1) {//exist
            return -2;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "INSERT INTO " + TABLE_CONTACTS + " (name, phone, email, company, title) VALUES ('" + name + "', '" + phone + "', '" + email + "', '" + company + "', '" + title + "')";
        try {
            db.execSQL(query);
        } catch (Exception e) {
            db.close();
            return -3;
        }
        db.close();
        return 0;
    }

    public int updateContact(ContactVO contact) {
        if(contact.getPhone().isEmpty() || contact.getName().isEmpty()) {
            return -1;
        }
        if(checkContactExists(contact.getPhone())==0) {//not exist
            return -2;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "UPDATE " + TABLE_CONTACTS + " SET name = '" + contact.getName() + "', email = '" + contact.getEmail() + "', company = '" + contact.getCompany() + "', title = '" + contact.getTitle() + "' WHERE phone = '" + contact.getPhone() + "'";
        try {
            db.execSQL(query);
        } catch (Exception e) {
            db.close();
            return -3;
        }
        db.close();
        return 0;
    }

    public int deleteContact(String phone) {
        // Delete record from database
        if(phone.isEmpty()) {
            return -1;
        }
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "DELETE FROM " + TABLE_CONTACTS + " WHERE phone = '" + phone + "'";
        try {
            db.execSQL(query);
        } catch (Exception e) {
            db.close();
            return -3;
        }
        db.close();
        return 0;
    }
    private int checkContactExists(String phone) {
        if(phone.isEmpty()) {
            return -1;
        }
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT * FROM " + TABLE_CONTACTS + " WHERE phone = '" + phone + "'";
        Cursor cursor = db.rawQuery(query, null);
        if(cursor.getCount() > 0) {
            cursor.close();
            db.close();
            return 1;
        }
        cursor.close();
        db.close();
        return 0;
    }


}