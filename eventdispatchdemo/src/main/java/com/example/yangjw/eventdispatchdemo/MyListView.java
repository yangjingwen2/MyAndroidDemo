package com.example.yangjw.eventdispatchdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ListView;

/**
 * Created by yangjw on 2016/3/3.
 */
public class MyListView extends ListView {

    private float startX;
    private float startY;
    private int childIndex;
    private View childView;
    private boolean isChange;

    public MyListView(Context context) {
        super(context);
    }

    public MyListView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public MyListView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (getFirstVisiblePosition() == 0) {
            getParent().requestDisallowInterceptTouchEvent(true);
        } else {
            getParent().requestDisallowInterceptTouchEvent(true);
        }

//        switch (ev.getAction()) {
//            case MotionEvent.ACTION_MOVE:
//
//                float moveX = ev.getX();
//                float moveY = ev.getY();
//
//                float xDistance = Math.abs(moveX - startX);
//                float yDistance = Math.abs(moveX - startY);
//                if (xDistance>yDistance && xDistance >= 100) {
//                    childIndex = pointToPosition((int) startX, (int) startY);
//                    childView = getChildAt(childIndex);
//                    TextView textView = (TextView) childView;
//                    textView.setText("dddddd");
//
//                    isChange = true;
//                }
//
//
//
//                Log.d("listview","----ACTION_MOVE");
//                break;
//            case MotionEvent.ACTION_DOWN:
//                startX = ev.getX();
//                startY = ev.getY();
//
//                Log.d("listview","----ACTION_DOWN");
//                break;
//            case MotionEvent.ACTION_UP:
//                Log.d("listview","----ACTION_UP");
//                break;
//        }
//        if (!isChange) {
//
//            return super.onTouchEvent(ev);
//        }

        return super.onTouchEvent(ev);
    }
}
