package com.example.yangjw.a1504demo.http;

import java.io.IOException;

/**
 * Created by yangjw on 2016/2/28.
 */
public interface RequestCallBack {

    /**
     * 请求成功的回调接口
     *
     * @param result      服务器返回的结果
     * @param requestCode 请求码
     */
    void onSuccess(String result, int requestCode);

    /**
     * 请求失败的回调接口，比如服务器返回的400+或者500+错误
     *
     * @param exception
     */
    void onFailure(String exception);

    /**
     * 请求异常的回调接口，比如发生网络异常
     *
     * @param ex
     */
    void onError(IOException ex);
}
