package com.kylindev.totalk.qgs.database.five;

/**
 * @date 2021/2/3 8:59
 * 人员定位公用bean类
 */
public class StopDataUser {
    private int id;
    private String gd;
    private String lat;
    private String lon;
    private String time;

    public StopDataUser() {
        super();
    }

    public StopDataUser(int id, String gd, String lat, String lon, String time) {
        this.id = id;
        this.gd = gd;
        this.lat = lat;
        this.lon = lon;
        this.time = time;
    }

    public String getGd() {
        return gd;
    }

    public void setGd(String gd) {
        this.gd = gd;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLon() {
        return lon;
    }

    public void setLon(String lon) {
        this.lon = lon;
    }
}
