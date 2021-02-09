package com.example.testposition;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.GpsSatellite;
import android.location.GpsStatus;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.provider.Settings;
import androidx.core.app.ActivityCompat;
import android.util.Log;
import android.widget.Toast;

import com.kylindev.totalk.app.ControlActivity;

import java.util.Iterator;


public class GpsUtils {
    //位置管理器
    private static LocationManager manager;
   static Context s_context;

    /**
     * 初始化定位管理
     */
    public static void initLocation(Activity context) {
        s_context=context;
        manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
        //判断GPS是否正常启动
        if (!manager.isProviderEnabled(LocationManager.GPS_PROVIDER)) {
            Toast.makeText(context, "请开启GPS导航", Toast.LENGTH_SHORT).show();
            //返回开启GPS导航设置界面
            Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
            context.startActivityForResult(intent, 0);
            return;
        }

        //添加卫星状态改变监听
        if (ActivityCompat.checkSelfPermission(context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        manager.addGpsStatusListener(gpsStatusListener);
        //1000位最小的时间间隔，1为最小位移变化；也就是说每隔1000ms会回调一次位置信息
        manager.requestLocationUpdates(LocationManager.PASSIVE_PROVIDER, 200, 0.05f,loc);
        Log.e("wocao","request");
        Location location = manager.getLastKnownLocation(LocationManager.PASSIVE_PROVIDER);
//        if(location!=null){
//            TestActivity.getTestAct().updatePosition(location);
//        }


//
    }

    static LocationListener loc=new LocationListener() {
        @Override
        public void onLocationChanged(Location location) {
            Log.e("wocao","gps change");

            if(TestActivity.getTestAct()!=null){
                TestActivity.getTestAct().updatePosition(location);
            }

            if( PositionActivity.getTestPos()!=null){
                PositionActivity.getTestPos().updatePosition(location);
            }

            if(ControlActivity.getControl()!=null){
                //ControlActivity.getControl().updatePosition(location);
            }
        }

        @Override
        public void onStatusChanged(String provider, int status, Bundle extras) {

        }

        @Override
        public void onProviderEnabled(String provider) {

        }

        @Override
        public void onProviderDisabled(String provider) {

        }
    };

    public static GpsStatus.Listener gpsStatusListener = new GpsStatus.Listener() {
        @Override
        public void onGpsStatusChanged(int event) {
            Log.e("wocao","start");
            switch (event) {
                //卫星状态改变
                case GpsStatus.GPS_EVENT_SATELLITE_STATUS:
                    //获取当前状态
                    if (ActivityCompat.checkSelfPermission(s_context, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        // TODO: Consider calling
                        //    ActivityCompat#requestPermissions
                        // here to request the missing permissions, and then overriding
                        //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                        //                                          int[] grantResults)
                        // to handle the case where the user grants the permission. See the documentation
                        // for ActivityCompat#requestPermissions for more details.
                        return;
                    }
                    GpsStatus gpsStatus = manager.getGpsStatus(null);
                    //获取卫星颗数的默认最大值
                    int maxSatellites = gpsStatus.getMaxSatellites();
                    //获取所有的卫星
                    Iterator<GpsSatellite> iters = gpsStatus.getSatellites().iterator();
                    //卫星颗数统计
                    int count = 0;
                    StringBuilder sb = new StringBuilder();
                    int calibrated=0;
                    while (iters.hasNext() && count <= maxSatellites) {
                        count++;
                        GpsSatellite s = iters.next();
                        //卫星的信噪比
                        float snr = s.getSnr();
                        if(snr>0){
                         calibrated++;
                        }
//                        sb.append("第").append(count).append("颗").append("：").append(snr).append("\n");
//                        Log.e("wocao",sb.toString());
                    }
                    if(TestActivity.getTestAct()!=null){

                    TestActivity.getTestAct().updateSatellite(count,calibrated);

                    }
                    break;
                default:
                    break;
            }
        }
    };


}
