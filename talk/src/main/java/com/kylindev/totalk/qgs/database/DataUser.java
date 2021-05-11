package com.kylindev.totalk.qgs.database;

/**
 * @date 2021/2/3 8:59
 * dmr  232数据保存bean类
 */
public class DataUser {
    private int id;
    private String time;
    private String signalling;

    public DataUser() {
        super();
    }

    public DataUser(int id, String time, String signalling) {
        this.id = id;
        this.time = time;
        this.signalling = signalling;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSignalling() {
        return signalling;
    }

    public void setSignalling(String signalling) {
        this.signalling = signalling;
    }
}
