
package com.zhouwei.anything.xxlog.formatter.stacktrace;


import com.zhouwei.anything.xxlog.internal.SystemCompat;

/**
 * Formatted stack trace looks like:
 */
public class DefaultStackTraceFormatter implements StackTraceFormatter {

  @Override
  public String format(StackTraceElement[] stackTrace) {
    StringBuilder sb = new StringBuilder(256);
    if (stackTrace == null || stackTrace.length == 0) {
      return null;
    } else if (stackTrace.length == 1) {
      return "\t─ " + stackTrace[0].toString();
    } else {
      for (int i = 0, N = stackTrace.length; i < N; i++) {
        if (i != N - 1) {
          sb.append("\t├ ");
          sb.append(stackTrace[i].toString());
          sb.append(SystemCompat.lineSeparator);
        } else {
          sb.append("\t└ ");
          sb.append(stackTrace[i].toString());
        }
      }
      return sb.toString();
    }
  }
}
