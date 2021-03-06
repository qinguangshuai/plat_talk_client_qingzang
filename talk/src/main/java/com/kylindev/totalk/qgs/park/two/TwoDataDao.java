package com.kylindev.totalk.qgs.park.two;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.kylindev.totalk.qgs.park.DataUser;

import java.util.ArrayList;
import java.util.List;

/**
 * @date 2021/2/3 9:01
 * 1道无数据时摘挂钩保存
 */
public class TwoDataDao {

    private TwoParkOpenHelper helper;

    public TwoDataDao(Context context) {
        helper = new TwoParkOpenHelper(context);
    }

    /**
     * 数据库的增加方法
     */
    public boolean add(String gd,String lat, String lon,String ratioOfGpsPointCar) {
        SQLiteDatabase db = helper.getWritableDatabase();
//		db.execSQL("insert into info(name,phone) values(?,?)", new Object[]{NULL,phone});

        /**
         * table 表明
         * nullColumnHack  null
         */
        ContentValues values = new ContentValues();  //实际就是一个map    key:对应我们表的列名   value :值
        values.put("gd", gd);
        values.put("lat", lat);
        values.put("lon", lon);
        values.put("ratioOfGpsPointCar", ratioOfGpsPointCar);
        //实际底层原理 就是在组拼sql语句
        long result = db.insert("twoparkcar", null, values);
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
        //代表 影响了多少行
        int delete = db.delete(name, null,null);
        db.close();
        return delete;
    }

    /**
     * 数据库的查询方法
     */
    public List<DataUser> find() {
        List<DataUser> personLists = new ArrayList<DataUser>();
        SQLiteDatabase db = helper.getWritableDatabase();
//		Cursor cursor = db.rawQuery("select * from info", null);

        /**
         * table 表名
         * columns  查询的列  具体查询的是哪一列
         * selection 根据什么条件去
         * selectionArgs
         */
//		Cursor cursor = db.query("info", new String[]{"phone"}, "name=?", new String[]{name}, null, null, null);

        Cursor cursor = db.query("twoparkcar", null, null, null, null, null, null);
        //对cursor 判断一下cursor
        if (cursor != null && cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                // 开始取 字段数据   sqliet 不区分数据类型  想要什么你就自己拿什么
                int anInt = cursor.getInt(0);
                String gd = cursor.getString(1);   //获取我们的phone
                String lat = cursor.getString(2);   //获取我们的phone
                String lon = cursor.getString(3);    //获取我们的name值
                String ratioOfGpsPointCar = cursor.getString(4);    //获取我们的name值
                DataUser dataUser = new DataUser();

                System.out.println("time--" + lat + "signalling--" + lon);
                dataUser.setId(anInt);
                dataUser.setGd(gd);
                dataUser.setLat(lat);
                dataUser.setLon(lon);
                dataUser.setRatioOfGpsPointCar(ratioOfGpsPointCar);
                // 把Person对象 加入到 personLists集合中.
                personLists.add(dataUser);
            }
            cursor.close();
            db.close();
        }
        return personLists;
    }
}
