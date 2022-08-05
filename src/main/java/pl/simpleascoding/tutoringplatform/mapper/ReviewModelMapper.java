package pl.simpleascoding.tutoringplatform.mapper;

import org.mapstruct.Mapper;
import pl.simpleascoding.tutoringplatform.domain.review.Review;
import pl.simpleascoding.tutoringplatform.dto.ReviewDTO;

@Mapper(componentModel = "spring")
public interface ReviewModelMapper {

    ReviewDTO mapReviewToDto(Review review);

}
