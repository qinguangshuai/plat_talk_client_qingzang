package com.tencent.qcloud.tim.demo.Database;


import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import static androidx.room.OnConflictStrategy.REPLACE;

@Dao
public interface DiaodanDAO {
    @Query("SELECT * FROM diaodan")
    List<Diaodan> getAll();
    @Query("SELECT * FROM diaodan WHERE reportid = :reportId LIMIT 1")
    Diaodan findByID(int reportId);
    @Query("SELECT * FROM diaodan WHERE current_time = :current_time LIMIT 1")
    Diaodan findByDate(String current_time);
    @Insert
    void insertAll(Diaodan... diaodan);
    @Insert
    long insert(Diaodan diaodan);
    @Delete
    void delete(Diaodan diaodan);
    @Update(onConflict = REPLACE)
    public void updateDiaodan(Diaodan... diaodan);
    @Query("DELETE FROM diaodan")
    void deleteAll();
}
