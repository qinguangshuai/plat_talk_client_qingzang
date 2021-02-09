package com.example.mylibrary.db;

import android.database.sqlite.SQLiteDatabase;

/**
 * Created by mac-yk on 2017/5/4.
 */

public interface DB {
    void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion);
    void onDowngrade(SQLiteDatabase db, int oldVersion, int newVersion);
    void onCreate(SQLiteDatabase db);
}
