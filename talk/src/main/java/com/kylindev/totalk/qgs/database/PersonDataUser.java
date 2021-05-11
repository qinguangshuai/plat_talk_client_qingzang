package com.kylindev.totalk.qgs.database;

/**
 * @date 2021/2/3 8:59
 * 人员定位公用bean类
 */
public class PersonDataUser {
    private int id;
    private String lat;
    private String lon;

    public PersonDataUser() {
        super();
    }

    public PersonDataUser(int id, String lat, String lon) {
        this.id = id;
        this.lat = lat;
        this.lon = lon;
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
