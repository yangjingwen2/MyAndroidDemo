package com.example.yangjw.imageloaderlibrary;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.util.Log;

import com.jakewharton.disklrucache.DiskLruCache;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * Created by yangjw on 2016/3/7.
 */
public class DiskCacheTool {

    private static Context mContext;
    private static DiskLruCache mDiskLruCache;

    static {


    }

    public static void init(Context context) {
        mContext = context;
        try {
            File file = getCacheDirectory();
            Log.d("demo", "--file->" + file.getAbsolutePath());
            mDiskLruCache = DiskLruCache.open(file, getVersionCode(), 1, 4 * 1024 * 1024);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static int getVersionCode() {
        if (mContext == null) {
            throw new NullPointerException("context == null , init method is not called");
        }

        try {
            PackageInfo packageInfo = mContext.getPackageManager().getPackageInfo(mContext.getPackageName(), 0);
            return packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return 0;
    }

    /**
     * 1、获取缓存的目录
     * 2、初始化DiskLruCache
     * 3、写入缓存
     * 4、读取缓存
     * 5、更新日志文件
     * 6、关闭缓存
     */

    /*
    获取缓存目录
     */
    private static File getCacheDirectory() {

        if (Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED
                && !Environment.isExternalStorageRemovable()) {
            return mContext.getExternalCacheDir();
        } else {
            return mContext.getCacheDir();
        }
    }


    /**
     * 获取格式化后的KEY值
     *
     * @param key
     * @return
     */
    private static String getCacheKey(String key) {
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            messageDigest.update(key.getBytes());
            byte[] bytes = messageDigest.digest();
            StringBuffer stringBuffer = new StringBuffer();

            for (int i = 0; i < bytes.length; i++) {
                stringBuffer.append(Integer.toHexString(Math.abs(bytes[i])));
            }

            return stringBuffer.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        return String.valueOf(key.hashCode());
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param bitmap
     */
    public static void writeImageCache(String key, Bitmap bitmap) {
        String keyCache = getCacheKey(key);
        try {
            DiskLruCache.Editor editor = mDiskLruCache.edit(keyCache);
            if (editor != null) {
                OutputStream outputStream = editor.newOutputStream(0);
                boolean isSuccess = bitmap.compress(Bitmap.CompressFormat.PNG, 100, outputStream);
                if (isSuccess) {
                    editor.commit();
                } else {
                    editor.abort();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 读出缓存
     *
     * @param key
     * @return
     */
    public static Bitmap readImageCache(String key) {
        String keyCache = getCacheKey(key);
        try {
            DiskLruCache.Snapshot snapshot = mDiskLruCache.get(keyCache);
            if (snapshot != null) {
                InputStream inputStream = snapshot.getInputStream(0);
                return BitmapFactory.decodeStream(inputStream);
            }


        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    /**
     * 刷新日志文件，一般在activity退出时刷新
     *
     * @throws IOException
     */
    public static void flush() throws IOException {
        if (mDiskLruCache != null) {
            mDiskLruCache.flush();
        }
    }

    /**
     * 关闭缓存
     *
     * @throws IOException
     */
    public static void close() throws IOException {
        if (mDiskLruCache != null) {
            mDiskLruCache.close();
        }
    }

}
