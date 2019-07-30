package com.example.demo;

import com.example.demo.log.CustomLogLevel;
import com.example.demo.log.CustomLogger;

import java.util.LinkedHashMap;
import java.util.Map;

public class App {

  /**
   * Demo native method
   *
   * @param args Start arguments
   */
  public static void main(String[] args) {
    //Demo call of the cusom logger:
    Map<String, String> add = new LinkedHashMap<>();
    add.put("PARAM1", "Value to param 1");
    add.put("PARAM2", "Some other value (to param 2)");
    add.put("LastParam", "LastValue");
    CustomLogger.warning(CustomLogLevel.HIGH, "This is just a testlog", new RuntimeException("Runtime stopped because of John"), add);
  }

}
