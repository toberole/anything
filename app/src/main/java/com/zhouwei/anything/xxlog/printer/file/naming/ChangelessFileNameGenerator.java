
package com.zhouwei.anything.xxlog.printer.file.naming;

/**
 * Generate a file name that is changeless.
 */
public class ChangelessFileNameGenerator implements FileNameGenerator {

  private final String fileName;

  /**
   * Constructor.
   *
   * @param fileName the changeless file name
   */
  public ChangelessFileNameGenerator(String fileName) {
    this.fileName = fileName;
  }

  @Override
  public boolean isFileNameChangeable() {
    return false;
  }

  @Override
  public String generateFileName(int logLevel, long timestamp) {
    return fileName;
  }
}
