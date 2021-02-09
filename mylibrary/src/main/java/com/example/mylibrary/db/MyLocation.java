package com.example.mylibrary.db;

public class MyLocation {
    String longitude;
    String latitude;
    String position;
    String track;
    public MyLocation(String position,String track,String longitude, String latitude ) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.position=position;
        this.track=track;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getTrack() {
        return track;
    }

    public void setTrack(String track) {
        this.track = track;
    }

    @Override
    public String toString() {
        return "MyLocation{" +
                "longitude='" + longitude + '\'' +
                ", latitude='" + latitude + '\'' +
                ", position='" + position + '\'' +
                ", track='" + track + '\'' +
                '}';
    }
}
