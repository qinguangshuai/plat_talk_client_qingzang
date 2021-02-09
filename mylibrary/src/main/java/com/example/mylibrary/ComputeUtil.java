package com.example.mylibrary;

import android.location.Location;
import android.util.Log;

import com.example.mylibrary.db.MyLocation;

import java.util.ArrayList;

public class ComputeUtil {
    private static double EARTH_RADIUS =6378137;
    private static double rad(double d)
    {
        return d * Math.PI / 180.0;
    }

    public static double GetDistance(double lat1, double lng1, double lat2, double lng2)
    {
        double radLat1 = rad(lat1);
        double radLat2 = rad(lat2);
        double a = radLat1 - radLat2;
        double b = rad(lng1) - rad(lng2);
        double s = 2 * Math.asin(Math.sqrt(Math.abs(Math.pow(Math.sin(a/2),2) +
                Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2))));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000);
        s=s/10000;
        return s;
    }


    public static double juli(double x0, double y0, double x1, double y1, double x2, double y2)
    {
        double jl = 0.0, A = 0.0, B = 0.0, C = 0.0, d = 0.0, d1 = 0.0, d2 = 0.0, x = 0.0, y = 0.0;
        A = y2 - y1;
        B = x1 - x2;
        C = x2 * y1 - x1 * y2;
        x = (B * B * x0 - A * B * y0 - A * C) / (A * A + B * B);         //垂足坐标
        y = (A * A * y0 - A * B * x0 - B * C) / (A * A + B * B);         //垂足坐标

        d = Math.abs((A * x0 + B * y0 + C) / Math.sqrt(A * A + B * B));  //点到直线距离
        d1 = Math.sqrt((x0 - x1) * (x0 - x1) + (y0 - y1) * (y0 - y1));   //点到点距离
        d2 = Math.sqrt((x0 - x2) * (x0 - x2) + (y0 - y2) * (y0 - y2));   //点到点距离

        if ((x <= Math.max(x1, x2)) || (x >= Math.min(x1, x2)))
        {
            jl = d;
        }
        else
        {
            jl = Math.min(d1, d2);
        }
        return jl;
    }


    public static double gudaojuli(Location location, ArrayList<MyLocation> locs)
    {

        double jl = 1.0, dd = 0.0;
        int i;

        for (i = 0; i < locs.size() - 1; i++)
        {
            Log.e("wocao",locs.get(i).toString());
            dd = GpsToDistance(location.getLongitude(), location.getLatitude(),
                    Double.valueOf(locs.get(i).getLongitude()), Double.valueOf(locs.get(i).getLatitude()),
                    Double.valueOf(locs.get(i+1).getLongitude()),Double.valueOf( locs.get(i+1).getLatitude()));
            if (dd <= jl)
            {
                jl = dd;
            }
        }

        return jl;
    }


    public static double GpsToDistance(double x0, double y0, double x1, double y1, double x2, double y2)
    {
        double R1, R2, a1, a2, b1, b2, c1, c2, xx1, xx2, yy1, yy2;

        R1 = 6377830;   //赤道半径
        R2 = 6356909;   //两极半径

        //经纬度之差
        a1 = x1 - x0;
        a2 = x2 - x0;
        b1 = y1 - y0;
        b2 = y2 - y0;


        //经纬度1度对应的距离
        c1 = 2 * Math.PI * R2 / 360.0;
        c2 = 2 * Math.PI * R1 * Math.cos(x0 * Math.PI / 180.0) / 360.0;

        //以米为单位的新坐标
        xx1 = a1 * c1;
        xx2 = a2 * c1;
        yy1 = b1 * c2;
        yy2 = b2 * c2;

        return juli(0d, 0d, xx1, yy1, xx2, yy2);

    }



}
