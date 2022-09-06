package pl.simpleascoding.tutoringplatform.dto;

import pl.simpleascoding.tutoringplatform.domain.advertisement.AdvertisementCategory;

public record CreateAdvertisementDTO(AdvertisementCategory category, String author, String title, String description,
                                     int costPerHour) {
}
