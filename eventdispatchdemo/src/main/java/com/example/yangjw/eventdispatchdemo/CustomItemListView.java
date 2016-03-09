package com.example.yangjw.eventdispatchdemo;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ListView;
import android.widget.TextView;

public class CustomItemListView extends ListView {

    float childDownX;
    float childDownY;
    TextView tv;
    private GestureDetector gestureDetector;
    private float startX, startY;
    private float endX, endY;
    private boolean isHorizontalScroll = false;


    public CustomItemListView(Context context) {
        super(context);
        // TODO Auto-generated constructor stub
//		gestureDetector = new GestureDetector(context, gestureListener);
    }

    public CustomItemListView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        // TODO Auto-generated constructor stub
//		gestureDetector = new GestureDetector(context, gestureListener);
    }


//	private OnGestureListener gestureListener = new GestureDetector.SimpleOnGestureListener() {
//
//		@Override
//		public boolean onScroll(MotionEvent e1, MotionEvent e2,
//				float distanceX, float distanceY) {
//			// TODO Auto-generated method stub
//			if (Math.abs(distanceX) > Math.abs(distanceY)) {
//				Log.i("yjw", "onScroll false  distanceX=" + distanceX + ";  distanceY=" + distanceY);
//				isHorizontalScroll = false;
//			}
//			isHorizontalScroll = true;
//			Log.i("yjw", "onScroll  distanceX=" + distanceX + ";  distanceY=" + distanceY);
//			return isHorizontalScroll;
//		}
//
//		@Override
//		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
//				float velocityY) {
//			// TODO Auto-generated method stub
//			Log.i("yjw", "onFling");
//			return false;
//		}
//
//		@Override
//		public boolean onDown(MotionEvent e) {
//			// TODO Auto-generated method stub
//			Log.i("yjw", "onDown");
//			isHorizontalScroll = true;
//			return isHorizontalScroll;
//		}
//		
//	};


    public CustomItemListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        // TODO Auto-generated constructor stub
//		gestureDetector = new GestureDetector(context, gestureListener);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
        Log.i("yjw", "onInterceptTouchEvent");
        return super.onInterceptTouchEvent(ev);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub
//		super.dispatchTouchEvent(ev);
        Log.i("yjw", "dispatchTouchEvent");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        // TODO Auto-generated method stub

        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                Log.i("yjw", "ACTION_DOWN");

                myTouch(ev);

                startX = ev.getX();
                startY = ev.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                endX = ev.getX();
                endY = ev.getY();
                myTouch(ev);
                if (isHorizontalScroll) {
                    return false;
                }
                Log.i("yjw", "distanceX=" + Math.abs(endX - startX));
                Log.i("yjw", "distanceY=" + Math.abs(endY - startY));
                if (Math.abs(endX - startX) > Math.abs(endY - startY)) {
                    Log.i("yjw", "ACTION_MOVE");
                    isHorizontalScroll = false;
                    Log.i("yjw", "ACTION_MOVE, isHorizontalScroll=" + isHorizontalScroll);
//				return isHorizontalScroll;
                }
                break;
            case MotionEvent.ACTION_UP:
                Log.i("yjw", "ACTION_UP");
//			if (Math.abs(endX - startX) > Math.abs(endY - startY)) {
//				isHorizontalScroll = true;
//			}
                break;
        }

        Log.i("yjw", "end, isHorizontalScroll=" + isHorizontalScroll);
        return super.onTouchEvent(ev);
    }

    private void myTouch(MotionEvent ev) {
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                childDownX = ev.getX();
                childDownY = ev.getY();
                int positon = pointToPosition((int) childDownX, (int) childDownY);
                tv = (TextView) getChildAt(positon);
                break;
            case MotionEvent.ACTION_MOVE:
                Log.i("yjw", "myTouch, ACTION_MOVE");
                float horizontalX = childDownX - ev.getX();
                float horizontalY = childDownY - ev.getY();
                if (horizontalX > 100) {
                    tv.setText("[down]");
                    isHorizontalScroll = true;
                } else if (horizontalX < -100) {
                    tv.setText("123456789");
                    isHorizontalScroll = false;
                }
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
    }

}
