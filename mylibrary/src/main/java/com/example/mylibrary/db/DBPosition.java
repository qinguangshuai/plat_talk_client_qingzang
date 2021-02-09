package com.example.mylibrary.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;



import java.util.ArrayList;

public class DBPosition implements DB{
    public static DBPosition instance = null;
    DBHelper dbHelper;
    String TAG="DBPosition";
    public static synchronized DBPosition getInstance(Context context) {
        if (instance == null) {
            instance = new DBPosition(context);
        }
        return instance;
    }

    public DBPosition(Context context) {
        dbHelper=DBHelper.getInstance(context);

    }

    @Override
    public void onUpgrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
//        if (oldVersion < 2 && newVersion >= 2) {
            onCreate(db);
//        }
    }

    @Override
    public void onDowngrade(final SQLiteDatabase db, final int oldVersion, final int newVersion) {
            onCreate(db);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("DROP TABLE IF EXISTS LOCATION ");
        StringBuilder sb = new StringBuilder();

        sb.append("CREATE TABLE ")
                .append("LOCATION").append("(")
                .append("NAME").append(" TEXT,")
                .append("TRACK").append(" TEXT,")
                .append("LON").append(" TEXT,")
                .append("LA").append(" TEXT)");
        db.execSQL(sb.toString());
        Log.e("wocao","create suc");
    }

    public synchronized boolean insert(MyLocation location){
        SQLiteDatabase database=dbHelper.getWritableDatabase();
        ContentValues values=new ContentValues();
        values.put("NAME",location.getPosition());
        values.put("TRACK",location.getTrack());
        values.put("LON",location.getLongitude());
        values.put("LA",location.getLatitude());
        if (database.isOpen()){

           Long a=database.insert("LOCATION",null,values);
            return a==1;
        }else {
            return false;
        }

    }


    public synchronized boolean delete(String name){
        SQLiteDatabase database=dbHelper.getWritableDatabase();
        if(database.isOpen()){
            if(database.delete("LOCATION","NAME=?",new String []{name})==1){
                return true;
            }else {
                return false;
            }
        }
        return false;
    }



    public MyLocation select1(String name){
        Log.e(TAG,"exc sel1");
        SQLiteDatabase database=dbHelper.getReadableDatabase();
        String sql="SELECT * FROM LOCATION WHERE NAME=?";
        Cursor cursor=database.rawQuery(sql,new String[]{name});
        if (cursor!=null) {
            if(cursor.moveToNext()){
                MyLocation location=new MyLocation(cursor.getString(0),
                        cursor.getString(1),cursor.getString(2),cursor.getString(3));
                return location;
            }
        }

        return null;
    }

    public ArrayList<MyLocation> selectAll(){
        ArrayList<MyLocation> locs=new ArrayList<>();
        Log.e(TAG,"exc sel1");
        SQLiteDatabase database=dbHelper.getReadableDatabase();
        String sql="SELECT * FROM LOCATION";
        Cursor cursor=database.rawQuery(sql,null);

            while(cursor.moveToNext()){
                MyLocation location=new MyLocation(  cursor.getString(0),
                        cursor.getString(1),cursor.getString(2)
                        ,cursor.getString(3));
                locs.add(location);
            }
            Log.e("wocao","locs"+locs.size());
        return locs;
    }

    public ArrayList<String> selectTracks(){
        ArrayList<String> tracks=new ArrayList<>();
        SQLiteDatabase database=dbHelper.getReadableDatabase();
        String sql="SELECT TRACK FROM LOCATION ";
        Cursor cursor=database.rawQuery(sql,null);

        while (cursor.moveToNext()){
            tracks.add(cursor.getString(0));
        }
        return tracks;
    }

    public ArrayList<MyLocation> selectByTrack(String track){
        ArrayList<MyLocation> locs=new ArrayList<>();
        SQLiteDatabase database=dbHelper.getReadableDatabase();
        String sql="SELECT * FROM LOCATION where track=?";
        Cursor cursor=database.rawQuery(sql,new String[]{track});

        while(cursor.moveToNext()){
            MyLocation location=new MyLocation(cursor.getString(0),
                    cursor.getString(1),cursor.getString(2)
                    ,cursor.getString(3));
            locs.add(location);
        }
        return locs;
    }

}
