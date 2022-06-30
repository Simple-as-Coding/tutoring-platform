package pl.simpleascoding.tutoringplatform.domain.token;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.simpleascoding.tutoringplatform.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "token")
@NoArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private TokenType type;

    private String value;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private User user;

    private LocalDateTime createdAt;
    private LocalDateTime expiresAt;
    private LocalDateTime confirmedAt;

    public Token(String value, TokenType type) {
        this.value = value;
        this.type = type;
        this.createdAt = LocalDateTime.now();
        this.expiresAt = createdAt.plusMinutes(15);
    }

    public void confirm() {
        this.confirmedAt = LocalDateTime.now();
    }

}
