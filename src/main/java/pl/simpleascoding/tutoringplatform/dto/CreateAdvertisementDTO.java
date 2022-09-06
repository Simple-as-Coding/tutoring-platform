package pl.simpleascoding.tutoringplatform.dto;

public record CreateAdvertisementDTO(String category, String author, String title, String description,
                                     int costPerHour) {
}
