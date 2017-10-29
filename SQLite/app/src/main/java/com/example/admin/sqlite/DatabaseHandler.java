package com.example.admin.sqlite;


import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;


/**
 * Created by ADMIN on 29-10-2017.
 */

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DB_VERSION = 1;
    private static final String DB_NAME = "MyContacts";
    private static final String TABLE = "contacts";

    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String PHONE = "phone_number";

    public DatabaseHandler(Context context){
        super(context,DB_NAME,null,DB_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db1){
        String CREATE_TABLE = "CREATE TABLE " + TABLE + "(" + ID + " INTEGER PRIMARY KEY," + NAME + " TEXT,"
                + PHONE + " TEXT )";
        db1.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db1,int oldVersion,int newVersion){
        db1.execSQL("DROP TABLE IF EXISTS " + TABLE);
        onCreate(db1);
    }

     void addContact(Contacts contacts){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,contacts.getName());
        contentValues.put(PHONE,contacts.getPhone());

        db.insert(TABLE,null,contentValues);
        db.close();
    }

     Contacts getContact(int id){
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE,new String[] {ID,NAME,PHONE},ID + "=?",new String[]{String.valueOf(id)},null,null,null,null);
        if(cursor!=null){
            cursor.moveToFirst();

        }
        Contacts contacts = new Contacts(Integer.parseInt(cursor.getString(0)),
                cursor.getString(1),cursor.getString(2));
        return contacts;
    }

    public List<Contacts> getAllContacts(){
        List<Contacts> contactsList = new ArrayList<Contacts>();
        String query = "SELECT * FROM " + TABLE;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(query,null);

        if(cursor.moveToFirst()){
            do{
                Contacts contacts = new Contacts();
                contacts.setId(Integer.parseInt(cursor.getString(0)));
                contacts.setName(cursor.getString(1));
                contacts.setPhone(cursor.getString(2));
                contactsList.add(contacts);
            }
            while (cursor.moveToNext());
        }
        return contactsList;
    }

    public int getCountContacts(){
        String query = "SELECT * FROM " + TABLE;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(query,null);
        int count = cursor.getCount();

        return count;
    }

    public int updateContact(Contacts contacts){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(NAME,contacts.getName());
        contentValues.put(PHONE,contacts.getPhone());

        return db.update(TABLE,contentValues,ID + "=?",new String[]{String.valueOf(contacts.getId())});
    }

    public int deleteContact(int id){
        SQLiteDatabase db = getWritableDatabase();
        int status = db.delete(TABLE,ID + "=?",new String[]{String.valueOf(id)});
        db.close();
        return status;
    }

}

