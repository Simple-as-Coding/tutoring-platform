package pl.simpleascoding.tutoringplatform.advertisement.dto;

import java.math.BigDecimal;
import java.time.Instant;

public record Advertisement(String category, String authorUsername, String title, String description,
                            Instant creationDate,
                            BigDecimal costPerHour) {

}
