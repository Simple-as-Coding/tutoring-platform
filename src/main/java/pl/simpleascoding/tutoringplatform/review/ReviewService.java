package pl.simpleascoding.tutoringplatform.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.simpleascoding.tutoringplatform.review.dto.CreateReviewDTO;
import pl.simpleascoding.tutoringplatform.review.dto.ReviewDTO;
import pl.simpleascoding.tutoringplatform.review.dto.UpdateReviewDTO;
import pl.simpleascoding.tutoringplatform.rscp.RscpDTO;

public interface ReviewService {

    RscpDTO<ReviewDTO> createReview(CreateReviewDTO dto, String authorUsername);

    RscpDTO<Page<ReviewDTO>> getReceivedReviewsForUser(long id, Pageable pageable);

    RscpDTO<Page<ReviewDTO>> getPostedReviewsForUser(long id, Pageable pageable);

    RscpDTO<ReviewDTO> updateReview(UpdateReviewDTO dto, String username, long reviewId);

    RscpDTO<?> deleteReview(String username, long reviewId);
}
