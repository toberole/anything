package com.zhouwei.anything.xxorm;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Created by zhouwei on 2017/12/20.
 */

public class DBEngine {
    private DBConfig dbConfig;
    private Context context;
    private volatile boolean isInit = false;

    private Map<String, String> tableDbNameDic = new HashMap<>();

    // dbName,DBSQLiteOpenHelper
    private HashMap<String, DBSQLiteOpenHelper> helpers = new HashMap<>();

    public Map<String, String> getTableDBNameDic() {
        return tableDbNameDic;
    }

    public void save(Class clazz, ContentValues values) {
        if (assertinit()) {
            DBSQLiteOpenHelper dbSQLiteOpenHelper = helpers.get(tableDbNameDic.get(clazz.getSimpleName()));
            if (dbSQLiteOpenHelper != null) {
                SQLiteDatabase db = dbSQLiteOpenHelper.getWritableDatabase();
                db.insert(clazz.getSimpleName(), "name", values);
                db.close();
            }
        }
    }

    public <T> List<T> get(Class clazz, String selection, String[] selectionArgs, String groupBy, String having, String orderBy) {
        List<T> res = null;
        if (assertinit()) {
            DBSQLiteOpenHelper dbSQLiteOpenHelper = helpers.get(tableDbNameDic.get(clazz.getSimpleName()));
            SQLiteDatabase db = dbSQLiteOpenHelper.getReadableDatabase();
            Cursor cursor = db.query(clazz.getSimpleName(), null, selection, selectionArgs, groupBy, having, orderBy);
            res = generateResults(clazz, cursor);
            db.close();
        }
        return res;
    }

    public <T> List<T> get(Class clazz, int offset, int rowCount) {
        List<T> res = null;
        if (assertinit()) {
            DBSQLiteOpenHelper dbSQLiteOpenHelper = helpers.get(tableDbNameDic.get(clazz.getSimpleName()));
            SQLiteDatabase db = dbSQLiteOpenHelper.getReadableDatabase();
            Cursor cursor = db.rawQuery(" SELECT * FROM " + clazz.getSimpleName() + " LIMIT " + rowCount + " OFFSET " + offset, null);
            res = generateResults(clazz, cursor);
            db.close();
        }
        return res;
    }

    public void delete(Class clazz, String whereClause, String[] whereArgs) {
        if (assertinit()) {
            DBSQLiteOpenHelper dbSQLiteOpenHelper = helpers.get(tableDbNameDic.get(clazz.getSimpleName()));
            SQLiteDatabase db = dbSQLiteOpenHelper.getReadableDatabase();
            db.delete(clazz.getSimpleName(), whereClause, whereArgs);
        }
    }

    public void update(Class clazz, ContentValues values, String whereClause, String[] whereArgs) {
        if (assertinit()) {
            DBSQLiteOpenHelper dbSQLiteOpenHelper = helpers.get(tableDbNameDic.get(clazz.getSimpleName()));
            SQLiteDatabase db = dbSQLiteOpenHelper.getReadableDatabase();
            db.update(clazz.getSimpleName(), values, whereClause, whereArgs);
        }
    }

    private void createdDBs(DBConfig instance) {
        Map<String, DBInfo> dbs = instance.getDbs();
        for (Map.Entry<String, DBInfo> en : dbs.entrySet()) {
            String dbName = en.getKey();
            DBInfo dbInfo = en.getValue();
            DBSQLiteOpenHelper helper = dbInfo.createDBSQLiteOpenHelper();
            helpers.put(dbName, helper);
        }
    }

    public List generateResults(Class clazz, Cursor cursor) {
        List res = new ArrayList();
        if (null != cursor) {
            while (cursor.moveToNext()) {
                try {
                    String[] columnNames = cursor.getColumnNames();
                    ContentValues values = new ContentValues();
                    for (int i = 0; i < columnNames.length; i++) {
                        String columnName = columnNames[i];
                        String columnValue = cursor.getString(cursor.getColumnIndex(columnName));
                        values.put(columnNames[i], columnValue);
                    }

                    Constructor constructor = clazz.getDeclaredConstructor(ContentValues.class);
                    constructor.setAccessible(true);
                    res.add(constructor.newInstance(values));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return res;
    }

    public Context getContext() {
        return context;
    }

    public synchronized void init(Context context) {
        if (!isInit) {
            this.context = context.getApplicationContext();
            try {
                dbConfig = DBConfig.getInstance();
                dbConfig.parserDBConfig(context.getAssets().open("db.xml"));
                createdDBs(DBConfig.getInstance());

                isInit = true;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public boolean assertinit() {
        return isInit;
    }

    private DBEngine() {
    }

    private static class DBEngineHolder {
        public static DBEngine instance = new DBEngine();
    }

    public static DBEngine getInstance() {
        return DBEngineHolder.instance;
    }
}
