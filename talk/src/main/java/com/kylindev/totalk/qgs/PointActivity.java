package com.kylindev.totalk.qgs;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.launcher.ARouter;
import com.example.mylibrary.RouterURLS;
import com.example.mylibrary.TestService;
import com.kylindev.totalk.R;
import com.kylindev.totalk.app.BaseActivity;
import com.kylindev.totalk.app.SerialPortActivity;
import com.kylindev.totalk.bjxt.SpUtil;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Pattern;

import android_serialport_api.SerialPort;
import io.github.prototypez.appjoint.AppJoint;
import util.ByteUtil;
import util.HexUtil;

public class PointActivity extends SerialPortActivity {

    private DrawTop mTrain;
    private String mData;
    @Autowired(name = RouterURLS.BASE_MAIN)
    TestService app;
    private SpUtil mSpUtil;
    private TextView mPointText;
    private SerialPort mSerialPort;
    private final String GPGGAHead1 = "24";
    private final String GPGGAHead2 = "2447";
    private final String GPGGAHead3 = "244750";
    private final String GPGGAHead4 = "24475047";
    private final String GPGGAHead5 = "2447504747";
    private final String GPGGAHead6 = "244750474741";
    private final String END = "0D0A";
    private String mEncodeHexStr, mEncodeHex;
    private String receive = "";
    private boolean flag = false;
    private String TAG = "PointActivity.class";

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
                //try {
                if (type == 485) {
                    //获取系统时间
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
                    Date curDate = new Date(System.currentTimeMillis());
                    String time = formatter.format(curDate);

                    char[] chars = HexUtil.encodeHex(buffer);
                    mEncodeHexStr = ByteUtil.bytes2HexString(buffer, size);
                    Log.i("testData", "485555数据: " + mEncodeHexStr);

                    //时间和串口数据保存
                    PointDao pointDao = new PointDao(getApplicationContext());
                    pointDao.add(time, mEncodeHexStr.replaceAll(" ", ""));

                    if (mEncodeHexStr.length() >= 4) {
                        //头
                        String head = mEncodeHexStr.substring(0, 4);
                        //尾
                        String end = mEncodeHexStr.substring(mEncodeHexStr.length() - 4, mEncodeHexStr.length());
                        if (mEncodeHexStr.equals("2447")) {
                            receive += mEncodeHexStr;
                        } else if (!end.equals("0D0A")) {
                            receive += mEncodeHexStr;
                        } else if (end.equals("0D0A")) {
                            receive += mEncodeHexStr;
                            //16进制转换为字符串
                            String data485 = HexUtil.hexStr2Str(receive);
                            if (data485.indexOf("$GPGGA") != -1) {
                                String GPGGA = receive.substring(receive.indexOf("244750474741"), receive.length());
                                String substring = GPGGA.substring(GPGGA.indexOf("244750474741"), GPGGA.indexOf("0D0A", 1));
                                String hexStr2Str = HexUtil.hexStr2Str(substring);
                                Log.i("组合数据GPGGA", "组合数据GPGGA: " + hexStr2Str);
                                String $GPGGA = data485.substring(data485.indexOf("$GPGGA"), data485.length());
                                Log.i("组合数据$GPGGA", "组合数据: " + $GPGGA);
                                if ($GPGGA.indexOf("N") != -1 && $GPGGA.indexOf("E") != -1) {
                                    String GGA = $GPGGA.substring(0, $GPGGA.indexOf(","));
                                    //String n = $GPGGA.substring($GPGGA.indexOf(",", $GPGGA.indexOf(",") + 1) + 1);
                                    //获取纬度
                                    String latitude = $GPGGA.substring($GPGGA.indexOf(",", $GPGGA.indexOf(",") + 1) + 1, $GPGGA.indexOf(",N"));
                                    Log.i("TAGhexcomma", "    " + latitude);
                                    //获取经度
                                    String longitude = $GPGGA.substring($GPGGA.indexOf("N") + 2, $GPGGA.indexOf(",E"));
                                    Log.i("TAGhexcomma", "    " + longitude);
                                    //DecimalFormat 是 NumberFormat 的一个具体子类，用于格式化十进制数字。
                                    //DecimalFormat 包含一个模式 和一组符号
                                    DecimalFormat df = new DecimalFormat("#.000000");
                                    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                                    df.setDecimalFormatSymbols(symbols);
                                    boolean weidu = isDoubleOrFloat(latitude);
                                    boolean jingdu = isDoubleOrFloat(longitude);
                                    if (latitude != null && longitude != null && weidu && jingdu) {
                                        String lathead = latitude.substring(0, 2);
                                        String latend = latitude.substring(2, latitude.length());
                                        String lonhead = longitude.substring(0, 3);
                                        String lonend = longitude.substring(3, longitude.length());
                                        double lathead1 = Double.valueOf(lathead);
                                        double latend1 = Double.valueOf(latend);
                                        double a1 = latend1 / 60 + lathead1;
                                        double lonhead1 = Double.valueOf(lonhead);
                                        double lonend1 = Double.valueOf(lonend);
                                        double b1 = lonend1 / 60 + lonhead1;
                                        String a2 = String.valueOf(a1);
                                        String b2 = String.valueOf(b1);
                                        String lat = df.format(Double.valueOf(a2));
                                        String lon = df.format(Double.valueOf(b2));
                                        String lat1 = lat.substring(lat.indexOf(".") + 1);
                                        String lon1 = lon.substring(lon.indexOf(".") + 1);
                                        String total = "0A-机车GPS-" + lat1 + "-" + lon1;
                                        String ma = lat.substring(lat.length() - 4, lat.length());
                                        String mb = lon.substring(lon.length() - 4, lon.length());
                                        float mvalue1 = Float.valueOf(ma);
                                        float mvalue2 = Float.valueOf(mb);
                                        Log.i("TAGhexama", "    " + total);
                                        //Log.i("TAGhexbmb", "    " + mb);
                                        mTrain.invalidate();
                                        mTrain.setX(mvalue2 / 6 - 20);
                                        mTrain.setY(mvalue1 / 9 - 10);
                                    }
                                }
                            } else {
                                //Toast.makeText(getApplication(),"无",Toast.LENGTH_SHORT).show();
                                Log.e(TAG,"无");
                            }
                            receive = "";
                        }
                    }
                }
                /*} catch (Exception e) {
                    Log.e("数据异常", "数据异常：" + e);
                }*/
            }
        });
    }

    /*
     * 是否为浮点数？double或float类型。
     * @param str 传入的字符串。
     * @return 是浮点数返回true,否则返回false。
     */
    public static boolean isDoubleOrFloat(String str) {
        Pattern pattern = Pattern.compile("^[-\\+]?[.\\d]*$");
        return pattern.matcher(str).matches();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);
        setSystemUIVisible(false);
        mTrain = findViewById(R.id.drawtop);
        mPointText = findViewById(R.id.point_text);
        /*mTrain.setX(1500/6-20);
        mTrain.setY(4527/9-10);*/
    }

    /**
     * 隐藏状态栏和导航栏
     *
     * @param show boolean类型，true:显示  false ：隐藏
     */
    private void setSystemUIVisible(boolean show) {
        if (show) {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            uiFlags |= 0x00001000;
            getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        } else {
            int uiFlags = View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_FULLSCREEN;
            uiFlags |= 0x00001000;
            getWindow().getDecorView().setSystemUiVisibility(uiFlags);
        }
    }

    @Override
    public void onResume() {
        //设置为横屏
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();
    }
}
