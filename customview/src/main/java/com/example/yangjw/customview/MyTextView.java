package com.example.yangjw.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Created by yangjw on 2016/3/2.
 */
public class MyTextView extends View {

    private Paint mPaint;

    private int color;
    private String text;
    private float size;
    private int textWidth;

    public MyTextView(Context context) {
        super(context);
        Log.d("demo", "--->MyTextView1");
        init();
    }

    public MyTextView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
        Log.d("demo", "--->MyTextView2");
    }

    public MyTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        Log.d("demo", "--->MyTextView3--->count = " + attrs.getAttributeCount());
        Log.d("demo", "--->MyTextView3--->defStyleAttr = " + defStyleAttr);
        TypedArray td = context.obtainStyledAttributes(attrs, R.styleable.custom, defStyleAttr, 0);
        color = td.getColor(R.styleable.custom_customColor, Color.BLACK);
        size = td.getDimension(R.styleable.custom_customSize, 5);
        text = td.getString(R.styleable.custom_customText);
        Log.d("demo", "--->color=" + color);
        Log.d("demo", "--->size=" + size);
        Log.d("demo", "--->text=" + text);
        td.recycle();
        textWidth = (int) ((text.length()) * size);
        init();
    }

    private void init() {
        mPaint = new Paint();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        Log.d("demo", "--->widthMode=" + (widthMode == MeasureSpec.EXACTLY));
        Log.d("demo", "--->heightMode=" + (heightMode == MeasureSpec.AT_MOST));
        Log.d("demo", "--->height=" + MeasureSpec.getSize(heightMeasureSpec));
        super.onMeasure(MeasureSpec.makeMeasureSpec(textWidth, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(((int) size) + 20, MeasureSpec.EXACTLY));

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight(), mPaint);
        mPaint.setColor(color);
        mPaint.setTextSize(size);
        Rect bounds = new Rect();
        mPaint.getTextBounds(text, 0, text.length(), bounds);

        canvas.drawText(text, 0, bounds.height(), mPaint);
    }
}
