package com.kylindev.totalk.qgs.park.fourteen;

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
 * @date 2021/6/2 16:33
 * 12道停留车
 */
public class FourteenParkCar extends View {

    private Paint mPaint;

    public FourteenParkCar(Context context) {
        this(context, null);
    }

    public FourteenParkCar(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public FourteenParkCar(Context context, AttributeSet attrs, int defStyleAttr) {
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

        FourteenParkDataDao fourteenParkDataDao = new FourteenParkDataDao(getContext());
        List<DataUser> dataUsers = fourteenParkDataDao.find();
        int size = dataUsers.size();
        if (size > 1 && size % 2 != 0) {
            for (int i = 1; i < size; i += 2) {
                String ratioOfGpsPointCar = dataUsers.get(i).getRatioOfGpsPointCar();
                Float aDoubleRatioOfGpsPointCar = Float.valueOf(ratioOfGpsPointCar);
                String ratioOfGpsPointCar1 = dataUsers.get(i + 1).getRatioOfGpsPointCar();
                Float aDoubleRatioOfGpsPointCar1 = Float.valueOf(ratioOfGpsPointCar1);
                //(350 - (46 - mGpsPistanceCar) * 6.52f)
                if (aDoubleRatioOfGpsPointCar < 47 && aDoubleRatioOfGpsPointCar1 < 47) {
                    canvas.drawLine((350 - (46 - aDoubleRatioOfGpsPointCar) * 6.52f), 500, (350 - (46 - aDoubleRatioOfGpsPointCar1) * 6.52f), 500, mPaint);
                } else if (aDoubleRatioOfGpsPointCar > 47 && aDoubleRatioOfGpsPointCar1 < 47) {
                    canvas.drawLine((350 - (46 - 46) * 6.52f), 500, (350 - (46 - aDoubleRatioOfGpsPointCar1) * 6.52f), 500, mPaint);
                } else if (aDoubleRatioOfGpsPointCar < 47 && aDoubleRatioOfGpsPointCar1 > 47) {
                    canvas.drawLine((350 - (46 - aDoubleRatioOfGpsPointCar) * 6.52f), 500, (350 - (46 - 46) * 6.52f), 500, mPaint);
                }
            }
        }
    }
}
