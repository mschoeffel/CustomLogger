# Custom Logger
This is a simpe, minimal, custom logger to log easily errors, exceptions or custom messages either in Spring or in a native application.\
Everything is editable from a simple properties file. 

## Components
- CustomLogger
- CustomLoggerUtil
- CustomLogLevel
- customLog.properties

### CustomLogger
This is the main class where all the magic is happening. It's a simple static class that handles the logging and message generation.

### CustomLoggerUtil
This is a helper class for the main CustomLogger. This class provides some special methods like easy reading from a property file.

### CustomLogLevel
This is an enumeration, that represents all the possible level of an error or exception. This class can edited like you need it for your own project, like adding new levels or changing levels etc.

### customLog.properties
In this propertiesfile you can configure all the Textsamples of the actual log message. So there you can set if the time should be logged or not or if you want a specific beginning text etc.

## Usage
To use the Logger there are two examples in the App and DemoApplication. (native and Spring)\
Its just a one liner to create a very richt log entry:
```java
CustomLogger.warning(CustomLogLevel.HIGH, "This is just a testlog", new RuntimeException("Runtime stopped because of John"));
```
This for example creates a warning log entry with the level "High" the text "This is just a testlog" and the RuntimeException "Runtime stopped because of John".

With the default config the result will be native:
```text
WARNUNG: 2019-05-29 11:01:57 - [COMPANYNAME] -HIGH- -this is just a testlog [END]
ERROR:
  java.lang.RuntimeException: Runtime stopped because of John
STACK:
  at com.example.demo.App.main(App.java:9)
```
And Spring:
```text
2019-05-29 11:28:59.247  WARN 5884 --- [           main] com.example.demo.log.CustomLogger        : 2019-05-29 11:28:59 - [COMPANYNAME] -HIGH- This is just a testlog [END]
ERROR:
  java.lang.RuntimeException: Runtime stopped because of John
STACK:
  at com.example.demo.DemoApplication.doSomethingAfterStartup(DemoApplication.java:23)
  at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
  at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
  at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
  at java.lang.reflect.Method.invoke(Method.java:498)
```

**Or, if you want to log even more Information in a simple and visual prepared way:**
You kann add a Map to the logger and the additional information will be logged like a table.
```java
Map<String, String> add = new LinkedHashMap<>();
add.put("PARAM1", "Value to param 1");
add.put("PARAM2", "Some other value (to param 2)");
add.put("LastParam", "LastValue");
CustomLogger.warning(CustomLogLevel.MEDIUM, "This is just a testlog", new RuntimeException("Runtime stopped because of John"), add);
```
This will result in the following output in native Java:
```text
WARNUNG: 2019-07-30 11:17:49 - [BEGIN|COMPANYNAME] -HIGH- This is just a testlog [END]
ADDITIONAL:
  PARAM1: Value to param 1
  PARAM2: Some other value (to param 2)
  LastParam: LastValue
ERROR:
  java.lang.RuntimeException: Runtime stopped because of John
STACK:
  com.example.demo.App.main(App.java:21)
```
Or Spring:
```text
2019-07-30 11:18:22.921  WARN 5592 --- [           main] com.example.demo.log.CustomLogger        : 2019-07-30 11:18:22 - [BEGIN|COMPANYNAME] -MEDIUM- This is just a testlog [END]
ADDITIONAL:
  PARAM1: Value to param 1
  PARAM2: Some other value (to param 2)
  LastParam: LastValue
ERROR:
  java.lang.RuntimeException: Runtime stopped because of John
STACK:
  com.example.demo.DemoApplication.doSomethingAfterStartup(DemoApplication.java:30)
  sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
  sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
  sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
  java.lang.reflect.Method.invoke(Method.java:498)
```

## Conclusion

Everything is editable from a simple properties file. For example you can modify the `[COMPANYNAME]`, the seperators between the informations, the `ERROR:`, set if you want the time to get logged and in which pattern or set seperatly if you want the exception to be logged etc. etc.

Just take a look inside the code everything is commented very well and all the configurations in the propertiesfile are commented and described. So this logger is very intuitive and easy to use.

Note: If you don't want to rely on the Spring Framework as dependency you have to change the Util class to resolve the resources path correctly, because at the moment this relys on a Spring dependency.

Feel free to modify, so it fits your needs best.

&copy; @mschoeffel
