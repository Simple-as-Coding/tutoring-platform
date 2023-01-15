package pl.simpleascoding.tutoringplatform.tutoring;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import pl.simpleascoding.tutoringplatform.user.User;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.Instant;

@Data
@Entity
@NoArgsConstructor
@Table(name = "tutoring_offer")
public class TutoringOffer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String description;

    private BigDecimal pricePerHour;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User addedBy;

    @Enumerated(EnumType.STRING)
    private OfferType offerType;

    @CreatedDate
    private Instant dateTimeAdded;
}
