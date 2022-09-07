package pl.simpleascoding.tutoringplatform.domain.advertisement;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.simpleascoding.tutoringplatform.domain.user.User;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

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
    @Min(value = 10)
    @Max(value = 50)
    private String title;

    @NotNull(message = "Description could not be empty")
    @Min(value = 50)
    @Max(value = 255)
    private String description;

    @NotNull
    private int costPerHour;

    public Advertisement(AdvertisementCategory category, User author, String title, String description, int costPerHour) {
        this.category = category;
        this.author = author;
        this.title = title;
        this.description = description;
        this.costPerHour = costPerHour;
    }
}
