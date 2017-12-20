
package com.zhouwei.anything.xxlog.printer;

/**
 * Represents a group of Printers that should used to print logs in the same time, each printer
 * may probably print the log to different place.
 */
public class PrinterSet implements Printer {

  private Printer[] printers;

  /**
   * Constructor, pass printers in and will use all these printers to print the same logs.
   *
   * @param printers the printers used to print the same logs
   */
  public PrinterSet(Printer... printers) {
    this.printers = printers;
  }

  @Override
  public void println(int logLevel, String tag, String msg) {
    for (Printer printer : printers) {
      printer.println(logLevel, tag, msg);
    }
  }
}
