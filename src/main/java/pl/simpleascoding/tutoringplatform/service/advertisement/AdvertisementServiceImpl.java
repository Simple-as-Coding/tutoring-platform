package pl.simpleascoding.tutoringplatform.service.advertisement;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import pl.simpleascoding.tutoringplatform.domain.advertisement.Advertisement;
import pl.simpleascoding.tutoringplatform.domain.advertisement.AdvertisementCategory;
import pl.simpleascoding.tutoringplatform.domain.user.RoleType;
import pl.simpleascoding.tutoringplatform.domain.user.User;
import pl.simpleascoding.tutoringplatform.dto.AdvertisementDTO;
import pl.simpleascoding.tutoringplatform.dto.CreateAdvertisementDTO;
import pl.simpleascoding.tutoringplatform.dto.RscpDTO;
import pl.simpleascoding.tutoringplatform.exception.UserIsNotATeacherException;
import pl.simpleascoding.tutoringplatform.repository.AdvertisementRepository;
import pl.simpleascoding.tutoringplatform.rscp.RscpStatus;
import pl.simpleascoding.tutoringplatform.service.user.UserService;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementRepository advertisementRepository;

    private final AdvertisementModelMapper advertisementModelMapper;

    private final UserService userService;

    @Override
    public RscpDTO<AdvertisementDTO> createAdvertisement(CreateAdvertisementDTO requestDTO) {
        User author = getAuthorEntity(requestDTO);
        isUserSignedAsTeacher(author);
        AdvertisementCategory category = getAdvertisementCategory(requestDTO);
        Advertisement advertisement = createAdvertisementEntity(requestDTO, author, category);
        AdvertisementDTO advertisementDTO = advertisementModelMapper.mapAdvertisementEntityToAdvertisementDTO(advertisement);
        advertisementRepository.save(advertisement);

        return new RscpDTO<>(RscpStatus.CREATED, "Advertisement created", advertisementDTO);
    }

    private void isUserSignedAsTeacher(User author) {
        if (!author.getRoles().contains(RoleType.TEACHER)) {
            throw new UserIsNotATeacherException();
        }
    }

    private Advertisement createAdvertisementEntity(CreateAdvertisementDTO requestDTO, User author, AdvertisementCategory category) {
        return new Advertisement(category, author, requestDTO.title(), requestDTO.description(), requestDTO.costPerHour());
    }

    private AdvertisementCategory getAdvertisementCategory(CreateAdvertisementDTO requestDTO) {
        return requestDTO.category();
    }

    private User getAuthorEntity(CreateAdvertisementDTO requestDTO) {
        return userService.getUserByUsername(requestDTO.author());
    }
}
