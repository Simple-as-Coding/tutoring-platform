package pl.simpleascoding.tutoringplatform.domain.token;

import lombok.Data;
import lombok.NoArgsConstructor;
import pl.simpleascoding.tutoringplatform.domain.user.User;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "token")
@NoArgsConstructor
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private TokenType type;

    private String value = UUID.randomUUID().toString();

    private String data;

    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private User user;

    private LocalDateTime createdAt = LocalDateTime.now();
    private LocalDateTime expiresAt = createdAt.plusMinutes(15);
    private LocalDateTime confirmedAt;

    public Token(TokenType type) {
        this.type = type;
    }

    public void confirm() {
        this.confirmedAt = LocalDateTime.now();
    }

}
