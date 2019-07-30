package com.example.demo.log;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Map;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Custom logger
 * Please change configurations in the customLog.properties file.
 *
 * @author (c) mschoeffel
 * @version 2.0
 */
public final class CustomLogger {

  /*------------------------------------------------------------------------------------------------------------------*/
  /* CONSTANTS FROM PROPERTIES FILE                                                                                   */
  /*------------------------------------------------------------------------------------------------------------------*/

  /*---------------------------------------------------------*/
  /* GENERAL CONSTANTS                                       */
  /*---------------------------------------------------------*/
  // Lowest level that will be logged
  private static final short LOG_LEVEL = Short.parseShort(
          Optional.ofNullable(CustomLoggerUtil.getProp("custom.log.level")).orElse("0"));
  // New line character
  public static final String NEW_LINE_CHAR = CustomLoggerUtil.getProp("custom.log.newlinechar");


  /*---------------------------------------------------------*/
  /* TIME CONSTANTS                                          */
  /*---------------------------------------------------------*/
  // Should the time section be logged
  private static final boolean LOG_MSG_TIME_SHOW = Boolean.parseBoolean(CustomLoggerUtil.getProp("custom.log.msg.time.show"));
  // Pattern of the logged time
  private static final String LOG_MSG_TIME_PATTERN = Optional.ofNullable(
          CustomLoggerUtil.getProp("custom.log.msg.time.pattern")).orElse("yyyy/MM/dd HH:mm:ss");
  // Seperator between time and main section
  private static final String LOG_MSG_TIME_SEP = CustomLoggerUtil.getProp("custom.log.msg.time.sep");


  /*---------------------------------------------------------*/
  /* MAIN CONSTANTS                                          */
  /*---------------------------------------------------------*/
  // Text at the beginning of the main section
  private static final String LOG_MSG_MAIN_BEGIN = CustomLoggerUtil.getProp("custom.log.msg.main.begin");
  // Text at the end of the main section
  private static final String LOG_MSG_MAIN_END = CustomLoggerUtil.getProp("custom.log.msg.main.end");
  // Separator between all the parts of the main section
  private static final String LOG_MSG_MAIN_SEP = CustomLoggerUtil.getProp("custom.log.msg.main.sep");


  /*---------------------------------------------------------*/
  /* ADDITIONAL CONSTANTS                                    */
  /*---------------------------------------------------------*/
  // Should the additional section be logged
  private static final boolean LOG_MSG_ADD_SHOW = Boolean.parseBoolean(CustomLoggerUtil.getProp("custom.log.msg.add.show"));
  // Sequence before additional headline
  private static final String LOG_MSG_ADD_HEAD_PRE = CustomLoggerUtil.getProp("custom.log.msg.add.head.pre");
  // Headline of the additional sequence
  private static final String LOG_MSG_ADD_HEAD_TEXT = CustomLoggerUtil.getProp("custom.log.msg.add.head.text");
  // Sequence before every additional entity
  private static final String LOG_MSG_ADD_ITEM_PRE = CustomLoggerUtil.getProp("custom.log.msg.add.item.pre");
  // Sequence between the single elements
  private static final String LOG_MSG_ADD_ITEM_SEP = CustomLoggerUtil.getProp("custom.log.msg.add.item.sep");


  /*---------------------------------------------------------*/
  /* ERROR CONSTANTS                                         */
  /*---------------------------------------------------------*/
  // Should the error section be logged
  private static final boolean LOG_MSG_ERR_SHOW = Boolean.parseBoolean(CustomLoggerUtil.getProp("custom.log.msg.err.show"));
  // Sequence before error headline
  private static final String LOG_MSG_ERR_HEAD_PRE = CustomLoggerUtil.getProp("custom.log.msg.err.head.pre");
  // Headline of the error sequence
  private static final String LOG_MSG_ERR_HEAD_TEXT = CustomLoggerUtil.getProp("custom.log.msg.err.head.text");
  // Sequence before the error information
  private static final String LOG_MSG_ERR_ITEM_PRE = CustomLoggerUtil.getProp("custom.log.msg.err.item.pre");
  // Seperator between exception class and message in the error information
  private static final String LOG_MSG_ERR_ITEM_SEP = CustomLoggerUtil.getProp("custom.log.msg.err.item.sep");


  /*---------------------------------------------------------*/
  /* STACKTRACE CONSTANTS                                    */
  /*---------------------------------------------------------*/
  // Should the stacktrace section be logged
  private static final boolean LOG_MSG_STACK_SHOW = Boolean.parseBoolean(CustomLoggerUtil.getProp("custom.log.msg.stack.show"));
  // Sequence before stacktrace headline
  private static final String LOG_MSG_STACK_HEAD_PRE = CustomLoggerUtil.getProp("custom.log.msg.stack.head.pre");
  // Headline of the stacktrace sequence
  private static final String LOG_MSG_STACK_HEAD_TEXT = CustomLoggerUtil.getProp("custom.log.msg.stack.head.text");
  // Sequence before every stacktrace entity
  private static final String LOG_MSG_STACK_ITEM_PRE = CustomLoggerUtil.getProp("custom.log.msg.stack.item.pre");
  // Minimum number of lines to log from the Stacktrace if -1 the whole stacktrace will be logged
  private static final int LOG_MSG_STACK_ITEMS_LINES = Integer.parseInt(
          Optional.ofNullable(CustomLoggerUtil.getProp("custom.log.msg.stack.items.lines.min")).orElse("5"));
  // Maximum number of lines to log from the Stacktrace
  private static final int LOG_MSG_STACK_ITEMS_LINES_MAX = Integer.parseInt(
          Optional.ofNullable(CustomLoggerUtil.getProp("custom.log.msg.stack.items.lines.max")).orElse("10"));
  // Border, when the LogLevel is higher than this given level the linex.max will be used instead of the normal lines.min
  private static final int LOG_MSG_STACK_LINES_BORDER = Integer.parseInt(
          Optional.ofNullable(CustomLoggerUtil.getProp("custom.log.msg.stack.items.lines.border")).orElse("4"));


  /*------------------------------------------------------------------------------------------------------------------*/
  /* CONSTRUCTOR                                                                                                      */
  /*------------------------------------------------------------------------------------------------------------------*/

  /**
   * Private constructor
   */
  private CustomLogger() {}


  /*------------------------------------------------------------------------------------------------------------------*/
  /* GENERAL METHODS                                                                                                  */
  /*------------------------------------------------------------------------------------------------------------------*/

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
    if (LOG_LEVEL < 0)
      return false;
    return level.level() >= LOG_LEVEL;
  }


  /*------------------------------------------------------------------------------------------------------------------*/
  /* GENERATOR METHODS                                                                                                */
  /*------------------------------------------------------------------------------------------------------------------*/

  /*---------------------------------------------------------*/
  /* TIME                                                    */
  /*---------------------------------------------------------*/

  /**
   * Generates the time sequence.
   *
   * @return Finish generated time sequence String.
   */
  private static String generateTimeSequence() {
    return LocalDateTime.now().format(DateTimeFormatter.ofPattern(LOG_MSG_TIME_PATTERN)) + LOG_MSG_TIME_SEP;
  }

  /*---------------------------------------------------------*/
  /* MAIN                                                    */
  /*---------------------------------------------------------*/

  /**
   * Generates the main head sequence.
   *
   * @param level Loglevel.
   * @param msg   Message.
   * @return Finish generated main head sequence String.
   */
  private static String generateMainSequence(CustomLogLevel level, String msg) {
    return LOG_MSG_MAIN_BEGIN + LOG_MSG_MAIN_SEP + level.text() + LOG_MSG_MAIN_SEP + msg + LOG_MSG_MAIN_SEP + LOG_MSG_MAIN_END;
  }

  /*---------------------------------------------------------*/
  /* ADDITIONAL                                              */
  /*---------------------------------------------------------*/

  /**
   * Generates the additional sequence.
   *
   * @param add Additional information map.
   * @return Finish generated additional sequence String.
   */
  private static String generateAddSequence(Map<String, ?> add) {
    StringBuilder sb = new StringBuilder();
    if (add != null) {
      sb.append(NEW_LINE_CHAR).append(LOG_MSG_ADD_HEAD_PRE).append(LOG_MSG_ADD_HEAD_TEXT);
      add.forEach((i, j) -> sb.append(NEW_LINE_CHAR).append(LOG_MSG_ADD_ITEM_PRE).append(i).append(LOG_MSG_ADD_ITEM_SEP).append(j));
    }
    return sb.toString();
  }

  /*---------------------------------------------------------*/
  /* ERROR                                                   */
  /*---------------------------------------------------------*/

  /**
   * Generates the error sequence.
   *
   * @param e Exception.
   * @return Finish generated error sequence String.
   */
  private static String generateErrorSequence(Exception e) {
    String result = "";
    if (e != null) {
      result = NEW_LINE_CHAR + LOG_MSG_ERR_HEAD_PRE + LOG_MSG_ERR_HEAD_TEXT +
              NEW_LINE_CHAR + LOG_MSG_ERR_ITEM_PRE + e.getClass().getName() + LOG_MSG_ERR_ITEM_SEP +
              e.getLocalizedMessage();
    }
    return result;
  }

  /*---------------------------------------------------------*/
  /* STACKTRACE                                              */
  /*---------------------------------------------------------*/

  /**
   * Generates the stacktrace sequence.
   *
   * @param level LogLevel.
   * @param e     Exception.
   * @return Finish generated tacktrace sequece String.
   */
  private static String generateStackTraceSequence(CustomLogLevel level, Exception e) {
    StringBuilder sb = new StringBuilder();
    if (e != null) {
      sb.append(NEW_LINE_CHAR).append(LOG_MSG_STACK_HEAD_PRE).append(LOG_MSG_STACK_HEAD_TEXT);
      StackTraceElement[] elements = e.getStackTrace();
      int lines = LOG_MSG_STACK_ITEMS_LINES;
      if (level.level() >= LOG_MSG_STACK_LINES_BORDER) {
        lines = LOG_MSG_STACK_ITEMS_LINES_MAX;
      }

      if (lines >= elements.length || LOG_MSG_STACK_ITEMS_LINES == -1) {
        lines = elements.length;
      }

      for (int i = 0; i < lines; i++) {
        sb.append(NEW_LINE_CHAR).append(LOG_MSG_STACK_ITEM_PRE).append(elements[i]);
      }
    }
    return sb.toString();
  }

  /*---------------------------------------------------------*/
  /* Main Generator                                          */
  /*---------------------------------------------------------*/

  /**
   * Generates the actual logmessage with the given information and settings done in the properties file.
   *
   * @param level Actual level.
   * @param msg   Actual message.
   * @param e     Actual exception (can be null).
   * @param add   Additional information map.
   * @return Finish generated String.
   */
  private static String generateLogMessage(CustomLogLevel level, String msg, Exception e, Map<String, ?> add) {
    StringBuilder result = new StringBuilder();
    if (LOG_MSG_TIME_SHOW) {
      result.append(generateTimeSequence());
    }
    result.append(generateMainSequence(level, msg));
    if (LOG_MSG_ADD_SHOW) {
      result.append(generateAddSequence(add));
    }
    if (LOG_MSG_ERR_SHOW) {
      result.append(generateErrorSequence(e));
    }
    if (LOG_MSG_STACK_SHOW) {
      result.append(generateStackTraceSequence(level, e));
    }
    return result.toString();
  }

  /*------------------------------------------------------------------------------------------------------------------*/
  /* DIFFERENT LOG METHODS                                                                                            */
  /*------------------------------------------------------------------------------------------------------------------*/

  /*---------------------------------------------------------*/
  /* LOG: WARNING                                            */
  /*---------------------------------------------------------*/

  /**
   * Writes a warning log entry.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   * @param e     Exception.
   * @param add   Additional information map.
   */
  public static void warning(CustomLogLevel level, String msg, Exception e, Map<String, ?> add) {
    if (checkLogLevel(level))
      getLogger().warning(generateLogMessage(level, msg, e, add));
  }

  /**
   * Writes a warning log entry.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   * @param e     Exception.
   */
  public static void warning(CustomLogLevel level, String msg, Exception e) {
    warning(level, msg, e, null);
  }

  /**
   * Writes a warning log entry.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   * @param add   Additional information map.
   */
  public static void warning(CustomLogLevel level, String msg, Map<String, ?> add) {
    warning(level, msg, null, add);
  }

  /**
   * Writes a warning log entry without an exception.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   */
  public static void warning(CustomLogLevel level, String msg) {
    warning(level, msg, null, null);
  }

  /**
   * Writes a warning log entry without an exception and with undefined level.
   *
   * @param msg Custom message.
   */
  public static void warning(String msg) {
    warning(CustomLogLevel.UNDEFINED, msg);
  }

  /*---------------------------------------------------------*/
  /* LOG: INFO                                               */
  /*---------------------------------------------------------*/

  /**
   * Writes an info log entry.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   * @param e     Exception.
   * @param add   Additional information map.
   */
  public static void info(CustomLogLevel level, String msg, Exception e, Map<String, ?> add) {
    if (checkLogLevel(level))
      getLogger().info(generateLogMessage(level, msg, e, add));
  }

  /**
   * Writes an info log entry.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   * @param e     Exception.
   */
  public static void info(CustomLogLevel level, String msg, Exception e) {
    info(level, msg, e, null);
  }

  /**
   * Writes an info log entry.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   * @param add   Additional information map.
   */
  public static void info(CustomLogLevel level, String msg, Map<String, ?> add) {
    info(level, msg, null, add);
  }

  /**
   * Writes an info log entry without an exception.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   */
  public static void info(CustomLogLevel level, String msg) {
    info(level, msg, null, null);
  }

  /**
   * Writes an info log entry without an exception and with undefined level.
   *
   * @param msg Custom message.
   */
  public static void info(String msg) {
    info(CustomLogLevel.UNDEFINED, msg);
  }

  /*---------------------------------------------------------*/
  /* LOG: CONFIG                                             */
  /*---------------------------------------------------------*/

  /**
   * Writes a config log entry.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   * @param e     Exception.
   * @param add   Additional information map.
   */
  public static void config(CustomLogLevel level, String msg, Exception e, Map<String, ?> add) {
    if (checkLogLevel(level))
      getLogger().config(generateLogMessage(level, msg, e, add));
  }

  /**
   * Writes a config log entry.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   * @param e     Exception.
   */
  public static void config(CustomLogLevel level, String msg, Exception e) {
    config(level, msg, e, null);
  }

  /**
   * Writes a config log entry.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   * @param add   Additional information map.
   */
  public static void config(CustomLogLevel level, String msg, Map<String, ?> add) {
    config(level, msg, null, add);
  }

  /**
   * Writes a config log entry without an exception.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   */
  public static void config(CustomLogLevel level, String msg) {
    config(level, msg, null, null);
  }

  /**
   * Writes a config log entry without an exception and with undefined level.
   *
   * @param msg Custom message.
   */
  public static void config(String msg) {
    config(CustomLogLevel.UNDEFINED, msg);
  }

  /*---------------------------------------------------------*/
  /* LOG: FINE                                               */
  /*---------------------------------------------------------*/

  /**
   * Writes a fine log entry. If fine isnt supported it gets delegated to an info log entry.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   * @param e     Exception.
   * @param add   Additional information map.
   */
  public static void fine(CustomLogLevel level, String msg, Exception e, Map<String, ?> add) {
    if (getLogger().isLoggable(Level.FINE)) {
      if (checkLogLevel(level))
        getLogger().fine(generateLogMessage(level, msg, e, add));
    } else {
      info(level, msg, e, add);
    }
  }

  /**
   * Writes a fine log entry. If fine isnt supported it gets delegated to an info log entry.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   * @param e     Exception.
   */
  public static void fine(CustomLogLevel level, String msg, Exception e) {
    fine(level, msg, e, null);
  }

  /**
   * Writes a fine log entry. If fine isnt supported it gets delegated to an info log entry.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   * @param add   Additional information map.
   */
  public static void fine(CustomLogLevel level, String msg, Map<String, ?> add) {
    fine(level, msg, null, add);
  }

  /**
   * Writes a fine log entry without an exception. If fine isnt supported it gets delegated to an info log entry.
   *
   * @param level Log level.
   * @param msg   Custom message.
   */
  public static void fine(CustomLogLevel level, String msg) {
    fine(level, msg, null, null);
  }

  /**
   * Writes a fine log entry without an exception and with undefined level. If fine isnt supported it gets delegated to an info log entry.
   *
   * @param msg Custom message.
   */
  public static void fine(String msg) {
    fine(CustomLogLevel.UNDEFINED, msg);
  }

  /*---------------------------------------------------------*/
  /* LOG: FINER                                              */
  /*---------------------------------------------------------*/

  /**
   * Writes a finer log entry. If finer isnt supported it gets delegated to an info log entry.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   * @param e     Exception.
   * @param add   Additional information map.
   */
  public static void finer(CustomLogLevel level, String msg, Exception e, Map<String, ?> add) {
    if (getLogger().isLoggable(Level.FINER)) {
      if (checkLogLevel(level))
        getLogger().finer(generateLogMessage(level, msg, e, add));
    } else {
      info(level, msg, e, add);
    }
  }

  /**
   * Writes a finer log entry. If finer isnt supported it gets delegated to an info log entry.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   * @param e     Exception.
   */
  public static void finer(CustomLogLevel level, String msg, Exception e) {
    finer(level, msg, e, null);
  }

  /**
   * Writes a finer log entry. If finer isnt supported it gets delegated to an info log entry.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   * @param add   Additional information map.
   */
  public static void finer(CustomLogLevel level, String msg, Map<String, ?> add) {
    finer(level, msg, null, add);
  }

  /**
   * Writes a finer log entry without an exception. If finer isnt supported it gets delegated to an info log entry.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   */
  public static void finer(CustomLogLevel level, String msg) {
    finer(level, msg, null, null);
  }

  /**
   * Writes a finer log entry without an exception and with undefined level. If finer isnt supportet it gets delegated to an info log entry.
   *
   * @param msg Custom message.
   */
  public static void finer(String msg) {
    finer(CustomLogLevel.UNDEFINED, msg);
  }

  /*---------------------------------------------------------*/
  /* LOG: FINEST                                             */
  /*---------------------------------------------------------*/

  /**
   * Writes a finest log entry. If finest isnt supported it gets delegated to an info log entry.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   * @param e     Exception.
   * @param add   Additional information map.
   */
  public static void finest(CustomLogLevel level, String msg, Exception e, Map<String, ?> add) {
    if (getLogger().isLoggable(Level.FINEST)) {
      if (checkLogLevel(level))
        getLogger().finest(generateLogMessage(level, msg, e, add));
    } else {
      info(level, msg, e, add);
    }
  }

  /**
   * Writes a finest log entry. If finest isnt supported it gets delegated to an info log entry.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   * @param e     Exception.
   */
  public static void finest(CustomLogLevel level, String msg, Exception e) {
    finest(level, msg, e, null);
  }

  /**
   * Writes a finest log entry. If finest isnt supported it gets delegated to an info log entry.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   * @param add   Additional information map.
   */
  public static void finest(CustomLogLevel level, String msg, Map<String, ?> add) {
    finest(level, msg, null, add);
  }

  /**
   * Writes a finest log entry without an exception. If finest isnt supported it gets delegated to an info log entry.
   *
   * @param level Loglevel.
   * @param msg   Custom message.
   */
  public static void finest(CustomLogLevel level, String msg) {
    finest(level, msg, null, null);
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
