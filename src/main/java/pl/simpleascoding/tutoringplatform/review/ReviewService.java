package pl.simpleascoding.tutoringplatform.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import pl.simpleascoding.tutoringplatform.review.dto.CreateReview;
import pl.simpleascoding.tutoringplatform.review.dto.Review;
import pl.simpleascoding.tutoringplatform.review.dto.UpdateReview;
import pl.simpleascoding.tutoringplatform.util.rscp.RscpDTO;

public interface ReviewService {

    RscpDTO<Review> createReview(CreateReview dto, String authorUsername);

    RscpDTO<Page<Review>> getReceivedReviewsForUser(long id, Pageable pageable);

    RscpDTO<Page<Review>> getPostedReviewsForUser(long id, Pageable pageable);

    RscpDTO<Review> updateReview(UpdateReview dto, String username, long reviewId);

    RscpDTO<?> deleteReview(String username, long reviewId);
}
