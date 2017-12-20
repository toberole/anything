
package com.zhouwei.anything.xxlog.printer.file.naming;


import com.zhouwei.anything.xxlog.LogLevel;

/**
 * Generate file name according to the log level, different levels lead to different file names.
 */
public class LevelFileNameGenerator implements FileNameGenerator {

  @Override
  public boolean isFileNameChangeable() {
    return true;
  }

  /**
   * Generate a file name which represent a specific log level.
   */
  @Override
  public String generateFileName(int logLevel, long timestamp) {
    return LogLevel.getLevelName(logLevel);
  }
}
