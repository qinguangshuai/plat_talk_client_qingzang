package com.kylindev.totalk.qgs.park.three;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.kylindev.totalk.bjxt.SpUtil;

/**
 * @date 2020/8/10 16:33
 * 1道停留车
 */
public class ThreeParkCar extends View {

    private Paint mPaint;

    public ThreeParkCar(Context context) {
        this(context, null);
    }

    public ThreeParkCar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ThreeParkCar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //创建一个画笔
        mPaint = new Paint(Paint.DITHER_FLAG);
        //设置非填充
        mPaint.setStyle(Paint.Style.STROKE);
        //笔宽5像素
        mPaint.setStrokeWidth(20);
        //设置为红笔
        mPaint.setColor(Color.parseColor("#C00404"));
        //设置抗锯齿
        mPaint.setAntiAlias(true);
        //设置图像抖动处理
        mPaint.setDither(true);
        //设置图像的结合方式
        mPaint.setStrokeJoin(Paint.Join.ROUND);

        int height = canvas.getHeight();
        int width = canvas.getWidth();

        //1道停留车左点
        SpUtil mThreePickLeft = new SpUtil(getContext(), "threepickleft");
        String nameLeft = mThreePickLeft.getPosition();
        //1道停留车右点
        SpUtil mThreepickrightight = new SpUtil(getContext(), "threepickright");
        String nameRight = mThreepickrightight.getPosition();
        if (nameLeft != null && nameRight != null){
            if (!nameLeft.equals("0") && !nameRight.equals("0")){
                Float left = Float.valueOf(nameLeft);
                Float right = Float.valueOf(nameRight);
                canvas.drawLine((128 + left * 8.46f), 400, (128 + right * 8.46f), 400, mPaint);
            }
        }
    }
}
