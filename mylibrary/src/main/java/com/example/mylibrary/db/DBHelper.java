package com.example.mylibrary.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by mac-yk on 2017/5/4.
 */

public class DBHelper extends SQLiteOpenHelper {
    static final int DBVersion=2;
    static DBHelper instance;
    static final String DB_NAME = "app.DB";
    Context context;
    public DBHelper(Context context) {
        super(context,DB_NAME, null, DBVersion);
        this.context=context;
    }
    public static synchronized DBHelper getInstance(Context context){
        if (instance==null){
            instance=new DBHelper(context);

        }
        return instance;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        DBPosition.getInstance(context).onCreate(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("wocao","sudu");
        DBPosition.getInstance(context).onUpgrade(db,oldVersion,newVersion);
    }

    @Override
    public void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        DBPosition.getInstance(context).onDowngrade(db, oldVersion, newVersion);
    }
}
