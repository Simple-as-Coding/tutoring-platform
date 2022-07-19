package pl.simpleascoding.tutoringplatform.service.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.simpleascoding.tutoringplatform.dto.CreateReviewDTO;
import pl.simpleascoding.tutoringplatform.dto.UpdateReviewDTO;
import pl.simpleascoding.tutoringplatform.dto.ReviewDTO;

public interface ReviewService {

    String createReview(CreateReviewDTO dto, String authorUsername);

    Page<ReviewDTO> getReceivedReviewsForUser(Long id, Pageable pageable);

    Page<ReviewDTO> getPostedReviewsForUser(Long id, Pageable pageable);

    String updateReview(UpdateReviewDTO dto, String username, Long reviewId);

    String deleteReview(String username, Long reviewId);
}
