package com.kylindev.totalk.qgs.tack;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 2021/03/03
 * 摘勾
 */
public class PickOpenHelper extends SQLiteOpenHelper {

    public PickOpenHelper(Context context) {
        /**
         * name 数据库的名字
         * factory 游标工厂  目的创建 cursor(结果集)
         * version 版本    版本从1开始  版本必须>=1
         */
        super(context, "zhaigouGPS_data.db", null, 1);
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
        db.execSQL("create table zhaigouGPS(id integer primary key autoincrement,lat varchar(200),lon varchar(200))");

    }

    /**
     * 当数据库需要升级的时候调用
     * 该方法适合做 表结构修改
     */
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        System.out.println("onUpgrade");
        // 比如 我给 info 表添加一列   phone
//		db.execSQL("alter table info add phone varchar(20)");

    }


}
