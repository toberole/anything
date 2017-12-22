package com.zhouwei.anything.xxorm;

import android.text.TextUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by zhouwei on 2017/12/21.
 */

public class Table {
    private String tableName;
    private String beanName;
    private Map<String, Property> properties = new HashMap<>();

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        if (!TextUtils.isEmpty(beanName) && isSprite(beanName)) {
            this.beanName = beanName;
            int index = beanName.lastIndexOf(".");
            tableName = beanName.substring(index + 1);
        }
    }

    private boolean isSprite(String beanName) {
        return false;
    }

    public String createTableSql() {
        StringBuilder sb = new StringBuilder();
        sb.append("CREATE TABLE IF NOT EXISTS ").append(tableName + " ");
        sb.append("(");
        for (Map.Entry<String, Property> en : properties.entrySet()) {
            String PropertyName = en.getKey();
            Property property = en.getValue();

            sb.append(PropertyName + " ");
            if (property.isAutoincre()) {
                sb.append("INTEGER PRIMARY KEY AUTOINCREMENT,");
            } else if (property.isPrimaryKey()) {
                sb.append("TEXT PRIMARY KEY,");
            } else {
                sb.append("TEXT,");
            }
        }
        return sb.substring(0, sb.length() - 1) + ")";
    }

    public String getTableName() {
        return tableName;
    }

    public void putProperty(Property property) {
        properties.put(property.getPropertyName(), property);
    }

    public Property getProperty(String propertyName) {
        return properties.get(propertyName);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("tableName: " + tableName + " ");
        sb.append("beanName: " + beanName + " ");
        for (Map.Entry<String, Property> en : properties.entrySet()) {
            sb.append("PropertyName: " + en.getKey() + " ");
            sb.append("Property: " + en.getValue() + " ");
        }
        return sb.toString();
    }
}
