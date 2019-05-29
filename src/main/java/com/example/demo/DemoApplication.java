package com.example.demo;

import com.example.demo.log.CustomLogLevel;
import com.example.demo.log.CustomLogger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

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
    CustomLogger.warning(CustomLogLevel.HIGH, "This is just a testlog", new RuntimeException("Runtime stopped because of John"));
  }

}
