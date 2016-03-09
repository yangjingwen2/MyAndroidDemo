package com.example.yangjw.a1504demo.application;

import android.app.Application;

import com.example.yangjw.imageloaderlibrary.DiskCacheTool;

/**
 * Created by yangjw on 2016/2/28.
 */
public class MyApplication extends Application {

    private static MyApplication mMyApplication;

    public static MyApplication getInstance() {
        return mMyApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mMyApplication = this;
        DiskCacheTool.init(this);
    }
}
