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
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.example.mylibrary.RouterURLS;
import com.example.mylibrary.TestService;
import com.kylindev.totalk.R;
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
import com.kylindev.totalk.qgs.database.PersonDataUser;
import com.kylindev.totalk.qgs.database.eight.EightDataDao;
import com.kylindev.totalk.qgs.database.five.FiveDataDao;
import com.kylindev.totalk.qgs.database.hang.HangDao;
import com.kylindev.totalk.qgs.database.nine.NineDataDao;
import com.kylindev.totalk.qgs.database.seven.SevenDataDao;
import com.kylindev.totalk.qgs.database.six.SixDataDao;
import com.kylindev.totalk.qgs.move.ControlMove;
import com.kylindev.totalk.qgs.move.ControlTranslation;
import com.kylindev.totalk.qgs.park.DataUser;
import com.kylindev.totalk.qgs.park.eight.EightParkCar;
import com.kylindev.totalk.qgs.park.eight.EightParkDataDao;
import com.kylindev.totalk.qgs.park.eighteen.EighteenParkCar;
import com.kylindev.totalk.qgs.park.eighteen.EighteenParkDataDao;
import com.kylindev.totalk.qgs.park.eleven.ElevenParkCar;
import com.kylindev.totalk.qgs.park.eleven.ElevenParkDataDao;
import com.kylindev.totalk.qgs.park.fifteen.FifteenParkCar;
import com.kylindev.totalk.qgs.park.fifteen.FifteenParkDataDao;
import com.kylindev.totalk.qgs.park.five.FiveParkCar;
import com.kylindev.totalk.qgs.park.five.FiveParkDataDao;
import com.kylindev.totalk.qgs.park.four.FourDataDao;
import com.kylindev.totalk.qgs.park.four.FourParkCar;
import com.kylindev.totalk.qgs.park.fourteen.FourteenParkCar;
import com.kylindev.totalk.qgs.park.fourteen.FourteenParkDataDao;
import com.kylindev.totalk.qgs.park.nine.NineParkCar;
import com.kylindev.totalk.qgs.park.nine.NineParkDataDao;
import com.kylindev.totalk.qgs.park.nineteen.NineteenParkCar;
import com.kylindev.totalk.qgs.park.nineteen.NineteenParkDataDao;
import com.kylindev.totalk.qgs.park.one.OneDataDao;
import com.kylindev.totalk.qgs.park.one.OneParkCar;
import com.kylindev.totalk.qgs.park.seven.SevenParkCar;
import com.kylindev.totalk.qgs.park.seven.SevenParkDataDao;
import com.kylindev.totalk.qgs.park.seventeen.SeventeenParkCar;
import com.kylindev.totalk.qgs.park.seventeen.SeventeenParkDataDao;
import com.kylindev.totalk.qgs.park.six.SixParkCar;
import com.kylindev.totalk.qgs.park.six.SixParkDataDao;
import com.kylindev.totalk.qgs.park.sixteen.SixteenParkCar;
import com.kylindev.totalk.qgs.park.sixteen.SixteenParkDataDao;
import com.kylindev.totalk.qgs.park.ten.TenParkCar;
import com.kylindev.totalk.qgs.park.ten.TenParkDataDao;
import com.kylindev.totalk.qgs.park.thirteen.ThirteenParkCar;
import com.kylindev.totalk.qgs.park.thirteen.ThirteenParkDataDao;
import com.kylindev.totalk.qgs.park.three.ThreeDataDao;
import com.kylindev.totalk.qgs.park.three.ThreeParkCar;
import com.kylindev.totalk.qgs.park.twelve.TwelveParkCar;
import com.kylindev.totalk.qgs.park.twelve.TwelveParkDataDao;
import com.kylindev.totalk.qgs.park.two.TwoDataDao;
import com.kylindev.totalk.qgs.park.two.TwoParkCar;
import com.kylindev.totalk.qgs.people.TransferPeople;
import com.kylindev.totalk.qgs.people.TransferPeopleFour;
import com.kylindev.totalk.qgs.people.TransferPeopleOne;
import com.kylindev.totalk.qgs.people.TransferPeopleThree;
import com.kylindev.totalk.qgs.people.TransferPeopleTwo;
import com.kylindev.totalk.qgs.tack.PickDao;
import com.kylindev.totalk.qgs.tack.WanAsynTask;

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

    private List<Integer> mListNum = new ArrayList<>();
    private List<Integer> mListInteger = new ArrayList<>();
    private DrawTop mTrain;
    private String mData;
    @Autowired(name = RouterURLS.BASE_MAIN)
    TestService app;
    private TextView mPointText;
    private String mEncodeHexStr, mEncodeHex;
    private String receive = "";
    private boolean flag = false;
    private String TAG = "PointActivity.class";
    //private GPSDao mGpsDao;
    private Button mBtn, mBtn1;
    private PickDao mPickDao;
    List<Double> mList = new ArrayList<>();
    private String mS1;
    private boolean shi = false;
    private boolean wu = false;
    private boolean san = false;
    private boolean yi = false;
    private String mName, mFace, mId, mCumulative, mTotal, mTotal1, mHead, mEnd, mName1, jieAll, mId1, mSubstring;
    private TextView mJuli, mJwd, mLat1, mLat2;
    private List<GPSUser> mGpsUsers;
    private SpUtil mInstructions, mCqncast, mControlCar, mControlshuntinghunting, mControlTuiJin, mCon;
    private SiveDao mSiveDao;
    private TestService mAppService;
    private SQLiteDatabase mMDatabase;
    private WanAsynTask mWanAsynTask;
    private double mDistance;
    private String mConversationId = "01";
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

    private boolean isSixTrack = true;

    private SpUtil mFirstInto;
    private String mReceiveHead, mRatioOfGpsTrackCar1,mRatioOfGpsTrack, mRatioOfGpsTrack2, mRatioOfGpsTrack3, mRatioOfGpsTrack4, mRatioOfGpsTrack5, mRatioOfGpsTrackCar, mTime1;
    private Double mLat3, mLon3, mGpsPistanceCar1,mGpsPistance, mGpsPistance2, mGpsPistance3, mGpsPistance4, mGpsPistance5, mLatLeadCar20, mLonLeadCar20, mGpsPistanceCar;
    private TransferPeople mTransferpeople;
    private TransferPeopleOne mPeopleOne;
    private SpUtil mPeople5, mPeople6, mPeople7, mPeople8, mPeople9;
    private SpUtil mMap1, mMap2, mMap3, mDataTransmission;
    private SpUtil mReceive1, mReceive2, mReceive3, qgs;
    private double mGetRatioOfGpsPointCar;
    private TransferPeopleTwo mPeopletwo;
    private TransferPeopleThree mPeoplethree;
    private TransferPeopleFour mPeoplefour;
    private SpUtil mAdvancedmr, mOnePickLeft, mTwoPickLeft, mThreePickLeft, mFourPickLeft, mFivePickLeft, mSixPickLeft, mSevenPickLeft, mEightPickLeft;
    private SpUtil mNinePickLeft, mTenPickLeft, mElevenpickleft, mTwelvePickLeft, mThirteenPickLeft, mFourteenPickLeft;
    private SpUtil mFifteenPickLeft, mSixteenPickLeft, mSeventeenPickLeft, mEighteenPickLeft, mNineteenPickLeft;
    private SpUtil mOnepickright, mTwopickright, mThreepickright, mFourpickright, mFivepickright, mSixpickright, mSevenpickright, mEightpickright;
    private SpUtil mNinepickright, mTenpickright, mElevenpickright, mTwelvepickright, mThirteenpickright, mFourteenpickright;
    private SpUtil mFifteenPickRight, mSixteenPickRight, mSeventeenPickRight, mEighteenPickRight, mNineteenPickRight;
    private XiNingBeiMap mXiningbeimap;
    private ChangFengMap mChangfengmap;
    private BaiLiMap mBailimap;
    private Button mDelete;
    private SpUtil mLeftCar, mRightCar, mLeadcar, mStopcar, mCarLocation, mMain;
    private int mGetGudaoOfGpsPoint;
    private String mStopcar1, mLeadcar1, mRatioOfGpsTrackCar2;
    private String mLat21, mLon21, mLat31, mLon31, mLat4, mLon4, mLat5, mLon5, mTrackCar, mLatCar, mLonCar, mPositionCar;
    private Double mLatStopCar01, mLonStopCar01, mLatStopCar02, mLonStopCar02, mLatStopCar03, mLonStopCar03, mLatStopCar04, mLonStopCar04, mPositionCar1;
    private int mGetGudaoOfGpsPoint2;
    private double mGetRatioOfGpsPointCar2, mGetRatioOfGpsPointCar3;
    private OneParkCar mOneparkcar;
    private TwoParkCar mTwoparkcar;
    private ThreeParkCar mThreeparkcar;
    private FourParkCar mFourparkcar;
    private FiveParkCar mFiveparkcar;
    private SixParkCar mSixparkcar;
    private EightParkCar mEightparkcar;
    private TenParkCar mTenparkcar;
    private SpUtil mControlOnePick, mControlTwoPick, mControlThreePick, mControlFourPick, mControlFivePick, mControlSixPick, mControlSevenPick, mControlEightPick;
    private SpUtil mControlNinePick, mControlTenPick, mControlElevenPick, mControlTwelvePick, mControlThirteenPick, mControlFourteenPick;
    private SpUtil mControlFifteenPick, mControlSixteenPick, mControlSeventeenPick, mControlEighteenPick, mControlNineteenPick;
    private int mGetGudaoOfGpsPoint3, mGetGudaoOfGpsPoint4, mGetGudaoOfGpsPoint5;
    private String mRatioOfGpsTrackCar3, mRatioOfGpsTrackCar4, mRatioOfGpsTrackCar5;
    private double mGetRatioOfGpsPointCar4, mGetRatioOfGpsPointCar5;
    private SixParkDataDao mSixParkDataDao;
    private RelativeLayout mRelative;
    private SixParkCar mSixParkCar;
    private SevenParkCar mSevenParkCar;
    private SevenParkDataDao mSevenParkDataDao;
    private NineParkDataDao mNineParkDataDao;
    private ElevenParkDataDao mElevenParkDataDao;
    private TwelveParkDataDao mTwelveParkDataDao;
    private ThirteenParkDataDao mThirteenParkDataDao;
    private FourteenParkDataDao mFourteenParkDataDao;
    private NineParkCar mNineParkCar;
    private ElevenParkCar mElevenParkCar;
    private TwelveParkCar mTwelveParkCar;
    private ThirteenParkCar mThirteenParkCar;
    private FourteenParkCar mFourteenParkCar;
    private FifteenParkDataDao mFifteenParkDataDao;
    private SixteenParkDataDao mSixteenParkDataDao;
    private SeventeenParkDataDao mSeventeenParkDataDao;
    private EighteenParkDataDao mEighteenParkDataDao;
    private NineteenParkDataDao mNineteenParkDataDao;
    private FifteenParkCar mFifteenParkCar;
    private SixteenParkCar mSixteenParkCar;
    private SeventeenParkCar mSeventeenParkCar;
    private EighteenParkCar mEighteenParkCar;
    private NineteenParkCar mNineteenParkCar;
    private String mOneTrackLeft, mOneLatLeft, mOneLonLeft, mOnePositionLeft, mTwoTrackLeft, mTwoLatLeft, mTwoLonLeft, mTwoPositionLeft, mThreeTrackLeft, mThreeLatLeft, mThreeLonLeft, mThreePositionLeft, mFourTrackLeft, mFourLatLeft, mFourLonLeft, mFourPositionLeft, mFiveTrackLeft, mFiveLatLeft, mFiveLonLeft, mFivePositionLeft;
    private String mOneTrackRight, mOneLatRight, mOneLonRight, mOnePositionRight, mTwoTrackRight, mTwoLatRight, mTwoLonRight, mTwoPositionRight, mThreeTrackRight, mThreeLatRight, mThreeLonRight, mThreePositionRight, mFourTrackRight, mFourLatRight, mFourLonRight, mFourPositionRight, mFiveTrackRight, mFiveLatRight, mFiveLonRight, mFivePositionRight;
    private SpUtil mCar;
    private String mPeopleId2;
    private Double mLatSp;
    private Double mLonSp;
    private SpUtil mControlTrack;
    private String mGpsPoint2;
    private String mControlTrackName;
    private SpUtil mControlMap;
    private String mMControlMapName;
    private SpUtil mPeople0, mPeople1, mPeople2, mPeople3, mPeople4;
    private OneDataDao mOneDataDao;
    private String mLat6, mLat61;
    private String mLon6, mLon61;
    private int mMinIndex;
    private int mMaxIndex;
    String maxString = "";
    private SpUtil mCopyCar;

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
                //try {
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
                    mMControlMapName = mControlMap.getName();
                    if (mMControlMapName.equals("zheng")) {
                        mXiningbeimap.setVisibility(View.VISIBLE);
                        mChangfengmap.setVisibility(View.GONE);
                        mBailimap.setVisibility(View.GONE);
                        mMain.setName("main");
                    } else if (mMControlMapName.equals("cf")) {
                        mChangfengmap.setVisibility(View.VISIBLE);
                        mXiningbeimap.setVisibility(View.GONE);
                        mBailimap.setVisibility(View.GONE);
                        mMain.setName("changfeng");
                    } else if (mMControlMapName.equals("bl")) {
                        mBailimap.setVisibility(View.VISIBLE);
                        mChangfengmap.setVisibility(View.GONE);
                        mXiningbeimap.setVisibility(View.GONE);
                        mMain.setName("baili");
                    }
                        /*mMap1.setName("visible");
                        mMap2.setName("gone");
                        mMap3.setName("gone");*/
                    /*if (name1.equals("visible")) {
                        mXiningbeimap.setVisibility(View.VISIBLE);
                        mChangfengmap.setVisibility(View.GONE);
                        mBailimap.setVisibility(View.GONE);
                        mMain.setName("main");
                        mainPicture();
                    }
                    if (name2.equals("visible")) {
                        mChangfengmap.setVisibility(View.VISIBLE);
                        mXiningbeimap.setVisibility(View.GONE);
                        mBailimap.setVisibility(View.GONE);
                        mMain.setName("changfeng");
                        changfengPicture();
                    }
                    if (name3.equals("visible")) {
                        mBailimap.setVisibility(View.VISIBLE);
                        mChangfengmap.setVisibility(View.GONE);
                        mXiningbeimap.setVisibility(View.GONE);
                        mMain.setName("baili");
                        bailiPicture();
                    }*/

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
                                                mCar.setLat(latCar + "");
                                                mCar.setLon(lonCar + "");
                                                //mGpsDao.add(a2, b2);

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
                                                ControlTranslation.proplrMove1(mControlMap, mTrain, mRatioOfGpsTrackCar, mGpsPistanceCar, transverse, disparity);
                                                //proplrMove();

                                                    /*String onePickLeftName = mOnePickLeft.getName();
                                                    String onepickrightightName = mOnepickright.getName();
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
                                                                mOnepickright.setName(ratioOfGpsPointCar);
                                                            } else if (ratioOfGpsPointCarDouble < ratioOfGpsPointCarDouble1) {
                                                                mOnePickLeft.setName(ratioOfGpsPointCar);
                                                                mOnepickright.setName(ratioOfGpsPointCar1);
                                                            }
                                                        }
                                                    }*/

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
                    /*DataDao232 dataDao232 = new DataDao232(getApplicationContext());
                    dataDao232.add(mTime1, mEncodeHexStr);*/

                    if (mEncodeHexStr.substring(0,2).equals("A5")) {
                        if (mEncodeHexStr != null && mEncodeHexStr.length() >= 8) {
                            //调号
                            String signature = mEncodeHexStr.substring(2, 4);
                            //人员号
                            mPeopleId2 = mEncodeHexStr.substring(4, 6);
                            String function2 = mEncodeHexStr.substring(6, 8);
                            String totalDmr = mPeopleId2 + signature + function2 + "03";
                    /*if (function2.equals("59")) {
                        String totalDmr = mPeopleId2 + signature + function2 + "03";
                        sendMessage(mConversationId, totalDmr);
                    } else if (function2.equals("5A")) {
                        String totalDmr = mPeopleId2 + signature + function2 + "03";
                        sendMessage(mConversationId, totalDmr);
                    } else {
                        String totalDmr = mPeopleId2 + signature + function2 + "03";
                        sendMessage(mConversationId, totalDmr);
                    }*/
                            switch (function2) {
                                //摘钩
                                case "59":
                            /*PickDao pickDao = new PickDao(getApplication());
                            pickDao.add(mTime1, mEncodeHexStr);*/
                                    String zhen = mEncodeHexStr.substring(8, 10);
                                    if (zhen.equals("01")) {
                                        sendMessage(mConversationId, totalDmr);
                                        //zhaigou();
                                    }
                                    break;
                                //挂钩
                                case "5A":
                                    //
                            /*HangDao hangDao = new HangDao(getApplication());
                            hangDao.add(mTime1, mEncodeHexStr);*/
                                    String zhen5A = mEncodeHexStr.substring(8, 10);
                                    if (zhen5A.equals("01")) {
                                        sendMessage(mConversationId, totalDmr);
                                        //guagou();
                                    }
                                    break;
                                //启动
                                case "41":
                                    sendMessage(mConversationId, totalDmr);
                                    //qidong();
                                    break;
                                //推进
                                case "43":
                                    sendMessage(mConversationId, totalDmr);
                                    //tuijin();
                                    break;
                                case "71":
                                    sendMessage(mConversationId, totalDmr);
                                    mAdvancedmr.setName("false");
                                    //停车
                                    san = false;
                                    wu = false;
                                    shi = false;
                                    yi = false;
                                    break;
                                case "98"://领车
                                    sendMessage(mConversationId, totalDmr);
                                    break;
                                case "9A"://领车完毕
                                    sendMessage(mConversationId, totalDmr);
                                    //收到领车指令后计算领车员与机车的经纬度差
                                    //紧急停车
                                    //停车股道号
                                    break;
                                case "73":
                                    sendMessage(mConversationId, totalDmr);
                                    //紧急停车
                                    //停车股道号
                                    //mStopcar1 = mStopcar.getName();
                                    //mLeadcar.setName(mPeopleId2);
                                    //获取机车的股道
                                    //mTrackCar = mStopcar.getTrack();
                                    //获取机车的经纬度
                                    //mLatCar = mStopcar.getLat();
                                    //mLonCar = mStopcar.getLon();
                                    //获取机车的位置
                                    //mPositionCar = mStopcar.getPosition();
                                    //mPositionCar1 = Double.valueOf(mPositionCar);

                                    //jinjitingche();
                                    break;
                                default:
                                    sendMessage(mConversationId, totalDmr);
                                    break;
                            }
                            //mTotal = "2001" + function + "02";
                        }
                    }
                }
                /*} catch (Exception e) {
                    Log.e("数据异常", "数据异常：" + e);
                }*/
            }
        });
    }

    String data = "";
    String dataSum = "";

    private void getSixNum() {
        mListInteger.clear();
        //找6道最大值最小值下标
        List<DataUser> dataUsers = mSixParkDataDao.find();
        int size = dataUsers.size();
        if (size > 1) {
            for (int i = 1; i < size; i++) {
                String lat = dataUsers.get(i).getLat();
                String lon = dataUsers.get(i).getLon();
                String ratioOfGpsPointCar = dataUsers.get(i).getRatioOfGpsPointCar();
                Integer integer = Integer.valueOf(ratioOfGpsPointCar);
                mListInteger.add(integer);
                if (lat.length() > 6 && lon.length() > 6) {
                    String lat1 = lat.substring(3, lat.length());
                    String lon1 = lon.substring(4, lon.length());
                    data += "(" + lat1 + "," + lon1 + ")";
                }
            }
            Log.e("秦广帅mListInteger", mListInteger.toString() + "");
            int minIndex = getMinIndex(mListInteger);
            Log.e("秦广帅minIndex", minIndex + "");
            int maxIndex = getMaxIndex(mListInteger);
            Log.e("秦广帅maxIndex", maxIndex + "");
            String minRatioOfGpsPointCar = dataUsers.get(minIndex + 1).getRatioOfGpsPointCar();
            String minLat = dataUsers.get(minIndex + 1).getLat();
            String minLon = dataUsers.get(minIndex + 1).getLon();
            Log.e("秦广帅maxLat", minRatioOfGpsPointCar + "  " + minLat + "  " + minLon);
            String maxRatioOfGpsPointCar = dataUsers.get(maxIndex + 1).getRatioOfGpsPointCar();
            String maxLat = dataUsers.get(maxIndex + 1).getLat();
            String maxLon = dataUsers.get(maxIndex + 1).getLon();
            Log.e("秦广帅maxLat", maxRatioOfGpsPointCar + "  " + maxLat + "  " + maxLon);
            mSixPickLeft.setPosition(minRatioOfGpsPointCar + "");
            mSixPickLeft.setLat(minLat);
            mSixPickLeft.setLon(minLon);
            mSixpickright.setPosition(maxRatioOfGpsPointCar + "");
            mSixpickright.setLat(maxLat);
            mSixpickright.setLon(maxLon);

            //mControlTrackName
            dataSum = "0" + mControlTrackName + "-停留车-" + data;
            sendMessage(mConversationId, dataSum);
        } else {
            mSixPickLeft.setPosition("0");
            mSixPickLeft.setLat("0");
            mSixPickLeft.setLon("0");
            mSixpickright.setPosition("0");
            mSixpickright.setLat("0");
            mSixpickright.setLon("0");
            dataSum = "0" + mControlTrackName + "-停留车-" + "(000000,000000)(000000,000000)";
            sendMessage(mConversationId, dataSum);
        }
        data = "";
        dataSum = "";
    }

    private void getSevenNum() {
        mListInteger.clear();
        //找7道最大值最小值下标
        List<DataUser> dataUsers = mSevenParkDataDao.find();
        int size = dataUsers.size();
        if (size > 1) {
            for (int i = 1; i < size; i++) {
                String lat = dataUsers.get(i).getLat();
                String lon = dataUsers.get(i).getLon();
                String ratioOfGpsPointCar = dataUsers.get(i).getRatioOfGpsPointCar();
                Integer integer = Integer.valueOf(ratioOfGpsPointCar);
                mListInteger.add(integer);
                if (lat.length() > 6 && lon.length() > 6) {
                    String lat1 = lat.substring(3, lat.length());
                    String lon1 = lon.substring(4, lon.length());
                    data += "(" + lat1 + "," + lon1 + ")";
                }
            }
            int min = getMin(mListInteger);
            int minIndex = getMinIndex(mListInteger);
            int max = getMax(mListInteger);
            int maxIndex = getMaxIndex(mListInteger);
            String minRatioOfGpsPointCar = dataUsers.get(minIndex + 1).getRatioOfGpsPointCar();
            String minLat = dataUsers.get(minIndex + 1).getLat();
            String minLon = dataUsers.get(minIndex + 1).getLon();
            String maxRatioOfGpsPointCar = dataUsers.get(maxIndex + 1).getRatioOfGpsPointCar();
            String maxLat = dataUsers.get(maxIndex + 1).getLat();
            String maxLon = dataUsers.get(maxIndex + 1).getLon();
            mSevenPickLeft.setPosition(minRatioOfGpsPointCar + "");
            mSevenPickLeft.setLat(minLat);
            mSevenPickLeft.setLon(minLon);
            mSevenpickright.setPosition(maxRatioOfGpsPointCar + "");
            mSevenpickright.setLat(maxLat);
            mSevenpickright.setLon(maxLon);

            dataSum = "0" + mControlTrackName + "-停留车-" + data;
            sendMessage(mConversationId, dataSum);
            data = "";
            dataSum = "";
        } else {
            mSevenPickLeft.setPosition("0");
            mSevenPickLeft.setLat("0");
            mSevenPickLeft.setLon("0");
            mSevenpickright.setPosition("0");
            mSevenpickright.setLat("0");
            mSevenpickright.setLon("0");

            dataSum = "0" + mControlTrackName + "-停留车-" + "(0,0)(0,0)";
            sendMessage(mConversationId, dataSum);
            data = "";
            dataSum = "";
        }
    }

    private void getNineNum() {
        mListInteger.clear();
        //找9道最大值最小值下标
        List<DataUser> dataUsers = mNineParkDataDao.find();
        int size = dataUsers.size();
        if (size > 1) {
            for (int i = 1; i < size; i++) {
                String lat = dataUsers.get(i).getLat();
                String lon = dataUsers.get(i).getLon();
                String ratioOfGpsPointCar = dataUsers.get(i).getRatioOfGpsPointCar();
                Integer integer = Integer.valueOf(ratioOfGpsPointCar);
                mListInteger.add(integer);
                if (lat.length() > 6 && lon.length() > 6) {
                    String lat1 = lat.substring(3, lat.length());
                    String lon1 = lon.substring(4, lon.length());
                    data += "(" + lat1 + "," + lon1 + ")";
                }
            }
            int min = getMin(mListInteger);
            int minIndex = getMinIndex(mListInteger);
            int max = getMax(mListInteger);
            int maxIndex = getMaxIndex(mListInteger);
            String minRatioOfGpsPointCar = dataUsers.get(minIndex + 1).getRatioOfGpsPointCar();
            String minLat = dataUsers.get(minIndex + 1).getLat();
            String minLon = dataUsers.get(minIndex + 1).getLon();
            String maxRatioOfGpsPointCar = dataUsers.get(maxIndex + 1).getRatioOfGpsPointCar();
            String maxLat = dataUsers.get(maxIndex + 1).getLat();
            String maxLon = dataUsers.get(maxIndex + 1).getLon();
            mNinePickLeft.setPosition(minRatioOfGpsPointCar + "");
            mNinePickLeft.setLat(minLat);
            mNinePickLeft.setLon(minLon);
            mNinepickright.setPosition(maxRatioOfGpsPointCar + "");
            mNinepickright.setLat(maxLat);
            mNinepickright.setLon(maxLon);

            dataSum = "0" + mControlTrackName + "-停留车-" + data;
            sendMessage(mConversationId, dataSum);
            data = "";
            dataSum = "";
        } else {
            mNinePickLeft.setPosition("0");
            mNinePickLeft.setLat("0");
            mNinePickLeft.setLon("0");
            mNinepickright.setPosition("0");
            mNinepickright.setLat("0");
            mNinepickright.setLon("0");

            dataSum = "0" + mControlTrackName + "-停留车-" + "(0,0)(0,0)";
            sendMessage(mConversationId, dataSum);
            data = "";
            dataSum = "";
        }
    }

    private void getElevenNum() {
        mListInteger.clear();
        //找11道最大值最小值下标
        List<DataUser> dataUsers = mElevenParkDataDao.find();
        int size = dataUsers.size();
        if (size > 1) {
            for (int i = 1; i < size; i++) {
                String lat = dataUsers.get(i).getLat();
                String lon = dataUsers.get(i).getLon();
                String ratioOfGpsPointCar = dataUsers.get(i).getRatioOfGpsPointCar();
                Integer integer = Integer.valueOf(ratioOfGpsPointCar);
                mListInteger.add(integer);
                if (lat.length() > 6 && lon.length() > 6) {
                    String lat1 = lat.substring(3, lat.length());
                    String lon1 = lon.substring(4, lon.length());
                    data += "(" + lat1 + "," + lon1 + ")";
                }
            }
            int minIndex = getMinIndex(mListInteger);
            int maxIndex = getMaxIndex(mListInteger);
            String minRatioOfGpsPointCar = dataUsers.get(minIndex + 1).getRatioOfGpsPointCar();
            String minLat = dataUsers.get(minIndex + 1).getLat();
            String minLon = dataUsers.get(minIndex + 1).getLon();
            String maxRatioOfGpsPointCar = dataUsers.get(maxIndex + 1).getRatioOfGpsPointCar();
            String maxLat = dataUsers.get(maxIndex + 1).getLat();
            String maxLon = dataUsers.get(maxIndex + 1).getLon();
            mElevenpickleft.setPosition(minRatioOfGpsPointCar + "");
            mElevenpickleft.setLat(minLat);
            mElevenpickleft.setLon(minLon);
            mElevenpickright.setPosition(maxRatioOfGpsPointCar + "");
            mElevenpickright.setLat(maxLat);
            mElevenpickright.setLon(maxLon);

            dataSum = mControlTrackName + "-停留车-" + data;
            sendMessage(mConversationId, dataSum);
            data = "";
            dataSum = "";
        } else {
            mElevenpickleft.setPosition("0");
            mElevenpickleft.setLat("0");
            mElevenpickleft.setLon("0");
            mElevenpickright.setPosition("0");
            mElevenpickright.setLat("0");
            mElevenpickright.setLon("0");

            dataSum = mControlTrackName + "-停留车-" + "(0,0)(0,0)";
            sendMessage(mConversationId, dataSum);
            data = "";
            dataSum = "";
        }
    }

    private void getTwelveNum() {
        mListInteger.clear();
        //找12道最大值最小值下标
        List<DataUser> dataUsers = mTwelveParkDataDao.find();
        int size = dataUsers.size();
        if (size > 1) {
            for (int i = 1; i < size; i++) {
                String lat = dataUsers.get(i).getLat();
                String lon = dataUsers.get(i).getLon();
                String ratioOfGpsPointCar = dataUsers.get(i).getRatioOfGpsPointCar();
                Integer integer = Integer.valueOf(ratioOfGpsPointCar);
                mListInteger.add(integer);
                if (lat.length() > 6 && lon.length() > 6) {
                    String lat1 = lat.substring(3, lat.length());
                    String lon1 = lon.substring(4, lon.length());
                    data += "(" + lat1 + "," + lon1 + ")";
                }
            }
            int minIndex = getMinIndex(mListInteger);
            int maxIndex = getMaxIndex(mListInteger);
            String minRatioOfGpsPointCar = dataUsers.get(minIndex + 1).getRatioOfGpsPointCar();
            String minLat = dataUsers.get(minIndex + 1).getLat();
            String minLon = dataUsers.get(minIndex + 1).getLon();
            String maxRatioOfGpsPointCar = dataUsers.get(maxIndex + 1).getRatioOfGpsPointCar();
            String maxLat = dataUsers.get(maxIndex + 1).getLat();
            String maxLon = dataUsers.get(maxIndex + 1).getLon();
            mTwelvePickLeft.setPosition(minRatioOfGpsPointCar + "");
            mTwelvePickLeft.setLat(minLat);
            mTwelvePickLeft.setLon(minLon);
            mTwelvepickright.setPosition(maxRatioOfGpsPointCar + "");
            mTwelvepickright.setLat(maxLat);
            mTwelvepickright.setLon(maxLon);

            dataSum = mControlTrackName + "-停留车-" + data;
            sendMessage(mConversationId, dataSum);
            data = "";
            dataSum = "";
        } else {
            mTwelvePickLeft.setPosition("0");
            mTwelvePickLeft.setLat("0");
            mTwelvePickLeft.setLon("0");
            mTwelvepickright.setPosition("0");
            mTwelvepickright.setLat("0");
            mTwelvepickright.setLon("0");

            dataSum = mControlTrackName + "-停留车-" + "(0,0)(0,0)";
            sendMessage(mConversationId, dataSum);
            data = "";
            dataSum = "";
        }
    }

    private void getThirteenNum() {
        mListInteger.clear();
        //找13道最大值最小值下标
        List<DataUser> dataUsers = mThirteenParkDataDao.find();
        int size = dataUsers.size();
        if (size > 1) {
            for (int i = 1; i < size; i++) {
                String lat = dataUsers.get(i).getLat();
                String lon = dataUsers.get(i).getLon();
                String ratioOfGpsPointCar = dataUsers.get(i).getRatioOfGpsPointCar();
                Integer integer = Integer.valueOf(ratioOfGpsPointCar);
                mListInteger.add(integer);
                if (lat.length() > 6 && lon.length() > 6) {
                    String lat1 = lat.substring(3, lat.length());
                    String lon1 = lon.substring(4, lon.length());
                    data += "(" + lat1 + "," + lon1 + ")";
                }
            }
            int minIndex = getMinIndex(mListInteger);
            int maxIndex = getMaxIndex(mListInteger);
            String minRatioOfGpsPointCar = dataUsers.get(minIndex + 1).getRatioOfGpsPointCar();
            String minLat = dataUsers.get(minIndex + 1).getLat();
            String minLon = dataUsers.get(minIndex + 1).getLon();
            String maxRatioOfGpsPointCar = dataUsers.get(maxIndex + 1).getRatioOfGpsPointCar();
            String maxLat = dataUsers.get(maxIndex + 1).getLat();
            String maxLon = dataUsers.get(maxIndex + 1).getLon();
            mThirteenPickLeft.setPosition(minRatioOfGpsPointCar + "");
            mThirteenPickLeft.setLat(minLat);
            mThirteenPickLeft.setLon(minLon);
            mThirteenpickright.setPosition(maxRatioOfGpsPointCar + "");
            mThirteenpickright.setLat(maxLat);
            mThirteenpickright.setLon(maxLon);

            dataSum = mControlTrackName + "-停留车-" + data;
            sendMessage(mConversationId, dataSum);
            data = "";
            dataSum = "";
        } else {
            mThirteenPickLeft.setPosition("0");
            mThirteenPickLeft.setLat("0");
            mThirteenPickLeft.setLon("0");
            mThirteenpickright.setPosition("0");
            mThirteenpickright.setLat("0");
            mThirteenpickright.setLon("0");

            dataSum = mControlTrackName + "-停留车-" + "(0,0)(0,0)";
            sendMessage(mConversationId, dataSum);
            data = "";
            dataSum = "";
        }
    }

    private void getFourteenNum() {
        mListInteger.clear();
        //找14道最大值最小值下标
        List<DataUser> dataUsers = mFourteenParkDataDao.find();
        int size = dataUsers.size();
        if (size > 1) {
            for (int i = 1; i < size; i++) {
                String lat = dataUsers.get(i).getLat();
                String lon = dataUsers.get(i).getLon();
                String ratioOfGpsPointCar = dataUsers.get(i).getRatioOfGpsPointCar();
                Integer integer = Integer.valueOf(ratioOfGpsPointCar);
                mListInteger.add(integer);
                if (lat.length() > 6 && lon.length() > 6) {
                    String lat1 = lat.substring(3, lat.length());
                    String lon1 = lon.substring(4, lon.length());
                    data += "(" + lat1 + "," + lon1 + ")";
                }
            }
            int minIndex = getMinIndex(mListInteger);
            int maxIndex = getMaxIndex(mListInteger);
            String minRatioOfGpsPointCar = dataUsers.get(minIndex + 1).getRatioOfGpsPointCar();
            String minLat = dataUsers.get(minIndex + 1).getLat();
            String minLon = dataUsers.get(minIndex + 1).getLon();
            String maxRatioOfGpsPointCar = dataUsers.get(maxIndex + 1).getRatioOfGpsPointCar();
            String maxLat = dataUsers.get(maxIndex + 1).getLat();
            String maxLon = dataUsers.get(maxIndex + 1).getLon();
            mFourteenPickLeft.setPosition(minRatioOfGpsPointCar + "");
            mFourteenPickLeft.setLat(minLat);
            mFourteenPickLeft.setLon(minLon);
            mFourteenpickright.setPosition(maxRatioOfGpsPointCar + "");
            mFourteenpickright.setLat(maxLat);
            mFourteenpickright.setLon(maxLon);

            dataSum = mControlTrackName + "-停留车-" + data;
            sendMessage(mConversationId, dataSum);
            data = "";
            dataSum = "";
        } else {
            mFourteenPickLeft.setPosition("0");
            mFourteenPickLeft.setLat("0");
            mFourteenPickLeft.setLon("0");
            mFourteenpickright.setPosition("0");
            mFourteenpickright.setLat("0");
            mFourteenpickright.setLon("0");

            dataSum = mControlTrackName + "-停留车-" + "(0,0)(0,0)";
            sendMessage(mConversationId, dataSum);
            data = "";
            dataSum = "";
        }
    }

    private void getFifteenNum() {
        mListInteger.clear();
        //找15道最大值最小值下标
        List<DataUser> dataUsers = mFifteenParkDataDao.find();
        int size = dataUsers.size();
        if (size > 1) {
            for (int i = 1; i < size; i++) {
                String lat = dataUsers.get(i).getLat();
                String lon = dataUsers.get(i).getLon();
                String ratioOfGpsPointCar = dataUsers.get(i).getRatioOfGpsPointCar();
                Integer integer = Integer.valueOf(ratioOfGpsPointCar);
                mListInteger.add(integer);
                if (lat.length() > 6 && lon.length() > 6) {
                    String lat1 = lat.substring(3, lat.length());
                    String lon1 = lon.substring(4, lon.length());
                    data += "(" + lat1 + "," + lon1 + ")";
                }
            }
            int minIndex = getMinIndex(mListInteger);
            int maxIndex = getMaxIndex(mListInteger);
            String minRatioOfGpsPointCar = dataUsers.get(minIndex + 1).getRatioOfGpsPointCar();
            String minLat = dataUsers.get(minIndex + 1).getLat();
            String minLon = dataUsers.get(minIndex + 1).getLon();
            String maxRatioOfGpsPointCar = dataUsers.get(maxIndex + 1).getRatioOfGpsPointCar();
            String maxLat = dataUsers.get(maxIndex + 1).getLat();
            String maxLon = dataUsers.get(maxIndex + 1).getLon();
            mFifteenPickLeft.setPosition(minRatioOfGpsPointCar + "");
            mFifteenPickLeft.setLat(minLat);
            mFifteenPickLeft.setLon(minLon);
            mFifteenPickRight.setPosition(maxRatioOfGpsPointCar + "");
            mFifteenPickRight.setLat(maxLat);
            mFifteenPickRight.setLon(maxLon);

            dataSum = mControlTrackName + "-停留车-" + data;
            sendMessage(mConversationId, dataSum);
            data = "";
            dataSum = "";
        } else {
            mFifteenPickLeft.setPosition("0");
            mFifteenPickLeft.setLat("0");
            mFifteenPickLeft.setLon("0");
            mFifteenPickRight.setPosition("0");
            mFifteenPickRight.setLat("0");
            mFifteenPickRight.setLon("0");

            dataSum = mControlTrackName + "-停留车-" + "(0,0)(0,0)";
            sendMessage(mConversationId, dataSum);
            data = "";
            dataSum = "";
        }
    }

    private void getSixteenNum() {
        mListInteger.clear();
        //找16道最大值最小值下标
        List<DataUser> dataUsers = mSixteenParkDataDao.find();
        int size = dataUsers.size();
        if (size > 1) {
            for (int i = 1; i < size; i++) {
                String lat = dataUsers.get(i).getLat();
                String lon = dataUsers.get(i).getLon();
                String ratioOfGpsPointCar = dataUsers.get(i).getRatioOfGpsPointCar();
                Integer integer = Integer.valueOf(ratioOfGpsPointCar);
                mListInteger.add(integer);
                if (lat.length() > 6 && lon.length() > 6) {
                    String lat1 = lat.substring(3, lat.length());
                    String lon1 = lon.substring(4, lon.length());
                    data += "(" + lat1 + "," + lon1 + ")";
                }
            }
            int minIndex = getMinIndex(mListInteger);
            int maxIndex = getMaxIndex(mListInteger);
            String minRatioOfGpsPointCar = dataUsers.get(minIndex + 1).getRatioOfGpsPointCar();
            String minLat = dataUsers.get(minIndex + 1).getLat();
            String minLon = dataUsers.get(minIndex + 1).getLon();
            String maxRatioOfGpsPointCar = dataUsers.get(maxIndex + 1).getRatioOfGpsPointCar();
            String maxLat = dataUsers.get(maxIndex + 1).getLat();
            String maxLon = dataUsers.get(maxIndex + 1).getLon();
            mSixteenPickLeft.setPosition(minRatioOfGpsPointCar + "");
            mSixteenPickLeft.setLat(minLat);
            mSixteenPickLeft.setLon(minLon);
            mSixteenPickRight.setPosition(maxRatioOfGpsPointCar + "");
            mSixteenPickRight.setLat(maxLat);
            mSixteenPickRight.setLon(maxLon);

            dataSum = mControlTrackName + "-停留车-" + data;
            sendMessage(mConversationId, dataSum);
            data = "";
            dataSum = "";
        } else {
            mSixteenPickLeft.setPosition("0");
            mSixteenPickLeft.setLat("0");
            mSixteenPickLeft.setLon("0");
            mSixteenPickRight.setPosition("0");
            mSixteenPickRight.setLat("0");
            mSixteenPickRight.setLon("0");

            dataSum = mControlTrackName + "-停留车-" + "(0,0)(0,0)";
            sendMessage(mConversationId, dataSum);
            data = "";
            dataSum = "";
        }
    }

    private void getSeventeenNum() {
        mListInteger.clear();
        //找17道最大值最小值下标
        List<DataUser> dataUsers = mSeventeenParkDataDao.find();
        int size = dataUsers.size();
        if (size > 1) {
            for (int i = 1; i < size; i++) {
                String lat = dataUsers.get(i).getLat();
                String lon = dataUsers.get(i).getLon();
                String ratioOfGpsPointCar = dataUsers.get(i).getRatioOfGpsPointCar();
                Integer integer = Integer.valueOf(ratioOfGpsPointCar);
                mListInteger.add(integer);
                if (lat.length() > 6 && lon.length() > 6) {
                    String lat1 = lat.substring(3, lat.length());
                    String lon1 = lon.substring(4, lon.length());
                    data += "(" + lat1 + "," + lon1 + ")";
                }
            }
            int minIndex = getMinIndex(mListInteger);
            int maxIndex = getMaxIndex(mListInteger);
            String minRatioOfGpsPointCar = dataUsers.get(minIndex + 1).getRatioOfGpsPointCar();
            String minLat = dataUsers.get(minIndex + 1).getLat();
            String minLon = dataUsers.get(minIndex + 1).getLon();
            String maxRatioOfGpsPointCar = dataUsers.get(maxIndex + 1).getRatioOfGpsPointCar();
            String maxLat = dataUsers.get(maxIndex + 1).getLat();
            String maxLon = dataUsers.get(maxIndex + 1).getLon();
            mSeventeenPickLeft.setPosition(minRatioOfGpsPointCar + "");
            mSeventeenPickLeft.setLat(minLat);
            mSeventeenPickLeft.setLon(minLon);
            mSeventeenPickRight.setPosition(maxRatioOfGpsPointCar + "");
            mSeventeenPickRight.setLat(maxLat);
            mSeventeenPickRight.setLon(maxLon);

            dataSum = mControlTrackName + "-停留车-" + data;
            sendMessage(mConversationId, dataSum);
            data = "";
            dataSum = "";
        } else {
            mSeventeenPickLeft.setPosition("0");
            mSeventeenPickLeft.setLat("0");
            mSeventeenPickLeft.setLon("0");
            mSeventeenPickRight.setPosition("");
            mSeventeenPickRight.setLat("0");
            mSeventeenPickRight.setLon("0");

            dataSum = mControlTrackName + "-停留车-" + "(0,0)(0,0)";
            sendMessage(mConversationId, dataSum);
            data = "";
            dataSum = "";
        }
    }

    private void getEighteenNum() {
        mListInteger.clear();
        //找6道最大值最小值下标
        List<DataUser> dataUsers = mEighteenParkDataDao.find();
        int size = dataUsers.size();
        if (size > 1) {
            for (int i = 1; i < size; i++) {
                String lat = dataUsers.get(i).getLat();
                String lon = dataUsers.get(i).getLon();
                String ratioOfGpsPointCar = dataUsers.get(i).getRatioOfGpsPointCar();
                Integer integer = Integer.valueOf(ratioOfGpsPointCar);
                mListInteger.add(integer);
                if (lat.length() > 6 && lon.length() > 6) {
                    String lat1 = lat.substring(3, lat.length());
                    String lon1 = lon.substring(4, lon.length());
                    data += "(" + lat1 + "," + lon1 + ")";
                }
            }
            int minIndex = getMinIndex(mListInteger);
            int maxIndex = getMaxIndex(mListInteger);
            String minRatioOfGpsPointCar = dataUsers.get(minIndex + 1).getRatioOfGpsPointCar();
            String minLat = dataUsers.get(minIndex + 1).getLat();
            String minLon = dataUsers.get(minIndex + 1).getLon();
            String maxRatioOfGpsPointCar = dataUsers.get(maxIndex + 1).getRatioOfGpsPointCar();
            String maxLat = dataUsers.get(maxIndex + 1).getLat();
            String maxLon = dataUsers.get(maxIndex + 1).getLon();
            mEighteenPickLeft.setPosition(minRatioOfGpsPointCar + "");
            mEighteenPickLeft.setLat(minLat);
            mEighteenPickLeft.setLon(minLon);
            mEighteenPickRight.setPosition(maxRatioOfGpsPointCar + "");
            mEighteenPickRight.setLat(maxLat);
            mEighteenPickRight.setLon(maxLon);

            dataSum = mControlTrackName + "-停留车-" + data;
            sendMessage(mConversationId, dataSum);
            data = "";
            dataSum = "";
        } else {
            mEighteenPickLeft.setPosition("0");
            mEighteenPickLeft.setLat("0");
            mEighteenPickLeft.setLon("0");
            mEighteenPickRight.setPosition("0");
            mEighteenPickRight.setLat("0");
            mEighteenPickRight.setLon("0");

            dataSum = mControlTrackName + "-停留车-" + "(0,0)(0,0)";
            sendMessage(mConversationId, dataSum);
            data = "";
            dataSum = "";
        }
    }

    private void getNineteenNum() {
        mListInteger.clear();
        //找6道最大值最小值下标
        List<DataUser> dataUsers = mNineteenParkDataDao.find();
        int size = dataUsers.size();
        if (size > 1) {
            for (int i = 1; i < size; i++) {
                String lat = dataUsers.get(i).getLat();
                String lon = dataUsers.get(i).getLon();
                String ratioOfGpsPointCar = dataUsers.get(i).getRatioOfGpsPointCar();
                Integer integer = Integer.valueOf(ratioOfGpsPointCar);
                mListInteger.add(integer);
                if (lat.length() > 6 && lon.length() > 6) {
                    String lat1 = lat.substring(3, lat.length());
                    String lon1 = lon.substring(4, lon.length());
                    data += "(" + lat1 + "," + lon1 + ")";
                }
            }
            int minIndex = getMinIndex(mListInteger);
            int maxIndex = getMaxIndex(mListInteger);
            String minRatioOfGpsPointCar = dataUsers.get(minIndex + 1).getRatioOfGpsPointCar();
            String minLat = dataUsers.get(minIndex + 1).getLat();
            String minLon = dataUsers.get(minIndex + 1).getLon();
            String maxRatioOfGpsPointCar = dataUsers.get(maxIndex + 1).getRatioOfGpsPointCar();
            String maxLat = dataUsers.get(maxIndex + 1).getLat();
            String maxLon = dataUsers.get(maxIndex + 1).getLon();
            mNineteenPickLeft.setPosition(minRatioOfGpsPointCar + "");
            mNineteenPickLeft.setLat(minLat);
            mNineteenPickLeft.setLon(minLon);
            mNineteenPickRight.setPosition(maxRatioOfGpsPointCar + "");
            mNineteenPickRight.setLat(maxLat);
            mNineteenPickRight.setLon(maxLon);

            dataSum = mControlTrackName + "-停留车-" + data;
            sendMessage(mConversationId, dataSum);
            data = "";
            dataSum = "";
        } else {
            mNineteenPickLeft.setPosition("0");
            mNineteenPickLeft.setLat("0");
            mNineteenPickLeft.setLon("0");
            mNineteenPickRight.setPosition("0");
            mNineteenPickRight.setLat("0");
            mNineteenPickRight.setLon("0");

            dataSum = mControlTrackName + "-停留车-" + "(0,0)(0,0)";
            sendMessage(mConversationId, dataSum);
            data = "";
            dataSum = "";
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_point);
        setSystemUIVisible(false);

        initView();
        getCustom();
        addLoc();
        getSp();
        //if (mJump.equals("false")) {
        /*Intent intent = new Intent(PointActivity.this, ChannelActivity.class);
        startActivity(intent);*/
        //mJump = "true";
        //}

        mAppService = AppJoint.service(TestService.class);

        //mGpsDao = new GPSDao(getApplicationContext());
        //mGpsDao.add("36.659485000000004", "101.768762");


        SpUtil spUtil = new SpUtil(getApplicationContext(), "controluncaughtException");
        spUtil.setName("0");
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
        //发送机车、检测人员位置
        mHandler.postDelayed(mRunnable, 1500);

        /*mFiveDataDao = new FiveDataDao(getApplicationContext());
        mSixDataDao = new SixDataDao(getApplicationContext());
        mSevenDataDao = new SevenDataDao(getApplicationContext());
        mEightDataDao = new EightDataDao(getApplicationContext());
        mNineDataDao = new NineDataDao(getApplicationContext());*/

        /*mWanAsynTask = new WanAsynTask(getApplication());
        AssetsDatabaseManager db = AssetsDatabaseManager.getManager();
        mMDatabase = db.getDatabase("wandian.db");*/

        /*int getGudaoOfGpsPoint = GetGudaoOfGpsPoint(101.766023, 36.666956);
        //mJuli.setText(getGudaoOfGpsPoint + "");
        mRatioOfGpsTrackCar = String.valueOf(getGudaoOfGpsPoint);
        Point3d point3d = new Point3d();
        point3d.setX(101.766023);
        point3d.setY(36.666956);
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

        mStopcar.setTrack(getGudaoOfGpsPoint + "");
        mStopcar.setLat("101.766023");
        mStopcar.setLon("36.666956");
        mStopcar.setPosition(mGpsPistanceCar + "");
        mStopcar.setName("股道" + getGudaoOfGpsPoint + "纬度" + "101.766023" + "经度" + "36.666956" + "位置" + mGpsPistanceCar);

        ControlTranslation.proplrMove1(mControlMap, mTrain, mRatioOfGpsTrackCar, mGpsPistanceCar, transverse, disparity);*/

        //proplrMove();

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

    private void getCustom() {
        //6道
        mSixParkCar = new SixParkCar(PointActivity.this);
        //7道
        mSevenParkCar = new SevenParkCar(PointActivity.this);
        //9道
        mNineParkCar = new NineParkCar(PointActivity.this);
        //11道
        mElevenParkCar = new ElevenParkCar(PointActivity.this);
        //12道
        mTwelveParkCar = new TwelveParkCar(PointActivity.this);
        //13道
        mThirteenParkCar = new ThirteenParkCar(PointActivity.this);
        //14道
        mFourteenParkCar = new FourteenParkCar(PointActivity.this);
        //15道
        mFifteenParkCar = new FifteenParkCar(PointActivity.this);
        //16道
        mSixteenParkCar = new SixteenParkCar(PointActivity.this);
        //17道
        mSeventeenParkCar = new SeventeenParkCar(PointActivity.this);
        //18道
        mEighteenParkCar = new EighteenParkCar(PointActivity.this);
        //19道
        mNineteenParkCar = new NineteenParkCar(PointActivity.this);

        mRelative.addView(mSixParkCar);
        mRelative.addView(mSevenParkCar);
        mRelative.addView(mNineParkCar);
        mRelative.addView(mElevenParkCar);
        mRelative.addView(mTwelveParkCar);
        mRelative.addView(mThirteenParkCar);
        mRelative.addView(mFourteenParkCar);
        mRelative.addView(mFifteenParkCar);
        mRelative.addView(mSixteenParkCar);
        mRelative.addView(mSeventeenParkCar);
        mRelative.addView(mEighteenParkCar);
        mRelative.addView(mNineteenParkCar);

        mSixParkCar.invalidate();
        mSevenParkCar.invalidate();
        mNineParkCar.invalidate();
        mTwelveParkCar.invalidate();
        mThirteenParkCar.invalidate();
        mFourteenParkCar.invalidate();
        mFifteenParkCar.invalidate();
        mSixteenParkCar.invalidate();
        mSeventeenParkCar.invalidate();
        mEighteenParkCar.invalidate();
        mNineteenParkCar.invalidate();
    }

    private void getSp() {

        mControlTrack = new SpUtil(getApplicationContext(), "controltrack");
        mCopyCar = new SpUtil(getApplicationContext(), "copycar");
        //mControlTrack.setName(1 + "");
        //控制三个布局
        //站内图
        mMain = new SpUtil(getApplicationContext(), "main");

        mControlMap = new SpUtil(getApplicationContext(), "controlmap");

        mFirstInto = new SpUtil(getApplicationContext(), "firstinto");

        //dmr控制推进信令
        mAdvancedmr = new SpUtil(getApplicationContext(), "advancedmr");

        //1道停留车左点
        mOnePickLeft = new SpUtil(getApplicationContext(), "onepickleft");
        //1道停留车右点
        mOnepickright = new SpUtil(getApplicationContext(), "onepickright");
        //控制1道是否有停留车
        mControlOnePick = new SpUtil(getApplicationContext(), "controlonepick");

        mOneDataDao = new OneDataDao(getApplicationContext());

        //2道停留车左点
        mTwoPickLeft = new SpUtil(getApplicationContext(), "twopickleft");
        //2道停留车右点
        mTwopickright = new SpUtil(getApplicationContext(), "twopickright");
        //控制2道是否有停留车
        mControlTwoPick = new SpUtil(getApplicationContext(), "controltwopick");
        //3道停留车左点
        mThreePickLeft = new SpUtil(getApplicationContext(), "threepickleft");
        //3道停留车右点
        mThreepickright = new SpUtil(getApplicationContext(), "threepickright");
        //控制3道是否有停留车
        mControlThreePick = new SpUtil(getApplicationContext(), "controlthreepick");
        //4道停留车左点
        mFourPickLeft = new SpUtil(getApplicationContext(), "fourpickleft");
        //4道停留车右点
        mFourpickright = new SpUtil(getApplicationContext(), "fourpickright");
        //控制4道是否有停留车
        mControlFourPick = new SpUtil(getApplicationContext(), "controlfourpick");
        //5道停留车左点
        mFivePickLeft = new SpUtil(getApplicationContext(), "fivepickleft");
        //5道停留车右点
        mFivepickright = new SpUtil(getApplicationContext(), "fivepickright");
        //控制5道是否有停留车
        mControlFivePick = new SpUtil(getApplicationContext(), "controlfivepick");

        //6道停留车左点
        mSixPickLeft = new SpUtil(getApplicationContext(), "sixpickleft");
        //6道停留车右点
        mSixpickright = new SpUtil(getApplicationContext(), "sixpickright");
        //控制6道是否有停留车
        mControlSixPick = new SpUtil(getApplicationContext(), "controlsixpick");

        mSixParkDataDao = new SixParkDataDao(getApplicationContext());

        //7道停留车左点
        mSevenPickLeft = new SpUtil(getApplicationContext(), "sevenpickleft");
        //7道停留车右点
        mSevenpickright = new SpUtil(getApplicationContext(), "sevenpickright");
        //控制7道是否有停留车
        mControlSevenPick = new SpUtil(getApplicationContext(), "controlsevenpick");

        mSevenParkDataDao = new SevenParkDataDao(getApplicationContext());

        //8道停留车左点
        mEightPickLeft = new SpUtil(getApplicationContext(), "eightpickleft");
        //8道停留车右点
        mEightpickright = new SpUtil(getApplicationContext(), "eightpickright");
        //控制8道是否有停留车
        mControlEightPick = new SpUtil(getApplicationContext(), "controleightpick");

        //9道停留车左点
        mNinePickLeft = new SpUtil(getApplicationContext(), "ninepickleft");
        //9道停留车右点
        mNinepickright = new SpUtil(getApplicationContext(), "ninepickright");
        //控制9道是否有停留车
        mControlNinePick = new SpUtil(getApplicationContext(), "controlninepick");

        mNineParkDataDao = new NineParkDataDao(getApplicationContext());

        //10道停留车左点
        mTenPickLeft = new SpUtil(getApplicationContext(), "tenpickleft");
        //10道停留车右点
        mTenpickright = new SpUtil(getApplicationContext(), "tenpickright");
        //控制10道是否有停留车
        mControlTenPick = new SpUtil(getApplicationContext(), "controltenpick");

        //11道停留车左点
        mElevenpickleft = new SpUtil(getApplicationContext(), "elevenpickleft");
        //11道停留车右点
        mElevenpickright = new SpUtil(getApplicationContext(), "elevenpickright");
        //控制11道是否有停留车
        mControlElevenPick = new SpUtil(getApplicationContext(), "controlelevenpick");

        mElevenParkDataDao = new ElevenParkDataDao(getApplicationContext());

        //12道停留车左点
        mTwelvePickLeft = new SpUtil(getApplicationContext(), "twelvepickleft");
        //12道停留车右点
        mTwelvepickright = new SpUtil(getApplicationContext(), "twelvepickright");
        //控制12道是否有停留车
        mControlTwelvePick = new SpUtil(getApplicationContext(), "controltwelvepick");

        mTwelveParkDataDao = new TwelveParkDataDao(getApplicationContext());

        //13道停留车左点
        mThirteenPickLeft = new SpUtil(getApplicationContext(), "thirteenpickleft");
        //13道停留车右点
        mThirteenpickright = new SpUtil(getApplicationContext(), "thirteenpickright");
        //控制13道是否有停留车
        mControlThirteenPick = new SpUtil(getApplicationContext(), "controlthirteenpick");

        mThirteenParkDataDao = new ThirteenParkDataDao(getApplicationContext());

        //14道停留车左点
        mFourteenPickLeft = new SpUtil(getApplicationContext(), "fourteenpickleft");
        //14道停留车右点
        mFourteenpickright = new SpUtil(getApplicationContext(), "fourteenpickright");
        //控制14道是否有停留车
        mControlFourteenPick = new SpUtil(getApplicationContext(), "controlfourteenpick");

        mFourteenParkDataDao = new FourteenParkDataDao(getApplicationContext());

        //15道停留车左点
        mFifteenPickLeft = new SpUtil(getApplicationContext(), "fifteenpickleft");
        //15道停留车右点
        mFifteenPickRight = new SpUtil(getApplicationContext(), "fifteenpickright");
        //控制15道是否有停留车
        mControlFifteenPick = new SpUtil(getApplicationContext(), "controlfifteenpick");

        mFifteenParkDataDao = new FifteenParkDataDao(getApplicationContext());

        //16道停留车左点
        mSixteenPickLeft = new SpUtil(getApplicationContext(), "sixteenpickleft");
        //16道停留车右点
        mSixteenPickRight = new SpUtil(getApplicationContext(), "sixteenpickright");
        //控制16道是否有停留车
        mControlSixteenPick = new SpUtil(getApplicationContext(), "controlsixteenpick");

        mSixteenParkDataDao = new SixteenParkDataDao(getApplicationContext());

        //17道停留车左点
        mSeventeenPickLeft = new SpUtil(getApplicationContext(), "seventeenpickleft");
        //17道停留车右点
        mSeventeenPickRight = new SpUtil(getApplicationContext(), "seventeenpickright");
        //控制17道是否有停留车
        mControlSeventeenPick = new SpUtil(getApplicationContext(), "controlseventeenpick");

        mSeventeenParkDataDao = new SeventeenParkDataDao(getApplicationContext());

        //18道停留车左点
        mEighteenPickLeft = new SpUtil(getApplicationContext(), "eighteenpickleft");
        //18道停留车右点
        mEighteenPickRight = new SpUtil(getApplicationContext(), "eighteenpickright");
        //控制18道是否有停留车
        mControlEighteenPick = new SpUtil(getApplicationContext(), "controleighteenpick");

        mEighteenParkDataDao = new EighteenParkDataDao(getApplicationContext());

        //19道停留车左点
        mNineteenPickLeft = new SpUtil(getApplicationContext(), "nineteenpickleft");
        //19道停留车右点
        mNineteenPickRight = new SpUtil(getApplicationContext(), "nineteenpickright");
        //控制19道是否有停留车
        mControlNineteenPick = new SpUtil(getApplicationContext(), "controlnineteenpick");

        mNineteenParkDataDao = new NineteenParkDataDao(getApplicationContext());

        //领车
        mLeadcar = new SpUtil(getApplicationContext(), "leadcar");

        //停车后查看机车位置，为了绑定摘挂钩时候的人员位置与机车位置
        mStopcar = new SpUtil(getApplicationContext(), "stopcar");
        //查看机车位置
        mCarLocation = new SpUtil(getApplicationContext(), "carlocation");
        //mCarLocation.setName("0");
        mReceive1 = new SpUtil(getApplicationContext(), "receive1");
        mReceive2 = new SpUtil(getApplicationContext(), "receive2");
        mReceive3 = new SpUtil(getApplicationContext(), "receive3");
        qgs = new SpUtil(getApplicationContext(), "qgs");
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

        //控制人员显示
        mPeople0 = new SpUtil(getApplicationContext(), "people0");
        mPeople1 = new SpUtil(getApplicationContext(), "people1");
        mPeople2 = new SpUtil(getApplicationContext(), "people2");
        mPeople3 = new SpUtil(getApplicationContext(), "people3");
        mPeople4 = new SpUtil(getApplicationContext(), "people4");

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
        mCar = new SpUtil(getApplicationContext(), "car");
        /*mStopcar.setLat("000000");
        mStopcar.setLon("000000");
        mCar.setLat("000000");
        mCar.setLon("000000");*/
        mLeftCar = new SpUtil(getApplicationContext(), "leftcar");
        mRightCar = new SpUtil(getApplicationContext(), "rightcar");
        /*mOneDataDao.add("0", "0", "0", "0", "0");
        mStopcar.setLat("000000");
        mStopcar.setLon("000000");
        mMap1.setName("visible");
        mMap2.setName("gone");
        mMap3.setName("gone");
        mCar.setLat("000000");
        mCar.setLon("000000");
        mLeftCar.setName("000000");
        mRightCar.setName("000000");
        mControlTrack.setName("0");
        mMain.setName("main");
        mFirstInto.setName("false");
        mAdvancedmr.setName("0");
        mOnePickLeft.setPosition("0");
        mOnepickright.setPosition("0");
        mControlOnePick.setName("0");
        mTwoPickLeft.setPosition("0");
        mTwopickright.setPosition("0");
        mControlTwoPick.setName("0");
        mThreePickLeft.setPosition("0");
        mThreepickright.setPosition("0");
        mControlThreePick.setName("0");
        mFourPickLeft.setPosition("0");
        mFourpickright.setPosition("0");
        mControlFourPick.setName("0");
        mFivePickLeft.setPosition("0");
        mFivepickright.setPosition("0");
        mControlFivePick.setName("0");
        mSixPickLeft.setPosition("0");
        mSixpickright.setPosition("0");
        mControlSixPick.setName("0");
        mControlSixPick.setPosition("0");
        mSixParkDataDao.add("0", "0", "0", "0", "0");
        mSevenPickLeft.setPosition("0");
        mSevenpickright.setPosition("0");
        mControlSevenPick.setName("0");
        mSevenParkDataDao.add("0", "0", "0", "0", "0");
        mEightPickLeft.setPosition("0");
        mEightpickright.setPosition("0");
        mControlEightPick.setName("0");
        mNinePickLeft.setPosition("0");
        mNinepickright.setPosition("0");
        mControlNinePick.setName("0");
        mNineParkDataDao.add("0", "0", "0", "0", "0");
        mTenPickLeft.setPosition("0");
        mTenpickright.setPosition("0");
        mControlTenPick.setName("0");
        mElevenpickleft.setPosition("0");
        mElevenpickright.setPosition("0");
        mControlElevenPick.setName("0");
        mElevenParkDataDao.add("0", "0", "0", "0", "0");
        mTwelvePickLeft.setPosition("0");
        mTwelvepickright.setPosition("0");
        mControlTwelvePick.setName("0");
        mTwelveParkDataDao.add("0", "0", "0", "0", "0");
        mThirteenPickLeft.setPosition("0");
        mThirteenpickright.setPosition("0");
        mControlThirteenPick.setName("0");
        mThirteenParkDataDao.add("0", "0", "0", "0", "0");
        mFourteenPickLeft.setPosition("0");
        mFourteenpickright.setPosition("0");
        mControlFourteenPick.setName("0");
        mFourteenParkDataDao.add("0", "0", "0", "0", "0");
        mFifteenPickLeft.setPosition("0");
        mFifteenPickRight.setPosition("0");
        mControlFifteenPick.setName("0");
        mFifteenParkDataDao.add("0", "0", "0", "0", "0");
        mSixteenPickLeft.setPosition("0");
        mSixteenPickRight.setPosition("0");
        mControlSixteenPick.setName("0");
        mSixteenParkDataDao.add("0", "0", "0", "0", "0");
        mSeventeenPickLeft.setPosition("0");
        mSeventeenPickRight.setPosition("0");
        mControlSeventeenPick.setName("0");
        mSeventeenParkDataDao.add("0", "0", "0", "0", "0");
        mEighteenPickLeft.setPosition("0");
        mEighteenPickRight.setPosition("0");
        mControlEighteenPick.setName("0");
        mEighteenParkDataDao.add("0", "0", "0", "0", "0");
        mNineteenPickLeft.setPosition("0");
        mNineteenPickRight.setPosition("0");
        mControlNineteenPick.setName("0");
        mNineteenParkDataDao.add("0", "0", "0", "0", "0");*/
    }

    private void initView() {
        mRelative = findViewById(R.id.relativepoint);
        mDelete = findViewById(R.id.delete_btn);
        mXiningbeimap = findViewById(R.id.xiningbeimap);
        mChangfengmap = findViewById(R.id.changfengmap);
        mOneparkcar = findViewById(R.id.oneparkcar);
        mTwoparkcar = findViewById(R.id.twoparkcar);
        mThreeparkcar = findViewById(R.id.threeparkcar);
        mFourparkcar = findViewById(R.id.fourparkcar);
        mFiveparkcar = findViewById(R.id.fiveparkcar);
        mEightparkcar = findViewById(R.id.eightparkcar);
        mTenparkcar = findViewById(R.id.tenparkcar);
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

        mBtn.setText("平调系统");
        mDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //mPickDao.del("zhaigouGPS");
                //mGpsDao.del("Gps");
                /*mFiveDataDao.del("fiveperson");
                mSixDataDao.del("sixperson");
                mSevenDataDao.del("sevenperson");
                mEightDataDao.del("eightperson");
                mNineDataDao.del("nineperson");*/
            }
        });
    }

    private void oneLeft(String mGpsPistance2, String mLat21, String mLon21, String mGetGudaoOfGpsPoint2) {
        mOnePickLeft.setPosition(mGpsPistance2);
        mOnePickLeft.setLon(mLat21);
        mOnePickLeft.setLat(mLon21);
        mOnePickLeft.setTrack(mGetGudaoOfGpsPoint2);
    }

    private void oneRight(String mGpsPistance2, String mLat21, String mLon21, String mGetGudaoOfGpsPoint2) {
        mOnepickright.setPosition(mGpsPistance2);
        mOnepickright.setLon(mLat21);
        mOnepickright.setLat(mLon21);
        mOnepickright.setTrack(mGetGudaoOfGpsPoint2);
    }

    private void twoLeft(String mGpsPistance2, String mLat21, String mLon21, String mGetGudaoOfGpsPoint2) {
        mTwoPickLeft.setPosition(mGpsPistance2);
        mTwoPickLeft.setLon(mLat21);
        mTwoPickLeft.setLat(mLon21);
        mTwoPickLeft.setTrack(mGetGudaoOfGpsPoint2);
    }

    private void twoRight(String mGpsPistance2, String mLat21, String mLon21, String mGetGudaoOfGpsPoint2) {
        mTwopickright.setPosition(mGpsPistance2);
        mTwopickright.setLon(mLat21);
        mTwopickright.setLat(mLon21);
        mTwopickright.setTrack(mGetGudaoOfGpsPoint2);
    }

    private void threeLeft(String mGpsPistance2, String mLat21, String mLon21, String mGetGudaoOfGpsPoint2) {
        mThreePickLeft.setPosition(mGpsPistance2);
        mThreePickLeft.setLon(mLat21);
        mThreePickLeft.setLat(mLon21);
        mThreePickLeft.setTrack(mGetGudaoOfGpsPoint2);
    }

    private void threeRight(String mGpsPistance2, String mLat21, String mLon21, String mGetGudaoOfGpsPoint2) {
        mThreepickright.setPosition(mGpsPistance2);
        mThreepickright.setLon(mLat21);
        mThreepickright.setLat(mLon21);
        mThreepickright.setTrack(mGetGudaoOfGpsPoint2);
    }

    private void fourLeft(String mGpsPistance2, String mLat21, String mLon21, String mGetGudaoOfGpsPoint2) {
        mFourPickLeft.setPosition(mGpsPistance2);
        mFourPickLeft.setLon(mLat21);
        mFourPickLeft.setLat(mLon21);
        mFourPickLeft.setTrack(mGetGudaoOfGpsPoint2);
    }

    private void fourRight(String mGpsPistance2, String mLat21, String mLon21, String mGetGudaoOfGpsPoint2) {
        mFourpickright.setPosition(mGpsPistance2);
        mFourpickright.setLon(mLat21);
        mFourpickright.setLat(mLon21);
        mFourpickright.setTrack(mGetGudaoOfGpsPoint2);
    }

    private void fiveLeft(String mGpsPistance2, String mLat21, String mLon21, String mGetGudaoOfGpsPoint2) {
        mFivePickLeft.setPosition(mGpsPistance2);
        mFivePickLeft.setLon(mLat21);
        mFivePickLeft.setLat(mLon21);
        mFivePickLeft.setTrack(mGetGudaoOfGpsPoint2);
    }

    private void fiveRight(String mGpsPistance2, String mLat21, String mLon21, String mGetGudaoOfGpsPoint2) {
        mFivepickright.setPosition(mGpsPistance2);
        mFivepickright.setLon(mLat21);
        mFivepickright.setLat(mLon21);
        mFivepickright.setTrack(mGetGudaoOfGpsPoint2);
    }

    private void eightLeft(String mGpsPistance2, String mLat21, String mLon21, String mGetGudaoOfGpsPoint2) {
        mEightPickLeft.setPosition(mGpsPistance2);
        mEightPickLeft.setLon(mLat21);
        mEightPickLeft.setLat(mLon21);
        mEightPickLeft.setTrack(mGetGudaoOfGpsPoint2);
    }

    private void eightRight(String mGpsPistance2, String mLat21, String mLon21, String mGetGudaoOfGpsPoint2) {
        mEightpickright.setPosition(mGpsPistance2);
        mEightpickright.setLon(mLat21);
        mEightpickright.setLat(mLon21);
        mEightpickright.setTrack(mGetGudaoOfGpsPoint2);
    }

    private void tenLeft(String mGpsPistance2, String mLat21, String mLon21, String mGetGudaoOfGpsPoint2) {
        mTenPickLeft.setPosition(mGpsPistance2);
        mTenPickLeft.setLon(mLat21);
        mTenPickLeft.setLat(mLon21);
        mTenPickLeft.setTrack(mGetGudaoOfGpsPoint2);
    }

    private void tenRight(String mGpsPistance2, String mLat21, String mLon21, String mGetGudaoOfGpsPoint2) {
        mTenpickright.setPosition(mGpsPistance2);
        mTenpickright.setLon(mLat21);
        mTenpickright.setLat(mLon21);
        mTenpickright.setTrack(mGetGudaoOfGpsPoint2);
    }

    private void sixPerson() {
        mLat6 = mPeople6.getLat();
        mLon6 = mPeople6.getLon();
        /*List<PersonDataUser> personDataUsers1 = mSixDataDao.find();
        int size2 = personDataUsers1.size();
        mLat21 = personDataUsers1.get(size2 - 1).getLat();
        mLon21 = personDataUsers1.get(size2 - 1).getLon();
        mLatStopCar01 = Double.valueOf(mLat21);
        mLonStopCar01 = Double.valueOf(mLon21);*/

        mLat61 = mPeople6.getLat1();
        mLon61 = mPeople6.getLon1();
        /*String total2 = "01-摘钩GPS-" + mLat61 + "-" + mLon61;
        sendMessage(mConversationId, total2);*/

        mLatSp = Double.valueOf(mLat6);
        mLonSp = Double.valueOf(mLon6);

        //获取股道号
        mGetGudaoOfGpsPoint2 = GetGudaoOfGpsPoint(mLonSp, mLatSp);
        //mJuli.setText(getGudaoOfGpsPoint + "");
        mRatioOfGpsTrackCar2 = String.valueOf(mGetGudaoOfGpsPoint2);
        Point3d point3d2 = new Point3d();
        point3d2.setX(mLonSp);
        point3d2.setY(mLatSp);
        //获取位置
        mGetRatioOfGpsPointCar2 = GetRatioOfGpsPoint(point3d2, mGetGudaoOfGpsPoint2);
        DecimalFormat df12 = new DecimalFormat("#####0.00%");
        DecimalFormatSymbols symbols12 = new DecimalFormatSymbols();
        df12.setDecimalFormatSymbols(symbols12);
        String ratioOfGpsPoint2 = df12.format(mGetRatioOfGpsPointCar2);
        mGpsPoint2 = ratioOfGpsPoint2.substring(0, ratioOfGpsPoint2.indexOf("."));
        mGpsPistance2 = Double.valueOf(mGpsPoint2);
        Log.e("秦广帅", mGpsPoint2);
        Log.e("秦广帅", mGpsPistance2 + "");
    }

    private void sevenPerson() {
        String lat = mPeople7.getLat();
        String lon = mPeople7.getLon();
        /*List<PersonDataUser> personDataUsers2 = mSevenDataDao.find();
        int size3 = personDataUsers2.size();
        mLat31 = personDataUsers2.get(size3 - 1).getLat();
        mLon31 = personDataUsers2.get(size3 - 1).getLon();
        mLatStopCar02 = Double.valueOf(mLat31);
        mLonStopCar02 = Double.valueOf(mLon31);*/
        mLat61 = mPeople7.getLat1();
        mLon61 = mPeople7.getLon1();
        /*String total2 = "02-摘钩GPS-" + mLat61 + "-" + mLon61;
        sendMessage(mConversationId, total2);*/

        mLatSp = Double.valueOf(lat);
        mLonSp = Double.valueOf(lon);

        //获取股道号
        //mGetGudaoOfGpsPoint3 = GetGudaoOfGpsPoint(mLonStopCar02, mLatStopCar02);
        mGetGudaoOfGpsPoint3 = GetGudaoOfGpsPoint(mLonSp, mLatSp);
        //mJuli.setText(getGudaoOfGpsPoint + "");
        mRatioOfGpsTrackCar3 = String.valueOf(mGetGudaoOfGpsPoint3);
        Point3d point3d3 = new Point3d();
        point3d3.setX(mLonStopCar02);
        point3d3.setY(mLatStopCar02);
        //获取位置
        mGetRatioOfGpsPointCar3 = GetRatioOfGpsPoint(point3d3, mGetGudaoOfGpsPoint3);
        DecimalFormat df12 = new DecimalFormat("#####0.00%");
        DecimalFormatSymbols symbols12 = new DecimalFormatSymbols();
        df12.setDecimalFormatSymbols(symbols12);
        String ratioOfGpsPoint2 = df12.format(mGetRatioOfGpsPointCar3);
        mGpsPoint2 = ratioOfGpsPoint2.substring(0, ratioOfGpsPoint2.indexOf("."));
        mGpsPistance2 = Double.valueOf(mGpsPoint2);
    }

    private void eightPerson() {
        String lat = mPeople8.getLat();
        String lon = mPeople8.getLon();
        /*List<PersonDataUser> personDataUsers3 = mEightDataDao.find();
        int size4 = personDataUsers3.size();
        mLat4 = personDataUsers3.get(size4 - 1).getLat();
        mLon4 = personDataUsers3.get(size4 - 1).getLon();
        mLatStopCar03 = Double.valueOf(mLat4);
        mLonStopCar03 = Double.valueOf(mLon4);*/
        mLat61 = mPeople8.getLat1();
        mLon61 = mPeople8.getLon1();
        /*String total2 = "03-摘钩GPS-" + mLat61 + "-" + mLon61;
        sendMessage(mConversationId, total2);*/

        mLatSp = Double.valueOf(lat);
        mLonSp = Double.valueOf(lon);

        //获取股道号
        //mGetGudaoOfGpsPoint4 = GetGudaoOfGpsPoint(mLonStopCar03, mLatStopCar03);
        mGetGudaoOfGpsPoint4 = GetGudaoOfGpsPoint(mLonSp, mLatSp);
        //mJuli.setText(getGudaoOfGpsPoint + "");
        mRatioOfGpsTrackCar4 = String.valueOf(mGetGudaoOfGpsPoint4);
        Point3d point3d4 = new Point3d();
        point3d4.setX(mLonStopCar03);
        point3d4.setY(mLatStopCar03);
        //获取位置
        mGetRatioOfGpsPointCar4 = GetRatioOfGpsPoint(point3d4, mGetGudaoOfGpsPoint4);
        DecimalFormat df12 = new DecimalFormat("#####0.00%");
        DecimalFormatSymbols symbols12 = new DecimalFormatSymbols();
        df12.setDecimalFormatSymbols(symbols12);
        String ratioOfGpsPoint2 = df12.format(mGetRatioOfGpsPointCar4);
        mGpsPoint2 = ratioOfGpsPoint2.substring(0, ratioOfGpsPoint2.indexOf("."));
        mGpsPistance2 = Double.valueOf(mGpsPoint2);
    }

    private void ninePerson() {
        String lat = mPeople9.getLat();
        String lon = mPeople9.getLon();
        /*List<PersonDataUser> personDataUsers4 = mNineDataDao.find();
        int size5 = personDataUsers4.size();
        mLat5 = personDataUsers4.get(size5 - 1).getLat();
        mLon5 = personDataUsers4.get(size5 - 1).getLon();
        mLatStopCar04 = Double.valueOf(mLat5);
        mLonStopCar04 = Double.valueOf(mLon5);*/
        mLat61 = mPeople9.getLat1();
        mLon61 = mPeople9.getLon1();
        /*String total2 = "04-摘钩GPS-" + mLat61 + "-" + mLon61;
        sendMessage(mConversationId, total2);*/

        mLatSp = Double.valueOf(lat);
        mLonSp = Double.valueOf(lon);

        //获取股道号
        //mGetGudaoOfGpsPoint5 = GetGudaoOfGpsPoint(mLonStopCar04, mLatStopCar04);
        mGetGudaoOfGpsPoint5 = GetGudaoOfGpsPoint(mLonSp, mLatSp);
        //mJuli.setText(getGudaoOfGpsPoint + "");
        mRatioOfGpsTrackCar5 = String.valueOf(mGetGudaoOfGpsPoint5);
        Point3d point3d5 = new Point3d();
        point3d5.setX(mLonStopCar04);
        point3d5.setY(mLatStopCar04);
        //获取位置
        mGetRatioOfGpsPointCar5 = GetRatioOfGpsPoint(point3d5, mGetGudaoOfGpsPoint5);
        DecimalFormat df12 = new DecimalFormat("#####0.00%");
        DecimalFormatSymbols symbols12 = new DecimalFormatSymbols();
        df12.setDecimalFormatSymbols(symbols12);
        String ratioOfGpsPoint2 = df12.format(mGetRatioOfGpsPointCar5);
        mGpsPoint2 = ratioOfGpsPoint2.substring(0, ratioOfGpsPoint2.indexOf("."));
        mGpsPistance2 = Double.valueOf(mGpsPoint2);
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

            String carLat = mStopcar.getLat();
            String carLon = mStopcar.getLon();
            String mLeftCarName = mLeftCar.getName();
            String mRightCarName = mRightCar.getName();
            if (carLat != null && carLon != null) {
                DecimalFormat df = new DecimalFormat("#.000000");
                String lat1 = df.format(Double.valueOf(carLat)).substring(df.format(Double.valueOf(carLat)).indexOf(".") + 1);
                String lon1 = df.format(Double.valueOf(carLon)).substring(df.format(Double.valueOf(carLon)).indexOf(".") + 1);
                String total = "0A-机车GPS-" + lat1 + "-" + lon1;
                sendMessage(mConversationId, total);
                Log.i("秦广帅1000", total);//0A-机车GPS-667636-768050
            }
            mTrain.invalidate();

            /*String carName = mCopyCar.getName();
            if (carName != null) {
                switch (carName) {
                    case "true":
                        String lat = mCopyCar.getLat();
                        String lon = mCopyCar.getLon();
                        Double lat1 = Double.valueOf(lat);
                        Double lon1 = Double.valueOf(lon);

                        int getGudaoOfGpsPoint = GetGudaoOfGpsPoint(lon1, lat1);
                        //mJuli.setText(getGudaoOfGpsPoint + "");
                        mRatioOfGpsTrackCar1 = String.valueOf(getGudaoOfGpsPoint);
                        Point3d point3d = new Point3d();
                        point3d.setX(lon1);
                        point3d.setY(lat1);
                        double getRatioOfGpsPoint = GetRatioOfGpsPoint(point3d, getGudaoOfGpsPoint);
                        DecimalFormat df1 = new DecimalFormat("#####0.00%");
                        DecimalFormatSymbols symbols1 = new DecimalFormatSymbols();
                        df1.setDecimalFormatSymbols(symbols1);
                        String ratioOfGpsPoint = df1.format(getRatioOfGpsPoint);
                        String gpsPoint = ratioOfGpsPoint.substring(0, ratioOfGpsPoint.indexOf("."));
                        mGpsPistanceCar1 = Double.valueOf(gpsPoint);
                        //mJwd.setText(ratioOfGpsPoint + "");
                        //mLat1.setText(getRatioOfGpsPoint + "");
                        //mLat2.setText(gpsPoint);
                        //proplrMove0();
                        ControlTranslation.proplrMove1(mControlMap, mTrain, mRatioOfGpsTrackCar1, mGpsPistanceCar1, transverse1, disparity);
                        break;
                }

                mMControlMapName = mControlMap.getName();
                if (mMControlMapName.equals("zheng")) {
                    mXiningbeimap.setVisibility(View.VISIBLE);
                    mChangfengmap.setVisibility(View.GONE);
                    mBailimap.setVisibility(View.GONE);
                    mMain.setName("main");
                } else if (mMControlMapName.equals("cf")) {
                    mChangfengmap.setVisibility(View.VISIBLE);
                    mXiningbeimap.setVisibility(View.GONE);
                    mBailimap.setVisibility(View.GONE);
                    mMain.setName("changfeng");
                } else if (mMControlMapName.equals("bl")) {
                    mBailimap.setVisibility(View.VISIBLE);
                    mChangfengmap.setVisibility(View.GONE);
                    mXiningbeimap.setVisibility(View.GONE);
                    mMain.setName("baili");
                }
            }*/

            //调车长
            String name = mPeople5.getName();
            if (name != null) {
                switch (name) {
                    case "true":
                        /*FiveDataDao fiveDataDao = new FiveDataDao(getApplicationContext());
                        List<PersonDataUser> fiveDataUsers = fiveDataDao.find();
                        int size0 = fiveDataUsers.size();
                        String lat = fiveDataUsers.get(size0 - 1).getLat();
                        String lon = fiveDataUsers.get(size0 - 1).getLon();
                        Double lat1 = Double.valueOf(lat);
                        Double lon1 = Double.valueOf(lon);*/

                        String lat = mPeople5.getLat();
                        String lon = mPeople5.getLon();
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
                        //proplrMove0();
                        ControlTranslation.proplrMove1(mPeople0, mTransferpeople, mRatioOfGpsTrack, mGpsPistance, transverse1, disparity);
                        mMControlMapName = mControlMap.getName();
                        if (mPeople0.getName().equals(mMControlMapName)) {
                            mTransferpeople.setVisibility(View.VISIBLE);
                        } else {
                            mTransferpeople.setVisibility(View.GONE);
                        }
                        break;
                }
            }

            //制动员6
            String name21 = mPeople6.getName();
            if (name21 != null) {
                switch (name21) {
                    case "true":
                        /*SixDataDao sixDataDao = new SixDataDao(getApplicationContext());
                        List<PersonDataUser> sixDataUsers = sixDataDao.find();
                        int size1 = sixDataUsers.size();
                        String lat2 = sixDataUsers.get(size1 - 1).getLat();
                        String lon2 = sixDataUsers.get(size1 - 1).getLon();
                        Double lat12 = Double.valueOf(lat2);
                        Double lon12 = Double.valueOf(lon2);*/

                        String lat = mPeople6.getLat();
                        String lon = mPeople6.getLon();
                        Double lat12 = Double.valueOf(lat);
                        Double lon12 = Double.valueOf(lon);

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
                        Log.e("秦广帅", mRatioOfGpsTrack2 + "    " + mGpsPistance2 + "    ");
                        //proplrMove1();
                        ControlTranslation.proplrMove1(mPeople1, mPeopleOne, mRatioOfGpsTrack2, mGpsPistance2, transverse1, disparity);
                        mMControlMapName = mControlMap.getName();
                        if (mPeople1.getName().equals(mMControlMapName)) {
                            mPeopleOne.setVisibility(View.VISIBLE);
                        } else {
                            mPeopleOne.setVisibility(View.GONE);
                        }
                        break;
                }
            }

            //制动员7
            String name31 = mPeople7.getName();
            if (name31 != null) {
                switch (name31) {
                    case "true":
                        /*SevenDataDao sevenDataDao = new SevenDataDao(getApplicationContext());
                        List<PersonDataUser> sevenDataUsers = sevenDataDao.find();
                        int size1 = sevenDataUsers.size();
                        String lat2 = sevenDataUsers.get(size1 - 1).getLat();
                        String lon2 = sevenDataUsers.get(size1 - 1).getLon();
                        Double lat12 = Double.valueOf(lat2);
                        Double lon12 = Double.valueOf(lon2);*/
                        String lat = mPeople7.getLat();
                        String lon = mPeople7.getLon();
                        Double lat12 = Double.valueOf(lat);
                        Double lon12 = Double.valueOf(lon);

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
                        //proplrMove2();
                        ControlTranslation.proplrMove1(mPeople2, mPeopletwo, mRatioOfGpsTrack3, mGpsPistance3, transverse1, disparity);
                        mMControlMapName = mControlMap.getName();
                        if (mPeople2.getName().equals(mMControlMapName)) {
                            mPeopletwo.setVisibility(View.VISIBLE);
                        } else {
                            mPeopletwo.setVisibility(View.GONE);
                        }
                        break;
                }
            }

            //制动员8
            String name4 = mPeople8.getName();
            if (name4 != null) {
                switch (name4) {
                    case "true":
                        /*EightDataDao eightDataDao = new EightDataDao(getApplicationContext());
                        List<PersonDataUser> eightDataUsers = eightDataDao.find();
                        int size1 = eightDataUsers.size();
                        String lat2 = eightDataUsers.get(size1 - 1).getLat();
                        String lon2 = eightDataUsers.get(size1 - 1).getLon();
                        Double lat12 = Double.valueOf(lat2);
                        Double lon12 = Double.valueOf(lon2);*/

                        String lat = mPeople8.getLat();
                        String lon = mPeople8.getLon();
                        Double lat12 = Double.valueOf(lat);
                        Double lon12 = Double.valueOf(lon);

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
                        //proplrMove3();
                        ControlTranslation.proplrMove1(mPeople3, mPeoplethree, mRatioOfGpsTrack4, mGpsPistance4, transverse1, disparity);
                        mMControlMapName = mControlMap.getName();
                        if (mPeople3.getName().equals(mMControlMapName)) {
                            mPeoplethree.setVisibility(View.VISIBLE);
                        } else {
                            mPeoplethree.setVisibility(View.GONE);
                        }
                        break;
                }
            }

            //制动员9
            String name5 = mPeople9.getName();
            if (name5 != null) {
                switch (name5) {
                    case "true":
                        /*NineDataDao nineDataDao = new NineDataDao(getApplicationContext());
                        List<PersonDataUser> nineDataUsers = nineDataDao.find();
                        int size1 = nineDataUsers.size();
                        String lat2 = nineDataUsers.get(size1 - 1).getLat();
                        String lon2 = nineDataUsers.get(size1 - 1).getLon();
                        Double lat12 = Double.valueOf(lat2);
                        Double lon12 = Double.valueOf(lon2);*/

                        String lat = mPeople9.getLat();
                        String lon = mPeople9.getLon();
                        Double lat12 = Double.valueOf(lat);
                        Double lon12 = Double.valueOf(lon);

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
                        //proplrMove4();
                        ControlTranslation.proplrMove1(mPeople4, mPeoplefour, mRatioOfGpsTrack5, mGpsPistance5, transverse1, disparity);
                        mMControlMapName = mControlMap.getName();
                        if (mPeople4.getName().equals(mMControlMapName)) {
                            mPeoplefour.setVisibility(View.VISIBLE);
                        } else {
                            mPeoplefour.setVisibility(View.GONE);
                        }
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

        /*FiveParkDataDao fiveDataDao = new FiveParkDataDao(getApplicationContext());
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
                //Log.i(TAG, "dd：" + dd);
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
        //Log.i(TAG, "Point3d：" + p.X);
        //Log.i(TAG, "Point3d：" + p.Y);
        //Log.i(TAG, "gd：" + gd);
        double r = 0d;
        double A, B, C, x, y, x1, x2;
        if (gd >= 0) {
            Point3d p1 = new Point3d();
            Point3d p2 = new Point3d();
            mGuDao = gd;
            int size = gps.get(mGuDao).size();
            p1 = gps.get(mGuDao).get(0);
            p2 = gps.get(mGuDao).get(size - 1);
            //Log.i(TAG, "size秦广帅：" + size);
            //Log.i(TAG, "p1秦广帅：" + p1.X);
            //Log.i(TAG, "p2秦广帅：" + p2.X);
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
            //Log.i(TAG, "x秦广帅：" + aDouble);
            x1 = gps.get(mGuDao).get(0).X;
            x2 = gps.get(mGuDao).get(size - 1).X;
            //Log.i(TAG, "x1秦广帅：" + x1);
            //Log.i(TAG, "x2秦广帅：" + x2);
            //x1 = listgpspt[gd][0].X;
            //x2 = listgpspt[gd][listgpspt[gd].Count - 1].X;
            //r = (x1 - x) / (x1 - x2);
            r = (aDouble - x2) / (x1 - x2);
            //Log.i(TAG, "r：" + r);
            //n = (int)Math.Round(r * 100);
        }
        return r;
    }

    //获取最小值下标
    private int getMinIndex(List<Integer> mList) {
        int min = mList.get(0);
        int index = 0;
        for (int i = 0; i < mList.size(); i++) {
            if (min > mList.get(i)) {
                min = mList.get(i);
                index = i;
            }
        }
        return index;
    }

    //获取最小值
    private int getMin(List<Integer> mList) {
        int min = mList.get(0);
        for (int i = 0; i < mList.size(); i++) {
            if (min > mList.get(i)) {
                min = mList.get(i);
            }
        }
        return min;
    }

    //获取最大值下标
    private int getMaxIndex(List<Integer> mList) {
        int max = mList.get(0);
        int index = 0;
        for (int i = 0; i < mList.size(); i++) {
            if (max < mList.get(i)) {
                max = mList.get(i);
                index = i;
            }
        }
        return index;
    }

    //获取最大值
    private int getMax(List<Integer> mList) {
        int max = mList.get(0);
        for (int i = 0; i < mList.size(); i++) {
            if (max > mList.get(i)) {
                max = mList.get(i);
            }
        }
        return max;
    }

    private void one() {
        mOneTrackLeft = mOnePickLeft.getTrack();
        mOneLatLeft = mOnePickLeft.getLat();
        mOneLonLeft = mOnePickLeft.getLon();
        mOnePositionLeft = mOnePickLeft.getPosition();
        mOneTrackRight = mOnepickright.getTrack();
        mOneLatRight = mOnepickright.getLat();
        mOneLonRight = mOnepickright.getLon();
        mOnePositionRight = mOnepickright.getPosition();
    }

    private void two() {
        mTwoTrackLeft = mTwoPickLeft.getTrack();
        mTwoLatLeft = mTwoPickLeft.getLat();
        mTwoLonLeft = mTwoPickLeft.getLon();
        mTwoPositionLeft = mTwoPickLeft.getPosition();
        mTwoTrackRight = mTwopickright.getTrack();
        mTwoLatRight = mTwopickright.getLat();
        mTwoLonRight = mTwopickright.getLon();
        mTwoPositionRight = mTwopickright.getPosition();
    }

    private void three() {
        mThreeTrackLeft = mThreePickLeft.getTrack();
        mThreeLatLeft = mThreePickLeft.getLat();
        mThreeLonLeft = mThreePickLeft.getLon();
        mThreePositionLeft = mThreePickLeft.getPosition();
        mThreeTrackRight = mThreepickright.getTrack();
        mThreeLatRight = mThreepickright.getLat();
        mThreeLonRight = mThreepickright.getLon();
        mThreePositionRight = mThreepickright.getPosition();
    }

    private void four() {
        mFourTrackLeft = mFourPickLeft.getTrack();
        mFourLatLeft = mFourPickLeft.getLat();
        mFourLonLeft = mFourPickLeft.getLon();
        mFourPositionLeft = mFourPickLeft.getPosition();
        mFourTrackRight = mFourpickright.getTrack();
        mFourLatRight = mFourpickright.getLat();
        mFourLonRight = mFourpickright.getLon();
        mFourPositionRight = mFourpickright.getPosition();
    }

    private void five() {
        mFiveTrackLeft = mFivePickLeft.getTrack();
        mFiveLatLeft = mFivePickLeft.getLat();
        mFiveLonLeft = mFivePickLeft.getLon();
        mFivePositionLeft = mFivePickLeft.getPosition();
        mFiveTrackRight = mFivepickright.getTrack();
        mFiveLatRight = mFivepickright.getLat();
        mFiveLonRight = mFivepickright.getLon();
        mFivePositionRight = mFivepickright.getPosition();
    }

    private void tuijin() {
        mListInteger.clear();
        mListNum.clear();
        mAdvancedmr.setName("true");
        mControlTrackName = mControlTrack.getName();
        Log.e("秦广帅name", mControlTrackName);
        isSixTrack = true;
        switch (mControlTrackName) {
            case "1":
                //查看1道保存的数据是否只有挂钩data485.indexOf
                List<DataUser> oneDataUsers = mOneDataDao.find();
                int size = oneDataUsers.size();
                String controlonepickrightName43 = mControlOnePick.getName();
                if (controlonepickrightName43.indexOf("摘钩") == -1) {
                    mOneDataDao.delete("oneparkcar", 1);
                    mOneDataDao.delete("oneparkcar", 2);
                    oneLeft("0", "0", "0", "0");
                    oneRight("0", "0", "0", "0");
                } else {
                    if (size > 1) {
                        for (int i = 1; i < size; i++) {
                            String ratioOfGpsPointCar = oneDataUsers.get(i).getRatioOfGpsPointCar();
                            Integer integerRatioOfGpsPointCar = Integer.valueOf(ratioOfGpsPointCar);
                            mListInteger.add(integerRatioOfGpsPointCar);
                        }

                        int maxIndex = getMaxIndex(mListInteger);
                        int minIndex = getMinIndex(mListInteger);
                        String maxRatioOfGpsPointCar = oneDataUsers.get(maxIndex + 1).getRatioOfGpsPointCar();
                        String maxLat = oneDataUsers.get(maxIndex + 1).getLat();
                        String maxLon = oneDataUsers.get(maxIndex + 1).getLon();
                        String maxGd = oneDataUsers.get(maxIndex + 1).getGd();
                        String minRatioOfGpsPointCar = oneDataUsers.get(minIndex + 1).getRatioOfGpsPointCar();
                        String minLat = oneDataUsers.get(minIndex + 1).getLat();
                        String minLon = oneDataUsers.get(minIndex + 1).getLon();
                        String minGd = oneDataUsers.get(minIndex + 1).getGd();
                        Log.e("秦广帅Index", maxIndex + "    " + minIndex);
                        Log.e("秦广帅max", maxRatioOfGpsPointCar + "    " + maxLat + "    " + maxLon + "    " + maxGd);
                        Log.e("秦广帅min", minRatioOfGpsPointCar + "    " + minLat + "    " + minLon + "    " + minGd);
                    }
                }
                /*String leftOnePosition = mOnePickLeft.getPosition();
                String leftOneLat = mOnePickLeft.getLat();
                String leftOneLon = mOnePickLeft.getLon();
                String rightOnePosition = mOnepickright.getPosition();
                String rightOneLat = mOnepickright.getLat();
                String rightOneLon = mOnepickright.getLon();*/

                one();
                if (mOneLatLeft.length() > 6 && mOneLonLeft.length() > 6 && mOneLatRight.length() > 6 && mOneLonRight.length() > 6) {
                    String lat = mOneLatLeft.substring(4, mOneLatLeft.length());
                    String lon = mOneLonLeft.substring(3, mOneLonLeft.length());
                    String lat1 = mOneLatRight.substring(4, mOneLatRight.length());
                    String lon1 = mOneLonRight.substring(3, mOneLonRight.length());
                    String textOneName = "0" + mControlTrackName + "-停留车-" + "(" + lon + "," + lat + ")" + "(" + lon1 + "," + lat1 + ")";
                    sendMessage(mConversationId, textOneName);
                } else if (mOneLatLeft.length() == 1 && mOneLonLeft.length() == 1 && mOneLatRight.length() == 1 && mOneLonRight.length() == 1) {
                    String textOneName = "0" + mControlTrackName + "-停留车-" + "(" + "000000" + "," + "000000" + ")" + "(" + "000000" + "," + "000000" + ")";
                    sendMessage(mConversationId, textOneName);
                }

                mControlOnePick.setName("0");
                mOneparkcar.invalidate();
                break;
            case "2":
                //查看2道保存的数据是否只有挂钩
                String controltwopickrightName43 = mControlTwoPick.getName();
                if (controltwopickrightName43.indexOf("摘钩") == -1) {
                    twoLeft("0", "0", "0", "0");
                    twoRight("0", "0", "0", "0");
                }
                /*String leftTwoPosition = mTwoPickLeft.getPosition();
                String leftTwoLat = mTwoPickLeft.getLat();
                String leftTwoLon = mTwoPickLeft.getLon();
                String rightTwoPosition = mTwopickright.getPosition();
                String rightTwoLat = mTwopickright.getLat();
                String rightTwoLon = mTwopickright.getLon();*/

                two();
                if (mTwoLatLeft.length() > 6 && mTwoLonLeft.length() > 6 && mTwoLatRight.length() > 6 && mTwoLonRight.length() > 6) {
                    String lat = mTwoLatLeft.substring(4, mTwoLatLeft.length());
                    String lon = mTwoLonLeft.substring(3, mTwoLonLeft.length());
                    String lat1 = mTwoLatRight.substring(4, mTwoLatRight.length());
                    String lon1 = mTwoLonRight.substring(3, mTwoLonRight.length());
                    String textTwoName = "0" + mControlTrackName + "-停留车-" + "(" + lon + "," + lat + ")" + "(" + lon1 + "," + lat1 + ")";
                    sendMessage(mConversationId, textTwoName);
                } else if (mTwoLatLeft.length() == 1 && mTwoLonLeft.length() == 1 && mTwoLatRight.length() == 1 && mTwoLonRight.length() == 1) {
                    String textTwoName = "0" + mControlTrackName + "-停留车-" + "(" + mTwoLonLeft + "," + mTwoLatLeft + ")" + "(" + mTwoLonRight + "," + mTwoLatRight + ")";
                    sendMessage(mConversationId, textTwoName);
                }

                mControlTwoPick.setName("0");
                mTwoparkcar.invalidate();
                break;
            case "3":
                //查看3道保存的数据是否只有挂钩
                String controlthreepickrightName43 = mControlThreePick.getName();
                if (controlthreepickrightName43.indexOf("摘钩") != -1) {
                    threeLeft("0", "0", "0", "0");
                    threeRight("0", "0", "0", "0");
                }

                three();
                if (mThreeLatLeft.length() > 6 && mThreeLonLeft.length() > 6 && mThreeLatRight.length() > 6 && mThreeLatRight.length() > 6) {
                    String lat = mThreeLatLeft.substring(4, mThreeLatLeft.length());
                    String lon = mThreeLonLeft.substring(3, mThreeLonLeft.length());
                    String lat1 = mThreeLatRight.substring(4, mThreeLatRight.length());
                    String lon1 = mThreeLonRight.substring(3, mThreeLonRight.length());
                    String textThreeName = "0" + mControlTrackName + "-停留车-" + "(" + lon + "," + lat + ")" + "(" + lon1 + "," + lat1 + ")";
                    sendMessage(mConversationId, textThreeName);
                } else if (mThreeLatLeft.length() == 1 && mThreeLonLeft.length() == 1 && mThreeLatRight.length() == 1 && mThreeLatRight.length() == 1) {
                    String textThreeName = "0" + mControlTrackName + "-停留车-" + "(" + mThreeLonLeft + "," + mThreeLatLeft + ")" + "(" + mThreeLonRight + "," + mThreeLatRight + ")";
                    sendMessage(mConversationId, textThreeName);
                }
                mControlThreePick.setName("0");
                mThreeparkcar.invalidate();
                break;
            case "4":
                //查看4道保存的数据是否只有挂钩
                String controlfourpickrightName43 = mControlFourPick.getName();
                if (controlfourpickrightName43.indexOf("摘钩") != -1) {
                    fourLeft("0", "0", "0", "0");
                    fourRight("0", "0", "0", "0");
                }

                four();
                if (mFourLatLeft.length() > 6 && mFourLonLeft.length() > 6 && mFourLatRight.length() > 6 && mFourLonRight.length() > 6) {
                    String lat = mFourLatLeft.substring(4, mFourLatLeft.length());
                    String lon = mFourLonLeft.substring(3, mFourLonLeft.length());
                    String lat1 = mFourLatRight.substring(4, mFourLatRight.length());
                    String lon1 = mFourLonRight.substring(3, mFourLonRight.length());
                    String textFourName = "0" + mControlTrackName + "-停留车-" + "(" + lon + "," + lat + ")" + "(" + lon1 + "," + lat1 + ")";
                    sendMessage(mConversationId, textFourName);
                } else if (mFourLatLeft.length() == 1 && mFourLonLeft.length() == 1 && mFourLatRight.length() == 1 && mFourLonRight.length() == 1) {
                    String textFourName = "0" + mControlTrackName + "-停留车-" + "(" + mFourLonLeft + "," + mFourLatLeft + ")" + "(" + mFourLonRight + "," + mFourLatRight + ")";
                    sendMessage(mConversationId, textFourName);
                }

                mControlFourPick.setName("0");
                mFourparkcar.invalidate();
                break;
            case "5":
                //查看5道保存的数据是否只有挂钩
                String controlfivepickrightName43 = mControlFivePick.getName();
                if (controlfivepickrightName43.indexOf("摘钩") != -1) {
                    fiveLeft("0", "0", "0", "0");
                    fiveRight("0", "0", "0", "0");
                }

                five();
                if (mFiveLatLeft.length() > 6 && mFiveLonLeft.length() > 6 && mFiveLatRight.length() > 6 && mFiveLonRight.length() > 6) {
                    String lat = mFiveLatLeft.substring(4, mFiveLatLeft.length());
                    String lon = mFiveLonLeft.substring(3, mFiveLonLeft.length());
                    String lat1 = mFiveLatRight.substring(4, mFiveLatRight.length());
                    String lon1 = mFiveLonRight.substring(3, mFiveLonRight.length());
                    String textFiveName = "0" + mControlTrackName + "-停留车-" + "(" + lon + "," + lat + ")" + "(" + lon1 + "," + lat1 + ")";
                    sendMessage(mConversationId, textFiveName);
                } else if (mFiveLatLeft.length() == 1 && mFiveLonLeft.length() == 1 && mFiveLatRight.length() == 1 && mFiveLonRight.length() == 1) {
                    String textFiveName = "0" + mControlTrackName + "-停留车-" + "(" + mFiveLonLeft + "," + mFiveLatLeft + ")" + "(" + mFiveLonRight + "," + mFiveLatRight + ")";
                    sendMessage(mConversationId, textFiveName);
                }

                mControlFivePick.setName("0");
                mFiveparkcar.invalidate();
                break;
            case "6":
                isSixTrack = true;
                //查看6道保存的数据是否只有挂钩
                String controlsixpickrightName43 = mControlSixPick.getName();
                Log.e("秦广帅43", controlsixpickrightName43);

                //mSixPickLeft.setName("");
                //mSixPickLeft.setName("");
                //String sixTrack = mControlSixPick.getTrack();

                List<DataUser> sixDataUsers = mSixParkDataDao.find();
                int sixSize = sixDataUsers.size();
                Log.e("秦广帅sixSize", sixSize + "");
                if (controlsixpickrightName43.contains("摘钩") == false && controlsixpickrightName43.contains("挂钩")) {
                    Log.e("秦广帅挂钩", "挂钩");
                    mSixParkCar.invalidate();
                    String sixPosition = mControlSixPick.getPosition();
                    //String sixLat = mControlSixPick.getLat();
                    //String sixLon = mControlSixPick.getLon();
                    Log.e("秦广帅sixPosition", "  " + sixPosition + "  ");

                    /*String num = sixDataUsers.get(sixSize - 1).getNum();
                    Integer integerNum = Integer.valueOf(num);
                    int sum = integerNum + 1;*/
                    if (sixSize > 1) {
                        for (int i = 1; i < sixSize; i++) {
                            String ratioOfGpsPointCar = sixDataUsers.get(i).getRatioOfGpsPointCar();
                            Integer integerGpsPoint = Integer.valueOf(ratioOfGpsPointCar);
                            Integer integerSixPosition = Integer.valueOf(sixPosition);
                            int sixNum = integerGpsPoint - integerSixPosition;
                            Log.e("秦广帅", ratioOfGpsPointCar + "  " + sixPosition + "  ");
                            if (sixNum < 0) {
                                int sixZheng = -sixNum;
                                mListInteger.add(sixZheng);
                            } else {
                                mListInteger.add(sixNum);
                            }
                        }

                        //获取最小值下标
                        int sixMinIndex = getMinIndex(mListInteger);
                        Log.e("秦广帅sixMinIndex", sixMinIndex + "  ");
                        //对应数据库的下标
                        int numIndex = sixMinIndex + 1;
                        //判断最小值的位置
                        if (sixMinIndex % 2 == 0) {
                            Log.e("秦广帅", " 0 ");
                            mSixParkDataDao.delete("sixparkcar", sixMinIndex + 2);
                            mSixParkDataDao.delete("sixparkcar", sixMinIndex + 1);
                            for (int i = 1; i < sixSize; i++) {
                                String sixNum = sixDataUsers.get(i).getNum();
                                Integer integerSixNum = Integer.valueOf(sixNum);
                                if (integerSixNum > sixMinIndex + 1) {
                                    int sixZhai = integerSixNum - 2;
                                    mSixParkDataDao.updateData("sixparkcar", "" + sixZhai, integerSixNum);
                                }
                            }
                        } else {
                            Log.e("秦广帅sixMinIndex", sixMinIndex + "  ");
                            mSixParkDataDao.delete("sixparkcar", sixMinIndex + 1);
                            mSixParkDataDao.delete("sixparkcar", sixMinIndex);
                            for (int i = 1; i < sixSize; i++) {
                                String sixNum = sixDataUsers.get(i).getNum();
                                Integer integerSixNum = Integer.valueOf(sixNum);
                                if (integerSixNum > sixMinIndex + 1) {
                                    int sixZhai = integerSixNum - 2;
                                    mSixParkDataDao.updateData("sixparkcar", "" + sixZhai, integerSixNum);
                                }
                            }
                        }
                    } else {
                        mSixPickLeft.setPosition("0");
                        mSixpickright.setPosition("0");
                    }
                } else if (controlsixpickrightName43.contains("摘钩") == true) {
                    Log.e("秦广帅摘钩", "摘钩");
                    mSixParkCar.invalidate();
                }

                getSixNum();

                mControlSixPick.setName("0");
                mSixParkCar.invalidate();
                break;
            case "7":
                isSixTrack = true;
                //查看7道保存的数据是否只有挂钩
                String controlsevenpickrightName43 = mControlSevenPick.getName();
                List<DataUser> sevenDataUsers = mSevenParkDataDao.find();
                int sevenSize = sevenDataUsers.size();
                if (controlsevenpickrightName43.contains("摘钩") == false && controlsevenpickrightName43.contains("挂钩")) {
                    String sevenPosition = mControlSevenPick.getPosition();
                    if (sevenSize > 1) {
                        for (int i = 1; i < sevenSize; i++) {
                            String ratioOfGpsPointCar = sevenDataUsers.get(i).getRatioOfGpsPointCar();
                            Integer integerGpsPoint = Integer.valueOf(ratioOfGpsPointCar);
                            Integer integerSevenPosition = Integer.valueOf(sevenPosition);
                            int sevenNum = integerGpsPoint - integerSevenPosition;
                            Log.e("秦广帅", ratioOfGpsPointCar + "  " + sevenPosition + "  ");
                            if (sevenNum < 0) {
                                int sevenZheng = -sevenNum;
                                mListInteger.add(sevenZheng);
                            } else {
                                mListInteger.add(sevenNum);
                            }
                        }

                        //获取最小值下标
                        int sevenMinIndex = getMinIndex(mListInteger);
                        Log.e("秦广帅sevenMinIndex", sevenMinIndex + "  ");
                        //对应数据库的下标
                        int numIndex = sevenMinIndex + 1;
                        //判断最小值的位置
                        if (sevenMinIndex % 2 == 0) {
                            Log.e("秦广帅", " 0 ");
                            mSevenParkDataDao.delete("sevenparkcar", sevenMinIndex + 2);
                            mSevenParkDataDao.delete("sevenparkcar", sevenMinIndex + 1);
                            for (int i = 1; i < sevenSize; i++) {
                                String sevenNum = sevenDataUsers.get(i).getNum();
                                Integer integerSevenNum = Integer.valueOf(sevenNum);
                                if (integerSevenNum > sevenMinIndex + 1) {
                                    int sevenZhai = integerSevenNum - 2;
                                    mSevenParkDataDao.updateData("sevenparkcar", "" + sevenZhai, integerSevenNum);
                                }
                            }
                        } else {
                            Log.e("秦广帅sevenMinIndex", sevenMinIndex + "  ");
                            mSevenParkDataDao.delete("sevenixparkcar", sevenMinIndex + 1);
                            mSevenParkDataDao.delete("sevenixparkcar", sevenMinIndex);
                            for (int i = 1; i < sevenSize; i++) {
                                String sevenNum = sevenDataUsers.get(i).getNum();
                                Integer integerSevenNum = Integer.valueOf(sevenNum);
                                if (integerSevenNum > sevenMinIndex + 1) {
                                    int sevenZhai = integerSevenNum - 2;
                                    mSevenParkDataDao.updateData("sevenparkcar", "" + sevenZhai, integerSevenNum);
                                }
                            }
                        }
                    } else {
                        mSevenPickLeft.setPosition("0");
                        mSevenpickright.setPosition("0");
                    }
                } else if (controlsevenpickrightName43.contains("摘钩") == true) {
                    Log.e("秦广帅摘钩", "摘钩");
                    mSevenParkCar.invalidate();
                }

                getSevenNum();

                mControlSevenPick.setName("0");
                mSevenParkCar.invalidate();
                break;
            case "8":
                //查看8道保存的数据是否只有挂钩
                String controleightpickrightName43 = mControlEightPick.getName();
                if (controleightpickrightName43.indexOf("摘钩") != -1) {
                    eightLeft("0", "0", "0", "0");
                    eightRight("0", "0", "0", "0");
                }

                String leftEightPosition = mEightPickLeft.getPosition();
                String leftEightLat = mEightPickLeft.getLat();
                String leftEightLon = mEightPickLeft.getLon();
                String rightEightPosition = mEightpickright.getPosition();
                String rightEightLat = mEightpickright.getLat();
                String rightEightLon = mEightpickright.getLon();

                if (leftEightLat.length() > 6 && leftEightLon.length() > 6 && rightEightLat.length() > 6 && rightEightLon.length() > 6) {
                    String lat = leftEightLat.substring(4, leftEightLat.length());
                    String lon = leftEightLon.substring(3, leftEightLon.length());
                    String lat1 = rightEightLat.substring(4, rightEightLat.length());
                    String lon1 = rightEightLon.substring(3, rightEightLon.length());
                    String textEightName = "0" + mControlTrackName + "-停留车-" + "(" + lon + "," + lat + ")" + "(" + lon1 + "," + lat1 + ")";
                    sendMessage(mConversationId, textEightName);
                } else if (leftEightLat.length() == 1 && leftEightLon.length() == 1 && rightEightLat.length() == 1 && rightEightLon.length() == 1) {
                    String textEightName = "0" + mControlTrackName + "-停留车-" + "(" + leftEightLon + "," + leftEightLat + ")" + "(" + rightEightLon + "," + rightEightLat + ")";
                    sendMessage(mConversationId, textEightName);
                }

                mControlEightPick.setName("0");
                mEightparkcar.invalidate();
                break;
            case "9":
                //查看9道保存的数据是否只有挂钩
                String controlninepickrightName43 = mControlNinePick.getName();
                isSixTrack = true;
                List<DataUser> nineDataUsers = mNineParkDataDao.find();
                int nineSize = nineDataUsers.size();
                if (controlninepickrightName43.contains("摘钩") == false && controlninepickrightName43.contains("挂钩")) {
                    String ninePosition = mControlNinePick.getPosition();
                    if (nineSize > 1) {
                        for (int i = 1; i < nineSize; i++) {
                            String ratioOfGpsPointCar = nineDataUsers.get(i).getRatioOfGpsPointCar();
                            Integer integerGpsPoint = Integer.valueOf(ratioOfGpsPointCar);
                            Integer integerNinePosition = Integer.valueOf(ninePosition);
                            int nineNum = integerGpsPoint - integerNinePosition;
                            Log.e("秦广帅", ratioOfGpsPointCar + "  " + ninePosition + "  ");
                            if (nineNum < 0) {
                                int nineZheng = -nineNum;
                                mListInteger.add(nineZheng);
                            } else {
                                mListInteger.add(nineNum);
                            }
                        }

                        //获取最小值下标
                        int nineMinIndex = getMinIndex(mListInteger);
                        Log.e("秦广帅nineMinIndex", nineMinIndex + "  ");
                        //对应数据库的下标
                        int numIndex = nineMinIndex + 1;
                        //判断最小值的位置
                        if (nineMinIndex % 2 == 0) {
                            Log.e("秦广帅", " 0 ");
                            mNineParkDataDao.delete("nineparkcar", nineMinIndex + 2);
                            mNineParkDataDao.delete("nineparkcar", nineMinIndex + 1);
                            for (int i = 1; i < nineSize; i++) {
                                String nineNum = nineDataUsers.get(i).getNum();
                                Integer integerNineNum = Integer.valueOf(nineNum);
                                if (integerNineNum > nineMinIndex + 1) {
                                    int nineZhai = integerNineNum - 2;
                                    mNineParkDataDao.updateData("nineparkcar", "" + nineZhai, integerNineNum);
                                }
                            }
                        } else {
                            Log.e("秦广帅nineMinIndex", nineMinIndex + "  ");
                            mNineParkDataDao.delete("nineparkcar", nineMinIndex + 1);
                            mNineParkDataDao.delete("nineparkcar", nineMinIndex);
                            for (int i = 1; i < nineSize; i++) {
                                String nineNum = nineDataUsers.get(i).getNum();
                                Integer integerNineNum = Integer.valueOf(nineNum);
                                if (integerNineNum > nineMinIndex + 1) {
                                    int nineZhai = integerNineNum - 2;
                                    mNineParkDataDao.updateData("nineparkcar", "" + nineZhai, integerNineNum);
                                }
                            }
                        }
                    } else {
                        mNinePickLeft.setPosition("0");
                        mNinepickright.setPosition("0");
                    }
                } else if (controlninepickrightName43.contains("摘钩") == true) {
                    Log.e("秦广帅摘钩", "摘钩");
                    mNineParkCar.invalidate();
                }

                getNineNum();

                mControlNinePick.setName("0");
                mNineParkCar.invalidate();
                break;
            case "10":
                //查看10道保存的数据是否只有挂钩
                String controlTenPickName43 = mControlTenPick.getName();
                if (controlTenPickName43.indexOf("摘钩") != -1) {
                    tenLeft("0", "0", "0", "0");
                    tenRight("0", "0", "0", "0");
                }

                String leftTenPosition = mTenPickLeft.getPosition();
                String leftTenLat = mTenPickLeft.getLat();
                String leftTenLon = mTenPickLeft.getLon();
                String rightTenPosition = mTenpickright.getPosition();
                String rightTenLat = mTenpickright.getLat();
                String rightTenLon = mTenpickright.getLon();

                if (leftTenLat.length() > 6 && leftTenLon.length() > 6 && rightTenLat.length() > 6 && rightTenLon.length() > 6) {
                    String lat = leftTenLat.substring(4, leftTenLat.length());
                    String lon = leftTenLon.substring(3, leftTenLon.length());
                    String lat1 = rightTenLat.substring(4, rightTenLat.length());
                    String lon1 = rightTenLon.substring(3, rightTenLon.length());
                    String textEightName = mControlTrackName + "-停留车-" + "(" + lon + "," + lat + ")" + "(" + lon1 + "," + lat1 + ")";
                    sendMessage(mConversationId, textEightName);
                } else if (leftTenLat.length() == 1 && leftTenLon.length() == 1 && rightTenLat.length() == 1 && rightTenLon.length() == 1) {
                    String textEightName = mControlTrackName + "-停留车-" + "(" + leftTenLon + "," + leftTenLat + ")" + "(" + rightTenLon + "," + rightTenLat + ")";
                    sendMessage(mConversationId, textEightName);
                }

                mControlTenPick.setName("0");
                mTenparkcar.invalidate();
                break;
            case "11":
                //查看11道保存的数据是否只有挂钩
                String controlelevenpickrightName43 = mControlElevenPick.getName();
                isSixTrack = true;
                List<DataUser> elevenDataUsers = mElevenParkDataDao.find();
                int elevenSize = elevenDataUsers.size();
                if (controlelevenpickrightName43.contains("摘钩") == false && controlelevenpickrightName43.contains("挂钩")) {
                    String elevenPosition = mControlElevenPick.getPosition();
                    if (elevenSize > 1) {
                        for (int i = 1; i < elevenSize; i++) {
                            String ratioOfGpsPointCar = elevenDataUsers.get(i).getRatioOfGpsPointCar();
                            Integer integerGpsPoint = Integer.valueOf(ratioOfGpsPointCar);
                            Integer integerElevenPosition = Integer.valueOf(elevenPosition);
                            int elevenNum = integerGpsPoint - integerElevenPosition;
                            Log.e("秦广帅", ratioOfGpsPointCar + "  " + elevenPosition + "  ");
                            if (elevenNum < 0) {
                                int elevenZheng = -elevenNum;
                                mListInteger.add(elevenZheng);
                            } else {
                                mListInteger.add(elevenNum);
                            }
                        }

                        //获取最小值下标
                        int elevenMinIndex = getMinIndex(mListInteger);
                        Log.e("秦广帅elevenMinIndex", elevenMinIndex + "  ");
                        //对应数据库的下标
                        int numIndex = elevenMinIndex + 1;
                        //判断最小值的位置
                        if (elevenMinIndex % 2 == 0) {
                            Log.e("秦广帅", " 0 ");
                            mElevenParkDataDao.delete("elevenparkcar", elevenMinIndex + 2);
                            mElevenParkDataDao.delete("elevenparkcar", elevenMinIndex + 1);
                            for (int i = 1; i < elevenSize; i++) {
                                String elevenNum = elevenDataUsers.get(i).getNum();
                                Integer integerElevenNum = Integer.valueOf(elevenNum);
                                if (integerElevenNum > elevenMinIndex + 1) {
                                    int elevenZhai = integerElevenNum - 2;
                                    mElevenParkDataDao.updateData("elevenparkcar", "" + elevenZhai, integerElevenNum);
                                }
                            }
                        } else {
                            Log.e("秦广帅elevenMinIndex", elevenMinIndex + "  ");
                            mElevenParkDataDao.delete("elevenparkcar", elevenMinIndex + 1);
                            mElevenParkDataDao.delete("elevenparkcar", elevenMinIndex);
                            for (int i = 1; i < elevenSize; i++) {
                                String elevenNum = elevenDataUsers.get(i).getNum();
                                Integer integerElevenNum = Integer.valueOf(elevenNum);
                                if (integerElevenNum > elevenMinIndex + 1) {
                                    int elevenZhai = integerElevenNum - 2;
                                    mElevenParkDataDao.updateData("elevenparkcar", "" + elevenZhai, integerElevenNum);
                                }
                            }
                        }
                    } else {
                        mElevenpickleft.setPosition("0");
                        mElevenpickright.setPosition("0");
                    }
                } else if (controlelevenpickrightName43.contains("摘钩") == true) {
                    Log.e("秦广帅摘钩", "摘钩");
                    mElevenParkCar.invalidate();
                }

                getElevenNum();

                mControlElevenPick.setName("0");
                mElevenParkCar.invalidate();
                break;
            case "12":
                //查看12道保存的数据是否只有挂钩
                String controltwelvepickrightName43 = mControlTwelvePick.getName();
                isSixTrack = true;
                List<DataUser> twelveDataUsers = mTwelveParkDataDao.find();
                int twelveSize = twelveDataUsers.size();
                if (controltwelvepickrightName43.contains("摘钩") == false && controltwelvepickrightName43.contains("挂钩")) {
                    String twelvePosition = mControlTwelvePick.getPosition();
                    if (twelveSize > 1) {
                        for (int i = 1; i < twelveSize; i++) {
                            String ratioOfGpsPointCar = twelveDataUsers.get(i).getRatioOfGpsPointCar();
                            Integer integerGpsPoint = Integer.valueOf(ratioOfGpsPointCar);
                            Integer integerTwelvePosition = Integer.valueOf(twelvePosition);
                            int twelveNum = integerGpsPoint - integerTwelvePosition;
                            Log.e("秦广帅", ratioOfGpsPointCar + "  " + twelvePosition + "  ");
                            if (twelveNum < 0) {
                                int twelveZheng = -twelveNum;
                                mListInteger.add(twelveZheng);
                            } else {
                                mListInteger.add(twelveNum);
                            }
                        }

                        //获取最小值下标
                        int twelveMinIndex = getMinIndex(mListInteger);
                        Log.e("秦广帅twelveMinIndex", twelveMinIndex + "  ");
                        //对应数据库的下标
                        int numIndex = twelveMinIndex + 1;
                        //判断最小值的位置
                        if (twelveMinIndex % 2 == 0) {
                            Log.e("秦广帅", " 0 ");
                            mTwelveParkDataDao.delete("twelveparkcar", twelveMinIndex + 2);
                            mTwelveParkDataDao.delete("twelveparkcar", twelveMinIndex + 1);
                            for (int i = 1; i < twelveSize; i++) {
                                String twelveNum = twelveDataUsers.get(i).getNum();
                                Integer integerTwelveNum = Integer.valueOf(twelveNum);
                                if (integerTwelveNum > twelveMinIndex + 1) {
                                    int twelveZhai = integerTwelveNum - 2;
                                    mTwelveParkDataDao.updateData("twelveparkcar", "" + twelveZhai, integerTwelveNum);
                                }
                            }
                        } else {
                            Log.e("秦广帅twelveMinIndex", twelveMinIndex + "  ");
                            mTwelveParkDataDao.delete("twelveparkcar", twelveMinIndex + 1);
                            mTwelveParkDataDao.delete("twelveparkcar", twelveMinIndex);
                            for (int i = 1; i < twelveSize; i++) {
                                String twelveNum = twelveDataUsers.get(i).getNum();
                                Integer integerTwelveNum = Integer.valueOf(twelveNum);
                                if (integerTwelveNum > twelveMinIndex + 1) {
                                    int twelveZhai = integerTwelveNum - 2;
                                    mTwelveParkDataDao.updateData("twelveparkcar", "" + twelveZhai, integerTwelveNum);
                                }
                            }
                        }
                    } else {
                        mTwelvePickLeft.setPosition("0");
                        mTwelvepickright.setPosition("0");
                    }
                } else if (controltwelvepickrightName43.contains("摘钩") == true) {
                    Log.e("秦广帅摘钩", "摘钩");
                    mTwelveParkCar.invalidate();
                }

                getTwelveNum();

                mControlTwelvePick.setName("0");
                mTwelveParkCar.invalidate();
                break;
            case "13":
                //查看13道保存的数据是否只有挂钩
                String controlthirteenpickrightName43 = mControlThirteenPick.getName();
                isSixTrack = true;
                List<DataUser> thirteenDataUsers = mThirteenParkDataDao.find();
                int thirteenSize = thirteenDataUsers.size();
                if (controlthirteenpickrightName43.contains("摘钩") == false && controlthirteenpickrightName43.contains("挂钩")) {
                    String thirteenPosition = mControlThirteenPick.getPosition();
                    if (thirteenSize > 1) {
                        for (int i = 1; i < thirteenSize; i++) {
                            String ratioOfGpsPointCar = thirteenDataUsers.get(i).getRatioOfGpsPointCar();
                            Integer integerGpsPoint = Integer.valueOf(ratioOfGpsPointCar);
                            Integer integerThirteenPosition = Integer.valueOf(thirteenPosition);
                            int thirteenNum = integerGpsPoint - integerThirteenPosition;
                            Log.e("秦广帅", ratioOfGpsPointCar + "  " + thirteenPosition + "  ");
                            if (thirteenNum < 0) {
                                int thirteenZheng = -thirteenNum;
                                mListInteger.add(thirteenZheng);
                            } else {
                                mListInteger.add(thirteenNum);
                            }
                        }

                        //获取最小值下标
                        int thirteenMinIndex = getMinIndex(mListInteger);
                        Log.e("秦广帅thirteenMinIndex", thirteenMinIndex + "  ");
                        //对应数据库的下标
                        int numIndex = thirteenMinIndex + 1;
                        //判断最小值的位置
                        if (thirteenMinIndex % 2 == 0) {
                            Log.e("秦广帅", " 0 ");
                            mThirteenParkDataDao.delete("thirteenparkcar", thirteenMinIndex + 2);
                            mThirteenParkDataDao.delete("thirteenparkcar", thirteenMinIndex + 1);
                            for (int i = 1; i < thirteenSize; i++) {
                                String thirteenNum = thirteenDataUsers.get(i).getNum();
                                Integer integerThirteenNum = Integer.valueOf(thirteenNum);
                                if (integerThirteenNum > thirteenMinIndex + 1) {
                                    int thirteenZhai = integerThirteenNum - 2;
                                    mThirteenParkDataDao.updateData("thirteenparkcar", "" + thirteenZhai, integerThirteenNum);
                                }
                            }
                        } else {
                            Log.e("秦广帅thirteenMinIndex", thirteenMinIndex + "  ");
                            mThirteenParkDataDao.delete("thirteenparkcar", thirteenMinIndex + 1);
                            mThirteenParkDataDao.delete("thirteenparkcar", thirteenMinIndex);
                            for (int i = 1; i < thirteenSize; i++) {
                                String thirteenNum = thirteenDataUsers.get(i).getNum();
                                Integer integerThirteenNum = Integer.valueOf(thirteenNum);
                                if (integerThirteenNum > thirteenMinIndex + 1) {
                                    int thirteenZhai = integerThirteenNum - 2;
                                    mThirteenParkDataDao.updateData("thirteenparkcar", "" + thirteenZhai, integerThirteenNum);
                                }
                            }
                        }
                    } else {
                        mThirteenPickLeft.setPosition("0");
                        mThirteenpickright.setPosition("0");
                    }
                } else if (controlthirteenpickrightName43.contains("摘钩") == true) {
                    Log.e("秦广帅摘钩", "摘钩");
                    mThirteenParkCar.invalidate();
                }

                getThirteenNum();

                mControlThirteenPick.setName("0");
                mThirteenParkCar.invalidate();
                break;
            case "14":
                //查看14道保存的数据是否只有挂钩
                String controlfourteenpickrightName43 = mControlFourteenPick.getName();
                isSixTrack = true;
                List<DataUser> fourteenDataUsers = mFourteenParkDataDao.find();
                int fourteenSize = fourteenDataUsers.size();
                if (controlfourteenpickrightName43.contains("摘钩") == false && controlfourteenpickrightName43.contains("挂钩")) {
                    String fourteenPosition = mControlFourteenPick.getPosition();
                    if (fourteenSize > 1) {
                        for (int i = 1; i < fourteenSize; i++) {
                            String ratioOfGpsPointCar = fourteenDataUsers.get(i).getRatioOfGpsPointCar();
                            Integer integerGpsPoint = Integer.valueOf(ratioOfGpsPointCar);
                            Integer integerFourteenPosition = Integer.valueOf(fourteenPosition);
                            int fourteenNum = integerGpsPoint - integerFourteenPosition;
                            Log.e("秦广帅", ratioOfGpsPointCar + "  " + fourteenPosition + "  ");
                            if (fourteenNum < 0) {
                                int fourteenZheng = -fourteenNum;
                                mListInteger.add(fourteenZheng);
                            } else {
                                mListInteger.add(fourteenNum);
                            }
                        }

                        //获取最小值下标
                        int fourteenMinIndex = getMinIndex(mListInteger);
                        Log.e("秦广帅fourteenMinIndex", fourteenMinIndex + "  ");
                        //对应数据库的下标
                        int numIndex = fourteenMinIndex + 1;
                        //判断最小值的位置
                        if (fourteenMinIndex % 2 == 0) {
                            Log.e("秦广帅", " 0 ");
                            mFourteenParkDataDao.delete("fourteenparkcar", fourteenMinIndex + 2);
                            mFourteenParkDataDao.delete("fourteenparkcar", fourteenMinIndex + 1);
                            for (int i = 1; i < fourteenSize; i++) {
                                String fourteenNum = fourteenDataUsers.get(i).getNum();
                                Integer integerFourteenNum = Integer.valueOf(fourteenNum);
                                if (integerFourteenNum > fourteenMinIndex + 1) {
                                    int fourteenZhai = integerFourteenNum - 2;
                                    mFourteenParkDataDao.updateData("fourteenparkcar", "" + fourteenZhai, integerFourteenNum);
                                }
                            }
                        } else {
                            Log.e("秦广帅fourteenMinIndex", fourteenMinIndex + "  ");
                            mFourteenParkDataDao.delete("fourteenparkcar", fourteenMinIndex + 1);
                            mFourteenParkDataDao.delete("fourteenparkcar", fourteenMinIndex);
                            for (int i = 1; i < fourteenSize; i++) {
                                String fourteenNum = fourteenDataUsers.get(i).getNum();
                                Integer integerFourteenNum = Integer.valueOf(fourteenNum);
                                if (integerFourteenNum > fourteenMinIndex + 1) {
                                    int fourteenZhai = integerFourteenNum - 2;
                                    mFourteenParkDataDao.updateData("fourteenparkcar", "" + fourteenZhai, integerFourteenNum);
                                }
                            }
                        }
                    } else {
                        mFourteenPickLeft.setPosition("0");
                        mFourteenpickright.setPosition("0");
                    }
                } else if (controlfourteenpickrightName43.contains("摘钩") == true) {
                    Log.e("秦广帅摘钩", "摘钩");
                    mFourteenParkCar.invalidate();
                }

                getFourteenNum();

                mControlFourteenPick.setName("0");
                mFourteenParkCar.invalidate();
                break;
            case "15":
                //查看15道保存的数据是否只有挂钩
                String controlfifteenpickrightName43 = mControlFifteenPick.getName();
                isSixTrack = true;
                List<DataUser> fifteenDataUsers = mFifteenParkDataDao.find();
                int fifteenSize = fifteenDataUsers.size();
                if (controlfifteenpickrightName43.contains("摘钩") == false && controlfifteenpickrightName43.contains("挂钩")) {
                    String fifteenPosition = mControlFifteenPick.getPosition();
                    if (fifteenSize > 1) {
                        for (int i = 1; i < fifteenSize; i++) {
                            String ratioOfGpsPointCar = fifteenDataUsers.get(i).getRatioOfGpsPointCar();
                            Integer integerGpsPoint = Integer.valueOf(ratioOfGpsPointCar);
                            Integer integerFifteenPosition = Integer.valueOf(fifteenPosition);
                            int fifteenNum = integerGpsPoint - integerFifteenPosition;
                            Log.e("秦广帅", ratioOfGpsPointCar + "  " + fifteenPosition + "  ");
                            if (fifteenNum < 0) {
                                int fifteenZheng = -fifteenNum;
                                mListInteger.add(fifteenZheng);
                            } else {
                                mListInteger.add(fifteenNum);
                            }
                        }

                        //获取最小值下标
                        int fifteenMinIndex = getMinIndex(mListInteger);
                        Log.e("秦广帅fifteenMinIndex", fifteenMinIndex + "  ");
                        //对应数据库的下标
                        int numIndex = fifteenMinIndex + 1;
                        //判断最小值的位置
                        if (fifteenMinIndex % 2 == 0) {
                            Log.e("秦广帅", " 0 ");
                            mFifteenParkDataDao.delete("fifteenparkcar", fifteenMinIndex + 2);
                            mFifteenParkDataDao.delete("fifteenparkcar", fifteenMinIndex + 1);
                            for (int i = 1; i < fifteenSize; i++) {
                                String fifteenNum = fifteenDataUsers.get(i).getNum();
                                Integer integerFifteenNum = Integer.valueOf(fifteenNum);
                                if (integerFifteenNum > fifteenMinIndex + 1) {
                                    int fifteenZhai = integerFifteenNum - 2;
                                    mFifteenParkDataDao.updateData("fifteenparkcar", "" + fifteenZhai, integerFifteenNum);
                                }
                            }
                        } else {
                            Log.e("秦广帅fifteenMinIndex", fifteenMinIndex + "  ");
                            mFifteenParkDataDao.delete("fifteenparkcar", fifteenMinIndex + 1);
                            mFifteenParkDataDao.delete("fifteenparkcar", fifteenMinIndex);
                            for (int i = 1; i < fifteenSize; i++) {
                                String fifteenNum = fifteenDataUsers.get(i).getNum();
                                Integer integerFifteenNum = Integer.valueOf(fifteenNum);
                                if (integerFifteenNum > fifteenMinIndex + 1) {
                                    int fifteenZhai = integerFifteenNum - 2;
                                    mFifteenParkDataDao.updateData("fifteenparkcar", "" + fifteenZhai, integerFifteenNum);
                                }
                            }
                        }
                    } else {
                        mFifteenPickLeft.setPosition("0");
                        mFifteenPickRight.setPosition("0");
                    }
                } else if (controlfifteenpickrightName43.contains("摘钩") == true) {
                    Log.e("秦广帅摘钩", "摘钩");
                    mFifteenParkCar.invalidate();
                }

                getFifteenNum();

                mControlFifteenPick.setName("0");
                mFifteenParkCar.invalidate();
                break;
            case "16":
                //查看16道保存的数据是否只有挂钩
                String controlsixteenpickrightName43 = mControlSixteenPick.getName();
                isSixTrack = true;
                List<DataUser> sixteenDataUsers = mSixteenParkDataDao.find();
                int sixteenSize = sixteenDataUsers.size();
                if (controlsixteenpickrightName43.contains("摘钩") == false && controlsixteenpickrightName43.contains("挂钩")) {
                    String sixteenPosition = mControlSixteenPick.getPosition();
                    if (sixteenSize > 1) {
                        for (int i = 1; i < sixteenSize; i++) {
                            String ratioOfGpsPointCar = sixteenDataUsers.get(i).getRatioOfGpsPointCar();
                            Integer integerGpsPoint = Integer.valueOf(ratioOfGpsPointCar);
                            Integer integerSixteenPosition = Integer.valueOf(sixteenPosition);
                            int sixteenNum = integerGpsPoint - integerSixteenPosition;
                            Log.e("秦广帅", ratioOfGpsPointCar + "  " + sixteenPosition + "  ");
                            if (sixteenNum < 0) {
                                int sixteenZheng = -sixteenNum;
                                mListInteger.add(sixteenZheng);
                            } else {
                                mListInteger.add(sixteenNum);
                            }
                        }

                        //获取最小值下标
                        int sixteenMinIndex = getMinIndex(mListInteger);
                        Log.e("秦广帅sixteenMinIndex", sixteenMinIndex + "  ");
                        //对应数据库的下标
                        int numIndex = sixteenMinIndex + 1;
                        //判断最小值的位置
                        if (sixteenMinIndex % 2 == 0) {
                            Log.e("秦广帅", " 0 ");
                            mSixteenParkDataDao.delete("sixteenparkcar", sixteenMinIndex + 2);
                            mSixteenParkDataDao.delete("sixteenparkcar", sixteenMinIndex + 1);
                            for (int i = 1; i < sixteenSize; i++) {
                                String sixteenNum = sixteenDataUsers.get(i).getNum();
                                Integer integerSixteenNum = Integer.valueOf(sixteenNum);
                                if (integerSixteenNum > sixteenMinIndex + 1) {
                                    int sixteenZhai = integerSixteenNum - 2;
                                    mSixteenParkDataDao.updateData("sixteenparkcar", "" + sixteenZhai, integerSixteenNum);
                                }
                            }
                        } else {
                            Log.e("秦广帅sixteenMinIndex", sixteenMinIndex + "  ");
                            mSixteenParkDataDao.delete("sixteenparkcar", sixteenMinIndex + 1);
                            mSixteenParkDataDao.delete("sixteenparkcar", sixteenMinIndex);
                            for (int i = 1; i < sixteenSize; i++) {
                                String sixteenNum = sixteenDataUsers.get(i).getNum();
                                Integer integerSixteenNum = Integer.valueOf(sixteenNum);
                                if (integerSixteenNum > sixteenMinIndex + 1) {
                                    int sixteenZhai = integerSixteenNum - 2;
                                    mSixteenParkDataDao.updateData("sixteenparkcar", "" + sixteenZhai, integerSixteenNum);
                                }
                            }
                        }
                    } else {
                        mSixteenPickLeft.setPosition("0");
                        mSixteenPickRight.setPosition("0");
                    }
                } else if (controlsixteenpickrightName43.contains("摘钩") == true) {
                    Log.e("秦广帅摘钩", "摘钩");
                    mSixteenParkCar.invalidate();
                }

                getSixteenNum();

                mControlSixteenPick.setName("0");
                mSixteenParkCar.invalidate();
                break;
            case "17":
                //查看17道保存的数据是否只有挂钩
                String controlseventeenpickrightName43 = mControlSeventeenPick.getName();
                isSixTrack = true;
                List<DataUser> seventeenDataUsers = mSeventeenParkDataDao.find();
                int seventeenSize = seventeenDataUsers.size();
                if (controlseventeenpickrightName43.contains("摘钩") == false && controlseventeenpickrightName43.contains("挂钩")) {
                    String seventeenPosition = mControlSeventeenPick.getPosition();
                    if (seventeenSize > 1) {
                        for (int i = 1; i < seventeenSize; i++) {
                            String ratioOfGpsPointCar = seventeenDataUsers.get(i).getRatioOfGpsPointCar();
                            Integer integerGpsPoint = Integer.valueOf(ratioOfGpsPointCar);
                            Integer integerSeventeenPosition = Integer.valueOf(seventeenPosition);
                            int seventeenNum = integerGpsPoint - integerSeventeenPosition;
                            Log.e("秦广帅", ratioOfGpsPointCar + "  " + seventeenPosition + "  ");
                            if (seventeenNum < 0) {
                                int seventeenZheng = -seventeenNum;
                                mListInteger.add(seventeenZheng);
                            } else {
                                mListInteger.add(seventeenNum);
                            }
                        }

                        //获取最小值下标
                        int seventeenMinIndex = getMinIndex(mListInteger);
                        Log.e("秦广帅seventeenMinIndex", seventeenMinIndex + "  ");
                        //对应数据库的下标
                        int numIndex = seventeenMinIndex + 1;
                        //判断最小值的位置
                        if (seventeenMinIndex % 2 == 0) {
                            Log.e("秦广帅", " 0 ");
                            mSeventeenParkDataDao.delete("seventeenparkcar", seventeenMinIndex + 2);
                            mSeventeenParkDataDao.delete("seventeenparkcar", seventeenMinIndex + 1);
                            for (int i = 1; i < seventeenSize; i++) {
                                String seventeenNum = seventeenDataUsers.get(i).getNum();
                                Integer integerSeventeenNum = Integer.valueOf(seventeenNum);
                                if (integerSeventeenNum > seventeenMinIndex + 1) {
                                    int seventeenZhai = integerSeventeenNum - 2;
                                    mSeventeenParkDataDao.updateData("seventeenparkcar", "" + seventeenZhai, integerSeventeenNum);
                                }
                            }
                        } else {
                            Log.e("秦广帅seventeenMinIndex", seventeenMinIndex + "  ");
                            mSeventeenParkDataDao.delete("seventeenparkcar", seventeenMinIndex + 1);
                            mSeventeenParkDataDao.delete("seventeenparkcar", seventeenMinIndex);
                            for (int i = 1; i < seventeenSize; i++) {
                                String seventeenNum = seventeenDataUsers.get(i).getNum();
                                Integer integerSeventeenNum = Integer.valueOf(seventeenNum);
                                if (integerSeventeenNum > seventeenMinIndex + 1) {
                                    int seventeenZhai = integerSeventeenNum - 2;
                                    mSeventeenParkDataDao.updateData("seventeenparkcar", "" + seventeenZhai, integerSeventeenNum);
                                }
                            }
                        }
                    } else {
                        mSeventeenPickLeft.setPosition("0");
                        mSeventeenPickRight.setPosition("0");
                    }
                } else if (controlseventeenpickrightName43.contains("摘钩") == true) {
                    Log.e("秦广帅摘钩", "摘钩");
                    mSeventeenParkCar.invalidate();
                }

                getSeventeenNum();

                mControlSeventeenPick.setName("0");
                mSeventeenParkCar.invalidate();
                break;
            case "18":
                //查看18道保存的数据是否只有挂钩
                String controleighteenpickrightName43 = mControlEighteenPick.getName();
                isSixTrack = true;
                List<DataUser> eighteenDataUsers = mEighteenParkDataDao.find();
                int eighteenSize = eighteenDataUsers.size();
                if (controleighteenpickrightName43.contains("摘钩") == false && controleighteenpickrightName43.contains("挂钩")) {
                    String eighteenPosition = mControlEighteenPick.getPosition();
                    if (eighteenSize > 1) {
                        for (int i = 1; i < eighteenSize; i++) {
                            String ratioOfGpsPointCar = eighteenDataUsers.get(i).getRatioOfGpsPointCar();
                            Integer integerGpsPoint = Integer.valueOf(ratioOfGpsPointCar);
                            Integer integerEighteenPosition = Integer.valueOf(eighteenPosition);
                            int eighteenNum = integerGpsPoint - integerEighteenPosition;
                            Log.e("秦广帅", ratioOfGpsPointCar + "  " + eighteenPosition + "  ");
                            if (eighteenNum < 0) {
                                int eighteenZheng = -eighteenNum;
                                mListInteger.add(eighteenZheng);
                            } else {
                                mListInteger.add(eighteenNum);
                            }
                        }

                        //获取最小值下标
                        int eighteenMinIndex = getMinIndex(mListInteger);
                        Log.e("秦广帅eighteenMinIndex", eighteenMinIndex + "  ");
                        //对应数据库的下标
                        int numIndex = eighteenMinIndex + 1;
                        //判断最小值的位置
                        if (eighteenMinIndex % 2 == 0) {
                            Log.e("秦广帅", " 0 ");
                            mEighteenParkDataDao.delete("eighteenparkcar", eighteenMinIndex + 2);
                            mEighteenParkDataDao.delete("eighteenparkcar", eighteenMinIndex + 1);
                            for (int i = 1; i < eighteenSize; i++) {
                                String eighteenNum = eighteenDataUsers.get(i).getNum();
                                Integer integerEighteenNum = Integer.valueOf(eighteenNum);
                                if (integerEighteenNum > eighteenMinIndex + 1) {
                                    int eighteenZhai = integerEighteenNum - 2;
                                    mEighteenParkDataDao.updateData("eighteenparkcar", "" + eighteenZhai, integerEighteenNum);
                                }
                            }
                        } else {
                            Log.e("秦广帅eighteenMinIndex", eighteenMinIndex + "  ");
                            mEighteenParkDataDao.delete("eighteenparkcar", eighteenMinIndex + 1);
                            mEighteenParkDataDao.delete("eighteenparkcar", eighteenMinIndex);
                            for (int i = 1; i < eighteenSize; i++) {
                                String eighteenNum = eighteenDataUsers.get(i).getNum();
                                Integer integerEighteenNum = Integer.valueOf(eighteenNum);
                                if (integerEighteenNum > eighteenMinIndex + 1) {
                                    int eighteenZhai = integerEighteenNum - 2;
                                    mEighteenParkDataDao.updateData("eighteenparkcar", "" + eighteenZhai, integerEighteenNum);
                                }
                            }
                        }
                    } else {
                        mEighteenPickLeft.setPosition("0");
                        mEighteenPickRight.setPosition("0");
                    }
                } else if (controleighteenpickrightName43.contains("摘钩") == true) {
                    Log.e("秦广帅摘钩", "摘钩");
                    mEighteenParkCar.invalidate();
                }

                getEighteenNum();

                mControlEighteenPick.setName("0");
                mEighteenParkCar.invalidate();
                break;
            case "19":
                //查看19道保存的数据是否只有挂钩
                String controlnineteenpickrightName43 = mControlNineteenPick.getName();
                isSixTrack = true;
                List<DataUser> nineteenDataUsers = mNineteenParkDataDao.find();
                int nineteenSize = nineteenDataUsers.size();
                if (controlnineteenpickrightName43.contains("摘钩") == false && controlnineteenpickrightName43.contains("挂钩")) {
                    String nineteenPosition = mControlNineteenPick.getPosition();
                    if (nineteenSize > 1) {
                        for (int i = 1; i < nineteenSize; i++) {
                            String ratioOfGpsPointCar = nineteenDataUsers.get(i).getRatioOfGpsPointCar();
                            Integer integerGpsPoint = Integer.valueOf(ratioOfGpsPointCar);
                            Integer integerNineteenPosition = Integer.valueOf(nineteenPosition);
                            int nineteenNum = integerGpsPoint - integerNineteenPosition;
                            Log.e("秦广帅", ratioOfGpsPointCar + "  " + nineteenPosition + "  ");
                            if (nineteenNum < 0) {
                                int nineteenZheng = -nineteenNum;
                                mListInteger.add(nineteenZheng);
                            } else {
                                mListInteger.add(nineteenNum);
                            }
                        }

                        //获取最小值下标
                        int nineteenMinIndex = getMinIndex(mListInteger);
                        Log.e("秦广帅nineteenMinIndex", nineteenMinIndex + "  ");
                        //对应数据库的下标
                        int numIndex = nineteenMinIndex + 1;
                        //判断最小值的位置
                        if (nineteenMinIndex % 2 == 0) {
                            Log.e("秦广帅", " 0 ");
                            mNineteenParkDataDao.delete("nineteenparkcar", nineteenMinIndex + 2);
                            mNineteenParkDataDao.delete("nineteenparkcar", nineteenMinIndex + 1);
                            for (int i = 1; i < nineteenSize; i++) {
                                String nineteenNum = nineteenDataUsers.get(i).getNum();
                                Integer integerNineteenNum = Integer.valueOf(nineteenNum);
                                if (integerNineteenNum > nineteenMinIndex + 1) {
                                    int nineteenZhai = integerNineteenNum - 2;
                                    mNineteenParkDataDao.updateData("nineteenparkcar", "" + nineteenZhai, integerNineteenNum);
                                }
                            }
                        } else {
                            Log.e("秦广帅nineteenMinIndex", nineteenMinIndex + "  ");
                            mNineteenParkDataDao.delete("nineteenparkcar", nineteenMinIndex + 1);
                            mNineteenParkDataDao.delete("nineteenparkcar", nineteenMinIndex);
                            for (int i = 1; i < nineteenSize; i++) {
                                String nineteenNum = nineteenDataUsers.get(i).getNum();
                                Integer integerNineteenNum = Integer.valueOf(nineteenNum);
                                if (integerNineteenNum > nineteenMinIndex + 1) {
                                    int nineteenZhai = integerNineteenNum - 2;
                                    mNineteenParkDataDao.updateData("nineteenparkcar", "" + nineteenZhai, integerNineteenNum);
                                }
                            }
                        }
                    } else {
                        mNineteenPickLeft.setPosition("0");
                        mNineteenPickRight.setPosition("0");
                    }
                } else if (controlnineteenpickrightName43.contains("摘钩") == true) {
                    Log.e("秦广帅摘钩", "摘钩");
                    mNineteenParkCar.invalidate();
                }

                getNineteenNum();

                mControlNineteenPick.setName("0");
                mNineteenParkCar.invalidate();
                break;
        }
    }

    private void qidong() {
        mListInteger.clear();
        mListNum.clear();
        mAdvancedmr.setName("true");
        mControlTrackName = mControlTrack.getName();
        Log.e("秦广帅name", mControlTrackName);
        isSixTrack = true;
        switch (mControlTrackName) {
            case "1":
                //查看1道保存的数据是否只有挂钩data485.indexOf
                List<DataUser> oneDataUsers = mOneDataDao.find();
                int size = oneDataUsers.size();
                Log.e("秦广帅size", size + "");
                String controlonepickrightName41 = mControlOnePick.getName();
                if (controlonepickrightName41.contains("摘钩") == false && controlonepickrightName41.contains("挂钩")) {
                    Log.e("秦广帅", "挂钩");
                    mOneDataDao.delete("oneparkcar", 1);
                    mOneDataDao.delete("oneparkcar", 2);
                    oneLeft("0", "0", "0", "0");
                    oneRight("0", "0", "0", "0");
                } else {
                    Log.e("秦广帅", "摘钩");
                    if (size > 1) {
                        for (int i = 1; i < size; i++) {
                            String ratioOfGpsPointCar = oneDataUsers.get(i).getRatioOfGpsPointCar();
                            Integer integerRatioOfGpsPointCar = Integer.valueOf(ratioOfGpsPointCar);
                            mListInteger.add(integerRatioOfGpsPointCar);
                        }
                        Log.e("秦广帅mListInteger", mListInteger.toString());

                        int maxIndex = getMaxIndex(mListInteger);
                        int minIndex = getMinIndex(mListInteger);
                        String maxRatioOfGpsPointCar = oneDataUsers.get(maxIndex + 1).getRatioOfGpsPointCar();
                        String maxLat = oneDataUsers.get(maxIndex + 1).getLat();
                        String maxLon = oneDataUsers.get(maxIndex + 1).getLon();
                        String maxGd = oneDataUsers.get(maxIndex + 1).getGd();
                        String minRatioOfGpsPointCar = oneDataUsers.get(minIndex + 1).getRatioOfGpsPointCar();
                        String minLat = oneDataUsers.get(minIndex + 1).getLat();
                        String minLon = oneDataUsers.get(minIndex + 1).getLon();
                        String minGd = oneDataUsers.get(minIndex + 1).getGd();
                        oneLeft(maxRatioOfGpsPointCar, minLat, minLon, minGd);
                        oneRight(minRatioOfGpsPointCar, maxLat, maxLon, maxGd);
                        Log.e("秦广帅Index", maxIndex + "    " + minIndex);
                        Log.e("秦广帅max", maxRatioOfGpsPointCar + "    " + maxLat + "    " + maxLon + "    " + maxGd);
                        Log.e("秦广帅min", minRatioOfGpsPointCar + "    " + minLat + "    " + minLon + "    " + minGd);
                    }
                }

                one();
                if (mOneLatLeft.length() > 6 && mOneLonLeft.length() > 6 && mOneLatRight.length() > 6 && mOneLonRight.length() > 6) {
                    String lat = mOneLatLeft.substring(4, mOneLatLeft.length());
                    String lon = mOneLonLeft.substring(3, mOneLonLeft.length());
                    String lat1 = mOneLatRight.substring(4, mOneLatRight.length());
                    String lon1 = mOneLonRight.substring(3, mOneLonRight.length());
                    String textOneName = "0" + mControlTrackName + "-停留车-" + "(" + lon + "," + lat + ")" + "(" + lon1 + "," + lat1 + ")";
                    sendMessage(mConversationId, textOneName);
                } else if (mOneLatLeft.length() == 1 && mOneLonLeft.length() == 1 && mOneLatRight.length() == 1 && mOneLonRight.length() == 1) {
                    String textOneName = "0" + mControlTrackName + "-停留车-" + "(" + mOneLonLeft + "," + mOneLatLeft + ")" + "(" + mOneLonRight + "," + mOneLatRight + ")";
                    sendMessage(mConversationId, textOneName);
                }
                mControlOnePick.setName("0");
                mOneparkcar.invalidate();
                break;
            case "2":
                //查看2道保存的数据是否只有挂钩
                String controltwopickrightName41 = mControlTwoPick.getName();
                if (controltwopickrightName41.indexOf("摘钩") == -1) {
                    twoLeft("0", "0", "0", "0");
                    twoRight("0", "0", "0", "0");
                }

                two();
                if (mTwoLatLeft.length() > 6 && mTwoLonLeft.length() > 6 && mTwoLatRight.length() > 6 && mTwoLonRight.length() > 6) {
                    String lat = mTwoLatLeft.substring(4, mTwoLatLeft.length());
                    String lon = mTwoLonLeft.substring(3, mTwoLonLeft.length());
                    String lat1 = mTwoLatRight.substring(4, mTwoLatRight.length());
                    String lon1 = mTwoLonRight.substring(3, mTwoLonRight.length());
                    String textTwoName = "0" + mControlTrackName + "-停留车-" + "(" + lon + "," + lat + ")" + "(" + lon1 + "," + lat1 + ")";
                    sendMessage(mConversationId, textTwoName);
                } else if (mTwoLatLeft.length() == 1 && mTwoLonLeft.length() == 1 && mTwoLatRight.length() == 1 && mTwoLonRight.length() == 1) {
                    String textTwoName = "0" + mControlTrackName + "-停留车-" + "(" + mTwoLonLeft + "," + mTwoLatLeft + ")" + "(" + mTwoLonRight + "," + mTwoLatRight + ")";
                    sendMessage(mConversationId, textTwoName);
                }

                mControlTwoPick.setName("0");
                mTwoparkcar.invalidate();
                break;
            case "3":
                //查看3道保存的数据是否只有挂钩
                String controlthreepickrightName41 = mControlThreePick.getName();
                if (controlthreepickrightName41.indexOf("摘钩") != -1) {
                    threeLeft("0", "0", "0", "0");
                    threeRight("0", "0", "0", "0");
                }

                three();
                if (mThreeLatLeft.length() > 6 && mThreeLonLeft.length() > 6 && mThreeLatRight.length() > 6 && mThreeLatRight.length() > 6) {
                    String lat = mThreeLatLeft.substring(4, mThreeLatLeft.length());
                    String lon = mThreeLonLeft.substring(3, mThreeLonLeft.length());
                    String lat1 = mThreeLatRight.substring(4, mThreeLatRight.length());
                    String lon1 = mThreeLonRight.substring(3, mThreeLonRight.length());
                    String textThreeName = mControlTrackName + "-停留车-" + "(" + lon + "," + lat + ")" + "(" + lon1 + "," + lat1 + ")";
                    sendMessage(mConversationId, textThreeName);
                } else if (mThreeLatLeft.length() == 1 && mThreeLonLeft.length() == 1 && mThreeLatRight.length() == 1 && mThreeLatRight.length() == 1) {
                    String textThreeName = mControlTrackName + "-停留车-" + "(" + mThreeLonLeft + "," + mThreeLatLeft + ")" + "(" + mThreeLonRight + "," + mThreeLatRight + ")";
                    sendMessage(mConversationId, textThreeName);
                }

                mControlThreePick.setName("0");
                mThreeparkcar.invalidate();
                break;
            case "4":
                //查看4道保存的数据是否只有挂钩
                String controlfourpickrightName41 = mControlFourPick.getName();
                if (controlfourpickrightName41.indexOf("摘钩") != -1) {
                    fourLeft("0", "0", "0", "0");
                    fourRight("0", "0", "0", "0");
                }

                four();
                if (mFourLatLeft.length() > 6 && mFourLonLeft.length() > 6 && mFourLatRight.length() > 6 && mFourLonRight.length() > 6) {
                    String lat = mFourLatLeft.substring(4, mFourLatLeft.length());
                    String lon = mFourLonLeft.substring(3, mFourLonLeft.length());
                    String lat1 = mFourLatRight.substring(4, mFourLatRight.length());
                    String lon1 = mFourLonRight.substring(3, mFourLonRight.length());
                    String textFourName = mControlTrackName + "-停留车-" + "(" + lon + "," + lat + ")" + "(" + lon1 + "," + lat1 + ")";
                    sendMessage(mConversationId, textFourName);
                } else if (mFourLatLeft.length() == 1 && mFourLonLeft.length() == 1 && mFourLatRight.length() == 1 && mFourLonRight.length() == 1) {
                    String textFourName = mControlTrackName + "-停留车-" + "(" + mFourLonLeft + "," + mFourLatLeft + ")" + "(" + mFourLonRight + "," + mFourLatRight + ")";
                    sendMessage(mConversationId, textFourName);
                }

                mControlFourPick.setName("0");
                mFourparkcar.invalidate();
                break;
            case "5":
                //查看5道保存的数据是否只有挂钩
                String controlfivepickrightName41 = mControlFivePick.getName();
                if (controlfivepickrightName41.indexOf("摘钩") != -1) {
                    fiveLeft("0", "0", "0", "0");
                    fiveRight("0", "0", "0", "0");
                }

                five();
                if (mFiveLatLeft.length() > 6 && mFiveLonLeft.length() > 6 && mFiveLatRight.length() > 6 && mFiveLonRight.length() > 6) {
                    String lat = mFiveLatLeft.substring(4, mFiveLatLeft.length());
                    String lon = mFiveLonLeft.substring(3, mFiveLonLeft.length());
                    String lat1 = mFiveLatRight.substring(4, mFiveLatRight.length());
                    String lon1 = mFiveLonRight.substring(3, mFiveLonRight.length());
                    String textFiveName = mControlTrackName + "-停留车-" + "(" + lon + "," + lat + ")" + "(" + lon1 + "," + lat1 + ")";
                    sendMessage(mConversationId, textFiveName);
                } else if (mFiveLatLeft.length() == 1 && mFiveLonLeft.length() == 1 && mFiveLatRight.length() == 1 && mFiveLonRight.length() == 1) {
                    String textFiveName = mControlTrackName + "-停留车-" + "(" + mFiveLonLeft + "," + mFiveLatLeft + ")" + "(" + mFiveLonRight + "," + mFiveLatRight + ")";
                    sendMessage(mConversationId, textFiveName);
                }

                mControlFivePick.setName("0");
                mFiveparkcar.invalidate();
                break;
            case "6":
                isSixTrack = true;
                //查看6道保存的数据是否只有挂钩
                String controlsixpickrightName41 = mControlSixPick.getName();
                Log.e("秦广帅41", controlsixpickrightName41);

                //mSixPickLeft.setName("");
                //mSixPickLeft.setName("");
                //String sixTrack = mControlSixPick.getTrack();

                List<DataUser> sixDataUsers = mSixParkDataDao.find();
                int sixSize = sixDataUsers.size();
                Log.e("秦广帅sixSize", sixSize + "");
                if (controlsixpickrightName41.contains("摘钩") == false && controlsixpickrightName41.contains("挂钩")) {
                    Log.e("秦广帅挂钩", "挂钩");
                    mSixParkCar.invalidate();
                    String sixPosition = mControlSixPick.getPosition();
                    //String sixLat = mControlSixPick.getLat();
                    //String sixLon = mControlSixPick.getLon();
                    Log.e("秦广帅sixPosition", "  " + sixPosition + "  ");

                    /*String num = sixDataUsers.get(sixSize - 1).getNum();
                    Integer integerNum = Integer.valueOf(num);
                    int sum = integerNum + 1;*/
                    if (sixSize > 1) {
                        for (int i = 1; i < sixSize; i++) {
                            String ratioOfGpsPointCar = sixDataUsers.get(i).getRatioOfGpsPointCar();
                            Integer integerGpsPoint = Integer.valueOf(ratioOfGpsPointCar);
                            Integer integerSixPosition = Integer.valueOf(sixPosition);
                            //mListNum.add(integerGpsPoint);
                            int sixNum = integerGpsPoint - integerSixPosition;
                            Log.e("秦广帅", ratioOfGpsPointCar + "  " + sixPosition + "  ");
                            if (sixNum < 0) {
                                int sixZheng = -sixNum;
                                mListInteger.add(sixZheng);
                            } else {
                                mListInteger.add(sixNum);
                            }
                        }

                        if (mListInteger.size() > 0) {
                            //获取最小值下标
                            int sixMinIndex = getMinIndex(mListInteger);
                            Log.e("秦广帅sixMinIndex", sixMinIndex + "  ");
                            //对应数据库的下标
                            int numIndex = sixMinIndex + 1;
                            //判断最小值的位置
                            if (sixMinIndex % 2 == 0) {
                                Log.e("秦广帅", " 0 ");
                                mSixParkDataDao.delete("sixparkcar", sixMinIndex + 2);
                                mSixParkDataDao.delete("sixparkcar", sixMinIndex + 1);
                                for (int i = 1; i < sixSize; i++) {
                                    String sixNum = sixDataUsers.get(i).getNum();
                                    Integer integerSixNum = Integer.valueOf(sixNum);
                                    if (integerSixNum > sixMinIndex + 1) {
                                        int sixZhai = integerSixNum - 2;
                                        mSixParkDataDao.updateData("sixparkcar", "" + sixZhai, integerSixNum);
                                    }
                                }
                            } else {
                                Log.e("秦广帅sixMinIndex", sixMinIndex + "  ");
                                mSixParkDataDao.delete("sixparkcar", sixMinIndex + 1);
                                mSixParkDataDao.delete("sixparkcar", sixMinIndex);
                                for (int i = 1; i < sixSize; i++) {
                                    String sixNum = sixDataUsers.get(i).getNum();
                                    Integer integerSixNum = Integer.valueOf(sixNum);
                                    if (integerSixNum > sixMinIndex + 1) {
                                        int sixZhai = integerSixNum - 2;
                                        mSixParkDataDao.updateData("sixparkcar", "" + sixZhai, integerSixNum);
                                    }
                                }
                            }
                        }

                        /*if (mListNum.size()>0){
                            int maxIndex = getMaxIndex(mListNum);
                            int minIndex = getMinIndex(mListNum);
                            String minLat = sixDataUsers.get(minIndex).getLat();
                            String maxLat = sixDataUsers.get(maxIndex).getLat();
                            String minLon = sixDataUsers.get(minIndex).getLon();
                            String maxLon = sixDataUsers.get(maxIndex).getLon();
                            String minGpsPointCar = sixDataUsers.get(minIndex).getRatioOfGpsPointCar();
                            String maxGpsPointCar = sixDataUsers.get(maxIndex).getRatioOfGpsPointCar();
                            mSixPickLeft.setLat(minLat);
                            mSixPickLeft.setLon(minLon);
                            mSixPickLeft.setPosition(minGpsPointCar);
                            mSixpickright.setLat(maxLat);
                            mSixpickright.setLon(maxLon);
                            mSixpickright.setPosition(maxGpsPointCar);
                        }*/
                    } else {
                        mSixPickLeft.setLat("0");
                        mSixPickLeft.setLon("0");
                        mSixPickLeft.setPosition("0");
                        mSixpickright.setLat("0");
                        mSixpickright.setLon("0");
                        mSixpickright.setPosition("0");
                    }
                } else if (controlsixpickrightName41.contains("摘钩") == true) {
                    Log.e("秦广帅摘钩", "摘钩");
                    mSixParkCar.invalidate();
                }

                getSixNum();

                mControlSixPick.setName("0");
                mSixParkCar.invalidate();
                break;
            case "7":
                isSixTrack = true;
                //查看7道保存的数据是否只有挂钩
                String controlsevenpickrightName41 = mControlSevenPick.getName();
                List<DataUser> sevenDataUsers = mSevenParkDataDao.find();
                int sevenSize = sevenDataUsers.size();
                if (controlsevenpickrightName41.contains("摘钩") == false && controlsevenpickrightName41.contains("挂钩")) {
                    String sevenPosition = mControlSevenPick.getPosition();
                    if (sevenSize > 1) {
                        for (int i = 1; i < sevenSize; i++) {
                            String ratioOfGpsPointCar = sevenDataUsers.get(i).getRatioOfGpsPointCar();
                            Integer integerGpsPoint = Integer.valueOf(ratioOfGpsPointCar);
                            Integer integerSevenPosition = Integer.valueOf(sevenPosition);
                            int sevenNum = integerGpsPoint - integerSevenPosition;
                            Log.e("秦广帅", ratioOfGpsPointCar + "  " + sevenPosition + "  ");
                            if (sevenNum < 0) {
                                int sevenZheng = -sevenNum;
                                mListInteger.add(sevenZheng);
                            } else {
                                mListInteger.add(sevenNum);
                            }
                        }

                        //获取最小值下标
                        int sevenMinIndex = getMinIndex(mListInteger);
                        Log.e("秦广帅sevenMinIndex", sevenMinIndex + "  ");
                        //对应数据库的下标
                        int numIndex = sevenMinIndex + 1;
                        //判断最小值的位置
                        if (sevenMinIndex % 2 == 0) {
                            Log.e("秦广帅", " 0 ");
                            mSevenParkDataDao.delete("sevenparkcar", sevenMinIndex + 2);
                            mSevenParkDataDao.delete("sevenparkcar", sevenMinIndex + 1);
                            for (int i = 1; i < sevenSize; i++) {
                                String sevenNum = sevenDataUsers.get(i).getNum();
                                Integer integerSevenNum = Integer.valueOf(sevenNum);
                                if (integerSevenNum > sevenMinIndex + 1) {
                                    int sevenZhai = integerSevenNum - 2;
                                    mSevenParkDataDao.updateData("sevenparkcar", "" + sevenZhai, integerSevenNum);
                                }
                            }
                        } else {
                            Log.e("秦广帅sevenMinIndex", sevenMinIndex + "  ");
                            mSevenParkDataDao.delete("sevenixparkcar", sevenMinIndex + 1);
                            mSevenParkDataDao.delete("sevenixparkcar", sevenMinIndex);
                            for (int i = 1; i < sevenSize; i++) {
                                String sevenNum = sevenDataUsers.get(i).getNum();
                                Integer integerSevenNum = Integer.valueOf(sevenNum);
                                if (integerSevenNum > sevenMinIndex + 1) {
                                    int sevenZhai = integerSevenNum - 2;
                                    mSevenParkDataDao.updateData("sevenparkcar", "" + sevenZhai, integerSevenNum);
                                }
                            }
                        }
                    } else {
                        mSevenPickLeft.setPosition("0");
                        mSevenpickright.setPosition("0");
                    }
                } else if (controlsevenpickrightName41.contains("摘钩") == true) {
                    Log.e("秦广帅摘钩", "摘钩");
                    mSevenParkCar.invalidate();
                }

                getSevenNum();

                mControlSevenPick.setName("0");
                mSevenParkCar.invalidate();
                break;
            case "8":
                //查看8道保存的数据是否只有挂钩
                String controleightpickrightName41 = mControlEightPick.getName();
                if (controleightpickrightName41.indexOf("摘钩") != -1) {
                    eightLeft("0", "0", "0", "0");
                    eightRight("0", "0", "0", "0");
                }

                String leftEightPosition = mEightPickLeft.getPosition();
                String leftEightLat = mEightPickLeft.getLat();
                String leftEightLon = mEightPickLeft.getLon();
                String rightEightPosition = mEightpickright.getPosition();
                String rightEightLat = mEightpickright.getLat();
                String rightEightLon = mEightpickright.getLon();

                if (leftEightLat.length() > 6 && leftEightLon.length() > 6 && rightEightLat.length() > 6 && rightEightLon.length() > 6) {
                    String lat = leftEightLat.substring(4, leftEightLat.length());
                    String lon = leftEightLon.substring(3, leftEightLon.length());
                    String lat1 = rightEightLat.substring(4, rightEightLat.length());
                    String lon1 = rightEightLon.substring(3, rightEightLon.length());
                    String textEightName = "0" + mControlTrackName + "-停留车-" + "(" + lon + "," + lat + ")" + "(" + lon1 + "," + lat1 + ")";
                    sendMessage(mConversationId, textEightName);
                } else if (leftEightLat.length() == 1 && leftEightLon.length() == 1 && rightEightLat.length() == 1 && rightEightLon.length() == 1) {
                    String textEightName = "0" + mControlTrackName + "-停留车-" + "(" + leftEightLon + "," + leftEightLat + ")" + "(" + rightEightLon + "," + rightEightLat + ")";
                    sendMessage(mConversationId, textEightName);
                }

                mControlEightPick.setName("0");
                mEightparkcar.invalidate();
                break;
            case "9":
                //查看9道保存的数据是否只有挂钩
                String controlninepickrightName41 = mControlNinePick.getName();
                isSixTrack = true;
                List<DataUser> nineDataUsers = mNineParkDataDao.find();
                int nineSize = nineDataUsers.size();
                if (controlninepickrightName41.contains("摘钩") == false && controlninepickrightName41.contains("挂钩")) {
                    String ninePosition = mControlNinePick.getPosition();
                    if (nineSize > 1) {
                        for (int i = 1; i < nineSize; i++) {
                            String ratioOfGpsPointCar = nineDataUsers.get(i).getRatioOfGpsPointCar();
                            Integer integerGpsPoint = Integer.valueOf(ratioOfGpsPointCar);
                            Integer integerNinePosition = Integer.valueOf(ninePosition);
                            int nineNum = integerGpsPoint - integerNinePosition;
                            Log.e("秦广帅", ratioOfGpsPointCar + "  " + ninePosition + "  ");
                            if (nineNum < 0) {
                                int nineZheng = -nineNum;
                                mListInteger.add(nineZheng);
                            } else {
                                mListInteger.add(nineNum);
                            }
                        }

                        //获取最小值下标
                        int nineMinIndex = getMinIndex(mListInteger);
                        Log.e("秦广帅nineMinIndex", nineMinIndex + "  ");
                        //对应数据库的下标
                        int numIndex = nineMinIndex + 1;
                        //判断最小值的位置
                        if (nineMinIndex % 2 == 0) {
                            Log.e("秦广帅", " 0 ");
                            mNineParkDataDao.delete("nineparkcar", nineMinIndex + 2);
                            mNineParkDataDao.delete("nineparkcar", nineMinIndex + 1);
                            for (int i = 1; i < nineSize; i++) {
                                String nineNum = nineDataUsers.get(i).getNum();
                                Integer integerNineNum = Integer.valueOf(nineNum);
                                if (integerNineNum > nineMinIndex + 1) {
                                    int nineZhai = integerNineNum - 2;
                                    mNineParkDataDao.updateData("nineparkcar", "" + nineZhai, integerNineNum);
                                }
                            }
                        } else {
                            Log.e("秦广帅nineMinIndex", nineMinIndex + "  ");
                            mNineParkDataDao.delete("nineparkcar", nineMinIndex + 1);
                            mNineParkDataDao.delete("nineparkcar", nineMinIndex);
                            for (int i = 1; i < nineSize; i++) {
                                String nineNum = nineDataUsers.get(i).getNum();
                                Integer integerNineNum = Integer.valueOf(nineNum);
                                if (integerNineNum > nineMinIndex + 1) {
                                    int nineZhai = integerNineNum - 2;
                                    mNineParkDataDao.updateData("nineparkcar", "" + nineZhai, integerNineNum);
                                }
                            }
                        }
                    } else {
                        mNinePickLeft.setPosition("0");
                        mNinepickright.setPosition("0");
                    }
                } else if (controlninepickrightName41.contains("摘钩") == true) {
                    Log.e("秦广帅摘钩", "摘钩");
                    mNineParkCar.invalidate();
                }

                getNineNum();

                mControlNinePick.setName("0");
                mNineParkCar.invalidate();
                break;
            case "10":
                //查看10道保存的数据是否只有挂钩
                String controlTenPickName41 = mControlTenPick.getName();
                if (controlTenPickName41.indexOf("摘钩") != -1) {
                    tenLeft("0", "0", "0", "0");
                    tenRight("0", "0", "0", "0");
                }

                String leftTenPosition = mTenPickLeft.getPosition();
                String leftTenLat = mTenPickLeft.getLat();
                String leftTenLon = mTenPickLeft.getLon();
                String rightTenPosition = mTenpickright.getPosition();
                String rightTenLat = mTenpickright.getLat();
                String rightTenLon = mTenpickright.getLon();

                if (leftTenLat.length() > 6 && leftTenLon.length() > 6 && rightTenLat.length() > 6 && rightTenLon.length() > 6) {
                    String lat = leftTenLat.substring(4, leftTenLat.length());
                    String lon = leftTenLon.substring(3, leftTenLon.length());
                    String lat1 = rightTenLat.substring(4, rightTenLat.length());
                    String lon1 = rightTenLon.substring(3, rightTenLon.length());
                    String textEightName = mControlTrackName + "-停留车-" + "(" + lon + "," + lat + ")" + "(" + lon1 + "," + lat1 + ")";
                    sendMessage(mConversationId, textEightName);
                } else if (leftTenLat.length() == 1 && leftTenLon.length() == 1 && rightTenLat.length() == 1 && rightTenLon.length() == 1) {
                    String textEightName = mControlTrackName + "-停留车-" + "(" + leftTenLon + "," + leftTenLat + ")" + "(" + rightTenLon + "," + rightTenLat + ")";
                    sendMessage(mConversationId, textEightName);
                }

                mControlTenPick.setName("0");
                mTenparkcar.invalidate();
                break;
            case "11":
                //查看11道保存的数据是否只有挂钩
                String controlelevenpickrightName41 = mControlElevenPick.getName();
                isSixTrack = true;
                List<DataUser> elevenDataUsers = mElevenParkDataDao.find();
                int elevenSize = elevenDataUsers.size();
                if (controlelevenpickrightName41.contains("摘钩") == false && controlelevenpickrightName41.contains("挂钩")) {
                    String elevenPosition = mControlElevenPick.getPosition();
                    if (elevenSize > 1) {
                        for (int i = 1; i < elevenSize; i++) {
                            String ratioOfGpsPointCar = elevenDataUsers.get(i).getRatioOfGpsPointCar();
                            Integer integerGpsPoint = Integer.valueOf(ratioOfGpsPointCar);
                            Integer integerElevenPosition = Integer.valueOf(elevenPosition);
                            int elevenNum = integerGpsPoint - integerElevenPosition;
                            Log.e("秦广帅", ratioOfGpsPointCar + "  " + elevenPosition + "  ");
                            if (elevenNum < 0) {
                                int elevenZheng = -elevenNum;
                                mListInteger.add(elevenZheng);
                            } else {
                                mListInteger.add(elevenNum);
                            }
                        }

                        //获取最小值下标
                        int elevenMinIndex = getMinIndex(mListInteger);
                        Log.e("秦广帅elevenMinIndex", elevenMinIndex + "  ");
                        //对应数据库的下标
                        int numIndex = elevenMinIndex + 1;
                        //判断最小值的位置
                        if (elevenMinIndex % 2 == 0) {
                            Log.e("秦广帅", " 0 ");
                            mElevenParkDataDao.delete("elevenparkcar", elevenMinIndex + 2);
                            mElevenParkDataDao.delete("elevenparkcar", elevenMinIndex + 1);
                            for (int i = 1; i < elevenSize; i++) {
                                String elevenNum = elevenDataUsers.get(i).getNum();
                                Integer integerElevenNum = Integer.valueOf(elevenNum);
                                if (integerElevenNum > elevenMinIndex + 1) {
                                    int elevenZhai = integerElevenNum - 2;
                                    mElevenParkDataDao.updateData("elevenparkcar", "" + elevenZhai, integerElevenNum);
                                }
                            }
                        } else {
                            Log.e("秦广帅elevenMinIndex", elevenMinIndex + "  ");
                            mElevenParkDataDao.delete("elevenparkcar", elevenMinIndex + 1);
                            mElevenParkDataDao.delete("elevenparkcar", elevenMinIndex);
                            for (int i = 1; i < elevenSize; i++) {
                                String elevenNum = elevenDataUsers.get(i).getNum();
                                Integer integerElevenNum = Integer.valueOf(elevenNum);
                                if (integerElevenNum > elevenMinIndex + 1) {
                                    int elevenZhai = integerElevenNum - 2;
                                    mElevenParkDataDao.updateData("elevenparkcar", "" + elevenZhai, integerElevenNum);
                                }
                            }
                        }
                    } else {
                        mElevenpickleft.setPosition("0");
                        mElevenpickright.setPosition("0");
                    }
                } else if (controlelevenpickrightName41.contains("摘钩") == true) {
                    Log.e("秦广帅摘钩", "摘钩");
                    mElevenParkCar.invalidate();
                }

                getElevenNum();

                mControlElevenPick.setName("0");
                mElevenParkCar.invalidate();
                break;
            case "12":
                //查看12道保存的数据是否只有挂钩
                String controltwelvepickrightName41 = mControlTwelvePick.getName();
                isSixTrack = true;
                List<DataUser> twelveDataUsers = mTwelveParkDataDao.find();
                int twelveSize = twelveDataUsers.size();
                if (controltwelvepickrightName41.contains("摘钩") == false && controltwelvepickrightName41.contains("挂钩")) {
                    String twelvePosition = mControlTwelvePick.getPosition();
                    if (twelveSize > 1) {
                        for (int i = 1; i < twelveSize; i++) {
                            String ratioOfGpsPointCar = twelveDataUsers.get(i).getRatioOfGpsPointCar();
                            Integer integerGpsPoint = Integer.valueOf(ratioOfGpsPointCar);
                            Integer integerTwelvePosition = Integer.valueOf(twelvePosition);
                            int twelveNum = integerGpsPoint - integerTwelvePosition;
                            Log.e("秦广帅", ratioOfGpsPointCar + "  " + twelvePosition + "  ");
                            if (twelveNum < 0) {
                                int twelveZheng = -twelveNum;
                                mListInteger.add(twelveZheng);
                            } else {
                                mListInteger.add(twelveNum);
                            }
                        }

                        //获取最小值下标
                        int twelveMinIndex = getMinIndex(mListInteger);
                        Log.e("秦广帅twelveMinIndex", twelveMinIndex + "  ");
                        //对应数据库的下标
                        int numIndex = twelveMinIndex + 1;
                        //判断最小值的位置
                        if (twelveMinIndex % 2 == 0) {
                            Log.e("秦广帅", " 0 ");
                            mTwelveParkDataDao.delete("twelveparkcar", twelveMinIndex + 2);
                            mTwelveParkDataDao.delete("twelveparkcar", twelveMinIndex + 1);
                            for (int i = 1; i < twelveSize; i++) {
                                String twelveNum = twelveDataUsers.get(i).getNum();
                                Integer integerTwelveNum = Integer.valueOf(twelveNum);
                                if (integerTwelveNum > twelveMinIndex + 1) {
                                    int twelveZhai = integerTwelveNum - 2;
                                    mTwelveParkDataDao.updateData("twelveparkcar", "" + twelveZhai, integerTwelveNum);
                                }
                            }
                        } else {
                            Log.e("秦广帅twelveMinIndex", twelveMinIndex + "  ");
                            mTwelveParkDataDao.delete("twelveparkcar", twelveMinIndex + 1);
                            mTwelveParkDataDao.delete("twelveparkcar", twelveMinIndex);
                            for (int i = 1; i < twelveSize; i++) {
                                String twelveNum = twelveDataUsers.get(i).getNum();
                                Integer integerTwelveNum = Integer.valueOf(twelveNum);
                                if (integerTwelveNum > twelveMinIndex + 1) {
                                    int twelveZhai = integerTwelveNum - 2;
                                    mTwelveParkDataDao.updateData("twelveparkcar", "" + twelveZhai, integerTwelveNum);
                                }
                            }
                        }
                    } else {
                        mTwelvePickLeft.setPosition("0");
                        mTwelvepickright.setPosition("0");
                    }
                } else if (controltwelvepickrightName41.contains("摘钩") == true) {
                    Log.e("秦广帅摘钩", "摘钩");
                    mTwelveParkCar.invalidate();
                }

                getTwelveNum();

                mControlTwelvePick.setName("0");
                mTwelveParkCar.invalidate();
                break;
            case "13":
                //查看13道保存的数据是否只有挂钩
                String controlthirteenpickrightName41 = mControlThirteenPick.getName();
                isSixTrack = true;
                List<DataUser> thirteenDataUsers = mThirteenParkDataDao.find();
                int thirteenSize = thirteenDataUsers.size();
                if (controlthirteenpickrightName41.contains("摘钩") == false && controlthirteenpickrightName41.contains("挂钩")) {
                    String thirteenPosition = mControlThirteenPick.getPosition();
                    if (thirteenSize > 1) {
                        for (int i = 1; i < thirteenSize; i++) {
                            String ratioOfGpsPointCar = thirteenDataUsers.get(i).getRatioOfGpsPointCar();
                            Integer integerGpsPoint = Integer.valueOf(ratioOfGpsPointCar);
                            Integer integerThirteenPosition = Integer.valueOf(thirteenPosition);
                            int thirteenNum = integerGpsPoint - integerThirteenPosition;
                            Log.e("秦广帅", ratioOfGpsPointCar + "  " + thirteenPosition + "  ");
                            if (thirteenNum < 0) {
                                int thirteenZheng = -thirteenNum;
                                mListInteger.add(thirteenZheng);
                            } else {
                                mListInteger.add(thirteenNum);
                            }
                        }

                        //获取最小值下标
                        int thirteenMinIndex = getMinIndex(mListInteger);
                        Log.e("秦广帅thirteenMinIndex", thirteenMinIndex + "  ");
                        //对应数据库的下标
                        int numIndex = thirteenMinIndex + 1;
                        //判断最小值的位置
                        if (thirteenMinIndex % 2 == 0) {
                            Log.e("秦广帅", " 0 ");
                            mThirteenParkDataDao.delete("thirteenparkcar", thirteenMinIndex + 2);
                            mThirteenParkDataDao.delete("thirteenparkcar", thirteenMinIndex + 1);
                            for (int i = 1; i < thirteenSize; i++) {
                                String thirteenNum = thirteenDataUsers.get(i).getNum();
                                Integer integerThirteenNum = Integer.valueOf(thirteenNum);
                                if (integerThirteenNum > thirteenMinIndex + 1) {
                                    int thirteenZhai = integerThirteenNum - 2;
                                    mThirteenParkDataDao.updateData("thirteenparkcar", "" + thirteenZhai, integerThirteenNum);
                                }
                            }
                        } else {
                            Log.e("秦广帅thirteenMinIndex", thirteenMinIndex + "  ");
                            mThirteenParkDataDao.delete("thirteenparkcar", thirteenMinIndex + 1);
                            mThirteenParkDataDao.delete("thirteenparkcar", thirteenMinIndex);
                            for (int i = 1; i < thirteenSize; i++) {
                                String thirteenNum = thirteenDataUsers.get(i).getNum();
                                Integer integerThirteenNum = Integer.valueOf(thirteenNum);
                                if (integerThirteenNum > thirteenMinIndex + 1) {
                                    int thirteenZhai = integerThirteenNum - 2;
                                    mThirteenParkDataDao.updateData("thirteenparkcar", "" + thirteenZhai, integerThirteenNum);
                                }
                            }
                        }
                    } else {
                        mThirteenPickLeft.setPosition("0");
                        mThirteenpickright.setPosition("0");
                    }
                } else if (controlthirteenpickrightName41.contains("摘钩") == true) {
                    Log.e("秦广帅摘钩", "摘钩");
                    mThirteenParkCar.invalidate();
                }

                getThirteenNum();

                mControlThirteenPick.setName("0");
                mThirteenParkCar.invalidate();
                break;
            case "14":
                //查看14道保存的数据是否只有挂钩
                String controlfourteenpickrightName41 = mControlFourteenPick.getName();
                isSixTrack = true;
                List<DataUser> fourteenDataUsers = mFourteenParkDataDao.find();
                int fourteenSize = fourteenDataUsers.size();
                if (controlfourteenpickrightName41.contains("摘钩") == false && controlfourteenpickrightName41.contains("挂钩")) {
                    String fourteenPosition = mControlFourteenPick.getPosition();
                    if (fourteenSize > 1) {
                        for (int i = 1; i < fourteenSize; i++) {
                            String ratioOfGpsPointCar = fourteenDataUsers.get(i).getRatioOfGpsPointCar();
                            Integer integerGpsPoint = Integer.valueOf(ratioOfGpsPointCar);
                            Integer integerFourteenPosition = Integer.valueOf(fourteenPosition);
                            int fourteenNum = integerGpsPoint - integerFourteenPosition;
                            Log.e("秦广帅", ratioOfGpsPointCar + "  " + fourteenPosition + "  ");
                            if (fourteenNum < 0) {
                                int fourteenZheng = -fourteenNum;
                                mListInteger.add(fourteenZheng);
                            } else {
                                mListInteger.add(fourteenNum);
                            }
                        }

                        //获取最小值下标
                        int fourteenMinIndex = getMinIndex(mListInteger);
                        Log.e("秦广帅fourteenMinIndex", fourteenMinIndex + "  ");
                        //对应数据库的下标
                        int numIndex = fourteenMinIndex + 1;
                        //判断最小值的位置
                        if (fourteenMinIndex % 2 == 0) {
                            Log.e("秦广帅", " 0 ");
                            mFourteenParkDataDao.delete("fourteenparkcar", fourteenMinIndex + 2);
                            mFourteenParkDataDao.delete("fourteenparkcar", fourteenMinIndex + 1);
                            for (int i = 1; i < fourteenSize; i++) {
                                String fourteenNum = fourteenDataUsers.get(i).getNum();
                                Integer integerFourteenNum = Integer.valueOf(fourteenNum);
                                if (integerFourteenNum > fourteenMinIndex + 1) {
                                    int fourteenZhai = integerFourteenNum - 2;
                                    mFourteenParkDataDao.updateData("fourteenparkcar", "" + fourteenZhai, integerFourteenNum);
                                }
                            }
                        } else {
                            Log.e("秦广帅fourteenMinIndex", fourteenMinIndex + "  ");
                            mFourteenParkDataDao.delete("fourteenparkcar", fourteenMinIndex + 1);
                            mFourteenParkDataDao.delete("fourteenparkcar", fourteenMinIndex);
                            for (int i = 1; i < fourteenSize; i++) {
                                String fourteenNum = fourteenDataUsers.get(i).getNum();
                                Integer integerFourteenNum = Integer.valueOf(fourteenNum);
                                if (integerFourteenNum > fourteenMinIndex + 1) {
                                    int fourteenZhai = integerFourteenNum - 2;
                                    mFourteenParkDataDao.updateData("fourteenparkcar", "" + fourteenZhai, integerFourteenNum);
                                }
                            }
                        }
                    } else {
                        mFourteenPickLeft.setPosition("0");
                        mFourteenpickright.setPosition("0");
                    }
                } else if (controlfourteenpickrightName41.contains("摘钩") == true) {
                    Log.e("秦广帅摘钩", "摘钩");
                    mFourteenParkCar.invalidate();
                }

                getFourteenNum();

                mControlFourteenPick.setName("0");
                mFourteenParkCar.invalidate();
                break;
            case "15":
                //查看15道保存的数据是否只有挂钩
                String controlfifteenpickrightName41 = mControlFifteenPick.getName();
                isSixTrack = true;
                List<DataUser> fifteenDataUsers = mFifteenParkDataDao.find();
                int fifteenSize = fifteenDataUsers.size();
                if (controlfifteenpickrightName41.contains("摘钩") == false && controlfifteenpickrightName41.contains("挂钩")) {
                    String fifteenPosition = mControlFifteenPick.getPosition();
                    if (fifteenSize > 1) {
                        for (int i = 1; i < fifteenSize; i++) {
                            String ratioOfGpsPointCar = fifteenDataUsers.get(i).getRatioOfGpsPointCar();
                            Integer integerGpsPoint = Integer.valueOf(ratioOfGpsPointCar);
                            Integer integerFifteenPosition = Integer.valueOf(fifteenPosition);
                            int fifteenNum = integerGpsPoint - integerFifteenPosition;
                            Log.e("秦广帅", ratioOfGpsPointCar + "  " + fifteenPosition + "  ");
                            if (fifteenNum < 0) {
                                int fifteenZheng = -fifteenNum;
                                mListInteger.add(fifteenZheng);
                            } else {
                                mListInteger.add(fifteenNum);
                            }
                        }

                        //获取最小值下标
                        int fifteenMinIndex = getMinIndex(mListInteger);
                        Log.e("秦广帅fifteenMinIndex", fifteenMinIndex + "  ");
                        //对应数据库的下标
                        int numIndex = fifteenMinIndex + 1;
                        //判断最小值的位置
                        if (fifteenMinIndex % 2 == 0) {
                            Log.e("秦广帅", " 0 ");
                            mFifteenParkDataDao.delete("fifteenparkcar", fifteenMinIndex + 2);
                            mFifteenParkDataDao.delete("fifteenparkcar", fifteenMinIndex + 1);
                            for (int i = 1; i < fifteenSize; i++) {
                                String fifteenNum = fifteenDataUsers.get(i).getNum();
                                Integer integerFifteenNum = Integer.valueOf(fifteenNum);
                                if (integerFifteenNum > fifteenMinIndex + 1) {
                                    int fifteenZhai = integerFifteenNum - 2;
                                    mFifteenParkDataDao.updateData("fifteenparkcar", "" + fifteenZhai, integerFifteenNum);
                                }
                            }
                        } else {
                            Log.e("秦广帅fifteenMinIndex", fifteenMinIndex + "  ");
                            mFifteenParkDataDao.delete("fifteenparkcar", fifteenMinIndex + 1);
                            mFifteenParkDataDao.delete("fifteenparkcar", fifteenMinIndex);
                            for (int i = 1; i < fifteenSize; i++) {
                                String fifteenNum = fifteenDataUsers.get(i).getNum();
                                Integer integerFifteenNum = Integer.valueOf(fifteenNum);
                                if (integerFifteenNum > fifteenMinIndex + 1) {
                                    int fifteenZhai = integerFifteenNum - 2;
                                    mFifteenParkDataDao.updateData("fifteenparkcar", "" + fifteenZhai, integerFifteenNum);
                                }
                            }
                        }
                    } else {
                        mFifteenPickLeft.setPosition("0");
                        mFifteenPickRight.setPosition("0");
                    }
                } else if (controlfifteenpickrightName41.contains("摘钩") == true) {
                    Log.e("秦广帅摘钩", "摘钩");
                    mFifteenParkCar.invalidate();
                }

                getFifteenNum();

                mControlFifteenPick.setName("0");
                mFifteenParkCar.invalidate();
                break;
            case "16":
                //查看16道保存的数据是否只有挂钩
                String controlsixteenpickrightName41 = mControlSixteenPick.getName();
                isSixTrack = true;
                List<DataUser> sixteenDataUsers = mSixteenParkDataDao.find();
                int sixteenSize = sixteenDataUsers.size();
                if (controlsixteenpickrightName41.contains("摘钩") == false && controlsixteenpickrightName41.contains("挂钩")) {
                    String sixteenPosition = mControlSixteenPick.getPosition();
                    if (sixteenSize > 1) {
                        for (int i = 1; i < sixteenSize; i++) {
                            String ratioOfGpsPointCar = sixteenDataUsers.get(i).getRatioOfGpsPointCar();
                            Integer integerGpsPoint = Integer.valueOf(ratioOfGpsPointCar);
                            Integer integerSixteenPosition = Integer.valueOf(sixteenPosition);
                            int sixteenNum = integerGpsPoint - integerSixteenPosition;
                            Log.e("秦广帅", ratioOfGpsPointCar + "  " + sixteenPosition + "  ");
                            if (sixteenNum < 0) {
                                int sixteenZheng = -sixteenNum;
                                mListInteger.add(sixteenZheng);
                            } else {
                                mListInteger.add(sixteenNum);
                            }
                        }

                        //获取最小值下标
                        int sixteenMinIndex = getMinIndex(mListInteger);
                        Log.e("秦广帅sixteenMinIndex", sixteenMinIndex + "  ");
                        //对应数据库的下标
                        int numIndex = sixteenMinIndex + 1;
                        //判断最小值的位置
                        if (sixteenMinIndex % 2 == 0) {
                            Log.e("秦广帅", " 0 ");
                            mSixteenParkDataDao.delete("sixteenparkcar", sixteenMinIndex + 2);
                            mSixteenParkDataDao.delete("sixteenparkcar", sixteenMinIndex + 1);
                            for (int i = 1; i < sixteenSize; i++) {
                                String sixteenNum = sixteenDataUsers.get(i).getNum();
                                Integer integerSixteenNum = Integer.valueOf(sixteenNum);
                                if (integerSixteenNum > sixteenMinIndex + 1) {
                                    int sixteenZhai = integerSixteenNum - 2;
                                    mSixteenParkDataDao.updateData("sixteenparkcar", "" + sixteenZhai, integerSixteenNum);
                                }
                            }
                        } else {
                            Log.e("秦广帅sixteenMinIndex", sixteenMinIndex + "  ");
                            mSixteenParkDataDao.delete("sixteenparkcar", sixteenMinIndex + 1);
                            mSixteenParkDataDao.delete("sixteenparkcar", sixteenMinIndex);
                            for (int i = 1; i < sixteenSize; i++) {
                                String sixteenNum = sixteenDataUsers.get(i).getNum();
                                Integer integerSixteenNum = Integer.valueOf(sixteenNum);
                                if (integerSixteenNum > sixteenMinIndex + 1) {
                                    int sixteenZhai = integerSixteenNum - 2;
                                    mSixteenParkDataDao.updateData("sixteenparkcar", "" + sixteenZhai, integerSixteenNum);
                                }
                            }
                        }
                    } else {
                        mSixteenPickLeft.setPosition("0");
                        mSixteenPickRight.setPosition("0");
                    }
                } else if (controlsixteenpickrightName41.contains("摘钩") == true) {
                    Log.e("秦广帅摘钩", "摘钩");
                    mSixteenParkCar.invalidate();
                }

                getSixteenNum();

                mControlSixteenPick.setName("0");
                mSixteenParkCar.invalidate();
                break;
            case "17":
                //查看17道保存的数据是否只有挂钩
                String controlseventeenpickrightName41 = mControlSeventeenPick.getName();
                isSixTrack = true;
                List<DataUser> seventeenDataUsers = mSeventeenParkDataDao.find();
                int seventeenSize = seventeenDataUsers.size();
                if (controlseventeenpickrightName41.contains("摘钩") == false && controlseventeenpickrightName41.contains("挂钩")) {
                    String seventeenPosition = mControlSeventeenPick.getPosition();
                    if (seventeenSize > 1) {
                        for (int i = 1; i < seventeenSize; i++) {
                            String ratioOfGpsPointCar = seventeenDataUsers.get(i).getRatioOfGpsPointCar();
                            Integer integerGpsPoint = Integer.valueOf(ratioOfGpsPointCar);
                            Integer integerSeventeenPosition = Integer.valueOf(seventeenPosition);
                            int seventeenNum = integerGpsPoint - integerSeventeenPosition;
                            Log.e("秦广帅", ratioOfGpsPointCar + "  " + seventeenPosition + "  ");
                            if (seventeenNum < 0) {
                                int seventeenZheng = -seventeenNum;
                                mListInteger.add(seventeenZheng);
                            } else {
                                mListInteger.add(seventeenNum);
                            }
                        }

                        //获取最小值下标
                        int seventeenMinIndex = getMinIndex(mListInteger);
                        Log.e("秦广帅seventeenMinIndex", seventeenMinIndex + "  ");
                        //对应数据库的下标
                        int numIndex = seventeenMinIndex + 1;
                        //判断最小值的位置
                        if (seventeenMinIndex % 2 == 0) {
                            Log.e("秦广帅", " 0 ");
                            mSeventeenParkDataDao.delete("seventeenparkcar", seventeenMinIndex + 2);
                            mSeventeenParkDataDao.delete("seventeenparkcar", seventeenMinIndex + 1);
                            for (int i = 1; i < seventeenSize; i++) {
                                String seventeenNum = seventeenDataUsers.get(i).getNum();
                                Integer integerSeventeenNum = Integer.valueOf(seventeenNum);
                                if (integerSeventeenNum > seventeenMinIndex + 1) {
                                    int seventeenZhai = integerSeventeenNum - 2;
                                    mSeventeenParkDataDao.updateData("seventeenparkcar", "" + seventeenZhai, integerSeventeenNum);
                                }
                            }
                        } else {
                            Log.e("秦广帅seventeenMinIndex", seventeenMinIndex + "  ");
                            mSeventeenParkDataDao.delete("seventeenparkcar", seventeenMinIndex + 1);
                            mSeventeenParkDataDao.delete("seventeenparkcar", seventeenMinIndex);
                            for (int i = 1; i < seventeenSize; i++) {
                                String seventeenNum = seventeenDataUsers.get(i).getNum();
                                Integer integerSeventeenNum = Integer.valueOf(seventeenNum);
                                if (integerSeventeenNum > seventeenMinIndex + 1) {
                                    int seventeenZhai = integerSeventeenNum - 2;
                                    mSeventeenParkDataDao.updateData("seventeenparkcar", "" + seventeenZhai, integerSeventeenNum);
                                }
                            }
                        }
                    } else {
                        mSeventeenPickLeft.setPosition("0");
                        mSeventeenPickRight.setPosition("0");
                    }
                } else if (controlseventeenpickrightName41.contains("摘钩") == true) {
                    Log.e("秦广帅摘钩", "摘钩");
                    mSeventeenParkCar.invalidate();
                }

                getSeventeenNum();

                mControlSeventeenPick.setName("0");
                mSeventeenParkCar.invalidate();
                break;
            case "18":
                //查看18道保存的数据是否只有挂钩
                String controleighteenpickrightName41 = mControlEighteenPick.getName();
                isSixTrack = true;
                List<DataUser> eighteenDataUsers = mEighteenParkDataDao.find();
                int eighteenSize = eighteenDataUsers.size();
                if (controleighteenpickrightName41.contains("摘钩") == false && controleighteenpickrightName41.contains("挂钩")) {
                    String eighteenPosition = mControlEighteenPick.getPosition();
                    if (eighteenSize > 1) {
                        for (int i = 1; i < eighteenSize; i++) {
                            String ratioOfGpsPointCar = eighteenDataUsers.get(i).getRatioOfGpsPointCar();
                            Integer integerGpsPoint = Integer.valueOf(ratioOfGpsPointCar);
                            Integer integerEighteenPosition = Integer.valueOf(eighteenPosition);
                            int eighteenNum = integerGpsPoint - integerEighteenPosition;
                            Log.e("秦广帅", ratioOfGpsPointCar + "  " + eighteenPosition + "  ");
                            if (eighteenNum < 0) {
                                int eighteenZheng = -eighteenNum;
                                mListInteger.add(eighteenZheng);
                            } else {
                                mListInteger.add(eighteenNum);
                            }
                        }

                        //获取最小值下标
                        int eighteenMinIndex = getMinIndex(mListInteger);
                        Log.e("秦广帅eighteenMinIndex", eighteenMinIndex + "  ");
                        //对应数据库的下标
                        int numIndex = eighteenMinIndex + 1;
                        //判断最小值的位置
                        if (eighteenMinIndex % 2 == 0) {
                            Log.e("秦广帅", " 0 ");
                            mEighteenParkDataDao.delete("eighteenparkcar", eighteenMinIndex + 2);
                            mEighteenParkDataDao.delete("eighteenparkcar", eighteenMinIndex + 1);
                            for (int i = 1; i < eighteenSize; i++) {
                                String eighteenNum = eighteenDataUsers.get(i).getNum();
                                Integer integerEighteenNum = Integer.valueOf(eighteenNum);
                                if (integerEighteenNum > eighteenMinIndex + 1) {
                                    int eighteenZhai = integerEighteenNum - 2;
                                    mEighteenParkDataDao.updateData("eighteenparkcar", "" + eighteenZhai, integerEighteenNum);
                                }
                            }
                        } else {
                            Log.e("秦广帅eighteenMinIndex", eighteenMinIndex + "  ");
                            mEighteenParkDataDao.delete("eighteenparkcar", eighteenMinIndex + 1);
                            mEighteenParkDataDao.delete("eighteenparkcar", eighteenMinIndex);
                            for (int i = 1; i < eighteenSize; i++) {
                                String eighteenNum = eighteenDataUsers.get(i).getNum();
                                Integer integerEighteenNum = Integer.valueOf(eighteenNum);
                                if (integerEighteenNum > eighteenMinIndex + 1) {
                                    int eighteenZhai = integerEighteenNum - 2;
                                    mEighteenParkDataDao.updateData("eighteenparkcar", "" + eighteenZhai, integerEighteenNum);
                                }
                            }
                        }
                    } else {
                        mEighteenPickLeft.setPosition("0");
                        mEighteenPickRight.setPosition("0");
                    }
                } else if (controleighteenpickrightName41.contains("摘钩") == true) {
                    Log.e("秦广帅摘钩", "摘钩");
                    mEighteenParkCar.invalidate();
                }

                getEighteenNum();

                mControlEighteenPick.setName("0");
                mEighteenParkCar.invalidate();
                break;
            case "19":
                //查看19道保存的数据是否只有挂钩
                String controlnineteenpickrightName41 = mControlNineteenPick.getName();
                isSixTrack = true;
                List<DataUser> nineteenDataUsers = mNineteenParkDataDao.find();
                int nineteenSize = nineteenDataUsers.size();
                if (controlnineteenpickrightName41.contains("摘钩") == false && controlnineteenpickrightName41.contains("挂钩")) {
                    String nineteenPosition = mControlNineteenPick.getPosition();
                    if (nineteenSize > 1) {
                        for (int i = 1; i < nineteenSize; i++) {
                            String ratioOfGpsPointCar = nineteenDataUsers.get(i).getRatioOfGpsPointCar();
                            Integer integerGpsPoint = Integer.valueOf(ratioOfGpsPointCar);
                            Integer integerNineteenPosition = Integer.valueOf(nineteenPosition);
                            int nineteenNum = integerGpsPoint - integerNineteenPosition;
                            Log.e("秦广帅", ratioOfGpsPointCar + "  " + nineteenPosition + "  ");
                            if (nineteenNum < 0) {
                                int nineteenZheng = -nineteenNum;
                                mListInteger.add(nineteenZheng);
                            } else {
                                mListInteger.add(nineteenNum);
                            }
                        }

                        //获取最小值下标
                        int nineteenMinIndex = getMinIndex(mListInteger);
                        Log.e("秦广帅nineteenMinIndex", nineteenMinIndex + "  ");
                        //对应数据库的下标
                        int numIndex = nineteenMinIndex + 1;
                        //判断最小值的位置
                        if (nineteenMinIndex % 2 == 0) {
                            Log.e("秦广帅", " 0 ");
                            mNineteenParkDataDao.delete("nineteenparkcar", nineteenMinIndex + 2);
                            mNineteenParkDataDao.delete("nineteenparkcar", nineteenMinIndex + 1);
                            for (int i = 1; i < nineteenSize; i++) {
                                String nineteenNum = nineteenDataUsers.get(i).getNum();
                                Integer integerNineteenNum = Integer.valueOf(nineteenNum);
                                if (integerNineteenNum > nineteenMinIndex + 1) {
                                    int nineteenZhai = integerNineteenNum - 2;
                                    mNineteenParkDataDao.updateData("nineteenparkcar", "" + nineteenZhai, integerNineteenNum);
                                }
                            }
                        } else {
                            Log.e("秦广帅nineteenMinIndex", nineteenMinIndex + "  ");
                            mNineteenParkDataDao.delete("nineteenparkcar", nineteenMinIndex + 1);
                            mNineteenParkDataDao.delete("nineteenparkcar", nineteenMinIndex);
                            for (int i = 1; i < nineteenSize; i++) {
                                String nineteenNum = nineteenDataUsers.get(i).getNum();
                                Integer integerNineteenNum = Integer.valueOf(nineteenNum);
                                if (integerNineteenNum > nineteenMinIndex + 1) {
                                    int nineteenZhai = integerNineteenNum - 2;
                                    mNineteenParkDataDao.updateData("nineteenparkcar", "" + nineteenZhai, integerNineteenNum);
                                }
                            }
                        }
                    } else {
                        mNineteenPickLeft.setPosition("0");
                        mNineteenPickRight.setPosition("0");
                    }
                } else if (controlnineteenpickrightName41.contains("摘钩") == true) {
                    Log.e("秦广帅摘钩", "摘钩");
                    mNineteenParkCar.invalidate();
                }

                getNineteenNum();

                mControlNineteenPick.setName("0");
                mNineteenParkCar.invalidate();
                break;
        }
    }

    private void zhaigou() {
        mListNum.clear();
        mListInteger.clear();
        maxString = "";
        switch (mPeopleId2) {
            //1号调车员
            case "01":
                sixPerson();
                String total0 = "01-摘钩GPS-" + mLat61 + "-" + mLon61;
                sendMessage(mConversationId, total0);
                mControlTrack.setName(mGetGudaoOfGpsPoint2 + "");
                switch (mGetGudaoOfGpsPoint2) {
                    case 1:
                        //判断是否有停留车
                        List<DataUser> oneDataUsers = mOneDataDao.find();
                        int oneSize = oneDataUsers.size();
                        Log.e("秦广帅oneSize", oneSize + "");
                        String num1 = oneDataUsers.get(oneSize - 1).getNum();
                        Log.e("秦广帅num1", num1 + "");
                        Integer integerNum1 = Integer.valueOf(num1);
                        int integerSum = integerNum1 + 1;
                        Log.e("秦广帅integerSum", integerSum + "");

                        if (oneSize < 3) {
                            mOneDataDao.add(mGetGudaoOfGpsPoint2 + "", mLat6, mLon6, mGpsPoint2 + "", integerSum + "");
                        } else {
                            //站场布局
                            String name1 = mMain.getName();
                            Log.e("秦广帅name1", name1);
                            if (name1.equals("main")) {
                                //查看机车位置在停留车的哪一侧
                                String position = mStopcar.getPosition();
                                Double positonDouble = Double.valueOf(position);
                                Log.e("秦广帅position", position + "");
                                String onePickLeftPosition = mOnePickLeft.getPosition();
                                Double onePickLeftPositionDouble = Double.valueOf(onePickLeftPosition);
                                String onepickrightPosition = mOnepickright.getPosition();
                                Double onepickrightPositionDouble = Double.valueOf(onepickrightPosition);
                                String ratioOfGpsPointCar = oneDataUsers.get(1).getRatioOfGpsPointCar();
                                Double ratioOfGpsPointCarDouble = Double.valueOf(ratioOfGpsPointCar);
                                String ratioOfGpsPointCarEnd = oneDataUsers.get(2).getRatioOfGpsPointCar();
                                Double ratioOfGpsPointCarEndDouble = Double.valueOf(ratioOfGpsPointCarEnd);
                                Log.e("秦广帅", ratioOfGpsPointCar + "    " + ratioOfGpsPointCarEnd);
                                if (positonDouble < ratioOfGpsPointCarDouble && positonDouble < ratioOfGpsPointCarEndDouble) {
                                    for (int i = 1; i < oneSize; i++) {
                                        String ratioOfGpsPointCar1 = oneDataUsers.get(i).getRatioOfGpsPointCar();
                                        Integer integer = Integer.valueOf(ratioOfGpsPointCar1);
                                        mListInteger.add(integer);
                                    }

                                    if (oneSize > 1) {
                                        int minIndex = getMinIndex(mListInteger);
                                        Log.e("秦广帅minIndex", minIndex + "");
                                        mOneDataDao.updaeUser("oneparkcar", mGetGudaoOfGpsPoint2 + "", mLat6, mLon6, mGpsPoint2 + "", minIndex + 1);
                                    }
                                } else if (positonDouble > ratioOfGpsPointCarDouble && positonDouble > ratioOfGpsPointCarEndDouble) {
                                    for (int i = 1; i < oneSize; i++) {
                                        String ratioOfGpsPointCar1 = oneDataUsers.get(i).getRatioOfGpsPointCar();
                                        Integer integer = Integer.valueOf(ratioOfGpsPointCar1);
                                        mListInteger.add(integer);
                                    }

                                    if (oneSize > 1) {
                                        int maxIndex = getMaxIndex(mListInteger);
                                        Log.e("秦广帅maxIndex", maxIndex + "");
                                        mOneDataDao.updaeUser("oneparkcar", mGetGudaoOfGpsPoint2 + "", mLat6, mLon6, mGpsPoint2 + "", maxIndex + 1);
                                    }
                                }
                            } else if (name1.equals("changfeng")) {
                                for (int i = 1; i < oneSize; i++) {
                                    String ratioOfGpsPointCar = oneDataUsers.get(oneSize).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int sum = integerPointCar - integerGpsPistance2;
                                    if (sum < 0) {
                                        int sumZheng = -sum;
                                        mListNum.add(sumZheng);
                                    } else {
                                        mListNum.add(sum);
                                    }
                                }
                                if (mListNum.size() > 1) {
                                    //取出list里最小值的下标替换
                                    int minIndex = getMinIndex(mListNum);
                                    mOneDataDao.updaeUser("oneparkcar", mGetGudaoOfGpsPoint2 + "", mLat6, mLon6, mGpsPoint2 + "", minIndex + 1);
                                }
                            } else if (name1.equals("baili")) {
                                for (int i = 1; i < oneSize; i++) {
                                    String ratioOfGpsPointCar = oneDataUsers.get(oneSize).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int sum = integerPointCar - integerGpsPistance2;
                                    if (sum < 0) {
                                        int sumZheng = -sum;
                                        mListNum.add(sumZheng);
                                    } else {
                                        mListNum.add(sum);
                                    }
                                }
                                if (mListNum.size() > 1) {
                                    //取出list里最小值的下标替换
                                    int minIndex = getMinIndex(mListNum);
                                    mOneDataDao.updaeUser("oneparkcar", mGetGudaoOfGpsPoint2 + "", mLat6, mLon6, mGpsPoint2 + "", minIndex + 1);
                                }
                            }
                        }

                        /*String onePickLeftName = mOnePickLeft.getPosition();
                        String onepickrightName = mOnepickright.getPosition();
                        String takeOffTotal = mControlOnePick.getName();
                        mControlOnePick.setName(takeOffTotal + "摘钩");
                        if (onePickLeftName.equals("0") || onepickrightName.equals("0")) {
                            if (onePickLeftName.equals("0") && !onepickrightName.equals("0")) {
                                oneLeft(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                            } else if (!onePickLeftName.equals("0") && onepickrightName.equals("0")) {
                                oneRight(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                            }
                        } else {
                            String track = mStopcar.getTrack();
                            String position = mStopcar.getPosition();
                            String lat = mStopcar.getLat();
                            String lon = mStopcar.getLon();
                            //站场布局
                            String name1 = mMain.getName();
                            Log.e("秦广帅name1", name1);
                            if (name1.equals("main")) {
                                //查看机车位置在停留车的哪一侧
                                Double positonDouble = Double.valueOf(position);
                                String onePickLeftPosition = mOnePickLeft.getPosition();
                                Double onePickLeftPositionDouble = Double.valueOf(onePickLeftPosition);
                                String onepickrightPosition = mOnepickright.getPosition();
                                Double onepickrightPositionDouble = Double.valueOf(onepickrightPosition);
                                if (positonDouble < onePickLeftPositionDouble) {
                                    oneLeft(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                                } else if (positonDouble > onepickrightPositionDouble) {
                                    oneRight(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                                }
                            } else if (name1.equals("changfeng")) {
                                oneLeft(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                            } else if (name1.equals("baili")) {
                                oneRight(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                            }
                        }*/
                        break;
                    case 2:
                        //判断是否有停留车
                        String twoPickLeftName = mTwoPickLeft.getPosition();
                        String twopickrightName = mTwopickright.getPosition();
                        String takeOffTotal2 = mControlTwoPick.getName();
                        mControlTwoPick.setName(takeOffTotal2 + "摘钩");
                        if (twoPickLeftName.equals("0") || twopickrightName.equals("0")) {
                            if (twoPickLeftName.equals("0") && !twopickrightName.equals("0")) {
                                twoLeft(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                            } else if (!twoPickLeftName.equals("0") && twopickrightName.equals("0")) {
                                twoRight(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                            }
                        } else {
                            String track = mStopcar.getTrack();
                            String position = mStopcar.getPosition();
                            String lat = mStopcar.getLat();
                            String lon = mStopcar.getLon();
                            //站场布局
                            String name1 = mMain.getName();
                            if (name1.equals("main")) {
                                //查看机车位置在停留车的哪一侧
                                Double positonDouble = Double.valueOf(position);
                                String twoPickLeftPosition = mTwoPickLeft.getPosition();
                                Double twoPickLeftPositionDouble = Double.valueOf(twoPickLeftPosition);
                                String twopickrightPosition = mTwopickright.getPosition();
                                Double twopickrightPositionDouble = Double.valueOf(twopickrightPosition);
                                if (positonDouble < twoPickLeftPositionDouble) {
                                    twoLeft(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                                } else if (positonDouble > twopickrightPositionDouble) {
                                    twoRight(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                                }
                            } else if (name1.equals("changfeng")) {
                                twoLeft(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                            } else if (name1.equals("baili")) {
                                twoRight(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                            }
                        }
                        break;
                    case 3:
                        //判断是否有停留车
                        String threePickLeftName = mThreePickLeft.getPosition();
                        String threepickrightName = mThreepickright.getPosition();
                        String takeOffTotal3 = mControlThreePick.getName();
                        mControlThreePick.setName(takeOffTotal3 + "摘钩");
                        if (threePickLeftName.equals("0") || threepickrightName.equals("0")) {
                            if (threePickLeftName.equals("0") && !threepickrightName.equals("0")) {
                                threeLeft(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                            } else if (!threePickLeftName.equals("0") && threepickrightName.equals("0")) {
                                threeRight(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                            }
                        } else {
                            String track = mStopcar.getTrack();
                            String position = mStopcar.getPosition();
                            String lat = mStopcar.getLat();
                            String lon = mStopcar.getLon();
                            //站场布局
                            String name1 = mMain.getName();
                            if (name1.equals("main")) {
                                //查看机车位置在停留车的哪一侧
                                Double positonDouble = Double.valueOf(position);
                                String threePickLeftPosition = mThreePickLeft.getPosition();
                                Double threePickLeftPositionDouble = Double.valueOf(threePickLeftPosition);
                                String threepickrightPosition = mThreepickright.getPosition();
                                Double threepickrightPositionDouble = Double.valueOf(threepickrightPosition);
                                if (positonDouble < threePickLeftPositionDouble) {
                                    threeLeft(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                                } else if (positonDouble > threepickrightPositionDouble) {
                                    threeRight(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                                }
                            } else if (name1.equals("changfeng")) {
                                threeLeft(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                            } else if (name1.equals("baili")) {
                                threeRight(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                            }
                        }
                        break;
                    case 4:
                        //判断是否有停留车
                        String fourPickLeftName = mFourPickLeft.getPosition();
                        String fourpickrightName = mFourpickright.getPosition();
                        String takeOffTotal4 = mControlFourPick.getName();
                        mControlFourPick.setName(takeOffTotal4 + "摘钩");
                        if (fourPickLeftName.equals("0") || fourpickrightName.equals("0")) {
                            if (fourPickLeftName.equals("0") && !fourpickrightName.equals("0")) {
                                fourLeft(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                            } else if (!fourPickLeftName.equals("0") && fourpickrightName.equals("0")) {
                                fourRight(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                            }
                        } else {
                            String track = mStopcar.getTrack();
                            String position = mStopcar.getPosition();
                            String lat = mStopcar.getLat();
                            String lon = mStopcar.getLon();
                            //站场布局
                            String name1 = mMain.getName();
                            if (name1.equals("main")) {
                                //查看机车位置在停留车的哪一侧
                                Double positonDouble = Double.valueOf(position);
                                String fourPickLeftPosition = mFourPickLeft.getPosition();
                                Double fourPickLeftPositionDouble = Double.valueOf(fourPickLeftPosition);
                                String fourpickrightPosition = mFourpickright.getPosition();
                                Double fourpickrightPositionDouble = Double.valueOf(fourpickrightPosition);
                                if (positonDouble < fourPickLeftPositionDouble) {
                                    fourLeft(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                                } else if (positonDouble > fourpickrightPositionDouble) {
                                    fourRight(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                                }
                            } else if (name1.equals("changfeng")) {
                                fourLeft(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                            } else if (name1.equals("baili")) {
                                fourRight(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                            }
                        }
                        break;
                    case 5:
                        //判断是否有停留车
                        String fivePickLeftName = mFivePickLeft.getPosition();
                        String fivepickrightName = mFivepickright.getPosition();
                        String takeOffTotal5 = mControlFivePick.getName();
                        mControlFivePick.setName(takeOffTotal5 + "摘钩");
                        if (fivePickLeftName.equals("0") || fivepickrightName.equals("0")) {
                            if (fivePickLeftName.equals("0") && !fivepickrightName.equals("0")) {
                                fiveLeft(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                            } else if (!fivePickLeftName.equals("0") && fivepickrightName.equals("0")) {
                                fiveRight(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                            }
                        } else {
                            String track = mStopcar.getTrack();
                            String position = mStopcar.getPosition();
                            String lat = mStopcar.getLat();
                            String lon = mStopcar.getLon();
                            //站场布局
                            String name1 = mMain.getName();
                            if (name1.equals("main")) {
                                //查看机车位置在停留车的哪一侧
                                Double positonDouble = Double.valueOf(position);
                                String fivePickLeftPosition = mFivePickLeft.getPosition();
                                Double fivePickLeftPositionDouble = Double.valueOf(fivePickLeftPosition);
                                String fivepickrightPosition = mFivepickright.getPosition();
                                Double fivepickrightPositionDouble = Double.valueOf(fivepickrightPosition);
                                if (positonDouble < fivePickLeftPositionDouble) {
                                    fiveLeft(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                                } else if (positonDouble > fivepickrightPositionDouble) {
                                    fiveRight(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                                }
                            } else if (name1.equals("changfeng")) {
                                fiveLeft(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                            } else if (name1.equals("baili")) {
                                fiveRight(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                            }
                        }
                        break;
                    case 6:
                        Log.e("秦广帅", isSixTrack + "");
                        String takeOffTotal6 = mControlSixPick.getName();
                        mControlSixPick.setName(takeOffTotal6 + "摘钩");
                        List<DataUser> sixDataUsers = mSixParkDataDao.find();
                        int sixSize = sixDataUsers.size();
                        String num = sixDataUsers.get(sixSize - 1).getNum();
                        Integer integerNum = Integer.valueOf(num);
                        int sum = integerNum + 1;
                        Log.e("秦广帅sixSize", sixSize + "    " + sum);
                        if (sixSize > 3) {
                            for (int i = 1; i < sixSize; i++) {
                                String lat = sixDataUsers.get(i).getLat();
                                String lon = sixDataUsers.get(i).getLon();
                                String ratioOfGpsPointCar = sixDataUsers.get(i).getRatioOfGpsPointCar();
                                Log.e("秦广帅lat", lat + "    " + lon + "    " + ratioOfGpsPointCar);
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat6), Double.valueOf(mLon6));
                                Log.e("秦广帅distance", distance + "    ");
                                Integer ratioOfGpsPointCarInteger = Integer.valueOf(ratioOfGpsPointCar);
                                mListNum.add(ratioOfGpsPointCarInteger);
                            }
                            if (mListNum.size() > 0) {
                                mMinIndex = getMinIndex(mListNum);
                                mMaxIndex = getMaxIndex(mListNum);
                            }
                            String sixPickLeftPosition = mSixPickLeft.getPosition();
                            String sixpickrightPosition = mSixpickright.getPosition();
                            if (Double.valueOf(sixPickLeftPosition) < mGpsPistance2 && mGpsPistance2 < Double.valueOf(sixpickrightPosition)) {
                                //查看机车位置在停留车的哪一侧
                                String position = mStopcar.getPosition();
                                Double positonDouble = Double.valueOf(position);
                                if (positonDouble < Double.valueOf(sixPickLeftPosition) && positonDouble < Double.valueOf(sixpickrightPosition)) {
                                    mSixParkDataDao.updaeUser("sixparkcar", mGetGudaoOfGpsPoint2 + "", mLat6, mLon6, mGpsPoint2 + "", mMinIndex + 1);
                                } else if (positonDouble > Double.valueOf(sixPickLeftPosition) && positonDouble > Double.valueOf(sixpickrightPosition)) {
                                    mSixParkDataDao.updaeUser("sixparkcar", mGetGudaoOfGpsPoint2 + "", mLat6, mLon6, mGpsPoint2 + "", mMaxIndex + 1);
                                }
                            } else {
                                for (int i = 1; i < sixSize; i++) {
                                    String lat = sixDataUsers.get(i).getLat();
                                    String lon = sixDataUsers.get(i).getLon();
                                    String ratioOfGpsPointCar = sixDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListInteger.add(i2);
                                    } else {
                                        mListInteger.add(i1);
                                    }
                                    //计算紧急停车位置与数据库里的每一条数据的距离
                                    double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat21), Double.valueOf(mLon21));
                                    if (distance > 11) {
                                        maxString += "0";
                                    } else {
                                        maxString += "1";
                                    }
                                }
                                if (maxString.contains("1") == false && maxString.contains("0")) {
                                    mSixParkDataDao.add(mGetGudaoOfGpsPoint2 + "", mLat6, mLon6, mGpsPoint2 + "", sum + "");
                                } else {
                                    if (mListInteger.size() > 0) {
                                        int minIndex = getMinIndex(mListInteger);
                                        mSixParkDataDao.updaeUser("sixparkcar", mGetGudaoOfGpsPoint2 + "", mLat6, mLon6, mGpsPoint2 + "", minIndex + 1);
                                    }
                                }
                            }
                        } else {
                            mSixParkDataDao.add(mGetGudaoOfGpsPoint2 + "", mLat6, mLon6, mGpsPoint2 + "", sum + "");
                        }

                        isSixTrack = true;
                        break;
                    case 7:
                        String takeOffTotal7 = mControlSevenPick.getName();
                        mControlSevenPick.setName(takeOffTotal7 + "摘钩");
                        List<DataUser> sevenDataUsers = mSevenParkDataDao.find();
                        int sevenSize = sevenDataUsers.size();
                        String sevenNum = sevenDataUsers.get(sevenSize - 1).getNum();
                        Integer integerSevenNum = Integer.valueOf(sevenNum);
                        int sevenSum = integerSevenNum + 1;
                        if (sevenSize > 2) {
                            for (int i = 1; i < sevenSize; i++) {
                                String lat = sevenDataUsers.get(i).getLat();
                                String lon = sevenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat21), Double.valueOf(mLon21));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mSevenParkDataDao.add(mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", sevenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = sevenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }
                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mSevenParkDataDao.updaeUser("sevenparkcar", mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mSevenParkDataDao.add(mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", sevenSum + "");
                        }
                        break;
                    case 8:
                        //判断是否有停留车
                        String eightPickLeftName = mEightPickLeft.getPosition();
                        String eightpickrightName = mEightpickright.getPosition();
                        String takeOffTotal8 = mControlEightPick.getName();
                        mControlFivePick.setName(takeOffTotal8 + "摘钩");
                        if (eightPickLeftName.equals("0") || eightpickrightName.equals("0")) {
                            if (eightPickLeftName.equals("0") && !eightpickrightName.equals("0")) {
                                eightLeft(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                            } else if (!eightPickLeftName.equals("0") && eightpickrightName.equals("0")) {
                                eightRight(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                            }
                        } else {
                            String track = mStopcar.getTrack();
                            String position = mStopcar.getPosition();
                            String lat = mStopcar.getLat();
                            String lon = mStopcar.getLon();
                            //站场布局
                            String name1 = mMain.getName();
                            if (name1.equals("main")) {
                                //查看机车位置在停留车的哪一侧
                                Double positonDouble = Double.valueOf(position);
                                String eightPickLeftPosition = mEightPickLeft.getPosition();
                                Double eightPickLeftPositionDouble = Double.valueOf(eightPickLeftPosition);
                                String eightpickrightPosition = mEightpickright.getPosition();
                                Double eightpickrightPositionDouble = Double.valueOf(eightpickrightPosition);
                                if (positonDouble < eightPickLeftPositionDouble) {
                                    eightLeft(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                                } else if (positonDouble > eightpickrightPositionDouble) {
                                    eightRight(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                                }
                            } else if (name1.equals("changfeng")) {
                                eightLeft(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                            } else if (name1.equals("baili")) {
                                eightRight(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                            }
                        }
                        break;
                    case 9:
                        String takeOffTotal9 = mControlNinePick.getName();
                        mControlNinePick.setName(takeOffTotal9 + "摘钩");
                        List<DataUser> nineDataUsers = mNineParkDataDao.find();
                        int nineSize = nineDataUsers.size();
                        String nineNum = nineDataUsers.get(nineSize - 1).getNum();
                        Integer integerNineNum = Integer.valueOf(nineNum);
                        int nineSum = integerNineNum + 1;
                        if (nineSize > 2) {
                            for (int i = 1; i < nineSize; i++) {
                                String lat = nineDataUsers.get(i).getLat();
                                String lon = nineDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat21), Double.valueOf(mLon21));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mNineParkDataDao.add(mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", nineSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = nineDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }
                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mNineParkDataDao.updaeUser("nineparkcar", mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", minIndex + 1);
                            }
                        } else {
                            mNineParkDataDao.add(mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", nineSum + "");
                        }
                        break;
                    case 10:
                        //判断是否有停留车
                        String tenPickLeftName = mTenPickLeft.getPosition();
                        String tenpickrightName = mTenpickright.getPosition();
                        String takeOffTotal10 = mControlEightPick.getName();
                        mControlFivePick.setName(takeOffTotal10 + "摘钩");
                        if (tenPickLeftName.equals("0") || tenpickrightName.equals("0")) {
                            if (tenPickLeftName.equals("0") && !tenpickrightName.equals("0")) {
                                tenLeft(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                            } else if (!tenPickLeftName.equals("0") && tenpickrightName.equals("0")) {
                                tenRight(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                            }
                        } else {
                            String track = mStopcar.getTrack();
                            String position = mStopcar.getPosition();
                            String lat = mStopcar.getLat();
                            String lon = mStopcar.getLon();
                            //站场布局
                            String name1 = mMain.getName();
                            if (name1.equals("main")) {
                                //查看机车位置在停留车的哪一侧
                                Double positonDouble = Double.valueOf(position);
                                String tenPickLeftPosition = mEightPickLeft.getPosition();
                                Double tenPickLeftPositionDouble = Double.valueOf(tenPickLeftPosition);
                                String tenpickrightPosition = mEightpickright.getPosition();
                                Double tenpickrightPositionDouble = Double.valueOf(tenpickrightPosition);
                                if (positonDouble < tenPickLeftPositionDouble) {
                                    tenLeft(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                                } else if (positonDouble > tenpickrightPositionDouble) {
                                    tenRight(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                                }
                            } else if (name1.equals("changfeng")) {
                                tenLeft(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                            } else if (name1.equals("baili")) {
                                tenRight(mGpsPistance2 + "", mLat21, mLon21, mGetGudaoOfGpsPoint2 + "");
                            }
                        }
                        break;
                    case 11:
                        String takeOffTotal11 = mControlElevenPick.getName();
                        mControlElevenPick.setName(takeOffTotal11 + "摘钩");
                        List<DataUser> elevenDataUsers = mElevenParkDataDao.find();
                        int elevenSize = elevenDataUsers.size();
                        String elevenNum = elevenDataUsers.get(elevenSize - 1).getNum();
                        Integer integerElevenNum = Integer.valueOf(elevenNum);
                        int elevenSum = integerElevenNum + 1;
                        if (elevenSize > 2) {
                            for (int i = 1; i < elevenSize; i++) {
                                String lat = elevenDataUsers.get(i).getLat();
                                String lon = elevenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat21), Double.valueOf(mLon21));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mElevenParkDataDao.add(mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", elevenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = elevenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }
                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mElevenParkDataDao.updaeUser("elevenparkcar", mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mElevenParkDataDao.add(mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", elevenSum + "");
                        }
                        break;
                    case 12:
                        String takeOffTotal12 = mControlTwelvePick.getName();
                        mControlTwelvePick.setName(takeOffTotal12 + "摘钩");
                        List<DataUser> twelveDataUsers = mTwelveParkDataDao.find();
                        int twelveSize = twelveDataUsers.size();
                        String twelveNum = twelveDataUsers.get(twelveSize - 1).getNum();
                        Integer integerTwelveNum = Integer.valueOf(twelveNum);
                        int twelveSum = integerTwelveNum + 1;
                        if (twelveSize > 2) {
                            for (int i = 1; i < twelveSize; i++) {
                                String lat = twelveDataUsers.get(i).getLat();
                                String lon = twelveDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat21), Double.valueOf(mLon21));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mTwelveParkDataDao.add(mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", twelveSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = twelveDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mTwelveParkDataDao.updaeUser("twelveparkcar", mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mTwelveParkDataDao.add(mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", twelveSum + "");
                        }
                        break;
                    case 13:
                        String takeOffTotal13 = mControlThirteenPick.getName();
                        mControlThirteenPick.setName(takeOffTotal13 + "摘钩");
                        List<DataUser> thirteenDataUsers = mThirteenParkDataDao.find();
                        int thirteenSize = thirteenDataUsers.size();
                        String thirteenNum = thirteenDataUsers.get(thirteenSize - 1).getNum();
                        Integer integerThirteenNum = Integer.valueOf(thirteenNum);
                        int thirteenSum = integerThirteenNum + 1;
                        if (thirteenSize > 2) {
                            for (int i = 1; i < thirteenSize; i++) {
                                String lat = thirteenDataUsers.get(i).getLat();
                                String lon = thirteenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat21), Double.valueOf(mLon21));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mThirteenParkDataDao.add(mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", thirteenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = thirteenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mThirteenParkDataDao.updaeUser("thirteenparkcar", mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mThirteenParkDataDao.add(mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", thirteenSum + "");
                        }
                        break;
                    case 14:
                        String takeOffTotal14 = mControlFourteenPick.getName();
                        mControlFourteenPick.setName(takeOffTotal14 + "摘钩");
                        List<DataUser> fourteenDataUsers = mFourteenParkDataDao.find();
                        int fourteenSize = fourteenDataUsers.size();
                        String fourteenNum = fourteenDataUsers.get(fourteenSize - 1).getNum();
                        Integer integerFourteenNum = Integer.valueOf(fourteenNum);
                        int fourteenSum = integerFourteenNum + 1;
                        if (fourteenSize > 2) {
                            for (int i = 1; i < fourteenSize; i++) {
                                String lat = fourteenDataUsers.get(i).getLat();
                                String lon = fourteenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat21), Double.valueOf(mLon21));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mFourteenParkDataDao.add(mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", fourteenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = fourteenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mFourteenParkDataDao.updaeUser("fourteenparkcar", mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", minIndex + 1);
                            }
                        } else {
                            mFourteenParkDataDao.add(mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", fourteenSum + "");
                        }
                        break;
                    case 15:
                        String takeOffTotal15 = mControlFifteenPick.getName();
                        mControlFifteenPick.setName(takeOffTotal15 + "摘钩");
                        List<DataUser> fifteenDataUsers = mFifteenParkDataDao.find();
                        int fifteenSize = fifteenDataUsers.size();
                        String fifteenNum = fifteenDataUsers.get(fifteenSize - 1).getNum();
                        Integer integerFifteenNum = Integer.valueOf(fifteenNum);
                        int fifteenSum = integerFifteenNum + 1;
                        if (fifteenSize > 2) {
                            for (int i = 1; i < fifteenSize; i++) {
                                String lat = fifteenDataUsers.get(i).getLat();
                                String lon = fifteenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat21), Double.valueOf(mLon21));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mFifteenParkDataDao.add(mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", fifteenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = fifteenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mFifteenParkDataDao.updaeUser("fifteenparkcar", mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mFifteenParkDataDao.add(mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", fifteenSum + "");
                        }
                        break;
                    case 16:
                        String takeOffTotal16 = mControlSixteenPick.getName();
                        mControlSixteenPick.setName(takeOffTotal16 + "摘钩");
                        List<DataUser> sixteenDataUsers = mSixteenParkDataDao.find();
                        int sixteenSize = sixteenDataUsers.size();
                        String sixteenNum = sixteenDataUsers.get(sixteenSize - 1).getNum();
                        Integer integerSixteenNum = Integer.valueOf(sixteenNum);
                        int sixteenSum = integerSixteenNum + 1;
                        if (sixteenSize > 2) {
                            for (int i = 1; i < sixteenSize; i++) {
                                String lat = sixteenDataUsers.get(i).getLat();
                                String lon = sixteenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat21), Double.valueOf(mLon21));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mSixteenParkDataDao.add(mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", sixteenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = sixteenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mSixteenParkDataDao.updaeUser("sixteenparkcar", mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mSixteenParkDataDao.add(mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", sixteenSum + "");
                        }
                        break;
                    case 17:
                        String takeOffTotal17 = mControlSeventeenPick.getName();
                        mControlSeventeenPick.setName(takeOffTotal17 + "摘钩");
                        List<DataUser> seventeenDataUsers = mSeventeenParkDataDao.find();
                        int seventeenSize = seventeenDataUsers.size();
                        String seventeenNum = seventeenDataUsers.get(seventeenSize - 1).getNum();
                        Integer integerSeventeenNum = Integer.valueOf(seventeenNum);
                        int seventeenSum = integerSeventeenNum + 1;
                        if (seventeenSize > 2) {
                            for (int i = 1; i < seventeenSize; i++) {
                                String lat = seventeenDataUsers.get(i).getLat();
                                String lon = seventeenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat21), Double.valueOf(mLon21));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mSeventeenParkDataDao.add(mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", seventeenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = seventeenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mSeventeenParkDataDao.updaeUser("seventeenparkcar", mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mSeventeenParkDataDao.add(mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", seventeenSum + "");
                        }
                        break;
                    case 18:
                        String takeOffTotal18 = mControlEighteenPick.getName();
                        mControlEighteenPick.setName(takeOffTotal18 + "摘钩");
                        List<DataUser> eighteenDataUsers = mEighteenParkDataDao.find();
                        int eighteenSize = eighteenDataUsers.size();
                        String eighteenNum = eighteenDataUsers.get(eighteenSize - 1).getNum();
                        Integer integerEighteenNum = Integer.valueOf(eighteenNum);
                        int eighteenSum = integerEighteenNum + 1;
                        if (eighteenSize > 2) {
                            for (int i = 1; i < eighteenSize; i++) {
                                String lat = eighteenDataUsers.get(i).getLat();
                                String lon = eighteenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat21), Double.valueOf(mLon21));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mEighteenParkDataDao.add(mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", eighteenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = eighteenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mEighteenParkDataDao.updaeUser("eighteenparkcar", mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", minIndex + 1);
                            }
                        } else {
                            mEighteenParkDataDao.add(mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", eighteenSum + "");
                        }
                        break;
                    case 19:
                        String takeOffTotal19 = mControlNineteenPick.getName();
                        mControlNineteenPick.setName(takeOffTotal19 + "摘钩");
                        List<DataUser> nineteenDataUsers = mNineteenParkDataDao.find();
                        int nineteenSize = nineteenDataUsers.size();
                        String nineteenNum = nineteenDataUsers.get(nineteenSize - 1).getNum();
                        Integer integerNineteenNum = Integer.valueOf(nineteenNum);
                        int nineteenSum = integerNineteenNum + 1;
                        if (nineteenSize > 2) {
                            for (int i = 1; i < nineteenSize; i++) {
                                String lat = nineteenDataUsers.get(i).getLat();
                                String lon = nineteenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat21), Double.valueOf(mLon21));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mNineteenParkDataDao.add(mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", nineteenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = nineteenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mNineteenParkDataDao.updaeUser("nineteenparkcar", mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", minIndex + 1);
                            }
                        } else {
                            mNineteenParkDataDao.add(mGetGudaoOfGpsPoint2 + "", mLat21, mLon21, mGpsPoint2 + "", nineteenSum + "");
                        }
                        break;
                }
                break;
            case "02":
                sevenPerson();
                String total1 = "02-摘钩GPS-" + mLat61 + "-" + mLon61;
                sendMessage(mConversationId, total1);
                switch (mGetGudaoOfGpsPoint3) {
                    case 1:
                        //判断是否有停留车
                        String onePickLeftName = mOnePickLeft.getPosition();
                        String onepickrightName = mOnepickright.getPosition();
                        String takeOffTotal = mControlOnePick.getName();
                        mControlOnePick.setName(takeOffTotal + "摘钩");
                        if (onePickLeftName.equals("0") || onepickrightName.equals("0")) {
                            if (onePickLeftName.equals("0") && !onepickrightName.equals("0")) {
                                oneLeft(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                            } else if (!onePickLeftName.equals("0") && onepickrightName.equals("0")) {
                                oneRight(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                            }
                        } else {
                            String track = mStopcar.getTrack();
                            String position = mStopcar.getPosition();
                            String lat = mStopcar.getLat();
                            String lon = mStopcar.getLon();
                            //站场布局
                            String name1 = mMain.getName();
                            if (name1.equals("main")) {
                                //查看机车位置在停留车的哪一侧
                                Double positonDouble = Double.valueOf(position);
                                String onePickLeftPosition = mOnePickLeft.getPosition();
                                Double onePickLeftPositionDouble = Double.valueOf(onePickLeftPosition);
                                String onepickrightPosition = mOnepickright.getPosition();
                                Double onepickrightPositionDouble = Double.valueOf(onepickrightPosition);
                                if (positonDouble < onePickLeftPositionDouble) {
                                    oneLeft(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                                } else if (positonDouble > onepickrightPositionDouble) {
                                    oneRight(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                                }
                            } else if (name1.equals("changfeng")) {
                                oneLeft(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                            } else if (name1.equals("baili")) {
                                oneRight(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                            }
                        }
                        break;
                    case 2:
                        //判断是否有停留车
                        String twoPickLeftName = mTwoPickLeft.getPosition();
                        String twopickrightName = mTwopickright.getPosition();
                        String takeOffTotal2 = mControlTwoPick.getName();
                        mControlTwoPick.setName(takeOffTotal2 + "摘钩");
                        if (twoPickLeftName.equals("0") || twopickrightName.equals("0")) {
                            if (twoPickLeftName.equals("0") && !twopickrightName.equals("0")) {
                                twoLeft(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                            } else if (!twoPickLeftName.equals("0") && twopickrightName.equals("0")) {
                                twoRight(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                            }
                        } else {
                            String track = mStopcar.getTrack();
                            String position = mStopcar.getPosition();
                            String lat = mStopcar.getLat();
                            String lon = mStopcar.getLon();
                            //站场布局
                            String name1 = mMain.getName();
                            if (name1.equals("main")) {
                                //查看机车位置在停留车的哪一侧
                                Double positonDouble = Double.valueOf(position);
                                String twoPickLeftPosition = mTwoPickLeft.getPosition();
                                Double twoPickLeftPositionDouble = Double.valueOf(twoPickLeftPosition);
                                String twopickrightPosition = mTwopickright.getPosition();
                                Double twopickrightPositionDouble = Double.valueOf(twopickrightPosition);
                                if (positonDouble < twoPickLeftPositionDouble) {
                                    twoLeft(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                                } else if (positonDouble > twopickrightPositionDouble) {
                                    twoRight(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                                }
                            } else if (name1.equals("changfeng")) {
                                twoLeft(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                            } else if (name1.equals("baili")) {
                                twoRight(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                            }
                        }
                        break;
                    case 3:
                        //判断是否有停留车
                        String threePickLeftName = mThreePickLeft.getPosition();
                        String threepickrightName = mThreepickright.getPosition();
                        String takeOffTotal3 = mControlThreePick.getName();
                        mControlThreePick.setName(takeOffTotal3 + "摘钩");
                        if (threePickLeftName.equals("0") || threepickrightName.equals("0")) {
                            if (threePickLeftName.equals("0") && !threepickrightName.equals("0")) {
                                threeLeft(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                            } else if (!threePickLeftName.equals("0") && threepickrightName.equals("0")) {
                                threeRight(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                            }
                        } else {
                            String track = mStopcar.getTrack();
                            String position = mStopcar.getPosition();
                            String lat = mStopcar.getLat();
                            String lon = mStopcar.getLon();
                            //站场布局
                            String name1 = mMain.getName();
                            if (name1.equals("main")) {
                                //查看机车位置在停留车的哪一侧
                                Double positonDouble = Double.valueOf(position);
                                String threePickLeftPosition = mThreePickLeft.getPosition();
                                Double threePickLeftPositionDouble = Double.valueOf(threePickLeftPosition);
                                String threepickrightPosition = mThreepickright.getPosition();
                                Double threepickrightPositionDouble = Double.valueOf(threepickrightPosition);
                                if (positonDouble < threePickLeftPositionDouble) {
                                    threeLeft(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                                } else if (positonDouble > threepickrightPositionDouble) {
                                    threeRight(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                                }
                            } else if (name1.equals("changfeng")) {
                                threeLeft(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                            } else if (name1.equals("baili")) {
                                threeRight(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                            }
                        }
                        break;
                    case 4:
                        //判断是否有停留车
                        String fourPickLeftName = mFourPickLeft.getPosition();
                        String fourpickrightName = mFourpickright.getPosition();
                        String takeOffTotal4 = mControlFourPick.getName();
                        mControlFourPick.setName(takeOffTotal4 + "摘钩");
                        if (fourPickLeftName.equals("0") || fourpickrightName.equals("0")) {
                            if (fourPickLeftName.equals("0") && !fourpickrightName.equals("0")) {
                                fourLeft(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                            } else if (!fourPickLeftName.equals("0") && fourpickrightName.equals("0")) {
                                fourRight(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                            }
                        } else {
                            String track = mStopcar.getTrack();
                            String position = mStopcar.getPosition();
                            String lat = mStopcar.getLat();
                            String lon = mStopcar.getLon();
                            //站场布局
                            String name1 = mMain.getName();
                            if (name1.equals("main")) {
                                //查看机车位置在停留车的哪一侧
                                Double positonDouble = Double.valueOf(position);
                                String fourPickLeftPosition = mFourPickLeft.getPosition();
                                Double fourPickLeftPositionDouble = Double.valueOf(fourPickLeftPosition);
                                String fourpickrightPosition = mFourpickright.getPosition();
                                Double fourpickrightPositionDouble = Double.valueOf(fourpickrightPosition);
                                if (positonDouble < fourPickLeftPositionDouble) {
                                    fourLeft(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                                } else if (positonDouble > fourpickrightPositionDouble) {
                                    fourRight(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                                }
                            } else if (name1.equals("changfeng")) {
                                fourLeft(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                            } else if (name1.equals("baili")) {
                                fourRight(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                            }
                        }
                        break;
                    case 5:
                        //判断是否有停留车
                        String fivePickLeftName = mFivePickLeft.getPosition();
                        String fivepickrightName = mFivepickright.getPosition();
                        String takeOffTotal5 = mControlFivePick.getName();
                        mControlFivePick.setName(takeOffTotal5 + "摘钩");
                        if (fivePickLeftName.equals("0") || fivepickrightName.equals("0")) {
                            if (fivePickLeftName.equals("0") && !fivepickrightName.equals("0")) {
                                fiveLeft(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                            } else if (!fivePickLeftName.equals("0") && fivepickrightName.equals("0")) {
                                fiveRight(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                            }
                        } else {
                            String track = mStopcar.getTrack();
                            String position = mStopcar.getPosition();
                            String lat = mStopcar.getLat();
                            String lon = mStopcar.getLon();
                            //站场布局
                            String name1 = mMain.getName();
                            if (name1.equals("main")) {
                                //查看机车位置在停留车的哪一侧
                                Double positonDouble = Double.valueOf(position);
                                String fivePickLeftPosition = mFivePickLeft.getPosition();
                                Double fivePickLeftPositionDouble = Double.valueOf(fivePickLeftPosition);
                                String fivepickrightPosition = mFivepickright.getPosition();
                                Double fivepickrightPositionDouble = Double.valueOf(fivepickrightPosition);
                                if (positonDouble < fivePickLeftPositionDouble) {
                                    fiveLeft(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                                } else if (positonDouble > fivepickrightPositionDouble) {
                                    fiveRight(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                                }
                            } else if (name1.equals("changfeng")) {
                                fiveLeft(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                            } else if (name1.equals("baili")) {
                                fiveRight(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                            }
                        }
                        break;
                    case 6:
                        String takeOffTotal6 = mControlSixPick.getName();
                        mControlSixPick.setName(takeOffTotal6 + "摘钩");
                        List<DataUser> sixDataUsers = mSixParkDataDao.find();
                        int sixSize = sixDataUsers.size();
                        String num = sixDataUsers.get(sixSize - 1).getNum();
                        Integer integerNum = Integer.valueOf(num);
                        int sum = integerNum + 1;
                        if (sixSize > 2) {
                            for (int i = 1; i < sixSize; i++) {
                                String lat = sixDataUsers.get(i).getLat();
                                String lon = sixDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat31), Double.valueOf(mLon21));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mSixParkDataDao.add(mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", sum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = sixDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mSixParkDataDao.updaeUser("sixparkcar", mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mSixParkDataDao.add(mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", sum + "");
                        }
                        break;
                    case 7:
                        String takeOffTotal7 = mControlSevenPick.getName();
                        mControlSevenPick.setName(takeOffTotal7 + "摘钩");
                        List<DataUser> sevenDataUsers = mSevenParkDataDao.find();
                        int sevenSize = sevenDataUsers.size();
                        String sevenNum = sevenDataUsers.get(sevenSize - 1).getNum();
                        Integer integerSevenNum = Integer.valueOf(sevenNum);
                        int sevenSum = integerSevenNum + 1;
                        if (sevenSize > 2) {
                            for (int i = 1; i < sevenSize; i++) {
                                String lat = sevenDataUsers.get(i).getLat();
                                String lon = sevenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat31), Double.valueOf(mLon31));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mSevenParkDataDao.add(mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", sevenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = sevenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mSevenParkDataDao.updaeUser("sevenparkcar", mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", minIndex + 1);
                            }
                        } else {
                            mSevenParkDataDao.add(mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", sevenSum + "");
                        }
                        break;
                    case 8:
                        //判断是否有停留车
                        String eightPickLeftName = mEightPickLeft.getPosition();
                        String eightpickrightName = mEightpickright.getPosition();
                        String takeOffTotal8 = mControlEightPick.getName();
                        mControlEightPick.setName(takeOffTotal8 + "摘钩");
                        if (eightPickLeftName.equals("0") || eightpickrightName.equals("0")) {
                            if (eightPickLeftName.equals("0") && !eightpickrightName.equals("0")) {
                                eightLeft(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                            } else if (!eightPickLeftName.equals("0") && eightpickrightName.equals("0")) {
                                eightRight(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                            }
                        } else {
                            String track = mStopcar.getTrack();
                            String position = mStopcar.getPosition();
                            String lat = mStopcar.getLat();
                            String lon = mStopcar.getLon();
                            //站场布局
                            String name1 = mMain.getName();
                            if (name1.equals("main")) {
                                //查看机车位置在停留车的哪一侧
                                Double positonDouble = Double.valueOf(position);
                                String eightPickLeftPosition = mEightPickLeft.getPosition();
                                Double eightPickLeftPositionDouble = Double.valueOf(eightPickLeftPosition);
                                String eightpickrightPosition = mEightpickright.getPosition();
                                Double eightpickrightPositionDouble = Double.valueOf(eightpickrightPosition);
                                if (positonDouble < eightPickLeftPositionDouble) {
                                    eightLeft(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                                } else if (positonDouble > eightpickrightPositionDouble) {
                                    eightRight(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                                }
                            } else if (name1.equals("changfeng")) {
                                eightLeft(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                            } else if (name1.equals("baili")) {
                                eightRight(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                            }
                        }
                        break;
                    case 9:
                        String takeOffTotal9 = mControlNinePick.getName();
                        mControlNinePick.setName(takeOffTotal9 + "摘钩");
                        List<DataUser> nineDataUsers = mNineParkDataDao.find();
                        int nineSize = nineDataUsers.size();
                        String nineNum = nineDataUsers.get(nineSize - 1).getNum();
                        Integer integerNineNum = Integer.valueOf(nineNum);
                        int nineSum = integerNineNum + 1;
                        if (nineSize > 2) {
                            for (int i = 1; i < nineSize; i++) {
                                String lat = nineDataUsers.get(i).getLat();
                                String lon = nineDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat31), Double.valueOf(mLon31));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mNineParkDataDao.add(mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", nineSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = nineDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mNineParkDataDao.updaeUser("nineparkcar", mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mNineParkDataDao.add(mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", nineSum + "");
                        }
                        break;
                    case 10:
                        //判断是否有停留车
                        String tenPickLeftName = mTenPickLeft.getPosition();
                        String tenpickrightName = mTenpickright.getPosition();
                        String takeOffTotal10 = mControlTenPick.getName();
                        mControlTenPick.setName(takeOffTotal10 + "摘钩");
                        if (tenPickLeftName.equals("0") || tenpickrightName.equals("0")) {
                            if (tenPickLeftName.equals("0") && !tenpickrightName.equals("0")) {
                                tenLeft(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                            } else if (!tenPickLeftName.equals("0") && tenpickrightName.equals("0")) {
                                tenRight(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                            }
                        } else {
                            String track = mStopcar.getTrack();
                            String position = mStopcar.getPosition();
                            String lat = mStopcar.getLat();
                            String lon = mStopcar.getLon();
                            //站场布局
                            String name1 = mMain.getName();
                            if (name1.equals("main")) {
                                //查看机车位置在停留车的哪一侧
                                Double positonDouble = Double.valueOf(position);
                                String tenPickLeftPosition = mEightPickLeft.getPosition();
                                Double tenPickLeftPositionDouble = Double.valueOf(tenPickLeftPosition);
                                String tenpickrightightPosition = mEightpickright.getPosition();
                                Double tenpickrightightPositionDouble = Double.valueOf(tenpickrightightPosition);
                                if (positonDouble < tenPickLeftPositionDouble) {
                                    tenLeft(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                                } else if (positonDouble > tenpickrightightPositionDouble) {
                                    tenRight(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                                }
                            } else if (name1.equals("changfeng")) {
                                tenLeft(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                            } else if (name1.equals("baili")) {
                                tenRight(mGpsPistance2 + "", mLat31, mLon31, mGetGudaoOfGpsPoint3 + "");
                            }
                        }
                        break;
                    case 11:
                        String takeOffTotal11 = mControlElevenPick.getName();
                        mControlElevenPick.setName(takeOffTotal11 + "摘钩");
                        List<DataUser> elevenDataUsers = mElevenParkDataDao.find();
                        int elevenSize = elevenDataUsers.size();
                        String elevenNum = elevenDataUsers.get(elevenSize - 1).getNum();
                        Integer integerElevenNum = Integer.valueOf(elevenNum);
                        int elevenSum = integerElevenNum + 1;
                        if (elevenSize > 2) {
                            for (int i = 1; i < elevenSize; i++) {
                                String lat = elevenDataUsers.get(i).getLat();
                                String lon = elevenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat31), Double.valueOf(mLon31));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mElevenParkDataDao.add(mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", elevenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = elevenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mElevenParkDataDao.updaeUser("elevenparkcar", mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mElevenParkDataDao.add(mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", elevenSum + "");
                        }
                        break;
                    case 12:
                        String takeOffTotal12 = mControlTwelvePick.getName();
                        mControlTwelvePick.setName(takeOffTotal12 + "摘钩");
                        List<DataUser> twelveDataUsers = mTwelveParkDataDao.find();
                        int twelveSize = twelveDataUsers.size();
                        String twelveNum = twelveDataUsers.get(twelveSize - 1).getNum();
                        Integer integerTwelveNum = Integer.valueOf(twelveNum);
                        int twelveSum = integerTwelveNum + 1;
                        if (twelveSize > 2) {
                            for (int i = 1; i < twelveSize; i++) {
                                String lat = twelveDataUsers.get(i).getLat();
                                String lon = twelveDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat31), Double.valueOf(mLon31));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mTwelveParkDataDao.add(mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", twelveSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = twelveDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mTwelveParkDataDao.updaeUser("twelveparkcar", mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mTwelveParkDataDao.add(mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", twelveSum + "");
                        }
                        break;
                    case 13:
                        String takeOffTotal13 = mControlThirteenPick.getName();
                        mControlThirteenPick.setName(takeOffTotal13 + "摘钩");
                        List<DataUser> thirteenDataUsers = mThirteenParkDataDao.find();
                        int thirteenSize = thirteenDataUsers.size();
                        String thirteenNum = thirteenDataUsers.get(thirteenSize - 1).getNum();
                        Integer integerThirteenNum = Integer.valueOf(thirteenNum);
                        int thirteenSum = integerThirteenNum + 1;
                        if (thirteenSize > 2) {
                            for (int i = 1; i < thirteenSize; i++) {
                                String lat = thirteenDataUsers.get(i).getLat();
                                String lon = thirteenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat31), Double.valueOf(mLon31));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mThirteenParkDataDao.add(mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", thirteenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = thirteenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mThirteenParkDataDao.updaeUser("thirteenparkcar", mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mThirteenParkDataDao.add(mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", thirteenSum + "");
                        }
                        break;
                    case 14:
                        String takeOffTotal14 = mControlFourteenPick.getName();
                        mControlFourteenPick.setName(takeOffTotal14 + "摘钩");
                        List<DataUser> fourteenDataUsers = mFourteenParkDataDao.find();
                        int fourteenSize = fourteenDataUsers.size();
                        String fourteenNum = fourteenDataUsers.get(fourteenSize - 1).getNum();
                        Integer integerFourteenNum = Integer.valueOf(fourteenNum);
                        int fourteenSum = integerFourteenNum + 1;
                        if (fourteenSize > 2) {
                            for (int i = 1; i < fourteenSize; i++) {
                                String lat = fourteenDataUsers.get(i).getLat();
                                String lon = fourteenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat21), Double.valueOf(mLon21));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mFourteenParkDataDao.add(mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", fourteenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = fourteenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mFourteenParkDataDao.updaeUser("fourteenparkcar", mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", minIndex + 1);
                            }
                        } else {
                            mFourteenParkDataDao.add(mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", fourteenSum + "");
                        }
                    case 15:
                        String takeOffTotal15 = mControlFifteenPick.getName();
                        mControlFifteenPick.setName(takeOffTotal15 + "摘钩");
                        List<DataUser> fifteenDataUsers = mFifteenParkDataDao.find();
                        int fifteenSize = fifteenDataUsers.size();
                        String fifteenNum = fifteenDataUsers.get(fifteenSize - 1).getNum();
                        Integer integerFifteenNum = Integer.valueOf(fifteenNum);
                        int fifteenSum = integerFifteenNum + 1;
                        if (fifteenSize > 2) {
                            for (int i = 1; i < fifteenSize; i++) {
                                String lat = fifteenDataUsers.get(i).getLat();
                                String lon = fifteenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat31), Double.valueOf(mLon31));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mFifteenParkDataDao.add(mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", fifteenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = fifteenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mFifteenParkDataDao.updaeUser("fifteenparkcar", mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mFifteenParkDataDao.add(mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", fifteenSum + "");
                        }
                        break;
                    case 16:
                        String takeOffTotal16 = mControlSixteenPick.getName();
                        mControlSixteenPick.setName(takeOffTotal16 + "摘钩");
                        List<DataUser> sixteenDataUsers = mSixteenParkDataDao.find();
                        int sixteenSize = sixteenDataUsers.size();
                        String sixteenNum = sixteenDataUsers.get(sixteenSize - 1).getNum();
                        Integer integerSixteenNum = Integer.valueOf(sixteenNum);
                        int sixteenSum = integerSixteenNum + 1;
                        if (sixteenSize > 2) {
                            for (int i = 1; i < sixteenSize; i++) {
                                String lat = sixteenDataUsers.get(i).getLat();
                                String lon = sixteenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat31), Double.valueOf(mLon31));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mSixteenParkDataDao.add(mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", sixteenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = sixteenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mSixteenParkDataDao.updaeUser("sixteenparkcar", mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mSixteenParkDataDao.add(mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", sixteenSum + "");
                        }
                        break;
                    case 17:
                        String takeOffTotal17 = mControlSeventeenPick.getName();
                        mControlSeventeenPick.setName(takeOffTotal17 + "摘钩");
                        List<DataUser> seventeenDataUsers = mSeventeenParkDataDao.find();
                        int seventeenSize = seventeenDataUsers.size();
                        String seventeenNum = seventeenDataUsers.get(seventeenSize - 1).getNum();
                        Integer integerSeventeenNum = Integer.valueOf(seventeenNum);
                        int seventeenSum = integerSeventeenNum + 1;
                        if (seventeenSize > 2) {
                            for (int i = 1; i < seventeenSize; i++) {
                                String lat = seventeenDataUsers.get(i).getLat();
                                String lon = seventeenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat31), Double.valueOf(mLon31));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mSeventeenParkDataDao.add(mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", seventeenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = seventeenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mSeventeenParkDataDao.updaeUser("seventeenparkcar", mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mSeventeenParkDataDao.add(mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", seventeenSum + "");
                        }
                        break;
                    case 18:
                        String takeOffTotal18 = mControlEighteenPick.getName();
                        mControlEighteenPick.setName(takeOffTotal18 + "摘钩");
                        List<DataUser> eighteenDataUsers = mEighteenParkDataDao.find();
                        int eighteenSize = eighteenDataUsers.size();
                        String eighteenNum = eighteenDataUsers.get(eighteenSize - 1).getNum();
                        Integer integerEighteenNum = Integer.valueOf(eighteenNum);
                        int eighteenSum = integerEighteenNum + 1;
                        if (eighteenSize > 2) {
                            for (int i = 1; i < eighteenSize; i++) {
                                String lat = eighteenDataUsers.get(i).getLat();
                                String lon = eighteenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat31), Double.valueOf(mLon31));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mEighteenParkDataDao.add(mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", eighteenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = eighteenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mEighteenParkDataDao.updaeUser("eighteenparkcar", mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mEighteenParkDataDao.add(mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", eighteenSum + "");
                        }
                        break;
                    case 19:
                        String takeOffTotal19 = mControlNineteenPick.getName();
                        mControlNineteenPick.setName(takeOffTotal19 + "摘钩");
                        List<DataUser> nineteenDataUsers = mNineteenParkDataDao.find();
                        int nineteenSize = nineteenDataUsers.size();
                        String nineteenNum = nineteenDataUsers.get(nineteenSize - 1).getNum();
                        Integer integerNineteenNum = Integer.valueOf(nineteenNum);
                        int nineteenSum = integerNineteenNum + 1;
                        if (nineteenSize > 2) {
                            for (int i = 1; i < nineteenSize; i++) {
                                String lat = nineteenDataUsers.get(i).getLat();
                                String lon = nineteenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat31), Double.valueOf(mLon31));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mNineteenParkDataDao.add(mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", nineteenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = nineteenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mNineteenParkDataDao.updaeUser("nineteenparkcar", mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mNineteenParkDataDao.add(mGetGudaoOfGpsPoint3 + "", mLat31, mLon31, mGpsPoint2 + "", nineteenSum + "");
                        }
                        break;
                }
                break;
            case "03":
                eightPerson();
                String total2 = "03-摘钩GPS-" + mLat61 + "-" + mLon61;
                sendMessage(mConversationId, total2);
                switch (mGetGudaoOfGpsPoint4) {
                    case 1:
                        //判断是否有停留车
                        String onePickLeftName = mOnePickLeft.getPosition();
                        String onepickrightName = mOnepickright.getPosition();
                        String takeOffTotal = mControlOnePick.getName();
                        mControlOnePick.setName(takeOffTotal + "摘钩");
                        if (onePickLeftName.equals("0") || onepickrightName.equals("0")) {
                            if (onePickLeftName.equals("0") && !onepickrightName.equals("0")) {
                                oneLeft(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                            } else if (!onePickLeftName.equals("0") && onepickrightName.equals("0")) {
                                oneRight(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                            }
                        } else {
                            String track = mStopcar.getTrack();
                            String position = mStopcar.getPosition();
                            String lat = mStopcar.getLat();
                            String lon = mStopcar.getLon();
                            //站场布局
                            String name1 = mMain.getName();
                            if (name1.equals("main")) {
                                //查看机车位置在停留车的哪一侧
                                Double positonDouble = Double.valueOf(position);
                                String onePickLeftPosition = mOnePickLeft.getPosition();
                                Double onePickLeftPositionDouble = Double.valueOf(onePickLeftPosition);
                                String onepickrightPosition = mOnepickright.getPosition();
                                Double onepickrightPositionDouble = Double.valueOf(onepickrightPosition);
                                if (positonDouble < onePickLeftPositionDouble) {
                                    oneLeft(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                                } else if (positonDouble > onepickrightPositionDouble) {
                                    oneRight(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                                }
                            } else if (name1.equals("changfeng")) {
                                oneLeft(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                            } else if (name1.equals("baili")) {
                                oneRight(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                            }
                        }
                        break;
                    case 2:
                        //判断是否有停留车
                        String twoPickLeftName = mTwoPickLeft.getPosition();
                        String twopickrightName = mTwopickright.getPosition();
                        String takeOffTotal2 = mControlTwoPick.getName();
                        mControlTwoPick.setName(takeOffTotal2 + "摘钩");
                        if (twoPickLeftName.equals("0") || twopickrightName.equals("0")) {
                            if (twoPickLeftName.equals("0") && !twopickrightName.equals("0")) {
                                twoLeft(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                            } else if (!twoPickLeftName.equals("0") && twopickrightName.equals("0")) {
                                twoRight(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                            }
                        } else {
                            String track = mStopcar.getTrack();
                            String position = mStopcar.getPosition();
                            String lat = mStopcar.getLat();
                            String lon = mStopcar.getLon();
                            //站场布局
                            String name1 = mMain.getName();
                            if (name1.equals("main")) {
                                //查看机车位置在停留车的哪一侧
                                Double positonDouble = Double.valueOf(position);
                                String twoPickLeftPosition = mTwoPickLeft.getPosition();
                                Double twoPickLeftPositionDouble = Double.valueOf(twoPickLeftPosition);
                                String twopickrightPosition = mTwopickright.getPosition();
                                Double twopickrightPositionDouble = Double.valueOf(twopickrightPosition);
                                if (positonDouble < twoPickLeftPositionDouble) {
                                    twoLeft(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                                } else if (positonDouble > twopickrightPositionDouble) {
                                    twoRight(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                                }
                            } else if (name1.equals("changfeng")) {
                                twoLeft(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                            } else if (name1.equals("baili")) {
                                twoRight(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                            }
                        }
                        break;
                    case 3:
                        //判断是否有停留车
                        String threePickLeftName = mThreePickLeft.getPosition();
                        String threepickrightName = mThreepickright.getPosition();
                        String takeOffTotal3 = mControlThreePick.getName();
                        mControlThreePick.setName(takeOffTotal3 + "摘钩");
                        if (threePickLeftName.equals("0") || threepickrightName.equals("0")) {
                            if (threePickLeftName.equals("0") && !threepickrightName.equals("0")) {
                                threeLeft(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                            } else if (!threePickLeftName.equals("0") && threepickrightName.equals("0")) {
                                threeRight(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                            }
                        } else {
                            String track = mStopcar.getTrack();
                            String position = mStopcar.getPosition();
                            String lat = mStopcar.getLat();
                            String lon = mStopcar.getLon();
                            //站场布局
                            String name1 = mMain.getName();
                            if (name1.equals("main")) {
                                //查看机车位置在停留车的哪一侧
                                Double positonDouble = Double.valueOf(position);
                                String threePickLeftPosition = mThreePickLeft.getPosition();
                                Double threePickLeftPositionDouble = Double.valueOf(threePickLeftPosition);
                                String threepickrightPosition = mThreepickright.getPosition();
                                Double threepickrightPositionDouble = Double.valueOf(threepickrightPosition);
                                if (positonDouble < threePickLeftPositionDouble) {
                                    threeLeft(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                                } else if (positonDouble > threepickrightPositionDouble) {
                                    threeRight(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                                }
                            } else if (name1.equals("changfeng")) {
                                threeLeft(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                            } else if (name1.equals("baili")) {
                                threeRight(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                            }
                        }
                        break;
                    case 4:
                        //判断是否有停留车
                        String fourPickLeftName = mFourPickLeft.getPosition();
                        String fourpickrightName = mFourpickright.getPosition();
                        String takeOffTotal4 = mControlFourPick.getName();
                        mControlFourPick.setName(takeOffTotal4 + "摘钩");
                        if (fourPickLeftName.equals("0") || fourpickrightName.equals("0")) {
                            if (fourPickLeftName.equals("0") && !fourpickrightName.equals("0")) {
                                fourLeft(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                            } else if (!fourPickLeftName.equals("0") && fourpickrightName.equals("0")) {
                                fourRight(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                            }
                        } else {
                            String track = mStopcar.getTrack();
                            String position = mStopcar.getPosition();
                            String lat = mStopcar.getLat();
                            String lon = mStopcar.getLon();
                            //站场布局
                            String name1 = mMain.getName();
                            if (name1.equals("main")) {
                                //查看机车位置在停留车的哪一侧
                                Double positonDouble = Double.valueOf(position);
                                String fourPickLeftPosition = mFourPickLeft.getPosition();
                                Double fourPickLeftPositionDouble = Double.valueOf(fourPickLeftPosition);
                                String fourpickrightPosition = mFourpickright.getPosition();
                                Double fourpickrightPositionDouble = Double.valueOf(fourpickrightPosition);
                                if (positonDouble < fourPickLeftPositionDouble) {
                                    fourLeft(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                                } else if (positonDouble > fourpickrightPositionDouble) {
                                    fourRight(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                                }
                            } else if (name1.equals("changfeng")) {
                                fourLeft(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                            } else if (name1.equals("baili")) {
                                fourRight(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                            }
                        }
                        break;
                    case 5:
                        //判断是否有停留车
                        String fivePickLeftName = mFivePickLeft.getPosition();
                        String fivepickrightName = mFivepickright.getPosition();
                        String takeOffTotal5 = mControlFivePick.getName();
                        mControlFivePick.setName(takeOffTotal5 + "摘钩");
                        if (fivePickLeftName.equals("0") || fivepickrightName.equals("0")) {
                            if (fivePickLeftName.equals("0") && !fivepickrightName.equals("0")) {
                                fiveLeft(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                            } else if (!fivePickLeftName.equals("0") && fivepickrightName.equals("0")) {
                                fiveRight(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                            }
                        } else {
                            String track = mStopcar.getTrack();
                            String position = mStopcar.getPosition();
                            String lat = mStopcar.getLat();
                            String lon = mStopcar.getLon();
                            //站场布局
                            String name1 = mMain.getName();
                            if (name1.equals("main")) {
                                //查看机车位置在停留车的哪一侧
                                Double positonDouble = Double.valueOf(position);
                                String fivePickLeftPosition = mFivePickLeft.getPosition();
                                Double fivePickLeftPositionDouble = Double.valueOf(fivePickLeftPosition);
                                String fivepickrightPosition = mFivepickright.getPosition();
                                Double fivepickrightPositionDouble = Double.valueOf(fivepickrightPosition);
                                if (positonDouble < fivePickLeftPositionDouble) {
                                    fiveLeft(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                                } else if (positonDouble > fivepickrightPositionDouble) {
                                    fiveRight(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                                }
                            } else if (name1.equals("changfeng")) {
                                fiveLeft(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                            } else if (name1.equals("baili")) {
                                fiveRight(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                            }
                        }
                        break;
                    case 6:
                        String takeOffTotal6 = mControlSixPick.getName();
                        mControlSixPick.setName(takeOffTotal6 + "摘钩");
                        List<DataUser> sixDataUsers = mSixParkDataDao.find();
                        int sixSize = sixDataUsers.size();
                        String num = sixDataUsers.get(sixSize - 1).getNum();
                        Integer integerNum = Integer.valueOf(num);
                        int sum = integerNum + 1;
                        if (sixSize > 2) {
                            for (int i = 1; i < sixSize; i++) {
                                String lat = sixDataUsers.get(i).getLat();
                                String lon = sixDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat4), Double.valueOf(mLon4));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mSixParkDataDao.add(mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", sum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = sixDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mSixParkDataDao.updaeUser("sixparkcar", mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mSixParkDataDao.add(mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", sum + "");
                        }
                        break;
                    case 7:
                        String takeOffTotal7 = mControlSevenPick.getName();
                        mControlSevenPick.setName(takeOffTotal7 + "摘钩");
                        List<DataUser> sevenDataUsers = mSevenParkDataDao.find();
                        int sevenSize = sevenDataUsers.size();
                        String sevenNum = sevenDataUsers.get(sevenSize - 1).getNum();
                        Integer integerSevenNum = Integer.valueOf(sevenNum);
                        int sevenSum = integerSevenNum + 1;
                        if (sevenSize > 2) {
                            for (int i = 1; i < sevenSize; i++) {
                                String lat = sevenDataUsers.get(i).getLat();
                                String lon = sevenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat4), Double.valueOf(mLon4));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mSevenParkDataDao.add(mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", sevenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = sevenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mSevenParkDataDao.updaeUser("sevenparkcar", mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mSevenParkDataDao.add(mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", sevenSum + "");
                        }
                        break;
                    case 8:
                        //判断是否有停留车
                        String eightPickLeftName = mEightPickLeft.getPosition();
                        String eightpickrightName = mEightpickright.getPosition();
                        String takeOffTotal8 = mControlEightPick.getName();
                        mControlEightPick.setName(takeOffTotal8 + "摘钩");
                        if (eightPickLeftName.equals("0") || eightpickrightName.equals("0")) {
                            if (eightPickLeftName.equals("0") && !eightpickrightName.equals("0")) {
                                eightLeft(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                            } else if (!eightPickLeftName.equals("0") && eightpickrightName.equals("0")) {
                                eightRight(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                            }
                        } else {
                            String track = mStopcar.getTrack();
                            String position = mStopcar.getPosition();
                            String lat = mStopcar.getLat();
                            String lon = mStopcar.getLon();
                            //站场布局
                            String name1 = mMain.getName();
                            if (name1.equals("main")) {
                                //查看机车位置在停留车的哪一侧
                                Double positonDouble = Double.valueOf(position);
                                String eightPickLeftPosition = mEightPickLeft.getPosition();
                                Double eightPickLeftPositionDouble = Double.valueOf(eightPickLeftPosition);
                                String eightpickrightPosition = mEightpickright.getPosition();
                                Double eightpickrightPositionDouble = Double.valueOf(eightpickrightPosition);
                                if (positonDouble < eightPickLeftPositionDouble) {
                                    eightLeft(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                                } else if (positonDouble > eightpickrightPositionDouble) {
                                    eightRight(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                                }
                            } else if (name1.equals("changfeng")) {
                                eightLeft(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                            } else if (name1.equals("baili")) {
                                eightRight(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                            }
                        }
                        break;
                    case 9:
                        String takeOffTotal9 = mControlNinePick.getName();
                        mControlNinePick.setName(takeOffTotal9 + "摘钩");
                        List<DataUser> nineDataUsers = mNineParkDataDao.find();
                        int nineSize = nineDataUsers.size();
                        String nineNum = nineDataUsers.get(nineSize - 1).getNum();
                        Integer integerNineNum = Integer.valueOf(nineNum);
                        int nineSum = integerNineNum + 1;
                        if (nineSize > 2) {
                            for (int i = 1; i < nineSize; i++) {
                                String lat = nineDataUsers.get(i).getLat();
                                String lon = nineDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat4), Double.valueOf(mLon4));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mNineParkDataDao.add(mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", nineSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = nineDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mNineParkDataDao.updaeUser("nineparkcar", mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mNineParkDataDao.add(mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", nineSum + "");
                        }
                        break;
                    case 10:
                        //判断是否有停留车
                        String tenPickLeftName = mTenPickLeft.getPosition();
                        String tenpickrightName = mTenpickright.getPosition();
                        String takeOffTotal10 = mControlTenPick.getName();
                        mControlTenPick.setName(takeOffTotal10 + "摘钩");
                        if (tenPickLeftName.equals("0") || tenpickrightName.equals("0")) {
                            if (tenPickLeftName.equals("0") && !tenpickrightName.equals("0")) {
                                tenLeft(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                            } else if (!tenPickLeftName.equals("0") && tenpickrightName.equals("0")) {
                                tenRight(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                            }
                        } else {
                            String track = mStopcar.getTrack();
                            String position = mStopcar.getPosition();
                            String lat = mStopcar.getLat();
                            String lon = mStopcar.getLon();
                            //站场布局
                            String name1 = mMain.getName();
                            if (name1.equals("main")) {
                                //查看机车位置在停留车的哪一侧
                                Double positonDouble = Double.valueOf(position);
                                String tenPickLeftPosition = mEightPickLeft.getPosition();
                                Double tenPickLeftPositionDouble = Double.valueOf(tenPickLeftPosition);
                                String tenpickrightPosition = mEightpickright.getPosition();
                                Double tenpickrightPositionDouble = Double.valueOf(tenpickrightPosition);
                                if (positonDouble < tenPickLeftPositionDouble) {
                                    tenLeft(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                                } else if (positonDouble > tenpickrightPositionDouble) {
                                    tenRight(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                                }
                            } else if (name1.equals("changfeng")) {
                                tenLeft(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                            } else if (name1.equals("baili")) {
                                tenRight(mGpsPistance2 + "", mLat4, mLon4, mGetGudaoOfGpsPoint4 + "");
                            }
                        }
                        break;
                    case 11:
                        String takeOffTotal11 = mControlElevenPick.getName();
                        mControlElevenPick.setName(takeOffTotal11 + "摘钩");
                        List<DataUser> elevenDataUsers = mElevenParkDataDao.find();
                        int elevenSize = elevenDataUsers.size();
                        String elevenNum = elevenDataUsers.get(elevenSize - 1).getNum();
                        Integer integerElevenNum = Integer.valueOf(elevenNum);
                        int elevenSum = integerElevenNum + 1;
                        if (elevenSize > 2) {
                            for (int i = 1; i < elevenSize; i++) {
                                String lat = elevenDataUsers.get(i).getLat();
                                String lon = elevenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat4), Double.valueOf(mLon4));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mElevenParkDataDao.add(mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", elevenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = elevenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mElevenParkDataDao.updaeUser("elevenparkcar", mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mElevenParkDataDao.add(mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", elevenSum + "");
                        }
                        break;
                    case 12:
                        String takeOffTotal12 = mControlTwelvePick.getName();
                        mControlTwelvePick.setName(takeOffTotal12 + "摘钩");
                        List<DataUser> twelveDataUsers = mTwelveParkDataDao.find();
                        int twelveSize = twelveDataUsers.size();
                        String twelveNum = twelveDataUsers.get(twelveSize - 1).getNum();
                        Integer integerTwelveNum = Integer.valueOf(twelveNum);
                        int twelveSum = integerTwelveNum + 1;
                        if (twelveSize > 2) {
                            for (int i = 1; i < twelveSize; i++) {
                                String lat = twelveDataUsers.get(i).getLat();
                                String lon = twelveDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat4), Double.valueOf(mLon4));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mTwelveParkDataDao.add(mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", twelveSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = twelveDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mTwelveParkDataDao.updaeUser("twelveparkcar", mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mTwelveParkDataDao.add(mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", twelveSum + "");
                        }
                        break;
                    case 13:
                        String takeOffTotal13 = mControlThirteenPick.getName();
                        mControlThirteenPick.setName(takeOffTotal13 + "摘钩");
                        List<DataUser> thirteenDataUsers = mThirteenParkDataDao.find();
                        int thirteenSize = thirteenDataUsers.size();
                        String thirteenNum = thirteenDataUsers.get(thirteenSize - 1).getNum();
                        Integer integerThirteenNum = Integer.valueOf(thirteenNum);
                        int thirteenSum = integerThirteenNum + 1;
                        if (thirteenSize > 2) {
                            for (int i = 1; i < thirteenSize; i++) {
                                String lat = thirteenDataUsers.get(i).getLat();
                                String lon = thirteenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat4), Double.valueOf(mLon4));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mThirteenParkDataDao.add(mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", thirteenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = thirteenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mThirteenParkDataDao.updaeUser("thirteenparkcar", mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mThirteenParkDataDao.add(mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", thirteenSum + "");
                        }
                        break;
                    case 14:
                        String takeOffTotal14 = mControlFourteenPick.getName();
                        mControlFourteenPick.setName(takeOffTotal14 + "摘钩");
                        List<DataUser> fourteenDataUsers = mFourteenParkDataDao.find();
                        int fourteenSize = fourteenDataUsers.size();
                        String fourteenNum = fourteenDataUsers.get(fourteenSize - 1).getNum();
                        Integer integerFourteenNum = Integer.valueOf(fourteenNum);
                        int fourteenSum = integerFourteenNum + 1;
                        if (fourteenSize > 2) {
                            for (int i = 1; i < fourteenSize; i++) {
                                String lat = fourteenDataUsers.get(i).getLat();
                                String lon = fourteenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat4), Double.valueOf(mLon4));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mFourteenParkDataDao.add(mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", fourteenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = fourteenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mFourteenParkDataDao.updaeUser("fourteenparkcar", mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mFourteenParkDataDao.add(mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", fourteenSum + "");
                        }
                    case 15:
                        String takeOffTotal15 = mControlFifteenPick.getName();
                        mControlFifteenPick.setName(takeOffTotal15 + "摘钩");
                        List<DataUser> fifteenDataUsers = mFifteenParkDataDao.find();
                        int fifteenSize = fifteenDataUsers.size();
                        String fifteenNum = fifteenDataUsers.get(fifteenSize - 1).getNum();
                        Integer integerFifteenNum = Integer.valueOf(fifteenNum);
                        int fifteenSum = integerFifteenNum + 1;
                        if (fifteenSize > 2) {
                            for (int i = 1; i < fifteenSize; i++) {
                                String lat = fifteenDataUsers.get(i).getLat();
                                String lon = fifteenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat4), Double.valueOf(mLon4));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mFifteenParkDataDao.add(mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", fifteenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = fifteenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mFifteenParkDataDao.updaeUser("fifteenparkcar", mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mFifteenParkDataDao.add(mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", fifteenSum + "");
                        }
                        break;
                    case 16:
                        String takeOffTotal16 = mControlSixteenPick.getName();
                        mControlSixteenPick.setName(takeOffTotal16 + "摘钩");
                        List<DataUser> sixteenDataUsers = mSixteenParkDataDao.find();
                        int sixteenSize = sixteenDataUsers.size();
                        String sixteenNum = sixteenDataUsers.get(sixteenSize - 1).getNum();
                        Integer integerSixteenNum = Integer.valueOf(sixteenNum);
                        int sixteenSum = integerSixteenNum + 1;
                        if (sixteenSize > 2) {
                            for (int i = 1; i < sixteenSize; i++) {
                                String lat = sixteenDataUsers.get(i).getLat();
                                String lon = sixteenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat4), Double.valueOf(mLon4));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mSixteenParkDataDao.add(mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", sixteenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = sixteenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mSixteenParkDataDao.updaeUser("sixteenparkcar", mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mSixteenParkDataDao.add(mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", sixteenSum + "");
                        }
                        break;
                    case 17:
                        String takeOffTotal17 = mControlSeventeenPick.getName();
                        mControlSeventeenPick.setName(takeOffTotal17 + "摘钩");
                        List<DataUser> seventeenDataUsers = mSeventeenParkDataDao.find();
                        int seventeenSize = seventeenDataUsers.size();
                        String seventeenNum = seventeenDataUsers.get(seventeenSize - 1).getNum();
                        Integer integerSeventeenNum = Integer.valueOf(seventeenNum);
                        int seventeenSum = integerSeventeenNum + 1;
                        if (seventeenSize > 2) {
                            for (int i = 1; i < seventeenSize; i++) {
                                String lat = seventeenDataUsers.get(i).getLat();
                                String lon = seventeenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat4), Double.valueOf(mLon4));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mSeventeenParkDataDao.add(mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", seventeenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = seventeenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mSeventeenParkDataDao.updaeUser("seventeenparkcar", mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mSeventeenParkDataDao.add(mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", seventeenSum + "");
                        }
                        break;
                    case 18:
                        String takeOffTotal18 = mControlEighteenPick.getName();
                        mControlEighteenPick.setName(takeOffTotal18 + "摘钩");
                        List<DataUser> eighteenDataUsers = mEighteenParkDataDao.find();
                        int eighteenSize = eighteenDataUsers.size();
                        String eighteenNum = eighteenDataUsers.get(eighteenSize - 1).getNum();
                        Integer integerEighteenNum = Integer.valueOf(eighteenNum);
                        int eighteenSum = integerEighteenNum + 1;
                        if (eighteenSize > 2) {
                            for (int i = 1; i < eighteenSize; i++) {
                                String lat = eighteenDataUsers.get(i).getLat();
                                String lon = eighteenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat4), Double.valueOf(mLon4));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mEighteenParkDataDao.add(mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", eighteenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = eighteenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mEighteenParkDataDao.updaeUser("eighteenparkcar", mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mEighteenParkDataDao.add(mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", eighteenSum + "");
                        }
                        break;
                    case 19:
                        String takeOffTotal19 = mControlNineteenPick.getName();
                        mControlNineteenPick.setName(takeOffTotal19 + "摘钩");
                        List<DataUser> nineteenDataUsers = mNineteenParkDataDao.find();
                        int nineteenSize = nineteenDataUsers.size();
                        String nineteenNum = nineteenDataUsers.get(nineteenSize - 1).getNum();
                        Integer integerNineteenNum = Integer.valueOf(nineteenNum);
                        int nineteenSum = integerNineteenNum + 1;
                        if (nineteenSize > 2) {
                            for (int i = 1; i < nineteenSize; i++) {
                                String lat = nineteenDataUsers.get(i).getLat();
                                String lon = nineteenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat4), Double.valueOf(mLon4));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mNineteenParkDataDao.add(mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", nineteenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = nineteenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mNineteenParkDataDao.updaeUser("nineteenparkcar", mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mNineteenParkDataDao.add(mGetGudaoOfGpsPoint4 + "", mLat4, mLon4, mGpsPoint2 + "", nineteenSum + "");
                        }
                        break;
                }
                break;
            case "04":
                ninePerson();
                String total3 = "04-摘钩GPS-" + mLat61 + "-" + mLon61;
                sendMessage(mConversationId, total3);
                switch (mGetGudaoOfGpsPoint5) {
                    case 1:
                        //判断是否有停留车
                        String onePickLeftName = mOnePickLeft.getPosition();
                        String onepickrightName = mOnepickright.getPosition();
                        String takeOffTotal = mControlOnePick.getName();
                        mControlOnePick.setName(takeOffTotal + "摘钩");
                        if (onePickLeftName.equals("0") || onepickrightName.equals("0")) {
                            if (onePickLeftName.equals("0") && !onepickrightName.equals("0")) {
                                oneLeft(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                            } else if (!onePickLeftName.equals("0") && onepickrightName.equals("0")) {
                                oneRight(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                            }
                        } else {
                            String track = mStopcar.getTrack();
                            String position = mStopcar.getPosition();
                            String lat = mStopcar.getLat();
                            String lon = mStopcar.getLon();
                            //站场布局
                            String name1 = mMain.getName();
                            if (name1.equals("main")) {
                                //查看机车位置在停留车的哪一侧
                                Double positonDouble = Double.valueOf(position);
                                String onePickLeftPosition = mOnePickLeft.getPosition();
                                Double onePickLeftPositionDouble = Double.valueOf(onePickLeftPosition);
                                String onepickrightPosition = mOnepickright.getPosition();
                                Double onepickrightPositionDouble = Double.valueOf(onepickrightPosition);
                                if (positonDouble < onePickLeftPositionDouble) {
                                    oneLeft(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                                } else if (positonDouble > onepickrightPositionDouble) {
                                    oneRight(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                                }
                            } else if (name1.equals("changfeng")) {
                                oneLeft(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                            } else if (name1.equals("baili")) {
                                oneRight(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                            }
                        }
                        break;
                    case 2:
                        //判断是否有停留车
                        String twoPickLeftName = mTwoPickLeft.getPosition();
                        String twopickrightName = mTwopickright.getPosition();
                        String takeOffTotal2 = mControlTwoPick.getName();
                        mControlTwoPick.setName(takeOffTotal2 + "摘钩");
                        if (twoPickLeftName.equals("0") || twopickrightName.equals("0")) {
                            if (twoPickLeftName.equals("0") && !twopickrightName.equals("0")) {
                                twoLeft(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                            } else if (!twoPickLeftName.equals("0") && twopickrightName.equals("0")) {
                                twoRight(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                            }
                        } else {
                            String track = mStopcar.getTrack();
                            String position = mStopcar.getPosition();
                            String lat = mStopcar.getLat();
                            String lon = mStopcar.getLon();
                            //站场布局
                            String name1 = mMain.getName();
                            if (name1.equals("main")) {
                                //查看机车位置在停留车的哪一侧
                                Double positonDouble = Double.valueOf(position);
                                String twoPickLeftPosition = mTwoPickLeft.getPosition();
                                Double twoPickLeftPositionDouble = Double.valueOf(twoPickLeftPosition);
                                String twopickrightPosition = mTwopickright.getPosition();
                                Double twopickrightPositionDouble = Double.valueOf(twopickrightPosition);
                                if (positonDouble < twoPickLeftPositionDouble) {
                                    twoLeft(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                                } else if (positonDouble > twopickrightPositionDouble) {
                                    twoRight(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                                }
                            } else if (name1.equals("changfeng")) {
                                twoLeft(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                            } else if (name1.equals("baili")) {
                                twoRight(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                            }
                        }
                        break;
                    case 3:
                        //判断是否有停留车
                        String threePickLeftName = mThreePickLeft.getPosition();
                        String threepickrightName = mThreepickright.getPosition();
                        String takeOffTotal3 = mControlThreePick.getName();
                        mControlThreePick.setName(takeOffTotal3 + "摘钩");
                        if (threePickLeftName.equals("0") || threepickrightName.equals("0")) {
                            if (threePickLeftName.equals("0") && !threepickrightName.equals("0")) {
                                threeLeft(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                            } else if (!threePickLeftName.equals("0") && threepickrightName.equals("0")) {
                                threeRight(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                            }
                        } else {
                            String track = mStopcar.getTrack();
                            String position = mStopcar.getPosition();
                            String lat = mStopcar.getLat();
                            String lon = mStopcar.getLon();
                            //站场布局
                            String name1 = mMain.getName();
                            if (name1.equals("main")) {
                                //查看机车位置在停留车的哪一侧
                                Double positonDouble = Double.valueOf(position);
                                String threePickLeftPosition = mThreePickLeft.getPosition();
                                Double threePickLeftPositionDouble = Double.valueOf(threePickLeftPosition);
                                String threepickrightPosition = mThreepickright.getPosition();
                                Double threepickrightPositionDouble = Double.valueOf(threepickrightPosition);
                                if (positonDouble < threePickLeftPositionDouble) {
                                    threeLeft(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                                } else if (positonDouble > threepickrightPositionDouble) {
                                    threeRight(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                                }
                            } else if (name1.equals("changfeng")) {
                                threeLeft(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                            } else if (name1.equals("baili")) {
                                threeRight(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                            }
                        }
                        break;
                    case 4:
                        //判断是否有停留车
                        String fourPickLeftName = mFourPickLeft.getPosition();
                        String fourpickrightName = mFourpickright.getPosition();
                        String takeOffTotal4 = mControlFourPick.getName();
                        mControlFourPick.setName(takeOffTotal4 + "摘钩");
                        if (fourPickLeftName.equals("0") || fourpickrightName.equals("0")) {
                            if (fourPickLeftName.equals("0") && !fourpickrightName.equals("0")) {
                                fourLeft(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                            } else if (!fourPickLeftName.equals("0") && fourpickrightName.equals("0")) {
                                fourRight(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                            }
                        } else {
                            String track = mStopcar.getTrack();
                            String position = mStopcar.getPosition();
                            String lat = mStopcar.getLat();
                            String lon = mStopcar.getLon();
                            //站场布局
                            String name1 = mMain.getName();
                            if (name1.equals("main")) {
                                //查看机车位置在停留车的哪一侧
                                Double positonDouble = Double.valueOf(position);
                                String fourPickLeftPosition = mFourPickLeft.getPosition();
                                Double fourPickLeftPositionDouble = Double.valueOf(fourPickLeftPosition);
                                String fourpickrightPosition = mFourpickright.getPosition();
                                Double fourpickrightPositionDouble = Double.valueOf(fourpickrightPosition);
                                if (positonDouble < fourPickLeftPositionDouble) {
                                    fourLeft(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                                } else if (positonDouble > fourpickrightPositionDouble) {
                                    fourRight(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                                }
                            } else if (name1.equals("changfeng")) {
                                fourLeft(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                            } else if (name1.equals("baili")) {
                                fourRight(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                            }
                        }
                        break;
                    case 5:
                        //判断是否有停留车
                        String fivePickLeftName = mFivePickLeft.getPosition();
                        String fivepickrightName = mFivepickright.getPosition();
                        String takeOffTotal5 = mControlFivePick.getName();
                        mControlFivePick.setName(takeOffTotal5 + "摘钩");
                        if (fivePickLeftName.equals("0") || fivepickrightName.equals("0")) {
                            if (fivePickLeftName.equals("0") && !fivepickrightName.equals("0")) {
                                fiveLeft(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                            } else if (!fivePickLeftName.equals("0") && fivepickrightName.equals("0")) {
                                fiveRight(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                            }
                        } else {
                            String track = mStopcar.getTrack();
                            String position = mStopcar.getPosition();
                            String lat = mStopcar.getLat();
                            String lon = mStopcar.getLon();
                            //站场布局
                            String name1 = mMain.getName();
                            if (name1.equals("main")) {
                                //查看机车位置在停留车的哪一侧
                                Double positonDouble = Double.valueOf(position);
                                String fivePickLeftPosition = mFivePickLeft.getPosition();
                                Double fivePickLeftPositionDouble = Double.valueOf(fivePickLeftPosition);
                                String fivepickrightPosition = mFivepickright.getPosition();
                                Double fivepickrightPositionDouble = Double.valueOf(fivepickrightPosition);
                                if (positonDouble < fivePickLeftPositionDouble) {
                                    fiveLeft(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                                } else if (positonDouble > fivepickrightPositionDouble) {
                                    fiveRight(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                                }
                            } else if (name1.equals("changfeng")) {
                                fiveLeft(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                            } else if (name1.equals("baili")) {
                                fiveRight(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                            }
                        }
                        break;
                    case 6:
                        String takeOffTotal6 = mControlSixPick.getName();
                        mControlSixPick.setName(takeOffTotal6 + "摘钩");
                        List<DataUser> sixDataUsers = mSixParkDataDao.find();
                        int sixSize = sixDataUsers.size();
                        String num = sixDataUsers.get(sixSize - 1).getNum();
                        Integer integerNum = Integer.valueOf(num);
                        int sum = integerNum + 1;
                        if (sixSize > 2) {
                            for (int i = 1; i < sixSize; i++) {
                                String lat = sixDataUsers.get(i).getLat();
                                String lon = sixDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat5), Double.valueOf(mLon5));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mSixParkDataDao.add(mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", sum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = sixDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mSixParkDataDao.updaeUser("sixparkcar", mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mSixParkDataDao.add(mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", sum + "");
                        }
                        break;
                    case 7:
                        String takeOffTotal7 = mControlSevenPick.getName();
                        mControlSevenPick.setName(takeOffTotal7 + "摘钩");
                        List<DataUser> sevenDataUsers = mSevenParkDataDao.find();
                        int sevenSize = sevenDataUsers.size();
                        String sevenNum = sevenDataUsers.get(sevenSize - 1).getNum();
                        Integer integerSevenNum = Integer.valueOf(sevenNum);
                        int sevenSum = integerSevenNum + 1;
                        if (sevenSize > 2) {
                            for (int i = 1; i < sevenSize; i++) {
                                String lat = sevenDataUsers.get(i).getLat();
                                String lon = sevenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat5), Double.valueOf(mLon5));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mSevenParkDataDao.add(mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", sevenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = sevenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mSevenParkDataDao.updaeUser("sevenparkcar", mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mSevenParkDataDao.add(mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", sevenSum + "");
                        }
                        break;
                    case 8:
                        //判断是否有停留车
                        String eightPickLeftName = mEightPickLeft.getPosition();
                        String eightpickrightName = mEightpickright.getPosition();
                        String takeOffTotal8 = mControlEightPick.getName();
                        mControlEightPick.setName(takeOffTotal8 + "摘钩");
                        if (eightPickLeftName.equals("0") || eightpickrightName.equals("0")) {
                            if (eightPickLeftName.equals("0") && !eightpickrightName.equals("0")) {
                                eightLeft(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                            } else if (!eightPickLeftName.equals("0") && eightpickrightName.equals("0")) {
                                eightRight(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                            }
                        } else {
                            String track = mStopcar.getTrack();
                            String position = mStopcar.getPosition();
                            String lat = mStopcar.getLat();
                            String lon = mStopcar.getLon();
                            //站场布局
                            String name1 = mMain.getName();
                            if (name1.equals("main")) {
                                //查看机车位置在停留车的哪一侧
                                Double positonDouble = Double.valueOf(position);
                                String eightPickLeftPosition = mEightPickLeft.getPosition();
                                Double eightPickLeftPositionDouble = Double.valueOf(eightPickLeftPosition);
                                String eightpickrightPosition = mEightpickright.getPosition();
                                Double eightpickrightPositionDouble = Double.valueOf(eightpickrightPosition);
                                if (positonDouble < eightPickLeftPositionDouble) {
                                    eightLeft(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                                } else if (positonDouble > eightpickrightPositionDouble) {
                                    eightRight(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                                }
                            } else if (name1.equals("changfeng")) {
                                eightLeft(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                            } else if (name1.equals("baili")) {
                                eightRight(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                            }
                        }
                        break;
                    case 9:
                        String takeOffTotal9 = mControlNinePick.getName();
                        mControlNinePick.setName(takeOffTotal9 + "摘钩");
                        List<DataUser> nineDataUsers = mNineParkDataDao.find();
                        int nineSize = nineDataUsers.size();
                        String nineNum = nineDataUsers.get(nineSize - 1).getNum();
                        Integer integerNineNum = Integer.valueOf(nineNum);
                        int nineSum = integerNineNum + 1;
                        if (nineSize > 2) {
                            for (int i = 1; i < nineSize; i++) {
                                String lat = nineDataUsers.get(i).getLat();
                                String lon = nineDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat5), Double.valueOf(mLon5));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mNineParkDataDao.add(mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", nineSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = nineDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mNineParkDataDao.updaeUser("nineparkcar", mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mNineParkDataDao.add(mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", nineSum + "");
                        }
                        break;
                    case 10:
                        //判断是否有停留车
                        String tenPickLeftName = mTenPickLeft.getPosition();
                        String tenpickrightName = mTenpickright.getPosition();
                        String takeOffTotal10 = mControlTenPick.getName();
                        mControlEightPick.setName(takeOffTotal10 + "摘钩");
                        if (tenPickLeftName.equals("0") || tenpickrightName.equals("0")) {
                            if (tenPickLeftName.equals("0") && !tenpickrightName.equals("0")) {
                                tenLeft(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                            } else if (!tenPickLeftName.equals("0") && tenpickrightName.equals("0")) {
                                tenRight(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                            }
                        } else {
                            String track = mStopcar.getTrack();
                            String position = mStopcar.getPosition();
                            String lat = mStopcar.getLat();
                            String lon = mStopcar.getLon();
                            //站场布局
                            String name1 = mMain.getName();
                            if (name1.equals("main")) {
                                //查看机车位置在停留车的哪一侧
                                Double positonDouble = Double.valueOf(position);
                                String tenPickLeftPosition = mTenPickLeft.getPosition();
                                Double tenPickLeftPositionDouble = Double.valueOf(tenPickLeftPosition);
                                String tenpickrightPosition = mTenpickright.getPosition();
                                Double tenpickrightPositionDouble = Double.valueOf(tenpickrightPosition);
                                if (positonDouble < tenPickLeftPositionDouble) {
                                    tenLeft(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                                } else if (positonDouble > tenpickrightPositionDouble) {
                                    tenRight(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                                }
                            } else if (name1.equals("changfeng")) {
                                tenLeft(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                            } else if (name1.equals("baili")) {
                                tenRight(mGpsPistance2 + "", mLat5, mLon5, mGetGudaoOfGpsPoint5 + "");
                            }
                        }
                        break;
                    case 11:
                        String takeOffTotal11 = mControlElevenPick.getName();
                        mControlElevenPick.setName(takeOffTotal11 + "摘钩");
                        List<DataUser> elevenDataUsers = mElevenParkDataDao.find();
                        int elevenSize = elevenDataUsers.size();
                        String elevenNum = elevenDataUsers.get(elevenSize - 1).getNum();
                        Integer integerElevenNum = Integer.valueOf(elevenNum);
                        int elevenSum = integerElevenNum + 1;
                        if (elevenSize > 2) {
                            for (int i = 1; i < elevenSize; i++) {
                                String lat = elevenDataUsers.get(i).getLat();
                                String lon = elevenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat5), Double.valueOf(mLon5));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mElevenParkDataDao.add(mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", elevenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = elevenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mElevenParkDataDao.updaeUser("elevenparkcar", mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", minIndex + 1);
                            }
                        } else {
                            mElevenParkDataDao.add(mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", elevenSum + "");
                        }
                        break;
                    case 12:
                        String takeOffTotal12 = mControlTwelvePick.getName();
                        mControlTwelvePick.setName(takeOffTotal12 + "摘钩");
                        List<DataUser> twelveDataUsers = mTwelveParkDataDao.find();
                        int twelveSize = twelveDataUsers.size();
                        String twelveNum = twelveDataUsers.get(twelveSize - 1).getNum();
                        Integer integerTwelveNum = Integer.valueOf(twelveNum);
                        int twelveSum = integerTwelveNum + 1;
                        if (twelveSize > 2) {
                            for (int i = 1; i < twelveSize; i++) {
                                String lat = twelveDataUsers.get(i).getLat();
                                String lon = twelveDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat5), Double.valueOf(mLon5));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mTwelveParkDataDao.add(mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", twelveSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = twelveDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mTwelveParkDataDao.updaeUser("twelveparkcar", mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mTwelveParkDataDao.add(mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", twelveSum + "");
                        }
                        break;
                    case 13:
                        String takeOffTotal13 = mControlThirteenPick.getName();
                        mControlThirteenPick.setName(takeOffTotal13 + "摘钩");
                        List<DataUser> thirteenDataUsers = mThirteenParkDataDao.find();
                        int thirteenSize = thirteenDataUsers.size();
                        String thirteenNum = thirteenDataUsers.get(thirteenSize - 1).getNum();
                        Integer integerThirteenNum = Integer.valueOf(thirteenNum);
                        int thirteenSum = integerThirteenNum + 1;
                        if (thirteenSize > 2) {
                            for (int i = 1; i < thirteenSize; i++) {
                                String lat = thirteenDataUsers.get(i).getLat();
                                String lon = thirteenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat5), Double.valueOf(mLon5));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mThirteenParkDataDao.add(mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", thirteenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = thirteenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mThirteenParkDataDao.updaeUser("thirteenparkcar", mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mThirteenParkDataDao.add(mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", thirteenSum + "");
                        }
                        break;
                    case 14:
                        String takeOffTotal14 = mControlFourteenPick.getName();
                        mControlFourteenPick.setName(takeOffTotal14 + "摘钩");
                        List<DataUser> fourteenDataUsers = mFourteenParkDataDao.find();
                        int fourteenSize = fourteenDataUsers.size();
                        String fourteenNum = fourteenDataUsers.get(fourteenSize - 1).getNum();
                        Integer integerFourteenNum = Integer.valueOf(fourteenNum);
                        int fourteenSum = integerFourteenNum + 1;
                        if (fourteenSize > 2) {
                            for (int i = 1; i < fourteenSize; i++) {
                                String lat = fourteenDataUsers.get(i).getLat();
                                String lon = fourteenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat5), Double.valueOf(mLon5));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mFourteenParkDataDao.add(mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", fourteenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = fourteenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mFourteenParkDataDao.updaeUser("fourteenparkcar", mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mFourteenParkDataDao.add(mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", fourteenSum + "");
                        }
                    case 15:
                        String takeOffTotal15 = mControlFifteenPick.getName();
                        mControlFifteenPick.setName(takeOffTotal15 + "摘钩");
                        List<DataUser> fifteenDataUsers = mFifteenParkDataDao.find();
                        int fifteenSize = fifteenDataUsers.size();
                        String fifteenNum = fifteenDataUsers.get(fifteenSize - 1).getNum();
                        Integer integerFifteenNum = Integer.valueOf(fifteenNum);
                        int fifteenSum = integerFifteenNum + 1;
                        if (fifteenSize > 2) {
                            for (int i = 1; i < fifteenSize; i++) {
                                String lat = fifteenDataUsers.get(i).getLat();
                                String lon = fifteenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat5), Double.valueOf(mLon5));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mFifteenParkDataDao.add(mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", fifteenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = fifteenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mFifteenParkDataDao.updaeUser("fifteenparkcar", mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mFifteenParkDataDao.add(mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", fifteenSum + "");
                        }
                        break;
                    case 16:
                        String takeOffTotal16 = mControlSixteenPick.getName();
                        mControlSixteenPick.setName(takeOffTotal16 + "摘钩");
                        List<DataUser> sixteenDataUsers = mSixteenParkDataDao.find();
                        int sixteenSize = sixteenDataUsers.size();
                        String sixteenNum = sixteenDataUsers.get(sixteenSize - 1).getNum();
                        Integer integerSixteenNum = Integer.valueOf(sixteenNum);
                        int sixteenSum = integerSixteenNum + 1;
                        if (sixteenSize > 2) {
                            for (int i = 1; i < sixteenSize; i++) {
                                String lat = sixteenDataUsers.get(i).getLat();
                                String lon = sixteenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat5), Double.valueOf(mLon5));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mSixteenParkDataDao.add(mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", sixteenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = sixteenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mSixteenParkDataDao.updaeUser("sixteenparkcar", mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mSixteenParkDataDao.add(mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", sixteenSum + "");
                        }
                        break;
                    case 17:
                        String takeOffTotal17 = mControlSeventeenPick.getName();
                        mControlSeventeenPick.setName(takeOffTotal17 + "摘钩");
                        List<DataUser> seventeenDataUsers = mSeventeenParkDataDao.find();
                        int seventeenSize = seventeenDataUsers.size();
                        String seventeenNum = seventeenDataUsers.get(seventeenSize - 1).getNum();
                        Integer integerSeventeenNum = Integer.valueOf(seventeenNum);
                        int seventeenSum = integerSeventeenNum + 1;
                        if (seventeenSize > 2) {
                            for (int i = 1; i < seventeenSize; i++) {
                                String lat = seventeenDataUsers.get(i).getLat();
                                String lon = seventeenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat5), Double.valueOf(mLon5));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mSeventeenParkDataDao.add(mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", seventeenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = seventeenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mSeventeenParkDataDao.updaeUser("seventeenparkcar", mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mSeventeenParkDataDao.add(mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", seventeenSum + "");
                        }
                        break;
                    case 18:
                        String takeOffTotal18 = mControlEighteenPick.getName();
                        mControlEighteenPick.setName(takeOffTotal18 + "摘钩");
                        List<DataUser> eighteenDataUsers = mEighteenParkDataDao.find();
                        int eighteenSize = eighteenDataUsers.size();
                        String eighteenNum = eighteenDataUsers.get(eighteenSize - 1).getNum();
                        Integer integerEighteenNum = Integer.valueOf(eighteenNum);
                        int eighteenSum = integerEighteenNum + 1;
                        if (eighteenSize > 2) {
                            for (int i = 1; i < eighteenSize; i++) {
                                String lat = eighteenDataUsers.get(i).getLat();
                                String lon = eighteenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat5), Double.valueOf(mLon5));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mEighteenParkDataDao.add(mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", eighteenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = eighteenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mEighteenParkDataDao.updaeUser("eighteenparkcar", mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mEighteenParkDataDao.add(mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", eighteenSum + "");
                        }
                        break;
                    case 19:
                        String takeOffTotal19 = mControlNineteenPick.getName();
                        mControlNineteenPick.setName(takeOffTotal19 + "摘钩");
                        List<DataUser> nineteenDataUsers = mNineteenParkDataDao.find();
                        int nineteenSize = nineteenDataUsers.size();
                        String nineteenNum = nineteenDataUsers.get(nineteenSize - 1).getNum();
                        Integer integerNineteenNum = Integer.valueOf(nineteenNum);
                        int nineteenSum = integerNineteenNum + 1;
                        if (nineteenSize > 2) {
                            for (int i = 1; i < nineteenSize; i++) {
                                String lat = nineteenDataUsers.get(i).getLat();
                                String lon = nineteenDataUsers.get(i).getLon();
                                //计算紧急停车位置与数据库里的每一条数据的距离
                                double distance = getDistance(Double.valueOf(lat), Double.valueOf(lon), Double.valueOf(mLat5), Double.valueOf(mLon5));
                                if (distance > 11) {
                                    if (isSixTrack) {
                                        isSixTrack = false;
                                        mNineteenParkDataDao.add(mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", nineteenSum + "");
                                    }
                                } else {
                                    //获取在某个股道上的位置
                                    String ratioOfGpsPointCar = nineteenDataUsers.get(i).getRatioOfGpsPointCar();
                                    Integer integerPointCar = Integer.valueOf(ratioOfGpsPointCar);
                                    Integer integerGpsPistance2 = Integer.valueOf(mGpsPoint2 + "");
                                    int i1 = integerPointCar - integerGpsPistance2;
                                    if (i1 < 0) {
                                        int i2 = -i1;
                                        mListNum.add(i2);
                                    } else {
                                        mListNum.add(i1);
                                    }
                                }
                            }

                            isSixTrack = true;
                            if (mListNum.size() != 0) {
                                //取出list里最小值的下标替换
                                int minIndex = getMinIndex(mListNum);
                                mNineteenParkDataDao.updaeUser("nineteenparkcar", mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", minIndex + 1);
                            }

                        } else {
                            mNineteenParkDataDao.add(mGetGudaoOfGpsPoint5 + "", mLat5, mLon5, mGpsPoint2 + "", nineteenSum + "");
                        }
                        break;

                }
                break;
        }
    }

    private void guagou() {
        switch (mPeopleId2) {
            //1号调车员
            case "01":
                sixPerson();

                String total0 = "01-挂钩GPS-" + mLat61 + "-" + mLon61;
                sendMessage(mConversationId, total0);
                String hookTotal = mControlOnePick.getName();
                switch (mGetGudaoOfGpsPoint2) {
                    case 1:
                        mControlOnePick.setName(hookTotal + "挂钩");
                        mControlOnePick.setTrack(mRatioOfGpsTrackCar2);
                        mControlOnePick.setLat(mLat21);
                        mControlOnePick.setLon(mLon21);
                        break;
                    case 2:
                        mControlTwoPick.setName(hookTotal + "挂钩");
                        break;
                    case 3:
                        mControlThreePick.setName(hookTotal + "挂钩");
                        break;
                    case 4:
                        mControlFourPick.setName(hookTotal + "挂钩");
                        break;
                    case 5:
                        mControlFivePick.setName(hookTotal + "挂钩");
                        break;
                    case 6:
                        mControlSixPick.setName(hookTotal + "挂钩");
                        mControlSixPick.setLat(mLat21);
                        mControlSixPick.setLon(mLon21);
                        //股道
                        mControlSixPick.setTrack(mRatioOfGpsTrackCar2);
                        //位置
                        mControlSixPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 7:
                        mControlSevenPick.setName(hookTotal + "挂钩");
                        mControlSevenPick.setLat(mLat21);
                        mControlSevenPick.setLon(mLon21);
                        //股道
                        mControlSevenPick.setTrack(mRatioOfGpsTrackCar2);
                        //位置
                        mControlSevenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 8:
                        mControlEightPick.setName(hookTotal + "挂钩");
                        break;
                    case 9:
                        mControlNinePick.setName(hookTotal + "挂钩");
                        mControlNinePick.setLat(mLat21);
                        mControlNinePick.setLon(mLon21);
                        //股道
                        mControlNinePick.setTrack(mRatioOfGpsTrackCar2);
                        //位置
                        mControlNinePick.setPosition(mGpsPoint2 + "");
                        break;
                    case 10:
                        mControlTenPick.setName(hookTotal + "挂钩");
                        break;
                    case 11:
                        mControlElevenPick.setName(hookTotal + "挂钩");
                        mControlElevenPick.setLat(mLat21);
                        mControlElevenPick.setLon(mLon21);
                        //股道
                        mControlElevenPick.setTrack(mRatioOfGpsTrackCar2);
                        //位置
                        mControlElevenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 12:
                        mControlTwelvePick.setName(hookTotal + "挂钩");
                        mControlTwelvePick.setLat(mLat21);
                        mControlTwelvePick.setLon(mLon21);
                        //股道
                        mControlTwelvePick.setTrack(mRatioOfGpsTrackCar2);
                        //位置
                        mControlTwelvePick.setPosition(mGpsPoint2 + "");
                        break;
                    case 13:
                        mControlThirteenPick.setName(hookTotal + "挂钩");
                        mControlThirteenPick.setLat(mLat21);
                        mControlThirteenPick.setLon(mLon21);
                        //股道
                        mControlThirteenPick.setTrack(mRatioOfGpsTrackCar2);
                        //位置
                        mControlThirteenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 14:
                        mControlFourteenPick.setName(hookTotal + "挂钩");
                        mControlFourteenPick.setLat(mLat21);
                        mControlFourteenPick.setLon(mLon21);
                        //股道
                        mControlFourteenPick.setTrack(mRatioOfGpsTrackCar2);
                        //位置
                        mControlFourteenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 15:
                        mControlFifteenPick.setName(hookTotal + "挂钩");
                        mControlFifteenPick.setLat(mLat21);
                        mControlFifteenPick.setLon(mLon21);
                        //股道
                        mControlFifteenPick.setTrack(mRatioOfGpsTrackCar2);
                        //位置
                        mControlFifteenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 16:
                        mControlSixteenPick.setName(hookTotal + "挂钩");
                        mControlSixteenPick.setLat(mLat21);
                        mControlSixteenPick.setLon(mLon21);
                        //股道
                        mControlSixteenPick.setTrack(mRatioOfGpsTrackCar2);
                        //位置
                        mControlSixteenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 17:
                        mControlSeventeenPick.setName(hookTotal + "挂钩");
                        mControlSeventeenPick.setLat(mLat21);
                        mControlSeventeenPick.setLon(mLon21);
                        //股道
                        mControlSeventeenPick.setTrack(mRatioOfGpsTrackCar2);
                        //位置
                        mControlSeventeenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 18:
                        mControlEighteenPick.setName(hookTotal + "挂钩");
                        mControlEighteenPick.setLat(mLat21);
                        mControlEighteenPick.setLon(mLon21);
                        //股道
                        mControlEighteenPick.setTrack(mRatioOfGpsTrackCar2);
                        //位置
                        mControlEighteenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 19:
                        mControlNineteenPick.setName(hookTotal + "挂钩");
                        mControlNineteenPick.setLat(mLat21);
                        mControlNineteenPick.setLon(mLon21);
                        //股道
                        mControlNineteenPick.setTrack(mRatioOfGpsTrackCar2);
                        //位置
                        mControlNineteenPick.setPosition(mGpsPoint2 + "");
                        break;
                }
                break;
            case "02":
                sevenPerson();

                String total1 = "02-挂钩GPS-" + mLat61 + "-" + mLon61;
                sendMessage(mConversationId, total1);
                String hookTotal2 = mControlOnePick.getName();
                switch (mGetGudaoOfGpsPoint3) {
                    case 1:
                        mControlOnePick.setName(hookTotal2 + "挂钩");
                        break;
                    case 2:
                        mControlTwoPick.setName(hookTotal2 + "挂钩");
                        break;
                    case 3:
                        mControlThreePick.setName(hookTotal2 + "挂钩");
                        break;
                    case 4:
                        mControlFourPick.setName(hookTotal2 + "挂钩");
                        break;
                    case 5:
                        mControlFivePick.setName(hookTotal2 + "挂钩");
                        break;
                    case 6:
                        mControlSixPick.setName(hookTotal2 + "挂钩");
                        mControlSixPick.setLat(mLat31);
                        mControlSixPick.setLon(mLon31);
                        //股道
                        mControlSixPick.setTrack(mRatioOfGpsTrackCar3);
                        //位置
                        mControlSixPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 7:
                        mControlSevenPick.setName(hookTotal2 + "挂钩");
                        mControlSevenPick.setLat(mLat31);
                        mControlSevenPick.setLon(mLon31);
                        //股道
                        mControlSevenPick.setTrack(mRatioOfGpsTrackCar3);
                        //位置
                        mControlSevenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 8:
                        mControlEightPick.setName(hookTotal2 + "挂钩");
                        break;
                    case 9:
                        mControlNinePick.setName(hookTotal2 + "挂钩");
                        mControlNinePick.setLat(mLat31);
                        mControlNinePick.setLon(mLon31);
                        //股道
                        mControlNinePick.setTrack(mRatioOfGpsTrackCar3);
                        //位置
                        mControlNinePick.setPosition(mGpsPoint2 + "");
                        break;
                    case 10:
                        mControlTenPick.setName(hookTotal2 + "挂钩");
                        break;
                    case 11:
                        mControlElevenPick.setName(hookTotal2 + "挂钩");
                        mControlElevenPick.setLat(mLat31);
                        mControlElevenPick.setLon(mLon31);
                        //股道
                        mControlElevenPick.setTrack(mRatioOfGpsTrackCar3);
                        //位置
                        mControlElevenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 12:
                        mControlTwelvePick.setName(hookTotal2 + "挂钩");
                        mControlTwelvePick.setLat(mLat31);
                        mControlTwelvePick.setLon(mLon31);
                        //股道
                        mControlTwelvePick.setTrack(mRatioOfGpsTrackCar3);
                        //位置
                        mControlTwelvePick.setPosition(mGpsPoint2 + "");
                        break;
                    case 13:
                        mControlThirteenPick.setName(hookTotal2 + "挂钩");
                        mControlThirteenPick.setLat(mLat31);
                        mControlThirteenPick.setLon(mLon31);
                        //股道
                        mControlThirteenPick.setTrack(mRatioOfGpsTrackCar3);
                        //位置
                        mControlThirteenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 14:
                        mControlFourteenPick.setName(hookTotal2 + "挂钩");
                        mControlFourteenPick.setLat(mLat31);
                        mControlFourteenPick.setLon(mLon31);
                        //股道
                        mControlFourteenPick.setTrack(mRatioOfGpsTrackCar3);
                        //位置
                        mControlFourteenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 15:
                        mControlFifteenPick.setName(hookTotal2 + "挂钩");
                        mControlFifteenPick.setLat(mLat31);
                        mControlFifteenPick.setLon(mLon31);
                        //股道
                        mControlFifteenPick.setTrack(mRatioOfGpsTrackCar3);
                        //位置
                        mControlFifteenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 16:
                        mControlSixteenPick.setName(hookTotal2 + "挂钩");
                        mControlSixteenPick.setLat(mLat31);
                        mControlSixteenPick.setLon(mLon31);
                        //股道
                        mControlSixteenPick.setTrack(mRatioOfGpsTrackCar3);
                        //位置
                        mControlSixteenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 17:
                        mControlSeventeenPick.setName(hookTotal2 + "挂钩");
                        mControlSeventeenPick.setLat(mLat31);
                        mControlSeventeenPick.setLon(mLon31);
                        //股道
                        mControlSeventeenPick.setTrack(mRatioOfGpsTrackCar3);
                        //位置
                        mControlSeventeenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 18:
                        mControlEighteenPick.setName(hookTotal2 + "挂钩");
                        mControlEighteenPick.setLat(mLat31);
                        mControlEighteenPick.setLon(mLon31);
                        //股道
                        mControlEighteenPick.setTrack(mRatioOfGpsTrackCar3);
                        //位置
                        mControlEighteenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 19:
                        mControlNineteenPick.setName(hookTotal2 + "挂钩");
                        mControlNineteenPick.setLat(mLat31);
                        mControlNineteenPick.setLon(mLon31);
                        //股道
                        mControlNineteenPick.setTrack(mRatioOfGpsTrackCar3);
                        //位置
                        mControlNineteenPick.setPosition(mGpsPoint2 + "");
                        break;
                }
                break;
            case "03":
                eightPerson();

                String total2 = "03-挂钩GPS-" + mLat61 + "-" + mLon61;
                sendMessage(mConversationId, total2);
                String hookTotal3 = mControlOnePick.getName();
                switch (mGetGudaoOfGpsPoint4) {
                    case 1:
                        mControlOnePick.setName(hookTotal3 + "挂钩");
                        break;
                    case 2:
                        mControlTwoPick.setName(hookTotal3 + "挂钩");
                        break;
                    case 3:
                        mControlThreePick.setName(hookTotal3 + "挂钩");
                        break;
                    case 4:
                        mControlFourPick.setName(hookTotal3 + "挂钩");
                        break;
                    case 5:
                        mControlFivePick.setName(hookTotal3 + "挂钩");
                        break;
                    case 6:
                        mControlSixPick.setName(hookTotal3 + "挂钩");
                        mControlSixPick.setLat(mLat4);
                        mControlSixPick.setLon(mLon4);
                        //股道
                        mControlSixPick.setTrack(mRatioOfGpsTrackCar4);
                        //位置
                        mControlSixPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 7:
                        mControlSevenPick.setName(hookTotal3 + "挂钩");
                        mControlSevenPick.setLat(mLat4);
                        mControlSevenPick.setLon(mLon4);
                        //股道
                        mControlSevenPick.setTrack(mRatioOfGpsTrackCar4);
                        //位置
                        mControlSevenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 8:
                        mControlEightPick.setName(hookTotal3 + "挂钩");
                        break;
                    case 9:
                        mControlNinePick.setName(hookTotal3 + "挂钩");
                        mControlNinePick.setLat(mLat4);
                        mControlNinePick.setLon(mLon4);
                        //股道
                        mControlNinePick.setTrack(mRatioOfGpsTrackCar4);
                        //位置
                        mControlNinePick.setPosition(mGpsPoint2 + "");
                        break;
                    case 10:
                        mControlTenPick.setName(hookTotal3 + "挂钩");
                        break;
                    case 11:
                        mControlElevenPick.setName(hookTotal3 + "挂钩");
                        mControlElevenPick.setLat(mLat4);
                        mControlElevenPick.setLon(mLon4);
                        //股道
                        mControlElevenPick.setTrack(mRatioOfGpsTrackCar4);
                        //位置
                        mControlElevenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 12:
                        mControlTwelvePick.setName(hookTotal3 + "挂钩");
                        mControlTwelvePick.setLat(mLat4);
                        mControlTwelvePick.setLon(mLon4);
                        //股道
                        mControlTwelvePick.setTrack(mRatioOfGpsTrackCar4);
                        //位置
                        mControlTwelvePick.setPosition(mGpsPoint2 + "");
                        break;
                    case 13:
                        mControlThirteenPick.setName(hookTotal3 + "挂钩");
                        mControlThirteenPick.setLat(mLat4);
                        mControlThirteenPick.setLon(mLon4);
                        //股道
                        mControlThirteenPick.setTrack(mRatioOfGpsTrackCar4);
                        //位置
                        mControlThirteenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 14:
                        mControlFourteenPick.setName(hookTotal3 + "挂钩");
                        mControlFourteenPick.setLat(mLat4);
                        mControlFourteenPick.setLon(mLon4);
                        //股道
                        mControlFourteenPick.setTrack(mRatioOfGpsTrackCar4);
                        //位置
                        mControlFourteenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 15:
                        mControlFifteenPick.setName(hookTotal3 + "挂钩");
                        mControlFifteenPick.setLat(mLat4);
                        mControlFifteenPick.setLon(mLon4);
                        //股道
                        mControlFifteenPick.setTrack(mRatioOfGpsTrackCar4);
                        //位置
                        mControlFifteenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 16:
                        mControlSixteenPick.setName(hookTotal3 + "挂钩");
                        mControlSixteenPick.setLat(mLat4);
                        mControlSixteenPick.setLon(mLon4);
                        //股道
                        mControlSixteenPick.setTrack(mRatioOfGpsTrackCar4);
                        //位置
                        mControlSixteenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 17:
                        mControlSeventeenPick.setName(hookTotal3 + "挂钩");
                        mControlSeventeenPick.setLat(mLat4);
                        mControlSeventeenPick.setLon(mLon4);
                        //股道
                        mControlSeventeenPick.setTrack(mRatioOfGpsTrackCar4);
                        //位置
                        mControlSeventeenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 18:
                        mControlEighteenPick.setName(hookTotal3 + "挂钩");
                        mControlEighteenPick.setLat(mLat4);
                        mControlEighteenPick.setLon(mLon4);
                        //股道
                        mControlEighteenPick.setTrack(mRatioOfGpsTrackCar4);
                        //位置
                        mControlEighteenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 19:
                        mControlNineteenPick.setName(hookTotal3 + "挂钩");
                        mControlNineteenPick.setLat(mLat4);
                        mControlNineteenPick.setLon(mLon4);
                        //股道
                        mControlNineteenPick.setTrack(mRatioOfGpsTrackCar4);
                        //位置
                        mControlNineteenPick.setPosition(mGpsPoint2 + "");
                        break;
                }
                break;
            case "04":
                ninePerson();

                String total3 = "04-挂钩GPS-" + mLat61 + "-" + mLon61;
                sendMessage(mConversationId, total3);
                String hookTotal4 = mControlOnePick.getName();
                switch (mGetGudaoOfGpsPoint4) {
                    case 1:
                        mControlOnePick.setName(hookTotal4 + "挂钩");
                        break;
                    case 2:
                        mControlTwoPick.setName(hookTotal4 + "挂钩");
                        break;
                    case 3:
                        mControlThreePick.setName(hookTotal4 + "挂钩");
                        break;
                    case 4:
                        mControlFourPick.setName(hookTotal4 + "挂钩");
                        break;
                    case 5:
                        mControlFivePick.setName(hookTotal4 + "挂钩");
                        break;
                    case 6:
                        mControlSixPick.setName(hookTotal4 + "挂钩");
                        mControlSixPick.setLat(mLat5);
                        mControlSixPick.setLon(mLon5);
                        //股道
                        mControlSixPick.setTrack(mRatioOfGpsTrackCar5);
                        //位置
                        mControlSixPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 7:
                        mControlSevenPick.setName(hookTotal4 + "挂钩");
                        mControlSevenPick.setLat(mLat5);
                        mControlSevenPick.setLon(mLon5);
                        //股道
                        mControlSevenPick.setTrack(mRatioOfGpsTrackCar5);
                        //位置
                        mControlSevenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 8:
                        mControlEightPick.setName(hookTotal4 + "挂钩");
                        break;
                    case 9:
                        mControlNinePick.setName(hookTotal4 + "挂钩");
                        mControlNinePick.setLat(mLat5);
                        mControlNinePick.setLon(mLon5);
                        //股道
                        mControlNinePick.setTrack(mRatioOfGpsTrackCar5);
                        //位置
                        mControlNinePick.setPosition(mGpsPoint2 + "");
                        break;
                    case 10:
                        mControlTenPick.setName(hookTotal4 + "挂钩");
                        break;
                    case 11:
                        mControlElevenPick.setName(hookTotal4 + "挂钩");
                        mControlElevenPick.setLat(mLat5);
                        mControlElevenPick.setLon(mLon5);
                        //股道
                        mControlElevenPick.setTrack(mRatioOfGpsTrackCar5);
                        //位置
                        mControlElevenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 12:
                        mControlTwelvePick.setName(hookTotal4 + "挂钩");
                        mControlTwelvePick.setLat(mLat5);
                        mControlTwelvePick.setLon(mLon5);
                        //股道
                        mControlTwelvePick.setTrack(mRatioOfGpsTrackCar5);
                        //位置
                        mControlTwelvePick.setPosition(mGpsPoint2 + "");
                        break;
                    case 13:
                        mControlThirteenPick.setName(hookTotal4 + "挂钩");
                        mControlThirteenPick.setLat(mLat5);
                        mControlThirteenPick.setLon(mLon5);
                        //股道
                        mControlThirteenPick.setTrack(mRatioOfGpsTrackCar5);
                        //位置
                        mControlThirteenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 14:
                        mControlFourteenPick.setName(hookTotal4 + "挂钩");
                        mControlFourteenPick.setLat(mLat5);
                        mControlFourteenPick.setLon(mLon5);
                        //股道
                        mControlFourteenPick.setTrack(mRatioOfGpsTrackCar5);
                        //位置
                        mControlFourteenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 15:
                        mControlFifteenPick.setName(hookTotal4 + "挂钩");
                        mControlFifteenPick.setLat(mLat5);
                        mControlFifteenPick.setLon(mLon5);
                        //股道
                        mControlFifteenPick.setTrack(mRatioOfGpsTrackCar5);
                        //位置
                        mControlFifteenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 16:
                        mControlSixteenPick.setName(hookTotal4 + "挂钩");
                        mControlSixteenPick.setLat(mLat5);
                        mControlSixteenPick.setLon(mLon5);
                        //股道
                        mControlSixteenPick.setTrack(mRatioOfGpsTrackCar5);
                        //位置
                        mControlSixteenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 17:
                        mControlSeventeenPick.setName(hookTotal4 + "挂钩");
                        mControlSeventeenPick.setLat(mLat5);
                        mControlSeventeenPick.setLon(mLon5);
                        //股道
                        mControlSeventeenPick.setTrack(mRatioOfGpsTrackCar5);
                        //位置
                        mControlSeventeenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 18:
                        mControlEighteenPick.setName(hookTotal4 + "挂钩");
                        mControlEighteenPick.setLat(mLat5);
                        mControlEighteenPick.setLon(mLon5);
                        //股道
                        mControlEighteenPick.setTrack(mRatioOfGpsTrackCar5);
                        //位置
                        mControlEighteenPick.setPosition(mGpsPoint2 + "");
                        break;
                    case 19:
                        mControlNineteenPick.setName(hookTotal4 + "挂钩");
                        mControlNineteenPick.setLat(mLat5);
                        mControlNineteenPick.setLon(mLon5);
                        //股道
                        mControlNineteenPick.setTrack(mRatioOfGpsTrackCar5);
                        //位置
                        mControlNineteenPick.setPosition(mGpsPoint2 + "");
                        break;
                }
                break;
        }
    }
}
