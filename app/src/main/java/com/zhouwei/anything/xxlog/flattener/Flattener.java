package com.zhouwei.anything.xxlog.flattener;

public interface Flattener {
    CharSequence flatten(int logLevel, String tag, String message);
}
