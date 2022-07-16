package pl.simpleascoding.tutoringplatform.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import pl.simpleascoding.tutoringplatform.domain.review.Review;

public interface ReviewRepository extends JpaRepository<Review, Long> {
    Page<Review> findReviewsByAuthorId(Long authorId, Pageable pageable);

    Page<Review> findReviewsByReceiverId(Long authorId, Pageable pageable);
}
