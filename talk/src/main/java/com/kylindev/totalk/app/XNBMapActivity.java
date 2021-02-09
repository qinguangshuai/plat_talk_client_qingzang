package com.kylindev.totalk.app;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.kylindev.totalk.R;
import com.kylindev.totalk.bjxt.SpUtil;
import com.kylindev.totalk.bjxt.custom.DrawTop;
import com.kylindev.totalk.bjxt.custom.ThirteenthMap;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;

public class XNBMapActivity extends Activity {

    private TextView mXnText;
    private ImageView mBack;
    private Button mBtn1, mBtn2, mBtn3, mBtn4;
    private DrawTop mTrain;
    private ThirteenthMap mThirt;
    private int num = 0;
    int mTime = 500;
    private SpUtil mSpUtil;
    private double a = 101.765617;
    private double b = 36.667481;
    private Integer mInteger3;
    private Integer mInteger4;
    private Integer mInteger1;
    private Integer mInteger2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xnbmap);
        //setSystemUIVisible(false);
        mXnText = findViewById(R.id.text_xn);
        mBack = findViewById(R.id.back_map);
        mXnText.setText(R.string.app_xn);
        mXnText.setTextSize(30);
        mXnText.setTypeface(Typeface.DEFAULT_BOLD);
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        mBtn1 = findViewById(R.id.btn1);
        mBtn2 = findViewById(R.id.btn2);
        mBtn3 = findViewById(R.id.btn3);
        mBtn4 = findViewById(R.id.btn4);
        mTrain = findViewById(R.id.train);
        mThirt = findViewById(R.id.thirt);
        mSpUtil = new SpUtil(getApplication(), "cqncast13");
        mSpUtil.setName("0");
        mSpUtil.setEmail("false");
        final String email = mSpUtil.getEmail();

        DecimalFormat df = new DecimalFormat("#.000000");
        DecimalFormatSymbols symbols = new DecimalFormatSymbols();
        df.setDecimalFormatSymbols(symbols);
        mTrain.setTranslationY(210);
        //拿到的经纬度
        String lat = df.format(Double.valueOf(a));
        String lon = df.format(Double.valueOf(b));
        //最大、小值
        String lat1 = df.format(Double.valueOf(101.765617));
        String lon1 = df.format(Double.valueOf(101.766317));

        //取后六位
        String aa = lat.substring(lat.indexOf(".") + 1);
        String bb = lon.substring(lon.indexOf(".") + 1);
        String cc = lat1.substring(lat1.indexOf(".") + 1);
        String dd = lon1.substring(lon1.indexOf(".") + 1);
        Log.e("QGS", aa + " " + bb + " " + cc + " " + dd);
        //a
        mInteger1 = Integer.valueOf(aa);
        //a
        mInteger2 = Integer.valueOf(bb);
        //c
        mInteger3 = Integer.valueOf(cc);
        //d
        mInteger4 = Integer.valueOf(dd);
        num = mInteger1;

        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (a >= 101.765617 || a <= 101.766317 || b >= 36.666439 || b <= 36.668411) {
                    if (num >= 765617 && num < 766317) {
                        int i2 = num + 100;
                        num = i2;
                        int i = (768 - 0) / (mInteger4 - mInteger3);
                        int i1 = i * (num - mInteger3) + 0;
                        mSpUtil.setName(i1 + "");
                        mTrain.setTranslationY(210);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", i1);
                        animator.setDuration(2000);
                        animator.start();
                    }
                }
            }
        });
        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (a >= 101.765617 || a <= 101.766317 || b >= 36.666439 || b <= 36.668411) {
                    if (num > 765617 && num <= 766317) {
                        int i2 = num - 100;
                        num = i2;
                        int i = (768 - 0) / (mInteger4 - mInteger3);
                        int i1 = i * (num - mInteger3) + 0;
                        mSpUtil.setName(i1 + "");
                        mTrain.setTranslationY(210);
                        ObjectAnimator animator
                                = ObjectAnimator.ofFloat(mTrain, "translationX", i1);
                        animator.setDuration(2000);
                        animator.start();
                    }
                }
            }
        });

        mBtn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mThirt.invalidate();
                mThirt.setVisibility(View.VISIBLE);
                /*String s = String.valueOf(num);
                Toast.makeText(getApplication(),s,Toast.LENGTH_SHORT).show();*/
            }
        });
        mBtn4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mThirt.invalidate();
                mThirt.setVisibility(View.GONE);
            }
        });
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

    /**
     * 区间映射算法
     * 将[Omin，Omax]上每个数映射到区间[Nmin,Nmax]上。
     * 映射算法思想：
     * 计算出N区间长度除以O区间长度，得出O区间上单位长度对应于N区间上的大小，
     * 再将O区间上每个数减去O区间最小值后乘以单位区间对应的长度，
     * 最后加上N区间的最小值，实现投射到N区间上。
     * */
    private int num(int newmax, int newmin, int oldmax, int oldmin, int currentvalue) {
        int i = (newmax - newmin) / (oldmax - oldmin);
        int i1 = i * (currentvalue - oldmin) + 0;
        return i1;
    }
}
