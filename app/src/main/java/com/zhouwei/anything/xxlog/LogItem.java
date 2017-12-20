
package com.zhouwei.anything.xxlog;

/**
 * Represent a single log going to be printed.
 */
public class LogItem {

  /**
   * Level of the log.
   *
   * @see LogLevel
   */
  public int level;

  /**
   * The tag, should not be null.
   */
  public String tag;

  /**
   * The formatted message, should not be null.
   */
  public String msg;

  /**
   * The formatted thread info, null if thread info is disabled.
   *
   * @see LogConfiguration.Builder#t()
   * @see LogConfiguration.Builder#nt()
   */
  public String threadInfo;

  /**
   * The formatted stack trace info, null if stack trace info is disabled.
   *
   * @see LogConfiguration.Builder#st(int)
   * @see LogConfiguration.Builder#nst()
   */
  public String stackTraceInfo;

  public LogItem(int level, String tag, String msg) {
    this.level = level;
    this.tag = tag;
    this.msg = msg;
  }

  public LogItem(int level, String tag, String threadInfo, String stackTraceInfo, String msg) {
    this.level = level;
    this.tag = tag;
    this.threadInfo = threadInfo;
    this.stackTraceInfo = stackTraceInfo;
    this.msg = msg;
  }
}
