package com.tencent.qcloud.tim.demo.qingzang;

import android.annotation.SuppressLint;

import androidx.room.Room;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;

import androidx.annotation.Nullable;

import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.kylindev.totalk.app.QGSActivity;
import com.kylindev.totalk.app.XNBMapActivity;
import com.kylindev.totalk.qgs.PointActivity;
import com.tencent.imsdk.TIMCallBack;
import com.tencent.imsdk.TIMConversation;
import com.tencent.imsdk.TIMConversationType;
import com.tencent.imsdk.TIMGroupManager;
import com.tencent.imsdk.TIMManager;
import com.tencent.imsdk.TIMMessage;
import com.tencent.imsdk.TIMTextElem;
import com.tencent.imsdk.TIMValueCallBack;
import com.tencent.qcloud.tim.demo.BaseActivity;
import com.tencent.qcloud.tim.demo.Database.Diaodan;
import com.tencent.qcloud.tim.demo.Database.DiaodanDatabase;
import com.tencent.qcloud.tim.demo.Layout.ReportDetailAdapter;
import com.tencent.qcloud.tim.demo.R;
import com.tencent.qcloud.tim.demo.light.FlashUtils;
import com.tencent.qcloud.tim.demo.main.MainActivity;
import com.tencent.qcloud.tim.demo.utils.CombineCommend;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class SendActivity extends BaseActivity {
    Button bt1, bt2, bt3;
    Button light, camera;
    Context context;
    boolean isOpen = false;
    FlashUtils utils = null;
    boolean red_isSend = true;
    boolean red_isSend2 = false;

    boolean green_isSend = true;
    boolean green_isSend2 = false;

    boolean yellow_isSend = true;
    boolean yellow_isSend2 = false;

    Long time2 = 0L;
    Long time = 0L;
    Long currenttime = 0L;
    ArrayList<String> list = new ArrayList<>();
    ArrayList<String> listhistory = new ArrayList<>();
    public static ArrayList<String> recall_listhistory = new ArrayList<>();

    public int soundId_send, soundId_hong, soundId_lv, soundId_huang, soundId_qidong, soundId_lianjie, soundId_tingche, soundId_tuijin, soundId_liufang, soundId_sanche, soundId_wuche, soundId_shiche, soundId_0, soundId_1, soundId_2, soundId_3, soundId_4, soundId_5, soundId_6, soundId_7, soundId_8, soundId_9;
    public SoundPool soundPool;

    TIMConversation conversation;
    TIMGroupManager groupManager;
    public static String zhishiTrans = "", caozuoyuan = "", currnumber = "", diaohao = "";
    public static TextView receive;
    private SharedPreferences sp;
    TIMValueCallBack timValueCallBack;
    TIMGroupManager.CreateGroupParam createGroupParam;
    CombineCommend combineCommend = new CombineCommend();
    private RecyclerView report_detail_recyclerview;
    private List<String> mList = new ArrayList<>();
    Button bt_refrash_send, bt_search_send, bt_clear_send;

    DiaodanDatabase db = null;
    private List<String> list_diaodan_display = new ArrayList<String>();
    private List<String> list_diao1 = new ArrayList<String>();
    private List<String> list_diao2 = new ArrayList<String>();
    private Spinner spinnertext1;
    private ArrayAdapter<String> adapter1;
    private String diaodan_number = "";
    String str1 = "";

    private ReportDetailAdapter detailAdapter;
    private BroadcastReceiver mMb;

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send);
        String id = getIntent().getStringExtra("id");
        id = "te";
        context = this;
        //不需要申请WakeLock和权限
        init();

        /*XintiaoReadThread xintiaoReadThread = new XintiaoReadThread();
        xintiaoReadThread.start();*/
    }

    public static String eml = "";

    private void init() {
        db = Room.databaseBuilder(context,
                DiaodanDatabase.class, "Diaodan_Database")
                .fallbackToDestructiveMigration()
                .build();

        soundPool = new SoundPool(10, AudioManager.STREAM_MUSIC, 2);
        sp = getSharedPreferences("yuShe", Context.MODE_PRIVATE);
        zhishiTrans = sp.getString("mode1", "");
        caozuoyuan = sp.getString("mode2", "");
        currnumber = sp.getString("mode3", "");
        diaohao = sp.getString("mode5", "");

        spinnertext1 = (Spinner) findViewById(R.id.spinner_diaodan);
        TextView tv_diaohao = findViewById(R.id.tv_diaohao);
        tv_diaohao.setText("调号: " + diaohao);
        TextView tv_benji = findViewById(R.id.tv_benji);
        tv_benji.setText("本机号码: 1001023");
        receive = findViewById(R.id.tv_receive_sendactivity);
        bt_refrash_send = findViewById(R.id.bt_refrash_send);
        bt_refrash_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                ReadDatabase readDatabase = new ReadDatabase();
                readDatabase.execute();
            }
        });

        bt_search_send = findViewById(R.id.bt_submit);
        bt_search_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FindDatabase findDatabase = new FindDatabase();
                findDatabase.execute();
            }
        });

        Button map = findViewById(R.id.bt_map_send);
        map.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SendActivity.this, PointActivity.class);
                startActivity(intent);
            }
        });

        /*bt_clear_send = findViewById(R.id.bt_clear_send);
        bt_clear_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                DeleteDatabase deleteDatabase = new DeleteDatabase();
                deleteDatabase.execute();
            }
        });*/
        //设置内容可滚动
        receive.setMovementMethod(ScrollingMovementMethod.getInstance());

        conversation = TIMManager.getInstance().getConversation(
                TIMConversationType.Group,   //会话类型：
                diaohao);                      //会话帐号//群ID

        JoinGroup(diaohao);

        list_diao2.add("调单");

        adapter1 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, list_diao2);
        //第三步：设置下拉列表下拉时的菜单样式
        adapter1.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        //第四步：将适配器添加到下拉列表上
        spinnertext1.setAdapter(adapter1);
        //第五步：添加监听器，为下拉列表设置事件的响应

        spinnertext1.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> argO, View argl, int arg2, long arg3) {
                diaodan_number = adapter1.getItem(arg2).replace("/", "").replace("-", "").replace(":", "");
                // TODO Auto-generated method stub
                argO.setVisibility(View.VISIBLE);
            }

            public void onNothingSelected(AdapterView<?> argO) {
                // TODO Auto-generated method stub
                argO.setVisibility(View.VISIBLE);
            }
        });

        //将spinnertext添加到OnTouchListener对内容选项触屏事件处理
        spinnertext1.setOnTouchListener(new Spinner.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                // 将mySpinner隐藏
                v.setVisibility(View.VISIBLE);
                return false;
            }
        });

        //焦点改变事件处理
        spinnertext1.setOnFocusChangeListener(new Spinner.OnFocusChangeListener() {
            public void onFocusChange(View v, boolean hasFocus) {
                // TODO Auto-generated method stub
                v.setVisibility(View.VISIBLE);
            }
        });

        //str1 = "DD 99 00 E1 07 00 00 19 07 18 14 21 59 03 D5 BE B5 F7 00 00 01 00 01 10 00 12 30 CB EF D5 BC C0 A5 00 00 C2 ED BD A8 BB AA 00 00 00 39 05 D5 BE D5 FB 07 36 36 36 36 36 36 01 00 2A CF C2 D1 A9 C2 B7 BB AC A1 A2 D7 A2 D2 E2 B0 B2 C8 AB 0D 0A CF C2 D3 EA C2 B7 BB AC A1 A2 D7 A2 D2 E2 B0 B2 C8 AB 0D 0A 58 04 31 B5 C0 81 53 20 31 32 33 34 35 36 37 20 D7 A2 D2 E2 B0 B2 C8 AB 0A 08 BB F5 B3 A1 31 BA C5 84 94 08 BB F5 B3 A1 31 BA C5 84 C9 B6 D3 C3 B6 BC C3 BB D3 D0 0E 02 31 04 CE D2 BE CD B2 BB D0 C5 C1 CB 09 04 B9 C9 31 88 54 42 51 9b 05 31 34 B1 B1 09 D0 C2 CE F7 C0 BC C0 B4  B3 B5 A1 A2 D7 A2 D2 E2 BD D8 BB F1 15 03 32 35 05 C7 B0 B7 BD C2 B7 D5 CF A1 A2 B2 BB D2 AA CD A3 0C EF";

        //DisplayDiaodanLayout(str1.replace(" ",""));
        if (caozuoyuan.matches("调车长")) {
            currnumber = "20";
        }

        mMb = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                if (zhishiTrans.matches("B制式") && caozuoyuan.matches("调车长") && diaochezhang_premiss) {
                    switch (intent.getAction()) {
                        case "DNF":
                            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
                            String s = intent.getStringExtra("name");
                            Log.i("接收广播", "成功");
                            try {
                                DisplayDiaodanLayout(s);
                            } catch (Exception e) {
                                Log.e("Exception:", e + "");
                            }

                            break;
                        case "LOL":
                            //String s2 = intent.getStringExtra("name2");
                            //DisplayDiaodanLayout(s2);
                            try {
                                ListDatabase listDatabase = new ListDatabase();
                                listDatabase.execute();
                                Log.i("接收广播2", "成功");
                            } catch (Exception e) {
                            }
                            break;
                        case "android.intent.action.side_key.keydown.CAMERA":
                            event_index = 0;
                            handler_xintiao.post(Key_broadcast_r_rrr);
                            break;
                        case "android.intent.action.side_key.keyup.CAMERA":
                            B_zhishi_press_control_diaochezhang(event_cout, "r", "停车", 300, 1);
                            event_cout = 0;
                            event_index = 1;
                            break;
                        case "android.intent.action.side_key.keydown.TORCH":
                            event_index = 0;
                            handler_xintiao.post(Key_broadcast_g_rrr);
                            break;
                        case "android.intent.action.side_key.keyup.TORCH":
                            B_zhishi_press_control_diaochezhang(event_cout, "y", "启动", 2000, 1);
                            event_cout = 0;
                            event_index = 1;
                            break;
                        case "android.intent.action.side_key.keydown.SOS":
                            event_index = 0;
                            handler_xintiao.post(Key_broadcast_y_rrr);
                            break;
                        case "android.intent.action.side_key.keyup.SOS":
                            B_zhishi_press_control_diaochezhang(event_cout, "y", "呼叫区长", 1500, 1);
                            event_cout = 0;
                            event_index = 1;
                            break;
                    }
                } else if (zhishiTrans.matches("B制式") && caozuoyuan.matches("制动员")) {
                    switch (intent.getAction()) {
                        case "DNF":
                            getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
                            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD);
                            String s = intent.getStringExtra("name");
                            Log.i("接收广播", "成功");
                            try {
                                DisplayDiaodanLayout(s);
                            } catch (Exception e) {

                            }
                            break;
                        case "LOL":
                            //String s2 = intent.getStringExtra("name2");
                            //DisplayDiaodanLayout(s2);
                            try {
                                ListDatabase listDatabase = new ListDatabase();
                                listDatabase.execute();
                                Log.i("接收广播2", "成功");
                            } catch (Exception e) {
                            }
                            break;
                        case "android.intent.action.side_key.keydown.CAMERA":
                            B_zhishi_press_control_zhidongyuan(0, "r", "紧急停车", 300, 0);
                            break;
                        case "android.intent.action.side_key.keyup.CAMERA":
                            B_zhishi_press_control_zhidongyuan(0, "r", "紧急停车", 300, 1);
                            break;
                        case "android.intent.action.side_key.keydown.TORCH":
                            break;
                        case "android.intent.action.side_key.keyup.TORCH":
                            break;
                        case "android.intent.action.side_key.keydown.SOS":
                            B_zhishi_press_control_zhidongyuan(0, "y", "解锁", 1500, 0);
                            break;
                        case "android.intent.action.side_key.keyup.SOS":
                            B_zhishi_press_control_zhidongyuan(0, "y", "解锁", 1500, 1);
                            break;
                    }
                }
            }
        };
        IntentFilter mif = new IntentFilter();
        mif.addAction("DNF");
        mif.addAction("LOL");
        mif.addAction("android.intent.action.side_key.keydown.CAMERA");
        mif.addAction("android.intent.action.side_key.keyup.CAMERA");
        mif.addAction("android.intent.action.side_key.keydown.SOS");
        mif.addAction("android.intent.action.side_key.keyup.SOS");
        mif.addAction("android.intent.action.side_key.keydown.SOS");
        mif.addAction("android.intent.action.side_key.keydown.TORCH");
        mif.addAction("android.intent.action.side_key.keyup.TORCH");
        mif.addAction("android.intent.action.side_key.keydown.VOLUME_DOWN");
        mif.addAction("android.intent.action.side_key.keyup.VOLUME_DOWN");
        registerReceiver(mMb, mif);

        /* Intent forgroundService = new Intent(this,BackGroundService.class);
         *//*if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //android8.0以上通过startForegroundService启动service
            startForegroundService(forgroundService);
        } else {*//*
            startService(forgroundService);*/

    }

    Runnable Key_broadcast_y_rrr = new Runnable() {
        @Override
        public void run() {
            System.out.println("dddddddddddddddddd" + event_cout);
            if (event_index == 0) {
                B_zhishi_press_control_diaochezhang(event_cout, "y", "呼叫区长", 1500, 0);
                event_cout++;
                handler_xintiao.postDelayed(this, 50);
            } else {
                handler_xintiao.removeCallbacks(Key_broadcast_y_rrr);
            }

        }
    };

    Runnable Key_broadcast_r_rrr = new Runnable() {
        @Override
        public void run() {
            if (event_index == 0) {
                B_zhishi_press_control_diaochezhang(event_cout, "r", "停车", 300, 0);
                event_cout++;
                handler_xintiao.postDelayed(this, 50);
            } else {
                handler_xintiao.removeCallbacks(Key_broadcast_y_rrr);
            }

        }
    };

    Runnable Key_broadcast_g_rrr = new Runnable() {
        @Override
        public void run() {
            if (event_index == 0) {
                B_zhishi_press_control_diaochezhang(event_cout, "g", "启动", 1500, 0);
                event_cout++;
                handler_xintiao.postDelayed(this, 50);
            } else {
                handler_xintiao.removeCallbacks(Key_broadcast_y_rrr);
            }

        }
    };

    int event_cout = 0, event_index = 0;

    private class ReadDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            list_diao1.clear();
            List<Diaodan> users = db.DiaodanDAO().getAll();
            if (!(users.isEmpty() || users == null)) {
                String allUsers = "";
                for (Diaodan temp : users) {
                    list_diao1.add(temp.getCurrent_time());
                }
                Collections.sort(list_diao1, Collections.reverseOrder());
                if (list_diao1.size() > 20) {
                    list_diao2.clear();
                    list_diao2.add("调单");
                    for (int i = 0; i < 20; i++) {
                        list_diao2.add(list_diao1.get(i).substring(0, 2) + "/" + list_diao1.get(i).substring(2, 4) + "/" + list_diao1.get(i).substring(4, 6) +
                                "-" + list_diao1.get(i).substring(6, 8) + ":" + list_diao1.get(i).substring(8, 10) + ":" + list_diao1.get(i).substring(10, 12));
                    }
                    for (Diaodan temp : users) {
                        if (list_diao2.contains(temp.current_time)) {
                        } else {
                            db.DiaodanDAO().delete(temp);
                        }
                    }
                } else {
                    list_diao2.clear();
                    list_diao2.add("调单");
                    for (int i = 0; i < list_diao1.size(); i++) {
                        list_diao2.add(list_diao1.get(i).substring(0, 2) + "/" + list_diao1.get(i).substring(2, 4) + "/" + list_diao1.get(i).substring(4, 6) +
                                "-" + list_diao1.get(i).substring(6, 8) + ":" + list_diao1.get(i).substring(8, 10) + ":" + list_diao1.get(i).substring(10, 12));
                    }
                }
                return "";
            } else {
                return "";
            }

        }


        @Override
        protected void onPostExecute(String details) {
        }
    }

    private class FindDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            gou_list.clear();
            List<Diaodan> users = db.DiaodanDAO().getAll();
            if (!(users.isEmpty() || users == null)) {
                String allUsers = "";
                String refresh_data = "";
                for (Diaodan temp : users) {
                    //String userstr = temp.gou_number;
                    if (diaodan_number.matches(temp.getCurrent_time())) {
                        allUsers = temp.gou_number;
                        ;
                        refresh_data = temp.str;
                    }
                }
                String[] strings = allUsers.split("-");
                for (int i = 0; i < strings.length; i++) {
                    gou_list.add(strings[i]);
                }
                return refresh_data;
            } else
                return "";
        }

        @Override
        protected void onPostExecute(String details) {
            //System.out.println(details);
            DisplayDiaodanLayout(details);
        }
    }

    public static boolean diaochezhang_premiss = true;

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        System.out.println("Back pressed. event.getKeyCode() => " + event.getKeyCode() + ", event.getAction() => " + event.getAction() + ", event.getRepeatCount() => " + event.getRepeatCount());
        if (zhishiTrans.matches("B制式") && caozuoyuan.matches("调车长") && diaochezhang_premiss) {
            switch (event.getKeyCode()) {
                    /*case 24:
                        B_zhishi_press_control_diaochezhang(event.getRepeatCount(), "r", "停车", 300, event.getAction());
                        break;
                    case 25:
                        B_zhishi_press_control_diaochezhang(event.getRepeatCount(), "g", "启动", 2000, event.getAction());
                        break;
                    case 27:
                        B_zhishi_press_control_diaochezhang(event.getRepeatCount(), "y", "呼叫区长", 1500, event.getAction());
                        break;*/
                case KeyEvent.KEYCODE_BACK:
                    Intent intent1 = new Intent(SendActivity.this, MainActivity.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent1);
                    break;
            }
        } else if (zhishiTrans.matches("B制式") && caozuoyuan.matches("制动员")) {
            switch (event.getKeyCode()) {
                    /*case 24:
                        B_zhishi_press_control_zhidongyuan(event, "r", "紧急停车", 300, event.getAction());
                        break;
                    case 25:
                        //B_zhishi_press_control_test(event, "g", "启动", 2000, event.getAction());
                        break;
                    case 27:
                        B_zhishi_press_control_zhidongyuan(event, "y", "解锁", 1500, event.getAction());
                        break;*/
                case KeyEvent.KEYCODE_BACK:
                    Intent intent1 = new Intent(SendActivity.this, MainActivity.class);
                    intent1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
                    startActivity(intent1);
                    break;
            }
        }
        return true;
    }


    private void setKeyguardEnable(boolean enable) {
        //disable
        if (!enable) {
            getWindow().addFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                    WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
            return;
        }

        //enable
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD |
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED);
    }

    int r_index = 0, g_index = 0, y_index = 0, red_index2 = 0;

    //发送的信息，12位是人员，调车长固定为20，制动员为号码；34位为调号;56位为指令;78位为ACK,00是不需要返回ACK，01为需要，02为收到确认；
    private void B_zhishi_press_control_diaochezhang(int event, String key, String order, int t, int action) {
        switch (key) {
            case "r":
                switch (action) {
                    case 0://按下
                        if (event == 0) {
                            if (System.currentTimeMillis() - time2 > 500) {
                                list.clear();
                            }
                            r_index = 0;
                            red_index2 = 0;
                            send_over = 0;
                            time = System.currentTimeMillis();
                            currenttime = System.currentTimeMillis();
                            //playVideo(key);
                        }
                        long ttr = System.currentTimeMillis() - currenttime;
                        if (ttr >= 300 && red_index2 == 0) {
                            red_index2 = 1;
                            playVideo(key);
                        }
                        if (ttr >= 1500 && r_index == 0) {
                            r_index = 1;
                            sendMessage("20" + diaohao + "71" + "01");
                            listhistory.add("停车");
                            xintiao_index = 2;
                            playVideo("send");
                        }
                        break;
                    case 1://抬起
                        time2 = System.currentTimeMillis();
                        if (System.currentTimeMillis() - time < 300) {
                            Log.i("up", "r1");
                            stopVideo();
                            /*new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    stopVideo();
                                }
                            }, 300 - System.currentTimeMillis() + time);*/
                        } else {
                            Log.i("up", "r2");
                            stopVideo();
                        }
                        handler_xintiao.postDelayed(send_over_rrr, 500);
                        if (System.currentTimeMillis() - time >= 300 && r_index == 0) {
                            list.add(key);
                            checkList_diaochezhang();
                        }
                        break;
                }
                break;
            case "g":
                switch (action) {
                    case 0://按下
                        if (event == 0) {
                            playVideo(key);
                            if (System.currentTimeMillis() - time2 > 500) {
                                list.clear();
                            }
                            send_over = 0;
                            g_index = 0;
                            time = System.currentTimeMillis();
                            currenttime = System.currentTimeMillis();

                        }
                        long ttg = System.currentTimeMillis() - currenttime;
                        if (ttg >= 2000 && g_index == 0) {
                            g_index = 1;
                            sendMessage("20" + diaohao + "41" + "01");
                            listhistory.add("启动");
                            playVideo("send");
                        }
                        break;
                    case 1://抬起
                        time2 = System.currentTimeMillis();
                        if (System.currentTimeMillis() - time < 100) {
                            new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    stopVideo();
                                }
                            }, 100 - System.currentTimeMillis() + time);
                        } else {
                            stopVideo();
                        }
                        handler_xintiao.postDelayed(send_over_rrr, 500);
                        if (System.currentTimeMillis() - time <= 500) {
                            list.add(key);
                            checkList_diaochezhang();
                        }
                        break;
                }
                break;
            case "y":
                switch (action) {
                    case 0://按下
                        if (event == 0) {
                            if (System.currentTimeMillis() - time2 > 500) {
                                list.clear();
                            }
                            send_over = 0;
                            y_index = 0;
                            time = System.currentTimeMillis();
                            currenttime = System.currentTimeMillis();
                            playVideo(key);
                        }
                        long tty = System.currentTimeMillis() - currenttime;
                        if (tty >= 1500 && listhistory.isEmpty() && y_index == 0) {
                            y_index = 1;
                            sendMessage("20" + diaohao + "27" + "01");
                            listhistory.add("十车");
                            currenttime = System.currentTimeMillis();
                            playVideo("send");
                            if (xintiao_index == 2) {
                                xintiao_index = 1;
                                handler_xintiao.postDelayed(xintiaorunnable, 5000);
                            }
                        } else if (!listhistory.isEmpty() && y_index == 0) {
                            switch (listhistory.get(listhistory.size() - 1)) {
                                case "十车":
                                    if (tty >= 500) {
                                        y_index = 1;
                                        sendMessage("20" + diaohao + "27" + "01");
                                        listhistory.add("十车");
                                        currenttime = System.currentTimeMillis();
                                        playVideo("send");
                                        if (xintiao_index == 2) {
                                            xintiao_index = 1;
                                            handler_xintiao.postDelayed(xintiaorunnable, 5000);
                                        }
                                    }
                                    break;
                                /*case "五车":
                                    if (tty >= 500) {
                                        sendMessage("三车");
                                        listhistory.add("三车");
                                        currenttime = System.currentTimeMillis();
                                        playVideo("send");
                                    }
                                    break;
                                case "三车":
                                    break;*/
                                default:
                                    if (tty >= 1500) {
                                        y_index = 1;
                                        sendMessage("20" + diaohao + "27" + "01");
                                        listhistory.add("十车");
                                        currenttime = System.currentTimeMillis();
                                        playVideo("send");
                                        if (xintiao_index == 2) {
                                            xintiao_index = 1;
                                            handler_xintiao.postDelayed(xintiaorunnable, 5000);
                                        }
                                    }
                                    break;
                            }
                        }
                        break;
                    case 1://抬起
                        time2 = System.currentTimeMillis();
                        if (System.currentTimeMillis() - time < 300) {
                            stopVideo();
                            /*new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    stopVideo();
                                }
                            }, 300 - System.currentTimeMillis() + time);*/
                        } else {
                            stopVideo();
                        }
                        handler_xintiao.postDelayed(send_over_rrr, 500);
                        if (System.currentTimeMillis() - time <= 500) {
                            list.add(key);
                            checkList_diaochezhang();
                        } else {
                        }
                        break;
                }
                break;
        }
    }

    private void B_zhishi_press_control_zhidongyuan(int event, String key, String order, int t, int action) {
        switch (key) {
            case "r":
                switch (action) {
                    case 0://按下
                        if (event == 0) {
                            if (System.currentTimeMillis() - time2 > 800) {
                                list.clear();
                            }
                            send_over = 0;
                            //y_index = 0;
                            time = System.currentTimeMillis();
                            //currenttime = System.currentTimeMillis();
                            playVideo(key);
                        }
                        break;
                    case 1://抬起
                        time2 = System.currentTimeMillis();
                        if (System.currentTimeMillis() - time < 300) {
                            stopVideo();
                            /*new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    stopVideo();
                                }
                            }, 300 - System.currentTimeMillis() + time);*/
                        } else {
                            stopVideo();
                        }
                        handler_xintiao.postDelayed(send_over_rrr, 800);
                        list.add(key);
                        checkList_zhidongyuan();
                        break;
                }
                break;
            case "y":
                switch (action) {
                    case 0://按下
                        if (event == 0) {
                            if (System.currentTimeMillis() - time2 > 800) {
                                list.clear();
                            }
                            send_over = 0;
                            time = System.currentTimeMillis();
                            playVideo(key);
                        }
                        break;
                    case 1://抬起
                        time2 = System.currentTimeMillis();
                        if (System.currentTimeMillis() - time < 300) {
                            stopVideo();
                            /*new Timer().schedule(new TimerTask() {
                                @Override
                                public void run() {
                                    stopVideo();
                                }
                            }, 300 - System.currentTimeMillis() + time);*/
                        } else {
                            stopVideo();
                        }
                        handler_xintiao.postDelayed(send_over_rrr, 800);
                        list.add(key);
                        checkList_zhidongyuan();
                        break;
                }
                break;
            default:
                break;
        }
    }

    private void checkList_diaochezhang() {
        String s = "";
        for (int i = 0; i < list.size(); i++) {
            s += list.get(i);
        }
        Log.e("wocao", s);
        switch (s) {
            case "r":
                sendMessage("20" + diaohao + "71" + "01");
                listhistory.add("停车");
                playVideo("send");
                list.clear();
                xintiao_index = 2;
                break;
            case "gg":
                sendMessage("20" + diaohao + "43" + "01");
                listhistory.add("推进");
                playVideo("send");
                list.clear();
                //xintiao_index = 1;
                if (xintiao_index == 2) {
                    xintiao_index = 1;
                    handler_xintiao.postDelayed(xintiaorunnable, 5000);
                }
                break;
            case "yy":
                sendMessage("20" + diaohao + "21" + "01");
                listhistory.add("减速");
                playVideo("send");
                list.clear();
                if (xintiao_index == 2) {
                    xintiao_index = 1;
                    handler_xintiao.postDelayed(xintiaorunnable, 5000);
                }
                break;
            case "yg":
                sendMessage("20" + diaohao + "25" + "01");
                listhistory.add("五车");
                playVideo("send");
                list.clear();
                if (xintiao_index == 2) {
                    xintiao_index = 1;
                    handler_xintiao.postDelayed(xintiaorunnable, 5000);
                }
                break;
            case "yr":
                sendMessage("20" + diaohao + "23" + "01");
                listhistory.add("三车");
                playVideo("send");
                list.clear();
                if (xintiao_index == 2) {
                    xintiao_index = 1;
                    handler_xintiao.postDelayed(xintiaorunnable, 5000);
                }
                break;
            case "gr":
                sendMessage("20" + diaohao + "45" + "01");
                listhistory.add("连接");
                playVideo("send");
                list.clear();
                if (xintiao_index == 2) {
                    xintiao_index = 1;
                    handler_xintiao.postDelayed(xintiaorunnable, 5000);
                }
                break;
            case "gy":
                sendMessage("20" + diaohao + "47" + "01");
                listhistory.add("溜放");
                playVideo("send");
                list.clear();
                if (xintiao_index == 2) {
                    xintiao_index = 1;
                    handler_xintiao.postDelayed(xintiaorunnable, 5000);
                }
                break;
        }

    }

    private void checkList_zhidongyuan() {
        String s = "";
        for (int i = 0; i < list.size(); i++) {
            s += list.get(i);
        }
        Log.e("wocao", s);
        switch (s) {
            case "r":
                sendMessage(currnumber + diaohao + "73" + "01");
                listhistory.add("紧急停车" + currnumber);
                playVideo("send");
                list.clear();
                break;
            case "y":
                sendMessage(currnumber + diaohao + "75" + "01");
                listhistory.add(currnumber + "解锁");
                playVideo("send");
                list.clear();
                break;
        }

    }

    @Override
    protected void onDestroy() {
        try {
            player.stop();
            player.release();
            player = null;
        } catch (Exception e) {

        }

        super.onDestroy();
    }

    @Override
    protected void onStop() {
        unregisterReceiver(mMb);//刚添加的;
        super.onStop();
    }

    private void stopVideo() {
        try {
            player.stop();
            player.release();
            player = null;
        } catch (Exception e) {

        }
        Runtime.getRuntime().gc();
    }

    volatile MediaPlayer player = null;


    Runnable send_over_rrr = new Runnable() {
        @Override
        public void run() {
            if (send_over == 1) {
            } else {
                playVideo("error");
            }
        }
    };
    int send_over = 0;

    private void playVideo(String s) {
        if (player != null) {
        } else {
            player = new MediaPlayer();
        }
        switch (s) {
            case "send":
                send_over = 1;
                player = MediaPlayer.create(this, R.raw.send);
                break;
            case "y":
                player = MediaPlayer.create(this, R.raw.yellow);
                break;
            case "g":
                player = MediaPlayer.create(this, R.raw.green);
                break;
            case "r":
                player = MediaPlayer.create(this, R.raw.red);
                break;
            case "error":
                player = MediaPlayer.create(this, R.raw.error);
                break;
        }
        player.start();
    }

    public void sendMessage(String s) {
        TIMMessage msg = new TIMMessage();
        //添加文本内容
        TIMTextElem elem = new TIMTextElem();
        elem.setText(s);
        if (msg.addElement(elem) != 0) {
            Log.d("wocao", "addElement failed");
            return;
        }


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
        msg = null;
    }


    public void startSend(String str) {
        sendMessage(str);
    }

    public static void receiveMessage(String str) {
        refreshLogView(str);
    }

    public static void refreshLogView(String msg) {
        receive.append(msg + "\n");
        int offset = receive.getLineCount() * receive.getLineHeight();
        if (offset > receive.getHeight()) {
            receive.scrollTo(0, offset - receive.getHeight());
        }
    }

    private class SendThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                try {
                    sendMessage("");
                } catch (Exception e) {
                    Log.d("error", "cat GPIO error");
                }
            }
        }
    }

    private String xn = "xining";

    //进群
    private void JoinGroup(String id) {
        TIMGroupManager.getInstance().applyJoinGroup(xn + id, "", new TIMCallBack() {
            @Override
            public void onError(int i, String s) {
            }

            @Override
            public void onSuccess() {
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

    //建群
    private void CreatGroup(String type, String name, String id) {
        TIMGroupManager.getInstance().createGroup(new TIMGroupManager.CreateGroupParam(type, name).setGroupId(id), new TIMValueCallBack<String>() {
            @Override
            public void onError(int i, String s) {
            }

            @Override
            public void onSuccess(String s) {
            }
        });
    }


    public static String time_date = "";

    public void DisplayDiaodanLayout(String str) {
        receive.setVisibility(View.GONE);
        Log.e("swy", str);
        mList = new ArrayList<>();
        ArrayList<String> diaocan_list = new ArrayList<>();
        try {
            if (combineCommend.CRC_Test(str)) {
                diaocan_list.addAll(combineCommend.Decode_diaodan(str));
            } else {
            }
            mList.add("第" + diaocan_list.get(5) + "号  机车: " + diaocan_list.get(11) + "\r\n" + "编制人: " + diaocan_list.get(8) + "--调车长: " + diaocan_list.get(9) + "\r\n" + "计划内容: " + diaocan_list.get(10) + "\r\n" + "计划时间: 自" + diaocan_list.get(6).substring(0, 2) + "时" + diaocan_list.get(6).substring(2, 4) + "分" + "至" + diaocan_list.get(7).substring(0, 2) + "时" + diaocan_list.get(6).substring(2, 4) + "分");
            Log.e("swy", diaocan_list.get(1));
            for (int i = 0; i < Integer.valueOf(diaocan_list.get(2)); i++) {
                mList.add(i + 1 + "," + diaocan_list.get(13 + (i * 3)) + "," + diaocan_list.get(14 + (i * 3)).substring(0, 1) + "," + diaocan_list.get(14 + (i * 3)).substring(1) + "," + diaocan_list.get(15 + (i * 3)));//红色 #FF0000 //牡丹红 #FF00FF
            }
            Log.e("swy", "11");
            report_detail_recyclerview = (RecyclerView) findViewById(R.id.report_detail_recyclerview);
            /*report_detail_recyclerview.setLayoutManager(new LinearLayoutManager(this));
            report_detail_recyclerview.setAdapter(new ReportDetailAdapter(this,mList));*/

            //设置layoutmanager
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            report_detail_recyclerview.setLayoutManager(layoutManager);

            //设置adapter
            detailAdapter = new ReportDetailAdapter(this, mList);
            report_detail_recyclerview.setAdapter(detailAdapter);
            TextView tv_jishi_content = findViewById(R.id.tv_jishi_content);
            time_date = diaocan_list.get(1);
            tv_jishi_content.setText("注意事项: " + diaocan_list.get(12) + "\n\r" + "编制时间: " + diaocan_list.get(1).substring(0, 2) + "年" + diaocan_list.get(1).substring(2, 4) + "月" + diaocan_list.get(1).substring(4, 6) + "日" + diaocan_list.get(1).substring(6, 8) + "时" + diaocan_list.get(1).substring(8, 10) + "分" + diaocan_list.get(1).substring(10, 12) + "秒");

            clickListener();
        } catch (Exception e) {
        }

    }

    public void initViews() {

        report_detail_recyclerview = (RecyclerView) findViewById(R.id.report_detail_recyclerview);
        //设置layoutmanager
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        report_detail_recyclerview.setLayoutManager(layoutManager);
        //设置adapter
        detailAdapter = new ReportDetailAdapter(this, mList);
        report_detail_recyclerview.setAdapter(detailAdapter);
        System.out.println(",,,,,,2");
    }


    private void clickListener() {
        detailAdapter.setOnItemClickListener(new ReportDetailAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(final int position, final int setcolor) {
            }
        });
    }


    //心跳
    Handler handler_xintiao = new Handler();
    public static int xintiao_index = 2;
    private int xintiao_count = 0;
    public static ArrayList<String> xintiao_list = new ArrayList<>();
    Runnable xintiaorunnable = new Runnable() {
        @Override
        public void run() {
            if (xintiao_index == 1) {
                sendMessage("1");
                xintiao_list.add("1");
                handler_xintiao.postDelayed(this, 5000);
            } else {
                xintiao_count = 0;
                xintiao_list.clear();
                handler_xintiao.removeCallbacks(xintiaorunnable);
            }

        }
    };


    //监听心跳
    private class XintiaoReadThread extends Thread {
        @Override
        public void run() {
            super.run();
            while (!isInterrupted()) {
                try {
                    if (xintiao_list.size() != xintiao_count && xintiao_index == 1) {//相同不处理
                        xintiao_count = xintiao_list.size();
                        switch (xintiao_list.size()) {
                            case 0:
                                break;
                            case 1:
                                break;
                            case 2:
                                if (xintiao_list.get(xintiao_list.size() - 1).matches("1") && xintiao_list.get(xintiao_list.size() - 2).matches("1")) {
                                    sendMessage("注意注意");
                                }
                                break;
                            default:
                                if (xintiao_list.get(xintiao_list.size() - 1).matches("1") && xintiao_list.get(xintiao_list.size() - 2).matches("1") && xintiao_list.get(xintiao_list.size() - 3).matches("1") && xintiao_index == 1) {
                                    sendMessage("停车停车");
                                    xintiao_index = 2;
                                } else if (xintiao_list.get(xintiao_list.size() - 1).matches("1") && xintiao_list.get(xintiao_list.size() - 2).matches("1") && xintiao_index == 1) {
                                    sendMessage("注意注意");
                                }
                                break;
                        }
                    }

                } catch (Exception e) {
                }
            }
        }
    }

    private class DeleteDatabase extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            mList.clear();
            db.DiaodanDAO().deleteAll();
            return null;
        }

        protected void onPostExecute(Void param) {
        }
    }

    public static ArrayList<String> gou_list = new ArrayList<>();

    private class ListDatabase extends AsyncTask<Void, Void, String> {
        @Override
        protected String doInBackground(Void... params) {
            gou_list.clear();
            List<Diaodan> users = db.DiaodanDAO().getAll();
            if (!(users.isEmpty() || users == null)) {
                String allUsers = "";
                String refresh_data = "";
                for (Diaodan temp : users) {
                    //String userstr = temp.gou_number;
                    if (SendActivity.time_date.matches(temp.getCurrent_time())) {
                        allUsers = temp.gou_number;
                        ;
                        refresh_data = temp.str;
                    }
                }
                String[] strings = allUsers.split("-");
                for (int i = 0; i < strings.length; i++) {
                    gou_list.add(strings[i]);
                }
                System.out.println(gou_list + "111111111111");
                return refresh_data;
            } else
                return "";
        }

        @Override
        protected void onPostExecute(String details) {
            if (mList.isEmpty()) {
                DisplayDiaodanLayout(details);
            } else {
                initViews();
            }
        }
    }

}
