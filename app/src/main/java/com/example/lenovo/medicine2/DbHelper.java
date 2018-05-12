package com.example.lenovo.medicine2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by LENOVO on 4/2/2018.
 */

public class DbHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "Medicine Alarms";
    public DbHelper(Context context){
        super(context, DATABASE_NAME, null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table if not exists medicine(_id integer not null primary key Autoincrement unique, id integer, name text, dose text, time text, interval text, status text)");
        }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int oldVersion, int newVersion) {
        sqLiteDatabase.execSQL("drop table if exists medicine");
        onCreate(sqLiteDatabase);
    }
    public void addMedicine(int id, String name, String dose, String time, String interval, String status){
        ContentValues values = new ContentValues(6);
        values.put("id", id);
        values.put("name", name);
        values.put("dose", dose);
        values.put("time", time);
        values.put("interval", interval);
        values.put("status", status);
        getWritableDatabase().insert("medicine", "name", values);
    }
    public Cursor getAllMedicine(){
        return getReadableDatabase().rawQuery("select * from medicine", null);
    }
    public void deleteMedicine(String name){
//        String[] x = {name};
        getWritableDatabase().delete("medicine", "name = ?",new String[]{name});
//        getWritableDatabase().execSQL("delete from medicine where name='"+name+"'", null);
//        return getWritableDatabase().delete("medicine","name",x) > 0;
    }
    public int getMedicineId(String name){
//        return getReadableDatabase().rawQuery("select id from medicine where name='"+name+"'", null).getInt(0);
//        return getReadableDatabase().rawQuery("select id from medicine where name = ?",new String[]{name});
        SQLiteDatabase db = getReadableDatabase();
        if (db==null){
            return 0;
        }
        ContentValues row= new ContentValues();
        Cursor cursor = db.rawQuery("select id from medicine where name=?", new String[]{String.valueOf(name)});
        if (cursor.moveToNext()){
            row.put("id", cursor.getInt(0));
        }
        return cursor.getInt(0);
    }

}
