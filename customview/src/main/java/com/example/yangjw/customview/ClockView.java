package com.example.yangjw.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.view.View;

import java.util.Calendar;

/**
 * Created by yangjw on 2016/3/2.
 */
public class ClockView extends View {

    private Paint mPaint;

    private int second = 0;

    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            Calendar calendar = Calendar.getInstance();
            second = calendar.get(Calendar.SECOND);
            mHandler.sendEmptyMessageDelayed(1, 1000);
            invalidate();
        }
    };

    public ClockView(Context context) {
        super(context);
        init();
    }

    public ClockView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ClockView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        //初始化画笔样式
        mPaint = new Paint();
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);

    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPaint.reset();
        mPaint.setColor(Color.GRAY);
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);

        //画表盘
        canvas.drawCircle(300, 400, 200, mPaint);

        //12个刻度
        for (int i = 0; i < 12; i++) {

            canvas.save();
            canvas.rotate((360 / 12) * i, 300, 400);
            canvas.drawLine(300, 200, 300, 220, mPaint);
            canvas.restore();
        }

        //时钟
        canvas.save();
        canvas.drawLine(300, 300, 300, 400, mPaint);
        canvas.restore();

        //分钟
        canvas.save();
        mPaint.setColor(Color.GREEN);
        canvas.drawLine(180, 400, 300, 400, mPaint);
        canvas.restore();


        //秒钟
        canvas.save();
        mPaint.setColor(Color.RED);
        canvas.rotate(6 * second, 300, 400);
        canvas.drawLine(300, 400, 450, 400, mPaint);
        canvas.restore();

        mHandler.sendEmptyMessageDelayed(1, 1000);

    }


}
