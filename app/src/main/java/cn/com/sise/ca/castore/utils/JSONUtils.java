package cn.com.sise.ca.castore.utils;

import com.google.gson.Gson;

public class JSONUtils {
    private static final Gson instance = new Gson();

    public static Gson getInstance() {
        return instance;
    }

    public static <T> T fromJSON(String json, Class<T> classOfT) {
        return instance.fromJson(json, classOfT);
    }
}
