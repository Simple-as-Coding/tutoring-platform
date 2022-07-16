package pl.simpleascoding.tutoringplatform.service.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.simpleascoding.tutoringplatform.dto.CreateUpdateReviewDTO;
import pl.simpleascoding.tutoringplatform.dto.ReviewDTO;

public interface ReviewService {

    String createReview(CreateUpdateReviewDTO dto, String authorUsername, Long receiverId);

    Page<ReviewDTO> getReceivedReviewsForUser(Long id, Pageable pageable);

    Page<ReviewDTO> getPostedReviewsForUser(Long id, Pageable pageable);

}
