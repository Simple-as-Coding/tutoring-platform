package pl.simpleascoding.tutoringplatform.advertisement;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import pl.simpleascoding.tutoringplatform.advertisement.dto.Advertisement;
import pl.simpleascoding.tutoringplatform.advertisement.dto.CreateAdvertisement;
import pl.simpleascoding.tutoringplatform.user.RoleType;
import pl.simpleascoding.tutoringplatform.user.User;
import pl.simpleascoding.tutoringplatform.util.rscp.RscpDTO;
import pl.simpleascoding.tutoringplatform.user.exception.UserIsNotATeacherException;
import pl.simpleascoding.tutoringplatform.util.rscp.RscpStatus;
import pl.simpleascoding.tutoringplatform.user.UserService;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementRepository advertisementRepository;

    private final AdvertisementModelMapper advertisementModelMapper;

    private final UserService userService;

    @Override
    public RscpDTO<Advertisement> createAdvertisement(CreateAdvertisement requestDTO) {
        User author = getAuthorEntity(requestDTO);
        isUserSignedAsTeacher(author);
        AdvertisementCategory category = getAdvertisementCategory(requestDTO);
        pl.simpleascoding.tutoringplatform.advertisement.Advertisement advertisement = createAdvertisementEntity(requestDTO, author, category);
        advertisementRepository.save(advertisement);
        Advertisement advertisementDTO = advertisementModelMapper.mapAdvertisementEntityToAdvertisementDTO(advertisement);

        return new RscpDTO<>(RscpStatus.CREATED, "Advertisement created", advertisementDTO);
    }

    @Override
    public RscpDTO<Page<Advertisement>> getUsersAdvertisements(String username, Pageable pageable) {
        return new RscpDTO<>(RscpStatus.OK, "Advertisement history found", advertisementRepository.findAllByAuthor_NameOrderByCreationDateDesc(username, pageable).map(advertisementModelMapper::mapAdvertisementEntityToAdvertisementDTO));
    }

    private void isUserSignedAsTeacher(User author) {
        if (!author.getRoles().contains(RoleType.TEACHER)) {
            throw new UserIsNotATeacherException();
        }
    }

    private pl.simpleascoding.tutoringplatform.advertisement.Advertisement createAdvertisementEntity(CreateAdvertisement requestDTO, User author, AdvertisementCategory category) {
        return new pl.simpleascoding.tutoringplatform.advertisement.Advertisement(category, author, requestDTO.title(), requestDTO.description(), requestDTO.costPerHour());
    }

    private AdvertisementCategory getAdvertisementCategory(CreateAdvertisement requestDTO) {
        return requestDTO.category();
    }

    private User getAuthorEntity(CreateAdvertisement requestDTO) {
        return userService.getUserByUsername(requestDTO.author());
    }
}
