
package com.zhouwei.anything.xxlog.printer.file.naming;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

/**
 * Generate file name according to the timestamp, different dates will lead to different file names.
 */
public class DateFileNameGenerator implements FileNameGenerator {

  ThreadLocal<SimpleDateFormat> mLocalDateFormat = new ThreadLocal<SimpleDateFormat>() {

    @Override
    protected SimpleDateFormat initialValue() {
      return new SimpleDateFormat("yyyy-MM-dd", Locale.US);
    }
  };

  @Override
  public boolean isFileNameChangeable() {
    return true;
  }

  /**
   * Generate a file name which represent a specific date.
   */
  @Override
  public String generateFileName(int logLevel, long timestamp) {
    SimpleDateFormat sdf = mLocalDateFormat.get();
    sdf.setTimeZone(TimeZone.getDefault());
    return sdf.format(new Date(timestamp));
  }
}
