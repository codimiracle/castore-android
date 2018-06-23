package cn.com.sise.ca.castore.components;

import android.graphics.Bitmap;

import java.util.HashMap;
import java.util.Map;

/*
 * 简单粗暴地保存到内存！
 * 位图缓存
 */
public class BitmapCache {
    private static Map<String, Bitmap> cacheMap;

    static {
        cacheMap = new HashMap<>();
    }

    public static boolean hasCache(String identify) {
        return cacheMap.containsKey(identify);
    }

    public static void put(String identify, Bitmap bitmap) {
        cacheMap.put(identify, bitmap);
    }

    public static Bitmap get(String identify) {
        return cacheMap.get(identify);
    }

    public static void clear() {
        cacheMap.clear();
    }
}
