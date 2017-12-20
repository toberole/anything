
package com.zhouwei.anything.xxlog.formatter;

/**
 * A formatter is used for format the data that is not a string, or that is a string but not well
 * formatted, we should format the data to a well formatted string so printers can print them.
 *
 * @param <T> the type of the data
 */
public interface Formatter<T> {

  /**
   * Format the data to a readable and loggable string.
   *
   * @param data the data to format
   * @return the formatted string data
   */
  String format(T data);
}
