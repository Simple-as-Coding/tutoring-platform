package pl.simpleascoding.tutoringplatform.service.user;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.simpleascoding.tutoringplatform.domain.user.Role;
import pl.simpleascoding.tutoringplatform.domain.user.RoleType;
import pl.simpleascoding.tutoringplatform.domain.user.User;
import pl.simpleascoding.tutoringplatform.dto.CreateUserDTO;
import pl.simpleascoding.tutoringplatform.exception.UsernameTakenException;
import pl.simpleascoding.tutoringplatform.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public String createUser(CreateUserDTO dto) {
        userRepository.findByUsername(dto.username()).ifPresent(user -> {
            throw new UsernameTakenException(dto.username());
        });
        User user = new User(dto.username(), dto.password(), dto.name(), dto.surname());
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user.getRoles().add(new Role(RoleType.USER));
        userRepository.save(user);
        return "SUCCESS";
    }

}
