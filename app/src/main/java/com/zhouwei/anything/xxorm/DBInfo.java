package com.zhouwei.anything.xxorm;

import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhouwei on 2017/12/20.
 */

public class DBInfo {
    private static final String TAG = DBInfo.class.getSimpleName();

    private String dbName;
    private int version;
    private SQLiteDatabase.CursorFactory cursorFactory;
    private DatabaseErrorHandler databaseErrorHandler;
    private Map<String, Table> tables = new HashMap<>();

    public List<String> sqls = new ArrayList<>();

    public DBSQLiteOpenHelper createDBSQLiteOpenHelper() {
        sqls.clear();
        for (Map.Entry<String, Table> en : tables.entrySet()) {
            Table table = en.getValue();
            String sql = table.createTableSql();
            sqls.add(sql);
        }

        DBSQLiteOpenHelper helper = new DBSQLiteOpenHelper(dbName, cursorFactory, version, sqls);
        return helper;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public SQLiteDatabase.CursorFactory getCursorFactory() {
        return cursorFactory;
    }

    public void setCursorFactoryClassName(String cursorFactoryClassName) {
        try {
            if (!TextUtils.isEmpty(cursorFactoryClassName) &&
                    !"com.zhouwei.anything.xxorm.DefaultCursorFactory"
                            .equals(cursorFactoryClassName)) {
                Object object = Class.forName(cursorFactoryClassName).newInstance();
                if (object instanceof SQLiteDatabase.CursorFactory) {
                    cursorFactory = (SQLiteDatabase.CursorFactory) object;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public DatabaseErrorHandler getDatabaseErrorHandler() {
        return databaseErrorHandler;
    }

    public void setDatabaseErrorHandlerClassName(String databaseErrorHandlerClassName) {
        try {
            if (!TextUtils.isEmpty(databaseErrorHandlerClassName) &&
                    !"com.zhouwei.anything.xxorm.DefaultDatabaseErrorHandler"
                            .equals(databaseErrorHandlerClassName)) {
                databaseErrorHandler = (DatabaseErrorHandler) Class.forName(databaseErrorHandlerClassName).newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Table getTable(String tableName) {
        return tables.get(tableName);
    }

    public void putTable(Table table) {
        tables.put(table.getTableName(), table);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();

        sb.append("dbName: " + dbName + " ");
        sb.append("version: " + version + " ");

        sb.append("cursorFactory: " + cursorFactory + " ");
        sb.append("databaseErrorHandler: " + databaseErrorHandler + " ");

        for (Map.Entry<String, Table> en : tables.entrySet()) {
            sb.append("tableName: " + en.getKey() + " ");
            sb.append("table: " + en.getValue() + " ");
        }
        return sb.toString();
    }
}
