package com.vivo.jvm.sandbox.moonbox.plugin.jdk.http.util;


import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

public class GsonUtil {
    public GsonUtil() {
    }

    public static final Gson getInstance() {
        return GsonUtil.LazyHolder.gsonLazy;
    }

    public static <T> T getObjectData(String jsonString, Class<T> type) {
        Object t = null;

        try {
            Gson gson = getInstance();
            t = gson.fromJson(jsonString, type);
        } catch (JsonSyntaxException var4) {
        }

        return (T) t;
    }

    public static String getStringData(Object type) {
        String jsonStr = "";

        try {
            Gson gson = getInstance();
            jsonStr = gson.toJson(type);
        } catch (JsonSyntaxException var3) {
        }

        return jsonStr;
    }

    static {
        getInstance();
    }

    private static class LazyHolder {
        private static Gson gsonLazy = new Gson();

        private LazyHolder() {
        }
    }
}
