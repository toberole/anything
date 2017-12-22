package com.zhouwei.anything.xxorm;

import android.text.TextUtils;


/**
 * Created by zhouwei on 2017/12/21.
 */

public class Property {
    private String propertyName;
    private Class type;
    private boolean isPrimaryKey;
    private boolean isAutoincre;

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public Class getPropertyType() {
        return type;
    }

    public void setPropertyType(String propertyType) {
        if (!TextUtils.isEmpty(propertyType)) {
            if ("int".equalsIgnoreCase(propertyType)) {
                type = int.class;
            } else if ("long".equalsIgnoreCase(propertyType)) {
                type = long.class;
            } else if ("float".equalsIgnoreCase(propertyType)) {
                type = float.class;
            } else if ("string".equalsIgnoreCase(propertyType)) {
                type = String.class;
            } else if ("double".equalsIgnoreCase(propertyType)) {
                type = double.class;
            } else if ("byte".equalsIgnoreCase(propertyType)) {
                type = byte.class;
            } else if ("short".equalsIgnoreCase(propertyType)) {
                type = short.class;
            }
        }
    }

    public boolean isPrimaryKey() {
        return isPrimaryKey;
    }

    public void setPrimaryKey(boolean primaryKey) {
        isPrimaryKey = primaryKey;
    }

    public boolean isAutoincre() {
        return isPrimaryKey && isAutoincre && type == int.class;
    }

    public void setAutoincre(boolean autoincre) {
        isAutoincre = autoincre;
    }

    @Override
    public String toString() {
        return "Property{" +
                "propertyName='" + propertyName + '\'' +
                ", type=" + type +
                ", isPrimaryKey=" + isPrimaryKey +
                ", isAutoincre=" + isAutoincre +
                '}';
    }
}
