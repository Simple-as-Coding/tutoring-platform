package pl.simpleascoding.tutoringplatform.domain.userDetails;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import pl.simpleascoding.tutoringplatform.domain.user.User;

import javax.persistence.*;
import java.util.Collection;

@Data
@Entity
@Table(name = "app_user_details")
@NoArgsConstructor
public class UserDetailsImpl implements UserDetails {

    @Id
    private Long userId;

    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    private User user;

    public UserDetailsImpl(User user) {
        this.user = user;
    }

    private boolean isAccountNonExpired = true;
    private boolean isAccountNonLocked = true;
    private boolean isCredentialsNonExpired = true;
    private boolean isEnabled = true;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return user.getRoles().stream().map(role -> new SimpleGrantedAuthority(role.getName())).toList();
    }

    public User getUser() {
        return user;
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

}
