package com.example.demo.log;

/**
 * Class that represents the different level of errors.
 * Can easily extended or modified with other or new level of errors.
 */
public enum CustomLogLevel {

  //Level with int and text representation:
  //The higher the int representation the more important the error level is.
  //The text representation will be shown in the log entry.
  INFO(1, "-INFO-"),
  LOW(2, "-LOW-"),
  MEDIUM(3, "-MEDIUM-"),
  HIGH(4, "-HIGH-"),
  UNDEFINED(0, "-UNDEFINED-");

  private final int level;
  private final String text;

  CustomLogLevel(int level, String text) {
    this.level = level;
    this.text = text;
  }

  /**
   * Returns the text representation of a level.
   *
   * @return Text representation.
   */
  public String text() {
    return text;
  }

  /**
   * Returns the int representation of a level.
   *
   * @return Int representation.
   */
  public int level() {
    return level;
  }
}
