package pl.simpleascoding.tutoringplatform;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TutoringPlatformApplication {


    private static final Logger logger = LogManager.getLogger(TutoringPlatformApplication.class);

    private static final Marker APP_MARKER = MarkerManager.getMarker("application");

    public static void main(String[] args) {
        logger.info(APP_MARKER, "APPLICATION_STARTED");
        SpringApplication.run(TutoringPlatformApplication.class, args);
    }

}
