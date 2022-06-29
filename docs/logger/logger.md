# Logger

## Log4j 2

### This table below should guide you on which log4j2 level should be used in which case.

| Level  | Log Event Level                                                                         |
|--------|-----------------------------------------------------------------------------------------|
| OFF    | When no events will be logged                                                           |
| FATAL  | When a severe error will prevent the application from continuing                        |
| ERROR  | When an error in the application, possibly recoverable                                  |
| WARN   | When an event that might possible lead to an error                                      |
| INFO   | When an event for informational purposes                                                |
| DEBUG  | When a general debugging event required                                                 |
| TRACE  | When a fine grained debug message, typically capturing the flow through the application |
| ALL    | When all events should be logged                                                        |

### We mainly have 3 components to work with Log4j

**Logger**: It is used to log the messages.  
**Appender**: It is used to publish the logging information to the destination like the file, database, console etc.  
**Layout**: It is used to format logging information in different styles.

### Create a Log Directory

Create directories for the log files.

Oracle recommends the directory/file permission should be (rw-,r---). Only the install owner and group should be allowed
to read these files due to the sensitive nature of the information that could be contained within them.

### Pre-requisites for Email Alerts

For email alerts to work, third-party libraries must be copied into the WEB-INF/lib folder.
The required files are _activation.jar_, which can be downloaded from

    http://java.sun.com/javase/technologies/desktop/javabeans/jaf/index.jsp

_mail.jar_, which can be downloaded from

    http://java.sun.com/products/javamail/
