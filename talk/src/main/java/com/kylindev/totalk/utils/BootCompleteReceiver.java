package com.kylindev.totalk.utils;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.util.Log;
import android.widget.Toast;

import com.kylindev.pttlib.LibConstants;
import com.kylindev.pttlib.service.InterpttService;
import com.kylindev.totalk.MainApp;

import static com.kylindev.pttlib.LibConstants.ACTION_AUTO_LAUNCH;


public class BootCompleteReceiver extends BroadcastReceiver {
	@Override
	public void onReceive(final Context context, Intent intent) {
		boolean auto = AppSettings.getInstance(context).getAutoLaunch();
		boolean isTotalkHardware = (Build.MODEL!=null && Build.MODEL.startsWith("TOTALK_"));

		if (auto || isTotalkHardware) {
			//延时一段时间再启动
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						Thread.sleep(5000);
						Intent serviceIntent = new Intent(context, InterpttService.class);

						//自动启动service，在Service的实现里判断，如果是自动启动的，则自动登录
						serviceIntent.setAction(ACTION_AUTO_LAUNCH);

						context.startService(serviceIntent);
					} catch (Exception e) {

					}
				}
			}).start();
		}
	}
}