package com.example.yangjw.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by yangjw on 2016/3/3.
 */
public class FlowTextLayout extends ViewGroup {
    private int proChildHeight;

    public FlowTextLayout(Context context) {
        super(context);
    }

    public FlowTextLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public FlowTextLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        int childCount = getChildCount();
//        for (int i=0; i<childCount; i++) {
//
//            View childView = getChildAt(i);
//            measureChild(childView,widthMeasureSpec,heightMeasureSpec);
//        }
        measureChildren(widthMeasureSpec, heightMeasureSpec);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {

            View view = getChildAt(i);
            view.layout(proChildHeight * i + 10, proChildHeight * i + 10, proChildHeight * i + 10 + view.getMeasuredWidth(), proChildHeight * i + 10 + view.getMeasuredHeight());
            proChildHeight = view.getMeasuredHeight();
        }
    }
}
