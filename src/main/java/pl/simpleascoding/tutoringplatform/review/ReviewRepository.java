package pl.simpleascoding.tutoringplatform.review;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.simpleascoding.tutoringplatform.review.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findReviewsByAuthorId(long authorId, Pageable pageable);

    Page<Review> findReviewsByReceiverId(long authorId, Pageable pageable);
}
