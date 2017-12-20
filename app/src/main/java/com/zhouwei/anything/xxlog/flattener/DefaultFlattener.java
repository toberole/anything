
package com.zhouwei.anything.xxlog.flattener;


import com.zhouwei.anything.xxlog.LogLevel;

/**
 * Simply join the timestamp, log level, tag and message together.
 */
public class DefaultFlattener implements Flattener {

  @Override
  public CharSequence flatten(int logLevel, String tag, String message) {
    return Long.toString(System.currentTimeMillis())
        + '|' + LogLevel.getShortLevelName(logLevel)
        + '|' + tag
        + '|' + message;
  }
}
