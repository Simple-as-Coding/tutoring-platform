package pl.simpleascoding.tutoringplatform.advertisement;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.simpleascoding.tutoringplatform.advertisement.dto.Advertisement;
import pl.simpleascoding.tutoringplatform.advertisement.dto.CreateAdvertisement;
import pl.simpleascoding.tutoringplatform.util.rscp.RscpDTO;

public interface AdvertisementService {

    RscpDTO<Advertisement> createAdvertisement(CreateAdvertisement requestDTO);

    RscpDTO<Page<Advertisement>> getUsersAdvertisements(String username, Pageable pageable);
}
