package pl.simpleascoding.tutoringplatform.service.advertisement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.simpleascoding.tutoringplatform.domain.advertisement.Advertisement;
import pl.simpleascoding.tutoringplatform.dto.AdvertisementDTO;

@Mapper(componentModel = "spring")
public interface AdvertisementModelMapper {

    @Mapping(source = "author.username", target = "authorUsername")
    AdvertisementDTO mapAdvertisementEntityToAdvertisementDTO(Advertisement entity);

}
