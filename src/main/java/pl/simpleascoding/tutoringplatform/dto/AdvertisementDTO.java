package pl.simpleascoding.tutoringplatform.dto;

import java.math.BigDecimal;

public record AdvertisementDTO(String category, String authorUsername, String title, String description,
                               BigDecimal costPerHour) {
}
