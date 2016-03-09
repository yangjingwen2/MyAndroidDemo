package com.example.yangjw.imageloaderlibrary;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yangjw on 2016/3/7.
 */
public class ImageLoader {

    static Handler mHandler = new Handler();
    private static ExecutorService mExecutorService;

    static {
        mExecutorService = Executors.newFixedThreadPool(2);
    }

    public static void loadImage(Context context, String url, ImageView imageView) {

        DiskCacheTool.init(context);
        imageView.setTag(url);
        imageView.setImageBitmap(null);
        mExecutorService.execute(new ImageThread(url, imageView));
    }

    static class ImageThread implements Runnable {

        private String url;
        private ImageView imageView;

        public ImageThread(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        public void run() {
            InputStream inputStream = null;
            try {
                URL urlConn = new URL(url);
                final Bitmap bitmapCache = DiskCacheTool.readImageCache(url);
                if (bitmapCache != null) {
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (imageView.getTag().equals(url)) {
                                imageView.setImageBitmap(bitmapCache);
                            }
                        }
                    });

                } else {
                    HttpURLConnection urlConnection = (HttpURLConnection) urlConn.openConnection();
                    urlConnection.connect();
                    inputStream = urlConnection.getInputStream();
                    ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                    int len = 0;
                    byte[] buffer = new byte[1024];
                    while ((len = inputStream.read(buffer)) != -1) {
                        byteArrayOutputStream.write(buffer, 0, len);
//                    byteArrayOutputStream.flush();
                    }
                    inputStream.close();

                    BitmapFactory.Options options = new BitmapFactory.Options();
                    options.inJustDecodeBounds = true;
                    byte[] imageByte = byteArrayOutputStream.toByteArray();
                    Log.d("demo", "-->imageByte=" + imageByte.length);
                    BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length, options);

                    int imageWidth = options.outWidth;
                    int imageHeight = options.outHeight;
                    Log.d("demo", "-->imageWidth=" + imageWidth);
                    Log.d("demo", "-->imageWidth=" + imageWidth);
                    int ratio = (imageWidth * imageHeight) / (440 * 440);
                    Log.d("demo", "-->ratio=" + ratio);
                    if (ratio < 1) {
                        ratio = 1;
                    }

                    options.inSampleSize = ratio;


                    options.inPreferredConfig = Bitmap.Config.ARGB_4444;
                    options.inJustDecodeBounds = false;

                    final Bitmap bitmap = BitmapFactory.decodeByteArray(imageByte, 0, imageByte.length, options);

                    Log.d("demo", "-->bitmap=" + bitmap.getByteCount());
                    if (bitmap != null) {

                        DiskCacheTool.writeImageCache(url, bitmap);
                    }
                    mHandler.post(new Runnable() {
                        @Override
                        public void run() {
                            if (imageView.getTag().equals(url)) {
                                imageView.setImageBitmap(bitmap);
                            }
                        }
                    });
                }
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (inputStream != null) {
                    try {
                        inputStream.close();

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    inputStream = null;
                }
            }

        }
    }
}
