package com.kylindev.totalk.qgs.park.eighteen;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import com.kylindev.totalk.qgs.park.DataUser;
import com.kylindev.totalk.qgs.park.seventeen.SeventeenParkDataDao;

import java.util.List;

/**
 * @date 2021/6/2 16:33
 * 12道停留车
 */
public class EighteenParkCar extends View {

    private Paint mPaint;

    public EighteenParkCar(Context context) {
        this(context, null);
    }

    public EighteenParkCar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public EighteenParkCar(Context context, AttributeSet attrs, int defStyleAttr) {
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

        EighteenParkDataDao eighteenParkDataDao = new EighteenParkDataDao(getContext());
        List<DataUser> dataUsers = eighteenParkDataDao.find();
        int size = dataUsers.size();
        if (size > 1 && size % 2 != 0) {
            for (int i = 1; i < size; i += 2) {
                String ratioOfGpsPointCar = dataUsers.get(i).getRatioOfGpsPointCar();
                Float aDoubleRatioOfGpsPointCar = Float.valueOf(ratioOfGpsPointCar);
                String ratioOfGpsPointCar1 = dataUsers.get(i + 1).getRatioOfGpsPointCar();
                Float aDoubleRatioOfGpsPointCar1 = Float.valueOf(ratioOfGpsPointCar1);
                //(550 + (mGpsPistanceCar - 8) * 2.82f)
                if (aDoubleRatioOfGpsPointCar < 8 && aDoubleRatioOfGpsPointCar1 < 80 && aDoubleRatioOfGpsPointCar1 >= 8) {
                    canvas.drawLine((550 + (8 - 8) * 2.82f), 250, (550 + (aDoubleRatioOfGpsPointCar1 - 8) * 2.82f), 250, mPaint);
                } else if (aDoubleRatioOfGpsPointCar < 8 && aDoubleRatioOfGpsPointCar1 < 8) {
                    canvas.drawLine((550 + (8 - 8) * 2.82f), 250, (550 + (8 - 8) * 2.82f), 250, mPaint);
                } else if (aDoubleRatioOfGpsPointCar >= 8 && aDoubleRatioOfGpsPointCar1 < 8) {
                    canvas.drawLine((550 + (aDoubleRatioOfGpsPointCar - 8) * 2.82f), 250, (550 + (8 - 8) * 2.82f), 250, mPaint);
                } else if (aDoubleRatioOfGpsPointCar >= 8 && aDoubleRatioOfGpsPointCar1 >= 8 && aDoubleRatioOfGpsPointCar1 < 80) {
                    canvas.drawLine((550 + (aDoubleRatioOfGpsPointCar - 8) * 2.82f), 250, (550 + (aDoubleRatioOfGpsPointCar1 - 8) * 2.82f), 250, mPaint);
                } else if (aDoubleRatioOfGpsPointCar1 >= 8 && aDoubleRatioOfGpsPointCar >= 8 && aDoubleRatioOfGpsPointCar < 80) {
                    canvas.drawLine((550 + (aDoubleRatioOfGpsPointCar1 - 8) * 2.82f), 250, (550 + (aDoubleRatioOfGpsPointCar - 8) * 2.82f), 250, mPaint);
                } else if (aDoubleRatioOfGpsPointCar1 < 8 && aDoubleRatioOfGpsPointCar < 80 && aDoubleRatioOfGpsPointCar >= 8) {
                    canvas.drawLine((550 + (8 - 8) * 2.82f), 250, (550 + (aDoubleRatioOfGpsPointCar - 8) * 2.82f), 250, mPaint);
                } else if (aDoubleRatioOfGpsPointCar1 < 8 && aDoubleRatioOfGpsPointCar < 8) {
                    canvas.drawLine((550 + (8 - 8) * 2.82f), 250, (550 + (8 - 8) * 2.82f), 250, mPaint);
                } else if (aDoubleRatioOfGpsPointCar1 >= 8 && aDoubleRatioOfGpsPointCar < 8) {
                    canvas.drawLine((550 + (aDoubleRatioOfGpsPointCar1 - 8) * 2.82f), 250, (550 + (8 - 8) * 2.82f), 250, mPaint);
                }
            }
        }
    }
}
