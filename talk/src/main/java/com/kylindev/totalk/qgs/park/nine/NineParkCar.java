package com.kylindev.totalk.qgs.park.nine;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.kylindev.totalk.qgs.park.DataUser;
import com.kylindev.totalk.qgs.park.six.SixParkDataDao;

import java.util.List;

/**
 * @date 2020/8/10 16:33
 * 9道停留车
 */
public class NineParkCar extends View {

    private Paint mPaint;
    private Bitmap mBitmap;
    private Canvas mCanvas1;
    private Bitmap mBitmap1;

    public NineParkCar(Context context) {
        this(context, null);
    }

    public NineParkCar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public NineParkCar(Context context, AttributeSet attrs, int defStyleAttr) {
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

        NineParkDataDao nineParkDataDao = new NineParkDataDao(getContext());
        List<DataUser> dataUsers = nineParkDataDao.find();
        int size = dataUsers.size();
        if (size > 1 && size % 2 != 0) {
            for (int i = 1; i < size; i += 2) {
                String ratioOfGpsPointCar = dataUsers.get(i).getRatioOfGpsPointCar();
                Float aDoubleRatioOfGpsPointCar = Float.valueOf(ratioOfGpsPointCar);
                String ratioOfGpsPointCar1 = dataUsers.get(i + 1).getRatioOfGpsPointCar();
                Float aDoubleRatioOfGpsPointCar1 = Float.valueOf(ratioOfGpsPointCar1);
                if (aDoubleRatioOfGpsPointCar < 43 && aDoubleRatioOfGpsPointCar1 > 81){
                    canvas.drawLine((50 + 43 * 8.65f), 400, (50 + 81 * 8.65f), 400, mPaint);
                }else if (aDoubleRatioOfGpsPointCar < 43 && aDoubleRatioOfGpsPointCar1 < 81){
                    canvas.drawLine((50 + 43 * 8.65f), 400, (50 + aDoubleRatioOfGpsPointCar1 * 8.65f), 400, mPaint);
                }else if (aDoubleRatioOfGpsPointCar > 43 && aDoubleRatioOfGpsPointCar1 > 81){
                    canvas.drawLine((50 + aDoubleRatioOfGpsPointCar * 8.65f), 400, (50 + 81 * 8.65f), 400, mPaint);
                }else {
                    canvas.drawLine((50 + aDoubleRatioOfGpsPointCar * 8.65f), 400, (50 + aDoubleRatioOfGpsPointCar1 * 8.65f), 400, mPaint);
                }
            }
        }
    }
}
