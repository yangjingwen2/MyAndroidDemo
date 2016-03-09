package com.example.yangjw.eventdispatchdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * Created by yangjw on 2016/3/3.
 */
public class MyView extends View {

    public MyView(Context context) {
        super(context);
    }

    public MyView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * 事件分发
     *
     * @param event
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        Log.d("View", "===dispatchTouchEvent");
        return super.dispatchTouchEvent(event);
    }

    /**
     * 事件消费
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("View", "===onTouchEvent");
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                Log.d("View", "===ACTION_MOVE");
//                return false;
                break;
            case MotionEvent.ACTION_DOWN:
                Log.d("View", "===ACTION_DOWN");
                break;
            case MotionEvent.ACTION_UP:
                Log.d("View", "===ACTION_UP");
                break;
        }
        return super.onTouchEvent(event);
    }
}
