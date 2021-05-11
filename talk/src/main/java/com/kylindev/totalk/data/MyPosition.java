package com.kylindev.totalk.data;

public class MyPosition {
    Double longitude;
    Double latitude;
    int track;

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Integer getTrack() {
        return track;
    }

    public void setTrack(int track) {
        this.track = track;
    }

    public MyPosition(int track, Double longitude, Double latitude) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.track = track;
    }
}
