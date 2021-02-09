package com.tencent.qcloud.tim.demo.Database;


import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class Diaodan {

    @PrimaryKey(autoGenerate = true)
    public Integer reportid;
    @ColumnInfo(name = "str")
    public String str;
    @ColumnInfo(name = "current_time")
    public String current_time;
    @ColumnInfo(name = "danhao")
    public String danhao;
    @ColumnInfo(name = "gou_number")
    public String gou_number;
    public Diaodan(String str, String current_time, String danhao, String gou_number) {
        this.str=str;
        this.current_time=current_time;
        this.danhao=danhao;
        this.gou_number=gou_number;
    }

    public int getReportsid() {
        return reportid;
    }

    public String getStr() {
        return str;
    }
    public void setStr(String str) {
        this.str = str;
    }

    public String getCurrent_time() {
        return current_time;
    }
    public void setCurrent_time(String current_time) {
        this.current_time = current_time;
    }

    public String getDanhao() {
        return danhao;
    }
    public void setDanhao(String danhao) {
        this.danhao = danhao;
    }

    public String getGou_number() {
        return gou_number;
    }
    public void setGou_number(String gou_number) {
        this.gou_number = gou_number;
    }
}
