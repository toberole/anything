
package com.zhouwei.anything.xxlog.formatter.message.throwable;


import com.zhouwei.anything.xxlog.internal.util.StackTraceUtil;

/**
 * Simply put each stack trace(method name, source file and line number) of the throwable
 * in a single line.
 */
public class DefaultThrowableFormatter implements ThrowableFormatter {

  @Override
  public String format(Throwable tr) {
    return StackTraceUtil.getStackTraceString(tr);
  }
}
