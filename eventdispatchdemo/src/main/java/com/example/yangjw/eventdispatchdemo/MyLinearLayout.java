package com.example.yangjw.eventdispatchdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by yangjw on 2016/3/3.
 */
public class MyLinearLayout extends LinearLayout {
    /**
     * 事件拦截
     *
     * @param ev
     * @return
     */
    float distance;
    private float startX, startY;
    private boolean isInter = true;

    public MyLinearLayout(Context context) {
        super(context);
    }

    public MyLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyLinearLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        Log.d("ViewGroup", "-->onInterceptTouchEvent--" + ev.getAction());

//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_MOVE:
//                float curX = ev.getRawX();
//                float curY = ev.getRawY();
//                distance = curY-startY;
//
//                Log.d("demo","Linear---ACTION_MOVE");
//                break;
//            case MotionEvent.ACTION_DOWN:
//                startX = ev.getRawX();
//                startY = ev.getRawY();
//                break;
//            case MotionEvent.ACTION_UP:
//                break;
//        }
////        if (distance > 0) {
////            return true;
////        }

        Log.d("demo", "------->" + isInter);
//        if (isInter) {
        return super.onInterceptTouchEvent(ev);
//        }
//        return true;

    }

    /**
     * 事件分发
     *
     * @param ev
     * @return
     */
    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        Log.d("ViewGroup", "-->dispatchTouchEvent--" + ev.getAction());
        return super.dispatchTouchEvent(ev);
    }

    /**
     * 事件消费
     *
     * @param event
     * @return
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Log.d("ViewGroup", "-->onTouchEvent--" + event.getAction());
        switch (event.getAction()) {
            case MotionEvent.ACTION_MOVE:
                float curX = event.getRawX();
                float curY = event.getRawY();
                float distance = curY - startY;
                setTop((int) distance);
                break;
            case MotionEvent.ACTION_DOWN:
                startX = event.getRawX();
                startY = event.getRawY();
                break;
            case MotionEvent.ACTION_UP:
                setTop(0);
                break;
        }
//        this.setTop((int)event.getRawY());
        return true;
    }

//    @Override
//    public void requestDisallowInterceptTouchEvent(boolean disallowIntercept) {
////        super.requestDisallowInterceptTouchEvent(disallowIntercept);
//        isInter = disallowIntercept;
//        Log.d("demo","--ss----->" + isInter);
//    }
}
