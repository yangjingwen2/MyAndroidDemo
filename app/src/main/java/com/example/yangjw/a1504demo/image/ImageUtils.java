package com.example.yangjw.a1504demo.image;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Rect;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.widget.ImageView;

import com.example.yangjw.a1504demo.application.MyApplication;
import com.example.yangjw.a1504demo.http.HttpImageThread;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Created by yangjw on 2016/2/28.
 */
public class ImageUtils {

    private static LruCache<String, Bitmap> mLruCache;
    private static ImageUtils mImageUtils;
    private static DiskLruCache mDiskLruCache;
    private static ExecutorService mExecutorService;

    static {
        mExecutorService = Executors.newFixedThreadPool(3);
    }

//    private static ImageUtils getInstance(Context context) {
//
//        if (mImageUtils == null) {
//            mImageUtils = new ImageUtils();
//        }
//
//
//
//        return mImageUtils;
//    }


    /**
     * 加载图片
     *
     * @param imageView
     * @param url
     */
    public static void loadImage(ImageView imageView, String url) {
        mExecutorService.execute(new ImageFindTask(url, imageView));
        //1、从内存中获取数据
//        Bitmap bitmap = getImageFromMemory(url);
//        if (bitmap != null) {
//            Log.d(ImageUtils.class.toString(),"---->memory");
//            imageView.setImageBitmap(bitmap);
//        } else {
//            bitmap = getImageFromDisk(url);
//            if (bitmap != null) {
//                Log.d(ImageUtils.class.toString(),"---->disk");
//                imageView.setImageBitmap(bitmap);
//            } else {
//                Log.d(ImageUtils.class.toString(),"---->net");
//                requestImageFromNetwork(url,imageView);
//            }
//        }

    }

    /**
     * 从内存中获取缓存数据
     *
     * @param key
     * @return
     */
    private static Bitmap getImageFromMemory(String key) {

        if (mLruCache == null) {
            int cacheMemory = (int) (Runtime.getRuntime().maxMemory() / 8);
//            int cacheMemory = 4 * 1024* 1024;
            mLruCache = new LruCache<String, Bitmap>(cacheMemory) {
                @Override
                protected int sizeOf(String key, Bitmap value) {
//                    return value.getRowBytes() * value.getHeight();
                    Log.d("demo", "--->" + value.getByteCount());
                    return value.getByteCount() / 1024;
                }

            };
        }


        return mLruCache.get(getMD5Key(key));
    }

    /**
     * 从磁盘上获取数据
     *
     * @param url
     * @return
     */
    private static Bitmap getImageFromDisk(String url) {
        if (mDiskLruCache == null) {
            File cacheDir = getCacheDir();
            int versionCode = getAppVersion();
            try {
                mDiskLruCache = DiskLruCache.open(cacheDir, versionCode, 1, 10 * 1024 * 1024);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            String key = getMD5Key(url);
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(key);
            if (snapshot != null) {
                InputStream is = snapshot.getInputStream(0);
                BitmapFactory.Options options = new BitmapFactory.Options();
                options.inJustDecodeBounds = true;
                int ratio = options.outWidth * options.outHeight / 200;
                ratio = ratio <= 1 ? 1 : ratio;
                options.inSampleSize = 3;
                options.inJustDecodeBounds = false;
                Bitmap bitmap = BitmapFactory.decodeStream(is, new Rect(5, 5, 5, 5), options);
                is.close();
                mLruCache.put(key, bitmap);

                return bitmap;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 从网络上获取图片
     */
    private static void requestImageFromNetwork(String url, ImageView imageView) {
        //TODO
        mExecutorService.execute(new HttpImageThread(url, imageView, mDiskLruCache, mLruCache));
    }

    private static File getCacheDir() {
        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
                && Environment.getExternalStorageState() != Environment.MEDIA_REMOVED) {
            return MyApplication.getInstance().getExternalCacheDir();
        } else {
            return MyApplication.getInstance().getCacheDir();
        }
    }

    /**
     * 获取系统版本号
     *
     * @return
     */
    private static int getAppVersion() {
        try {
            PackageInfo packageInfo = MyApplication.getInstance().getPackageManager()
                    .getPackageInfo(MyApplication.getInstance().getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return 0;
    }

    /**
     * 在activity的onPause方法中，需要更新日志，调用此方法
     */
    public static void flushCache() {
        try {
            mDiskLruCache.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void closeCache() {
        try {
            mDiskLruCache.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String getMD5Key(String url) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(url.getBytes());
            return bytesToHexString(messageDigest.digest());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return String.valueOf(url.hashCode());
        }
    }

    private static String bytesToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bytes.length; i++) {
            String hex = Integer.toHexString(0xFF & bytes[i]);
            if (hex.length() == 1) {
                sb.append('0');
            }
            sb.append(hex);
        }
        return sb.toString();
    }

    static class ImageFindTask implements Runnable {

        private String url;

        private ImageView imageView;
        Handler handler = new Handler() {
            @Override
            public void handleMessage(Message msg) {
                imageView.setImageBitmap((Bitmap) msg.obj);
            }
        };

        public ImageFindTask(String url, ImageView imageView) {
            this.url = url;
            this.imageView = imageView;
        }

        @Override
        public void run() {
            Message msg = handler.obtainMessage();
            Bitmap bitmap = getImageFromMemory(url);
            if (bitmap != null) {
                Log.d(ImageUtils.class.toString(), "---->memory");
//                imageView.setImageBitmap(bitmap);
                msg.obj = bitmap;
                handler.sendMessage(msg);
            } else {
                bitmap = getImageFromDisk(url);
                if (bitmap != null) {
                    Log.d(ImageUtils.class.toString(), "---->disk");
//                    imageView.setImageBitmap(bitmap);
                    msg.obj = bitmap;
                    handler.sendMessage(msg);
                } else {
                    Log.d(ImageUtils.class.toString(), "---->net");
                    requestImageFromNetwork(url, imageView);
                }
            }
        }
    }

}
