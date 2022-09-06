package pl.simpleascoding.tutoringplatform.service.advertisement;

import org.mapstruct.Mapper;
import pl.simpleascoding.tutoringplatform.domain.advertisement.Advertisement;
import pl.simpleascoding.tutoringplatform.dto.AdvertisementDTO;

@Mapper(componentModel = "spring")
public interface AdvertisementModelMapper {
    AdvertisementDTO mapAdvertisementEntityToAdvertisementDTO(Advertisement entity);
}
