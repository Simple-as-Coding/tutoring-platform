package pl.simpleascoding.tutoringplatform.service.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.simpleascoding.tutoringplatform.dto.CreateReviewDTO;
import pl.simpleascoding.tutoringplatform.dto.ReviewDTO;
import pl.simpleascoding.tutoringplatform.dto.RscpDTO;
import pl.simpleascoding.tutoringplatform.dto.UpdateReviewDTO;

public interface ReviewService {

    RscpDTO<ReviewDTO> createReview(CreateReviewDTO dto, String authorUsername);

    RscpDTO<Page<ReviewDTO>> getReceivedReviewsForUser(long id, Pageable pageable);

    RscpDTO<Page<ReviewDTO>> getPostedReviewsForUser(long id, Pageable pageable);

    RscpDTO<ReviewDTO> updateReview(UpdateReviewDTO dto, String username, long reviewId);

    RscpDTO<?> deleteReview(String username, long reviewId);
}
