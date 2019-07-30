package com.example.demo;

import com.example.demo.log.CustomLogLevel;
import com.example.demo.log.CustomLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.LinkedHashMap;
import java.util.Map;

@SpringBootApplication
public class DemoApplication {

  public static void main(String[] args) {
    SpringApplication.run(DemoApplication.class, args);
  }

  /**
   * Demo method in Sring
   */
  @EventListener(ApplicationReadyEvent.class)
  public void doSomethingAfterStartup() {
    //Demo call of the cusom logger:
    Map<String, String> add = new LinkedHashMap<>();
    add.put("PARAM1", "Value to param 1");
    add.put("PARAM2", "Some other value (to param 2)");
    add.put("LastParam", "LastValue");
    CustomLogger.warning(CustomLogLevel.HIGH, "This is just a testlog", new RuntimeException("Runtime stopped because of John"), add);
  }

}
