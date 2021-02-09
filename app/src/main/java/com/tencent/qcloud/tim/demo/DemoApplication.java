package com.tencent.qcloud.tim.demo;

import android.annotation.SuppressLint;
import android.app.Activity;

import androidx.multidex.MultiDexApplication;
import androidx.room.Room;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import androidx.multidex.MultiDex;

import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.mylibrary.RouterURLS;
import com.example.mylibrary.TestService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.huawei.android.hms.agent.HMSAgent;
import com.kylindev.totalk.app.BaseActivity;
import com.kylindev.totalk.bjxt.SpUtil;
import com.kylindev.totalk.qgs.PointActivity;
import com.meizu.cloud.pushsdk.PushManager;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.imsdk.TIMBackgroundParam;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMOfflinePushNotification;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.session.SessionWrapper;
import com.tencent.imsdk.utils.IMFunc;
import com.tencent.qcloud.tim.demo.Database.Diaodan;
import com.tencent.qcloud.tim.demo.Database.DiaodanDatabase;
import com.tencent.qcloud.tim.demo.bjxt.sqlite.ReceiveDao;
import com.tencent.qcloud.tim.demo.bjxt.sqlite.SendDao;
import com.tencent.qcloud.tim.demo.bjxt.sqlite.SiveDao;
import com.tencent.qcloud.tim.demo.bjxt.sqlite.SiveData;
import com.tencent.qcloud.tim.demo.helper.ConfigHelper;
import com.tencent.qcloud.tim.demo.helper.CustomAVCallUIController;
import com.tencent.qcloud.tim.demo.helper.CustomMessage;
import com.tencent.qcloud.tim.demo.qingzang.SendActivity;
import com.tencent.qcloud.tim.demo.signature.GenerateTestUserSig;
import com.tencent.qcloud.tim.demo.thirdpush.ThirdPushTokenMgr;
import com.tencent.qcloud.tim.demo.utils.CombineCommend;
import com.tencent.qcloud.tim.demo.utils.DemoLog;
import com.tencent.qcloud.tim.demo.utils.HexUtil;
import com.tencent.qcloud.tim.demo.utils.PrivateConstants;
import com.tencent.qcloud.tim.uikit.TUIKit;
import com.tencent.qcloud.tim.uikit.base.IMEventListener;
import com.vivo.push.PushClient;
import com.xiaomi.mipush.sdk.MiPushClient;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Set;

import io.github.prototypez.appjoint.core.ServiceProvider;
import util.MyUtil;

import static com.tencent.qcloud.tim.demo.qingzang.SendActivity.xintiao_index;

@ServiceProvider
public class DemoApplication extends MultiDexApplication implements TestService {

    private static final String TAG = DemoApplication.class.getSimpleName();

    private static DemoApplication instance;
    private String mId;
    DiaodanDatabase db = null;
    private String mPeopleId;
    private SpUtil mSpUtil;
    private String mPeople;

    public static DemoApplication instance() {
        return instance;
    }

    public SoundPool soundPool;
    Context context;
    CombineCommend combineCommend = new CombineCommend();
    SendActivity sendActivity = new SendActivity();
    public String input = "", danhao = "";
    //public static List<String> gousumit_list = new ArrayList<>();
    private String[] alpha = new String[200];
    private String dateString = "";

    @SuppressLint("HandlerLeak")
    private Handler THandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            DemoApplication.instance().sendMessage(mId, "123");
            //Toast.makeText(getApplicationContext(), "DemoApplication.class", Toast.LENGTH_SHORT).show();
        }
    };

    private class InsertDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            Diaodan diaodan = new Diaodan(input, dateString, danhao, "");
            db.DiaodanDAO().insert(diaodan);
            return "";
        }

        @Override
        protected void onPostExecute(String details) {
        }
    }

    public Handler handler = new Handler();

    @Override
    public void onCreate() {
        DemoLog.i(TAG, "onCreate");
        super.onCreate();
        instance = this;

        context = this;
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);

        db = Room.databaseBuilder(context,
                DiaodanDatabase.class, "Diaodan_Database")
                .fallbackToDestructiveMigration()
                .build();

        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this);
        Log.e("wocao", "init");

        MultiDex.install(this);

        //THandle.sendEmptyMessageDelayed(1, 10000); // 十秒后发送消息

        // bugly上报
        CrashReport.UserStrategy strategy = new CrashReport.UserStrategy(getApplicationContext());
        strategy.setAppVersion(TIMManager.getInstance().getVersion());
        CrashReport.initCrashReport(getApplicationContext(), PrivateConstants.BUGLY_APPID, true, strategy);

        //判断是否是在主线程
        if (SessionWrapper.isMainProcess(getApplicationContext())) {
            /**
             * TUIKit的初始化函数
             *
             * @param context  应用的上下文，一般为对应应用的ApplicationContext
             * @param sdkAppID 您在腾讯云注册应用时分配的sdkAppID
             * @param configs  TUIKit的相关配置项，一般使用默认即可，需特殊配置参考API文档
             */
            TUIKit.init(this, GenerateTestUserSig.SDKAPPID, new ConfigHelper().getConfigs());

            if (ThirdPushTokenMgr.USER_GOOGLE_FCM) {
                FirebaseInstanceId.getInstance().getInstanceId()
                        .addOnCompleteListener(new OnCompleteListener<InstanceIdResult>() {
                            @Override
                            public void onComplete(Task<InstanceIdResult> task) {
                                if (!task.isSuccessful()) {
                                    DemoLog.w(TAG, "getInstanceId failed exception = " + task.getException());
                                    return;
                                }

                                // Get new Instance ID token
                                String token = task.getResult().getToken();
                                DemoLog.i(TAG, "google fcm getToken = " + token);

                                ThirdPushTokenMgr.getInstance().setThirdPushToken(token);
                            }
                        });
            } else if (IMFunc.isBrandXiaoMi()) {
                // 小米离线推送
                MiPushClient.registerPush(this, PrivateConstants.XM_PUSH_APPID, PrivateConstants.XM_PUSH_APPKEY);
            } else if (IMFunc.isBrandHuawei()) {
                // 华为离线推送
                HMSAgent.init(this);
            } else if (MzSystemUtils.isBrandMeizu(this)) {
                // 魅族离线推送
                PushManager.register(this, PrivateConstants.MZ_PUSH_APPID, PrivateConstants.MZ_PUSH_APPKEY);
            } else if (IMFunc.isBrandVivo()) {
                // vivo离线推送
                PushClient.getInstance(getApplicationContext()).initialize();
            }

            registerActivityLifecycleCallbacks(new StatisticActivityLifecycleCallback());
        }
//        if (BuildConfig.DEBUG) {
//            if (LeakCanary.isInAnalyzerProcess(this)) {
//                return;
//            }
//            LeakCanary.install(this);
//        }

        CustomAVCallUIController.getInstance().onCreate();

        IMEventListener imEventListener = new IMEventListener() {
            @Override
            public void onNewMessages(List<TIMMessage> msgs) {

//                DemoLog.i(TAG, "onNewMessages");
//                CustomAVCallUIController.getInstance().onNewMessage(msgs);

                try {
                    TIMTextElem textElem = (TIMTextElem) msgs.get(0).getElement(0);
//                ReceiveActivity.changeText(textElem.getText());
                    String str = textElem.getText().replace(" ", "");

                    // 20017101/02/03
                    Log.e("wocao", textElem.getText() + "ssssss" + System.currentTimeMillis());

                    //获取系统时间
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
                    Date curDate = new Date(System.currentTimeMillis());
                    String time = formatter.format(curDate);
                    SiveDao siveDao = new SiveDao(getApplicationContext());
                    //存入数据库
                    ReceiveDao receiveDao = new ReceiveDao(getApplicationContext());
                    receiveDao.add(time, str);
                    //指令通知协议
                    mSpUtil = new SpUtil(getApplicationContext(), "instructions");

                    int length = str.length();
                    if (length >= 2) {
                        if (length == 8) {
                            //人员号
                            mPeopleId = str.substring(0, 2);
                            mPeople = MyUtil.str2HexStr(mPeopleId);
                            int parse = Integer.parseInt(mPeopleId.toString(), 16);
                            //调号（群id）
                            mId = str.substring(2, 4);
                            //指令
                            String instructions = str.substring(4, 6);
                            int parseInt = Integer.parseInt(instructions.toString(), 16);
                            String s = MyUtil.str2HexStr(instructions);
                            //ack
                            String ack = str.substring(6, 8);
                            mSpUtil.setName(mId);
                            //4G-MainBoard
                            //A5+调号+人员号+命令+帧号+FF+checksum
                            String s1 = String.valueOf(parse);
                            String s2 = String.valueOf(parseInt);
                            String form = "A5" + mId + mPeopleId + instructions + "01" + "FF";
                            String data = form.replaceAll(" ", "");
                            int total = 0;
                            for (int i = 0; i < data.length(); i += 2) {
                                //strB.append("0x").append(strData.substring(i,i+2));  //0xC30x3C0x010x120x340x560x780xAA
                                total = total + Integer.parseInt(data.substring(i, i + 2), 16);
                            }
                            //noTotal为累加和取反加一
                            int noTotal = ~total + 1;
                            Log.i("total", String.valueOf(noTotal));
                            //负整数时，前面输入了多余的 FF ，没有去掉前面多余的 FF，按并双字节形式输出
                            //0xFF会像转换成0x000000FF后再进行位运算
                            String hex = Integer.toHexString(noTotal).toUpperCase();
                            Log.i("TAGhex", hex);
                            String key = hex.substring(hex.length() - 2);
                            Log.i("TAG校验码key", key);
                            Log.i("TAGhex", key);
                            //将求得的最后两位拼接到setup字符串后面
                            String cumulative = data + key;
                            Log.i("setUp", data + "    00");
                            Log.i("cumulative", cumulative + "    00");
                            Log.i("parse", parse + "    00");
                            Log.i("parseInt", parseInt + "    00");
                            Log.i("form", form + "    00");


                            SendActivity.receiveMessage(str);
                            soundIdMap.clear();

                            switch (str.substring(0, 2)) {
                                case "20"://调车长
                                    //Select_music(str.substring(4, 6));
                                    //Select_music(str.substring(4, 6));
                                    break;
                                case "0A"://机控器
                                    break;
                                case "0B"://区长台
                                    break;
                                default://制动员
                                /*Select_music(str.substring(4, 6));
                                Select_music(str.substring(0, 1));
                                Select_music(str.substring(1, 2));
                                Select_music("号");
                                Select_music(str.substring(4, 6));
                                Select_music(str.substring(0, 1));
                                Select_music(str.substring(1, 2));
                                Select_music("号");*/
                                    break;
                            }
                            switch (ack) {
                                case "01":
                                    String cumulative1 = mPeopleId + mId + instructions + "02";
                                    sendMessage(mId, cumulative1);
                                    SendDao sendDao = new SendDao(getApplicationContext());
                                    sendDao.add(time, cumulative1);
                                    PointActivity.sendHexString(cumulative.replaceAll("\\s*", ""), "485");
                                    break;
                                case "00":

                                    break;
                            }
                            switch (instructions) {
                                case "73":
                                    //sendMessage(mId, "9");
                                    break;
                                case "75":
                                    //sendMessage(mId, "8");
                                    break;
                            }
                            new PlayThread().run();
                        } else if (str.length() > 8 && str.substring(0, 4).matches("DD99")) {
                            input = str;
                            dateString = str.substring(14, 26);
                            danhao = str.substring(42, 46);
                            InsertDatabase addDatabase = new InsertDatabase();
                            addDatabase.execute();

                            if (combineCommend.CRC_Test(str)) {
                                //SendActivity.eml = str;
                                sendMessage(SendActivity.diaohao, SendActivity.currnumber + "03");
                                Intent in = new Intent("DNF");
                                in.putExtra("name", str);
                                sendBroadcast(in);
                            }
                        } else if (str.equals("停车")) {
                            mSpUtil.setNotice(str);
                        } else if (str.equals("启动")) {
                            mSpUtil.setNotice(str);
                        }
                    } else {
                        if (str.equals("0") && str == "0") {
                            siveDao.add(time, str);
                            sendMessage(mId, "1");
                            List<SiveData> siveData = siveDao.find();
                            int size = siveData.size();
                            String time1 = siveData.get(size - 1).getTime();

                            THandle.removeMessages(1); // 移除这个消息队列(重新计时)
                            THandle.sendEmptyMessageDelayed(1, 10000);// 十秒钟后重新发送消息
                        } else {
                            THandle.sendEmptyMessageDelayed(1, 10000); // 十秒后发送消息
                        }
                    }
                } catch (Exception e) {
                    Log.e("数据异常", "数据异常:" + e);
                }
            }
        };
        TUIKit.addIMEventListener(imEventListener);
    }

    @Override
    public void showToast(Context context) {
        Toast.makeText(instance.getBaseContext(), "wocao", Toast.LENGTH_SHORT);
        Log.e("wocao", "toast");
    }

    @Override
    public void sendMessage(String uid, String s) {
        TIMConversation conversation = TIMManager.getInstance().getConversation(
                TIMConversationType.Group,    //会话类型：单聊
                uid);                      //会话对方用户帐号//对方ID

        TIMMessage msg = new TIMMessage();
        //添加文本内容
        TIMTextElem elem = new TIMTextElem();
        elem.setText(s);
        msg.addElement(elem);
        conversation.sendMessage(msg, new TIMValueCallBack<TIMMessage>() {//发送消息回调
            @Override
            public void onError(int code, String desc) {//发送消息失败
                //错误码 code 和错误描述 desc，可用于定位请求失败原因
                //错误码 code 含义请参见错误码表
                Log.d("wocao", "send message failed. code: " + code + " errmsg: " + desc);
            }

            @Override
            public void onSuccess(TIMMessage msg) {//发送消息成功
                Log.e("wocao", "SendMsg ok");
            }
        });

    }

    @Override
    public void init(Context context) {

    }

    class StatisticActivityLifecycleCallback implements ActivityLifecycleCallbacks {
        private int foregroundActivities = 0;
        private boolean isChangingConfiguration;
        private IMEventListener mIMEventListener = new IMEventListener() {
            @Override
            public void onNewMessages(List<TIMMessage> msgs) {
                if (CustomMessage.convert2VideoCallData(msgs) != null) {
                    // 会弹出接电话的对话框，不再需要通知
                    return;
                }
                for (TIMMessage msg : msgs) {
                    // 小米手机需要在设置里面把demo的"后台弹出权限"打开才能点击Notification跳转。TIMOfflinePushNotification后续不再维护，如有需要，建议应用自己调用系统api弹通知栏消息。
                    TIMOfflinePushNotification notification = new TIMOfflinePushNotification(DemoApplication.this, msg);
                    notification.doNotify(DemoApplication.this, R.drawable.default_user_icon);
                }
            }
        };

        @Override
        public void onActivityCreated(Activity activity, Bundle bundle) {
            DemoLog.i(TAG, "onActivityCreated bundle: " + bundle);
            if (bundle != null) { // 若bundle不为空则程序异常结束
                // 重启整个程序
                Intent intent = new Intent(activity, SplashActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }
        }

        @Override
        public void onActivityStarted(Activity activity) {
            foregroundActivities++;
            if (foregroundActivities == 1 && !isChangingConfiguration) {
                // 应用切到前台
                DemoLog.i(TAG, "application enter foreground");
                TIMManager.getInstance().doForeground(new TIMCallBack() {
                    @Override
                    public void onError(int code, String desc) {
                        DemoLog.e(TAG, "doForeground err = " + code + ", desc = " + desc);
                    }

                    @Override
                    public void onSuccess() {
                        DemoLog.i(TAG, "doForeground success");
                    }
                });
                TUIKit.removeIMEventListener(mIMEventListener);
            }
            isChangingConfiguration = false;
        }

        @Override
        public void onActivityResumed(Activity activity) {

        }

        @Override
        public void onActivityPaused(Activity activity) {

        }

        @Override
        public void onActivityStopped(Activity activity) {
            foregroundActivities--;
            if (foregroundActivities == 0) {
                // 应用切到后台
                DemoLog.i(TAG, "application enter background");
                int unReadCount = 0;
                List<TIMConversation> conversationList = TIMManager.getInstance().getConversationList();
                for (TIMConversation timConversation : conversationList) {
                    unReadCount += timConversation.getUnreadMessageNum();
                }
                TIMBackgroundParam param = new TIMBackgroundParam();
                param.setC2cUnread(unReadCount);
                TIMManager.getInstance().doBackground(param, new TIMCallBack() {
                    @Override
                    public void onError(int code, String desc) {
                        DemoLog.e(TAG, "doBackground err = " + code + ", desc = " + desc);
                    }

                    @Override
                    public void onSuccess() {
                        DemoLog.i(TAG, "doBackground success");

                    }
                });
                // 应用退到后台，消息转化为系统通知
                TUIKit.addIMEventListener(mIMEventListener);
            }
            isChangingConfiguration = activity.isChangingConfigurations();
        }

        @Override
        public void onActivitySaveInstanceState(Activity activity, Bundle bundle) {

        }

        @Override
        public void onActivityDestroyed(Activity activity) {

        }
    }


    //加载音频文件
    LinkedHashMap<Integer, Integer> soundIdMap = new LinkedHashMap<>();

    private Integer[] loadRaw(SoundPool soundPool, Context context, int raw) {
        int soundId = soundPool.load(context, raw, 1);
        int duration = getMp3Duration(context, raw);
        return new Integer[]{soundId, duration};
    }

    //获取音频文件的时长
    private int getMp3Duration(Context context, int rawId) {
        try {
            Uri uri = Uri.parse("android.resource://" + context.getPackageName() + "/" + rawId);
            MediaPlayer mediaPlayer = new MediaPlayer();
            mediaPlayer.setDataSource(context, uri);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.prepare();
            return mediaPlayer.getDuration();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }

    //音频播放线程
    private class PlayThread extends Thread {
        @Override
        public void run() {

            Set<Integer> soundIdSet = soundIdMap.keySet();
            for (Integer soundId : soundIdSet) {
                soundPool.play(soundId, 1.0f, 1.0f, 2, 0, 1);
                try {
                    //获取当前音频的时长
                    Thread.sleep(soundIdMap.get(soundId));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
                /*soundPool.setOnLoadCompleteListener(new SoundPool.OnLoadCompleteListener() {
                    @Override
                    public void onLoadComplete(SoundPool soundPool, int i, int i1) {
                        Set<Integer> soundIdSet = soundIdMap.keySet();
                        for (Integer soundId : soundIdSet) {
                            soundPool.play(soundId, 1.0f, 1.0f, 2, 0, 1);
                            try {
                                //获取当前音频的时长
                                Thread.sleep(soundIdMap.get(soundId));
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                });*/
        }
    }


    private void Select_music(String str) {
        switch (str) {
            case "1":
                Integer[] mp31 = loadRaw(soundPool, context, R.raw.yi);
                soundIdMap.put(mp31[0], mp31[1]);
                break;
            case "2":
                Integer[] mp32 = loadRaw(soundPool, context, R.raw.er);
                soundIdMap.put(mp32[0], mp32[1]);
                break;
            case "3":
                Integer[] mp33 = loadRaw(soundPool, context, R.raw.san);
                soundIdMap.put(mp33[0], mp33[1]);
                break;
            case "4":
                Integer[] mp34 = loadRaw(soundPool, context, R.raw.si);
                soundIdMap.put(mp34[0], mp34[1]);
                break;
            case "5":
                Integer[] mp35 = loadRaw(soundPool, context, R.raw.wu);
                soundIdMap.put(mp35[0], mp35[1]);
                break;
            case "6":
                Integer[] mp36 = loadRaw(soundPool, context, R.raw.liu);
                soundIdMap.put(mp36[0], mp36[1]);
                break;
            case "7":
                Integer[] mp37 = loadRaw(soundPool, context, R.raw.qi);
                soundIdMap.put(mp37[0], mp37[1]);
                break;
            case "8":
                Integer[] mp38 = loadRaw(soundPool, context, R.raw.ba);
                soundIdMap.put(mp38[0], mp38[1]);
                break;
            case "9":
                Integer[] mp39 = loadRaw(soundPool, context, R.raw.jiu);
                soundIdMap.put(mp39[0], mp39[1]);
                break;
            case "0":
                Integer[] mp30 = loadRaw(soundPool, context, R.raw.ling);
                soundIdMap.put(mp30[0], mp30[1]);
                break;
            case "71":
                xintiao_index = 2;
                Integer[] mp3tingche = loadRaw(soundPool, context, R.raw.tingche);
                soundIdMap.put(mp3tingche[0], mp3tingche[1]);
                break;
            case "41":
                xintiao_index = 2;
                Integer[] mp3qidong = loadRaw(soundPool, context, R.raw.qidong);
                soundIdMap.put(mp3qidong[0], mp3qidong[1]);
                break;
            case "43":
                Integer[] mp3tuijin = loadRaw(soundPool, context, R.raw.tuijin);
                soundIdMap.put(mp3tuijin[0], mp3tuijin[1]);
                break;
            case "21":
                Integer[] mp3jiansu = loadRaw(soundPool, context, R.raw.jiansu);
                soundIdMap.put(mp3jiansu[0], mp3jiansu[1]);
                break;
            case "27":
                Integer[] mp3shiche = loadRaw(soundPool, context, R.raw.shiche);
                soundIdMap.put(mp3shiche[0], mp3shiche[1]);
                break;
            case "25":
                Integer[] mp3wuche = loadRaw(soundPool, context, R.raw.wuche);
                soundIdMap.put(mp3wuche[0], mp3wuche[1]);
                break;
            case "23":
                Integer[] mp3sanche = loadRaw(soundPool, context, R.raw.sanche);
                soundIdMap.put(mp3sanche[0], mp3sanche[1]);
                break;
            case "45":
                Integer[] mp3lianjie = loadRaw(soundPool, context, R.raw.lianjie);
                soundIdMap.put(mp3lianjie[0], mp3lianjie[1]);
                break;
            case "47":
                Integer[] mp3liufang = loadRaw(soundPool, context, R.raw.liufang);
                soundIdMap.put(mp3liufang[0], mp3liufang[1]);
                break;
            case "73":
                Integer[] mp3jinjitingche = loadRaw(soundPool, context, R.raw.jinjitingche);
                soundIdMap.put(mp3jinjitingche[0], mp3jinjitingche[1]);
                break;
            case "75":
                Integer[] mp3jiesuo = loadRaw(soundPool, context, R.raw.jiesuo);
                soundIdMap.put(mp3jiesuo[0], mp3jiesuo[1]);
                break;
            case "号":
                Integer[] mp3hao = loadRaw(soundPool, context, R.raw.hao);
                soundIdMap.put(mp3hao[0], mp3hao[1]);
                break;
        }
    }

    String refresh_data = "";

    private class UpdateDatabase extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            String every_add = alpha[4] + "-";
            List<Diaodan> users = db.DiaodanDAO().getAll();
            if (!(users.isEmpty() || users == null)) {
                String allUsers = "";
                for (Diaodan temp : users) {
                    if (alpha[5].matches(temp.getCurrent_time())) {
                        Diaodan diaodan = temp;
                        diaodan.setGou_number(temp.gou_number + every_add);
                        db.DiaodanDAO().updateDiaodan(diaodan);
                        allUsers = diaodan.gou_number;
                    }
                }
                System.out.println(alpha[4] + "---------------" + alpha[5] + "----------" + allUsers);
                return allUsers;
            } else {
                return "";
            }
        }

        @Override
        protected void onPostExecute(String details) {
            Intent in2 = new Intent("LOL");
            //in2.putExtra("name2", refresh_data);
            sendBroadcast(in2);
        }
    }

}
