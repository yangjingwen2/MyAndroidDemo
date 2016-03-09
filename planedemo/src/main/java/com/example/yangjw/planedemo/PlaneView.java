package com.example.yangjw.planedemo;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by yangjw on 2016/3/1.
 */
public class PlaneView extends View {
    private float planeX;
    private float planeY;
    private Paint mPaint;
    private Bitmap mBitmap;
    private Context mContext;

    private float screenWidth;
    private float screenHeight;

    private int mark;
    private List<Bullet> bullets;

    private boolean isMove;

    public PlaneView(Context context) {
        super(context);
        init(context);
    }

    public PlaneView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(context);

    }

    private void init(Context context) {
        mContext = context;
        mPaint = new Paint();
        bullets = new ArrayList<>();
        mBitmap = BitmapFactory.decodeResource(context.getResources(), R.drawable.myplane);
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Point point = new Point();
        windowManager.getDefaultDisplay().getSize(point);
        screenHeight = point.y;
        screenWidth = point.x;

//        planeX = screenWidth/2 - 66;
//        planeY = screenHeight -240;

        Log.d("plane", ">>>planeX=" + planeX);
        Log.d("plane", ">>>planeY=" + planeY);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {


//        mark = (int) planeX;
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                planeX = event.getRawX() - 33;
                planeY = event.getRawY() - 200;

                planeX = planeX < 0 ? 0 : planeX;
                planeY = planeY < 0 ? 0 : planeY;

                planeX = planeX + 66 > screenWidth ? screenWidth - 66 : planeX;
                planeY = planeY + 240 > screenHeight ? screenHeight - 240 : planeY;
                break;
            case MotionEvent.ACTION_DOWN:
                isMove = true;
                break;
            case MotionEvent.ACTION_UP:
                isMove = false;
                break;
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
//        super.onDraw(canvas);
//        Log.d("plane","--->" + planeY);
//        if (isMove) {


        canvas.drawColor(Color.WHITE);
        //绘制飞机
        canvas.drawBitmap(mBitmap, planeX, planeY, mPaint);

        if (mark % 5 == 0) {
            Bullet bullet = new Bullet(mContext, planeX + 33, planeY, canvas, mPaint);
            bullets.add(bullet);
        }

        for (int i = 0; i < bullets.size(); i++) {

            bullets.get(i).move();

            if (bullets.get(i).y <= 0) {
                bullets.remove(i);
            }
        }

        mark++;
        invalidate();

    }
}
