package com.example.yangjw.a1504demo.http;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

import com.example.yangjw.a1504demo.image.ImageUtils;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by yangjw on 2016/2/28.
 */
public class HttpImageThread extends HttpBaseThread {

    private String url;
    private ImageView imageView;
    private DiskLruCache mDiskLruCache;
    private LruCache<String, Bitmap> mLruCache;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case RESULT.SUCCESS:
                    imageView.setImageBitmap((Bitmap) msg.obj);
                    break;
                default:
                    //展示加载失败的图片
                    break;
            }
        }
    };

    public HttpImageThread(String url, ImageView imageView, DiskLruCache mDiskLruCache, LruCache<String, Bitmap> lruCache) {
        this.url = url;
        this.imageView = imageView;
        this.mDiskLruCache = mDiskLruCache;
        this.mLruCache = lruCache;
        imageView.setTag(url);
    }

    @Override
    public void run() {
        Message msg = mHandler.obtainMessage();
        InputStream inStream = null;
        HttpURLConnection httpURLConnection = null;
        DiskLruCache.Editor editor = null;
        String key = ImageUtils.getMD5Key(url);
        try {
            URL urlNet = new URL(url);
            httpURLConnection = (HttpURLConnection) urlNet.openConnection();
//            httpURLConnection.setDoOutput(true);
            httpURLConnection.connect();
            int responseCode = httpURLConnection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                //请求成功
                inStream = httpURLConnection.getInputStream();
//                DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);

//                ByteArrayOutputStream arrayOutputStream = new ByteArrayOutputStream();
                editor = mDiskLruCache.edit(key);

                if (editor != null) {
                    OutputStream outputStream = editor.newOutputStream(0);
                    byte[] buffer = new byte[1024];
                    int len = 0;
                    while ((len = inStream.read(buffer)) != -1) {
                        outputStream.write(buffer, 0, len);
                        outputStream.flush();
//                    arrayOutputStream.write(buffer,0,len);
//                    arrayOutputStream.flush();
                    }

                    editor.commit();
                    outputStream.close();
                }

//                byte[] arrayByte = arrayOutputStream.toByteArray();
//                Bitmap bitmap = BitmapFactory.decodeByteArray(arrayByte,0,arrayByte.length);


                inStream.close();
                InputStream is = mDiskLruCache.get(key).getInputStream(0);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                int zoomInt = 2;
//                while (options.outWidth*options.outHeight > 200) {
//                    zoomInt++;
//                }
                int ratio = options.outWidth * options.outHeight / 200;
                ratio = ratio <= 1 ? 1 : ratio;
                options.inSampleSize = 3;
                options.inJustDecodeBounds = false;
                Bitmap bitmap = BitmapFactory.decodeStream(is, new Rect(5, 5, 5, 5), options);
                mLruCache.put(key, bitmap);
                msg.what = RESULT.SUCCESS;
                msg.obj = bitmap;
                is.close();

            }
        } catch (IOException e) {
            e.printStackTrace();
            try {
                editor.abort();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
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

//    private String getMD5Key(String url) {
//        try {
//            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
//            messageDigest.update(url.getBytes());
//            return new String(messageDigest.digest());
//        } catch (NoSuchAlgorithmException e) {
//            e.printStackTrace();
//            return String.valueOf(url.hashCode());
//        }
//    }
}
