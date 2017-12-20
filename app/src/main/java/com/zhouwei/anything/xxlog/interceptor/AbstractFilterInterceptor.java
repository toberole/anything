
package com.zhouwei.anything.xxlog.interceptor;


import com.zhouwei.anything.xxlog.LogItem;

/**
 * An filter interceptor is used to filter some specific logs out, this filtered logs won't be
 * printed by any printer.
 *
 * @since 1.3.0
 */
public abstract class AbstractFilterInterceptor implements Interceptor {

  /**
   * {@inheritDoc}
   *
   * @param log the original log
   * @return the original log if it is acceptable, or null if it should be filtered out
   */
  @Override
  public LogItem intercept(LogItem log) {
    if (reject(log)) {
      // Filter this log out.
      return null;
    }
    return log;
  }

  /**
   * Whether specific log should be filtered out.
   *
   * @param log the specific log
   * @return true if the log should be filtered out, false otherwise
   */
  protected abstract boolean reject(LogItem log);
}
