package pl.simpleascoding.tutoringplatform.service.tutoring;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TutoringFacade {

    private final TutoringServiceImpl tutoringServiceImpl;

}
