package com.kylindev.totalk.qgs.tack;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kylindev.totalk.qgs.PointOpenHelper;
import com.kylindev.totalk.qgs.SiveData;

import java.util.ArrayList;
import java.util.List;

/**
 * 2021/03/03
 * 摘勾
 */
public class PickDao {

    private PickOpenHelper helper;

    public PickDao(Context context) {
        helper = new PickOpenHelper(context);
    }

    /**
     * 数据库的增加方法
     */
    public boolean add(String lat, String lon) {
        SQLiteDatabase db = helper.getWritableDatabase();
//		db.execSQL("insert into info(name,phone) values(?,?)", new Object[]{NULL,phone});

        /**
         * table 表明
         * nullColumnHack  null
         */
        ContentValues values = new ContentValues();  //实际就是一个map    key:对应我们表的列名   value :值
        values.put("lat", lat);
        values.put("lon", lon);
        //实际底层原理 就是在组拼sql语句
        long result = db.insert("zhaigouGPS", null, values);
        db.close();
        if (result == -1) {
            //说明插入失败
            return false;
        } else {
            return true;
        }
    }

    /**
     * 数据库的删除方法
     */
    public int del(String name){
        SQLiteDatabase db = helper.getReadableDatabase();
//		db.execSQL("delete from info where name=?", new Object[]{name});

        /**
         * table 表名
         * whereClause  删除条件
         */
        //清空表数据
        //int delete = db.delete("zhaigouGPS", "name=?", new String[]{name});
        int delete = db.delete(name, null,null);
        db.close();
        return delete;
    }

    /**
     * 数据库的查询方法
     */
    public List<PickData> find() {
        List<PickData> personLists = new ArrayList<PickData>();
        SQLiteDatabase db = helper.getWritableDatabase();
//		Cursor cursor = db.rawQuery("select * from info", null);

        /**
         * table 表名
         * columns  查询的列  具体查询的是哪一列
         * selection 根据什么条件去
         * selectionArgs
         */
//		Cursor cursor = db.query("info", new String[]{"phone"}, "name=?", new String[]{name}, null, null, null);

        Cursor cursor = db.query("zhaigouGPS", null, null, null, null, null, null);
        //对cursor 判断一下cursor
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                // 开始取 字段数据   sqliet 不区分数据类型  想要什么你就自己拿什么
                int anInt = cursor.getInt(0);
                String lat = cursor.getString(1);   //获取我们的phone
                String lon = cursor.getString(2);    //获取我们的name值
                PickData pickData = new PickData();

                System.out.println("lat1--" + lat + "lon1--" + lon);
                pickData.setLat(lat);
                pickData.setLon(lon);
                // 把Person对象 加入到 personLists集合中.
                personLists.add(pickData);
            }
            cursor.close();
            db.close();
        }
        return personLists;
    }
}
