package com.example.yangjw.a1504demo.bitmap;

import android.graphics.Bitmap;
import android.support.v4.util.LruCache;

/**
 * Created by yangjw on 2016/3/6.
 */
public class LruCacheUtil {

    private static LruCache<String, Bitmap> mLruCache;

    static {
//        long maxSize = Runtime.getRuntime().maxMemory()/8;
        int maxSize = 4 * 1024 * 1024;
        mLruCache = new LruCache<String, Bitmap>(maxSize) {
            @Override
            protected int sizeOf(String key, Bitmap value) {
//                return super.sizeOf(key, value);
                return value.getByteCount();
            }

        };
    }

    public static void putCacheMemory(String key, Bitmap value) {
        if (mLruCache != null) {
            mLruCache.put(key, value);
        }
    }

    public static Bitmap getCacheMemory(String key) {
        if (mLruCache != null) {
            return mLruCache.get(key);
        }
        return null;
    }
}
