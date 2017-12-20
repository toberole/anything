
package com.zhouwei.anything.xxlog.printer;


import com.zhouwei.anything.xxlog.flattener.Flattener;
import com.zhouwei.anything.xxlog.internal.DefaultsFactory;

public class ConsolePrinter implements Printer {

  /**
   * The log flattener when print a log.
   */
  private Flattener flattener;

  /**
   * Constructor.
   */
  public ConsolePrinter() {
    this.flattener = DefaultsFactory.createFlattener();
  }

  /**
   * Constructor.
   *
   * @param flattener the log flattener when print a log
   */
  public ConsolePrinter(Flattener flattener) {
    this.flattener = flattener;
  }

  @Override
  public void println(int logLevel, String tag, String msg) {
    String flattenedLog = flattener.flatten(logLevel, tag, msg).toString();
    System.out.println(flattenedLog);
  }
}
