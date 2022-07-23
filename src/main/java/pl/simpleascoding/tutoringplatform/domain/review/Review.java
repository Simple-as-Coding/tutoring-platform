package pl.simpleascoding.tutoringplatform.domain.review;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.validator.constraints.Range;
import pl.simpleascoding.tutoringplatform.domain.user.User;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.Instant;
import java.util.Optional;

@Data
@Entity
@Table(name = "review")
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String content;

    @Range(min = 1, max = 5)
    private byte stars;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private User author;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private User receiver;

    @CreationTimestamp
    private Instant createdAt;

    public Review(String content, byte stars) {
        this.setContent(content);
        this.setStars(stars);
    }

    public void setContent(String content){
        this.content = Optional.ofNullable(content).orElse("").trim();
    }
}
