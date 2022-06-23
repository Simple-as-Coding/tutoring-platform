package pl.simpleascoding.tutoringplatform.domain.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;


@Data
@Entity
@NoArgsConstructor
@Table(name = "app_role")
public class Role {
    public static final String USER = "USER";
    public static final String ADMIN = "ADMIN";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    private User user;

    public Role(String name) {
        this.name = name;
    }

}

