package com.kylindev.totalk.app;

import android.Manifest;
import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.bluetooth.BluetoothDevice;
import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.os.RemoteException;
import android.provider.Settings;

import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.Surface;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.mylibrary.RouterURLS;
import com.example.mylibrary.TestService;
import com.kylindev.pttlib.LibConstants;
import com.kylindev.pttlib.service.BaseServiceObserver;
import com.kylindev.pttlib.service.InterpttProtocolHandler.DisconnectReason;
import com.kylindev.pttlib.service.InterpttService;
import com.kylindev.pttlib.service.InterpttService.HandmicState;
import com.kylindev.pttlib.service.InterpttService.HeadsetState;
import com.kylindev.pttlib.service.InterpttService.LocalBinder;
import com.kylindev.pttlib.service.InterpttService.MicState;
import com.kylindev.pttlib.service.model.Channel;
import com.kylindev.pttlib.service.model.Contact;
import com.kylindev.pttlib.service.model.User;
import com.kylindev.totalk.AppConstants;
import com.kylindev.totalk.IO.GPIO;
import com.kylindev.totalk.R;
import com.kylindev.totalk.bjxt.SpUtil;
import com.kylindev.totalk.chat.RecyclerViewChatActivity;
import com.kylindev.totalk.qgs.GPSDao;
import com.kylindev.totalk.qgs.PointActivity;
import com.kylindev.totalk.qgs.database.six.SixDataDao;
import com.kylindev.totalk.utils.AppCommonUtil;
import com.kylindev.totalk.utils.AppSettings;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import io.github.prototypez.appjoint.AppJoint;
import util.ByteUtil;
import util.HexUtil;

import static com.kylindev.pttlib.LibConstants.INTERPTT_SERVICE;
import static com.kylindev.pttlib.service.InterpttService.ConnState;
import static com.kylindev.pttlib.service.InterpttService.ConnState.CONNECTION_STATE_CONNECTED;
import static com.kylindev.pttlib.service.InterpttService.ConnState.CONNECTION_STATE_CONNECTING;
import static com.kylindev.pttlib.service.InterpttService.ConnState.CONNECTION_STATE_SYNCHRONIZING;
import static com.kylindev.totalk.app.SerialPortActivity.sendHexString;

public abstract class BaseActivity extends Activity implements OnClickListener {

    /**
     * The InterpttService instance that drives this activity's data.
     */
    public static InterpttService mService;
    // Create dialog
    //private ProgressDialog mConnectDialog = null;
    protected Intent mServiceIntent = null;

    private Handler mHandler = new Handler();    //用于其他线程更新UI

    //顶部
    protected ImageView mIVBarLeft, mIVBarRight, mIVBarLeftInner, mIVBarRightInner;
    protected TextView mTVBarTitle, mTVBarCount;
    protected ViewGroup activityView; //子类布局

    public ImageView mIVVoice;
    private TextView mTVConnect;
    private TextView mTVCurrentChanName, mTVCurrentChanTalker, mTVCurrentChanTalkerBg;
    private TextView mTVListenChanName, mTVListenChanTalker;
    private ImageView mIVCurrentChanTalkerDevice, mIVListenChanTalkerDevice;
    private TextView mTVHistoryTalker0, mTVHistoryTalker1, mTVHistoryTalker2;

    //以下是ptt相关界面
    private TextView mTVDebug;
    private ImageView mIVPttCirc, mIVPtt;
    public ImageView mIVHistory;
    public ImageView mIVHeadset;
    public ImageView mIVHandmic;
    private TextView mTVPttTimer;
    protected LinearLayout mLLlcd;
    private ImageView mIVHoriVolume;
    protected boolean mServiceBind = false;        //有时在unbindService时报错java.lang.IllegalArgumentException:  Service not registered，因此增加此变量unbind之前判断

    private boolean isScreenOn = false;
    private boolean isMap = false;
    protected LinearLayout mLLPttArea;
    /**
     * Management of service connection state.
     */
    protected ServiceConnection mServiceConnection = null;
    private static boolean actived = false;
    private boolean next = false;
    private static boolean bug = true;
    private SpUtil mFirstInto;
    //控制
    String gpioValue = "";
    FileReader fileReader;
    BufferedReader reader;
    private IOReadThread ioReadThread;
    /*private String mEncodeHexStr;
    private String mData;*/

    /*@Autowired(name = RouterURLS.BASE_MAIN)
    TestService app;
    private SpUtil mSpUtil;

    void sendMessage(String uid, String s) {
        ARouter.getInstance().inject(this);
        if (app != null) {
            app.sendMessage(uid, s);
        }
    }

    @Override
    protected void onDataReceived(final byte[] buffer, final int size, final int type) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (type == 485) {
                        String toString = buffer.toString();
                        char[] chars = HexUtil.encodeHex(buffer);
                        mEncodeHexStr = ByteUtil.bytes2HexString(buffer, size);
                        mData = mEncodeHexStr.replaceAll(" ", "");
                        String header = mData.substring(0, 2);
                        int length = mData.length();
                        String sum = mData.substring(length - 2, length);
                        Log.e("mEncodeHexStr", mEncodeHexStr + "");
                        if (header.equals("A5") && length == 14) {
                            int total = 0;
                            for (int i = 0; i < mData.length() - 2; i += 2) {
                                //strB.append("0x").append(strData.substring(i,i+2));  //0xC30x3C0x010x120x340x560x780xAA
                                total = total + Integer.parseInt(mData.substring(i, i + 2), 16);
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
                            if (sum.equals(key)) {
                                sendHexString(mEncodeHexStr.replaceAll("\\s*", ""), "485");
                            }
                        }
                    } else if (type == 4852) {
                        String toString = buffer.toString();
                        char[] chars = HexUtil.encodeHex(buffer);
                        mEncodeHexStr = ByteUtil.bytes2HexString(buffer, size);
                        mData = mEncodeHexStr.replaceAll(" ", "");
                        String head = mData.substring(0, 6);
                        int length = mData.length();
                        String sum = mData.substring(length - 2, length);
                        Log.e("二宝", mEncodeHexStr + "");
                        String notice = mSpUtil.getNotice();
                        if (notice.equals("启动")) {
                            Log.e("GGA数据", mEncodeHexStr + "");
                            if (head.equals("474741")) {
                                String data485 = HexUtil.hexStr2Str(mData);
                                String substring = data485.substring(0, data485.indexOf(","));
                                if (substring.equals("GGA")) {
                                    //截取纬度、N/S指示、经度
                                    String comma = data485.substring(data485.indexOf(",", data485.indexOf(",") + 1) + 1);
                                    //获取纬度
                                    String latitude = comma.substring(0, comma.indexOf(","));
                                    //获取经度
                                    String longitude = comma.substring(comma.indexOf(",", comma.indexOf(",") + 1) + 1);
                                    //DecimalFormat 是 NumberFormat 的一个具体子类，用于格式化十进制数字。
                                    //DecimalFormat 包含一个模式 和一组符号
                                    DecimalFormat df = new DecimalFormat("#.000000");
                                    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                                    df.setDecimalFormatSymbols(symbols);
                                    String lat = df.format(Double.valueOf(latitude));
                                    String lon = df.format(Double.valueOf(longitude));
                                    String lat1 = lat.substring(lat.indexOf(".") + 1);
                                    String lon1 = lon.substring(lon.indexOf(".") + 1);
                                    String total = "0A-机车GPS-" + lat1 + "-" + lon1;
                                    TestService service = AppJoint.service(TestService.class);
                                    service.sendMessage("01", total);
                                    *//*String name = mSpUtil.getName();
                                    if (name != null) {
                                        service.sendMessage(name, total);
                                    } else {
                                        service.sendMessage("01", total);
                                    }*//*
                                }
                            }
                        } else {
                            //Toast.makeText(getApplication(), "您已停车", Toast.LENGTH_SHORT).show();
                        }
                    }
                } catch (Exception e) {
                    Log.e("数据异常", "数据异常：" + e);
                }
            }
        });
    }*/

    private void initServiceConnection() {
        mServiceConnection = new ServiceConnection() {
            @Override
            public void onServiceConnected(ComponentName name, IBinder service) {
                LocalBinder localBinder = (LocalBinder) service;
                mService = localBinder.getService();
                serviceConnected(); //通知子类

                mService.registerObserver(serviceObserver);

                //channel界面出现过一次后，才设置notification对应的activity
//                Intent notifIntent = new Intent(MainApp.getContext(), ChannelActivity.class);
//                mService.setNotifIntent(notifIntent);

                refreshLcdChannel();
                //设置ptt界面的初始状态
                refreshHeadsetIcon();
                refreshHandmicIcon();
                //初始化声音开关
                boolean isOn = mService.getVoiceOn();
                mIVVoice.setImageResource(isOn ? R.drawable.voice_on : R.drawable.voice_off);

                refreshMicState(mService.getMicState());
                //设置连接提示是否可见
                showConnection();

            }

            //此方法调用时机：This is called when the connection with the service has been unexpectedly disconnected
            //-- that is, its process crashed. Because it is running in our same process, we should never see this happen.
            @Override
            public void onServiceDisconnected(ComponentName name) {
                mService = null;
                mServiceConnection = null;
                stopService(mServiceIntent);

                //此函数只有在service被异常停止时才会调用，如被系统或其他软件强行停止
                finish();
            }
        };
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_base);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);

        mTVConnect = (TextView) findViewById(R.id.tv_connection_lost);
        mTVConnect.setOnClickListener(this);

        //顶部栏
        mIVBarLeft = (ImageView) findViewById(R.id.iv_bar_left);
        mIVBarRight = (ImageView) findViewById(R.id.iv_bar_right);
        mIVBarLeftInner = (ImageView) findViewById(R.id.iv_bar_left_inner);
        mIVBarRightInner = (ImageView) findViewById(R.id.iv_bar_right_innner);
        mTVBarTitle = (TextView) findViewById(R.id.tv_bar_title);
        mTVBarTitle.setText("北交信通调度");
        mTVBarCount = (TextView) findViewById(R.id.tv_bar_count);
        activityView = (ViewGroup) findViewById(R.id.fl_act_content);
        activityView.addView(View.inflate(this, getContentViewId(), null));
        mLLlcd = (LinearLayout) findViewById(R.id.ll_lcd);

        mTVCurrentChanName = (TextView) findViewById(R.id.tv_current_chan_name);
        mIVCurrentChanTalkerDevice = (ImageView) findViewById(R.id.iv_current_talker_device);
        mTVCurrentChanTalker = (TextView) findViewById(R.id.tv_current_chan_talker);

        mTVListenChanName = (TextView) findViewById(R.id.tv_listen_chan_name);
        mIVListenChanTalkerDevice = (ImageView) findViewById(R.id.iv_listen_talker_device);
        mTVListenChanTalker = (TextView) findViewById(R.id.tv_listen_chan_talker);

        mTVHistoryTalker0 = (TextView) findViewById(R.id.tv_history_talker0);
        mTVHistoryTalker1 = (TextView) findViewById(R.id.tv_history_talker1);
        mTVHistoryTalker2 = (TextView) findViewById(R.id.tv_history_talker2);

        //可能是首次运行，也可能是用户重新launch
        //因此，先检查service是否在运行，如果是，则直接bind以获取mService实例；如果没有，则startService，再bind
        mServiceIntent = new Intent(this, InterpttService.class);

        if (!AppCommonUtil.isServiceRunning(this, INTERPTT_SERVICE)) {
            startService(mServiceIntent);
        }

        initServiceConnection();
        mServiceBind = bindService(mServiceIntent, mServiceConnection, 0);

        // Handle differences in CallMode
        setVolumeControlStream(AudioManager.STREAM_MUSIC);

        mIVVoice = (ImageView) findViewById(R.id.iv_voice_switch);
        mIVVoice.setOnClickListener(this);

        //debug按钮
        mTVDebug = (TextView) findViewById(R.id.tv_debug);
        if (AppConstants.DEBUG) {
            mTVDebug.setVisibility(View.VISIBLE);
            mTVDebug.setOnClickListener(this);
        }


        // Set up PTT button.
        mLLPttArea = findViewById(R.id.ll_ptt_area);
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mLLPttArea.getLayoutParams();
        WindowManager wm = this.getWindowManager();
        params.height = wm.getDefaultDisplay().getHeight() * 36 / 180;
        mLLPttArea.setLayoutParams(params);
        mTVPttTimer = (TextView) findViewById(R.id.tv_ptt_timer);
        mIVPtt = (ImageView) findViewById(R.id.iv_ptt);
        mIVPttCirc = (ImageView) findViewById(R.id.iv_ptt_circle);
        FrameLayout flPtt = (FrameLayout) findViewById(R.id.fl_ptt);
        findViewById(R.id.fl_ptt);
        flPtt.setOnTouchListener(new View.OnTouchListener() {
            //private static final int TOGGLE_INTERVAL = 250; // 250ms is the interval needed to toggle push to talk.
            //private long lastTouch = 0;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (mService == null) {
                    return false;
                }

                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                        if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                            ArrayList<String> permissions = new ArrayList<String>();
                            permissions.add(Manifest.permission.RECORD_AUDIO);
                            requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
                        } else {
                            mService.userPressDown();
                        }
                    } else {
                        mService.userPressDown();
                    }
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    mService.userPressUp();
                } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
                    mService.userPressUp();
                }

                return true; // We return true so that the selector that changes the background does not fire.
            }
        });

        mIVHoriVolume = (ImageView) findViewById(R.id.iv_volume);

        mIVHistory = (ImageView) findViewById(R.id.iv_history);
        mIVHistory.setOnClickListener(this);
        mIVHeadset = (ImageView) findViewById(R.id.iv_headset);
        mIVHeadset.setOnClickListener(this);
        mIVHandmic = (ImageView) findViewById(R.id.iv_handmic);
        mIVHandmic.setOnClickListener(this);

        //mSpUtil = new SpUtil(getApplicationContext(), "instructions");

        //首次运行，显示帮助信息
        boolean firstUse = AppSettings.getInstance(this).getFirstUse();
        if (firstUse) {
            help();
            AppSettings.getInstance(this).setFirstUse(false);
        }

        getPermissions();

        //mFirstInto = new SpUtil(getApplicationContext(), "firstinto");

        //GPIO.gpio_crtl_in(132, 1);
        ioReadThread = new IOReadThread();//监听pe2io口
        ioReadThread.start();
    }

    public void setIsMap(boolean isMapOrNo) {
        isMap = isMapOrNo;
    }


    public void touchPtt(MotionEvent event) {
        if (mService == null) {
            return;
        }

        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (checkSelfPermission(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    ArrayList<String> permissions = new ArrayList<String>();
                    permissions.add(Manifest.permission.RECORD_AUDIO);
                    requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
                } else {
                    mService.userPressDown();
                }
            } else {
                mService.userPressDown();
            }
        } else if (event.getAction() == MotionEvent.ACTION_UP) {
            mService.userPressUp();
        } else if (event.getAction() == MotionEvent.ACTION_CANCEL) {
            mService.userPressUp();
        }
    }

    @Override
    public void onResume() {
        Intent bi = new Intent();
        bi.setAction(LibConstants.ACTION_FLOAT_WINDOW_HIDE);
        this.sendBroadcast(bi);
        isScreenOn = true;

        //mService.activityShowing(false);
        //关闭悬窗。在serviceConnected和onResume都需要调用close，因为resume时可能service仍为空，而按home再进入时，不会调用bindService
        if (mService != null) {
            //声音开关在pause期间，可能被改变了
            boolean voiceOn = mService.getVoiceOn();
            mIVVoice.setImageResource(voiceOn ? R.drawable.voice_on : R.drawable.voice_off);
        }

        super.onResume();
    }

    @Override
    public void onPause() {
        super.onPause();

        isScreenOn = false;
        Intent bi = new Intent();
        bi.setAction(LibConstants.ACTION_FLOAT_WINDOW_SHOW);
        this.sendBroadcast(bi);
    }

    @Override
    protected void onDestroy() {

        // Unbind to service
        //为保证service不被杀死，activity在back按键时，只pause，不destroy。
        //那么，如果发现destroy，则应检查是否是用户关闭的。如果不是，则应重新启动activity
        //此时，说明activity不是用户退出的，而是被系统或其他应用杀死的。
        //应通知service，让其稍后重启activity

        if (mService != null) {
            mService.unregisterObserver(serviceObserver);
            if (mServiceConnection != null) {
                if (mServiceBind) {
                    unbindService(mServiceConnection);
                }
                mServiceConnection = null;
            }

            mService = null;
        }

        super.onDestroy();

        //mFirstInto.setName("false");
        //mFirstInto.setName("false");
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        Log.e("wocao", "touch" + keyCode);
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            boolean selecting = mService != null && mService.isSelectingContact();
            if (selecting) {
                mService.cancelSelect();
                return true;
            }
        } else {
            if (mService != null) {
                int savedCode = mService.getPttKeycode();
                if (savedCode != 0 && savedCode == keyCode) {
                    //至此，说明需要响应ptt事件
                    Log.e("wocao", "fule" + keyCode);
                    mService.userPressDown();
                    return true;
                }
            } else {
                return false;
            }
        }

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (mService != null) {
            int savedCode = mService.getPttKeycode();
            if (savedCode != 0 && savedCode == keyCode) {
                mService.userPressUp();
                super.onKeyUp(keyCode, event);
                return true;
            }
        }

        return super.onKeyUp(keyCode, event);
    }

    protected abstract void serviceConnected();

    protected abstract int getContentViewId();

    ////////////////////////////////
    private BaseServiceObserver serviceObserver = new BaseServiceObserver() {
        public void onConnectionStateChanged(ConnState state) throws RemoteException {
            switch (state) {
                case CONNECTION_STATE_CONNECTING:
                    break;
                case CONNECTION_STATE_SYNCHRONIZING:
                    break;
                case CONNECTION_STATE_CONNECTED:
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            mTVConnect.setVisibility(View.GONE);
                        }
                    });
                    break;
                case CONNECTION_STATE_DISCONNECTED:
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            refreshVolumeWave((short) 0);
                        }
                    });
                    break;
            }
            mHandler.post(new Runnable() {
                @Override
                public void run() {
                    if (mService != null) {
                        refreshMicState(mService.getMicState());
                        refreshLcdChannel();
                        showConnection();
                    }
                }
            });

            if (mService != null) {
                mService.userPressUp();
            }
        }

        @Override
        public void onPermissionDenied(String reason, int denyType) throws RemoteException {
            AppCommonUtil.showToast(BaseActivity.this, AppConstants.permReason[denyType]);
        }

        @Override
        public void onCurrentChannelChanged() throws RemoteException {
            if (mService == null) {
                return;
            }

            if (mService.getConnectionState() == CONNECTION_STATE_CONNECTED) {
                refreshLcdChannel();
                //zcx add
                refreshMicState(mService.getMicState());
                //如果按着ptt时切换频道，则需先放弃讲话
                mService.userPressUp();
            }
        }

        @Override
        public void onChannelAdded(Channel channel) throws RemoteException {
        }

        @Override
        public void onChannelRemoved(Channel channel) throws RemoteException {
        }

        //某个频道信息有变化
        @Override
        public void onChannelUpdated(Channel channel) throws RemoteException {
        }

        @Override
        public void onUserUpdated(User user) throws RemoteException {
        }

        @Override
        public void onUserTalkingChanged(User user, boolean talk) throws RemoteException {
        }

        @Override
        public void onLocalUserTalkingChanged(User user, boolean talk) throws RemoteException {
            //refreshLcdChannel();
            if (!actived && bug) {
                Log.e("bug", bug + "  123");
                if (talk && !next) {
                    Log.i("next", next + "  123");
                    /*mSp = new SpUtil(getApplication(),"itcast");
                    mSp.setName("true");*/
                    //String dat = "A5 01 0C 21 01 00 2C";
                    String dat = "AA 55 01";
                    sendHexString(dat.replaceAll("\\s*", ""), "485");
                    Log.i("PTT_ON", "AA 55 01" + "  有载波" + talk);
                    next = true;
                } else if (!talk && next) {
                    /*mSp = new SpUtil(getApplication(),"itcast");
                    mSp.setName("false");*/
                    String date = "AA 55 02";
                    sendHexString(date.replaceAll("\\s*", ""), "485");
                    Log.i("PTT_OFF", "AA 55 02" + "  无载波" + talk);
                    next = false;
                }
                actived = false;
                refreshLcdChannel();
            }
        }

        @Override
        public void onNewVolumeData(short volume) throws RemoteException {
            refreshVolumeWave(volume);
        }

        @Override
        public void onMicStateChanged(MicState s) throws RemoteException {
            refreshMicState(s);
        }

        @Override
        public void onHeadsetStateChanged(HeadsetState s) throws RemoteException {
            refreshHeadsetIcon();
        }

        @Override
        public void onScoStateChanged(int s) throws RemoteException {
            refreshHeadsetIcon();
        }

        @Override
        public void onTargetHandmicStateChanged(BluetoothDevice device, HandmicState s) throws RemoteException {
            refreshHandmicIcon();
        }

        @Override
        public void onTalkingTimerTick(int seconds) throws RemoteException {
            String time = String.format("%02d", seconds / 60) + ":" + String.format("%02d", seconds % 60);
            mTVPttTimer.setText(time);
        }

        @Override
        public void onTalkingTimerCanceled() throws RemoteException {
            mTVPttTimer.setText(getString(R.string.push_to_talk));
        }

        @Override
        public void onUserAdded(final User u) throws RemoteException {
        }

        @Override
        public void onUserRemoved(final User user) throws RemoteException {
        }

        @Override
        public void onLeDeviceScanStarted(boolean start) throws RemoteException {
            refreshHandmicIcon();
        }

        @Override
        public void onShowToast(final String str) throws RemoteException {
            AppCommonUtil.showToast(BaseActivity.this, str);
        }

        @Override
        public void onInvited(final Channel chan) throws RemoteException {
            //mayNotifyInvitation();
        }

        @Override
        public void onUserSearched(User user) throws RemoteException {

        }

        @Override
        public void onPendingMemberChanged() throws RemoteException {
        }

        @Override
        public void onApplyContactReceived(boolean add, Contact contact) throws RemoteException {
        }

        @Override
        public void onPendingContactChanged() throws RemoteException {
        }

        @Override
        public void onContactChanged() throws RemoteException {

        }

        @Override
        public void onSynced() throws RemoteException {

        }

        @Override
        public void onVoiceToggleChanged(boolean on) throws RemoteException {
            mIVVoice.setImageResource(on ? R.drawable.voice_on : R.drawable.voice_off);
        }

        @Override
        public void onListenChanged(boolean listen) throws RemoteException {
            refreshLcdChannel();
        }

        @Override
        public void onApplyOrderResult(int uid, int cid, String phone, boolean success) throws RemoteException {

        }

        @Override
        public void onUserOrderCall(final User user, boolean talk, String number) {
        }

        @Override
        public void onGeneralMessageGot(final int type, final String content) throws RemoteException {
        }
    };

    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub
        int id = v.getId();

        if (id == R.id.iv_voice_switch) {
            if (mService != null) {
                mService.toggleVoiceOn();
            }
        } else if (id == R.id.iv_handmic) {
            handMicActivity();
        } else if (id == R.id.iv_history) {
            if (mService != null && mService.getCurrentChannel() != null) {
                Intent intentshowChatUi = new Intent(this, RecyclerViewChatActivity.class);
                //同时启动chat界面
                intentshowChatUi.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                if (isMap) {
                    intentshowChatUi.putExtra("SOURCE", "OPEN");
                } else {
                    if (mService.getAllowAcceptOrder()) {
                        intentshowChatUi.putExtra("SOURCE", "OPEN");
                    } else {
                        intentshowChatUi.putExtra("SOURCE", "NULL");
                    }
                }
                startActivity(intentshowChatUi);
            }
        } else if (id == R.id.iv_headset) {
            Intent intent = new Intent();
            intent.setAction(Settings.ACTION_BLUETOOTH_SETTINGS);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            try {
                startActivity(intent);
            } catch (ActivityNotFoundException ex) {
                ex.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (id == R.id.tv_connection_lost) {
            help();
        } else if (id == R.id.tv_debug) {
            String debugInfo = mService.getDebug();
            EditText et = new EditText(BaseActivity.this);
            et.setText(debugInfo);
            et.setTextSize(12);
            new AlertDialog.Builder(BaseActivity.this)
                    .setView(et)
                    .setPositiveButton(getString(R.string.close), null)
                    .show();
        }
    }

    protected void handMicActivity() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            Intent i = new Intent(BaseActivity.this, DeviceScanActivity.class);
            startActivity(i);
        } else {
            AppCommonUtil.showToast(this, "蓝牙外设只支持Android 4.3及以上版本");
        }
    }

    private void refreshHeadsetIcon() {
        if (mService == null) {
            return;
        }
        int sco = mService.getScoState();
        if (sco == AudioManager.SCO_AUDIO_STATE_CONNECTED) {
            mIVHeadset.setImageResource(R.drawable.headset_recording);
        } else {
            HeadsetState s = mService.getHeadsetState();
            if (s == HeadsetState.HEADSET_CONNECTED) {
                mIVHeadset.setImageResource(R.drawable.headset_connected);
                boolean scoRecording = mService.getSupportScoRecording();
            } else {
                mIVHeadset.setImageResource(R.drawable.headset_disconnected);
            }
        }
    }

    private void refreshHandmicIcon() {
        if (mService == null) {
            return;
        }

        InterpttService.HandmicState s = mService.getTargetHandmicState();
        switch (s) {
            case HANDMIC_CONNECTED:
                mIVHandmic.setImageResource(R.drawable.handmic_connected);
                mIVHandmic.clearAnimation();
                break;
            case HANDMIC_CONNECTING:
                mIVHandmic.setImageResource(R.drawable.handmic_connected);
                mIVHandmic.startAnimation(AppCommonUtil.createTalkingAnimation());
                break;
            case HANDMIC_DISCONNECTED:
                mIVHandmic.setImageResource(R.drawable.handmic_disconnected);
                //再看是否在扫描期间
                boolean scanning = mService.getIsBleScanning();
                if (scanning) {
                    mIVHandmic.startAnimation(AppCommonUtil.createTalkingAnimation());
                } else {
                    mIVHandmic.clearAnimation();
                }
                break;
        }
    }

    private void refreshMicState(MicState state) {
        boolean ready = true;

        if (mService == null) {
            ready = false;
        } else if (mService.getConnectionState() != CONNECTION_STATE_CONNECTED) {
            ready = false;
        } else {
            Channel c = mService.getCurrentChannel();
            if (c == null || c.id == 0) {
                ready = false;
            }
        }

        if (!ready) {
            mIVPtt.setImageResource(R.drawable.ic_ptt_up);
            mIVPttCirc.setImageResource(R.drawable.ic_ptt_circle_noready);
        } else {
            switch (state) {
                case MIC_READY:
                    mIVPtt.setImageResource(R.drawable.ic_ptt_up);
                    mIVPttCirc.setImageResource(R.drawable.ic_ptt_circle_ready);
                    break;
                case MIC_GIVINGBACK:
                    break;
                case MIC_APPLYING:
                    //注意：申请期间，按键是按下状态，模仿真实按钮
                    mIVPtt.setImageResource(R.drawable.ic_ptt_down);
                    mIVPttCirc.setImageResource(R.drawable.ic_ptt_circle_applying);
                    break;
                case MIC_OPENING_SCO:
                    mIVPtt.setImageResource(R.drawable.ic_ptt_down);
                    mIVPttCirc.setImageResource(R.drawable.ic_ptt_circle_opening_sco);
                    break;
                case MIC_TALKING:
                    mIVPtt.setImageResource(R.drawable.ic_ptt_down);
                    mIVPttCirc.setImageResource(R.drawable.ic_ptt_circle_talking);
                    break;
            }
        }
    }

    private void refreshVolumeWave(short volume) {
        int horiVol = volume / 1024;    //32768max, 分20级别，适当增加点级别，取1500
        if (isScreenOn) {
            mIVHoriVolume.setImageLevel(horiVol);
        }
    }

    private void refreshLcdChannel() {
        if (mService == null) {
            return;
        }

        Channel c = mService.getCurrentChannel();
        if (c == null || c.id == 0) {
            mTVCurrentChanName.setText(getString(R.string.idle));
        } else {
            mTVCurrentChanName.setText(c.name);
        }

        Channel lc = mService.getListenChannel();
        if (lc == null) {
            mTVListenChanName.setText("");
        } else {
            mTVListenChanName.setText(lc.name);
        }
        //显示历史讲话者
        String historyTalker0 = mService.getHistoryTalker0();
        if (historyTalker0 != null) {
            mTVHistoryTalker0.setText(historyTalker0);
        }
        String historyTalker1 = mService.getHistoryTalker1();
        if (historyTalker1 != null) {
            mTVHistoryTalker1.setText(historyTalker1);
        }
        String historyTalker2 = mService.getHistoryTalker2();
        if (historyTalker2 != null) {
            mTVHistoryTalker2.setText(historyTalker2);
        }

        refreshCurrentVoiceTalker();
    }

    private void refreshCurrentVoiceTalker() {
        //先清空
        mIVCurrentChanTalkerDevice.setVisibility(View.INVISIBLE);
        mIVListenChanTalkerDevice.setVisibility(View.INVISIBLE);

        mTVCurrentChanTalker.setText("");
        mTVCurrentChanTalker.setVisibility(View.INVISIBLE);
        mTVListenChanTalker.setText("");
        mTVListenChanTalker.setVisibility(View.INVISIBLE);

        if (mService == null) {
            return;
        }
        User talker = mService.getcurrentVoiceTalker();

        //刷新talkinguser
        if (talker != null) {
            Channel tc = talker.getChannel();
            Channel cc = mService.getCurrentChannel();
            if (tc != null && cc != null && tc.id == cc.id) {
                //在同一频道
                //设备图标
                mIVCurrentChanTalkerDevice.setVisibility(View.VISIBLE);
                mIVCurrentChanTalkerDevice.setImageLevel(talker.audioSource);

                mTVCurrentChanTalker.setText(talker.nick);
                mTVCurrentChanTalker.setVisibility(View.VISIBLE);
            }

            //可能监听本频道。此时两个都显示
            Channel lc = mService.getListenChannel();
            if (tc != null && lc != null && tc.id == lc.id) {
                //在监听频道
                mIVListenChanTalkerDevice.setVisibility(View.VISIBLE);
                mIVListenChanTalkerDevice.setImageLevel(talker.audioSource);

                mTVListenChanTalker.setText(talker.nick);
                mTVListenChanTalker.setVisibility(View.VISIBLE);
            }
        }
    }

    private void showConnection() {
        ConnState connState = mService.getConnectionState();
        if (connState == CONNECTION_STATE_CONNECTED) {
            mTVConnect.setVisibility(View.GONE);
        } else if (connState == CONNECTION_STATE_CONNECTING || connState == CONNECTION_STATE_SYNCHRONIZING) {
            mTVConnect.setText(getString(R.string.syncing));
            mTVConnect.setVisibility(View.VISIBLE);
        } else {
            //此时可能是暂时断线，也可能是其他设备登录而被踢掉
            if (mService.getDisconnectReason() == DisconnectReason.Kick) {
                mTVConnect.setText(getString(R.string.be_kicked));
            } else {
                mTVConnect.setText(R.string.net_fail_retry);
            }

            mTVConnect.setVisibility(View.VISIBLE);
        }
    }

    private static final int SDK_PERMISSION_REQUEST = 127;

    @TargetApi(23)
    protected void getPermissions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            // 麦克风权限
            addPermission(permissions, Manifest.permission.RECORD_AUDIO);
            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }

        } else {
            return true;
        }
    }

    @TargetApi(23)
    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        // TODO Auto-generated method stub
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case SDK_PERMISSION_REQUEST:
                Map<String, Integer> perms = new HashMap<String, Integer>();
                // Initial
                perms.put(Manifest.permission.RECORD_AUDIO, PackageManager.PERMISSION_GRANTED);
                // Fill with results
                for (int i = 0; i < permissions.length; i++)
                    perms.put(permissions[i], grantResults[i]);

                if (perms.get(Manifest.permission.RECORD_AUDIO) != PackageManager.PERMISSION_GRANTED) {
                    AppCommonUtil.showToast(this, R.string.need_mic_permission);
                }
                break;
            default:
                super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        }
    }

    protected void help() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = LayoutInflater.from(this);
        View layout = inflater.inflate(R.layout.app_help, null);
        TextView tvContactUs = layout.findViewById(R.id.tv_contact_us);
        tvContactUs.setVisibility(View.GONE);

        builder.setTitle(R.string.help);
        builder.setView(layout);

        builder.setPositiveButton(R.string.ok, null);
        builder.show();
    }

    //pe5//检测sql口
    private class IOReadThread extends Thread {
        @Override
        public void run() {
            super.run();
            String[] last = {"1", "1"};//pe5置高，相同不处理，不同处理
            while (!isInterrupted()) {
                try {
                    // 定义路径
                    String gpioPath = "/sys/class/gpio/gpio132/value";
                    // 创建接收缓冲区
                    char[] buffer = new char[2048];
                    fileReader = new FileReader(gpioPath);
                    reader = new BufferedReader(fileReader);
                    reader.read(buffer);
                    gpioValue = buffer[0] + "";
                    last[1] = gpioValue;
                    //Log.e("二宝", gpioValue);

                    if (last[0].matches(last[1])) {
                    } else if (!last[0].matches(last[1]) && gpioValue.matches("0")) {
                        Log.d("error", "0");
                        last[0] = gpioValue;
                        //sendHexString("1002C107000404C1020004A8031003", "232");
                        iohandle.post(iorunnable0);
                    } else if (!last[0].matches(last[1]) && gpioValue.matches("1")) {
                        Log.d("error", "1");
                        last[0] = gpioValue;
                        iohandle.post(iorunnable1);
                        //sendHexString("1002C107000B0BC102000BF9161003", "232");
                    }

                    //Log.e("swy", "GPIOt"+buffer[0]);
                    //gpioValue = buffer[0] + "";
                } catch (IOException e) {
                    Log.d("error", "cat GPIO error");
                    e.printStackTrace();
                }
            }
        }
    }

    //pe2打开通道
    private Handler iohandle = new Handler();
    Runnable iorunnable0 = new Runnable() {
        @Override
        public void run() {
            mService.userPressDown();
        }
    };
    Runnable iorunnable1 = new Runnable() {
        @Override
        public void run() {
            mService.userPressUp();
        }
    };
}
