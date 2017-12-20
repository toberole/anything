
package com.zhouwei.anything.xxlog.internal;

import android.annotation.SuppressLint;
import android.os.Build;

import com.zhouwei.anything.xxlog.printer.AndroidPrinter;
import com.zhouwei.anything.xxlog.printer.ConsolePrinter;
import com.zhouwei.anything.xxlog.printer.Printer;


public class Platform {

  private static final Platform PLATFORM = findPlatform();

  public static Platform get() {
    return PLATFORM;
  }

  @SuppressLint("NewApi")
  String lineSeparator() {
    return System.lineSeparator();
  }

  Printer defaultPrinter() {
    return new ConsolePrinter();
  }

  public void warn(String msg) {
    System.out.println(msg);
  }

  private static Platform findPlatform() {
    try {
      Class.forName("android.os.Build");
      if (Build.VERSION.SDK_INT != 0) {
        return new Android();
      }
    } catch (ClassNotFoundException ignored) {
    }
    return new Platform();
  }

  static class Android extends Platform {
    @Override
    String lineSeparator() {
      if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
        return "\n";
      }
      return System.lineSeparator();
    }

    @Override
    Printer defaultPrinter() {
      return new AndroidPrinter();
    }

    @Override
    public void warn(String msg) {
      android.util.Log.w("XLog", msg);
    }
  }
}
