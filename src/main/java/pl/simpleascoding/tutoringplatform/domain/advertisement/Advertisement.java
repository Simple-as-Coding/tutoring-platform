package pl.simpleascoding.tutoringplatform.domain.advertisement;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.simpleascoding.tutoringplatform.domain.user.User;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.math.BigDecimal;

@Data
@Entity
@Table(name = "advertisement")
@NoArgsConstructor
public class Advertisement {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Enumerated(EnumType.STRING)
    private AdvertisementCategory category;

    @NotNull
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private User author;

    @NotNull(message = "Title could not be empty")
    @Size(min = 10, max = 50, message = "Title has to be between 10 and 50 characters")
    private String title;

    @NotNull(message = "Description could not be empty")
    @Size(min = 50, max = 255, message = "Description has to be between 50 and 255 characters")
    private String description;

    @NotNull
    @Positive
    private BigDecimal costPerHour;

    public Advertisement(AdvertisementCategory category, User author, String title, String description, BigDecimal costPerHour) {
        this.category = category;
        this.author = author;
        this.title = title;
        this.description = description;
        this.costPerHour = costPerHour;
    }
}
