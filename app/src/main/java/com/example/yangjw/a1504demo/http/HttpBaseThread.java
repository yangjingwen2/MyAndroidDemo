package com.example.yangjw.a1504demo.http;

/**
 * Created by yangjw on 2016/2/29.
 */
public abstract class HttpBaseThread implements Runnable {

    protected class RESULT {
        public static final int SUCCESS = 0x01;
        public static final int FAILURE = 0x02;
        public static final int ERROR = 0x03;
    }

}
