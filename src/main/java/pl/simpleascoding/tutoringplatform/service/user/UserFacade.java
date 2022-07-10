package pl.simpleascoding.tutoringplatform.service.user;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;
import pl.simpleascoding.tutoringplatform.dto.CreateUserDTO;
import pl.simpleascoding.tutoringplatform.dto.GeneralDTO;

@Service
@RequiredArgsConstructor
public class UserFacade {

	private final UserServiceImpl userServiceImpl;

	public GeneralDTO createUser(CreateUserDTO dto) {

		return userServiceImpl.createUser(dto);

	}

}
