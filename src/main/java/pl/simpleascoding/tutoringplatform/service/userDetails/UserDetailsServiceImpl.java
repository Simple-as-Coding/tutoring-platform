package pl.simpleascoding.tutoringplatform.service.userDetails;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pl.simpleascoding.tutoringplatform.repository.UserDetailsImplRepository;

@RequiredArgsConstructor
@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserDetailsImplRepository userDetailsImplRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userDetailsImplRepository.findByUser_Username(username).orElseThrow(() -> new UsernameNotFoundException("User with username \"" + username + "\" not found"));
    }

}