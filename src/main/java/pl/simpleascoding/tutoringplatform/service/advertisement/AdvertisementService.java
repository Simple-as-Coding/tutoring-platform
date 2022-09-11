package pl.simpleascoding.tutoringplatform.service.advertisement;

import pl.simpleascoding.tutoringplatform.dto.AdvertisementDTO;
import pl.simpleascoding.tutoringplatform.dto.CreateAdvertisementDTO;
import pl.simpleascoding.tutoringplatform.dto.RscpDTO;

public interface AdvertisementService {
    
    RscpDTO<AdvertisementDTO> createAdvertisement(CreateAdvertisementDTO requestDTO);

}
