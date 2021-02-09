package com.tencent.qcloud.tim.demo.qingzang;

import android.annotation.SuppressLint;
import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.os.IBinder;

import com.tencent.qcloud.tim.demo.R;

import static androidx.core.app.NotificationCompat.PRIORITY_MAX;

public class BackGroundService extends Service {
 
    Notification notification;
    private Context mContext;
    private static Thread uploadGpsThread;
    private MediaPlayer bgmediaPlayer;
    private boolean isrun = true;

    public void MyService() {
    }
 
    @SuppressLint("WrongConstant")
    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
 
        mContext = this;
 
        Intent notificationIntent = new Intent(this, SendActivity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(mContext, 0,
        notificationIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        //1.通知栏占用，不清楚的看官网或者音乐类APP的效果
        notification = new Notification.Builder(mContext)
                .setSmallIcon(R.drawable.app_icon)
                .setWhen(System.currentTimeMillis())
                .setTicker("测试")
                .setContentTitle("GPS测试标题")
                .setContentText("GPS测试内容")
                .setOngoing(true)
                .setPriority(PRIORITY_MAX)
                .setContentIntent(pendingIntent)
                .setAutoCancel(false)
                .build();
        /*使用startForeground,如果id为0，那么notification将不会显示*/
        startForeground(0, notification);
 
        //2.开启线程（或者需要定时操作的事情）
        if(uploadGpsThread == null){
            uploadGpsThread = new Thread(new Runnable() {
                @Override
                public void run() {
                    //这里用死循环就是模拟一直执行的操作
                    while (isrun){
                        //你需要执行的任务
                        /*doSomething();*/

                        try {
                            Thread.sleep(10000L);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }
            });
        }
 
        //3.最关键的神来之笔，也是最投机的动作，没办法要骗过CPU
        //这就是播放音乐类APP不被杀的做法，自己找个无声MP3放进来循环播放
        if(bgmediaPlayer == null){
            bgmediaPlayer = MediaPlayer.create(this,R.raw.wusheng);
            bgmediaPlayer.setLooping(true);
            bgmediaPlayer.start();
        }
 
        return START_STICKY;
    }
 
    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
 
    @Override
    public void onDestroy() {
        isrun = false;
        stopForeground(true);
        bgmediaPlayer.release();
        stopSelf();
        super.onDestroy();
    }
}