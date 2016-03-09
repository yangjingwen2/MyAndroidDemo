package com.example.yangjw.eventdispatchdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

/**
 * Created by yangjw on 2016/3/3.
 */
public class MyScroll extends HorizontalScrollView {
    public MyScroll(Context context) {
        super(context);
    }

    public MyScroll(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyScroll(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
//        super.onTouchEvent(ev);
        return false;
    }
}
