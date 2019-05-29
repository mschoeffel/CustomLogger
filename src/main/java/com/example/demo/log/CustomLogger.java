package com.example.demo.log;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Custom logger
 * Please change configurations in the customLog.properties file.
 * @version 1.0
 * @author schoeffel_michael
 */
public final class CustomLogger {

  //Lowest level that will be logged:
  private static final short LOG_LEVEL_PROP = Short.parseShort(Optional.ofNullable(CustomLoggerUtil.getProp("custom.log.level")).orElse("0"));

  //Beginmessagephrase
  private static final String CUSTOM_MSG_BEGIN = CustomLoggerUtil.getProp("custom.log.msg.begin");
  //Endmessagephrase
  private static final String CUSTOM_MSG_END = CustomLoggerUtil.getProp("custom.log.msg.end");
  //Message seperator sequence
  private static final String CUSTOM_MSG_SEPERATOR = CustomLoggerUtil.getProp("custom.log.msg.seperator");

  //Boolean if time should be logged
  private static final boolean CUSTOM_LOG_TIME = Boolean.parseBoolean(CustomLoggerUtil.getProp("custom.log.time"));
  //Timepattern of the logged time
  private static final String CUSTOM_MSG_TIMEPATTERN = Optional.ofNullable(CustomLoggerUtil.getProp("custom.log.msg.timepattern")).orElse("yyyy/MM/dd HH:mm:ss");
  //Seperator sequence behind the time
  private static final String CUSTOM_MSG_TIMESEPERATOR = CustomLoggerUtil.getProp("custom.log.msg.timeseperator");

  //Boolean if the Exception should be logged
  private static final boolean CUSTOM_LOG_EXEPTION = Boolean.parseBoolean(CustomLoggerUtil.getProp("custom.log.exception"));
  //Errorheading phrase in front of the actual errortext
  private static final String CUSTOM_MSG_ERROR = CustomLoggerUtil.getProp("custom.log.msg.error");
  //Sequence before the actual errortext
  private static final String CUSTOM_MSG_PRE_ERRORTXT = CustomLoggerUtil.getProp("custom.log.msg.preerror");
  //Stackheading phrase in front of the actual stacktrace
  private static final String CUSTOM_MSG_STACK = CustomLoggerUtil.getProp("custom.log.msg.stack");
  //Sequence before the actual stacktrace
  private static final String CUSTOM_MSG_PRE_STACKTXT = CustomLoggerUtil.getProp("custom.log.msg.prestack");

  /**
   * Private constructor
   */
  private CustomLogger() {
  }

  /**
   * Gets the logger of the project.
   *
   * @return Logger of the project.
   */
  private static Logger getLogger() {
    return Logger.getLogger(CustomLogger.class.getName());
  }

  /**
   * Checks, if the given level should be logged or not depending on the setting of the lowest level that should be logged in the properties file.
   *
   * @param level Actual level.
   * @return Boolean if the level should be logged (true) or not (false).
   */
  private static boolean checkLogLevel(CustomLogLevel level) {
    if (LOG_LEVEL_PROP < 0)
      return false;
    return level.level() >= LOG_LEVEL_PROP;
  }

  /**
   * Generates the actual logmessage with the given information and settings done in the properties file.
   *
   * @param level Actual level.
   * @param msg   Actual message.
   * @param e     Actual exception (can be null).
   * @return Finish generated String.
   */
  private static String generateLogMessage(CustomLogLevel level, String msg, Exception e) {
    StringBuilder result = new StringBuilder();
    if (CUSTOM_LOG_TIME) {
      result.append(LocalDateTime.now().format(DateTimeFormatter.ofPattern(CUSTOM_MSG_TIMEPATTERN)))
              .append(CUSTOM_MSG_TIMESEPERATOR);
    }
    result.append(CUSTOM_MSG_BEGIN).append(CUSTOM_MSG_SEPERATOR)
            .append(level.text()).append(CUSTOM_MSG_SEPERATOR)
            .append(msg).append(CUSTOM_MSG_SEPERATOR).append(CUSTOM_MSG_END);
    if (e != null && CUSTOM_LOG_EXEPTION) {
      result.append(CUSTOM_MSG_ERROR).append(CUSTOM_MSG_PRE_ERRORTXT).append(e.getLocalizedMessage());
      StringWriter sw = new StringWriter();
      PrintWriter pw = new PrintWriter(sw, true);
      e.printStackTrace(pw);
      result.append(CUSTOM_MSG_STACK).append(CUSTOM_MSG_PRE_STACKTXT).append(sw.getBuffer());
      try {
        sw.close();
      } catch (IOException ex) {
        ex.printStackTrace();
      }
      pw.close();
    }
    return result.toString();
  }

  /**
   * Writes a warning log entry.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   * @param e     Exception.
   */
  public static void warning(CustomLogLevel level, String msg, Exception e) {
    if (checkLogLevel(level))
      getLogger().warning(generateLogMessage(level, msg, e));
  }

  /**
   * Writes a warning log entry without an exception.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   */
  public static void warning(CustomLogLevel level, String msg) {
    warning(level, msg, null);
  }

  /**
   * Writes a warning log entry without an exception and with undefined level.
   *
   * @param msg Custom message.
   */
  public static void warning(String msg) {
    warning(CustomLogLevel.UNDEFINED, msg);
  }

  /**
   * Writes an info log entry.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   * @param e     Exception.
   */
  public static void info(CustomLogLevel level, String msg, Exception e) {
    if (checkLogLevel(level))
      getLogger().info(generateLogMessage(level, msg, e));
  }

  /**
   * Writes an info log entry without an exception.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   */
  public static void info(CustomLogLevel level, String msg) {
    info(level, msg, null);
  }

  /**
   * Writes an info log entry without an exception and with undefined level.
   *
   * @param msg Custom message.
   */
  public static void info(String msg) {
    info(CustomLogLevel.UNDEFINED, msg);
  }

  /**
   * Writes a config log entry.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   * @param e     Exception.
   */
  public static void config(CustomLogLevel level, String msg, Exception e) {
    if (checkLogLevel(level))
      getLogger().config(generateLogMessage(level, msg, e));
  }

  /**
   * Writes a config log entry without an exception.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   */
  public static void config(CustomLogLevel level, String msg) {
    config(level, msg, null);
  }

  /**
   * Writes a config log entry without an exception and with undefined level.
   *
   * @param msg Custom message.
   */
  public static void config(String msg) {
    config(CustomLogLevel.UNDEFINED, msg);
  }

  /**
   * Writes a fine log entry. If fine isnt supported it gets delegated to an info log entry.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   * @param e     Exception.
   */
  public static void fine(CustomLogLevel level, String msg, Exception e) {
    if (getLogger().isLoggable(Level.FINE)) {
      if (checkLogLevel(level))
        getLogger().fine(generateLogMessage(level, msg, e));
    } else {
      info(level, msg, e);
    }
  }

  /**
   * Writes a fine log entry without an exception. If fine isnt supported it gets delegated to an info log entry.
   *
   * @param level Log level.
   * @param msg   Custom message.
   */
  public static void fine(CustomLogLevel level, String msg) {
    fine(level, msg, null);
  }

  /**
   * Writes a fine log entry without an exception and with undefined level. If fine isnt supported it gets delegated to an info log entry.
   *
   * @param msg Custom message.
   */
  public static void fine(String msg) {
    fine(CustomLogLevel.UNDEFINED, msg);
  }

  /**
   * Writes a finer log entry. If finer isnt supported it gets delegated to an info log entry.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   * @param e     Exception.
   */
  public static void finer(CustomLogLevel level, String msg, Exception e) {
    if (getLogger().isLoggable(Level.FINER)) {
      if (checkLogLevel(level))
        getLogger().finer(generateLogMessage(level, msg, e));
    } else {
      info(level, msg, e);
    }
  }

  /**
   * Writes a finer log entry without an exception. If finer isnt supported it gets delegated to an info log entry.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   */
  public static void finer(CustomLogLevel level, String msg) {
    finer(level, msg, null);
  }

  /**
   * Writes a finer log entry without an exception and with undefined level. If finer isnt supportet it gets delegated to an info log entry.
   *
   * @param msg Custom message.
   */
  public static void finer(String msg) {
    finer(CustomLogLevel.UNDEFINED, msg);
  }

  /**
   * Writes a finest log entry. If finest isnt supported it gets delegated to an info log entry.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   * @param e     Exception.
   */
  public static void finest(CustomLogLevel level, String msg, Exception e) {
    if (getLogger().isLoggable(Level.FINEST)) {
      if (checkLogLevel(level))
        getLogger().finest(generateLogMessage(level, msg, e));
    } else {
      info(level, msg, e);
    }
  }

  /**
   * Writes a finest log entry without an exception. If finest isnt supported it gets delegated to an info log entry.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   */
  public static void finest(CustomLogLevel level, String msg) {
    finest(level, msg, null);
  }

  /**
   * Writes a finest log entry without an exception and with undefined level. If finest isnt supported it gets delegated to an info log enrty.
   *
   * @param msg Custom message.
   */
  public static void finest(String msg) {
    finest(CustomLogLevel.UNDEFINED, msg);
  }
}
