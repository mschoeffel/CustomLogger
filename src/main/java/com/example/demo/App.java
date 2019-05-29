package com.example.demo;

import com.example.demo.log.CustomLogLevel;
import com.example.demo.log.CustomLogger;

public class App {

  /**
   * Demo native method
   * @param args Start arguments
   */
  public static void main(String[] args){
    //Demo call of the cusom logger:
    CustomLogger.warning(CustomLogLevel.HIGH, "This is just a testlog", new RuntimeException("Runtime stopped because of John"));
  }

}
