#Custom Logger
This is a simpe, minimal, custom logger to log easily errors, exceptions or custom messages either in Spring or in a native application.

##Components
- CustomLogger
- CustomLoggerUtil
- CustomLogLevel
- customLog.properties

###CustomLogger
This is the main class where all the magic is happening. It's a simple static class that handles the logging and message generation.

###CustomLoggerUtil
This is a helper class for the main CustomLogger. This class provides some special methods like easy reading from a property file.

###CustomLogLevel
This is an enumeration, that represents all the possible level of an error or exception. This class can edited like you need it for your own project, like adding new levels or changing levels etc.

###customLog.properties
In this propertiesfile you can configure all the Textsamples of the actual log message. So there you can set if the time should be logged or not or if you want a specific beginning text etc.

##Usage
To use the Logger there are two examples in the App and DemoApplication. (native and Spring)\
Its just a oe liner to create a very richt log entry:
```java
CustomLogger.warning(CustomLogLevel.HIGH, "This is just a testlog", new RuntimeException("Runtime stopped because of John"));
```
This for example creates a warning log entry with the level "High" the text "This is just a testlog" and the RuntimeException "Runtime stopped because of John".

With the default config the result will be native:
```text
WARNUNG: 2019-05-29 11:01:57 - [COMPANYNAME] -HIGH- -this is just a testlog [END]
ERROR:
  Runtime stopped because of John
STACK:
  java.lang.RuntimeException: Runtime stopped because of John
	at com.example.demo.App.main(App.java:9)
```
And Spring:
```text
2019-05-29 11:28:59.247  WARN 5884 --- [           main] com.example.demo.log.CustomLogger        : 2019-05-29 11:28:59 - [COMPANYNAME] -HIGH- This is just a testlog [END]
ERROR:
  Runtime stopped because of John
STACK:
  java.lang.RuntimeException: Runtime stopped because of John
	at com.example.demo.DemoApplication.doSomethingAfterStartup(DemoApplication.java:23)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.springframework.context.event.ApplicationListenerMethodAdapter.doInvoke(ApplicationListenerMethodAdapter.java:261)
	at org.springframework.context.event.ApplicationListenerMethodAdapter.processEvent(ApplicationListenerMethodAdapter.java:179)
	at org.springframework.context.event.ApplicationListenerMethodAdapter.onApplicationEvent(ApplicationListenerMethodAdapter.java:142)
	at org.springframework.context.event.SimpleApplicationEventMulticaster.doInvokeListener(SimpleApplicationEventMulticaster.java:172)
	at org.springframework.context.event.SimpleApplicationEventMulticaster.invokeListener(SimpleApplicationEventMulticaster.java:165)
	at org.springframework.context.event.SimpleApplicationEventMulticaster.multicastEvent(SimpleApplicationEventMulticaster.java:139)
	at org.springframework.context.support.AbstractApplicationContext.publishEvent(AbstractApplicationContext.java:402)
	at org.springframework.context.support.AbstractApplicationContext.publishEvent(AbstractApplicationContext.java:359)
	at org.springframework.boot.context.event.EventPublishingRunListener.running(EventPublishingRunListener.java:105)
	at org.springframework.boot.SpringApplicationRunListeners.running(SpringApplicationRunListeners.java:78)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:332)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1260)
	at org.springframework.boot.SpringApplication.run(SpringApplication.java:1248)
	at com.example.demo.DemoApplication.main(DemoApplication.java:14)
```

But for example you can modify the `[COMPANYNAME]`, the seperators between the informations, the `ERROR:`, set if you want the time to get logged and in which pattern or set seperatly if you want the exception to be logged etc. etc.\

Just take a look inside the code everything is commented well and all the configurations in the propertiesfile are commented and described. So it's very intuitive and easy to use this Logger.

Feel free to modify, so it fits your needs best.

@mschoeffel
