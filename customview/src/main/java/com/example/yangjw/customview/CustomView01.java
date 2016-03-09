package com.example.yangjw.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Handler;
import android.os.Message;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import java.util.Calendar;

public class CustomView01 extends View {

    private Paint mPaint;
    private int width;
    private int height;
    private int secondsDefrees = 6;
    private int hours = 3;
    private int munites = 35;
    private int second = 40;

    private boolean isClick;

    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            Calendar calendar = Calendar.getInstance();
            if (!isClick) {
                hours = calendar.get(Calendar.HOUR);
                munites = calendar.get(Calendar.MINUTE);
                second = calendar.get(Calendar.SECOND);
                invalidate();
                sendEmptyMessageDelayed(1, 1000);
            } else {
                if (hours <= 12) {
                    hours++;
                }
                if (munites <= 60) {
                    munites++;
                }
                if (hours >= 12 && munites >= 60) {
                    isClick = false;
                }
                invalidate();
                sendEmptyMessageDelayed(1, 500);
            }
        }

    };

    public CustomView01(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
        mPaint = new Paint();
        mHandler.sendEmptyMessageDelayed(1, 2000);
    }

    public CustomView01(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        // TODO Auto-generated constructor stub
        mPaint = new Paint();
        mHandler.sendEmptyMessageDelayed(1, 2000);
    }

    public CustomView01(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
        mPaint = new Paint();
        mHandler.sendEmptyMessageDelayed(1, 2000);
    }

    /**
     * �ؼ�����Ҫ��ˮƽ�ռ�ʹ�ֱ�ռ�
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        Log.d("demo", "--->" + widthMeasureSpec);
        Log.d("demo", "--->" + heightMeasureSpec);
        Log.d("demo", "---size-->" + MeasureSpec.getSize(widthMeasureSpec));
        Log.d("demo", "---mode-->" + MeasureSpec.getMode(widthMeasureSpec));
//		Log.d("demo", "---AT_MOST-->"+MeasureSpec.AT_MOST);
//		Log.d("demo", "---EXACTLY-->"+MeasureSpec.EXACTLY);
//		Log.d("demo", "---UNSPECIFIED-->"+MeasureSpec.UNSPECIFIED);
//		Log.d("demo", "---MAX-->"+Integer.MAX_VALUE);
//		Log.d("demo", "---MAX>>2-->"+(Integer.MAX_VALUE>>2));
        // TODO Auto-generated method stub
//		super.onMeasure(widthMeasureSpec, heightMeasureSpec);s
//		int defaultSize = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
//		Log.d("demo", "---defaultSize-->"+defaultSize);
//		Log.d("demo", "---defaultSize1-->"+MeasureSpec.getSize(defaultSize));
//		int spec=MeasureSpec.makeMeasureSpec(200, MeasureSpec.EXACTLY);
//		Log.d("demo", "---spec-->"+spec);
//		setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//		int measuredWidth = MeasureSpec.makeMeasureSpec(200, MeasureSpec.AT_MOST);
//		int measuredHeight = MeasureSpec.makeMeasureSpec(300, MeasureSpec.AT_MOST);
//		setMeasuredDimension(measuredWidth, measuredHeight);
        width = getDefaultSize(getSuggestedMinimumWidth(), widthMeasureSpec);
        height = getDefaultSize(getSuggestedMinimumHeight(), heightMeasureSpec);

    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right,
                            int bottom) {
        // TODO Auto-generated method stub
        super.onLayout(changed, left, top, right, bottom);
        Log.d("demo", "---changed-->" + changed);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        // TODO Auto-generated method stub
//		super.onDraw(canvas);
        mPaint.setColor(Color.BLACK);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(10);
        mPaint.setStyle(Paint.Style.STROKE);
        //		mPaint.setTextSize(14);
        //		canvas.rotate(30);
        //		canvas.drawText("abcdddddd", 0, 15, mPaint);
        //		canvas.restore();
        canvas.drawCircle(width / 2, height / 2, width / 2 - 40, mPaint);
        mPaint.setStrokeWidth(5);
        canvas.drawCircle(width / 2, height / 2, width / 2 - 50, mPaint);

        mPaint.setColor(Color.GRAY);
        for (int i = 0; i < 12; i++) {
            canvas.save();
            canvas.rotate(360 / 12 * i, width / 2, height / 2);
            canvas.drawLine(60, height / 2, 70, height / 2, mPaint);
            canvas.restore();
        }


        //ʱ��
        canvas.save();
        canvas.rotate(360 / 12 * hours + munites * 0.5F, width / 2, height / 2);
        canvas.drawLine(width / 2, height / 2, width / 2, height / 2 - 50, mPaint);
        canvas.restore();
        mPaint.setStrokeWidth(3);

        //����
        canvas.save();
        canvas.rotate(munites * 6, width / 2, height / 2);
        canvas.drawLine(width / 2, height / 2, width / 2, height / 2 - 70, mPaint);
        canvas.restore();
        //����
        mPaint.setStrokeWidth(1);
        canvas.save();
        canvas.rotate(second * 6, width / 2, height / 2);
        canvas.drawLine(width / 2, height / 2, width / 2, height / 2 - 100, mPaint);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // TODO Auto-generated method stub
        switch (event.getAction()) {
            case MotionEvent.ACTION_UP:
                Log.d("demo", "--->UP");
                isClick = true;
                hours = 0;
                munites = 0;
                break;
        }
        return true;
    }

}
