package com.example.yangjw.a1504demo.http;

import android.os.Handler;
import android.os.Message;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * POST请求的Thread
 * Created by yangjw on 2016/2/28.
 */
public class HttpPostThread implements Runnable {

    private String url;
    private RequestCallBack callBack;
    private Map<String, Object> params;
    private int requestCode;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case HttpBaseThread.RESULT.SUCCESS:
                    callBack.onSuccess(msg.obj.toString(), requestCode);
                    break;
                case HttpBaseThread.RESULT.ERROR:
                    callBack.onError((IOException) msg.obj);
                    break;
                case HttpBaseThread.RESULT.FAILURE:
                    callBack.onFailure(msg.obj.toString());
                    break;
            }
        }
    };

    public HttpPostThread(String url, Map<String, Object> params, RequestCallBack callBack, int requestCode) {
        this.url = url;
        this.callBack = callBack;
        this.params = params;
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
            httpURLConnection.setDoOutput(true);

            //设置POST请求的入参
            String requestParams = formatParams(params);
            httpURLConnection.getOutputStream().write(requestParams.getBytes());

            httpURLConnection.connect();

            //获取服务器返回的状态码
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
                msg.what = HttpBaseThread.RESULT.SUCCESS;
                msg.obj = stringBuffer;

            } else {
                //请求失败，根据状态码，需要处理400+和500+的状态码
                //400+表示客户端的请求数据或者请求格式有问题
                //500+表示服务器处理出错。
                //此处需要注意的是，对于开发者，如果服务器返回了500+的状态码，应该让后台服务器开发者联调。
                msg.what = HttpBaseThread.RESULT.FAILURE;
                msg.obj = "error:" + responseCode;
            }
        } catch (IOException e) {
            //            e.printStackTrace();
            msg.what = HttpBaseThread.RESULT.ERROR;
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
    }

    /**
     * 格式化参数
     *
     * @param params
     * @return
     */
    private String formatParams(Map<String, Object> params) {
        StringBuffer stringBuffer = new StringBuffer();
//        Set<String> keySet = params.keySet();
//        Iterator<String> iterator = keySet.iterator();
        Set entrySet = params.entrySet();
        Iterator iterator = entrySet.iterator();
        while (iterator.hasNext()) {
            Object key = iterator.next();
//            Object value = params.get(key);
//            stringBuffer.append(key).append("=").append(value).append("&");
            stringBuffer.append(key).append("&");
        }
        int lastIndex = stringBuffer.lastIndexOf("&");
        return stringBuffer.substring(0, lastIndex);
    }


}
