package com.kylindev.totalk.qgs;

import android.animation.ObjectAnimator;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.database.sqlite.SQLiteDatabase;
import android.location.Location;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.example.mylibrary.RouterURLS;
import com.example.mylibrary.TestService;
import com.kylindev.totalk.R;
import com.kylindev.totalk.app.BaseActivity;
import com.kylindev.totalk.app.ChannelActivity;
import com.kylindev.totalk.app.SerialPortActivity;
import com.kylindev.totalk.bjxt.SiveDao;
import com.kylindev.totalk.bjxt.SpUtil;
import com.kylindev.totalk.bjxt.SuoData;
import com.kylindev.totalk.data.Point3d;
import com.kylindev.totalk.data.TrackDataUtil;
import com.kylindev.totalk.qgs.custom.BaiLiMap;
import com.kylindev.totalk.qgs.custom.ChangFengMap;
import com.kylindev.totalk.qgs.custom.XiNingBeiMap;
import com.kylindev.totalk.qgs.data232.DataDao232;
import com.kylindev.totalk.qgs.database.DataDao;
import com.kylindev.totalk.qgs.database.PersonDataUser;
import com.kylindev.totalk.qgs.database.eight.EightDataDao;
import com.kylindev.totalk.qgs.database.five.StopDataUser;
import com.kylindev.totalk.qgs.database.five.StopPickDao;
import com.kylindev.totalk.qgs.database.five.FiveDataDao;
import com.kylindev.totalk.qgs.database.hang.HangDao;
import com.kylindev.totalk.qgs.database.nine.NineDataDao;
import com.kylindev.totalk.qgs.database.seven.SevenDataDao;
import com.kylindev.totalk.qgs.database.six.SixDataDao;
import com.kylindev.totalk.qgs.park.one.OneDataDao;
import com.kylindev.totalk.qgs.park.one.OneDataUser;
import com.kylindev.totalk.qgs.people.TransferPeople;
import com.kylindev.totalk.qgs.people.TransferPeopleFour;
import com.kylindev.totalk.qgs.people.TransferPeopleOne;
import com.kylindev.totalk.qgs.people.TransferPeopleThree;
import com.kylindev.totalk.qgs.people.TransferPeopleTwo;
import com.kylindev.totalk.qgs.tack.AssetsDatabaseManager;
import com.kylindev.totalk.qgs.tack.PickDao;
import com.kylindev.totalk.qgs.tack.WanAsynTask;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import io.github.prototypez.appjoint.AppJoint;
import util.ByteUtil;
import util.HexUtil;

public class PointActivity extends SerialPortActivity {

    private DrawTop mTrain;
    private String mData;
    @Autowired(name = RouterURLS.BASE_MAIN)
    TestService app;
    private TextView mPointText;
    private String mEncodeHexStr, mEncodeHex;
    private String receive = "";
    private boolean flag = false;
    private String TAG = "PointActivity.class";
    private GPSDao mGpsDao;
    private Button mBtn, mBtn1;
    private PickDao mPickDao;
    List<Double> mList = new ArrayList<>();
    private String mS1;
    private boolean shi = false;
    private boolean wu = false;
    private boolean san = false;
    private boolean yi = false;
    private String mName;
    private String mFace;
    private String mId;
    private String mCumulative;
    private String mTotal;
    private TextView mJuli;
    private TextView mJwd;
    private String mTotal1;
    private List<GPSUser> mGpsUsers;
    private String mHead;
    private String mEnd;
    private SpUtil mInstructions;
    private SpUtil mCqncast;
    private SpUtil mControlCar;
    private String mName1;
    private SiveDao mSiveDao;
    private String jieAll;
    private String mId1;
    private SpUtil mControlshuntinghunting;
    private String mSubstring;
    private SpUtil mControlTuiJin;
    private SpUtil mCon;
    private TestService mAppService;
    private SQLiteDatabase mMDatabase;
    private WanAsynTask mWanAsynTask;
    private double mDistance;
    private Double mLat3;
    private Double mLon3;
    private String mConversationId = "01";
    private TextView mLat1, mLat2;
    private boolean qiehuan = false;
    //private boolean firstInto = false;
    HashMap<Integer, ArrayList<Point3d>> gps = new HashMap<>();
    private int mGuDao;
    boolean oneTrack = false;
    boolean twoTrack = false;
    boolean threeTrack = false;
    boolean fourTrack = false;
    boolean fiveTrack = false;
    boolean sixTrack = false;
    boolean sevenTrack = false;
    boolean eightTrack = false;

    boolean nine = false;
    boolean ten = false;
    boolean eleven = false;
    boolean twelve = false;
    boolean thirteen = false;
    boolean fourteen = false;

    boolean fifteen = false;
    boolean sixteen = false;
    boolean seventeen = false;
    boolean eighteen = false;
    boolean nineteen = false;
    int mTime = 2000;
    //机车纵向减
    int disparity = 20;
    int disparity1 = 30;
    //机车横向减
    int transverse = 30;
    //人员横向减
    int transverse1 = 15;
    private SpUtil mFirstInto;
    private String mRatioOfGpsTrack, mRatioOfGpsTrack2, mRatioOfGpsTrack3, mRatioOfGpsTrack4, mRatioOfGpsTrack5;
    private Double mGpsPistance, mGpsPistance2, mGpsPistance3, mGpsPistance4, mGpsPistance5;
    private TransferPeople mTransferpeople;
    private TransferPeopleOne mPeopleOne;
    private SpUtil mPeople5, mPeople6, mPeople7, mPeople8, mPeople9;
    private SpUtil mMap1, mMap2, mMap3;
    private SpUtil mDataTransmission;
    private String mReceiveHead;
    private SpUtil mReceive1, mReceive2, mReceive3, qgs;
    private String mRatioOfGpsTrackCar;
    private double mGetRatioOfGpsPointCar;
    private Double mGpsPistanceCar;
    private TransferPeopleTwo mPeopletwo;
    private TransferPeopleThree mPeoplethree;
    private TransferPeopleFour mPeoplefour;
    private SpUtil mAdvancedmr;
    private SpUtil mOnePickLeft;
    private SpUtil mOnepickrightight;
    private Double mLatLeadCar20;
    private Double mLonLeadCar20;
    private XiNingBeiMap mXiningbeimap;
    private ChangFengMap mChangfengmap;
    private BaiLiMap mBailimap;
    private Button mDelete;
    private FiveDataDao mFiveDataDao;
    private SixDataDao mSixDataDao;
    private SevenDataDao mSevenDataDao;
    private EightDataDao mEightDataDao;
    private NineDataDao mNineDataDao;
    private String mTime1;
    private SpUtil mLeftCar;
    private SpUtil mRightCar;
    private int mGetGudaoOfGpsPoint;
    private SpUtil mLeadcar;
    private SpUtil mStopcar;
    private String mStopcar1;
    private String mLeadcar1;
    private SpUtil mCarLocation;
    private SpUtil mMain, mBaiLi, mChangFeng;
    private String mLat21;
    private String mLon21;
    private Double mLatStopCar01;
    private Double mLonStopCar01;
    private int mGetGudaoOfGpsPoint2;
    private String mRatioOfGpsTrackCar2;
    private double mGetRatioOfGpsPointCar2;

    void sendMessage(String uid, String s) {

        //ARouter.getInstance().inject(this);
        if (mAppService != null) {
            mAppService.sendMessage(uid, s);
        }
    }

    @Override
    protected void onDataReceived(final byte[] buffer, final int size, final int type) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                try {
                    if (type == 485) {
                        //String name1 = mDataTransmission.getName();
                        //if (name1 != null) {
                        //switch (name1) {
                        //case "true":
                        char[] chars = HexUtil.encodeHex(buffer);
                        mEncodeHexStr = ByteUtil.bytes2HexString(buffer, size);
                        Log.i("testData", "485555数据: " + mEncodeHexStr);

                        //获取系统时间
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
                        Date curDate = new Date(System.currentTimeMillis());
                        String time = formatter.format(curDate);

                        String name1 = mMap1.getName();
                        String name2 = mMap2.getName();
                        String name3 = mMap3.getName();
                        /*mMap1.setName("visible");
                        mMap2.setName("gone");
                        mMap3.setName("gone");*/
                        if (name1.equals("visible")) {
                            mXiningbeimap.setVisibility(View.VISIBLE);
                            mChangfengmap.setVisibility(View.GONE);
                            mBailimap.setVisibility(View.GONE);
                            mMain.setName("main");
                        }
                        if (name2.equals("visible")) {
                            mChangfengmap.setVisibility(View.VISIBLE);
                            mXiningbeimap.setVisibility(View.GONE);
                            mBailimap.setVisibility(View.GONE);
                            mMain.setName("changfeng");
                        }
                        if (name3.equals("visible")) {
                            mBailimap.setVisibility(View.VISIBLE);
                            mChangfengmap.setVisibility(View.GONE);
                            mXiningbeimap.setVisibility(View.GONE);
                            mMain.setName("baili");
                        }

                        if (mEncodeHexStr.length() >= 12) {
                            mHead = mEncodeHexStr.substring(0, 12);
                            mEnd = mEncodeHexStr.substring(mEncodeHexStr.length() - 4, mEncodeHexStr.length());

                            if (mHead.equals("24474E524D43") && !mEnd.equals("0D0A")) {
                                //receive1 += mEncodeHexStr;
                                mReceive1.setName(mEncodeHexStr);
                            } else if (!mHead.equals("24474E524D43") && !mEnd.equals("0D0A")) {
                                //receive2 += mEncodeHexStr;
                                mReceive2.setName(mEncodeHexStr);
                            } else if (!mHead.equals("24474E524D43") && mEnd.equals("0D0A")) {
                                //receive3 += mEncodeHexStr;
                                mReceive3.setName(mEncodeHexStr);
                            }
                            String nameReceive1 = mReceive1.getName();
                            String nameReceive2 = mReceive2.getName();
                            String nameReceive3 = mReceive3.getName();
                            receive = nameReceive1 + nameReceive2 + nameReceive3;
                            int length = receive.length();
                            if (receive != null) {
                                if (receive.length() >= 144) {
                                    Log.i("receive4", receive);
                                    String receiveHead = receive.substring(0, 12);
                                    String receiveEnd = receive.substring(receive.length() - 4, receive.length());
                                    if (receiveHead.equals("24474E524D43") && receiveEnd.equals("0D0A")) {

                                        //16进制转换为字符串
                                        String data485 = HexUtil.hexStr2Str(receive);
                                        Log.e("data485数据", data485 + "");
                                        if (data485.indexOf("$GNRMC") != -1) {
                                            if (data485.indexOf(",N,") != -1 && data485.indexOf(",E,") != -1) {
                                                Log.i("data485", "    " + data485);
                                                String GGA = data485.substring(0, data485.indexOf(","));
                                                //String n = $GPGGA.substring($GPGGA.indexOf(",", $GPGGA.indexOf(",") + 1) + 1);
                                                //获取纬度
                                                String latitude = data485.substring(data485.indexOf(",", data485.indexOf(",") + 1) + 3, data485.indexOf(",N"));
                                                Log.i("TAGhexcomma", "    " + latitude);
                                                //获取经度
                                                String longitude = data485.substring(data485.indexOf("N,") + 2, data485.indexOf(",E"));
                                                Log.i("TAGhexcomma", "    " + longitude);
                                                //DecimalFormat 是 NumberFormat 的一个具体子类，用于格式化十进制数字。
                                                //DecimalFormat 包含一个模式 和一组符号
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

                                                    /*DecimalFormat df = new DecimalFormat("#.000000");
                                                    DecimalFormatSymbols symbols = new DecimalFormatSymbols();
                                                    df.setDecimalFormatSymbols(symbols);
                                                    String lat = df.format(Double.valueOf(a1));
                                                    String lon = df.format(Double.valueOf(b1));
                                                    Double latDouble = Double.valueOf(lat);
                                                    Double lonDouble = Double.valueOf(lon);*/
                                                    //大圆盘坐标减去与实际坐标的差值
                                                    double latDifference = a1 - 0.000017;
                                                    double lonDifference = b1 - 0.000021;
                                                    String a2 = String.valueOf(latDifference);
                                                    String b2 = String.valueOf(lonDifference);
                                                    String lat1 = a2.substring(a2.indexOf(".") + 1);
                                                    String lon1 = b2.substring(b2.indexOf(".") + 1);
                                                    mTotal1 = "0A-机车GPS-" + lat1 + "-" + lon1;
                                                    /*String ma = lat.substring(lat.length() - 4, lat.length());
                                                    String mb = lon.substring(lon.length() - 4, lon.length());
                                                    float mvalue1 = Float.valueOf(ma);
                                                    float mvalue2 = Float.valueOf(mb);*/
                                                    Log.e("弯点", "弯点a1: " + a1 + " ");
                                                    Log.e("弯点", "弯点b1: " + b1 + " ");

                                                    DecimalFormat df = new DecimalFormat("#.000000");
                                                    String lat = df.format(Double.valueOf(latDifference)).substring(df.format(Double.valueOf(latDifference)).indexOf(".") + 1);
                                                    String lon = df.format(Double.valueOf(lonDifference)).substring(df.format(Double.valueOf(lonDifference)).indexOf(".") + 1);
                                                    Double value1 = Double.valueOf(lat);
                                                    Double value2 = Double.valueOf(lon);
                                                    String latCar = lathead + "." + lat;
                                                    String lonCar = lonhead + "." + lon;
                                                    //mGpsDao = new GPSDao(getApplicationContext());
                                                    //mJwd.setText(lat + "    " + lon);
                                                    mLeftCar.setName(latCar + "");
                                                    mRightCar.setName(lonCar + "");
                                                    mGpsDao.add(a2, b2);

                                                    //股道号
                                                    mGetGudaoOfGpsPoint = GetGudaoOfGpsPoint(b1, a1);
                                                    //mJuli.setText(getGudaoOfGpsPoint + "");
                                                    mRatioOfGpsTrackCar = String.valueOf(mGetGudaoOfGpsPoint);
                                                    Point3d point3d = new Point3d();
                                                    point3d.setX(b1);
                                                    point3d.setY(a1);
                                                    mGetRatioOfGpsPointCar = GetRatioOfGpsPoint(point3d, mGetGudaoOfGpsPoint);

                                                    DecimalFormat df1 = new DecimalFormat("#####0.00%");
                                                    DecimalFormatSymbols symbols1 = new DecimalFormatSymbols();
                                                    df1.setDecimalFormatSymbols(symbols1);
                                                    String ratioOfGpsPoint = df1.format(mGetRatioOfGpsPointCar);
                                                    String gpsPoint = ratioOfGpsPoint.substring(0, ratioOfGpsPoint.indexOf("."));
                                                    mGpsPistanceCar = Double.valueOf(gpsPoint);
                                                    mCarLocation.setName(mGpsPistanceCar + "");
                                                    mStopcar.setTrack(mGetGudaoOfGpsPoint + "");
                                                    mStopcar.setLat(latCar);
                                                    mStopcar.setLon(lonCar);
                                                    mStopcar.setPosition(mGpsPistanceCar + "");
                                                    mStopcar.setName("股道" + mGetGudaoOfGpsPoint + "纬度" + latCar + "经度" + lonCar + "位置" + mGpsPistanceCar);
                                                    //mJwd.setText(ratioOfGpsPoint + "");
                                                    //mLat1.setText(mGetRatioOfGpsPointCar + "");
                                                    //mLat2.setText(gpsPoint);

                                                    proplrMove();

                                                    String onePickLeftName = mOnePickLeft.getName();
                                                    String onepickrightightName = mOnepickrightight.getName();
                                                    if (onePickLeftName.equals("0") && onepickrightightName.equals("0")) {
                                                        OneDataDao oneDataDao = new OneDataDao(getApplication());
                                                        List<OneDataUser> oneDataUsers = oneDataDao.find();
                                                        int oneDataUserssize = oneDataUsers.size();
                                                        if (oneDataUserssize >= 2) {
                                                            String ratioOfGpsPointCar = oneDataUsers.get(0).getRatioOfGpsPointCar();
                                                            String ratioOfGpsPointCar1 = oneDataUsers.get(1).getRatioOfGpsPointCar();
                                                            Double ratioOfGpsPointCarDouble = Double.valueOf(ratioOfGpsPointCar);
                                                            Double ratioOfGpsPointCarDouble1 = Double.valueOf(ratioOfGpsPointCar1);
                                                            if (ratioOfGpsPointCarDouble > ratioOfGpsPointCarDouble1) {
                                                                mOnePickLeft.setName(ratioOfGpsPointCar1);
                                                                mOnepickrightight.setName(ratioOfGpsPointCar);
                                                            } else if (ratioOfGpsPointCarDouble < ratioOfGpsPointCarDouble1) {
                                                                mOnePickLeft.setName(ratioOfGpsPointCar);
                                                                mOnepickrightight.setName(ratioOfGpsPointCar1);
                                                            }
                                                        }
                                                    }

                                                    /*String name = mAdvancedmr.getName();
                                                    if (name != null) {
                                                        switch (name) {
                                                            case "true":
                                                                StopPickDao fiveDao = new StopPickDao(getApplication());
                                                                List<StopDataUser> personDataUsers = fiveDao.find();
                                                                int size1 = personDataUsers.size();
                                                                String lat2 = personDataUsers.get(size1 - 1).getLat();
                                                                String lon2 = personDataUsers.get(size1 - 1).getLon();
                                                                Double value1 = Double.valueOf(lat2);
                                                                Double value2 = Double.valueOf(lon2);
                                                                //latDifference  lonDifference
                                                                int getGudaoOfZhaiGouPoint = GetGudaoOfGpsPoint(value2, value1);
                                                                String value = String.valueOf(getGudaoOfZhaiGouPoint);
                                                                Point3d point3d1 = new Point3d();
                                                                point3d.setX(value2);
                                                                point3d.setY(value1);
                                                                double getRatioOfGpsPoint = GetRatioOfGpsPoint(point3d1, getGudaoOfZhaiGouPoint);
                                                                String format = df1.format(getRatioOfGpsPoint);
                                                                String substring = format.substring(0, format.indexOf("."));
                                                                Double aDouble = Double.valueOf(substring);
                                                                Log.i("一车", "一车" + aDouble);

                                                                double distance = getDistance(lonDifference, latDifference, value2, value1);
                                                                Log.i("QGS", "一车" + distance);
                                                                //if (aDouble < 5) {
                                                                if (distance <= 11 && yi == false) {
                                                                    //一车
                                                                    Log.i("QGS", "一车");
                                                                    String total1 = mFace + "26" + "02";
                                                                    String sum = getSum("26");
                                                                    mJuli.setText("" + "一车");
                                                                    sendHexString(sum.replaceAll("\\s*", ""), "232");
                                                                    san = true;
                                                                    wu = true;
                                                                    shi = true;
                                                                    yi = true;
                                                                } else if (distance > 11 && distance <= 33 && san == false) {
                                                                    //三车
                                                                    Log.i("QGS", "三车");
                                                                    String total1 = mFace + "23" + "02";
                                                                    String sum = getSum("23");
                                                                    mJuli.setText("" + "三车");
                                                                    sendHexString(sum.replaceAll("\\s*", ""), "232");
                                                                    san = true;
                                                                    wu = true;
                                                                    shi = true;
                                                                } else if (distance > 33 && distance <= 55 && wu == false) {
                                                                    //五车
                                                                    Log.i("QGS", "五车");
                                                                    mJuli.setText("" + "五车");
                                                                    String total1 = mFace + "25" + "02";
                                                                    String sum = getSum("25");
                                                                    sendHexString(sum.replaceAll("\\s*", ""), "232");
                                                                    wu = true;
                                                                    shi = true;
                                                                } else if (distance > 55 && distance <= 110 && shi == false) {
                                                                    //十车
                                                                    Log.i("QGS", "十车");
                                                                    mJuli.setText("" + "十车");
                                                                    String total1 = mFace + "27" + "02";
                                                                    String sum = getSum("27");
                                                                    sendHexString(sum.replaceAll("\\s*", ""), "232");
                                                                    shi = true;
                                                                }
                                                                //}
                                                                break;
                                                        }
                                                    }*/
                                                }
                                            }
                                        }
                                    }
                                    receive = "";
                                    mReceive1.setName("");
                                    mReceive2.setName("");
                                    mReceive3.setName("");
                                }
                            }

                        }

                        //break;
                        //default:
                        //receive = "";
                        //break;
                        //}
                        //}
                    } else if (type == 232) {
                        //String hexStr = HexUtil.encodeHexStr(buffer, false, size);
                        char[] chars = HexUtil.encodeHex(buffer);
                        mEncodeHexStr = ByteUtil.bytes2HexString(buffer, size);
                        Log.i("232数据", mEncodeHexStr + "      121212");
                        qgs.setName("" + mEncodeHexStr);

                        //获取系统时间
                        SimpleDateFormat formatter = new SimpleDateFormat("yyyy年MM月dd日   HH:mm:ss");
                        Date curDate = new Date(System.currentTimeMillis());
                        mTime1 = formatter.format(curDate);
                        DataDao232 dataDao232 = new DataDao232(getApplicationContext());
                        dataDao232.add(mTime1, mEncodeHexStr);

                        //调号
                        String signature = mEncodeHexStr.substring(2, 4);
                        //人员号
                        String peopleId2 = mEncodeHexStr.substring(4, 6);
                        String function2 = mEncodeHexStr.substring(6, 8);
                        String totalDmr = peopleId2 + signature + function2 + "03";
                        sendMessage(mConversationId, totalDmr);
                        switch (function2) {
                            //停车
                            case "":

                                break;
                            //摘钩
                            case "59":
                                //
                                PickDao pickDao = new PickDao(getApplication());
                                pickDao.add(mTime1, mEncodeHexStr);
                                switch (peopleId2) {
                                    //1号调车员
                                    case "01":
                                        List<PersonDataUser> personDataUsers1 = mSixDataDao.find();
                                        int size2 = personDataUsers1.size();
                                        String lat2 = personDataUsers1.get(size2 - 1).getLat();
                                        String lon2 = personDataUsers1.get(size2 - 1).getLon();
                                        String lat12 = lat2.substring(lat2.indexOf(".") + 1, lat2.length());
                                        String lon12 = lon2.substring(lon2.indexOf(".") + 1, lon2.length());
                                        String total2 = "01-摘钩GPS-" + lat12 + "-" + lon12;
                                        sendMessage(mConversationId, total2);

                                        sixPerson();

                                        //判断是否有停留车
                                        String onePickLeftName = mOnePickLeft.getName();
                                        String onepickrightightName = mOnepickrightight.getName();
                                        if (onePickLeftName.equals("0") || onepickrightightName.equals("0")) {
                                            switch (mGetGudaoOfGpsPoint2) {
                                                case 1:
                                                    if (onePickLeftName.equals("0") && !onepickrightightName.equals("0")) {
                                                        mOnePickLeft.setPosition(mGpsPistance2 + "");
                                                        mOnePickLeft.setLon(mLat21);
                                                        mOnePickLeft.setLat(mLon21);
                                                        mOnePickLeft.setTrack(mGetGudaoOfGpsPoint2 + "");
                                                    } else if (!onePickLeftName.equals("0") && onepickrightightName.equals("0")) {
                                                        mOnepickrightight.setPosition(mGpsPistance2 + "");
                                                        mOnepickrightight.setLon(mLat21);
                                                        mOnepickrightight.setLat(mLon21);
                                                        mOnepickrightight.setTrack(mGetGudaoOfGpsPoint2 + "");
                                                    }
                                                    break;
                                            }
                                        }else {
                                            switch (mGetGudaoOfGpsPoint2) {
                                                case 1:
                                                    //if (){}
                                                    break;
                                            }
                                        }
                                        break;
                                    case "02":
                                        List<PersonDataUser> personDataUsers2 = mSevenDataDao.find();
                                        int size3 = personDataUsers2.size();
                                        String lat3 = personDataUsers2.get(size3 - 1).getLat();
                                        String lon3 = personDataUsers2.get(size3 - 1).getLon();
                                        String lat13 = lat3.substring(lat3.indexOf(".") + 1, lat3.length());
                                        String lon13 = lon3.substring(lon3.indexOf(".") + 1, lon3.length());
                                        String total3 = "02-摘钩GPS-" + lat13 + "-" + lon13;
                                        sendMessage(mConversationId, total3);
                                        break;
                                    case "03":
                                        List<PersonDataUser> personDataUsers3 = mEightDataDao.find();
                                        int size4 = personDataUsers3.size();
                                        String lat4 = personDataUsers3.get(size4 - 1).getLat();
                                        String lon4 = personDataUsers3.get(size4 - 1).getLon();
                                        String lat14 = lat4.substring(lat4.indexOf(".") + 1, lat4.length());
                                        String lon14 = lon4.substring(lon4.indexOf(".") + 1, lon4.length());
                                        String total4 = "03-摘钩GPS-" + lat14 + "-" + lon14;
                                        sendMessage(mConversationId, total4);
                                        break;
                                    case "04":
                                        List<PersonDataUser> personDataUsers4 = mNineDataDao.find();
                                        int size5 = personDataUsers4.size();
                                        String lat5 = personDataUsers4.get(size5 - 1).getLat();
                                        String lon5 = personDataUsers4.get(size5 - 1).getLon();
                                        String lat15 = lat5.substring(lat5.indexOf(".") + 1, lat5.length());
                                        String lon15 = lon5.substring(lon5.indexOf(".") + 1, lon5.length());
                                        String total5 = "04-摘钩GPS-" + lat15 + "-" + lon15;
                                        sendMessage(mConversationId, total5);
                                        break;
                                }

                                /*//停车股道号
                                mStopcar1 = mStopcar.getName();
                                //领车人员号
                                mLeadcar1 = mLeadcar.getName();
                                //先判断人员位置
                                switch (mLeadcar1) {
                                    case "01":
                                        //判断是否有停留车
                                        String onePickLeftName = mOnePickLeft.getName();
                                        String onepickrightightName = mOnepickrightight.getName();
                                        if (onePickLeftName.equals("0") || onepickrightightName.equals("0")) {
                                            sixPerson();
                                            switch (mGetGudaoOfGpsPoint2) {
                                                case 1:
                                                    if (onePickLeftName.equals("0") && !onepickrightightName.equals("0")) {
                                                        mOnePickLeft.setPosition(mGpsPistance2 + "");
                                                        mOnePickLeft.setLon(mLat21);
                                                        mOnePickLeft.setLat(mLon21);
                                                        mOnePickLeft.setTrack(mGetGudaoOfGpsPoint2 + "");
                                                    } else if (!onePickLeftName.equals("0") && onepickrightightName.equals("0")) {
                                                        mOnepickrightight.setPosition(mGpsPistance2 + "");
                                                        mOnepickrightight.setLon(mLat21);
                                                        mOnepickrightight.setLat(mLon21);
                                                        mOnepickrightight.setTrack(mGetGudaoOfGpsPoint2 + "");
                                                    }
                                                    break;
                                            }
                                            //5-94
                                        } else {
                                            //有停留车
                                        }
                                        break;
                                }*/
                                break;
                            //挂钩
                            case "5A":
                                //
                                HangDao hangDao = new HangDao(getApplication());
                                hangDao.add(mTime1, mEncodeHexStr);
                                switch (peopleId2) {
                                    //1号调车员
                                    case "01":
                                        List<PersonDataUser> personDataUsers1 = mSixDataDao.find();
                                        int size2 = personDataUsers1.size();
                                        String lat2 = personDataUsers1.get(size2 - 1).getLat();
                                        String lon2 = personDataUsers1.get(size2 - 1).getLon();
                                        String lat12 = lat2.substring(lat2.indexOf(".") + 1, lat2.length());
                                        String lon12 = lon2.substring(lon2.indexOf(".") + 1, lon2.length());
                                        String total2 = "01-挂钩GPS-" + lat12 + "-" + lon12;
                                        sendMessage(mConversationId, total2);
                                        break;
                                    case "02":
                                        List<PersonDataUser> personDataUsers2 = mSevenDataDao.find();
                                        int size3 = personDataUsers2.size();
                                        String lat3 = personDataUsers2.get(size3 - 1).getLat();
                                        String lon3 = personDataUsers2.get(size3 - 1).getLon();
                                        String lat13 = lat3.substring(lat3.indexOf(".") + 1, lat3.length());
                                        String lon13 = lon3.substring(lon3.indexOf(".") + 1, lon3.length());
                                        String total3 = "02-挂钩GPS-" + lat13 + "-" + lon13;
                                        sendMessage(mConversationId, total3);
                                        break;
                                    case "03":
                                        List<PersonDataUser> personDataUsers3 = mEightDataDao.find();
                                        int size4 = personDataUsers3.size();
                                        String lat4 = personDataUsers3.get(size4 - 1).getLat();
                                        String lon4 = personDataUsers3.get(size4 - 1).getLon();
                                        String lat14 = lat4.substring(lat4.indexOf(".") + 1, lat4.length());
                                        String lon14 = lon4.substring(lon4.indexOf(".") + 1, lon4.length());
                                        String total4 = "03-挂钩GPS-" + lat14 + "-" + lon14;
                                        sendMessage(mConversationId, total4);
                                        break;
                                    case "04":
                                        List<PersonDataUser> personDataUsers4 = mNineDataDao.find();
                                        int size5 = personDataUsers4.size();
                                        String lat5 = personDataUsers4.get(size5 - 1).getLat();
                                        String lon5 = personDataUsers4.get(size5 - 1).getLon();
                                        String lat15 = lat5.substring(lat5.indexOf(".") + 1, lat5.length());
                                        String lon15 = lon5.substring(lon5.indexOf(".") + 1, lon5.length());
                                        String total5 = "04-挂钩GPS-" + lat15 + "-" + lon15;
                                        sendMessage(mConversationId, total5);
                                        break;
                                }
                                break;
                            case "43":
                                mAdvancedmr.setName("true");
                                break;
                            case "71":
                                mAdvancedmr.setName("false");
                                //停车
                                san = false;
                                wu = false;
                                shi = false;
                                yi = false;
                                /*String stopcar = mStopcar.getName();
                                String leadcar = mLeadcar.getName();*/
                                break;
                            case "9A"://领车
                                //收到领车指令后计算领车员与机车的经纬度差
                                mLeadcar.setName(peopleId2);
                                switch (peopleId2) {
                                    case "20":
                                        List<PersonDataUser> personDataUsers = mFiveDataDao.find();
                                        int size1 = personDataUsers.size();
                                        String lat = personDataUsers.get(size1 - 1).getLat();
                                        String lon = personDataUsers.get(size1 - 1).getLon();
                                        mLatLeadCar20 = Double.valueOf(lat);
                                        mLonLeadCar20 = Double.valueOf(lon);

                                        List<GPSUser> gpsUsers = mGpsDao.find();
                                        int gpsSize = gpsUsers.size();
                                        String gpsLat = gpsUsers.get(gpsSize - 1).getLat();
                                        String gpsLon = gpsUsers.get(gpsSize - 1).getLon();
                                        break;
                                }
                                break;
                            case "73":
                                //紧急停车
                                //停车股道号
                                mStopcar1 = mStopcar.getName();
                                mLeadcar.setName(peopleId2);
                                //判断是否有停留车
                                String onePickLeftName = mOnePickLeft.getName();
                                String onepickrightightName = mOnepickrightight.getName();
                                if (onePickLeftName.equals("0") || onepickrightightName.equals("0")) {
                                    switch (peopleId2) {
                                        case "01":
                                            sixPerson();
                                            switch (mGetGudaoOfGpsPoint2) {
                                                case 1:
                                                    OneDataDao oneDataDao = new OneDataDao(getApplication());
                                                    oneDataDao.add(mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPistance2 + "");
                                                    String name = mCarLocation.getName();
                                                    //获取机车的股道
                                                    String track = mStopcar.getTrack();
                                                    //获取机车的经纬度
                                                    String lat = mStopcar.getLat();
                                                    String lon = mStopcar.getLon();
                                                    //获取机车的位置
                                                    String position = mStopcar.getPosition();
                                                    Double positionCar = Double.valueOf(position);
                                                    if (track.equals(mGetGudaoOfGpsPoint2)) {
                                                        if (mGpsPistance2 < positionCar) {
                                                            mOnePickLeft.setPosition(mGpsPistance2 + "");
                                                            mOnePickLeft.setLon(mLat21);
                                                            mOnePickLeft.setLat(mLon21);
                                                            mOnePickLeft.setTrack(mGetGudaoOfGpsPoint2 + "");
                                                        } else if (mGpsPistance2 > positionCar) {
                                                            mOnepickrightight.setPosition(mGpsPistance2 + "");
                                                            mOnepickrightight.setLon(mLat21);
                                                            mOnepickrightight.setLat(mLon21);
                                                            mOnepickrightight.setTrack(mGetGudaoOfGpsPoint2 + "");
                                                        }
                                                    } else {
                                                        String name1 = mMain.getName();
                                                        if (name1.equals("baili")) {
                                                            mOnePickLeft.setPosition(mGpsPistance2 + "");
                                                            mOnePickLeft.setLon(mLat21);
                                                            mOnePickLeft.setLat(mLon21);
                                                            mOnePickLeft.setTrack(mGetGudaoOfGpsPoint2 + "");
                                                        } else if (name1.equals("changfeng")) {
                                                            mOnepickrightight.setPosition(mGpsPistance2 + "");
                                                            mOnepickrightight.setLon(mLat21);
                                                            mOnepickrightight.setLat(mLon21);
                                                            mOnepickrightight.setTrack(mGetGudaoOfGpsPoint2 + "");
                                                        }
                                                    }
                                                    break;
                                            }
                                            break;
                                        case "02":
                                            List<PersonDataUser> personDataUsers2 = mSevenDataDao.find();
                                            int size3 = personDataUsers2.size();
                                            String lat3 = personDataUsers2.get(size3).getLat();
                                            String lon3 = personDataUsers2.get(size3).getLon();
                                            Double latStopCar02 = Double.valueOf(lat3);
                                            Double lonStopCar02 = Double.valueOf(lon3);
                                            //获取股道号
                                            int getGudaoOfGpsPoint3 = GetGudaoOfGpsPoint(lonStopCar02, latStopCar02);
                                            //mJuli.setText(getGudaoOfGpsPoint + "");
                                            String ratioOfGpsTrackCar3 = String.valueOf(getGudaoOfGpsPoint3);
                                            Point3d point3d3 = new Point3d();
                                            point3d3.setX(lonStopCar02);
                                            point3d3.setY(latStopCar02);
                                            //获取位置
                                            double getRatioOfGpsPointCar3 = GetRatioOfGpsPoint(point3d3, getGudaoOfGpsPoint3);

                                            switch (getGudaoOfGpsPoint3) {
                                                case 1:
                                                    OneDataDao oneDataDao = new OneDataDao(getApplication());
                                                    oneDataDao.add(getGudaoOfGpsPoint3 + "", lat3, lon3, getRatioOfGpsPointCar3 + "");
                                                    break;
                                            }
                                            break;
                                        case "03":
                                            List<PersonDataUser> personDataUsers3 = mEightDataDao.find();
                                            int size4 = personDataUsers3.size();
                                            String lat4 = personDataUsers3.get(size4).getLat();
                                            String lon4 = personDataUsers3.get(size4).getLon();
                                            Double latStopCar03 = Double.valueOf(lat4);
                                            Double lonStopCar03 = Double.valueOf(lon4);
                                            //获取股道号
                                            int getGudaoOfGpsPoint4 = GetGudaoOfGpsPoint(lonStopCar03, latStopCar03);
                                            //mJuli.setText(getGudaoOfGpsPoint + "");
                                            String ratioOfGpsTrackCar4 = String.valueOf(getGudaoOfGpsPoint4);
                                            Point3d point3d4 = new Point3d();
                                            point3d4.setX(lonStopCar03);
                                            point3d4.setY(latStopCar03);
                                            //获取位置
                                            double getRatioOfGpsPointCar4 = GetRatioOfGpsPoint(point3d4, getGudaoOfGpsPoint4);

                                            switch (getGudaoOfGpsPoint4) {
                                                case 1:
                                                    OneDataDao oneDataDao = new OneDataDao(getApplication());
                                                    oneDataDao.add(getGudaoOfGpsPoint4 + "", lat4, lon4, getRatioOfGpsPointCar4 + "");
                                                    break;
                                            }
                                            break;
                                        case "04":
                                            List<PersonDataUser> personDataUsers4 = mNineDataDao.find();
                                            int size5 = personDataUsers4.size();
                                            String lat5 = personDataUsers4.get(size5).getLat();
                                            String lon5 = personDataUsers4.get(size5).getLon();
                                            Double latStopCar04 = Double.valueOf(lat5);
                                            Double lonStopCar04 = Double.valueOf(lon5);
                                            //获取股道号
                                            int getGudaoOfGpsPoint5 = GetGudaoOfGpsPoint(lonStopCar04, latStopCar04);
                                            //mJuli.setText(getGudaoOfGpsPoint + "");
                                            String ratioOfGpsTrackCar5 = String.valueOf(getGudaoOfGpsPoint5);
                                            Point3d point3d5 = new Point3d();
                                            point3d5.setX(lonStopCar04);
                                            point3d5.setY(latStopCar04);
                                            //获取位置
                                            double getRatioOfGpsPointCar5 = GetRatioOfGpsPoint(point3d5, getGudaoOfGpsPoint5);

                                            switch (getGudaoOfGpsPoint5) {
                                                case 1:
                                                    OneDataDao oneDataDao = new OneDataDao(getApplication());
                                                    oneDataDao.add(getGudaoOfGpsPoint5 + "", lat5, lon5, getRatioOfGpsPointCar5 + "");
                                                    break;
                                            }
                                            break;
                                    }
                                } else {

                                }
                                break;
                        }
                        //mTotal = "2001" + function + "02";
                    }
                } catch (Exception e) {
                    Log.e("数据异常", "数据异常：" + e);
                }
            }
        });
    }

    private void sixPerson(){
        List<PersonDataUser> personDataUsers1 = mSixDataDao.find();
        int size2 = personDataUsers1.size();
        mLat21 = personDataUsers1.get(size2 - 1).getLat();
        mLon21 = personDataUsers1.get(size2 - 1).getLon();
        mLatStopCar01 = Double.valueOf(mLat21);
        mLonStopCar01 = Double.valueOf(mLon21);
        //获取股道号
        mGetGudaoOfGpsPoint2 = GetGudaoOfGpsPoint(mLonStopCar01, mLatStopCar01);
        //mJuli.setText(getGudaoOfGpsPoint + "");
        mRatioOfGpsTrackCar2 = String.valueOf(mGetGudaoOfGpsPoint2);
        Point3d point3d2 = new Point3d();
        point3d2.setX(mLonStopCar01);
        point3d2.setY(mLatStopCar01);
        //获取位置
        mGetRatioOfGpsPointCar2 = GetRatioOfGpsPoint(point3d2, mGetGudaoOfGpsPoint2);
        DecimalFormat df12 = new DecimalFormat("#####0.00%");
        DecimalFormatSymbols symbols12 = new DecimalFormatSymbols();
        df12.setDecimalFormatSymbols(symbols12);
        String ratioOfGpsPoint2 = df12.format(mGetRatioOfGpsPointCar2);
        String gpsPoint2 = ratioOfGpsPoint2.substring(0, ratioOfGpsPoint2.indexOf("."));
        mGpsPistance2 = Double.valueOf(gpsPoint2);
    }

    private String zhuyi(String fuction) {
        String s = mId1 + mSubstring + fuction + "02";
        return s;
    }

    public String getSum(String instructions) {
        mName1 = mCqncast.getName();
        if (mName1.length() >= 4) {
            mFace = mName1.substring(0, 2);
            mId = mName1.substring(2, 4);
            //2001" + function + "02
            String form = "A5" + "01" + mFace + instructions + "01" + "02";
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
            mCumulative = data + key;
            Log.i("setUp", data + "    00");
            Log.i("cumulative", mCumulative + "    00");
            //Log.i("parse", parse + "    00");
            //Log.i("parseInt", parseInt + "    00");
            Log.i("form", form + "    00");
        }
        return mCumulative;
    }

    public double getDistance(double lat1, double lon1, double lat2, double lon2) {
        float[] results = new float[1];
        Location.distanceBetween(lat1, lon1, lat2, lon2, results);
        return results[0];
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

    //直线提取公共方法
    private void setStraightLine(double a, double b, double c) {
        ObjectAnimator animator
                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (a - transverse + b * c));
        animator.setDuration(mTime);
        animator.start();
    }

    private String mJump = "false";
    /*private double a1 = 36.659485000001234;
    private double b1 = 101.76685599999999;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);
        setSystemUIVisible(false);
        mDelete = findViewById(R.id.delete_btn);
        mXiningbeimap = findViewById(R.id.xiningbeimap);
        mChangfengmap = findViewById(R.id.changfengmap);
        mBailimap = findViewById(R.id.bailimap);
        mTrain = findViewById(R.id.drawtop);
        mPointText = findViewById(R.id.point_text);
        mBtn = findViewById(R.id.taotao_btn);
        mBtn1 = findViewById(R.id.taotao_btn1);
        mJuli = findViewById(R.id.juli);
        mJwd = findViewById(R.id.lat);
        mLat1 = findViewById(R.id.lat1);
        mLat2 = findViewById(R.id.lat2);
        mTransferpeople = findViewById(R.id.transferpeople);
        mPeopleOne = findViewById(R.id.peopleone);
        mPeopletwo = findViewById(R.id.peopletwo);
        mPeoplethree = findViewById(R.id.peoplethree);
        mPeoplefour = findViewById(R.id.peoplefour);

        addLoc();

        //if (mJump.equals("false")) {
        /*Intent intent = new Intent(PointActivity.this, ChannelActivity.class);
        startActivity(intent);*/
        //mJump = "true";
        //}

        mAppService = AppJoint.service(TestService.class);

        mGpsDao = new GPSDao(getApplicationContext());
        //mGpsDao.add("36.659485000000004", "101.768762");

        /*//摘钩
        mPickDao = new PickDao(getApplicationContext());
        //mPickDao.add("133438", "463264");
        double latDifference = a1 - 0.000017;
        double lonDifference = b1 - 0.000021;
        String a2 = String.valueOf(latDifference);
        String b2 = String.valueOf(lonDifference);
        String lat1 = a2.substring(a2.indexOf(".") + 1);
        String lon1 = b2.substring(b2.indexOf(".") + 1);
        mTotal1 = "0A-机车GPS-" + lat1 + "-" + lon1;
        Log.e("弯点", "弯点a1: " + latDifference + " ");
        Log.e("弯点", "弯点b1: " + lonDifference + " ");
        //mGpsDao = new GPSDao(getApplicationContext());
        //mJwd.setText(lat + "    " + lon);
        mGpsDao.add(a2, b2);*/

        /*mTrain.setX(384 - transverse);
        mTrain.setY(500 - disparity);*/
        //控制三个布局
        //站内图
        mMain = new SpUtil(getApplicationContext(), "main");
        mBaiLi = new SpUtil(getApplicationContext(), "baili");
        mChangFeng = new SpUtil(getApplicationContext(), "changfeng");

        mFirstInto = new SpUtil(getApplicationContext(), "firstinto");

        //dmr控制推进信令
        mAdvancedmr = new SpUtil(getApplicationContext(), "advancedmr");

        //1道停留车左点
        mOnePickLeft = new SpUtil(getApplicationContext(), "onepickleft");
        mOnePickLeft.setName("0");
        //1道停留车右点
        mOnepickrightight = new SpUtil(getApplicationContext(), "onepickright");
        mOnepickrightight.setName("0");

        //领车
        mLeadcar = new SpUtil(getApplicationContext(), "leadcar");

        //停车后查看机车位置，为了绑定摘挂钩时候的人员位置与机车位置
        mStopcar = new SpUtil(getApplicationContext(), "stopcar");
        //查看机车位置
        mCarLocation = new SpUtil(getApplicationContext(), "carlocation");

        //调车长开机后通知是否是调车长领车还是几号制动员领车
        String name = mFirstInto.getName();
        if (name.equals("false")) {
            mSiveDao = new SiveDao(getApplicationContext());
            List<SuoData> suoData = mSiveDao.find();
            if (suoData != null) {
                int size = suoData.size();
                if (size > 0) {
                    for (int i = 0; i < size; i++) {
                        String ack1 = suoData.get(i).getAck();
                        jieAll += ack1;
                        //Log.e("jiesuo", ack1 + "");
                    }
                    Log.e("jiesuo", jieAll + "");
                    if (jieAll.indexOf("73") != -1) {
                        jieAll = "";
                    } else {
                        //sendMessage(mId, "8");
                        sendMessage(mConversationId, "9");
                        //mControlshuntinghunting.setName("unlock");
                        jieAll = "";
                    }
                } else {
                    sendMessage(mConversationId, "9");
                }
            } else {
                sendMessage(mConversationId, "9");
            }
            mFirstInto.setName("true");
        }

        //new TimeThread().start(); //启动新的线程

        mReceive1 = new SpUtil(getApplicationContext(), "receive1");
        mReceive2 = new SpUtil(getApplicationContext(), "receive2");
        mReceive3 = new SpUtil(getApplicationContext(), "receive3");
        qgs = new SpUtil(getApplicationContext(), "qgs");

        //发送机车、检测人员位置

        mHandler.postDelayed(mRunnable, 1500);
        mCqncast = new SpUtil(getApplicationContext(), "cqncast");
        mInstructions = new SpUtil(getApplicationContext(), "instructions");
        mControlCar = new SpUtil(getApplicationContext(), "controlcar");
        //根据制动员解锁状态来判断调车长是否启动
        mControlshuntinghunting = new SpUtil(getApplicationContext(), "controlshunting");
        //mControlshuntinghunting.setName("unlock");
        //注意十车五车三车一车
        mControlTuiJin = new SpUtil(getApplicationContext(), "controltuijin");
        //控制停车
        //mDataTransmission = new SpUtil(getApplicationContext(), "datatransmission");
        //mDataTransmission.setName("false");
        //人员号5
        mPeople5 = new SpUtil(getApplicationContext(), "people5");
        //人员号6
        mPeople6 = new SpUtil(getApplicationContext(), "people6");
        //人员号7
        mPeople7 = new SpUtil(getApplicationContext(), "people7");
        //人员号8
        mPeople8 = new SpUtil(getApplicationContext(), "people8");
        //人员号9
        mPeople9 = new SpUtil(getApplicationContext(), "people9");

        mMap1 = new SpUtil(getApplicationContext(), "map1");
        mMap2 = new SpUtil(getApplicationContext(), "map2");
        mMap3 = new SpUtil(getApplicationContext(), "map3");
        /*mMap1.setName("visible");
        mMap2.setName("gone");
        mMap3.setName("gone");*/
        mLeftCar = new SpUtil(getApplicationContext(), "leftcar");
        mLeftCar.setName("000000");
        mRightCar = new SpUtil(getApplicationContext(), "rightcar");
        mRightCar.setName("000000");
        mBtn.setText("平调系统");
        mFiveDataDao = new FiveDataDao(getApplicationContext());
        mSixDataDao = new SixDataDao(getApplicationContext());
        mSevenDataDao = new SevenDataDao(getApplicationContext());
        mEightDataDao = new EightDataDao(getApplicationContext());
        mNineDataDao = new NineDataDao(getApplicationContext());
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPickDao.del("zhaigouGPS");
                mGpsDao.del("Gps");
                mFiveDataDao.del("fiveperson");
                mSixDataDao.del("sixperson");
                mSevenDataDao.del("sevenperson");
                mEightDataDao.del("eightperson");
                mNineDataDao.del("nineperson");
            }
        });

        /*mWanAsynTask = new WanAsynTask(getApplication());
        AssetsDatabaseManager db = AssetsDatabaseManager.getManager();
        mMDatabase = db.getDatabase("wandian.db");*/

        /*int getGudaoOfGpsPoint = GetGudaoOfGpsPoint(101.771547, 36.646941);
        //mJuli.setText(getGudaoOfGpsPoint + "");
        mRatioOfGpsTrackCar = String.valueOf(getGudaoOfGpsPoint);
        Point3d point3d = new Point3d();
        point3d.setX(101.771547);
        point3d.setY(36.646941);
        mGetRatioOfGpsPointCar = GetRatioOfGpsPoint(point3d, getGudaoOfGpsPoint);

        DecimalFormat df1 = new DecimalFormat("#####0.00%");
        DecimalFormatSymbols symbols1 = new DecimalFormatSymbols();
        df1.setDecimalFormatSymbols(symbols1);
        String ratioOfGpsPoint = df1.format(mGetRatioOfGpsPointCar);
        String gpsPoint = ratioOfGpsPoint.substring(0, ratioOfGpsPoint.indexOf("."));
        mGpsPistanceCar = Double.valueOf(gpsPoint);
        Log.i("TAG", "mRatioOfGpsTrackCar" + mRatioOfGpsTrackCar);
        Log.i("TAG", "mGetRatioOfGpsPointCar" + mGetRatioOfGpsPointCar);
        Log.i("TAG", "mGpsPistanceCar" + mGpsPistanceCar);

        proplrMove();*/

        /*DecimalFormat df = new DecimalFormat("#.000000");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        df.setDecimalFormatSymbols(symbols);
        String lat = df.format(Double.valueOf(a1));
        String lon = df.format(Double.valueOf(b1));
        Double latDouble = Double.valueOf(lat);
        Double lonDouble = Double.valueOf(lon);
        Log.i("二宝",""+a1);
        Log.i("二宝",""+b1);
        //大圆盘坐标减去与实际坐标的差值
        double latDifference = latDouble - 0.000017;
        double lonDifference = lonDouble - 0.000021;
        String a2 = String.valueOf(latDifference);
        String b2 = String.valueOf(lonDifference);
        Log.i("二宝a2",""+a2);
        Log.i("二宝b2",""+b2);
        String lat1 = a2.substring(a2.indexOf(".") + 1);
        String lon1 = b2.substring(b2.indexOf(".") + 1);
        Log.i("二宝lat1",""+lat1);
        Log.i("二宝lon1",""+lon1);
        mTotal1 = "0A-机车GPS-" + lat1 + "-" + lon1;
        String ma = lat.substring(lat.length() - 4, lat.length());
        String mb = lon.substring(lon.length() - 4, lon.length());
        float mvalue1 = Float.valueOf(ma);
        float mvalue2 = Float.valueOf(mb);
        //mGpsDao = new GPSDao(getApplicationContext());
        //mJwd.setText(lat + "    " + lon);
        mGpsDao.add(a2, b2);
        mGpsUsers = mGpsDao.find();
        int size = mGpsUsers.size();
        Log.i("二宝size",""+size);
        String latsize = mGpsUsers.get(size - 1).getLat();
        String lonsize = mGpsUsers.get(size - 1).getLon();
        Log.i("二宝size",""+latsize);
        Log.i("二宝size",""+lonsize);*/
    }

    private Handler mHandler = new Handler();
    private Runnable mRunnable = new Runnable() {
        @Override
        public void run() {
            /*mGpsUsers = mGpsDao.find();
            int size = mGpsUsers.size();
            if (size > 0) {
                String lat = mGpsUsers.get(size - 1).getLat();
                String lon = mGpsUsers.get(size - 1).getLon();
                Log.i("秦广帅1000", lat);
                Log.i("秦广帅1000", lon);

                DecimalFormat df = new DecimalFormat("#.000000");
                String lat1 = df.format(Double.valueOf(lat)).substring(df.format(Double.valueOf(lat)).indexOf(".") + 1);
                String lon1 = df.format(Double.valueOf(lon)).substring(df.format(Double.valueOf(lon)).indexOf(".") + 1);

                *//*String lat2 = lat1.substring(3, lat.length());
                String lon2 = lon1.substring(4, lon.length());*//*
                String total = "0A-机车GPS-" + lat1 + "-" + lon1;
                sendMessage(mConversationId, total);
                Log.i("秦广帅1000", total);//0A-机车GPS-667636-768050
            }*/

            String mLeftCarName = mLeftCar.getName();
            String mRightCarName = mRightCar.getName();
            if (mLeftCarName != null && mRightCarName != null) {
                DecimalFormat df = new DecimalFormat("#.000000");
                String lat1 = df.format(Double.valueOf(mLeftCarName)).substring(df.format(Double.valueOf(mLeftCarName)).indexOf(".") + 1);
                String lon1 = df.format(Double.valueOf(mRightCarName)).substring(df.format(Double.valueOf(mRightCarName)).indexOf(".") + 1);
                String total = "0A-机车GPS-" + lat1 + "-" + lon1;
                sendMessage(mConversationId, total);
                Log.i("秦广帅1000", total);//0A-机车GPS-667636-768050
            }
            mTrain.invalidate();

            //调车长
            String name = mPeople5.getName();
            if (name != null) {
                switch (name) {
                    case "true":
                        FiveDataDao fiveDataDao = new FiveDataDao(getApplicationContext());
                        List<PersonDataUser> fiveDataUsers = fiveDataDao.find();
                        int size0 = fiveDataUsers.size();
                        String lat = fiveDataUsers.get(size0 - 1).getLat();
                        String lon = fiveDataUsers.get(size0 - 1).getLon();
                        Double lat1 = Double.valueOf(lat);
                        Double lon1 = Double.valueOf(lon);
                        int getGudaoOfGpsPoint = GetGudaoOfGpsPoint(lon1, lat1);
                        //mJuli.setText(getGudaoOfGpsPoint + "");
                        mRatioOfGpsTrack = String.valueOf(getGudaoOfGpsPoint);
                        Point3d point3d = new Point3d();
                        point3d.setX(lon1);
                        point3d.setY(lat1);
                        double getRatioOfGpsPoint = GetRatioOfGpsPoint(point3d, getGudaoOfGpsPoint);
                        DecimalFormat df1 = new DecimalFormat("#####0.00%");
                        DecimalFormatSymbols symbols1 = new DecimalFormatSymbols();
                        df1.setDecimalFormatSymbols(symbols1);
                        String ratioOfGpsPoint = df1.format(getRatioOfGpsPoint);
                        String gpsPoint = ratioOfGpsPoint.substring(0, ratioOfGpsPoint.indexOf("."));
                        mGpsPistance = Double.valueOf(gpsPoint);
                        //mJwd.setText(ratioOfGpsPoint + "");
                        //mLat1.setText(getRatioOfGpsPoint + "");
                        //mLat2.setText(gpsPoint);
                        proplrMove0();
                        break;
                }
            }

            //制动员6
            String name21 = mPeople6.getName();
            if (name21 != null) {
                switch (name21) {
                    case "true":
                        SixDataDao sixDataDao = new SixDataDao(getApplicationContext());
                        List<PersonDataUser> sixDataUsers = sixDataDao.find();
                        int size1 = sixDataUsers.size();
                        String lat2 = sixDataUsers.get(size1 - 1).getLat();
                        String lon2 = sixDataUsers.get(size1 - 1).getLon();
                        Double lat12 = Double.valueOf(lat2);
                        Double lon12 = Double.valueOf(lon2);
                        int getGudaoOfGpsPoint2 = GetGudaoOfGpsPoint(lon12, lat12);
                        //mJuli.setText(getGudaoOfGpsPoint + "");
                        mRatioOfGpsTrack2 = String.valueOf(getGudaoOfGpsPoint2);
                        Point3d point3d2 = new Point3d();
                        point3d2.setX(lon12);
                        point3d2.setY(lat12);
                        double getRatioOfGpsPoint2 = GetRatioOfGpsPoint(point3d2, getGudaoOfGpsPoint2);
                        DecimalFormat df12 = new DecimalFormat("#####0.00%");
                        DecimalFormatSymbols symbols12 = new DecimalFormatSymbols();
                        df12.setDecimalFormatSymbols(symbols12);
                        String ratioOfGpsPoint2 = df12.format(getRatioOfGpsPoint2);
                        String gpsPoint2 = ratioOfGpsPoint2.substring(0, ratioOfGpsPoint2.indexOf("."));
                        mGpsPistance2 = Double.valueOf(gpsPoint2);
                        mJwd.setText(getGudaoOfGpsPoint2 + "");
                        mLat1.setText(getRatioOfGpsPoint2 + "");
                        mLat2.setText(mGpsPistance2 + "");
                        proplrMove1();
                        break;
                }
            }

            //制动员7
            String name31 = mPeople7.getName();
            if (name31 != null) {
                switch (name31) {
                    case "true":
                        SevenDataDao sevenDataDao = new SevenDataDao(getApplicationContext());
                        List<PersonDataUser> sevenDataUsers = sevenDataDao.find();
                        int size1 = sevenDataUsers.size();
                        String lat2 = sevenDataUsers.get(size1 - 1).getLat();
                        String lon2 = sevenDataUsers.get(size1 - 1).getLon();
                        Double lat12 = Double.valueOf(lat2);
                        Double lon12 = Double.valueOf(lon2);
                        int getGudaoOfGpsPoint2 = GetGudaoOfGpsPoint(lon12, lat12);
                        //mJuli.setText(getGudaoOfGpsPoint + "");
                        mRatioOfGpsTrack3 = String.valueOf(getGudaoOfGpsPoint2);
                        Point3d point3d2 = new Point3d();
                        point3d2.setX(lon12);
                        point3d2.setY(lat12);
                        double getRatioOfGpsPoint2 = GetRatioOfGpsPoint(point3d2, getGudaoOfGpsPoint2);
                        DecimalFormat df12 = new DecimalFormat("#####0.00%");
                        DecimalFormatSymbols symbols12 = new DecimalFormatSymbols();
                        df12.setDecimalFormatSymbols(symbols12);
                        String ratioOfGpsPoint2 = df12.format(getRatioOfGpsPoint2);
                        String gpsPoint2 = ratioOfGpsPoint2.substring(0, ratioOfGpsPoint2.indexOf("."));
                        mGpsPistance3 = Double.valueOf(gpsPoint2);
                        //mJwd.setText(ratioOfGpsPoint + "");
                        //mLat1.setText(getRatioOfGpsPoint + "");
                        //mLat2.setText(gpsPoint);
                        proplrMove2();
                        break;
                }
            }

            //制动员8
            String name4 = mPeople8.getName();
            if (name4 != null) {
                switch (name4) {
                    case "true":
                        EightDataDao eightDataDao = new EightDataDao(getApplicationContext());
                        List<PersonDataUser> eightDataUsers = eightDataDao.find();
                        int size1 = eightDataUsers.size();
                        String lat2 = eightDataUsers.get(size1 - 1).getLat();
                        String lon2 = eightDataUsers.get(size1 - 1).getLon();
                        Double lat12 = Double.valueOf(lat2);
                        Double lon12 = Double.valueOf(lon2);
                        int getGudaoOfGpsPoint2 = GetGudaoOfGpsPoint(lon12, lat12);
                        //mJuli.setText(getGudaoOfGpsPoint + "");
                        mRatioOfGpsTrack4 = String.valueOf(getGudaoOfGpsPoint2);
                        Point3d point3d2 = new Point3d();
                        point3d2.setX(lon12);
                        point3d2.setY(lat12);
                        double getRatioOfGpsPoint2 = GetRatioOfGpsPoint(point3d2, getGudaoOfGpsPoint2);
                        DecimalFormat df12 = new DecimalFormat("#####0.00%");
                        DecimalFormatSymbols symbols12 = new DecimalFormatSymbols();
                        df12.setDecimalFormatSymbols(symbols12);
                        String ratioOfGpsPoint2 = df12.format(getRatioOfGpsPoint2);
                        String gpsPoint2 = ratioOfGpsPoint2.substring(0, ratioOfGpsPoint2.indexOf("."));
                        mGpsPistance4 = Double.valueOf(gpsPoint2);
                        //mJwd.setText(ratioOfGpsPoint + "");
                        //mLat1.setText(getRatioOfGpsPoint + "");
                        //mLat2.setText(gpsPoint);
                        proplrMove3();
                        break;
                }
            }

            //制动员9
            String name5 = mPeople9.getName();
            if (name5 != null) {
                switch (name5) {
                    case "true":
                        NineDataDao nineDataDao = new NineDataDao(getApplicationContext());
                        List<PersonDataUser> nineDataUsers = nineDataDao.find();
                        int size1 = nineDataUsers.size();
                        String lat2 = nineDataUsers.get(size1 - 1).getLat();
                        String lon2 = nineDataUsers.get(size1 - 1).getLon();
                        Double lat12 = Double.valueOf(lat2);
                        Double lon12 = Double.valueOf(lon2);
                        int getGudaoOfGpsPoint2 = GetGudaoOfGpsPoint(lon12, lat12);
                        //mJuli.setText(getGudaoOfGpsPoint + "");
                        mRatioOfGpsTrack5 = String.valueOf(getGudaoOfGpsPoint2);
                        Point3d point3d2 = new Point3d();
                        point3d2.setX(lon12);
                        point3d2.setY(lat12);
                        double getRatioOfGpsPoint2 = GetRatioOfGpsPoint(point3d2, getGudaoOfGpsPoint2);
                        DecimalFormat df12 = new DecimalFormat("#####0.00%");
                        DecimalFormatSymbols symbols12 = new DecimalFormatSymbols();
                        df12.setDecimalFormatSymbols(symbols12);
                        String ratioOfGpsPoint2 = df12.format(getRatioOfGpsPoint2);
                        String gpsPoint2 = ratioOfGpsPoint2.substring(0, ratioOfGpsPoint2.indexOf("."));
                        mGpsPistance5 = Double.valueOf(gpsPoint2);
                        //mJwd.setText(ratioOfGpsPoint + "");
                        //mLat1.setText(getRatioOfGpsPoint + "");
                        //mLat2.setText(gpsPoint);
                        proplrMove4();
                        break;
                }
            }

            mHandler.postDelayed(mRunnable, 1500);
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mHandler.removeCallbacks(mRunnable);
        mFirstInto.setName("false");
        //mJump = "false";
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
    protected void onPause() {
        super.onPause();
    }

    @Override
    public void onResume() {
        //设置为横屏
        if (getRequestedOrientation() != ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
        }
        super.onResume();

        mBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //qiehuan = true;
                startActivity(new Intent(PointActivity.this, ChannelActivity.class));
                //mDataTransmission.setName("false");
            }
        });
        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //qiehuan = false;
                mCon = new SpUtil(getApplicationContext(), "con");
                String name = mCon.getName();
                mLat1.setText(name + "");
            }
        });
        //mDataTransmission.setName("true");

        /*FiveDataDao fiveDataDao = new FiveDataDao(getApplicationContext());
        List<PersonDataUser> fiveDataUsers = fiveDataDao.find();
        int size = fiveDataUsers.size();
        String lat = fiveDataUsers.get(size - 1).getLat();
        String lon = fiveDataUsers.get(size - 1).getLon();
        Double lat1 = Double.valueOf(lat);
        Double lon1 = Double.valueOf(lon);
        int getGudaoOfGpsPoint = GetGudaoOfGpsPoint(lat1, lon1);
        mJuli.setText(getGudaoOfGpsPoint + "");
        mRatioOfGpsTrack = String.valueOf(getGudaoOfGpsPoint);
        Point3d point3d = new Point3d();
        point3d.setX(lat1);
        point3d.setY(lon1);
        double getRatioOfGpsPoint = GetRatioOfGpsPoint(point3d, getGudaoOfGpsPoint);
        DecimalFormat df1 = new DecimalFormat("#####0.00%");
        DecimalFormatSymbols symbols1 = new DecimalFormatSymbols();
        df1.setDecimalFormatSymbols(symbols1);
        String ratioOfGpsPoint = df1.format(getRatioOfGpsPoint);
        String gpsPoint = ratioOfGpsPoint.substring(0, ratioOfGpsPoint.indexOf("."));
        mGpsPistance = Double.valueOf(gpsPoint);
        mJwd.setText(ratioOfGpsPoint + "");
        mLat1.setText(getRatioOfGpsPoint + "");
        mLat2.setText(gpsPoint);
        proplrMove();*/
    }

    /*class TimeThread extends Thread {
        @Override
        public void run() {
            do {
                try {
                    Thread.sleep(1000);
                    Message msg = new Message();
                    msg.what = 1;  //消息(一个整型值)
                    mHandler1.sendMessage(msg);// 每隔1秒发送一个msg给mHandler
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);
        }
    }*/

    //在主线程里面处理消息并更新UI界面
    /*@SuppressLint("HandlerLeak")
    private Handler mHandler1 = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1:
                    //每天12点自己删除
                    //获取系统时间
                    SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss");
                    Date curDate = new Date(System.currentTimeMillis());
                    String time = formatter.format(curDate);
                    Log.i("erhei",time);
                    if (time.equals("22:25:00")) {
                        GPSDao gpsDao = new GPSDao(getApplicationContext());
                        gpsDao.del("Gps");
                        SixDataDao sixDataDao = new SixDataDao(getApplicationContext());
                        sixDataDao.del("sixperson");
                    }

                    long sysTime = System.currentTimeMillis();//获取系统时间
                    CharSequence sysTimeStr = DateFormat.format("hh:mm:ss", sysTime);//时间显示格式
                    mLat2.setText(sysTimeStr); //更新时间
                    break;
                default:
                    break;

            }
        }
    };*/

    @Override
    protected void onStop() {
        super.onStop();
        //mDataTransmission.setName("false");
    }

    private void addLoc() {
        gps = TrackDataUtil.getGps();
    }

    public double DistanceOfPointToLine(double x0, double y0, double x1, double y1, double x2, double y2) {
        double jl = 0.0, A = 0.0, B = 0.0, C = 0.0, d = 0.0, d1 = 0.0, d2 = 0.0, x = 0.0, y = 0.0;
        A = y2 - y1;
        B = x1 - x2;
        C = x2 * y1 - x1 * y2;
        x = (B * B * x0 - A * B * y0 - A * C) / (A * A + B * B);         //垂足坐标
        y = (A * A * y0 - A * B * x0 - B * C) / (A * A + B * B);         //垂足坐标
        d = Math.abs((A * x0 + B * y0 + C) / Math.sqrt(A * A + B * B));  //点到直线距离
        d1 = Math.sqrt((x0 - x1) * (x0 - x1) + (y0 - y1) * (y0 - y1));   //点到点距离
        d2 = Math.sqrt((x0 - x2) * (x0 - x2) + (y0 - y2) * (y0 - y2));   //点到点距离
        if ((x <= Math.max(x1, x2)) && (x >= Math.min(x1, x2))) {
            jl = d;
        } else {
            jl = Math.min(d1, d2);
        }
        return jl;
    }

    public double GetGpsDistanceOfPointToLine(Point3d p, Point3d p1, Point3d p2) {
        double d = 0.0;
        double c, x1, y1, x2, y2;
        double lat;    //纬度
        double lon;    //经度
        double R = 6371393.0;   //平均半径  数据来源:百度百科
        //平均纬度
        c = (p.X + p1.X + p2.X) / 3.0;
        //经纬度1度对应距离
        lat = 2 * Math.PI * R / 360.0;
        lon = 2 * Math.PI * R * Math.cos(c * Math.PI / 180.0) / 360.0;
        //以米为单位的新坐标
        x1 = (p1.X - p.X) * lat;
        y1 = (p1.Y - p.Y) * lon;
        x2 = (p2.X - p.X) * lat;
        y2 = (p2.Y - p.Y) * lon;
        d = DistanceOfPointToLine(0d, 0d, x1, y1, x2, y2);
        return d;
    }

    public int GetGudaoOfGpsPoint(double x, double y) {
        int i, j, n;
        double dis = 5.0, dd = 0d;
        n = -1;
        Point3d p = new Point3d();
        p.X = x;
        p.Y = y;
        //p.Z = 0d;
        for (i = 1; i <= gps.size(); i++) {
            for (j = 0; j < gps.get(i).size() - 1; j++) {
                dd = GetGpsDistanceOfPointToLine(p, gps.get(i).get(j), gps.get(i).get(j + 1));
                Log.i(TAG, "dd：" + dd);
                if (dd < dis) {
                    dis = dd;
                    n = i;                              //GPS股道从9开始，UWB股道从4开始
                }
            }
            //mTv3.setText("点到股道距离:     gudao: " + i + "     " + dis);
        }
        if (dis > 5.0) {
            n = -1;
        }
        return n;
    }

    public double GetRatioOfGpsPoint(Point3d p, int gd) {
        Log.i(TAG, "Point3d：" + p.X);
        Log.i(TAG, "Point3d：" + p.Y);
        Log.i(TAG, "gd：" + gd);
        double r = 0d;
        double A, B, C, x, y, x1, x2;
        if (gd >= 0) {
            Point3d p1 = new Point3d();
            Point3d p2 = new Point3d();
            mGuDao = gd;
            int size = gps.get(mGuDao).size();
            p1 = gps.get(mGuDao).get(0);
            p2 = gps.get(mGuDao).get(size - 1);
            Log.i(TAG, "size秦广帅：" + size);
            Log.i(TAG, "p1秦广帅：" + p1.X);
            Log.i(TAG, "p2秦广帅：" + p2.X);
            //p1 = listgpspt[gd][0];
            //p2 = listgpspt[gd][listgpspt[gd].Count - 1];
            A = p2.Y - p1.Y;
            B = p1.X - p2.X;
            C = p2.X * p1.Y - p1.X * p2.Y;
            x = (B * B * p.X - A * B * p.Y - A * C) / (A * A + B * B);         //垂足坐标
            y = (A * A * p.Y - A * B * p.X - B * C) / (A * A + B * B);         //垂足坐标
            DecimalFormat df = new DecimalFormat("#.000000");
            DecimalFormatSymbols symbols = new DecimalFormatSymbols();
            df.setDecimalFormatSymbols(symbols);
            String chui = df.format(x);
            Double aDouble = Double.valueOf(chui);
            Log.i(TAG, "x秦广帅：" + aDouble);
            x1 = gps.get(mGuDao).get(0).X;
            x2 = gps.get(mGuDao).get(size - 1).X;
            Log.i(TAG, "x1秦广帅：" + x1);
            Log.i(TAG, "x2秦广帅：" + x2);
            //x1 = listgpspt[gd][0].X;
            //x2 = listgpspt[gd][listgpspt[gd].Count - 1].X;
            //r = (x1 - x) / (x1 - x2);
            r = (aDouble - x2) / (x1 - x2);
            Log.i(TAG, "r：" + r);
            //n = (int)Math.Round(r * 100);
        }
        return r;
    }

    //机车
    private void proplrMove() {
        switch (mRatioOfGpsTrackCar) {
            case "1":
                twoTrack = false;
                threeTrack = false;
                fourTrack = false;
                fiveTrack = false;
                sixTrack = false;
                sevenTrack = false;
                eightTrack = false;
                mMap1.setName("visible");
                mMap2.setName("gone");
                mMap3.setName("gone");
                if (oneTrack == false) {
                    mTrain.setX(320 - transverse);
                    mTrain.setY(450 - disparity);
                    oneTrack = true;
                    if (mGpsPistanceCar <= 5) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (320 - transverse + mGpsPistanceCar * 12.8f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (450 - disparity + mGpsPistanceCar * 10f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistanceCar > 5 && mGpsPistanceCar <= 94) {
                        mTrain.setY(500 - disparity);
                        //setStraightLine(340, mGpsPistanceCar - 5, 2.88f);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (384 - transverse + (mGpsPistanceCar - 5) * 2.88f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (640 - transverse + (mGpsPistanceCar - 94) * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (500 - disparity + (mGpsPistanceCar - 94) * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistanceCar <= 5) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (320 - transverse + mGpsPistanceCar * 12.8f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (450 - disparity + mGpsPistanceCar * 10f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistanceCar > 5 && mGpsPistanceCar <= 94) {
                        mTrain.setY(500 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (384 - transverse + (mGpsPistanceCar - 5) * 2.88f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (640 - transverse + (mGpsPistanceCar - 94) * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (500 - disparity + (mGpsPistanceCar - 94) * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "2":
                mMap1.setName("visible");
                mMap2.setName("gone");
                mMap3.setName("gone");
                oneTrack = false;
                threeTrack = false;
                fourTrack = false;
                fiveTrack = false;
                sixTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (twoTrack == false) {
                    mTrain.setX(50 - transverse);
                    mTrain.setY(450 - disparity);
                    twoTrack = true;
                    if (mGpsPistanceCar <= 87) {
                        mTrain.setY(450 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (50 - transverse + mGpsPistanceCar * 8.25f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (768 - transverse + (mGpsPistanceCar - 87) * 4.92f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (450 - disparity + (mGpsPistanceCar - 87) * 3.84f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistanceCar <= 87) {
                        mTrain.setY(450 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (50 - transverse + mGpsPistanceCar * 8.25f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (768 - transverse + (mGpsPistanceCar - 87) * 4.92f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (450 - disparity + (mGpsPistanceCar - 87) * 3.84f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "3":
                mMap1.setName("visible");
                mMap2.setName("gone");
                mMap3.setName("gone");
                oneTrack = false;
                twoTrack = false;
                fourTrack = false;
                fiveTrack = false;
                sixTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (threeTrack == false) {
                    mTrain.setX(128 - transverse);
                    mTrain.setY(400 - disparity);
                    threeTrack = true;
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (128 - transverse + mGpsPistanceCar * 8.46f));
                    animator.setDuration(mTime);
                    animator.start();
                } else {
                    mTrain.setY(400 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (128 - transverse + mGpsPistanceCar * 8.46f));
                    animator.setDuration(mTime);
                    animator.start();
                }
                break;
            case "4":
                mMap1.setName("visible");
                mMap2.setName("gone");
                mMap3.setName("gone");
                oneTrack = false;
                twoTrack = false;
                threeTrack = false;
                fiveTrack = false;
                sixTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (fourTrack == false) {
                    mTrain.setX(256 - transverse);
                    mTrain.setY(400 - disparity);
                    fourTrack = true;
                    if (mGpsPistanceCar <= 6) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (256 - transverse + mGpsPistanceCar * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (400 - disparity - mGpsPistanceCar * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistanceCar > 6 && mGpsPistanceCar <= 94) {
                        mTrain.setY(350 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (320 - transverse + (mGpsPistanceCar - 6) * 4.36f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (704 - transverse + (mGpsPistanceCar - 94) * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (350 - disparity + (mGpsPistanceCar - 94) * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistanceCar <= 6) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (256 - transverse + mGpsPistanceCar * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (400 - disparity - mGpsPistanceCar * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistanceCar > 6 && mGpsPistanceCar <= 94) {
                        mTrain.setY(350 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (320 - transverse + (mGpsPistanceCar - 6) * 4.36f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (704 - transverse + (mGpsPistanceCar - 94) * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (350 - disparity + (mGpsPistanceCar - 94) * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "5":
                mMap1.setName("visible");
                mMap2.setName("gone");
                mMap3.setName("gone");
                oneTrack = false;
                twoTrack = false;
                threeTrack = false;
                fourTrack = false;
                sixTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (fiveTrack == false) {
                    mTrain.setX(320 - transverse);
                    mTrain.setY(350 - disparity);
                    fiveTrack = true;
                    if (mGpsPistanceCar <= 6) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (320 - transverse + mGpsPistanceCar * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (350 - disparity - mGpsPistanceCar * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistanceCar > 6 && mGpsPistanceCar <= 93) {
                        mTrain.setY(300 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (384 - transverse + (mGpsPistanceCar - 6) * 2.94f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (640 - transverse + (mGpsPistanceCar - 93) * 9.14f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (300 - disparity + (mGpsPistanceCar - 93) * 7.14f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistanceCar <= 6) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (320 - transverse + mGpsPistanceCar * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (350 - disparity - mGpsPistanceCar * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistanceCar > 6 && mGpsPistanceCar <= 93) {
                        mTrain.setY(300 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (384 - transverse + (mGpsPistanceCar - 6) * 2.94f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (640 - transverse + (mGpsPistanceCar - 93) * 9.14f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (300 - disparity + (mGpsPistanceCar - 93) * 7.14f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "6":
                mMap1.setName("visible");
                mMap2.setName("gone");
                mMap3.setName("gone");
                oneTrack = false;
                twoTrack = false;
                threeTrack = false;
                fourTrack = false;
                fiveTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (sixTrack == false) {
                    mTrain.setX(50 - transverse);
                    mTrain.setY(250 - disparity);
                    sixTrack = true;
                    if (mGpsPistanceCar <= 83) {
                        mTrain.setY(250 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (50 - transverse + mGpsPistanceCar * 8.65f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (768 - transverse + (mGpsPistanceCar - 83) * 7.53f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (250 - disparity + (mGpsPistanceCar - 83) * 8.82f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistanceCar <= 83) {
                        mTrain.setY(250 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (50 - transverse + mGpsPistanceCar * 8.65f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (768 - transverse + (mGpsPistanceCar - 83) * 7.53f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (250 - disparity + (mGpsPistanceCar - 83) * 8.82f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "7":
                mMap1.setName("visible");
                mMap2.setName("gone");
                mMap3.setName("gone");
                oneTrack = false;
                twoTrack = false;
                threeTrack = false;
                fourTrack = false;
                fiveTrack = false;
                sixTrack = false;
                eightTrack = false;
                if (sevenTrack == false) {
                    mTrain.setX(128 - transverse);
                    mTrain.setY(250 - disparity);
                    sevenTrack = true;
                    if (mGpsPistanceCar <= 20) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (128 - transverse + mGpsPistanceCar * 3.84f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (250 - disparity - mGpsPistanceCar * 5f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistanceCar > 20 && mGpsPistanceCar <= 78) {
                        mTrain.setY(150 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (205 - transverse + (mGpsPistanceCar - 20) * 9.71f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (768 - transverse + (mGpsPistanceCar - 78) * 2.91f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (150 - disparity + (mGpsPistanceCar - 78) * 7.95f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistanceCar <= 20) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (128 - transverse + mGpsPistanceCar * 3.84f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (250 - disparity - mGpsPistanceCar * 5f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistanceCar > 20 && mGpsPistanceCar <= 78) {
                        mTrain.setY(150 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (205 - transverse + (mGpsPistanceCar - 20) * 9.71f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (768 - transverse + (mGpsPistanceCar - 78) * 2.91f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (150 - disparity + (mGpsPistanceCar - 78) * 7.95f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "8":
                mMap1.setName("visible");
                mMap2.setName("gone");
                mMap3.setName("gone");
                oneTrack = false;
                twoTrack = false;
                threeTrack = false;
                fourTrack = false;
                sixTrack = false;
                fiveTrack = false;
                sevenTrack = false;
                if (eightTrack == false) {
                    mTrain.setX(230 - transverse1);
                    mTrain.setY(150 - disparity);
                    eightTrack = true;
                    if (mGpsPistanceCar <= 21) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (230 - transverse + mGpsPistanceCar * 3.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (150 - disparity - mGpsPistanceCar * 4.76f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistanceCar > 21 && mGpsPistanceCar <= 76) {
                        mTrain.setY(50 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (307 - transverse + (mGpsPistanceCar - 21) * 6.05f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (640 - transverse + (mGpsPistanceCar - 76) * 5.33f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (50 - disparity + (mGpsPistanceCar - 76) * 4.17f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistanceCar <= 21) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (230 - transverse + mGpsPistanceCar * 3.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (150 - disparity - mGpsPistanceCar * 4.76f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistanceCar > 21 && mGpsPistanceCar <= 76) {
                        mTrain.setY(50 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (307 - transverse + (mGpsPistanceCar - 21) * 6.05f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (640 - transverse + (mGpsPistanceCar - 76) * 5.33f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (50 - disparity + (mGpsPistanceCar - 76) * 4.17f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "9":
                mMap2.setName("visible");
                mMap1.setName("gone");
                mMap3.setName("gone");
                ten = false;
                eleven = false;
                twelve = false;
                thirteen = false;
                fourteen = false;
                if (nine == false) {
                    nine = true;
                    mTrain.setX(1000 - transverse1);
                    mTrain.setY(400 - disparity);
                    if (mGpsPistanceCar >= 81) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (1000 - transverse - (100 - mGpsPistanceCar) * 10.53f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else if (mGpsPistanceCar >= 43 && mGpsPistanceCar < 81) {
                        mTrain.setX(800 - transverse1);
                        mTrain.setY(400 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (800 - transverse - (81 - mGpsPistanceCar) * 2.7f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (400 - disparity + (81 - mGpsPistanceCar) * 2.7f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistanceCar >= 0 && mGpsPistanceCar < 43) {
                        mTrain.setY(700 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (700 - transverse - (43 - mGpsPistanceCar) * 4.76f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                } else {
                    if (mGpsPistanceCar >= 81) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (1000 - transverse - (100 - mGpsPistanceCar) * 10.53f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else if (mGpsPistanceCar >= 43 && mGpsPistanceCar < 81) {
                        mTrain.setX(800 - transverse1);
                        mTrain.setY(400 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (800 - transverse - (81 - mGpsPistanceCar) * 2.7f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (400 - disparity + (81 - mGpsPistanceCar) * 2.7f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistanceCar >= 0 && mGpsPistanceCar < 43) {
                        mTrain.setY(700 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (700 - transverse - (43 - mGpsPistanceCar) * 4.76f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                }
                break;
            case "10":
                mMap2.setName("visible");
                mMap1.setName("gone");
                mMap3.setName("gone");
                nine = false;
                eleven = false;
                twelve = false;
                thirteen = false;
                fourteen = false;
                if (ten == false) {
                    ten = true;
                    mTrain.setX(700 - transverse1);
                    mTrain.setY(500 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (700 - transverse + mGpsPistanceCar * 3f));
                    animator.setDuration(mTime);
                    animator.start();
                } else {
                    mTrain.setY(500 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (700 - transverse + mGpsPistanceCar * 3f));
                    animator.setDuration(mTime);
                    animator.start();
                }
                break;
            case "11":
                mMap2.setName("visible");
                mMap1.setName("gone");
                mMap3.setName("gone");
                nine = false;
                ten = false;
                twelve = false;
                thirteen = false;
                fourteen = false;
                if (eleven == false) {
                    eleven = true;
                    mTrain.setX(600 - transverse1);
                    mTrain.setY(400 - disparity);
                    if (mGpsPistanceCar >= 77) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (600 - transverse - (100 - mGpsPistanceCar) * 2.17f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (400 - disparity - (100 - mGpsPistanceCar) * 4.35f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mTrain.setY(300 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (550 - transverse - (76 - mGpsPistanceCar) * 6.58f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                } else {
                    if (mGpsPistanceCar >= 77) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (600 - transverse - (100 - mGpsPistanceCar) * 2.17f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (400 - disparity - (100 - mGpsPistanceCar) * 4.35f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mTrain.setY(300 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (550 - transverse - (76 - mGpsPistanceCar) * 6.58f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                }
                break;
            case "12":
                mMap2.setName("visible");
                mMap1.setName("gone");
                mMap3.setName("gone");
                nine = false;
                ten = false;
                eleven = false;
                thirteen = false;
                fourteen = false;
                if (twelve == false) {
                    twelve = true;
                    mTrain.setX(500 - transverse1);
                    mTrain.setY(300 - disparity);
                    if (mGpsPistanceCar >= 76) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (500 - transverse - (100 - mGpsPistanceCar) * 2.08f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (300 - disparity - (100 - mGpsPistanceCar) * 4.17f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistanceCar >= 26 && mGpsPistanceCar < 76) {
                        mTrain.setY(200 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (450 - transverse - (75 - mGpsPistanceCar) * 6.12f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (150 - transverse + (25 - mGpsPistanceCar) * 2f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (200 - disparity - (25 - mGpsPistanceCar) * 4f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistanceCar >= 76) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (500 - transverse - (100 - mGpsPistanceCar) * 2.08f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (300 - disparity - (100 - mGpsPistanceCar) * 4.17f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistanceCar >= 26 && mGpsPistanceCar < 76) {
                        mTrain.setY(200 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (450 - transverse - (75 - mGpsPistanceCar) * 6.12f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (150 - transverse + (25 - mGpsPistanceCar) * 2f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (200 - disparity - (25 - mGpsPistanceCar) * 4f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "13":
                mMap2.setName("visible");
                mMap1.setName("gone");
                mMap3.setName("gone");
                nine = false;
                ten = false;
                eleven = false;
                twelve = false;
                fourteen = false;
                if (thirteen == false) {
                    thirteen = true;
                    mTrain.setX(800 - transverse1);
                    mTrain.setY(400 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (800 - transverse - (100 - mGpsPistanceCar) * 7.5f));
                    animator.setDuration(mTime);
                    animator.start();
                } else {
                    mTrain.setY(400 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (800 - transverse - (100 - mGpsPistanceCar) * 7.5f));
                    animator.setDuration(mTime);
                    animator.start();
                }
                break;
            case "14":
                mMap2.setName("visible");
                mMap1.setName("gone");
                mMap3.setName("gone");
                nine = false;
                ten = false;
                eleven = false;
                twelve = false;
                thirteen = false;
                if (fourteen == false) {
                    fourteen = true;
                    mTrain.setX(450 - transverse1);
                    mTrain.setY(400 - disparity);
                    if (mGpsPistanceCar >= 47) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (450 - transverse - (100 - mGpsPistanceCar) * 1.89f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (400 - disparity + (100 - mGpsPistanceCar) * 1.89f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mTrain.setY(500 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (350 - transverse - (46 - mGpsPistanceCar) * 6.52f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                } else {
                    if (mGpsPistanceCar >= 47) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (450 - transverse - (100 - mGpsPistanceCar) * 1.89f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (400 - disparity + (100 - mGpsPistanceCar) * 1.89f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mTrain.setY(500 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (350 - transverse - (46 - mGpsPistanceCar) * 6.52f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                }
                break;
            case "15":
                mMap3.setName("visible");
                mMap1.setName("gone");
                mMap2.setName("gone");
                sixteen = false;
                seventeen = false;
                eighteen = false;
                nineteen = false;
                if (fifteen == false) {
                    fifteen = true;
                    mTrain.setX(50 - transverse1);
                    mTrain.setY(300 - disparity);
                    if (mGpsPistanceCar <= 42) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (50 - transverse + mGpsPistanceCar * 10.71f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (300 - disparity + mGpsPistanceCar * 5.95f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mTrain.setX(500 - transverse1);
                        mTrain.setY(550 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (500 - transverse + (mGpsPistanceCar - 43) * 5.26f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                } else {
                    if (mGpsPistanceCar <= 42) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (50 - transverse + mGpsPistanceCar * 10.71f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (300 - disparity + mGpsPistanceCar * 5.95f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mTrain.setY(550 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (500 - transverse + (mGpsPistanceCar - 43) * 5.26f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                }
                break;
            case "16":
                mMap3.setName("visible");
                mMap1.setName("gone");
                mMap2.setName("gone");
                fifteen = false;
                seventeen = false;
                eighteen = false;
                nineteen = false;
                if (sixteen == false) {
                    sixteen = true;
                    mTrain.setX(450 - transverse1);
                    mTrain.setY(350 - disparity);
                    if (mGpsPistanceCar <= 30) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (450 - transverse + mGpsPistanceCar * 3.33f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (350 - disparity + mGpsPistanceCar * 3.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mTrain.setY(450 - disparity);
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (550 - transverse + (mGpsPistanceCar - 31) * 4.93f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistanceCar <= 30) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (450 - transverse + mGpsPistanceCar * 3.33f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (350 - disparity + mGpsPistanceCar * 3.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mTrain.setY(450 - disparity);
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (550 - transverse + (mGpsPistanceCar - 31) * 4.93f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "17":
                mMap3.setName("visible");
                mMap1.setName("gone");
                mMap2.setName("gone");
                fifteen = false;
                sixteen = false;
                eighteen = false;
                nineteen = false;
                if (seventeen == false) {
                    seventeen = true;
                    mTrain.setX(150 - transverse1);
                    mTrain.setY(150 - disparity);
                    if (mGpsPistanceCar <= 52) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (150 - transverse + mGpsPistanceCar * 4.81f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (150 - disparity + mGpsPistanceCar * 3.85f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mTrain.setX(400 - transverse1);
                        mTrain.setY(350 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (400 - transverse + (mGpsPistanceCar - 53) * 8.51f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                } else {
                    if (mGpsPistanceCar <= 52) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (150 - transverse + mGpsPistanceCar * 4.81f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (150 - disparity + mGpsPistanceCar * 3.85f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mTrain.setY(350 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (400 - transverse + (mGpsPistanceCar - 53) * 8.51f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                }
                break;
            case "18":
                mMap3.setName("visible");
                mMap1.setName("gone");
                mMap2.setName("gone");
                fifteen = false;
                sixteen = false;
                seventeen = false;
                eighteen = false;
                nineteen = false;
                if (eighteen == false) {
                    eighteen = true;
                    mTrain.setX(500 - transverse1);
                    mTrain.setY(350 - disparity);
                    if (mGpsPistanceCar <= 8) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (500 - transverse + mGpsPistanceCar * 6.25f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (350 - disparity - mGpsPistanceCar * 12.5f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistanceCar > 8 && mGpsPistanceCar <= 79) {
                        mTrain.setY(250 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (550 - transverse + (mGpsPistanceCar - 8) * 2.82f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (750 - transverse + (mGpsPistanceCar - 79) * 2.38f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (250 - disparity + (mGpsPistanceCar - 79) * 4.76f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistanceCar <= 8) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (500 - transverse + mGpsPistanceCar * 6.25f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (350 - disparity - mGpsPistanceCar * 12.5f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistanceCar > 8 && mGpsPistanceCar <= 79) {
                        mTrain.setY(250 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (550 - transverse + (mGpsPistanceCar - 8) * 2.82f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (750 - transverse + (mGpsPistanceCar - 79) * 2.38f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTrain, "translationY", (float) (250 - disparity + (mGpsPistanceCar - 79) * 4.76f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "19":
                mMap3.setName("visible");
                mMap1.setName("gone");
                mMap2.setName("gone");
                fifteen = false;
                sixteen = false;
                seventeen = false;
                eighteen = false;
                if (nineteen == false) {
                    nineteen = true;
                    mTrain.setX(800 - transverse1);
                    mTrain.setY(350 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (800 - transverse + mGpsPistanceCar * 2f));
                    animator.setDuration(mTime);
                    animator.start();
                } else {
                    mTrain.setY(350 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mTrain, "translationX", (float) (800 - transverse + mGpsPistanceCar * 2f));
                    animator.setDuration(mTime);
                    animator.start();
                }
                break;
        }
    }

    //调车长
    private void proplrMove0() {
        switch (mRatioOfGpsTrack) {
            case "1":
                twoTrack = false;
                threeTrack = false;
                fourTrack = false;
                fiveTrack = false;
                sixTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (oneTrack == false) {
                    mTransferpeople.setX(320 - transverse1);
                    mTransferpeople.setY(450 - disparity);
                    oneTrack = true;
                    if (mGpsPistance <= 5) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (320 - transverse1 + mGpsPistance * 12.8f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (450 - disparity + mGpsPistance * 10f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance > 5 && mGpsPistance <= 94) {
                        //setStraightLine(340, mGpsPistance - 5, 2.88f);
                        mTransferpeople.setY(500 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (384 - transverse1 + (mGpsPistance - 5) * 2.88f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (640 - transverse1 + (mGpsPistance - 94) * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (500 - disparity + (mGpsPistance - 94) * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance <= 5) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (320 - transverse1 + mGpsPistance * 12.8f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (450 - disparity + mGpsPistance * 10f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance > 5 && mGpsPistance <= 94) {
                        mTransferpeople.setY(500 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (384 - transverse1 + (mGpsPistance - 5) * 2.88f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (640 - transverse1 + (mGpsPistance - 94) * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (500 - disparity + (mGpsPistance - 94) * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "2":
                oneTrack = false;
                threeTrack = false;
                fourTrack = false;
                fiveTrack = false;
                sixTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (twoTrack == false) {
                    mTransferpeople.setX(50 - transverse1);
                    mTransferpeople.setY(450 - disparity);
                    twoTrack = true;
                    if (mGpsPistance <= 87) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (50 - transverse1 + mGpsPistance * 8.25f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (768 - transverse1 + (mGpsPistance - 87) * 4.92f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (450 - disparity + (mGpsPistance - 87) * 3.84f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance <= 87) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (50 - transverse1 + mGpsPistance * 8.25f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (768 - transverse1 + (mGpsPistance - 87) * 4.92f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (450 - disparity + (mGpsPistance - 87) * 3.84f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "3":
                oneTrack = false;
                twoTrack = false;
                fourTrack = false;
                fiveTrack = false;
                sixTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (threeTrack == false) {
                    mTransferpeople.setX(128 - transverse1);
                    mTransferpeople.setY(400 - disparity);
                    threeTrack = true;
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (128 - transverse1 + mGpsPistance * 8.46f));
                    animator.setDuration(mTime);
                    animator.start();
                } else {
                    mTransferpeople.setY(400 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (128 - transverse1 + mGpsPistance * 8.46f));
                    animator.setDuration(mTime);
                    animator.start();
                }
                break;
            case "4":
                oneTrack = false;
                twoTrack = false;
                threeTrack = false;
                fiveTrack = false;
                sixTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (fourTrack == false) {
                    mTransferpeople.setX(256 - transverse1);
                    mTransferpeople.setY(400 - disparity);
                    fourTrack = true;
                    if (mGpsPistance <= 6) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (256 - transverse1 + mGpsPistance * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (400 - disparity - mGpsPistance * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance > 6 && mGpsPistance <= 94) {
                        mTransferpeople.setY(350 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (320 - transverse1 + (mGpsPistance - 6) * 4.36f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (704 - transverse1 + (mGpsPistance - 94) * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (350 - disparity + (mGpsPistance - 94) * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance <= 6) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (256 - transverse1 + mGpsPistance * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (400 - disparity - mGpsPistance * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance > 6 && mGpsPistance <= 94) {
                        mTransferpeople.setY(350 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (320 - transverse1 + (mGpsPistance - 6) * 4.36f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (704 - transverse1 + (mGpsPistance - 94) * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (350 - disparity + (mGpsPistance - 94) * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "5":
                oneTrack = false;
                twoTrack = false;
                threeTrack = false;
                fourTrack = false;
                sixTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (fiveTrack == false) {
                    mTransferpeople.setX(320 - transverse);
                    mTransferpeople.setY(350 - disparity);
                    fiveTrack = true;
                    if (mGpsPistance <= 6) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (320 - transverse1 + mGpsPistance * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (350 - disparity - mGpsPistance * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance > 6 && mGpsPistance <= 93) {
                        mTransferpeople.setY(300 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (384 - transverse1 + (mGpsPistance - 6) * 2.94f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (640 - transverse1 + (mGpsPistance - 93) * 9.14f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (300 - disparity + (mGpsPistance - 93) * 7.14f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance <= 6) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (320 - transverse1 + mGpsPistance * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (350 - disparity - mGpsPistance * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance > 6 && mGpsPistance <= 93) {
                        mTransferpeople.setY(300 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (384 - transverse1 + (mGpsPistance - 6) * 2.94f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (640 - transverse1 + (mGpsPistance - 93) * 9.14f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (300 - disparity + (mGpsPistance - 93) * 7.14f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "6":
                oneTrack = false;
                twoTrack = false;
                threeTrack = false;
                fourTrack = false;
                fiveTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (sixTrack == false) {
                    mTransferpeople.setX(50 - transverse1);
                    mTransferpeople.setY(250 - disparity);
                    sixTrack = true;
                    if (mGpsPistance <= 83) {
                        mTransferpeople.setY(250 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (50 - transverse1 + mGpsPistance * 8.65f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (768 - transverse1 + (mGpsPistance - 83) * 7.53f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (250 - disparity + (mGpsPistance - 83) * 8.82f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance <= 83) {
                        mTransferpeople.setY(250 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (50 - transverse1 + mGpsPistance * 8.65f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (768 - transverse1 + (mGpsPistance - 83) * 7.53f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (250 - disparity + (mGpsPistance - 83) * 8.82f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "7":
                oneTrack = false;
                twoTrack = false;
                threeTrack = false;
                fourTrack = false;
                fiveTrack = false;
                sixTrack = false;
                eightTrack = false;
                if (sevenTrack == false) {
                    mTransferpeople.setX(128 - transverse);
                    mTransferpeople.setY(250 - disparity);
                    sevenTrack = true;
                    if (mGpsPistance <= 20) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (128 - transverse1 + mGpsPistance * 3.84f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (250 - disparity - mGpsPistance * 5f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance > 20 && mGpsPistance <= 78) {
                        mTransferpeople.setY(150 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (205 - transverse1 + (mGpsPistance - 20) * 9.71f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (768 - transverse1 + (mGpsPistance - 78) * 2.91f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (150 - disparity + (mGpsPistance - 78) * 7.95f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance <= 20) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (128 - transverse1 + mGpsPistance * 3.84f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (250 - disparity - mGpsPistance * 5f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance > 20 && mGpsPistance <= 78) {
                        mTransferpeople.setY(150 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (205 - transverse1 + (mGpsPistance - 20) * 9.71f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (768 - transverse1 + (mGpsPistance - 78) * 2.91f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (150 - disparity + (mGpsPistance - 78) * 7.95f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "8":
                oneTrack = false;
                twoTrack = false;
                threeTrack = false;
                fourTrack = false;
                sixTrack = false;
                fiveTrack = false;
                sevenTrack = false;
                if (eightTrack == false) {
                    mTransferpeople.setX(230 - transverse1);
                    mTransferpeople.setY(150 - disparity);
                    eightTrack = true;
                    if (mGpsPistance <= 21) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (230 - transverse1 + mGpsPistance * 3.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (150 - disparity - mGpsPistance * 4.76f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance > 21 && mGpsPistance <= 76) {
                        mTransferpeople.setY(50 - transverse1);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (307 - transverse1 + (mGpsPistance - 21) * 6.05f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (640 - transverse1 + (mGpsPistance - 76) * 5.33f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (50 - disparity + (mGpsPistance - 76) * 4.17f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance <= 21) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (230 - transverse1 + mGpsPistance * 3.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (150 - disparity - mGpsPistance * 4.76f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance > 21 && mGpsPistance <= 76) {
                        mTransferpeople.setY(50 - transverse1);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (307 - transverse1 + (mGpsPistance - 21) * 6.05f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (640 - transverse1 + (mGpsPistance - 76) * 5.33f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (50 - disparity + (mGpsPistance - 76) * 4.17f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "9":
                ten = false;
                eleven = false;
                twelve = false;
                thirteen = false;
                fourteen = false;
                if (nine == false) {
                    nine = true;
                    mTransferpeople.setX(1000 - transverse1);
                    mTransferpeople.setY(400 - disparity);
                    if (mGpsPistance >= 81) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (1000 - transverse1 - (100 - mGpsPistance) * 10.53f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else if (mGpsPistance >= 43 && mGpsPistance < 81) {
                        /*mTransferpeople.setX(800 - transverse1);
                        mTransferpeople.setY(400 - disparity);*/
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (800 - transverse1 - (81 - mGpsPistance) * 2.7f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (400 - disparity + (81 - mGpsPistance) * 2.7f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mTransferpeople.setY(700 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (700 - transverse1 - (43 - mGpsPistance) * 4.76f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                } else {
                    if (mGpsPistance >= 81) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (1000 - transverse1 - (100 - mGpsPistance) * 10.53f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else if (mGpsPistance >= 43 && mGpsPistance < 81) {
                        /*mTransferpeople.setX(800 - transverse1);
                        mTransferpeople.setY(400 - disparity);*/
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (800 - transverse1 - (81 - mGpsPistance) * 2.7f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (400 - disparity + (81 - mGpsPistance) * 2.7f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance >= 0 && mGpsPistance < 43) {
                        mTransferpeople.setY(700 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (700 - transverse1 - (43 - mGpsPistance) * 4.76f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                }
                break;
            case "10":
                nine = false;
                eleven = false;
                twelve = false;
                thirteen = false;
                fourteen = false;
                if (ten == false) {
                    ten = true;
                    mTransferpeople.setX(700 - transverse1);
                    mTransferpeople.setY(500 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (700 - transverse1 + mGpsPistance * 3f));
                    animator.setDuration(mTime);
                    animator.start();
                } else {
                    mTransferpeople.setY(500 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (700 - transverse1 + mGpsPistance * 3f));
                    animator.setDuration(mTime);
                    animator.start();
                }
                break;
            case "11":
                nine = false;
                ten = false;
                twelve = false;
                thirteen = false;
                fourteen = false;
                if (eleven == false) {
                    eleven = true;
                    mTransferpeople.setX(600 - transverse1);
                    mTransferpeople.setY(400 - disparity);
                    if (mGpsPistance >= 77) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (600 - transverse1 - (100 - mGpsPistance) * 2.17f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (400 - disparity - (100 - mGpsPistance) * 4.35f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mTransferpeople.setY(300 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (550 - transverse1 - (76 - mGpsPistance) * 6.58f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                } else {
                    if (mGpsPistance >= 77) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (600 - transverse1 - (100 - mGpsPistance) * 2.17f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (400 - disparity - (100 - mGpsPistance) * 4.35f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mTransferpeople.setY(300 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (550 - transverse1 - (76 - mGpsPistance) * 6.58f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                }
                break;
            case "12":
                nine = false;
                ten = false;
                eleven = false;
                thirteen = false;
                fourteen = false;
                if (twelve == false) {
                    twelve = true;
                    mTransferpeople.setX(500 - transverse1);
                    mTransferpeople.setY(300 - disparity);
                    if (mGpsPistance >= 76) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (500 - transverse1 - (100 - mGpsPistance) * 2.08f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (300 - disparity - (100 - mGpsPistance) * 4.17f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance >= 26 && mGpsPistance < 76) {
                        mTransferpeople.setY(200 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (450 - transverse1 - (75 - mGpsPistance) * 6.12f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (150 - transverse1 + (25 - mGpsPistance) * 2f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (200 - disparity - (25 - mGpsPistance) * 4f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance >= 76) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (500 - transverse1 - (100 - mGpsPistance) * 2.08f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (300 - disparity - (100 - mGpsPistance) * 4.17f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance >= 26 && mGpsPistance < 76) {
                        mTransferpeople.setY(200 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (450 - transverse1 - (75 - mGpsPistance) * 6.12f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (150 - transverse1 + (25 - mGpsPistance) * 2f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (200 - disparity - (25 - mGpsPistance) * 4f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "13":
                nine = false;
                ten = false;
                eleven = false;
                twelve = false;
                fourteen = false;
                if (thirteen == false) {
                    thirteen = true;
                    mTransferpeople.setX(800 - transverse1);
                    mTransferpeople.setY(400 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (800 - transverse - (100 - mGpsPistance) * 7.5f));
                    animator.setDuration(mTime);
                    animator.start();
                } else {
                    mTransferpeople.setY(400 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (800 - transverse - (100 - mGpsPistance) * 7.5f));
                    animator.setDuration(mTime);
                    animator.start();
                }
                break;
            case "14":
                nine = false;
                ten = false;
                eleven = false;
                twelve = false;
                thirteen = false;
                if (fourteen == false) {
                    fourteen = true;
                    mTransferpeople.setX(450 - transverse1);
                    mTransferpeople.setY(400 - disparity);
                    if (mGpsPistance >= 47) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (450 - transverse1 - (100 - mGpsPistance) * 1.89f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (400 - disparity + (100 - mGpsPistance) * 1.89f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mTransferpeople.setY(500 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (350 - transverse1 - (46 - mGpsPistance) * 6.52f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                } else {
                    if (mGpsPistance >= 47) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (450 - transverse1 - (100 - mGpsPistance) * 1.89f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (400 - disparity + (100 - mGpsPistance) * 1.89f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mTransferpeople.setY(500 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (350 - transverse1 - (46 - mGpsPistance) * 6.52f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                }
                break;
            case "15":
                sixteen = false;
                seventeen = false;
                eighteen = false;
                nineteen = false;
                if (fifteen == false) {
                    fifteen = true;
                    mTransferpeople.setX(50 - transverse1);
                    mTransferpeople.setY(300 - disparity);
                    if (mGpsPistance <= 42) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (50 - transverse1 + mGpsPistance * 10.71f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (300 - disparity + mGpsPistance * 5.95f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mTransferpeople.setX(500 - transverse1);
                        mTransferpeople.setY(550 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (500 - transverse1 + (mGpsPistance - 43) * 5.26f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                } else {
                    if (mGpsPistance <= 42) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (50 - transverse1 + mGpsPistance * 10.71f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (300 - disparity + mGpsPistance * 5.95f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mTransferpeople.setY(550 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (500 - transverse1 + (mGpsPistance - 43) * 5.26f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                }
                break;
            case "16":
                fifteen = false;
                seventeen = false;
                eighteen = false;
                nineteen = false;
                if (sixteen == false) {
                    sixteen = true;
                    mTransferpeople.setX(450 - transverse1);
                    mTransferpeople.setY(350 - disparity);
                    if (mGpsPistance <= 30) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (450 - transverse1 + mGpsPistance * 3.33f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (350 - disparity + mGpsPistance * 3.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mTransferpeople.setY(450 - disparity);
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (550 - transverse1 + (mGpsPistance - 31) * 4.93f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance <= 30) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (450 - transverse1 + mGpsPistance * 3.33f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (350 - disparity + mGpsPistance * 3.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mTransferpeople.setY(450 - disparity);
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (550 - transverse1 + (mGpsPistance - 31) * 4.93f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "17":
                fifteen = false;
                sixteen = false;
                eighteen = false;
                nineteen = false;
                if (seventeen == false) {
                    seventeen = true;
                    mTransferpeople.setX(150 - transverse1);
                    mTransferpeople.setY(150 - disparity);
                    if (mGpsPistance <= 52) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (150 - transverse1 + mGpsPistance * 4.81f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (150 - disparity + mGpsPistance * 3.85f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mTransferpeople.setX(400 - transverse1);
                        mTransferpeople.setY(350 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (400 - transverse1 + (mGpsPistance - 53) * 8.51f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                } else {
                    if (mGpsPistance <= 52) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (150 - transverse1 + mGpsPistance * 4.81f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (150 - disparity + mGpsPistance * 3.85f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mTransferpeople.setY(350 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (400 - transverse1 + (mGpsPistance - 53) * 8.51f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                }
                break;
            case "18":
                fifteen = false;
                sixteen = false;
                seventeen = false;
                eighteen = false;
                nineteen = false;
                if (eighteen == false) {
                    eighteen = true;
                    mTransferpeople.setX(500 - transverse1);
                    mTransferpeople.setY(350 - disparity);
                    if (mGpsPistance <= 8) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (500 - transverse1 + mGpsPistance * 6.25f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (350 - disparity - mGpsPistance * 12.5f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance > 8 && mGpsPistance <= 79) {
                        mTransferpeople.setY(250 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (550 - transverse1 + (mGpsPistance - 8) * 2.82f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (750 - transverse1 + (mGpsPistance - 79) * 2.38f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (250 - disparity + (mGpsPistance - 79) * 4.76f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance <= 8) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (500 - transverse1 + mGpsPistance * 6.25f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (350 - disparity - mGpsPistance * 12.5f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance > 8 && mGpsPistance <= 79) {
                        mTransferpeople.setY(250 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (550 - transverse1 + (mGpsPistance - 8) * 2.82f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (750 - transverse1 + (mGpsPistance - 79) * 2.38f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mTransferpeople, "translationY", (float) (250 - disparity + (mGpsPistance - 79) * 4.76f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "19":
                mMap3.setName("visible");
                mMap1.setName("gone");
                mMap2.setName("gone");
                fifteen = false;
                sixteen = false;
                seventeen = false;
                eighteen = false;
                if (nineteen == false) {
                    nineteen = true;
                    mTransferpeople.setX(800 - transverse1);
                    mTransferpeople.setY(350 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (800 - transverse1 + mGpsPistance * 2f));
                    animator.setDuration(mTime);
                    animator.start();
                } else {
                    mTransferpeople.setY(350 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mTransferpeople, "translationX", (float) (800 - transverse1 + mGpsPistance * 2f));
                    animator.setDuration(mTime);
                    animator.start();
                }
                break;
        }
    }

    //制动员6
    private void proplrMove1() {
        switch (mRatioOfGpsTrack2) {
            case "1":
                twoTrack = false;
                threeTrack = false;
                fourTrack = false;
                fiveTrack = false;
                sixTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (oneTrack == false) {
                    mPeopleOne.setX(320 - transverse1);
                    mPeopleOne.setY(450 - disparity);
                    oneTrack = true;
                    if (mGpsPistance2 <= 5) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (320 - transverse1 + mGpsPistance2 * 12.8f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (450 - disparity + mGpsPistance2 * 10f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance2 > 5 && mGpsPistance2 <= 94) {
                        //setStraightLine(340, mGpsPistance2 - 5, 2.88f);
                        mPeopleOne.setY(500 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (384 - transverse1 + (mGpsPistance2 - 5) * 2.88f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (640 - transverse1 + (mGpsPistance2 - 94) * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (500 - disparity + (mGpsPistance2 - 94) * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance2 <= 5) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (320 - transverse1 + mGpsPistance2 * 12.8f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (450 - disparity + mGpsPistance2 * 10f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance2 > 5 && mGpsPistance2 <= 94) {
                        mPeopleOne.setY(500 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (384 - transverse1 + (mGpsPistance2 - 5) * 2.88f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (640 - transverse1 + (mGpsPistance2 - 94) * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (500 - disparity + (mGpsPistance2 - 94) * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "2":
                oneTrack = false;
                threeTrack = false;
                fourTrack = false;
                fiveTrack = false;
                sixTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (twoTrack == false) {
                    mPeopleOne.setX(50 - transverse1);
                    mPeopleOne.setY(450 - disparity);
                    twoTrack = true;
                    if (mGpsPistance2 <= 87) {
                        mPeopleOne.setY(450 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (50 - transverse1 + mGpsPistance2 * 8.25f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (768 - transverse1 + (mGpsPistance2 - 87) * 4.92f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (450 - disparity + (mGpsPistance2 - 87) * 3.84f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance2 <= 87) {
                        mPeopleOne.setY(450 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (50 - transverse1 + mGpsPistance2 * 8.25f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (768 - transverse1 + (mGpsPistance2 - 87) * 4.92f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (450 - disparity + (mGpsPistance2 - 87) * 3.84f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "3":
                oneTrack = false;
                twoTrack = false;
                fourTrack = false;
                fiveTrack = false;
                sixTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (threeTrack == false) {
                    mPeopleOne.setX(128 - transverse1);
                    mPeopleOne.setY(400 - disparity);
                    threeTrack = true;
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (128 - transverse1 + mGpsPistance2 * 8.46f));
                    animator.setDuration(mTime);
                    animator.start();
                } else {
                    mPeopleOne.setY(400 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (128 - transverse1 + mGpsPistance2 * 8.46f));
                    animator.setDuration(mTime);
                    animator.start();
                }
                break;
            case "4":
                oneTrack = false;
                twoTrack = false;
                threeTrack = false;
                fiveTrack = false;
                sixTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (fourTrack == false) {
                    mPeopleOne.setX(256 - transverse1);
                    mPeopleOne.setY(400 - disparity);
                    fourTrack = true;
                    if (mGpsPistance2 <= 6) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (256 - transverse1 + mGpsPistance2 * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (400 - disparity - mGpsPistance2 * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance2 > 6 && mGpsPistance2 <= 94) {
                        mPeopleOne.setY(350 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (320 - transverse1 + (mGpsPistance2 - 6) * 4.36f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (704 - transverse1 + (mGpsPistance2 - 94) * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (350 - disparity + (mGpsPistance2 - 94) * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance2 <= 6) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (256 - transverse1 + mGpsPistance2 * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (400 - disparity - mGpsPistance2 * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance2 > 6 && mGpsPistance2 <= 94) {
                        mPeopleOne.setY(350 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (320 - transverse1 + (mGpsPistance2 - 6) * 4.36f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (704 - transverse1 + (mGpsPistance2 - 94) * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (350 - disparity + (mGpsPistance2 - 94) * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "5":
                oneTrack = false;
                twoTrack = false;
                threeTrack = false;
                fourTrack = false;
                sixTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (fiveTrack == false) {
                    mPeopleOne.setX(320 - transverse);
                    mPeopleOne.setY(350 - disparity);
                    fiveTrack = true;
                    if (mGpsPistance2 <= 6) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (320 - transverse1 + mGpsPistance2 * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (350 - disparity - mGpsPistance2 * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance2 > 6 && mGpsPistance2 <= 93) {
                        mPeopleOne.setY(300 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (384 - transverse1 + (mGpsPistance2 - 6) * 2.94f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (640 - transverse1 + (mGpsPistance2 - 93) * 9.14f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (300 - disparity + (mGpsPistance2 - 93) * 7.14f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance2 <= 6) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (320 - transverse1 + mGpsPistance2 * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (350 - disparity - mGpsPistance2 * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance2 > 6 && mGpsPistance2 <= 93) {
                        mPeopleOne.setY(300 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (384 - transverse1 + (mGpsPistance2 - 6) * 2.94f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (640 - transverse1 + (mGpsPistance2 - 93) * 9.14f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (300 - disparity + (mGpsPistance2 - 93) * 7.14f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "6":
                oneTrack = false;
                twoTrack = false;
                threeTrack = false;
                fourTrack = false;
                fiveTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (sixTrack == false) {
                    mPeopleOne.setX(50 - transverse1);
                    mPeopleOne.setY(250 - disparity);
                    sixTrack = true;
                    if (mGpsPistance2 <= 83) {
                        mPeopleOne.setY(250 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (50 - transverse1 + mGpsPistance2 * 8.65f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (768 - transverse1 + (mGpsPistance2 - 83) * 7.53f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (250 - disparity + (mGpsPistance2 - 83) * 8.82f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance2 <= 83) {
                        mPeopleOne.setY(250 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (50 - transverse1 + mGpsPistance2 * 8.65f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (768 - transverse1 + (mGpsPistance2 - 83) * 7.53f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (250 - disparity + (mGpsPistance2 - 83) * 8.82f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "7":
                oneTrack = false;
                twoTrack = false;
                threeTrack = false;
                fourTrack = false;
                fiveTrack = false;
                sixTrack = false;
                eightTrack = false;
                if (sevenTrack == false) {
                    mPeopleOne.setX(128 - transverse);
                    mPeopleOne.setY(250 - disparity);
                    sevenTrack = true;
                    if (mGpsPistance2 <= 20) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (128 - transverse1 + mGpsPistance2 * 3.84f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (250 - disparity - mGpsPistance2 * 5f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance2 > 20 && mGpsPistance2 <= 78) {
                        mPeopleOne.setY(150 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (205 - transverse1 + (mGpsPistance2 - 20) * 9.71f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (768 - transverse1 + (mGpsPistance2 - 78) * 2.91f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (150 - disparity + (mGpsPistance2 - 78) * 7.95f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance2 <= 20) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (128 - transverse1 + mGpsPistance2 * 3.84f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (250 - disparity - mGpsPistance2 * 5f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance2 > 20 && mGpsPistance2 <= 78) {
                        mPeopleOne.setY(150 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (205 - transverse1 + (mGpsPistance2 - 20) * 9.71f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (768 - transverse1 + (mGpsPistance2 - 78) * 2.91f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (150 - disparity + (mGpsPistance2 - 78) * 7.95f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "8":
                oneTrack = false;
                twoTrack = false;
                threeTrack = false;
                fourTrack = false;
                sixTrack = false;
                fiveTrack = false;
                sevenTrack = false;
                if (eightTrack == false) {
                    mPeopleOne.setX(230 - transverse1);
                    mPeopleOne.setY(150 - disparity);
                    eightTrack = true;
                    if (mGpsPistance2 <= 21) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (230 - transverse1 + mGpsPistance2 * 3.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (150 - disparity - mGpsPistance2 * 4.76f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance2 > 21 && mGpsPistance2 <= 76) {
                        mPeopleOne.setY(50 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (307 - transverse1 + (mGpsPistance2 - 21) * 6.05f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (640 - transverse1 + (mGpsPistance2 - 76) * 5.33f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (50 - disparity + (mGpsPistance2 - 76) * 4.17f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance2 <= 21) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (230 - transverse1 + mGpsPistance2 * 3.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (150 - disparity - mGpsPistance2 * 4.76f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance2 > 21 && mGpsPistance2 <= 76) {
                        mPeopleOne.setY(50 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (307 - transverse1 + (mGpsPistance2 - 21) * 6.05f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (640 - transverse1 + (mGpsPistance2 - 76) * 5.33f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (50 - disparity + (mGpsPistance2 - 76) * 4.17f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "9":
                ten = false;
                eleven = false;
                twelve = false;
                thirteen = false;
                fourteen = false;
                if (nine == false) {
                    nine = true;
                    mPeopleOne.setX(1000 - transverse1);
                    mPeopleOne.setY(400 - disparity);
                    if (mGpsPistance2 >= 81) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (1000 - transverse1 - (100 - mGpsPistance2) * 10.53f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else if (mGpsPistance2 >= 43 && mGpsPistance2 < 81) {
                        /*mPeopleOne.setX(800 - transverse1);
                        mPeopleOne.setY(400 - disparity);*/
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (800 - transverse1 - (81 - mGpsPistance2) * 2.7f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (400 - disparity + (81 - mGpsPistance2) * 2.7f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeopleOne.setY(700 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (700 - transverse1 - (43 - mGpsPistance2) * 4.76f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                } else {
                    if (mGpsPistance2 >= 81) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (1000 - transverse1 - (100 - mGpsPistance2) * 10.53f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else if (mGpsPistance2 >= 43 && mGpsPistance2 < 81) {
                        /*mPeopleOne.setX(800 - transverse1);
                        mPeopleOne.setY(400 - disparity);*/
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (800 - transverse1 - (81 - mGpsPistance2) * 2.7f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (400 - disparity + (81 - mGpsPistance2) * 2.7f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance2 >= 0 && mGpsPistance2 < 43) {
                        mPeopleOne.setY(700 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (700 - transverse1 - (43 - mGpsPistance2) * 4.76f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                }
                break;
            case "10":
                nine = false;
                eleven = false;
                twelve = false;
                thirteen = false;
                fourteen = false;
                if (ten == false) {
                    ten = true;
                    mPeopleOne.setX(700 - transverse1);
                    mPeopleOne.setY(500 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (700 - transverse1 + mGpsPistance2 * 3f));
                    animator.setDuration(mTime);
                    animator.start();
                } else {
                    mPeopleOne.setY(500 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (700 - transverse1 + mGpsPistance2 * 3f));
                    animator.setDuration(mTime);
                    animator.start();
                }
                break;
            case "11":
                nine = false;
                ten = false;
                twelve = false;
                thirteen = false;
                fourteen = false;
                if (eleven == false) {
                    eleven = true;
                    mPeopleOne.setX(600 - transverse1);
                    mPeopleOne.setY(400 - disparity);
                    if (mGpsPistance2 >= 77) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (600 - transverse1 - (100 - mGpsPistance2) * 2.17f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (400 - disparity - (100 - mGpsPistance2) * 4.35f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeopleOne.setY(300 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (550 - transverse1 - (76 - mGpsPistance2) * 6.58f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                } else {
                    if (mGpsPistance2 >= 77) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (600 - transverse1 - (100 - mGpsPistance2) * 2.17f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (400 - disparity - (100 - mGpsPistance2) * 4.35f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeopleOne.setY(300 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (550 - transverse1 - (76 - mGpsPistance2) * 6.58f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                }
                break;
            case "12":
                nine = false;
                ten = false;
                eleven = false;
                thirteen = false;
                fourteen = false;
                if (twelve == false) {
                    twelve = true;
                    mPeopleOne.setX(500 - transverse1);
                    mPeopleOne.setY(300 - disparity);
                    if (mGpsPistance2 >= 76) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (500 - transverse1 - (100 - mGpsPistance2) * 2.08f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (300 - disparity - (100 - mGpsPistance2) * 4.17f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance2 >= 26 && mGpsPistance2 < 76) {
                        mPeopleOne.setY(200 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (450 - transverse1 - (75 - mGpsPistance2) * 6.12f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (150 - transverse1 + (25 - mGpsPistance2) * 2f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (200 - disparity - (25 - mGpsPistance2) * 4f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance2 >= 76) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (500 - transverse1 - (100 - mGpsPistance2) * 2.08f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (300 - disparity - (100 - mGpsPistance2) * 4.17f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance2 >= 26 && mGpsPistance2 < 76) {
                        mPeopleOne.setY(200 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (450 - transverse1 - (75 - mGpsPistance2) * 6.12f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (150 - transverse1 + (25 - mGpsPistance2) * 2f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (200 - disparity - (25 - mGpsPistance2) * 4f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "13":
                nine = false;
                ten = false;
                eleven = false;
                twelve = false;
                fourteen = false;
                if (thirteen == false) {
                    thirteen = true;
                    mPeopleOne.setX(800 - transverse1);
                    mPeopleOne.setY(400 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (800 - transverse - (100 - mGpsPistance2) * 7.5f));
                    animator.setDuration(mTime);
                    animator.start();
                } else {
                    mPeopleOne.setY(400 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (800 - transverse - (100 - mGpsPistance2) * 7.5f));
                    animator.setDuration(mTime);
                    animator.start();
                }
                break;
            case "14":
                nine = false;
                ten = false;
                eleven = false;
                twelve = false;
                thirteen = false;
                if (fourteen == false) {
                    fourteen = true;
                    mPeopleOne.setX(450 - transverse1);
                    mPeopleOne.setY(400 - disparity);
                    if (mGpsPistance2 >= 47) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (450 - transverse1 - (100 - mGpsPistance2) * 1.89f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (400 - disparity + (100 - mGpsPistance2) * 1.89f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeopleOne.setY(500 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (350 - transverse1 - (46 - mGpsPistance2) * 6.52f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                } else {
                    if (mGpsPistance2 >= 47) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (450 - transverse1 - (100 - mGpsPistance2) * 1.89f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (400 - disparity + (100 - mGpsPistance2) * 1.89f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeopleOne.setY(500 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (350 - transverse1 - (46 - mGpsPistance2) * 6.52f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                }
                break;
            case "15":
                sixteen = false;
                seventeen = false;
                eighteen = false;
                nineteen = false;
                if (fifteen == false) {
                    fifteen = true;
                    mPeopleOne.setX(50 - transverse1);
                    mPeopleOne.setY(300 - disparity);
                    if (mGpsPistance2 <= 42) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (50 - transverse1 + mGpsPistance2 * 10.71f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (300 - disparity + mGpsPistance2 * 5.95f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeopleOne.setX(500 - transverse1);
                        mPeopleOne.setY(550 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (500 - transverse1 + (mGpsPistance2 - 43) * 5.26f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                } else {
                    if (mGpsPistance2 <= 42) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (50 - transverse1 + mGpsPistance2 * 10.71f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (300 - disparity + mGpsPistance2 * 5.95f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeopleOne.setY(550 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (500 - transverse1 + (mGpsPistance2 - 43) * 5.26f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                }
                break;
            case "16":
                fifteen = false;
                seventeen = false;
                eighteen = false;
                nineteen = false;
                if (sixteen == false) {
                    sixteen = true;
                    mPeopleOne.setX(450 - transverse1);
                    mPeopleOne.setY(350 - disparity);
                    if (mGpsPistance2 <= 30) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (450 - transverse1 + mGpsPistance2 * 3.33f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (350 - disparity + mGpsPistance2 * 3.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeopleOne.setY(450 - disparity);
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (550 - transverse1 + (mGpsPistance2 - 31) * 4.93f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance2 <= 30) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (450 - transverse1 + mGpsPistance2 * 3.33f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (350 - disparity + mGpsPistance2 * 3.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeopleOne.setY(450 - disparity);
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (550 - transverse1 + (mGpsPistance2 - 31) * 4.93f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "17":
                fifteen = false;
                sixteen = false;
                eighteen = false;
                nineteen = false;
                if (seventeen == false) {
                    seventeen = true;
                    mPeopleOne.setX(150 - transverse1);
                    mPeopleOne.setY(150 - disparity);
                    if (mGpsPistance2 <= 52) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (150 - transverse1 + mGpsPistance2 * 4.81f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (150 - disparity + mGpsPistance2 * 3.85f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeopleOne.setX(400 - transverse1);
                        mPeopleOne.setY(350 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (400 - transverse1 + (mGpsPistance2 - 53) * 8.51f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                } else {
                    if (mGpsPistance2 <= 52) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (150 - transverse1 + mGpsPistance2 * 4.81f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (150 - disparity + mGpsPistance2 * 3.85f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeopleOne.setY(350 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (400 - transverse1 + (mGpsPistance2 - 53) * 8.51f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                }
                break;
            case "18":
                fifteen = false;
                sixteen = false;
                seventeen = false;
                eighteen = false;
                nineteen = false;
                if (eighteen == false) {
                    eighteen = true;
                    mPeopleOne.setX(500 - transverse1);
                    mPeopleOne.setY(350 - disparity);
                    if (mGpsPistance2 <= 8) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (500 - transverse1 + mGpsPistance2 * 6.25f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (350 - disparity - mGpsPistance2 * 12.5f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance2 > 8 && mGpsPistance2 <= 79) {
                        mPeopleOne.setY(250 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (550 - transverse1 + (mGpsPistance2 - 8) * 2.82f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (750 - transverse1 + (mGpsPistance2 - 79) * 2.38f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (250 - disparity + (mGpsPistance2 - 79) * 4.76f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance2 <= 8) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (500 - transverse1 + mGpsPistance2 * 6.25f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (350 - disparity - mGpsPistance2 * 12.5f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance2 > 8 && mGpsPistance2 <= 79) {
                        mPeopleOne.setY(250 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (550 - transverse1 + (mGpsPistance2 - 8) * 2.82f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (750 - transverse1 + (mGpsPistance2 - 79) * 2.38f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopleOne, "translationY", (float) (250 - disparity + (mGpsPistance2 - 79) * 4.76f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "19":
                mMap3.setName("visible");
                mMap1.setName("gone");
                mMap2.setName("gone");
                fifteen = false;
                sixteen = false;
                seventeen = false;
                eighteen = false;
                if (nineteen == false) {
                    nineteen = true;
                    mPeopleOne.setX(800 - transverse1);
                    mPeopleOne.setY(350 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (800 - transverse1 + mGpsPistance2 * 2f));
                    animator.setDuration(mTime);
                    animator.start();
                } else {
                    mPeopleOne.setY(350 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeopleOne, "translationX", (float) (800 - transverse1 + mGpsPistance2 * 2f));
                    animator.setDuration(mTime);
                    animator.start();
                }
                break;
        }
    }

    //制动员7
    private void proplrMove2() {
        switch (mRatioOfGpsTrack3) {
            case "1":
                twoTrack = false;
                threeTrack = false;
                fourTrack = false;
                fiveTrack = false;
                sixTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (oneTrack == false) {
                    mPeopletwo.setX(320 - transverse1);
                    mPeopletwo.setY(450 - disparity);
                    oneTrack = true;
                    if (mGpsPistance3 <= 5) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (320 - transverse1 + mGpsPistance3 * 12.8f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (450 - disparity + mGpsPistance3 * 10f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance3 > 5 && mGpsPistance3 <= 94) {
                        mPeopletwo.setY(500 - disparity);
                        //setStraightLine(340, mGpsPistance3 - 5, 2.88f);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (384 - transverse1 + (mGpsPistance3 - 5) * 2.88f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (640 - transverse1 + (mGpsPistance3 - 94) * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (500 - disparity + (mGpsPistance3 - 94) * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance3 <= 5) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (320 - transverse1 + mGpsPistance3 * 12.8f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (450 - disparity + mGpsPistance3 * 10f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance3 > 5 && mGpsPistance3 <= 94) {
                        mPeopletwo.setY(500 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (384 - transverse1 + (mGpsPistance3 - 5) * 2.88f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (640 - transverse1 + (mGpsPistance3 - 94) * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (500 - disparity + (mGpsPistance3 - 94) * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "2":
                oneTrack = false;
                threeTrack = false;
                fourTrack = false;
                fiveTrack = false;
                sixTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (twoTrack == false) {
                    mPeopletwo.setX(50 - transverse1);
                    mPeopletwo.setY(450 - disparity);
                    twoTrack = true;
                    if (mGpsPistance3 <= 87) {
                        mPeopletwo.setY(450 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (50 - transverse1 + mGpsPistance3 * 8.25f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (768 - transverse1 + (mGpsPistance3 - 87) * 4.92f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (450 - disparity + (mGpsPistance3 - 87) * 3.84f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance3 <= 87) {
                        mPeopletwo.setY(450 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (50 - transverse1 + mGpsPistance3 * 8.25f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (768 - transverse1 + (mGpsPistance3 - 87) * 4.92f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (450 - disparity + (mGpsPistance3 - 87) * 3.84f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "3":
                oneTrack = false;
                twoTrack = false;
                fourTrack = false;
                fiveTrack = false;
                sixTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (threeTrack == false) {
                    mPeopletwo.setX(128 - transverse1);
                    mPeopletwo.setY(400 - disparity);
                    threeTrack = true;
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (128 - transverse1 + mGpsPistance3 * 8.46f));
                    animator.setDuration(mTime);
                    animator.start();
                } else {
                    mPeopletwo.setY(400 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (128 - transverse1 + mGpsPistance3 * 8.46f));
                    animator.setDuration(mTime);
                    animator.start();
                }
                break;
            case "4":
                oneTrack = false;
                twoTrack = false;
                threeTrack = false;
                fiveTrack = false;
                sixTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (fourTrack == false) {
                    mPeopletwo.setX(256 - transverse1);
                    mPeopletwo.setY(400 - disparity);
                    fourTrack = true;
                    if (mGpsPistance3 <= 6) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (256 - transverse1 + mGpsPistance3 * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (400 - disparity - mGpsPistance3 * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance3 > 6 && mGpsPistance3 <= 94) {
                        mPeopletwo.setY(350 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (320 - transverse1 + (mGpsPistance3 - 6) * 4.36f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (704 - transverse1 + (mGpsPistance3 - 94) * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (350 - disparity + (mGpsPistance3 - 94) * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance3 <= 6) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (256 - transverse1 + mGpsPistance3 * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (400 - disparity - mGpsPistance3 * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance3 > 6 && mGpsPistance3 <= 94) {
                        mPeopletwo.setY(350 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (320 - transverse1 + (mGpsPistance3 - 6) * 4.36f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (704 - transverse1 + (mGpsPistance3 - 94) * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (350 - disparity + (mGpsPistance3 - 94) * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "5":
                oneTrack = false;
                twoTrack = false;
                threeTrack = false;
                fourTrack = false;
                sixTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (fiveTrack == false) {
                    mPeopletwo.setX(320 - transverse1);
                    mPeopletwo.setY(350 - disparity);
                    fiveTrack = true;
                    if (mGpsPistance3 <= 6) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (320 - transverse1 + mGpsPistance3 * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (350 - disparity - mGpsPistance3 * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance3 > 6 && mGpsPistance3 <= 93) {
                        mPeopletwo.setY(300 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (384 - transverse1 + (mGpsPistance3 - 6) * 2.94f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (640 - transverse1 + (mGpsPistance3 - 93) * 9.14f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (300 - disparity + (mGpsPistance3 - 93) * 7.14f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance3 <= 6) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (320 - transverse1 + mGpsPistance3 * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (350 - disparity - mGpsPistance3 * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance3 > 6 && mGpsPistance3 <= 93) {
                        mPeopletwo.setY(300 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (384 - transverse1 + (mGpsPistance3 - 6) * 2.94f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (640 - transverse1 + (mGpsPistance3 - 93) * 9.14f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (300 - disparity + (mGpsPistance3 - 93) * 7.14f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "6":
                oneTrack = false;
                twoTrack = false;
                threeTrack = false;
                fourTrack = false;
                fiveTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (sixTrack == false) {
                    mPeopletwo.setX(50 - transverse1);
                    mPeopletwo.setY(250 - disparity);
                    sixTrack = true;
                    if (mGpsPistance3 <= 83) {
                        mPeopletwo.setY(250 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (50 - transverse1 + mGpsPistance3 * 8.65f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (768 - transverse1 + (mGpsPistance3 - 83) * 7.53f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (250 - disparity + (mGpsPistance3 - 83) * 8.82f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance3 <= 83) {
                        mPeopletwo.setY(250 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (50 - transverse1 + mGpsPistance3 * 8.65f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (768 - transverse1 + (mGpsPistance3 - 83) * 7.53f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (250 - disparity + (mGpsPistance3 - 83) * 8.82f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "7":
                oneTrack = false;
                twoTrack = false;
                threeTrack = false;
                fourTrack = false;
                fiveTrack = false;
                sixTrack = false;
                eightTrack = false;
                if (sevenTrack == false) {
                    mPeopletwo.setX(128 - transverse);
                    mPeopletwo.setY(250 - disparity);
                    sevenTrack = true;
                    if (mGpsPistance3 <= 20) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (128 - transverse1 + mGpsPistance3 * 3.84f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (250 - disparity - mGpsPistance3 * 5f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance3 > 20 && mGpsPistance3 <= 78) {
                        mPeopletwo.setY(150 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (205 - transverse1 + (mGpsPistance3 - 20) * 9.71f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (768 - transverse1 + (mGpsPistance3 - 78) * 2.91f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (150 - disparity + (mGpsPistance3 - 78) * 7.95f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance3 <= 20) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (128 - transverse1 + mGpsPistance3 * 3.84f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (250 - disparity - mGpsPistance3 * 5f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance3 > 20 && mGpsPistance3 <= 78) {
                        mPeopletwo.setY(150 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (205 - transverse1 + (mGpsPistance3 - 20) * 9.71f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (768 - transverse1 + (mGpsPistance3 - 78) * 2.91f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (150 - disparity + (mGpsPistance3 - 78) * 7.95f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "8":
                oneTrack = false;
                twoTrack = false;
                threeTrack = false;
                fourTrack = false;
                sixTrack = false;
                fiveTrack = false;
                sevenTrack = false;
                if (eightTrack == false) {
                    mPeopletwo.setX(230 - transverse1);
                    mPeopletwo.setY(150 - disparity);
                    eightTrack = true;
                    if (mGpsPistance3 <= 21) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (230 - transverse1 + mGpsPistance3 * 3.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (150 - disparity - mGpsPistance3 * 4.76f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance3 > 21 && mGpsPistance3 <= 76) {
                        mPeopletwo.setY(50 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (307 - transverse1 + (mGpsPistance3 - 21) * 6.05f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (640 - transverse1 + (mGpsPistance3 - 76) * 5.33f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (50 - disparity + (mGpsPistance3 - 76) * 4.17f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance3 <= 21) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (230 - transverse1 + mGpsPistance3 * 3.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (150 - disparity - mGpsPistance3 * 4.76f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance3 > 21 && mGpsPistance3 <= 76) {
                        mPeopletwo.setY(50 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (307 - transverse1 + (mGpsPistance3 - 21) * 6.05f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (640 - transverse1 + (mGpsPistance3 - 76) * 5.33f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (50 - disparity + (mGpsPistance3 - 76) * 4.17f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "9":
                ten = false;
                eleven = false;
                twelve = false;
                thirteen = false;
                fourteen = false;
                if (nine == false) {
                    nine = true;
                    mPeopletwo.setX(1000 - transverse1);
                    mPeopletwo.setY(400 - disparity);
                    if (mGpsPistance3 >= 81) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (1000 - transverse1 - (100 - mGpsPistance3) * 10.53f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else if (mGpsPistance3 >= 43 && mGpsPistance3 < 81) {
                        /*mPeopletwo.setX(800 - transverse1);
                        mPeopletwo.setY(400 - disparity);*/
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (800 - transverse1 - (81 - mGpsPistance3) * 2.7f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (400 - disparity + (81 - mGpsPistance3) * 2.7f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeopletwo.setY(700 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (700 - transverse1 - (43 - mGpsPistance3) * 4.76f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                } else {
                    if (mGpsPistance3 >= 81) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (1000 - transverse1 - (100 - mGpsPistance3) * 10.53f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else if (mGpsPistance3 >= 43 && mGpsPistance3 < 81) {
                        /*mPeopletwo.setX(800 - transverse1);
                        mPeopletwo.setY(400 - disparity);*/
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (800 - transverse1 - (81 - mGpsPistance3) * 2.7f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (400 - disparity + (81 - mGpsPistance3) * 2.7f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance3 >= 0 && mGpsPistance3 < 43) {
                        mPeopletwo.setY(700 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (700 - transverse1 - (43 - mGpsPistance3) * 4.76f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                }
                break;
            case "10":
                nine = false;
                eleven = false;
                twelve = false;
                thirteen = false;
                fourteen = false;
                if (ten == false) {
                    ten = true;
                    mPeopletwo.setX(700 - transverse1);
                    mPeopletwo.setY(500 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (700 - transverse1 + mGpsPistance3 * 3f));
                    animator.setDuration(mTime);
                    animator.start();
                } else {
                    mPeopletwo.setY(500 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (700 - transverse1 + mGpsPistance3 * 3f));
                    animator.setDuration(mTime);
                    animator.start();
                }
                break;
            case "11":
                nine = false;
                ten = false;
                twelve = false;
                thirteen = false;
                fourteen = false;
                if (eleven == false) {
                    eleven = true;
                    mPeopletwo.setX(600 - transverse1);
                    mPeopletwo.setY(400 - disparity);
                    if (mGpsPistance3 >= 77) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (600 - transverse1 - (100 - mGpsPistance3) * 2.17f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (400 - disparity - (100 - mGpsPistance3) * 4.35f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeopletwo.setY(300 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (550 - transverse1 - (76 - mGpsPistance3) * 6.58f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                } else {
                    if (mGpsPistance3 >= 77) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (600 - transverse1 - (100 - mGpsPistance3) * 2.17f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (400 - disparity - (100 - mGpsPistance3) * 4.35f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeopletwo.setY(300 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (550 - transverse1 - (76 - mGpsPistance3) * 6.58f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                }
                break;
            case "12":
                nine = false;
                ten = false;
                eleven = false;
                thirteen = false;
                fourteen = false;
                if (twelve == false) {
                    twelve = true;
                    mPeopletwo.setX(500 - transverse1);
                    mPeopletwo.setY(300 - disparity);
                    if (mGpsPistance3 >= 76) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (500 - transverse1 - (100 - mGpsPistance3) * 2.08f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (300 - disparity - (100 - mGpsPistance3) * 4.17f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance3 >= 26 && mGpsPistance3 < 76) {
                        mPeopletwo.setY(200 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (450 - transverse1 - (75 - mGpsPistance3) * 6.12f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (150 - transverse1 + (25 - mGpsPistance3) * 2f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (200 - disparity - (25 - mGpsPistance3) * 4f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance3 >= 76) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (500 - transverse1 - (100 - mGpsPistance3) * 2.08f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (300 - disparity - (100 - mGpsPistance3) * 4.17f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance3 >= 26 && mGpsPistance3 < 76) {
                        mPeopletwo.setY(200 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (450 - transverse1 - (75 - mGpsPistance3) * 6.12f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (150 - transverse1 + (25 - mGpsPistance3) * 2f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (200 - disparity - (25 - mGpsPistance3) * 4f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "13":
                nine = false;
                ten = false;
                eleven = false;
                twelve = false;
                fourteen = false;
                if (thirteen == false) {
                    thirteen = true;
                    mPeopletwo.setX(800 - transverse1);
                    mPeopletwo.setY(400 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (800 - transverse - (100 - mGpsPistance3) * 7.5f));
                    animator.setDuration(mTime);
                    animator.start();
                } else {
                    mPeopletwo.setY(400 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (800 - transverse - (100 - mGpsPistance3) * 7.5f));
                    animator.setDuration(mTime);
                    animator.start();
                }
                break;
            case "14":
                nine = false;
                ten = false;
                eleven = false;
                twelve = false;
                thirteen = false;
                if (fourteen == false) {
                    fourteen = true;
                    mPeopletwo.setX(450 - transverse1);
                    mPeopletwo.setY(400 - disparity);
                    if (mGpsPistance3 >= 47) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (450 - transverse1 - (100 - mGpsPistance3) * 1.89f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (400 - disparity + (100 - mGpsPistance3) * 1.89f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeopletwo.setY(500 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (350 - transverse1 - (46 - mGpsPistance3) * 6.52f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                } else {
                    if (mGpsPistance3 >= 47) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (450 - transverse1 - (100 - mGpsPistance3) * 1.89f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (400 - disparity + (100 - mGpsPistance3) * 1.89f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeopletwo.setY(500 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (350 - transverse1 - (46 - mGpsPistance3) * 6.52f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                }
                break;
            case "15":
                sixteen = false;
                seventeen = false;
                eighteen = false;
                nineteen = false;
                if (fifteen == false) {
                    fifteen = true;
                    mPeopletwo.setX(50 - transverse1);
                    mPeopletwo.setY(300 - disparity);
                    if (mGpsPistance3 <= 42) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (50 - transverse1 + mGpsPistance3 * 10.71f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (300 - disparity + mGpsPistance3 * 5.95f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeopletwo.setX(500 - transverse1);
                        mPeopletwo.setY(550 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (500 - transverse1 + (mGpsPistance3 - 43) * 5.26f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                } else {
                    if (mGpsPistance3 <= 42) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (50 - transverse1 + mGpsPistance3 * 10.71f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (300 - disparity + mGpsPistance3 * 5.95f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeopletwo.setY(550 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (500 - transverse1 + (mGpsPistance3 - 43) * 5.26f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                }
                break;
            case "16":
                fifteen = false;
                seventeen = false;
                eighteen = false;
                nineteen = false;
                if (sixteen == false) {
                    sixteen = true;
                    mPeopletwo.setX(450 - transverse1);
                    mPeopletwo.setY(350 - disparity);
                    if (mGpsPistance3 <= 30) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (450 - transverse1 + mGpsPistance3 * 3.33f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (350 - disparity + mGpsPistance3 * 3.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeopletwo.setY(450 - disparity);
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (550 - transverse1 + (mGpsPistance3 - 31) * 4.93f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance3 <= 30) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (450 - transverse1 + mGpsPistance3 * 3.33f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (350 - disparity + mGpsPistance3 * 3.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeopletwo.setY(450 - disparity);
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (550 - transverse1 + (mGpsPistance3 - 31) * 4.93f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "17":
                fifteen = false;
                sixteen = false;
                eighteen = false;
                nineteen = false;
                if (seventeen == false) {
                    seventeen = true;
                    mPeopletwo.setX(150 - transverse1);
                    mPeopletwo.setY(150 - disparity);
                    if (mGpsPistance3 <= 52) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (150 - transverse1 + mGpsPistance3 * 4.81f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (150 - disparity + mGpsPistance3 * 3.85f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeopletwo.setX(400 - transverse1);
                        mPeopletwo.setY(350 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (400 - transverse1 + (mGpsPistance3 - 53) * 8.51f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                } else {
                    if (mGpsPistance3 <= 52) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (150 - transverse1 + mGpsPistance3 * 4.81f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (150 - disparity + mGpsPistance3 * 3.85f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeopletwo.setY(350 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (400 - transverse1 + (mGpsPistance3 - 53) * 8.51f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                }
                break;
            case "18":
                fifteen = false;
                sixteen = false;
                seventeen = false;
                eighteen = false;
                nineteen = false;
                if (eighteen == false) {
                    eighteen = true;
                    mPeopletwo.setX(500 - transverse1);
                    mPeopletwo.setY(350 - disparity);
                    if (mGpsPistance3 <= 8) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (500 - transverse1 + mGpsPistance3 * 6.25f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (350 - disparity - mGpsPistance3 * 12.5f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance3 > 8 && mGpsPistance3 <= 79) {
                        mPeopletwo.setY(250 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (550 - transverse1 + (mGpsPistance3 - 8) * 2.82f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (750 - transverse1 + (mGpsPistance3 - 79) * 2.38f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (250 - disparity + (mGpsPistance3 - 79) * 4.76f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance3 <= 8) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (500 - transverse1 + mGpsPistance3 * 6.25f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (350 - disparity - mGpsPistance3 * 12.5f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance3 > 8 && mGpsPistance3 <= 79) {
                        mPeopletwo.setY(250 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (550 - transverse1 + (mGpsPistance3 - 8) * 2.82f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (750 - transverse1 + (mGpsPistance3 - 79) * 2.38f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeopletwo, "translationY", (float) (250 - disparity + (mGpsPistance3 - 79) * 4.76f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "19":
                mMap3.setName("visible");
                mMap1.setName("gone");
                mMap2.setName("gone");
                fifteen = false;
                sixteen = false;
                seventeen = false;
                eighteen = false;
                if (nineteen == false) {
                    nineteen = true;
                    mPeopletwo.setX(800 - transverse1);
                    mPeopletwo.setY(350 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (800 - transverse1 + mGpsPistance3 * 2f));
                    animator.setDuration(mTime);
                    animator.start();
                } else {
                    mPeopletwo.setY(350 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeopletwo, "translationX", (float) (800 - transverse1 + mGpsPistance3 * 2f));
                    animator.setDuration(mTime);
                    animator.start();
                }
                break;
        }
    }

    //制动员8
    private void proplrMove3() {
        switch (mRatioOfGpsTrack4) {
            case "1":
                twoTrack = false;
                threeTrack = false;
                fourTrack = false;
                fiveTrack = false;
                sixTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (oneTrack == false) {
                    mPeoplethree.setX(320 - transverse1);
                    mPeoplethree.setY(450 - disparity);
                    oneTrack = true;
                    if (mGpsPistance4 <= 5) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (320 - transverse1 + mGpsPistance4 * 12.8f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (450 - disparity + mGpsPistance4 * 10f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance4 > 5 && mGpsPistance4 <= 94) {
                        mPeoplethree.setY(500 - disparity);
                        //setStraightLine(340, mGpsPistance4 - 5, 2.88f);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (384 - transverse1 + (mGpsPistance4 - 5) * 2.88f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (640 - transverse1 + (mGpsPistance4 - 94) * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (500 - disparity + (mGpsPistance4 - 94) * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance4 <= 5) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (320 - transverse1 + mGpsPistance4 * 12.8f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (450 - disparity + mGpsPistance4 * 10f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance4 > 5 && mGpsPistance4 <= 94) {
                        mPeoplethree.setY(500 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (384 - transverse1 + (mGpsPistance4 - 5) * 2.88f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (640 - transverse1 + (mGpsPistance4 - 94) * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (500 - disparity + (mGpsPistance4 - 94) * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "2":
                oneTrack = false;
                threeTrack = false;
                fourTrack = false;
                fiveTrack = false;
                sixTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (twoTrack == false) {
                    mPeoplethree.setX(50 - transverse1);
                    mPeoplethree.setY(450 - disparity);
                    twoTrack = true;
                    if (mGpsPistance4 <= 87) {
                        mPeoplethree.setY(450 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (50 - transverse1 + mGpsPistance4 * 8.25f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (768 - transverse1 + (mGpsPistance4 - 87) * 4.92f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (450 - disparity + (mGpsPistance4 - 87) * 3.84f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance4 <= 87) {
                        mPeoplethree.setY(450 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (50 - transverse1 + mGpsPistance4 * 8.25f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (768 - transverse1 + (mGpsPistance4 - 87) * 4.92f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (450 - disparity + (mGpsPistance4 - 87) * 3.84f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "3":
                oneTrack = false;
                twoTrack = false;
                fourTrack = false;
                fiveTrack = false;
                sixTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (threeTrack == false) {
                    mPeoplethree.setX(128 - transverse1);
                    mPeoplethree.setY(400 - disparity);
                    threeTrack = true;
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (128 - transverse1 + mGpsPistance4 * 8.46f));
                    animator.setDuration(mTime);
                    animator.start();
                } else {
                    mPeoplethree.setY(400 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (128 - transverse1 + mGpsPistance4 * 8.46f));
                    animator.setDuration(mTime);
                    animator.start();
                }
                break;
            case "4":
                oneTrack = false;
                twoTrack = false;
                threeTrack = false;
                fiveTrack = false;
                sixTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (fourTrack == false) {
                    mPeoplethree.setX(256 - transverse1);
                    mPeoplethree.setY(400 - disparity);
                    fourTrack = true;
                    if (mGpsPistance4 <= 6) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (256 - transverse1 + mGpsPistance4 * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (400 - disparity - mGpsPistance4 * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance4 > 6 && mGpsPistance4 <= 94) {
                        mPeoplethree.setY(350 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (320 - transverse1 + (mGpsPistance4 - 6) * 4.36f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (704 - transverse1 + (mGpsPistance4 - 94) * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (350 - disparity + (mGpsPistance4 - 94) * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance4 <= 6) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (256 - transverse1 + mGpsPistance4 * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (400 - disparity - mGpsPistance4 * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance4 > 6 && mGpsPistance4 <= 94) {
                        mPeoplethree.setY(350 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (320 - transverse1 + (mGpsPistance4 - 6) * 4.36f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (704 - transverse1 + (mGpsPistance4 - 94) * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (350 - disparity + (mGpsPistance4 - 94) * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "5":
                oneTrack = false;
                twoTrack = false;
                threeTrack = false;
                fourTrack = false;
                sixTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (fiveTrack == false) {
                    mPeoplethree.setX(320 - transverse1);
                    mPeoplethree.setY(350 - disparity);
                    fiveTrack = true;
                    if (mGpsPistance4 <= 6) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (320 - transverse1 + mGpsPistance4 * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (350 - disparity - mGpsPistance4 * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance4 > 6 && mGpsPistance4 <= 93) {
                        mPeoplethree.setY(300 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (384 - transverse1 + (mGpsPistance4 - 6) * 2.94f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (640 - transverse1 + (mGpsPistance4 - 93) * 9.14f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (300 - disparity + (mGpsPistance4 - 93) * 7.14f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance4 <= 6) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (320 - transverse1 + mGpsPistance4 * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (350 - disparity - mGpsPistance4 * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance4 > 6 && mGpsPistance4 <= 93) {
                        mPeoplethree.setY(300 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (384 - transverse1 + (mGpsPistance4 - 6) * 2.94f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (640 - transverse1 + (mGpsPistance4 - 93) * 9.14f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (300 - disparity + (mGpsPistance4 - 93) * 7.14f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "6":
                oneTrack = false;
                twoTrack = false;
                threeTrack = false;
                fourTrack = false;
                fiveTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (sixTrack == false) {
                    mPeoplethree.setX(50 - transverse1);
                    mPeoplethree.setY(250 - disparity);
                    sixTrack = true;
                    if (mGpsPistance4 <= 83) {
                        mPeoplethree.setY(250 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (50 - transverse1 + mGpsPistance4 * 8.65f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (768 - transverse1 + (mGpsPistance4 - 83) * 7.53f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (250 - disparity + (mGpsPistance4 - 83) * 8.82f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance4 <= 83) {
                        mPeoplethree.setY(250 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (50 - transverse1 + mGpsPistance4 * 8.65f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (768 - transverse1 + (mGpsPistance4 - 83) * 7.53f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (250 - disparity + (mGpsPistance4 - 83) * 8.82f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "7":
                oneTrack = false;
                twoTrack = false;
                threeTrack = false;
                fourTrack = false;
                fiveTrack = false;
                sixTrack = false;
                eightTrack = false;
                if (sevenTrack == false) {
                    mPeoplethree.setX(128 - transverse);
                    mPeoplethree.setY(250 - disparity);
                    sevenTrack = true;
                    if (mGpsPistance4 <= 20) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (128 - transverse1 + mGpsPistance4 * 3.84f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (250 - disparity - mGpsPistance4 * 5f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance4 > 20 && mGpsPistance4 <= 78) {
                        mPeoplethree.setY(150 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (205 - transverse1 + (mGpsPistance4 - 20) * 9.71f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (768 - transverse1 + (mGpsPistance4 - 78) * 2.91f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (150 - disparity + (mGpsPistance4 - 78) * 7.95f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance4 <= 20) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (128 - transverse1 + mGpsPistance4 * 3.84f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (250 - disparity - mGpsPistance4 * 5f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance4 > 20 && mGpsPistance4 <= 78) {
                        mPeoplethree.setY(150 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (205 - transverse1 + (mGpsPistance4 - 20) * 9.71f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (768 - transverse1 + (mGpsPistance4 - 78) * 2.91f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (150 - disparity + (mGpsPistance4 - 78) * 7.95f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "8":
                oneTrack = false;
                twoTrack = false;
                threeTrack = false;
                fourTrack = false;
                sixTrack = false;
                fiveTrack = false;
                sevenTrack = false;
                if (eightTrack == false) {
                    mPeoplethree.setX(230 - transverse1);
                    mPeoplethree.setY(150 - disparity);
                    eightTrack = true;
                    if (mGpsPistance4 <= 21) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (230 - transverse1 + mGpsPistance4 * 3.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (150 - disparity - mGpsPistance4 * 4.76f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance4 > 21 && mGpsPistance4 <= 76) {
                        mPeoplethree.setY(50 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (307 - transverse1 + (mGpsPistance4 - 21) * 6.05f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (640 - transverse1 + (mGpsPistance4 - 76) * 5.33f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (50 - disparity + (mGpsPistance4 - 76) * 4.17f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance4 <= 21) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (230 - transverse1 + mGpsPistance4 * 3.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (150 - disparity - mGpsPistance4 * 4.76f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance4 > 21 && mGpsPistance4 <= 76) {
                        mPeoplethree.setY(50 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (307 - transverse1 + (mGpsPistance4 - 21) * 6.05f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (640 - transverse1 + (mGpsPistance4 - 76) * 5.33f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (50 - disparity + (mGpsPistance4 - 76) * 4.17f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "9":
                ten = false;
                eleven = false;
                twelve = false;
                thirteen = false;
                fourteen = false;
                if (nine == false) {
                    nine = true;
                    mPeoplethree.setX(1000 - transverse1);
                    mPeoplethree.setY(400 - disparity);
                    if (mGpsPistance4 >= 81) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (1000 - transverse1 - (100 - mGpsPistance4) * 10.53f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else if (mGpsPistance4 >= 43 && mGpsPistance4 < 81) {
                        /*mPeoplethree.setX(800 - transverse1);
                        mPeoplethree.setY(400 - disparity);*/
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (800 - transverse1 - (81 - mGpsPistance4) * 2.7f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (400 - disparity + (81 - mGpsPistance4) * 2.7f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeoplethree.setY(700 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (700 - transverse1 - (43 - mGpsPistance4) * 4.76f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                } else {
                    if (mGpsPistance4 >= 81) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (1000 - transverse1 - (100 - mGpsPistance4) * 10.53f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else if (mGpsPistance4 >= 43 && mGpsPistance4 < 81) {
                        /*mPeoplethree.setX(800 - transverse1);
                        mPeoplethree.setY(400 - disparity);*/
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (800 - transverse1 - (81 - mGpsPistance4) * 2.7f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (400 - disparity + (81 - mGpsPistance4) * 2.7f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance4 >= 0 && mGpsPistance4 < 43) {
                        mPeoplethree.setY(700 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (700 - transverse1 - (43 - mGpsPistance4) * 4.76f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                }
                break;
            case "10":
                nine = false;
                eleven = false;
                twelve = false;
                thirteen = false;
                fourteen = false;
                if (ten == false) {
                    ten = true;
                    mPeoplethree.setX(700 - transverse1);
                    mPeoplethree.setY(500 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (700 - transverse1 + mGpsPistance4 * 3f));
                    animator.setDuration(mTime);
                    animator.start();
                } else {
                    mPeoplethree.setY(500 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (700 - transverse1 + mGpsPistance4 * 3f));
                    animator.setDuration(mTime);
                    animator.start();
                }
                break;
            case "11":
                nine = false;
                ten = false;
                twelve = false;
                thirteen = false;
                fourteen = false;
                if (eleven == false) {
                    eleven = true;
                    mPeoplethree.setX(600 - transverse1);
                    mPeoplethree.setY(400 - disparity);
                    if (mGpsPistance4 >= 77) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (600 - transverse1 - (100 - mGpsPistance4) * 2.17f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (400 - disparity - (100 - mGpsPistance4) * 4.35f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeoplethree.setY(300 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (550 - transverse1 - (76 - mGpsPistance4) * 6.58f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                } else {
                    if (mGpsPistance4 >= 77) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (600 - transverse1 - (100 - mGpsPistance4) * 2.17f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (400 - disparity - (100 - mGpsPistance4) * 4.35f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeoplethree.setY(300 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (550 - transverse1 - (76 - mGpsPistance4) * 6.58f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                }
                break;
            case "12":
                nine = false;
                ten = false;
                eleven = false;
                thirteen = false;
                fourteen = false;
                if (twelve == false) {
                    twelve = true;
                    mPeoplethree.setX(500 - transverse1);
                    mPeoplethree.setY(300 - disparity);
                    if (mGpsPistance4 >= 76) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (500 - transverse1 - (100 - mGpsPistance4) * 2.08f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (300 - disparity - (100 - mGpsPistance4) * 4.17f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance4 >= 26 && mGpsPistance4 < 76) {
                        mPeoplethree.setY(200 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (450 - transverse1 - (75 - mGpsPistance4) * 6.12f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (150 - transverse1 + (25 - mGpsPistance4) * 2f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (200 - disparity - (25 - mGpsPistance4) * 4f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance4 >= 76) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (500 - transverse1 - (100 - mGpsPistance4) * 2.08f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (300 - disparity - (100 - mGpsPistance4) * 4.17f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance4 >= 26 && mGpsPistance4 < 76) {
                        mPeoplethree.setY(200 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (450 - transverse1 - (75 - mGpsPistance4) * 6.12f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (150 - transverse1 + (25 - mGpsPistance4) * 2f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (200 - disparity - (25 - mGpsPistance4) * 4f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "13":
                nine = false;
                ten = false;
                eleven = false;
                twelve = false;
                fourteen = false;
                if (thirteen == false) {
                    thirteen = true;
                    mPeoplethree.setX(800 - transverse1);
                    mPeoplethree.setY(400 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (800 - transverse - (100 - mGpsPistance4) * 7.5f));
                    animator.setDuration(mTime);
                    animator.start();
                } else {
                    mPeoplethree.setY(400 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (800 - transverse - (100 - mGpsPistance4) * 7.5f));
                    animator.setDuration(mTime);
                    animator.start();
                }
                break;
            case "14":
                nine = false;
                ten = false;
                eleven = false;
                twelve = false;
                thirteen = false;
                if (fourteen == false) {
                    fourteen = true;
                    mPeoplethree.setX(450 - transverse1);
                    mPeoplethree.setY(400 - disparity);
                    if (mGpsPistance4 >= 47) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (450 - transverse1 - (100 - mGpsPistance4) * 1.89f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (400 - disparity + (100 - mGpsPistance4) * 1.89f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeoplethree.setY(500 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (350 - transverse1 - (46 - mGpsPistance4) * 6.52f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                } else {
                    if (mGpsPistance4 >= 47) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (450 - transverse1 - (100 - mGpsPistance4) * 1.89f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (400 - disparity + (100 - mGpsPistance4) * 1.89f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeoplethree.setY(500 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (350 - transverse1 - (46 - mGpsPistance4) * 6.52f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                }
                break;
            case "15":
                sixteen = false;
                seventeen = false;
                eighteen = false;
                nineteen = false;
                if (fifteen == false) {
                    fifteen = true;
                    mPeoplethree.setX(50 - transverse1);
                    mPeoplethree.setY(300 - disparity);
                    if (mGpsPistance4 <= 42) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (50 - transverse1 + mGpsPistance4 * 10.71f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (300 - disparity + mGpsPistance4 * 5.95f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeoplethree.setX(500 - transverse1);
                        mPeoplethree.setY(550 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (500 - transverse1 + (mGpsPistance4 - 43) * 5.26f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                } else {
                    if (mGpsPistance4 <= 42) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (50 - transverse1 + mGpsPistance4 * 10.71f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (300 - disparity + mGpsPistance4 * 5.95f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeoplethree.setY(550 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (500 - transverse1 + (mGpsPistance4 - 43) * 5.26f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                }
                break;
            case "16":
                fifteen = false;
                seventeen = false;
                eighteen = false;
                nineteen = false;
                if (sixteen == false) {
                    sixteen = true;
                    mPeoplethree.setX(450 - transverse1);
                    mPeoplethree.setY(350 - disparity);
                    if (mGpsPistance4 <= 30) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (450 - transverse1 + mGpsPistance4 * 3.33f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (350 - disparity + mGpsPistance4 * 3.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeoplethree.setY(450 - disparity);
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (550 - transverse1 + (mGpsPistance4 - 31) * 4.93f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance4 <= 30) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (450 - transverse1 + mGpsPistance4 * 3.33f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (350 - disparity + mGpsPistance4 * 3.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeoplethree.setY(450 - disparity);
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (550 - transverse1 + (mGpsPistance4 - 31) * 4.93f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "17":
                fifteen = false;
                sixteen = false;
                eighteen = false;
                nineteen = false;
                if (seventeen == false) {
                    seventeen = true;
                    mPeoplethree.setX(150 - transverse1);
                    mPeoplethree.setY(150 - disparity);
                    if (mGpsPistance4 <= 52) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (150 - transverse1 + mGpsPistance4 * 4.81f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (150 - disparity + mGpsPistance4 * 3.85f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeoplethree.setX(400 - transverse1);
                        mPeoplethree.setY(350 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (400 - transverse1 + (mGpsPistance4 - 53) * 8.51f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                } else {
                    if (mGpsPistance4 <= 52) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (150 - transverse1 + mGpsPistance4 * 4.81f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (150 - disparity + mGpsPistance4 * 3.85f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeoplethree.setY(350 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (400 - transverse1 + (mGpsPistance4 - 53) * 8.51f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                }
                break;
            case "18":
                fifteen = false;
                sixteen = false;
                seventeen = false;
                eighteen = false;
                nineteen = false;
                if (eighteen == false) {
                    eighteen = true;
                    mPeoplethree.setX(500 - transverse1);
                    mPeoplethree.setY(350 - disparity);
                    if (mGpsPistance4 <= 8) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (500 - transverse1 + mGpsPistance4 * 6.25f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (350 - disparity - mGpsPistance4 * 12.5f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance4 > 8 && mGpsPistance4 <= 79) {
                        mPeoplethree.setY(250 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (550 - transverse1 + (mGpsPistance4 - 8) * 2.82f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (750 - transverse1 + (mGpsPistance4 - 79) * 2.38f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (250 - disparity + (mGpsPistance4 - 79) * 4.76f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance4 <= 8) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (500 - transverse1 + mGpsPistance4 * 6.25f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (350 - disparity - mGpsPistance4 * 12.5f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance4 > 8 && mGpsPistance4 <= 79) {
                        mPeoplethree.setY(250 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (550 - transverse1 + (mGpsPistance4 - 8) * 2.82f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (750 - transverse1 + (mGpsPistance4 - 79) * 2.38f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplethree, "translationY", (float) (250 - disparity + (mGpsPistance4 - 79) * 4.76f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "19":
                mMap3.setName("visible");
                mMap1.setName("gone");
                mMap2.setName("gone");
                fifteen = false;
                sixteen = false;
                seventeen = false;
                eighteen = false;
                if (nineteen == false) {
                    nineteen = true;
                    mPeoplethree.setX(800 - transverse1);
                    mPeoplethree.setY(350 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (800 - transverse1 + mGpsPistance4 * 2f));
                    animator.setDuration(mTime);
                    animator.start();
                } else {
                    mPeoplethree.setY(350 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeoplethree, "translationX", (float) (800 - transverse1 + mGpsPistance4 * 2f));
                    animator.setDuration(mTime);
                    animator.start();
                }
                break;
        }
    }

    //制动员9
    private void proplrMove4() {
        switch (mRatioOfGpsTrack5) {
            case "1":
                twoTrack = false;
                threeTrack = false;
                fourTrack = false;
                fiveTrack = false;
                sixTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (oneTrack == false) {
                    mPeoplefour.setX(320 - transverse1);
                    mPeoplefour.setY(450 - disparity);
                    oneTrack = true;
                    if (mGpsPistance5 <= 5) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (320 - transverse1 + mGpsPistance5 * 12.8f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (450 - disparity + mGpsPistance5 * 10f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance5 > 5 && mGpsPistance5 <= 94) {
                        mPeoplefour.setY(500 - disparity);
                        //setStraightLine(340, mGpsPistance5 - 5, 2.88f);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (384 - transverse1 + (mGpsPistance5 - 5) * 2.88f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (640 - transverse1 + (mGpsPistance5 - 94) * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (500 - disparity + (mGpsPistance5 - 94) * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance5 <= 5) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (320 - transverse1 + mGpsPistance5 * 12.8f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (450 - disparity + mGpsPistance5 * 10f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance5 > 5 && mGpsPistance5 <= 94) {
                        mPeoplefour.setY(500 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (384 - transverse1 + (mGpsPistance5 - 5) * 2.88f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (640 - transverse1 + (mGpsPistance5 - 94) * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (500 - disparity + (mGpsPistance5 - 94) * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "2":
                oneTrack = false;
                threeTrack = false;
                fourTrack = false;
                fiveTrack = false;
                sixTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (twoTrack == false) {
                    mPeoplefour.setX(50 - transverse1);
                    mPeoplefour.setY(450 - disparity);
                    twoTrack = true;
                    if (mGpsPistance5 <= 87) {
                        mPeoplefour.setY(450 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (50 - transverse1 + mGpsPistance5 * 8.25f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (768 - transverse1 + (mGpsPistance5 - 87) * 4.92f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (450 - disparity + (mGpsPistance5 - 87) * 3.84f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance5 <= 87) {
                        mPeoplefour.setY(450 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (50 - transverse1 + mGpsPistance5 * 8.25f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (768 - transverse1 + (mGpsPistance5 - 87) * 4.92f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (450 - disparity + (mGpsPistance5 - 87) * 3.84f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "3":
                oneTrack = false;
                twoTrack = false;
                fourTrack = false;
                fiveTrack = false;
                sixTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (threeTrack == false) {
                    mPeoplefour.setX(128 - transverse1);
                    mPeoplefour.setY(400 - disparity);
                    threeTrack = true;
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (128 - transverse1 + mGpsPistance5 * 8.46f));
                    animator.setDuration(mTime);
                    animator.start();
                } else {
                    mPeoplefour.setY(400 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (128 - transverse1 + mGpsPistance5 * 8.46f));
                    animator.setDuration(mTime);
                    animator.start();
                }
                break;
            case "4":
                oneTrack = false;
                twoTrack = false;
                threeTrack = false;
                fiveTrack = false;
                sixTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (fourTrack == false) {
                    mPeoplefour.setX(256 - transverse1);
                    mPeoplefour.setY(400 - disparity);
                    fourTrack = true;
                    if (mGpsPistance5 <= 6) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (256 - transverse1 + mGpsPistance5 * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (400 - disparity - mGpsPistance5 * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance5 > 6 && mGpsPistance5 <= 94) {
                        mPeoplefour.setY(350 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (320 - transverse1 + (mGpsPistance5 - 6) * 4.36f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (704 - transverse1 + (mGpsPistance5 - 94) * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (350 - disparity + (mGpsPistance5 - 94) * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance5 <= 6) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (256 - transverse1 + mGpsPistance5 * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (400 - disparity - mGpsPistance5 * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance5 > 6 && mGpsPistance5 <= 94) {
                        mPeoplefour.setY(350 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (320 - transverse1 + (mGpsPistance5 - 6) * 4.36f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (704 - transverse1 + (mGpsPistance5 - 94) * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (350 - disparity + (mGpsPistance5 - 94) * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "5":
                oneTrack = false;
                twoTrack = false;
                threeTrack = false;
                fourTrack = false;
                sixTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (fiveTrack == false) {
                    mPeoplefour.setX(320 - transverse1);
                    mPeoplefour.setY(350 - disparity);
                    fiveTrack = true;
                    if (mGpsPistance5 <= 6) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (320 - transverse1 + mGpsPistance5 * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (350 - disparity - mGpsPistance5 * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance5 > 6 && mGpsPistance5 <= 93) {
                        mPeoplefour.setY(300 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (384 - transverse1 + (mGpsPistance5 - 6) * 2.94f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (640 - transverse1 + (mGpsPistance5 - 93) * 9.14f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (300 - disparity + (mGpsPistance5 - 93) * 7.14f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance5 <= 6) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (320 - transverse1 + mGpsPistance5 * 10.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (350 - disparity - mGpsPistance5 * 8.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance5 > 6 && mGpsPistance5 <= 93) {
                        mPeoplefour.setY(300 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (384 - transverse1 + (mGpsPistance5 - 6) * 2.94f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (640 - transverse1 + (mGpsPistance5 - 93) * 9.14f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (300 - disparity + (mGpsPistance5 - 93) * 7.14f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "6":
                oneTrack = false;
                twoTrack = false;
                threeTrack = false;
                fourTrack = false;
                fiveTrack = false;
                sevenTrack = false;
                eightTrack = false;
                if (sixTrack == false) {
                    mPeoplefour.setX(50 - transverse1);
                    mPeoplefour.setY(250 - disparity);
                    sixTrack = true;
                    if (mGpsPistance5 <= 83) {
                        mPeoplefour.setY(250 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (50 - transverse1 + mGpsPistance5 * 8.65f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (768 - transverse1 + (mGpsPistance5 - 83) * 7.53f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (250 - disparity + (mGpsPistance5 - 83) * 8.82f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance5 <= 83) {
                        mPeoplefour.setY(250 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (50 - transverse1 + mGpsPistance5 * 8.65f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (768 - transverse1 + (mGpsPistance5 - 83) * 7.53f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (250 - disparity + (mGpsPistance5 - 83) * 8.82f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "7":
                oneTrack = false;
                twoTrack = false;
                threeTrack = false;
                fourTrack = false;
                fiveTrack = false;
                sixTrack = false;
                eightTrack = false;
                if (sevenTrack == false) {
                    mPeoplefour.setX(128 - transverse);
                    mPeoplefour.setY(250 - disparity);
                    sevenTrack = true;
                    if (mGpsPistance5 <= 20) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (128 - transverse1 + mGpsPistance5 * 3.84f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (250 - disparity - mGpsPistance5 * 5f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance5 > 20 && mGpsPistance5 <= 78) {
                        mPeoplefour.setY(150 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (205 - transverse1 + (mGpsPistance5 - 20) * 9.71f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (768 - transverse1 + (mGpsPistance5 - 78) * 2.91f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (150 - disparity + (mGpsPistance5 - 78) * 7.95f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance5 <= 20) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (128 - transverse1 + mGpsPistance5 * 3.84f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (250 - disparity - mGpsPistance5 * 5f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance5 > 20 && mGpsPistance5 <= 78) {
                        mPeoplefour.setY(150 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (205 - transverse1 + (mGpsPistance5 - 20) * 9.71f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (768 - transverse1 + (mGpsPistance5 - 78) * 2.91f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (150 - disparity + (mGpsPistance5 - 78) * 7.95f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "8":
                oneTrack = false;
                twoTrack = false;
                threeTrack = false;
                fourTrack = false;
                sixTrack = false;
                fiveTrack = false;
                sevenTrack = false;
                if (eightTrack == false) {
                    mPeoplefour.setX(230 - transverse1);
                    mPeoplefour.setY(150 - disparity);
                    eightTrack = true;
                    if (mGpsPistance5 <= 21) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (230 - transverse1 + mGpsPistance5 * 3.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (150 - disparity - mGpsPistance5 * 4.76f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance5 > 21 && mGpsPistance5 <= 76) {
                        mPeoplefour.setY(50 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (307 - transverse1 + (mGpsPistance5 - 21) * 6.05f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (640 - transverse1 + (mGpsPistance5 - 76) * 5.33f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (50 - disparity + (mGpsPistance5 - 76) * 4.17f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance5 <= 21) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (230 - transverse1 + mGpsPistance5 * 3.67f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (150 - disparity - mGpsPistance5 * 4.76f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance5 > 21 && mGpsPistance5 <= 76) {
                        mPeoplefour.setY(50 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (307 - transverse1 + (mGpsPistance5 - 21) * 6.05f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (640 - transverse1 + (mGpsPistance5 - 76) * 5.33f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (50 - disparity + (mGpsPistance5 - 76) * 4.17f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "9":
                ten = false;
                eleven = false;
                twelve = false;
                thirteen = false;
                fourteen = false;
                if (nine == false) {
                    nine = true;
                    mPeoplefour.setX(1000 - transverse1);
                    mPeoplefour.setY(400 - disparity);
                    if (mGpsPistance5 >= 81) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (1000 - transverse1 - (100 - mGpsPistance5) * 10.53f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else if (mGpsPistance5 >= 43 && mGpsPistance5 < 81) {
                        /*mPeoplefour.setX(800 - transverse1);
                        mPeoplefour.setY(400 - disparity);*/
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (800 - transverse1 - (81 - mGpsPistance5) * 2.7f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (400 - disparity + (81 - mGpsPistance5) * 2.7f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeoplefour.setY(700 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (700 - transverse1 - (43 - mGpsPistance5) * 4.76f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                } else {
                    if (mGpsPistance5 >= 81) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (1000 - transverse1 - (100 - mGpsPistance5) * 10.53f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else if (mGpsPistance5 >= 43 && mGpsPistance5 < 81) {
                        /*mPeoplefour.setX(800 - transverse1);
                        mPeoplefour.setY(400 - disparity);*/
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (800 - transverse1 - (81 - mGpsPistance5) * 2.7f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (400 - disparity + (81 - mGpsPistance5) * 2.7f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance5 >= 0 && mGpsPistance5 < 43) {
                        mPeoplefour.setY(700 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (700 - transverse1 - (43 - mGpsPistance5) * 4.76f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                }
                break;
            case "10":
                nine = false;
                eleven = false;
                twelve = false;
                thirteen = false;
                fourteen = false;
                if (ten == false) {
                    ten = true;
                    mPeoplefour.setX(700 - transverse1);
                    mPeoplefour.setY(500 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (700 - transverse1 + mGpsPistance5 * 3f));
                    animator.setDuration(mTime);
                    animator.start();
                } else {
                    mPeoplefour.setY(500 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (700 - transverse1 + mGpsPistance5 * 3f));
                    animator.setDuration(mTime);
                    animator.start();
                }
                break;
            case "11":
                nine = false;
                ten = false;
                twelve = false;
                thirteen = false;
                fourteen = false;
                if (eleven == false) {
                    eleven = true;
                    mPeoplefour.setX(600 - transverse1);
                    mPeoplefour.setY(400 - disparity);
                    if (mGpsPistance5 >= 77) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (600 - transverse1 - (100 - mGpsPistance5) * 2.17f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (400 - disparity - (100 - mGpsPistance5) * 4.35f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeoplefour.setY(300 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (550 - transverse1 - (76 - mGpsPistance5) * 6.58f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                } else {
                    if (mGpsPistance5 >= 77) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (600 - transverse1 - (100 - mGpsPistance5) * 2.17f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (400 - disparity - (100 - mGpsPistance5) * 4.35f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeoplefour.setY(300 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (550 - transverse1 - (76 - mGpsPistance5) * 6.58f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                }
                break;
            case "12":
                nine = false;
                ten = false;
                eleven = false;
                thirteen = false;
                fourteen = false;
                if (twelve == false) {
                    twelve = true;
                    mPeoplefour.setX(500 - transverse1);
                    mPeoplefour.setY(300 - disparity);
                    if (mGpsPistance5 >= 76) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (500 - transverse1 - (100 - mGpsPistance5) * 2.08f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (300 - disparity - (100 - mGpsPistance5) * 4.17f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance5 >= 26 && mGpsPistance5 < 76) {
                        mPeoplefour.setY(200 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (450 - transverse1 - (75 - mGpsPistance5) * 6.12f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (150 - transverse1 + (25 - mGpsPistance5) * 2f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (200 - disparity - (25 - mGpsPistance5) * 4f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance5 >= 76) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (500 - transverse1 - (100 - mGpsPistance5) * 2.08f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (300 - disparity - (100 - mGpsPistance5) * 4.17f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance5 >= 26 && mGpsPistance5 < 76) {
                        mPeoplefour.setY(200 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (450 - transverse1 - (75 - mGpsPistance5) * 6.12f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (150 - transverse1 + (25 - mGpsPistance5) * 2f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (200 - disparity - (25 - mGpsPistance5) * 4f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "13":
                nine = false;
                ten = false;
                eleven = false;
                twelve = false;
                fourteen = false;
                if (thirteen == false) {
                    thirteen = true;
                    mPeoplefour.setX(800 - transverse1);
                    mPeoplefour.setY(400 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (800 - transverse - (100 - mGpsPistance5) * 7.5f));
                    animator.setDuration(mTime);
                    animator.start();
                } else {
                    mPeoplefour.setY(400 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (800 - transverse - (100 - mGpsPistance5) * 7.5f));
                    animator.setDuration(mTime);
                    animator.start();
                }
                break;
            case "14":
                nine = false;
                ten = false;
                eleven = false;
                twelve = false;
                thirteen = false;
                if (fourteen == false) {
                    fourteen = true;
                    mPeoplefour.setX(450 - transverse1);
                    mPeoplefour.setY(400 - disparity);
                    if (mGpsPistance5 >= 47) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (450 - transverse1 - (100 - mGpsPistance5) * 1.89f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (400 - disparity + (100 - mGpsPistance5) * 1.89f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeoplefour.setY(500 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (350 - transverse1 - (46 - mGpsPistance5) * 6.52f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                } else {
                    if (mGpsPistance5 >= 47) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (450 - transverse1 - (100 - mGpsPistance5) * 1.89f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (400 - disparity + (100 - mGpsPistance5) * 1.89f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeoplefour.setY(500 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (350 - transverse1 - (46 - mGpsPistance5) * 6.52f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                }
                break;
            case "15":
                sixteen = false;
                seventeen = false;
                eighteen = false;
                nineteen = false;
                if (fifteen == false) {
                    fifteen = true;
                    mPeoplefour.setX(50 - transverse1);
                    mPeoplefour.setY(300 - disparity);
                    if (mGpsPistance5 <= 42) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (50 - transverse1 + mGpsPistance5 * 10.71f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (300 - disparity + mGpsPistance5 * 5.95f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeoplefour.setX(500 - transverse1);
                        mPeoplefour.setY(550 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (500 - transverse1 + (mGpsPistance5 - 43) * 5.26f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                } else {
                    if (mGpsPistance5 <= 42) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (50 - transverse1 + mGpsPistance5 * 10.71f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (300 - disparity + mGpsPistance5 * 5.95f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeoplefour.setY(550 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (500 - transverse1 + (mGpsPistance5 - 43) * 5.26f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                }
                break;
            case "16":
                fifteen = false;
                seventeen = false;
                eighteen = false;
                nineteen = false;
                if (sixteen == false) {
                    sixteen = true;
                    mPeoplefour.setX(450 - transverse1);
                    mPeoplefour.setY(350 - disparity);
                    if (mGpsPistance4 <= 30) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (450 - transverse1 + mGpsPistance5 * 3.33f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (350 - disparity + mGpsPistance5 * 3.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeoplefour.setY(450 - disparity);
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (550 - transverse1 + (mGpsPistance5 - 31) * 4.93f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance4 <= 30) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (450 - transverse1 + mGpsPistance5 * 3.33f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (350 - disparity + mGpsPistance5 * 3.33f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeoplefour.setY(450 - disparity);
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (550 - transverse1 + (mGpsPistance5 - 31) * 4.93f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "17":
                fifteen = false;
                sixteen = false;
                eighteen = false;
                nineteen = false;
                if (seventeen == false) {
                    seventeen = true;
                    mPeoplefour.setX(150 - transverse1);
                    mPeoplefour.setY(150 - disparity);
                    if (mGpsPistance5 <= 52) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (150 - transverse1 + mGpsPistance5 * 4.81f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (150 - disparity + mGpsPistance5 * 3.85f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeoplefour.setX(400 - transverse1);
                        mPeoplefour.setY(350 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (400 - transverse1 + (mGpsPistance5 - 53) * 8.51f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                } else {
                    if (mGpsPistance5 <= 52) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (150 - transverse1 + mGpsPistance5 * 4.81f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (150 - disparity + mGpsPistance5 * 3.85f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else {
                        mPeoplefour.setY(350 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (400 - transverse1 + (mGpsPistance5 - 53) * 8.51f));
                        animator.setDuration(mTime);
                        animator.start();
                    }
                }
                break;
            case "18":
                fifteen = false;
                sixteen = false;
                seventeen = false;
                eighteen = false;
                nineteen = false;
                if (eighteen == false) {
                    eighteen = true;
                    mPeoplefour.setX(500 - transverse1);
                    mPeoplefour.setY(350 - disparity);
                    if (mGpsPistance5 <= 8) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (500 - transverse1 + mGpsPistance5 * 6.25f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (350 - disparity - mGpsPistance5 * 12.5f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance5 > 8 && mGpsPistance5 <= 79) {
                        mPeoplefour.setY(250 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (550 - transverse1 + (mGpsPistance5 - 8) * 2.82f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (750 - transverse1 + (mGpsPistance5 - 79) * 2.38f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (250 - disparity + (mGpsPistance5 - 79) * 4.76f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                } else {
                    if (mGpsPistance5 <= 8) {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (500 - transverse1 + mGpsPistance5 * 6.25f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (350 - disparity - mGpsPistance5 * 12.5f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    } else if (mGpsPistance5 > 8 && mGpsPistance5 <= 79) {
                        mPeoplefour.setY(250 - disparity);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (550 - transverse1 + (mGpsPistance5 - 8) * 2.82f));
                        animator.setDuration(mTime);
                        animator.start();
                    } else {
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (750 - transverse1 + (mGpsPistance5 - 79) * 2.38f));
                        animator.setDuration(mTime);
                        animator.start();
                        ObjectAnimator animator1
                                = ObjectAnimator.ofFloat(mPeoplefour, "translationY", (float) (250 - disparity + (mGpsPistance5 - 79) * 4.76f));
                        animator1.setDuration(mTime);
                        animator1.start();
                    }
                }
                break;
            case "19":
                mMap3.setName("visible");
                mMap1.setName("gone");
                mMap2.setName("gone");
                fifteen = false;
                sixteen = false;
                seventeen = false;
                eighteen = false;
                if (nineteen == false) {
                    nineteen = true;
                    mPeoplefour.setX(800 - transverse1);
                    mPeoplefour.setY(350 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (800 - transverse1 + mGpsPistance5 * 2f));
                    animator.setDuration(mTime);
                    animator.start();
                } else {
                    mPeoplefour.setY(350 - disparity);
                    ObjectAnimator animator
                            = ObjectAnimator.ofFloat(mPeoplefour, "translationX", (float) (800 - transverse1 + mGpsPistance5 * 2f));
                    animator.setDuration(mTime);
                    animator.start();
                }
                break;
        }
    }
}
