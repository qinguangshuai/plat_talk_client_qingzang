package com.kylindev.totalk.app;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;

import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.mylibrary.RouterURLS;
import com.example.mylibrary.TestService;
import com.example.mylibrary.db.MyLocation;
import com.kylindev.totalk.R;


import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Set;

//@Route(path = RouterURLS.BASE_MAIN)
public class ControlActivity extends BaseActivity implements View.OnClickListener {

    static EditText et_rear;
    static EditText et_train;
    EditText et_confirm;
    TextView tv_version;
    static TextView tv_wind;
    TextView tv_track;
    TextView tv_gps;
    static TextView tv_show,tv_receive;
    static Button bt_rear,bt_jiche,bt_chezhan;
    static Button bt_confirm;
    Button call_train;
    Button call_station;
    static Button bt_remove;
    static Context context;

    static SharedPreferences controlsp;
    String bt = "",channal ="1";

    public static SoundPool soundPool;

    public int soundId_jiche,soundId_dengdai, soundId_xiaohao, soundId_fengya, soundId_wanbi, soundId_queren,soundId_0, soundId_1, soundId_2, soundId_3, soundId_4, soundId_5, soundId_6, soundId_7, soundId_8, soundId_9;

    HashMap<String, ArrayList<MyLocation>> locs = new HashMap<>();

    public static ControlActivity getControl() {
        return (ControlActivity) context;
    }
    private ProgressDialog pDialog;

    private int sounfId_hujiche, soundId_huchezhan;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
//        setContentView(R.layout.activity_control);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE |
                WindowManager.LayoutParams.SOFT_INPUT_STATE_HIDDEN);
        this.setIsMap(false);
        initView();


    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            Intent intent1 = new Intent(ControlActivity.this, CheckActivity.class);
            intent1.addFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
            startActivity(intent1);
        }
        return true;
    }

    @Override
    protected void serviceConnected() {

    }


    @Override
    protected int getContentViewId() {
        return R.layout.activity_control;
    }


    static void refreshLogView(String msg){
        tv_receive.append(msg+"\n");
            int offset=tv_receive.getLineCount()*tv_receive.getLineHeight();
            if(offset>tv_receive.getHeight()){
                tv_receive.scrollTo(0,offset-tv_receive.getHeight());
                }
         }

    private void initView() {
    }

    static int start = 0;
    public static void setstart(){
        start = 1;
    }
    public static int getstart(){
        return start;
    }

    @Autowired(name = RouterURLS.BASE_MAIN)
    TestService app;

    void sendMessage(String uid, String s) {
        ARouter.getInstance().inject(this);

        if (app != null) {
            app.sendMessage(uid, s);
        }

    }


    public static void receiveMessage(String str){
            refreshLogView(str+"\n");
            if (!str.isEmpty()){
                switch (str){
                    case "无指令返回":
                        break;
                    case "查询风压":
                        break;
                    case "风压":

                        break;
                    case "风压报警":

                        break;
                    case "确认输号":

                        break;
                    case "列尾确认完毕":

                        break;
                    case "销号":

                        break;
                    case "查询日期":

                        break;
                    case "列尾等待确认":

                        break;
                    case "列尾已连接":

                        break;
                    case "销号完毕":

                        break;
                    case "已销号":
                        Toast.makeText(ControlActivity.context,"已销号",Toast.LENGTH_SHORT).show();
                        break;
                    case "电池报警":
                        Toast.makeText(ControlActivity.context,"电池报警",Toast.LENGTH_SHORT).show();
                        break;
                }

            }else {

            }

    }


    void test() {
        ARouter.getInstance().inject(this);

        if (app != null) {
            app.showToast(getApplicationContext());
        }
    }


    //加载音频文件

    static LinkedHashMap<Integer, Integer> soundIdMap = new LinkedHashMap<>();

    private static Integer[] loadRaw(SoundPool soundPool, Context context, int raw) {
        int soundId = soundPool.load(context, raw, 1);
        int duration = getMp3Duration(context, raw);
        return new Integer[]{soundId, duration};
    }

    //获取音频文件的时长
    private static int getMp3Duration(Context context, int rawId) {
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
    private static class PlayThread extends Thread {
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
        }
    }

    private void loadmusic(){
    }


    private static void Select(String str) {
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
        }
    }


    private void setDialogText(View v) {

        if (v instanceof ViewGroup) {
            ViewGroup parent = (ViewGroup) v;
            int count = parent.getChildCount();
            for (int i = 0; i < count; i++) {
                View child = parent.getChildAt(i);
                setDialogText(child);
            }
        } else if (v instanceof TextView) {
            ((TextView) v).setTextSize(40);
        }
    }

}
