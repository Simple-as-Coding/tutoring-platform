package pl.simpleascoding.tutoringplatform.service.advertisement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.simpleascoding.tutoringplatform.dto.AdvertisementDTO;
import pl.simpleascoding.tutoringplatform.dto.CreateAdvertisementDTO;
import pl.simpleascoding.tutoringplatform.dto.RscpDTO;

public interface AdvertisementService {

    RscpDTO<AdvertisementDTO> createAdvertisement(CreateAdvertisementDTO requestDTO);

    RscpDTO<Page<AdvertisementDTO>> getUsersAdvertisements(String username, Pageable pageable);
}
