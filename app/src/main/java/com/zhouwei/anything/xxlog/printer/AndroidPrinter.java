
package com.zhouwei.anything.xxlog.printer;

public class AndroidPrinter implements Printer {

  static final int MAX_LENGTH_OF_SINGLE_MESSAGE = 4063;

  @Override
  public void println(int logLevel, String tag, String msg) {
    if (msg.length() <= MAX_LENGTH_OF_SINGLE_MESSAGE) {
      printChunk(logLevel, tag, msg);
      return;
    }

    int msgLength = msg.length();
    int start = 0;
    int end = start + MAX_LENGTH_OF_SINGLE_MESSAGE;
    while (start < msgLength) {
      printChunk(logLevel, tag, msg.substring(start, end));

      start = end;
      end = Math.min(start + MAX_LENGTH_OF_SINGLE_MESSAGE, msgLength);
    }
  }

  /**
   * Print single chunk of log in new line.
   *
   * @param logLevel the level of log
   * @param tag      the tag of log
   * @param msg      the msg of log
   */
  void printChunk(int logLevel, String tag, String msg) {
    android.util.Log.println(logLevel, tag, msg);
  }
}
