package com.zhouwei.anything.xxorm;

import android.content.Context;
import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;


/**
 * Created by zhouwei on 2017/12/21.
 */

public class DBSQLiteOpenHelper extends SQLiteOpenHelper {
    private final static String TAG = "newDBSQLiteOpenHelper";
    private List<String> sqls;

    public DBSQLiteOpenHelper(String name, SQLiteDatabase.CursorFactory factory, int version, List<String> sqls) {
        super(DBEngine.getInstance().getContext(), name, factory, version);
        this.sqls = sqls;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        Log.i(TAG, "sqls size: " + sqls.size());
        for (int i = 0; i < sqls.size(); i++) {
            db.execSQL(sqls.get(i));
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
