package pl.simpleascoding.tutoringplatform.service.review;

import org.mapstruct.Mapper;
import pl.simpleascoding.tutoringplatform.domain.review.Review;
import pl.simpleascoding.tutoringplatform.dto.ReviewDTO;

@Mapper(componentModel = "spring")
interface ReviewModelMapper {

    ReviewDTO mapReviewToDto(Review review);

}
