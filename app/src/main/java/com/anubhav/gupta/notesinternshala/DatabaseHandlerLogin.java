package com.anubhav.gupta.notesinternshala;
import java.util.ArrayList;
import java.util.List;
 
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
 
 class DatabaseHandlerLogin extends SQLiteOpenHelper {
 

    private static final int DATABASE_VERSION = 1;
 

    private static final String DATABASE_NAME = "loginManager";
 

    private static final String TABLE_LOGIN_DATA = "login_data";


    private static final String KEY_UID = "user_id";
    private static final String KEY_PASS = "user_pass";


 
     DatabaseHandlerLogin(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);

    }
 

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_LOGIN_TABLE = "CREATE TABLE " + TABLE_LOGIN_DATA + "("
                + KEY_UID + " TEXT UNIQUE," + KEY_PASS + " TEXT"
                + ")";
        db.execSQL(CREATE_LOGIN_TABLE);
    }
 

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + TABLE_LOGIN_DATA);
        onCreate(db);
    }
 

    void addUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_UID, user.getUser());
        values.put(KEY_PASS, user.getPass());
 

        db.insert(TABLE_LOGIN_DATA, null, values);
        db.close();
    }
 

    User getUser(String id1,String id2) {
        SQLiteDatabase db = this.getReadableDatabase();
 
        Cursor cursor = db.query(TABLE_LOGIN_DATA, new String[] { KEY_UID,
                KEY_PASS }, KEY_UID + "=?" +"AND "+KEY_PASS+"=?",
                new String[] { String.valueOf(id1) ,String.valueOf(id2)}, null, null, null, null);
        if (cursor != null)
            cursor.moveToFirst();
        User myuser=new User(null,null);

String u,p;
        if (cursor != null) {
            try {
                u = cursor.getString(0);
                p = cursor.getString(0);

                myuser = new User(u ,p);
            }
            catch (Exception e)
            {
                myuser=null;
            }
        }

        cursor.close();

        return myuser;
    }
 

 

    int updateUser(User user) {
        SQLiteDatabase db = this.getWritableDatabase();
 
        ContentValues values = new ContentValues();
        values.put(KEY_UID, user.getUser());
        values.put(KEY_PASS, user.getPass());
 

        return db.update(TABLE_LOGIN_DATA, values, KEY_UID + " = ?",
                new String[] { String.valueOf(user.getUser()) });
    }

    List<User> getAllUsers() {
        List<User> itemList = new ArrayList<>();

        String selectQuery = "SELECT  * FROM " + TABLE_LOGIN_DATA;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);


        if (cursor.moveToFirst()) {
            do {
                User myuser = new User(cursor.getString(0),cursor.getString(1));

                itemList.add(myuser);
            } while (cursor.moveToNext());
        }


        return itemList;
    }
 
}