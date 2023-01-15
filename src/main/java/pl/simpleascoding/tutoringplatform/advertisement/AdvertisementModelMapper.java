package pl.simpleascoding.tutoringplatform.advertisement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.simpleascoding.tutoringplatform.advertisement.dto.AdvertisementDTO;

@Mapper(componentModel = "spring")
public interface AdvertisementModelMapper {

    @Mapping(source = "author.username", target = "authorUsername")
    AdvertisementDTO mapAdvertisementEntityToAdvertisementDTO(Advertisement entity);

}
