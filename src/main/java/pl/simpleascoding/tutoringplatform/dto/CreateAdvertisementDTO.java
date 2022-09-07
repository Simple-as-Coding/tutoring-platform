package pl.simpleascoding.tutoringplatform.dto;

import pl.simpleascoding.tutoringplatform.domain.advertisement.AdvertisementCategory;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public record CreateAdvertisementDTO(@NotNull(message = "Category could not be null") AdvertisementCategory category,
                                     @NotNull(message = "Author could not be null") String author,
                                     @NotNull(message = "Title could not be null") String title,
                                     @NotNull(message = "Description could not be null") String description,
                                     @NotNull(message = "Cost per hour could not be null") BigDecimal costPerHour) {
}
