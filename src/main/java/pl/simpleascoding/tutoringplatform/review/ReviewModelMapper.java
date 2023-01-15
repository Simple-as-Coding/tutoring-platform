package pl.simpleascoding.tutoringplatform.review;

import org.mapstruct.Mapper;
import pl.simpleascoding.tutoringplatform.review.dto.ReviewDTO;

@Mapper(componentModel = "spring")
interface ReviewModelMapper {

    ReviewDTO mapReviewToDto(Review review);

}
