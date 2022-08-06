package pl.simpleascoding.tutoringplatform;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@EnableScheduling
public class TutoringPlatformApplication {

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    private static final Logger LOGGER = LogManager.getLogger(TutoringPlatformApplication.class);

    private static final Marker APP_MARKER = MarkerManager.getMarker("application");

    public static void main(String[] args) {
        LOGGER.info(APP_MARKER, "APPLICATION_STARTED");
        SpringApplication.run(TutoringPlatformApplication.class, args);
    }

}
