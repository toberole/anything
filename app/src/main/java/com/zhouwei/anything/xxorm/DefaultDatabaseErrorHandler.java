package com.zhouwei.anything.xxorm;

import android.database.DatabaseErrorHandler;
import android.database.sqlite.SQLiteDatabase;

/**
 * Created by zhouwei on 2017/12/21.
 */

public class DefaultDatabaseErrorHandler implements DatabaseErrorHandler {
    @Override
    public void onCorruption(SQLiteDatabase dbObj) {

    }
}
