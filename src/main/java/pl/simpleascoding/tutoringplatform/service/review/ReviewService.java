package pl.simpleascoding.tutoringplatform.service.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.simpleascoding.tutoringplatform.dto.CreateReviewDTO;
import pl.simpleascoding.tutoringplatform.dto.UpdateReviewDTO;
import pl.simpleascoding.tutoringplatform.dto.ReviewDTO;

public interface ReviewService {

    String createReview(CreateReviewDTO dto, String authorUsername);

    Page<ReviewDTO> getReceivedReviewsForUser(long id, Pageable pageable);

    Page<ReviewDTO> getPostedReviewsForUser(long id, Pageable pageable);

    String updateReview(UpdateReviewDTO dto, String username, long reviewId);

    String deleteReview(String username, long reviewId);
}
