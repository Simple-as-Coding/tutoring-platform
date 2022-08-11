package pl.simpleascoding.tutoringplatform.domain.user;

import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Objects;

@Data
@Entity
@NoArgsConstructor
@Table(name = "app_role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private RoleType roleType;

    @ManyToOne(cascade = {CascadeType.ALL})
    private User user;

    public Role(RoleType role) {
        this.roleType = role;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Role role = (Role) o;
        return roleType == role.roleType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(roleType);
    }
}
