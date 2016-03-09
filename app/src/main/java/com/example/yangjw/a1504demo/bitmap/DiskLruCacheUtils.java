package com.example.yangjw.a1504demo.bitmap;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;

import com.example.yangjw.a1504demo.application.MyApplication;
import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by yangjw on 2016/3/6.
 */
public class DiskLruCacheUtils {

    private static DiskLruCache mDiskLruCache;
    private static Context mContext;

    static {
        /**
         * Opens the cache in {@code directory}, creating a cache if none exists
         * there.
         *
         * @param directory a writable directory
         * @param versionCode
         * @param valueCount the number of values per cache entry. Must be positive.
         * @param maxSize the maximum number of bytes this cache should use to store
         * @throws IOException if reading or writing the cache directory fails
         */
        File cacheFile = getCacheDirectory();
        mContext = MyApplication.getInstance();

//        int versionCode = null;

        try {
            PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            int versionCode = packageInfo.versionCode;
            mDiskLruCache = DiskLruCache.open(cacheFile, versionCode, 1, 10 * 1024 * 1024);
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


    }

    private static File getCacheDirectory() {
        if (Environment.MEDIA_MOUNTED == Environment.getExternalStorageState()
                || !Environment.isExternalStorageRemovable()) {
            return mContext.getExternalCacheDir();
        }
        return mContext.getCacheDir();
    }

    public static void writeDiskCache(String key, Bitmap bitmap) {

        if (mDiskLruCache != null) {
            String keyCache = formatKey(key);
            try {
                DiskLruCache.Editor editor = mDiskLruCache.edit(keyCache);
                OutputStream outputStream = editor.newOutputStream(0);
                boolean isCompressSuccess = bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                if (isCompressSuccess) {
                    editor.commit();
                } else {
                    editor.abort();
                }

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static Bitmap readDiskCache(String key) {

        if (mDiskLruCache != null) {
            try {
                DiskLruCache.Snapshot snapshot = mDiskLruCache.get(formatKey(key));
                if (snapshot != null) {
                    InputStream inputStream = snapshot.getInputStream(0);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    return bitmap;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

        return null;
    }

    public static void flush() {
        if (mDiskLruCache != null) {
            try {
                mDiskLruCache.flush();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public static void close() {
        if (mDiskLruCache != null) {
            try {
                mDiskLruCache.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }


    private static String formatKey(String key) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(key.getBytes());
            byte[] bytes = messageDigest.digest();
            StringBuffer stringBuffer = new StringBuffer();
            for (int i = 0; i < bytes.length; i++) {

                stringBuffer.append(Integer.toHexString(0xff * bytes[0]));
            }

            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return key.hashCode() + "";

    }
}
