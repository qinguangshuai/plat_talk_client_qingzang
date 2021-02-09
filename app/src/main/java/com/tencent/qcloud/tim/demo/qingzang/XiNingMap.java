package com.tencent.qcloud.tim.demo.qingzang;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * @date 2021/1/13 14:36
 */
public class XiNingMap extends View {

    private Paint mPaint, paint;
    private Bitmap mBitmap;
    private Canvas mCanvas1;
    private Bitmap mBitmap1;
    int i = 90;

    public XiNingMap(Context context) {
        this(context, null);
    }

    public XiNingMap(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XiNingMap(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.parseColor("#FAEBD6"));

        //创建一个画笔
        mPaint = new Paint(Paint.DITHER_FLAG);
        paint = new Paint(Paint.DITHER_FLAG);
        //设置位图的宽高
        mBitmap = Bitmap.createBitmap(1280, 800, Bitmap.Config.RGB_565);
        //绘制内容保存到Bitmap
        mCanvas1 = new Canvas(mBitmap);
        //设置非填充
        mPaint.setStyle(Paint.Style.STROKE);
        paint.setStyle(Paint.Style.FILL);
        //笔宽5像素
        mPaint.setStrokeWidth(3);
        paint.setStrokeWidth(1);
        paint.setTextSize(18);
        //设置为红笔
        mPaint.setColor(Color.BLACK);
        paint.setColor(Color.RED);
        //设置抗锯齿
        mPaint.setAntiAlias(true);
        paint.setAntiAlias(true);
        //设置图像抖动处理
        mPaint.setDither(true);
        paint.setDither(true);
        //设置图像的结合方式
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        paint.setStrokeJoin(Paint.Join.ROUND);

        int height = canvas.getHeight();
        int width = canvas.getWidth();
        Log.e("二宝", width + "  :  " + height);
        int height1 = height - 100 - 50;
        int wight1 = width / 8 / 5;

        //H3直线
        canvas.drawLine(width / 8 * 2 + wight1, 30 + height1 / 11, width / 8 * 6, 30 + height1 / 11, mPaint);
        canvas.drawText("H3", (float) (width / 10 * 4.5), 30 + height1 / 11 + 15, paint);
        //H3左斜线
        canvas.drawLine(width / 8 * 2 + wight1, 30 + height1 / 11, width / 8 * 2 - wight1, 30 + height1 / 11 * 3, mPaint);
        //H3右斜线
        canvas.drawLine((float) (width / 8 * 6 - wight1 * 1.3), 30 + height1 / 11 * 3, width / 8 * 4 + wight1 * 3, 30 + height1 / 11, mPaint);
        //H2直线
        canvas.drawLine(width / 8 * 2 - wight1 * 2, 30 + height1 / 11 * 3, width / 8 * 6 - wight1, 30 + height1 / 11 * 3, mPaint);
        //右斜线
        canvas.drawLine(width / 8 * 6 - wight1, 30 + height1 / 11 * 3, (float) (width / 8 * 6.4), (float) (30 + height1 / 11 * 7.2), mPaint);
        canvas.drawText("H2", (float) (width / 10 * 4.5), 30 + height1 / 11 * 3 + 15, paint);
        //H1直线
        canvas.drawLine(0, 30 + height1 / 11 * 6, width / 8 * 6, 30 + height1 / 11 * 6, mPaint);
        //右斜线
        canvas.drawLine(width / 8 * 6, 30 + height1 / 11 * 6, width / 8 * 7, 30 + height1 / 11 * 9, mPaint);
        canvas.drawText("H1", (float) (width / 10 * 4.5), 30 + height1 / 11 * 6 + 15, paint);
        //H1左斜线
        canvas.drawLine(width / 8 * 2 - wight1 * 2, 30 + height1 / 11 * 3, width / 8, 30 + height1 / 11 * 6, mPaint);
        //5直线
        canvas.drawLine(width / 8 * 2 + wight1 * 2, 30 + height1 / 11 * 7, width / 8 * 4 + wight1 * 3, 30 + height1 / 11 * 7, mPaint);
        //左斜线
        canvas.drawLine(width / 8 * 2 + wight1 * 2, 30 + height1 / 11 * 7, width / 8 + wight1 * 2, 30 + height1 / 11 * 9, mPaint);
        //右斜线
        canvas.drawLine(width / 8 * 4 + wight1 * 3, 30 + height1 / 11 * 7, width / 8 * 5 + wight1 * 3, 30 + height1 / 11 * 9, mPaint);
        canvas.drawText("5", (float) (width / 10 * 4.5), 30 + height1 / 11 * 7 + 15, paint);
        //4直线
        canvas.drawLine((float) (width / 8 * 1.9), 30 + height1 / 11 * 8, (float) (width / 8 * 5.1), 30 + height1 / 11 * 8, mPaint);
        canvas.drawText("4", (float) (width / 10 * 4.5), 30 + height1 / 11 * 8 + 15, paint);
        //III直线
        canvas.drawLine((float) (width / 8 * 0.7), 30 + height1 / 11 * 9, width, 30 + height1 / 11 * 9, mPaint);
        //左斜线
        canvas.drawLine((float) (width / 8 * 0.7), 30 + height1 / 11 * 9, 0, 30 + height1 / 11 * 6, mPaint);
        //斜线
        canvas.drawLine(width / 8, 30 + height1 / 11 * 9, (float) (width / 8 * 1.7), 30 + height1 / 11 * 10, mPaint);
        canvas.drawText("III", (float) (width / 10 * 4.5), 30 + height1 / 11 * 9 + 15, paint);
        //2直线
        canvas.drawLine(0, 30 + height1 / 11 * 10, width / 8 * 7, 30 + height1 / 11 * 10, mPaint);
        canvas.drawLine(0, 30 + height1 / 11 * 9, (float) (width / 8 * 0.4), 30 + height1 / 11 * 9, mPaint);
        canvas.drawLine((float) (width / 8 * 0.4), 30 + height1 / 11 * 9, (float) (width / 8 * 0.7), 30 + height1 / 11 * 10, mPaint);
        //右斜线
        canvas.drawLine(width / 8 * 7, 30 + height1 / 11 * 10, (float) (width / 8 * 7.4), 30 + height1 / 11 * 12, mPaint);
        canvas.drawText("2", (float) (width / 10 * 4.5), 30 + height1 / 11 * 10 + 15, paint);
        //物2
        canvas.drawLine((float) (width / 8 * 7.4), 30 + height1 / 11 * 12, width, 30 + height1 / 11 * 12, mPaint);
        canvas.drawText("物2", (float) (width / 8 * 7.8), 30 + height1 / 11 * 12 + 15, paint);
        //物3
        canvas.drawLine((float) (width / 8 * 7.5), 30 + height1 / 11 * 12, (float) (width / 8 * 7.6), 30 + height1 / 11 * 11, mPaint);
        canvas.drawLine((float) (width / 8 * 7.6), 30 + height1 / 11 * 11, width, 30 + height1 / 11 * 11, mPaint);
        canvas.drawText("物3", (float) (width / 8 * 7.8), 30 + height1 / 11 * 11 + 15, paint);
        //物1
        canvas.drawLine((float) (width / 8 * 7.4), 30 + height1 / 11 * 12, (float) (width / 8 * 7.6), 30 + height1 / 11 * 13, mPaint);
        canvas.drawLine((float) (width / 8 * 7.6), 30 + height1 / 11 * 13, width, 30 + height1 / 11 * 13, mPaint);
        canvas.drawText("物1", (float) (width / 8 * 7.8), 30 + height1 / 11 * 13 + 15, paint);
        //1直线
        canvas.drawLine((float) (width / 8 * 2.4), 30 + height1 / 11 * 11, width / 8 * 5, 30 + height1 / 11 * 11, mPaint);
        //左斜线
        canvas.drawLine((float) (width / 8 * 2.4), 30 + height1 / 11 * 11, width / 8 * 2, 30 + height1 / 11 * 10, mPaint);
        //长丰专用线
        canvas.drawLine(width / 8, 30 + height1 / 11 * 10, (float) (width / 8 * 0.8), 30 + height1 / 11 * 11, mPaint);
        canvas.drawLine((float) (width / 8 * 0.1), 30 + height1 / 11 * 11, (float) (width / 8 * 1.2), 30 + height1 / 11 * 11, mPaint);
        canvas.drawLine((float) (width / 8 * 1.5), 30 + height1 / 11 * 10, (float) (width / 8 * 1.7), 30 + height1 / 11 * 11, mPaint);
        canvas.drawLine((float) (width / 8 * 1.7), 30 + height1 / 11 * 11, width / 8 * 2, 30 + height1 / 11 * 11, mPaint);
        canvas.drawText("长丰专用线", width / 8, 30 + height1 / 11 * 11 + 15, paint);
        //右斜线
        canvas.drawLine(width / 8 * 5, 30 + height1 / 11 * 11, (float) (width / 8 * 6.8), 30 + height1 / 11 * 14, mPaint);
        canvas.drawLine((float) (width / 8 * 6.8), 30 + height1 / 11 * 14, (float) (width / 8 * 7.5), 30 + height1 / 11 * 14, mPaint);
        canvas.drawText("百立专用线", (float) (width / 8 * 6.6), (float) (30 + height1 / 11 * 13.5), paint);
        canvas.drawText("1", (float) (width / 10 * 4.5), 30 + height1 / 11 * 11 + 15, paint);

        //canvas.drawLine(0, 500, 1024, 500, paint);
    }
}
