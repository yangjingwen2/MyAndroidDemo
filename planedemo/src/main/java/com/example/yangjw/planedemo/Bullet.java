package com.example.yangjw.planedemo;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;

/**
 * Created by yangjw on 2016/3/1.
 */
public class Bullet {

    public float x;
    public float y;

    private Canvas mCanvas;
    private Paint mPaint;
    private Bitmap bulletBitmap;


    public Bullet(Context context, float x, float y, Canvas mCanvas, Paint mPaint) {
        this.x = x;
        this.y = y;
        this.mCanvas = mCanvas;
        this.mPaint = mPaint;
        bulletBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.bullet);
        mCanvas.drawBitmap(bulletBitmap, x, y, mPaint);
    }

    public void move() {
        y = y - 10;
        mCanvas.drawBitmap(bulletBitmap, x, y, mPaint);
    }
}
