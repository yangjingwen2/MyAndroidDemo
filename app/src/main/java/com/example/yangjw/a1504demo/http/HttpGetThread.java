package com.example.yangjw.a1504demo.http;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * GET请求的Thread
 * Created by yangjw on 2016/2/28.
 */
public class HttpGetThread extends HttpBaseThread {

    private String url;
    private RequestCallBack callBack;
    private int requestCode;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RESULT.SUCCESS:
                    callBack.onSuccess(msg.obj.toString(), requestCode);
                    break;
                case RESULT.ERROR:
                    callBack.onError((IOException) msg.obj);
                    break;
                case RESULT.FAILURE:
                    callBack.onFailure(msg.obj.toString());
                    break;
            }
        }
    };

    public HttpGetThread(String url, RequestCallBack callBack, int requestCode) {
        this.url = url;
        this.callBack = callBack;
        this.requestCode = requestCode;
    }

    @Override
    public void run() {
        Message msg = mHandler.obtainMessage();
        HttpURLConnection httpURLConnection = null;
        InputStream inStream = null;
        try {
            URL urlNet = new URL(url);
            httpURLConnection = (HttpURLConnection) urlNet.openConnection();
//            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                //请求成功
                inStream = httpURLConnection.getInputStream();
                StringBuffer stringBuffer = new StringBuffer();
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = inStream.read(buffer)) != -1) {

                    stringBuffer.append(new String(buffer, 0, len));
                }

                msg.what = RESULT.SUCCESS;
                msg.obj = stringBuffer;

            } else {
                //请求失败，根据状态码，需要处理400+和500+的状态码
                //400+表示客户端的请求数据或者请求格式有问题
                //500+表示服务器处理出错。
                //此处需要注意的是，对于开发者，如果服务器返回了500+的状态码，应该让后台服务器开发者联调。

                msg.what = RESULT.FAILURE;
                msg.obj = "error:" + responseCode;
            }
        } catch (IOException e) {
            //            e.printStackTrace();
            msg.what = RESULT.ERROR;
            msg.obj = e;
        } finally {
            try {
                if (inStream != null) {
                    inStream.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (httpURLConnection != null) {
                httpURLConnection.disconnect();
            }
        }

        mHandler.sendMessage(msg);
    }


}
