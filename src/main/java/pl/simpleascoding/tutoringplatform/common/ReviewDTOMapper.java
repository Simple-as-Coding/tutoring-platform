package pl.simpleascoding.tutoringplatform.common;

import pl.simpleascoding.tutoringplatform.domain.review.Review;
import pl.simpleascoding.tutoringplatform.dto.ReviewDTO;

public final class ReviewDTOMapper {
    private ReviewDTOMapper() {
    }

    public static ReviewDTO map(Review review) {
        return new ReviewDTO(review.getId(),
                review.getContent(),
                review.getStars(),
                UserDTOMapper.map(review.getAuthor()),
                UserDTOMapper.map(review.getReceiver()),
                review.getCreatedAt());
    }
}
