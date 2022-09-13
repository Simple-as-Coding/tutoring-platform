package pl.simpleascoding.tutoringplatform.service.advertisement;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional
public class AdvertisementServiceImpl implements AdvertisementService {

    private final AdvertisementRepository advertisementRepository;

    private final AdvertisementModelMapper advertisementModelMapper;

    private final UserService userService;

    @Override
    public RscpDTO<AdvertisementDTO> createAdvertisement(CreateAdvertisementDTO requestDTO) {
        Date date = new Date();
        User author = getAuthorEntity(requestDTO);
        isUserSignedAsTeacher(author);
        AdvertisementCategory category = getAdvertisementCategory(requestDTO);
        Advertisement advertisement = createAdvertisementEntity(requestDTO, author, category, date);
        AdvertisementDTO advertisementDTO = advertisementModelMapper.mapAdvertisementEntityToAdvertisementDTO(advertisement);
        advertisementRepository.save(advertisement);

        return new RscpDTO<>(RscpStatus.CREATED, "Advertisement created", advertisementDTO);
    }

    @Override
    public RscpDTO<Page<AdvertisementDTO>> getUsersAdvertisements(String username, Pageable pageable) {
        return new RscpDTO<>(RscpStatus.OK, "Advertisement history found", advertisementRepository.findAllByAuthor_NameOrderByCreationDateDesc(username, pageable).map(advertisementModelMapper::mapAdvertisementEntityToAdvertisementDTO));
    }

    private void isUserSignedAsTeacher(User author) {
        if (!author.getRoles().contains(RoleType.TEACHER)) {
            throw new UserIsNotATeacherException();
        }
    }

    private Advertisement createAdvertisementEntity(CreateAdvertisementDTO requestDTO, User author, AdvertisementCategory category, Date date) {
        return new Advertisement(category, author, requestDTO.title(), requestDTO.description(), date.toInstant(), requestDTO.costPerHour());
    }

    private AdvertisementCategory getAdvertisementCategory(CreateAdvertisementDTO requestDTO) {
        return requestDTO.category();
    }

    private User getAuthorEntity(CreateAdvertisementDTO requestDTO) {
        return userService.getUserByUsername(requestDTO.author());
    }
}
