package pl.simpleascoding.tutoringplatform.review;

import org.mapstruct.Mapper;
import pl.simpleascoding.tutoringplatform.review.dto.Review;

@Mapper(componentModel = "spring")
interface ReviewModelMapper {

    Review mapReviewToDto(pl.simpleascoding.tutoringplatform.review.Review review);

}
