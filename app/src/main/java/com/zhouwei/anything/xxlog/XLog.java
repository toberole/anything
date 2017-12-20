
package com.zhouwei.anything.xxlog;

import com.zhouwei.anything.xxlog.formatter.border.BorderFormatter;
import com.zhouwei.anything.xxlog.formatter.message.json.JsonFormatter;
import com.zhouwei.anything.xxlog.formatter.message.object.ObjectFormatter;
import com.zhouwei.anything.xxlog.formatter.message.throwable.ThrowableFormatter;
import com.zhouwei.anything.xxlog.formatter.message.xml.XmlFormatter;
import com.zhouwei.anything.xxlog.formatter.stacktrace.StackTraceFormatter;
import com.zhouwei.anything.xxlog.formatter.thread.ThreadFormatter;
import com.zhouwei.anything.xxlog.interceptor.Interceptor;
import com.zhouwei.anything.xxlog.internal.DefaultsFactory;
import com.zhouwei.anything.xxlog.internal.Platform;
import com.zhouwei.anything.xxlog.internal.util.StackTraceUtil;
import com.zhouwei.anything.xxlog.printer.Printer;
import com.zhouwei.anything.xxlog.printer.PrinterSet;

public class XLog {

  /**
   * Global logger for all direct logging via {@link XLog}.
   */
  private static Logger sLogger;

  /**
   * Global log configuration.
   */
  static LogConfiguration sLogConfiguration;

  /**
   * Global log printer.
   */
  static Printer sPrinter;

  static boolean sIsInitialized;

  /**
   * Prevent instance.
   */
  private XLog() {
  }

  /**
   * Initialize log system, should be called only once.
   *
   * @since 1.3.0
   */
  public static void init() {
    init(new LogConfiguration.Builder().build(), DefaultsFactory.createPrinter());
  }

  /**
   * Initialize log system, should be called only once.
   *
   * @param logLevel the log level, logs with a lower level than which would not be printed
   */
  public static void init(int logLevel) {
    init(new LogConfiguration.Builder().logLevel(logLevel).build(),
        DefaultsFactory.createPrinter());
  }

  /**
   * Initialize log system, should be called only once.
   *
   * @param logLevel         the log level, logs with a lower level than which would not be printed
   * @param logConfiguration the log configuration
   * @deprecated the log level is part of log configuration now, use {@link #init(LogConfiguration)}
   * instead, since 1.3.0
   */
  @Deprecated
  public static void init(int logLevel, LogConfiguration logConfiguration) {
    init(new LogConfiguration.Builder(logConfiguration).logLevel(logLevel).build());
  }

  /**
   * Initialize log system, should be called only once.
   *
   * @param logConfiguration the log configuration
   * @since 1.3.0
   */
  public static void init(LogConfiguration logConfiguration) {
    init(logConfiguration, DefaultsFactory.createPrinter());
  }

  /**
   * Initialize log system, should be called only once.
   *
   * @param printers the printers, each log would be printed by all of the printers
   * @since 1.3.0
   */
  public static void init(Printer... printers) {
    init(new LogConfiguration.Builder().build(), printers);
  }

  /**
   * Initialize log system, should be called only once.
   *
   * @param logLevel the log level, logs with a lower level than which would not be printed
   * @param printers the printers, each log would be printed by all of the printers
   */
  public static void init(int logLevel, Printer... printers) {
    init(new LogConfiguration.Builder().logLevel(logLevel).build(), printers);
  }

  /**
   * Initialize log system, should be called only once.
   *
   * @param logLevel         the log level, logs with a lower level than which would not be printed
   * @param logConfiguration the log configuration
   * @param printers         the printers, each log would be printed by all of the printers
   * @deprecated the log level is part of log configuration now,
   * use {@link #init(LogConfiguration, Printer...)} instead, since 1.3.0
   */
  @Deprecated
  public static void init(int logLevel, LogConfiguration logConfiguration, Printer... printers) {
    init(new LogConfiguration.Builder(logConfiguration).logLevel(logLevel).build(), printers);
  }

  /**
   * Initialize log system, should be called only once.
   *
   * @param logConfiguration the log configuration
   * @param printers         the printers, each log would be printed by all of the printers
   * @since 1.3.0
   */
  public static void init(LogConfiguration logConfiguration, Printer... printers) {
    if (sIsInitialized) {
      Platform.get().warn("XLog is already initialized, do not initialize again");
    }
    sIsInitialized = true;

    if (logConfiguration == null) {
      throw new IllegalArgumentException("Please specify a LogConfiguration");
    }
    sLogConfiguration = logConfiguration;

    sPrinter = new PrinterSet(printers);

    sLogger = new Logger(sLogConfiguration, sPrinter);
  }

  /**
   * Throw an IllegalStateException if not initialized.
   */
  static void assertInitialization() {
    if (!sIsInitialized) {
      throw new IllegalStateException("Do you forget to initialize XLog?");
    }
  }

  /**
   * Start to customize a {@link Logger} and set the log level.
   *
   * @param logLevel the log level to customize
   * @return the {@link Logger.Builder} to build the {@link Logger}
   * @since 1.3.0
   */
  public static Logger.Builder logLevel(int logLevel) {
    return new Logger.Builder().logLevel(logLevel);
  }

  /**
   * Start to customize a {@link Logger} and set the tag.
   *
   * @param tag the tag to customize
   * @return the {@link Logger.Builder} to build the {@link Logger}
   */
  public static Logger.Builder tag(String tag) {
    return new Logger.Builder().tag(tag);
  }

  /**
   * Start to customize a {@link Logger} and enable thread info.
   *
   * @return the {@link Logger.Builder} to build the {@link Logger}
   */
  public static Logger.Builder t() {
    return new Logger.Builder().t();
  }

  /**
   * Start to customize a {@link Logger} and disable thread info.
   *
   * @return the {@link Logger.Builder} to build the {@link Logger}
   */
  public static Logger.Builder nt() {
    return new Logger.Builder().nt();
  }

  /**
   * Start to customize a {@link Logger} and enable stack trace.
   *
   * @param depth the number of stack trace elements we should log, 0 if no limitation
   * @return the {@link Logger.Builder} to build the {@link Logger}
   */
  public static Logger.Builder st(int depth) {
    return new Logger.Builder().st(depth);
  }

  /**
   * Start to customize a {@link Logger} and enable stack trace.
   *
   * @param stackTraceOrigin the origin of stack trace elements from which we should NOT log,
   *                         it can be a package name like "com.elvishew.xlog", a class name
   *                         like "com.yourdomain.logWrapper", or something else between
   *                         package name and class name, like "com.yourdomain.".
   *                         It is mostly used when you are using a logger wrapper
   * @param depth            the number of stack trace elements we should log, 0 if no limitation
   * @return the {@link Logger.Builder} to build the {@link Logger}
   * @since 1.4.0
   */
  public static Logger.Builder st(String stackTraceOrigin, int depth) {
    return new Logger.Builder().st(stackTraceOrigin, depth);
  }

  /**
   * Start to customize a {@link Logger} and disable stack trace.
   *
   * @return the {@link Logger.Builder} to build the {@link Logger}
   */
  public static Logger.Builder nst() {
    return new Logger.Builder().nst();
  }

  /**
   * Start to customize a {@link Logger} and enable border.
   *
   * @return the {@link Logger.Builder} to build the {@link Logger}
   */
  public static Logger.Builder b() {
    return new Logger.Builder().b();
  }

  /**
   * Start to customize a {@link Logger} and disable border.
   *
   * @return the {@link Logger.Builder} to build the {@link Logger}
   */
  public static Logger.Builder nb() {
    return new Logger.Builder().nb();
  }

  /**
   * Start to customize a {@link Logger} and set the {@link JsonFormatter}.
   *
   * @param jsonFormatter the {@link JsonFormatter} to customize
   * @return the {@link Logger.Builder} to build the {@link Logger}
   */
  public static Logger.Builder jsonFormatter(JsonFormatter jsonFormatter) {
    return new Logger.Builder().jsonFormatter(jsonFormatter);
  }

  /**
   * Start to customize a {@link Logger} and set the {@link XmlFormatter}.
   *
   * @param xmlFormatter the {@link XmlFormatter} to customize
   * @return the {@link Logger.Builder} to build the {@link Logger}
   */
  public static Logger.Builder xmlFormatter(XmlFormatter xmlFormatter) {
    return new Logger.Builder().xmlFormatter(xmlFormatter);
  }

  /**
   * Start to customize a {@link Logger} and set the {@link ThrowableFormatter}.
   *
   * @param throwableFormatter the {@link ThrowableFormatter} to customize
   * @return the {@link Logger.Builder} to build the {@link Logger}
   */
  public static Logger.Builder throwableFormatter(ThrowableFormatter throwableFormatter) {
    return new Logger.Builder().throwableFormatter(throwableFormatter);
  }

  /**
   * Start to customize a {@link Logger} and set the {@link ThreadFormatter}.
   *
   * @param threadFormatter the {@link ThreadFormatter} to customize
   * @return the {@link Logger.Builder} to build the {@link Logger}
   */
  public static Logger.Builder threadFormatter(ThreadFormatter threadFormatter) {
    return new Logger.Builder().threadFormatter(threadFormatter);
  }

  /**
   * Start to customize a {@link Logger} and set the {@link StackTraceFormatter}.
   *
   * @param stackTraceFormatter the {@link StackTraceFormatter} to customize
   * @return the {@link Logger.Builder} to build the {@link Logger}
   */
  public static Logger.Builder stackTraceFormatter(StackTraceFormatter stackTraceFormatter) {
    return new Logger.Builder().stackTraceFormatter(stackTraceFormatter);
  }

  /**
   * Start to customize a {@link Logger} and set the {@link BorderFormatter}.
   *
   * @param borderFormatter the {@link BorderFormatter} to customize
   * @return the {@link Logger.Builder} to build the {@link Logger}
   */
  public static Logger.Builder borderFormatter(BorderFormatter borderFormatter) {
    return new Logger.Builder().borderFormatter(borderFormatter);
  }

  /**
   * Start to customize a {@link Logger} and add an object formatter for specific class of object.
   *
   * @param objectClass     the class of object
   * @param objectFormatter the object formatter to add
   * @param <T>             the type of object
   * @return the {@link Logger.Builder} to build the {@link Logger}
   * @since 1.1.0
   */
  public static <T> Logger.Builder addObjectFormatter(Class<T> objectClass,
                                                      ObjectFormatter<? super T> objectFormatter) {
    return new Logger.Builder().addObjectFormatter(objectClass, objectFormatter);
  }

  /**
   * Start to customize a {@link Logger} and add an interceptor.
   *
   * @param interceptor the interceptor to add
   * @return the {@link Logger.Builder} to build the {@link Logger}
   * @since 1.3.0
   */
  public static Logger.Builder addInterceptor(Interceptor interceptor) {
    return new Logger.Builder().addInterceptor(interceptor);
  }

  /**
   * Start to customize a {@link Logger} and set the {@link Printer} array.
   *
   * @param printers the {@link Printer} array to customize
   * @return the {@link Logger.Builder} to build the {@link Logger}
   */
  public static Logger.Builder printers(Printer... printers) {
    return new Logger.Builder().printers(printers);
  }

  /**
   * Log an object with level {@link LogLevel#VERBOSE}.
   *
   * @param object the object to log
   * @see LogConfiguration.Builder#addObjectFormatter(Class, ObjectFormatter)
   * @since 1.1.0
   */
  public static void v(Object object) {
    assertInitialization();
    sLogger.v(object);
  }

  /**
   * Log an array with level {@link LogLevel#VERBOSE}.
   *
   * @param array the array to log
   */
  public static void v(Object[] array) {
    assertInitialization();
    sLogger.v(array);
  }

  /**
   * Log a message with level {@link LogLevel#VERBOSE}.
   *
   * @param format the format of the message to log
   * @param args   the arguments of the message to log
   */
  public static void v(String format, Object... args) {
    assertInitialization();
    sLogger.v(format, args);
  }

  /**
   * Log a message with level {@link LogLevel#VERBOSE}.
   *
   * @param msg the message to log
   */
  public static void v(String msg) {
    assertInitialization();
    sLogger.v(msg);
  }

  /**
   * Log a message and a throwable with level {@link LogLevel#VERBOSE}.
   *
   * @param msg the message to log
   * @param tr  the throwable to be log
   */
  public static void v(String msg, Throwable tr) {
    assertInitialization();
    sLogger.v(msg, tr);
  }

  /**
   * Log an object with level {@link LogLevel#DEBUG}.
   *
   * @param object the object to log
   * @see LogConfiguration.Builder#addObjectFormatter(Class, ObjectFormatter)
   * @since 1.1.0
   */
  public static void d(Object object) {
    assertInitialization();
    sLogger.d(object);
  }

  /**
   * Log an array with level {@link LogLevel#DEBUG}.
   *
   * @param array the array to log
   */
  public static void d(Object[] array) {
    assertInitialization();
    sLogger.d(array);
  }

  /**
   * Log a message with level {@link LogLevel#DEBUG}.
   *
   * @param format the format of the message to log
   * @param args   the arguments of the message to log
   */
  public static void d(String format, Object... args) {
    assertInitialization();
    sLogger.d(format, args);
  }

  /**
   * Log a message with level {@link LogLevel#DEBUG}.
   *
   * @param msg the message to log
   */
  public static void d(String msg) {
    assertInitialization();
    sLogger.d(msg);
  }

  /**
   * Log a message and a throwable with level {@link LogLevel#DEBUG}.
   *
   * @param msg the message to log
   * @param tr  the throwable to be log
   */
  public static void d(String msg, Throwable tr) {
    assertInitialization();
    sLogger.d(msg, tr);
  }

  /**
   * Log an object with level {@link LogLevel#INFO}.
   *
   * @param object the object to log
   * @see LogConfiguration.Builder#addObjectFormatter(Class, ObjectFormatter)
   * @since 1.1.0
   */
  public static void i(Object object) {
    assertInitialization();
    sLogger.i(object);
  }

  /**
   * Log an array with level {@link LogLevel#INFO}.
   *
   * @param array the array to log
   */
  public static void i(Object[] array) {
    assertInitialization();
    sLogger.i(array);
  }

  /**
   * Log a message with level {@link LogLevel#INFO}.
   *
   * @param format the format of the message to log
   * @param args   the arguments of the message to log
   */
  public static void i(String format, Object... args) {
    assertInitialization();
    sLogger.i(format, args);
  }

  /**
   * Log a message with level {@link LogLevel#INFO}.
   *
   * @param msg the message to log
   */
  public static void i(String msg) {
    assertInitialization();
    sLogger.i(msg);
  }

  /**
   * Log a message and a throwable with level {@link LogLevel#INFO}.
   *
   * @param msg the message to log
   * @param tr  the throwable to be log
   */
  public static void i(String msg, Throwable tr) {
    assertInitialization();
    sLogger.i(msg, tr);
  }

  /**
   * Log an object with level {@link LogLevel#WARN}.
   *
   * @param object the object to log
   * @see LogConfiguration.Builder#addObjectFormatter(Class, ObjectFormatter)
   * @since 1.1.0
   */
  public static void w(Object object) {
    assertInitialization();
    sLogger.w(object);
  }

  /**
   * Log an array with level {@link LogLevel#WARN}.
   *
   * @param array the array to log
   */
  public static void w(Object[] array) {
    assertInitialization();
    sLogger.w(array);
  }

  /**
   * Log a message with level {@link LogLevel#WARN}.
   *
   * @param format the format of the message to log
   * @param args   the arguments of the message to log
   */
  public static void w(String format, Object... args) {
    assertInitialization();
    sLogger.w(format, args);
  }

  /**
   * Log a message with level {@link LogLevel#WARN}.
   *
   * @param msg the message to log
   */
  public static void w(String msg) {
    assertInitialization();
    sLogger.w(msg);
  }

  /**
   * Log a message and a throwable with level {@link LogLevel#WARN}.
   *
   * @param msg the message to log
   * @param tr  the throwable to be log
   */
  public static void w(String msg, Throwable tr) {
    assertInitialization();
    sLogger.w(msg, tr);
  }

  /**
   * Log an object with level {@link LogLevel#ERROR}.
   *
   * @param object the object to log
   * @see LogConfiguration.Builder#addObjectFormatter(Class, ObjectFormatter)
   * @since 1.1.0
   */
  public static void e(Object object) {
    assertInitialization();
    sLogger.e(object);
  }

  /**
   * Log an array with level {@link LogLevel#ERROR}.
   *
   * @param array the array to log
   */
  public static void e(Object[] array) {
    assertInitialization();
    sLogger.e(array);
  }

  /**
   * Log a message with level {@link LogLevel#ERROR}.
   *
   * @param format the format of the message to log
   * @param args   the arguments of the message to log
   */
  public static void e(String format, Object... args) {
    assertInitialization();
    sLogger.e(format, args);
  }

  /**
   * Log a message with level {@link LogLevel#ERROR}.
   *
   * @param msg the message to log
   */
  public static void e(String msg) {
    assertInitialization();
    sLogger.e(msg);
  }

  /**
   * Log a message and a throwable with level {@link LogLevel#ERROR}.
   *
   * @param msg the message to log
   * @param tr  the throwable to be log
   */
  public static void e(String msg, Throwable tr) {
    assertInitialization();
    sLogger.e(msg, tr);
  }

  /**
   * Log an object with specific log level.
   *
   * @param logLevel the specific log level
   * @param object   the object to log
   * @see LogConfiguration.Builder#addObjectFormatter(Class, ObjectFormatter)
   * @since 1.4.0
   */
  public static void log(int logLevel, Object object) {
    assertInitialization();
    sLogger.log(logLevel, object);
  }

  /**
   * Log an array with specific log level.
   *
   * @param logLevel the specific log level
   * @param array    the array to log
   * @since 1.4.0
   */
  public static void log(int logLevel, Object[] array) {
    assertInitialization();
    sLogger.log(logLevel, array);
  }

  /**
   * Log a message with specific log level.
   *
   * @param logLevel the specific log level
   * @param format   the format of the message to log
   * @param args     the arguments of the message to log
   * @since 1.4.0
   */
  public static void log(int logLevel, String format, Object... args) {
    assertInitialization();
    sLogger.log(logLevel, format, args);
  }

  /**
   * Log a message with specific log level.
   *
   * @param logLevel the specific log level
   * @param msg      the message to log
   * @since 1.4.0
   */
  public static void log(int logLevel, String msg) {
    assertInitialization();
    sLogger.log(logLevel, msg);
  }

  /**
   * Log a message and a throwable with specific log level.
   *
   * @param logLevel the specific log level
   * @param msg      the message to log
   * @param tr       the throwable to be log
   * @since 1.4.0
   */
  public static void log(int logLevel, String msg, Throwable tr) {
    assertInitialization();
    sLogger.log(logLevel, msg, tr);
  }

  /**
   * Log a JSON string, with level {@link LogLevel#DEBUG} by default.
   *
   * @param json the JSON string to log
   */
  public static void json(String json) {
    assertInitialization();
    sLogger.json(json);
  }

  /**
   * Log a XML string, with level {@link LogLevel#DEBUG} by default.
   *
   * @param xml the XML string to log
   */
  public static void xml(String xml) {
    assertInitialization();
    sLogger.xml(xml);
  }

  /**
   * Compatible class with {@link android.util.Log}.
   *
   * @deprecated please use {@link XLog} instead
   */
  public static class Log {

    /**
     * @deprecated compatible with {@link android.util.Log#v(String, String)}
     */
    public static void v(String tag, String msg) {
      tag(tag).build().v(msg);
    }

    /**
     * @deprecated compatible with {@link android.util.Log#v(String, String, Throwable)}
     */
    public static void v(String tag, String msg, Throwable tr) {
      tag(tag).build().v(msg, tr);
    }

    /**
     * @deprecated compatible with {@link android.util.Log#d(String, String)}
     */
    public static void d(String tag, String msg) {
      tag(tag).build().d(msg);
    }

    /**
     * @deprecated compatible with {@link android.util.Log#d(String, String, Throwable)}
     */
    public static void d(String tag, String msg, Throwable tr) {
      tag(tag).build().d(msg, tr);
    }

    /**
     * @deprecated compatible with {@link android.util.Log#i(String, String)}
     */
    public static void i(String tag, String msg) {
      tag(tag).build().i(msg);
    }

    /**
     * @deprecated compatible with {@link android.util.Log#i(String, String, Throwable)}
     */
    public static void i(String tag, String msg, Throwable tr) {
      tag(tag).build().i(msg, tr);
    }

    /**
     * @deprecated compatible with {@link android.util.Log#w(String, String)}
     */
    public static void w(String tag, String msg) {
      tag(tag).build().w(msg);
    }

    /**
     * @deprecated compatible with {@link android.util.Log#w(String, String, Throwable)}
     */
    public static void w(String tag, String msg, Throwable tr) {
      tag(tag).build().w(msg, tr);
    }

    /**
     * @deprecated compatible with {@link android.util.Log#w(String, Throwable)}
     */
    public static void w(String tag, Throwable tr) {
      tag(tag).build().w("", tr);
    }

    /**
     * @deprecated compatible with {@link android.util.Log#e(String, String)}
     */
    public static void e(String tag, String msg) {
      tag(tag).build().e(msg);
    }

    /**
     * @deprecated compatible with {@link android.util.Log#e(String, String, Throwable)}
     */
    public static void e(String tag, String msg, Throwable tr) {
      tag(tag).build().e(msg, tr);
    }

    /**
     * @deprecated compatible with {@link android.util.Log#wtf(String, String)}
     */
    public static void wtf(String tag, String msg) {
      e(tag, msg);
    }

    /**
     * @deprecated compatible with {@link android.util.Log#wtf(String, Throwable)}
     */
    public static void wtf(String tag, Throwable tr) {
      wtf(tag, "", tr);
    }

    /**
     * @deprecated compatible with {@link android.util.Log#wtf(String, String, Throwable)}
     */
    public static void wtf(String tag, String msg, Throwable tr) {
      e(tag, msg, tr);
    }

    /**
     * @deprecated compatible with {@link android.util.Log#println(int, String, String)}
     */
    public static void println(int logLevel, String tag, String msg) {
      tag(tag).build().println(logLevel, msg);
    }

    /**
     * @deprecated compatible with {@link android.util.Log#isLoggable(String, int)}
     */
    public static boolean isLoggable(String tag, int level) {
      return sLogConfiguration.isLoggable(level);
    }

    /**
     * @deprecated compatible with {@link android.util.Log#getStackTraceString(Throwable)}
     */
    public static String getStackTraceString(Throwable tr) {
      return StackTraceUtil.getStackTraceString(tr);
    }
  }
}
