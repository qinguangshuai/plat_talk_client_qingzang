package com.kylindev.totalk.qgs.park.one;

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
public class OneParkCar extends View {

    private Paint mPaint;
    private Bitmap mBitmap;
    private Canvas mCanvas1;
    private Bitmap mBitmap1;

    public OneParkCar(Context context) {
        this(context, null);
    }

    public OneParkCar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OneParkCar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        //创建一个画笔
        mPaint = new Paint(Paint.DITHER_FLAG);
        //设置位图的宽高
        mBitmap = Bitmap.createBitmap(1280, 800, Bitmap.Config.RGB_565);
        //绘制内容保存到Bitmap
        mCanvas1 = new Canvas(mBitmap);
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
        SpUtil mOnePickLeft = new SpUtil(getContext(), "onepickleft");
        String nameLeft = mOnePickLeft.getName();
        //1道停留车右点
        SpUtil mOnepickrightight = new SpUtil(getContext(), "onepickright");
        String nameRight = mOnepickrightight.getName();
        if (nameLeft != null && nameRight != null){
            Float left = Float.valueOf(nameLeft);
            Float right = Float.valueOf(nameRight);
            canvas.drawLine((384 + (left - 5) * 2.88f), 500, (384 + (right - 5) * 2.88f), 500, mPaint);
        }
    }
}
