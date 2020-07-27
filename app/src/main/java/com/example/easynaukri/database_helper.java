package com.example.easynaukri;

import android.annotation.SuppressLint;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.provider.ContactsContract;
import android.text.BoringLayout;

import androidx.annotation.Nullable;

public class database_helper extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "userdatabasetable";
    private static final String TABLE_USER = "user";
    private static final String KEY_USERNAME = "username";
    private static String KEY_EMAIL = "email";
    private static final String KEY_PASSWORD = "password";
    private static final String KEY_PH_NO = "phone";
    public database_helper(@Nullable Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_USER_TABLE = "CREATE TABLE " + TABLE_USER + "(username varchar,email varchar,password varchar,phone varchar  )";
        db.execSQL("CREATE TABLE " + TABLE_USER + "(username varchar,email varchar,password varchar,phone varchar  )");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_USER);
        onCreate(db);
    }
    public boolean insertdata(String username,String email,String password,String number){
        SQLiteDatabase database=this.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put(KEY_USERNAME,username);
        values.put(KEY_EMAIL,email);
        values.put(KEY_PASSWORD,password);
        values.put(KEY_PH_NO,number);
        long result=database.insert(TABLE_USER,null,values);
        if(result==-1){
            return false;
        }
        else
            return true;
    }

   public Cursor showuserdata(String passemail,SQLiteDatabase database){
        String[] projections={KEY_USERNAME};
        String selection= KEY_EMAIL+"LIKE ?";
        String[] selectionagrs={passemail};
        String mail=passemail;
        //Cursor res=database.query(TABLE_USER,projections,selection,selectionagrs,null,null,null);
         Cursor res=database.rawQuery("SELECT username FROM user WHERE email = :mail LIMIT 1",null);
        return res;
   }
    public Cursor showdata(){
        SQLiteDatabase database=this.getWritableDatabase();
        Cursor res=database.rawQuery("select * from "+ TABLE_USER,null);
        return res;
    }
 public String username(String Email,String  password) {
     String found = "NOTHING ";
     String query = "select username from user where email"+"=?"+" and password"+"=?";
     SQLiteDatabase database = this.getWritableDatabase();
     String[] args={Email,password};
     String[] column={KEY_USERNAME};
     String selection=KEY_EMAIL+"=?"+KEY_PASSWORD+"=?";
     String[] selectionargs={Email,password};
     //Cursor cursor=database.query(TABLE_USER,column,selection,selectionargs,null,null,null);
     Cursor cursor = database.rawQuery(query, args);
     if (cursor.moveToNext()) {
         found = cursor.getString(cursor.getColumnIndex("username"));
     }
     return found;

 }
 public Boolean checkmail(String Email)
 {
     SQLiteDatabase database=this.getReadableDatabase();
     String query="select username from user where email"+"=?";
     String[] args={Email};

   //  String[] column={KEY_USERNAME};
    // String selection=KEY_EMAIL+"=?"+KEY_PASSWORD+"=?";
     //String[] selectionargs={email};
     Cursor cursor=database.rawQuery(query,args);
     database.close();
     //Cursor cursor=database.query(TABLE_USER,column,selection,selectionargs,null,null,null);
     int count=cursor.getCount();
     if(count>0) {
         return true;
     }
     else {
         return false;
     }
 }




}
