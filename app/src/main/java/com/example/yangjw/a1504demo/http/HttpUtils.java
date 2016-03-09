package com.example.yangjw.a1504demo.http;

import android.support.annotation.Nullable;

import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


/**
 * 网络请求帮助类
 * Created by yangjw on 2016/2/27.
 */
public class HttpUtils {

    private static ExecutorService mExecutorService;

    static {
        //创建一个定长的线程池
        if (mExecutorService == null) {
            mExecutorService = Executors.newFixedThreadPool(4);
        }
    }

    /**
     * Http的GET请求
     *
     * @param url         请求链接
     * @param callBack
     * @param requestCode
     */
    public static void get(String url, RequestCallBack callBack, @Nullable int requestCode) {
        mExecutorService.execute(new HttpGetThread(url, callBack, requestCode));
    }

    /**
     * Http的POST请求方式
     *
     * @param url         请求链接
     * @param params      参数
     * @param callBack
     * @param requestCode
     */
    public static void post(String url, Map<String, Object> params, RequestCallBack callBack, @Nullable int requestCode) {
        mExecutorService.execute(new HttpPostThread(url, params, callBack, requestCode));
    }


}
