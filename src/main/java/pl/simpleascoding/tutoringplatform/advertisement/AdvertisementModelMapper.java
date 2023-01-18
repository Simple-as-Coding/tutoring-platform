package pl.simpleascoding.tutoringplatform.advertisement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import pl.simpleascoding.tutoringplatform.advertisement.dto.Advertisement;

@Mapper(componentModel = "spring")
interface AdvertisementModelMapper {

    @Mapping(source = "author.username", target = "authorUsername")
    Advertisement mapAdvertisementEntityToAdvertisementDTO(pl.simpleascoding.tutoringplatform.advertisement.Advertisement entity);

}
