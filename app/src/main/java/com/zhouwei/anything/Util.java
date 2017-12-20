package com.zhouwei.anything;


import org.json.JSONObject;

import java.lang.reflect.Field;


/**
 * Created by zhouwei on 2017/12/14.
 */

public class Util {
    public static <T> T jsonObjectToBean(JSONObject jsonObject, Class clazz) {
        T res = null;
        if (jsonObject == null || clazz == null) {
            return res;
        }

        try {
            res = (T) clazz.newInstance();
            Field[] fields = clazz.getDeclaredFields();
            if (fields.length != 0) {
                for (int i = 0; i < fields.length; i++) {
                    Field field = fields[i];
                    field.setAccessible(true);
                    if (field.getType().equals(int.class)) {
                        field.set(res, jsonObject.optInt(field.getName(), -1));
                    } else if (field.getType().equals(double.class)) {
                        field.set(res, jsonObject.optDouble(field.getName(), -1));
                    } else if (field.getType().equals(float.class)) {
                        field.set(res, (float) jsonObject.optDouble(field.getName(), -1));
                    } else if (field.getType().equals(long.class)) {
                        field.set(res, jsonObject.optLong(field.getName(), -1));
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return res;
    }
}
