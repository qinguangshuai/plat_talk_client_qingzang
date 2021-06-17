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

import android.util.Log;
import android.widget.Toast;

import com.alibaba.android.arouter.launcher.ARouter;
import com.example.mylibrary.TestService;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.huawei.android.hms.agent.HMSAgent;
import com.kylindev.totalk.bjxt.SiveDao;
import com.kylindev.totalk.bjxt.SpUtil;
import com.kylindev.totalk.bjxt.SuoData;
import com.kylindev.totalk.qgs.database.eight.EightDataDao;
import com.kylindev.totalk.qgs.database.eleven.ElevenDataDao;
import com.kylindev.totalk.qgs.database.five.FiveDataDao;
import com.kylindev.totalk.qgs.database.fourteen.FourteenDataDao;
import com.kylindev.totalk.qgs.database.nine.NineDataDao;
import com.kylindev.totalk.qgs.database.seven.SevenDataDao;
import com.kylindev.totalk.qgs.database.six.SixDataDao;
import com.kylindev.totalk.qgs.database.ten.TenDataDao;
import com.kylindev.totalk.qgs.database.thirteen.ThirteenDataDao;
import com.kylindev.totalk.qgs.database.twelve.TwelveDataDao;
import com.kylindev.totalk.qgs.tack.PickDao;
import com.meizu.cloud.pushsdk.PushManager;
import com.meizu.cloud.pushsdk.util.MzSystemUtils;
import com.tencent.bugly.crashreport.CrashReport;
import com.tencent.imsdk.TIMBackgroundParam;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMGroupManager;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMOfflinePushNotification;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.imsdk.session.SessionWrapper;
import com.tencent.imsdk.utils.IMFunc;
import com.tencent.qcloud.tim.demo.Database.Diaodan;
import com.tencent.qcloud.tim.demo.Database.DiaodanDatabase;
import com.tencent.qcloud.tim.demo.bjxt.CrashHandler;
import com.tencent.qcloud.tim.demo.bjxt.sqlite.ReceiveDao;
import com.tencent.qcloud.tim.demo.bjxt.sqlite.SendDao;
import com.tencent.qcloud.tim.demo.helper.ConfigHelper;
import com.tencent.qcloud.tim.demo.helper.CustomAVCallUIController;
import com.tencent.qcloud.tim.demo.helper.CustomMessage;
import com.tencent.qcloud.tim.demo.main.MainActivity;
import com.tencent.qcloud.tim.demo.qingzang.SendActivity;
import com.tencent.qcloud.tim.demo.signature.GenerateTestUserSig;
import com.tencent.qcloud.tim.demo.thirdpush.ThirdPushTokenMgr;
import com.tencent.qcloud.tim.demo.utils.CombineCommend;
import com.tencent.qcloud.tim.demo.utils.DemoLog;
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
    private PickDao mPickDao;
    private SpUtil mCqncast;
    private String jieAll = "";
    private SpUtil mControlCar;
    private SpUtil mControlPeople;
    private SpUtil mControlZhidongyuan;
    private String mCumulative1;
    private SpUtil mControlStart;
    private String mPeopleId1;
    private String mAck1;
    private String mFlag;
    private SpUtil mControlshuntinghunting;
    private String mInstructions;
    private SpUtil mControlZdy;
    private String mControlZdyName;
    private boolean tingche = false;
    private SpUtil mControlTuiJin;
    private SpUtil mCon;
    private SpUtil mControlLingChe;
    private String mConversationId = "02";

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
    private boolean tc = false;
    private boolean jiansu = false;

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

    @SuppressLint("RestrictedApi")
    @Override
    public void onCreate() {
        DemoLog.i(TAG, "onCreate");
        super.onCreate();
        instance = this;

        context = this;
        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 1);
        //使用自定义全局异常捕获类
        CrashHandler.getInstance().init(context);

        db = Room.databaseBuilder(context,
                DiaodanDatabase.class, "Diaodan_Database")
                .fallbackToDestructiveMigration()
                .build();

        ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行，必须开启调试模式！线上版本需要关闭,否则有安全风险)
        ARouter.init(this);
        Log.e("wocao", "init");

        MultiDex.install(this);

        //初始化SpUtil
        mCqncast = new SpUtil(getApplicationContext(), "cqncast");
        //mCqncast.setName("00");
        //指令通知协议
        mSpUtil = new SpUtil(getApplicationContext(), "instructions");
        //.setName("00");
        //控制车
        mControlCar = new SpUtil(getApplicationContext(), "controlcar");
        //mControlCar.setName("00");
        //控制人员
        mControlPeople = new SpUtil(getApplicationContext(), "controlpeople");
        mControlZhidongyuan = new SpUtil(getApplicationContext(), "controlzhidongyuan");
        //mControlPeople.setName("00");
        mControlStart = new SpUtil(getApplicationContext(), "controlstart");
        mControlStart.setName("true");
        mControlshuntinghunting = new SpUtil(getApplicationContext(), "controlshunting");
        mControlZdy = new SpUtil(getApplicationContext(), "controlzdy");
        mControlLingChe = new SpUtil(getApplicationContext(), "controllingche");
        mControlTuiJin = new SpUtil(getApplicationContext(), "controltuijin");
        mCon = new SpUtil(getApplicationContext(), "con");

        TIMConversation conversation = TIMManager.getInstance().getConversation(
                TIMConversationType.Group,   //会话类型：
                "01");//会话帐号//群ID
        JoinGroup("01");

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
                    Log.e("wocao", textElem.getText() + "  ssssss");

                    //获取系统时间
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
                    Date curDate = new Date(System.currentTimeMillis());
                    String time = formatter.format(curDate);
                    SiveDao siveDao = new SiveDao(getApplicationContext());
                    //存入数据库
                    ReceiveDao receiveDao = new ReceiveDao(getApplicationContext());
                    receiveDao.add(time, str);
                    mCon.setName(str + "");

                    int length = str.length();
                    if (length >= 2) {
                        if (length == 8) {
                            //人员号
                            mPeopleId = str.substring(0, 2);
                            mPeople = MyUtil.str2HexStr(mPeopleId);
                            //int parse = Integer.parseInt(mPeopleId.toString(), 16);
                            //调号（群id）
                            mId = str.substring(2, 4);
                            //指令
                            mInstructions = str.substring(4, 6);
                            //int parseInt = Integer.parseInt(mInstructions.toString(), 16);
                            String s = MyUtil.str2HexStr(mInstructions);
                            //ack
                            String ack = str.substring(6, 8);
                            if (ack.equals("01")) {
                                //4G-MainBoard
                                //A5+调号+人员号+命令+帧号+FF+checksum
                                //String s1 = String.valueOf(parse);
                                //String s2 = String.valueOf(parseInt);

                                String reply = getReply(mId, mPeopleId, mInstructions);

                                mCumulative1 = mPeopleId + mId + mInstructions + "02";
                                SendDao sendDao = new SendDao(getApplicationContext());
                            /*SendActivity.receiveMessage(str);
                            soundIdMap.clear();*/

                                switch (mPeopleId) {
                                    case "20"://调车长
                                        //Select_music(str.substring(4, 6));
                                        //Select_music(str.substring(4, 6));
                                        mControlPeople.setName("90");
                                        break;
                                    case "0A"://机控器
                                        break;
                                    case "0B"://区长台
                                        break;
                                    default://制动员
                                        mControlZhidongyuan.setName("60");
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
                                //01017301
                                switch (mInstructions) {
                                    case "56"://作业开始
                                        //PointActivity.sendHexString(reply.replaceAll("\\s*", ""), "232");
                                        break;
                                    case "49"://领车   推进、连接、溜放
                                        //String reply1 = getReply(mId, mPeopleId, "98");
                                        String name1 = mControlLingChe.getName();

                                        if (name1 != null && name1.equals("49")) {
                                            if (tingche == false) {
                                                mControlZdy.setName(mPeopleId);
                                                tingche = true;
                                                //PointActivity.sendHexString(reply.replaceAll("\\s*", ""), "232");
                                            }
                                        }
                                        jiansu = true;
                                        break;
                                    case "71"://停车
                                        tingche = false;
                                        tc = false;
                                        jiansu = false;
                                        mControlLingChe.setName("00");
                                        mControlStart.setName("true");
                                        mControlCar.setName("71");
                                        mCqncast.setName(mCumulative1);
                                        sendDao.add(time, mCumulative1);
                                        //PointActivity.sendHexString(reply.replaceAll("\\s*", ""), "232");
                                        break;
                                    case "41"://启动
                                        jiansu = true;
                                        mControlLingChe.setName("00");
                                        String name = mControlStart.getName();
                                        if (name != null && name.equals("true")) {
                                            mControlCar.setName("41");
                                            mCqncast.setName(mCumulative1);
                                            sendDao.add(time, mCumulative1);
                                            //PointActivity.sendHexString(reply.replaceAll("\\s*", ""), "232");
                                            mControlStart.setName("false");
                                        }
                                        break;
                                    case "73"://紧急停车
                                /*if (siveDao != null) {
                                    siveDao.del(mPeopleId);
                                }*/
                                        jiansu = false;
                                        tc = false;
                                        mControlLingChe.setName("00");
                                        if (siveDao != null) {
                                            List<SuoData> suoData = siveDao.find();
                                            int size = suoData.size();
                                            if (size == 0) {
                                                siveDao.add(mPeopleId, "73", "true");
                                                //PointActivity.sendHexString(reply.replaceAll("\\s*", ""), "232");
                                                siveDao.del(mPeopleId);
                                                siveDao.add(mPeopleId, "73", "false");
                                            } else {
                                                for (int i = 0; i < size; i++) {
                                                    mPeopleId1 = suoData.get(i).getPeopleId();
                                                    mAck1 = suoData.get(i).getAck();
                                                    mFlag = suoData.get(i).getFlag();
                                                }
                                                if (mPeopleId.equals(mPeopleId1) && "73".equals(mAck1) && mFlag.equals("true")) {
                                                    //PointActivity.sendHexString(reply.replaceAll("\\s*", ""), "232");
                                                    siveDao.del(mPeopleId);
                                                    siveDao.add(mPeopleId, "73", "false");
                                                } else if (mPeopleId != mPeopleId1) {
                                                    siveDao.add(mPeopleId, "73", "true");
                                                    //PointActivity.sendHexString(reply.replaceAll("\\s*", ""), "232");
                                                    siveDao.del(mPeopleId);
                                                    siveDao.add(mPeopleId, "73", "false");
                                                }
                                            }

                                        } else if (siveDao == null) {
                                            siveDao.add(mPeopleId, "73", "true");
                                            //PointActivity.sendHexString(reply.replaceAll("\\s*", ""), "232");
                                            siveDao.del(mPeopleId);
                                            siveDao.add(mPeopleId, "73", "false");
                                        }
                                        //PointActivity.sendHexString(cumulative.replaceAll("\\s*", ""), "232");
                                        mCqncast.setName(mCumulative1);
                                        sendDao.add(time, mCumulative1);
                                        break;
                                    case "75"://解锁
                                        jiansu = true;
                                        siveDao.del(mPeopleId);
                                        siveDao.add(mPeopleId, "75", "false");
                                        mCqncast.setName(mCumulative1);
                                        sendDao.add(time, mCumulative1);
                                        List<SuoData> suoData = siveDao.find();
                                        int size = suoData.size();
                                        for (int i = 0; i < size; i++) {
                                            String peopleId = suoData.get(i).getPeopleId();
                                            String ack1 = suoData.get(i).getAck();
                                            String flag = suoData.get(i).getFlag();
                                            if (mPeopleId.equals(peopleId) && "75".equals(ack1) && flag.equals("false")) {
                                                //PointActivity.sendHexString(reply.replaceAll("\\s*", ""), "232");
                                                siveDao.del(mPeopleId);
                                                siveDao.add(mPeopleId, "75", "true");
                                            }
                                        }
                                        //PointActivity.sendHexString(cumulative.replaceAll("\\s*", ""), "232");
                                        break;
                                    case "43"://推进
                                        jiansu = true;
                                        tc = true;
                                        mControlLingChe.setName("49");
                                        mControlStart.setName("false");
                                        //THandle.sendEmptyMessageDelayed(1, 10000); // 十秒后发送消息
                                        mSpUtil.setName(mInstructions);
                                        mCqncast.setName(mCumulative1);
                                        sendDao.add(time, mCumulative1);
                                        //PointActivity.sendHexString(reply.replaceAll("\\s*", ""), "232");
                                        break;
                                    case "21"://减速
                                        if (jiansu == true) {
                                            mControlLingChe.setName("00");
                                            mControlStart.setName("false");
                                            mSpUtil.setName(mInstructions);
                                            mCqncast.setName(mCumulative1);
                                            sendDao.add(time, mCumulative1);
                                            //PointActivity.sendHexString(reply.replaceAll("\\s*", ""), "232");
                                        }

                                        break;
                                    case "23"://三车
                                        if (tc == true) {
                                            jiansu = true;
                                            mControlLingChe.setName("00");
                                            mControlTuiJin.setName("23");
                                            mControlStart.setName("false");
                                            mSpUtil.setName(mInstructions);
                                            mCqncast.setName(mCumulative1);
                                            sendDao.add(time, mCumulative1);
                                            //PointActivity.sendHexString(reply.replaceAll("\\s*", ""), "232");
                                        }

                                        break;
                                    case "25"://五车
                                        if (tc == true) {
                                            jiansu = true;
                                            mControlLingChe.setName("00");
                                            mControlTuiJin.setName("25");
                                            mControlStart.setName("false");
                                            mSpUtil.setName(mInstructions);
                                            mCqncast.setName(mCumulative1);
                                            sendDao.add(time, mCumulative1);
                                            //PointActivity.sendHexString(reply.replaceAll("\\s*", ""), "232");
                                        }

                                        break;
                                    case "26"://一车
                                        if (tc == true) {
                                            jiansu = true;
                                            mControlLingChe.setName("00");
                                            mControlTuiJin.setName("26");
                                            mControlStart.setName("false");
                                            mSpUtil.setName(mInstructions);
                                            mCqncast.setName(mCumulative1);
                                            sendDao.add(time, mCumulative1);
                                            //PointActivity.sendHexString(reply.replaceAll("\\s*", ""), "232");
                                        }

                                        break;
                                    case "27"://十车

                                        if (tc == true) {
                                            jiansu = true;
                                            mControlLingChe.setName("00");
                                            mControlTuiJin.setName("27");
                                            mControlStart.setName("false");
                                            mSpUtil.setName(mInstructions);
                                            mCqncast.setName(mCumulative1);
                                            sendDao.add(time, mCumulative1);
                                            //PointActivity.sendHexString(reply.replaceAll("\\s*", ""), "232");
                                        }

                                        break;
                                    case "45"://连接
                                        tc = true;
                                        jiansu = true;
                                        mControlLingChe.setName("49");
                                        mControlStart.setName("false");
                                        mSpUtil.setName(mInstructions);
                                        mCqncast.setName(mCumulative1);
                                        sendDao.add(time, mCumulative1);
                                        //PointActivity.sendHexString(reply.replaceAll("\\s*", ""), "232");
                                        break;
                                    case "47"://溜放
                                        tc = true;
                                        jiansu = true;
                                        mControlLingChe.setName("49");
                                        mControlStart.setName("false");
                                        mSpUtil.setName(mInstructions);
                                        mCqncast.setName(mCumulative1);
                                        sendDao.add(time, mCumulative1);
                                        //PointActivity.sendHexString(reply.replaceAll("\\s*", ""), "232");
                                        break;
                                }
                            }

                            //new PlayThread().run();
                        } else if (str.equals("1111")) {
                            sendMessage(mConversationId, mId + mPeopleId);
                        } else if (str.length() > 8 && str.substring(0, 4).matches("DD99")) {
                            input = str;
                            dateString = str.substring(14, 26);
                            danhao = str.substring(42, 46);
                            InsertDatabase addDatabase = new InsertDatabase();
                            addDatabase.execute();

                            if (combineCommend.CRC_Test(str)) {
                                //SendActivity.eml = str;
                                //sendMessage(SendActivity.diaohao, SendActivity.currnumber + "03");
                                Intent in = new Intent("DNF");
                                in.putExtra("name", str);
                                sendBroadcast(in);
                            }
                        } else if (str.indexOf("摘勾GPS") != -1) {//20-摘勾GPS-134345-463655
                            String GPSlength = str.substring(str.indexOf("-") + 1, str.indexOf("-", str.indexOf("-") + 1));
                            int length1 = GPSlength.length();
                            String GPSTotal = str.substring(str.indexOf("摘勾GPS-", str.indexOf(",") + 1) + length1 + 1, str.length());
                            String lat = GPSTotal.substring(0, GPSTotal.indexOf("-"));
                            String lon = GPSTotal.substring(GPSTotal.indexOf("-") + 1, GPSTotal.length());
                            mPickDao = new PickDao(getApplicationContext());
                            mPickDao.add(lat, lon);
                        } else if (str.indexOf("挂勾GPS") != -1) {
                            String GPSlength = str.substring(str.indexOf("-") + 1, str.indexOf("-", str.indexOf("-") + 1));
                            int length1 = GPSlength.length();
                            String GPSTotal = str.substring(str.indexOf("挂勾GPS-", str.indexOf(",") + 1) + length1 + 1, str.length());
                            String lat = GPSTotal.substring(0, GPSTotal.indexOf("-"));
                            String lon = GPSTotal.substring(GPSTotal.indexOf("-") + 1, GPSTotal.length());
                            mPickDao = new PickDao(getApplicationContext());
                            mPickDao.add(lat, lon);
                        } else if (str.indexOf("说话GPS") != -1) {
                            //获取人员号
                            String personNumber = str.substring(0, 2);
                            String GPSlength = str.substring(str.indexOf("-") + 1, str.indexOf("-", str.indexOf("-") + 1));
                            int length1 = GPSlength.length();
                            String GPSTotal = str.substring(str.indexOf("说话GPS-", str.indexOf(",") + 1) + length1 + 1, str.length());
                            String lat = GPSTotal.substring(0, GPSTotal.indexOf("-"));
                            String lon = GPSTotal.substring(GPSTotal.indexOf("-") + 1, GPSTotal.length());
                            //纬度
                            String latitude = "36." + lat;
                            //经度
                            String longitude = "101." + lon;
                            Log.i("111111",latitude);
                            Log.i("111111",longitude);
                            switch (personNumber) {
                                case "20":
                                    FiveDataDao fiveDataDao = new FiveDataDao(getApplicationContext());
                                    fiveDataDao.add(latitude,longitude);
                                    SpUtil people5 = new SpUtil(getApplicationContext(), "people5");
                                    people5.setName("true");
                                    people5.setLat(latitude);
                                    people5.setLon(longitude);
                                    break;
                                case "01":
                                    SixDataDao sixDataDao = new SixDataDao(getApplicationContext());
                                    sixDataDao.add(latitude,longitude);
                                    SpUtil people6 = new SpUtil(getApplicationContext(), "people6");
                                    people6.setName("true");
                                    people6.setLat(latitude);
                                    people6.setLon(longitude);
                                    break;
                                case "02":
                                    SevenDataDao sevenDataDao = new SevenDataDao(getApplicationContext());
                                    sevenDataDao.add(latitude,longitude);
                                    SpUtil people7 = new SpUtil(getApplicationContext(), "people7");
                                    people7.setName("true");
                                    people7.setLat(latitude);
                                    people7.setLon(longitude);
                                    break;
                                case "03":
                                    EightDataDao eightDataDao = new EightDataDao(getApplicationContext());
                                    eightDataDao.add(latitude,longitude);
                                    SpUtil people8 = new SpUtil(getApplicationContext(), "people8");
                                    people8.setName("true");
                                    people8.setLat(latitude);
                                    people8.setLon(longitude);
                                    break;
                                case "04":
                                    NineDataDao nineDataDao = new NineDataDao(getApplicationContext());
                                    nineDataDao.add(latitude,longitude);
                                    SpUtil people9 = new SpUtil(getApplicationContext(), "people9");
                                    people9.setName("true");
                                    people9.setLat(latitude);
                                    people9.setLon(longitude);
                                    break;
                                case "05":
                                    TenDataDao tenDataDao = new TenDataDao(getApplicationContext());
                                    tenDataDao.add(latitude,longitude);
                                    break;
                                case "06":
                                    ElevenDataDao elevenDataDao = new ElevenDataDao(getApplicationContext());
                                    elevenDataDao.add(latitude,longitude);
                                    break;
                                case "07":
                                    TwelveDataDao twelveDataDao = new TwelveDataDao(getApplicationContext());
                                    twelveDataDao.add(latitude,longitude);
                                    break;
                                case "08":
                                    ThirteenDataDao thirteenDataDao = new ThirteenDataDao(getApplicationContext());
                                    thirteenDataDao.add(latitude,longitude);
                                    break;
                                case "09":
                                    FourteenDataDao fourteenDataDao = new FourteenDataDao(getApplicationContext());
                                    fourteenDataDao.add(latitude,longitude);
                                    break;
                            }
                        }
                    } else {
                        switch (str) {
                            case "1":
                                String name = mControlPeople.getName();
                                switch (name) {
                                    case "90":
                                        if (name != null) {
                                            String sum = getSum(name, "20");
                                            //PointActivity.sendHexString(sum.replaceAll("\\s*", ""), "232");
                                        }
                                        break;
                                /*case "60":
                                    //sendMessage("01", "2");
                                    String sum1 = getSum(name);
                                    PointActivity.sendHexString(sum1.replaceAll("\\s*", ""), "232");
                                    break;*/
                                }
                                break;
                            case "3":
                                String name1 = mControlZhidongyuan.getName();
                                switch (name1) {
                                /*case "90":
                                    String sum = getSum(name1);
                                    PointActivity.sendHexString(sum.replaceAll("\\s*", ""), "232");
                                    break;*/
                                    case "60":
                                        //sendMessage("01", "2");
                                        if (mControlZdy != null) {
                                            mControlZdyName = mControlZdy.getName();
                                        }
                                        if (name1 != null && mPeopleId != null) {
                                            String sum1 = getSum(name1, mControlZdyName);
                                            //PointActivity.sendHexString(sum1.replaceAll("\\s*", ""), "232");
                                        }
                                        break;
                                }
                                break;
                            case "7":
                                String shunting = mControlshuntinghunting.getName();
                                if (shunting != null) {
                                    switch (shunting) {
                                        case "unlock":
                                            sendMessage(mConversationId, "9");
                                            break;
                                        case "lock":
                                            sendMessage(mConversationId, "8");
                                            break;
                                        default:
                                            sendMessage(mConversationId, "9");
                                            break;
                                    }
                                } else {
                                    sendMessage(mConversationId, "9");
                                }
                                break;
                        }
                    }
                } catch (Exception e) {
                    Log.e("数据异常", "数据异常:" + e);
                }
            }
        };
        TUIKit.addIMEventListener(imEventListener);
    }

    public String getReply(String id, String peopleId, String instructions) {
        String form = "A5" + id + peopleId + instructions + "01" + "01";
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
        Log.i("TAGhex", cumulative);
        return cumulative;
    }

    public String getSum(String instructions, String people) {
        String form = "A5" + mId + people + instructions + "01" + "FF";
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
        Log.i("cumulative1", cumulative + "    00");
        //Log.i("parse", parse + "    00");
        //Log.i("parseInt", parseInt + "    00");
        Log.i("form", form + "    00");
        return cumulative;
    }

    @Override
    public void showToast(Context context) {
        Toast.makeText(instance.getBaseContext(), "wocao", Toast.LENGTH_SHORT);
        Log.e("wocao", "toast");
    }

    private String xn = "xining";

    @Override
    public void sendMessage(String uid, String s) {
        TIMConversation conversation = TIMManager.getInstance().getConversation(
                TIMConversationType.Group,    //会话类型：单聊
                xn + uid);                      //会话对方用户帐号//对方ID

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

    //进群
    private void JoinGroup(String id) {
        TIMGroupManager.getInstance().applyJoinGroup(xn + id, "", new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
            }

            @Override
            public void onSuccess() {
                Log.e("wocao", "wocao1");
            }
        });
    }

    //退群
    private void QuitGroup(String id) {

        TIMGroupManager.getInstance().quitGroup(xn + id, new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
                Log.e("swy", "----------------------not quit--------------------");
                //System.out.println("----------------------not quit--------------------");
            }

            @Override
            public void onSuccess() {
                Log.e("swy", "----------------------quit--------------------");
                //System.out.println("----------------------quit--------------------");
            }
        });
    }
}
