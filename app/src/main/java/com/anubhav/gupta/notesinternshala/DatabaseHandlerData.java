package com.anubhav.gupta.notesinternshala;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHandlerData extends SQLiteOpenHelper {


    private static final int DATABASE_VERSION = 1;


    private static final String DATABASE_NAME = "notesManager";



    private static final String TABLE_NOTES_DATA = "notes_data";


    private static final String KEY_ID = "text_id";
    private static final String KEY_TEXT = "text_data";
    private static final String KEY_USER = "user_data";




     DatabaseHandlerData(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_NOTES_TABLE = "CREATE TABLE " + TABLE_NOTES_DATA + "("
                + KEY_ID + " INTEGER," + KEY_TEXT + " TEXT,"
                + KEY_USER + " TEXT" + ")";
        Log.i("abc",CREATE_NOTES_TABLE);
        db.execSQL(CREATE_NOTES_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NOTES_DATA);
 
        onCreate(db);
    }
 

   void additem(itemData item) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_ID, item.getCount());
        values.put(KEY_TEXT, item.getText());
        values.put(KEY_USER,item.getUser());

        db.insert(TABLE_NOTES_DATA, null, values);

        db.close();
    }

    itemData gettem(int id1) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NOTES_DATA, new String[] { KEY_ID,
                        KEY_TEXT }, KEY_ID + "=?",
                new String[] { String.valueOf(id1)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        itemData myItem=new itemData(0,null,null);

        String t,u;
        int n;
        if (cursor != null) {
            try {
                n = cursor.getInt(0);
                t = cursor.getString(1);
                u = cursor.getString(2);

                myItem = new itemData(n ,t,u);
            }
            catch (Exception e)
            {
                myItem=null;
            }
        }

        assert cursor != null;
        cursor.close();



        return myItem;
    }

    public List<itemData> getAllNotes() {

        List<itemData> itemList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_NOTES_DATA+" WHERE "+KEY_USER+" = \""+List_Main.currentUser.getUser()+"\"";
 
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
 

        if (cursor.moveToFirst()) {
            do {
                itemData item = new itemData(Integer.parseInt(cursor.getString(0)),cursor.getString(1),cursor.getString(2));

              itemList.add(item);
            } while (cursor.moveToNext());
        }


        return itemList;
    }
 

     int updateNote(itemData item) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_ID, item.getCount());
        values.put(KEY_TEXT, item.getText());
        values.put(KEY_USER,item.getUser());

        return db.update(TABLE_NOTES_DATA, values, KEY_ID + " = ?",
                new String[] { String.valueOf(item.getCount()) });
    }
     void updateAllNotes(List<itemData> list)
    {SQLiteDatabase db = this.getWritableDatabase();
       db.execSQL("DELETE FROM " + TABLE_NOTES_DATA +" WHERE "+KEY_USER+" = \""+List_Main.currentUser.getUser()+"\"");
        for (itemData item : list )
        {

            additem(item);

        }
    }

    public void deleteNote(itemData item) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NOTES_DATA, KEY_ID + " = ?",
                new String[] { String.valueOf(item.getCount()) });
        db.close();
    }
 

 
}