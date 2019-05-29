package com.example.demo.log;

import org.springframework.util.ResourceUtils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Helperclass to the CustomLogger
 */
public final class CustomLoggerUtil {

  private CustomLoggerUtil() {
  }

  /**
   * Gets the value of a property from the customLog.property file in the resources directory.
   *
   * @param name Name of the properity.
   * @return Value of the property.
   */
  public static String getProp(String name) {
    Properties prop = new Properties();
    try (FileInputStream input = new FileInputStream(ResourceUtils.getFile("classpath:customLog.properties"))) {
      prop.load(input);
    } catch (IOException e) {
      return null;
    }
    return prop.getProperty(name);
  }
}
