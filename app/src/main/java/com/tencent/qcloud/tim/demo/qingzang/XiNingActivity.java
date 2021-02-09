package com.tencent.qcloud.tim.demo.qingzang;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.ObjectAnimator;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.tencent.qcloud.tim.demo.R;
import com.tencent.qcloud.tim.demo.bjxt.DrawTop;

public class XiNingActivity extends AppCompatActivity {

    private TextView mXnText;
    private ImageView mBack;
    private Button mBtn1;
    private Button mBtn2;
    private DrawTop mTrain;
    private int num = 0;
    int mTime = 500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xi_ning);
        setSystemUIVisible(false);
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
        mTrain = findViewById(R.id.train);
        mBtn1 = findViewById(R.id.btn1);
        mBtn2 = findViewById(R.id.btn2);
        mBtn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = num + 10;
                num = i;
                ObjectAnimator animator
                        = ObjectAnimator.ofFloat(mTrain, "translationX", 30 + i * 9.2f);
                animator.setDuration(mTime);
                animator.start();
            }
        });
        mBtn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int i = num - 10;
                num = i;
                ObjectAnimator animator
                        = ObjectAnimator.ofFloat(mTrain, "translationX", 30 + i * 9.2f);
                animator.setDuration(mTime);
                animator.start();
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
}
