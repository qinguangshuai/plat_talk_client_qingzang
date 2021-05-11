package com.kylindev.totalk.qgs.database.thirteen;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 人员号：13号
 * */
public class ThirteenDataOpenHelper extends SQLiteOpenHelper {

    public ThirteenDataOpenHelper(Context context) {
        /**
         * name 数据库的名字
         * factory 游标工厂  目的创建 cursor(结果集)
         * version 版本    版本从1开始  版本必须>=1
         */
        super(context, "thirteenperson_gps.db", null, 1);
    }

    /**
     * Called when the database is created for the first time
     * 当 数据第一次创建的时候调用
     * 该方法适合做 表结构的初始化
     */
    @Override
    public void onCreate(SQLiteDatabase db) {

        System.out.println("oncreate");
        //创建一个教info的表里面的字段有 id(android官方建议前面加下划线_) integer类型 主键  自动增长
        db.execSQL("create table thirteenperson(id integer primary key autoincrement,lat varchar(60),lon varchar(60))");

    }

    /**
     * 当数据库需要升级的时候调用
     * 该方法适合做 表结构修改
     *
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        System.out.println("onUpgrade");
        // 比如 我给 info 表添加一列   phone
//		db.execSQL("alter table info add phone varchar(20)");

    }


}
