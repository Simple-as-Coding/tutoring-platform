package pl.simpleascoding.tutoringplatform.advertisement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.simpleascoding.tutoringplatform.advertisement.dto.AdvertisementDTO;
import pl.simpleascoding.tutoringplatform.advertisement.dto.CreateAdvertisementDTO;
import pl.simpleascoding.tutoringplatform.rscp.RscpDTO;

public interface AdvertisementService {

    RscpDTO<AdvertisementDTO> createAdvertisement(CreateAdvertisementDTO requestDTO);

    RscpDTO<Page<AdvertisementDTO>> getUsersAdvertisements(String username, Pageable pageable);
}
