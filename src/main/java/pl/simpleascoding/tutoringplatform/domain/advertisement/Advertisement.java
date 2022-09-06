package pl.simpleascoding.tutoringplatform.domain.advertisement;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.simpleascoding.tutoringplatform.domain.user.User;

import javax.persistence.*;
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
    private AdvertisementCategory category;

    @NotNull
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private User author;

    @NotNull
    private String title;

    @NotNull
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
