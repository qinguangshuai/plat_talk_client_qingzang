package com.kylindev.totalk.qgs.park.ten;

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
public class TenParkCar extends View {

    private Paint mPaint;

    public TenParkCar(Context context) {
        this(context, null);
    }

    public TenParkCar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TenParkCar(Context context, AttributeSet attrs, int defStyleAttr) {
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
        SpUtil mTenPickLeft = new SpUtil(getContext(), "tenpickleft");
        String nameLeft = mTenPickLeft.getPosition();
        //1道停留车右点
        SpUtil mTenpickrightight = new SpUtil(getContext(), "tenpickright");
        String nameRight = mTenpickrightight.getPosition();
        if (nameLeft != null && nameRight != null){
            if (!nameLeft.equals("0") && !nameRight.equals("0")){
                Float left = Float.valueOf(nameLeft);
                Float right = Float.valueOf(nameRight);
                if (left < 21 && right > 76) {
                    canvas.drawLine((384 + (21 - 21) * 6.05f), 50, (384 + (76 - 21) * 6.05f), 50, mPaint);
                }else if (left < 21 && right <= 76) {
                    canvas.drawLine((384 + (21 - 21) * 6.05f), 50, (384 + (right - 21) * 6.05f), 50, mPaint);
                }else if (left >= 21 && right > 76){
                    canvas.drawLine((384 + (left - 21) * 6.05f), 50, (384 + (76 - 21) * 6.05f), 50, mPaint);
                }else {
                    canvas.drawLine((384 + (left - 21) * 6.05f), 50, (384 + (right - 21) * 6.05f), 50, mPaint);
                }
            }
        }
    }
}
