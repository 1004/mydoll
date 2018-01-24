package com.qike.telecast.library.utils;

import com.google.gson.Gson;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by xky on 16/7/22.
 */
public class JsonUtils {
    public static String Obj2Json(Object obj) {
        String jsonStr = "";
        try {
            Gson gson = new Gson();
            jsonStr = gson.toJson(obj);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return jsonStr;
    }

    public static <T> T json2Obj(String json, Class<T> cls) {
        T obj = null;
        try {
            Gson gson = new Gson();
            obj = gson.fromJson(json, cls);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }

    public static List json2listobj(String json,Type type){
        List obj = null;
        try {
            Gson gson = new Gson();
            obj = gson.fromJson(json, type);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return obj;
    }
}
