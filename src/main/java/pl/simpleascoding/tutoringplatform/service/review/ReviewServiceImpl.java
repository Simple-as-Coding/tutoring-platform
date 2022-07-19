package pl.simpleascoding.tutoringplatform.service.review;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.simpleascoding.tutoringplatform.common.ReviewDTOMapper;
import pl.simpleascoding.tutoringplatform.domain.review.Review;
import pl.simpleascoding.tutoringplatform.domain.user.User;
import pl.simpleascoding.tutoringplatform.dto.CreateReviewDTO;
import pl.simpleascoding.tutoringplatform.dto.UpdateReviewDTO;
import pl.simpleascoding.tutoringplatform.dto.ReviewDTO;
import pl.simpleascoding.tutoringplatform.exception.ReviewNotFoundException;
import pl.simpleascoding.tutoringplatform.exception.UserNotFoundException;
import pl.simpleascoding.tutoringplatform.repository.ReviewRepository;
import pl.simpleascoding.tutoringplatform.service.user.UserService;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final UserService userService;
    private final ReviewRepository reviewRepository;

    @Override
    @Transactional
    public String createReview(CreateReviewDTO dto, String authorUsername) {
        User receiver = userService.getUserById(dto.receiverId()).orElseThrow(() -> new UserNotFoundException(dto.receiverId()));
        User author = userService.getUserByUsername(authorUsername).orElseThrow(() -> new UsernameNotFoundException(authorUsername));

        Review review = new Review(dto.content(), dto.stars());

        author.getPostedReviews().add(review);
        receiver.getReceivedReviews().add(review);

        return HttpStatus.CREATED.getReasonPhrase();
    }

    @Override
    public Page<ReviewDTO> getReceivedReviewsForUser(Long id, Pageable pageable) {
        if (!userService.checkUserExists(id)) {
            throw new UserNotFoundException(id);
        }

        return reviewRepository.findReviewsByReceiverId(id, pageable).map(ReviewDTOMapper::map);
    }

    @Override
    public Page<ReviewDTO> getPostedReviewsForUser(Long id, Pageable pageable) {
        if (!userService.checkUserExists(id)) {
            throw new UserNotFoundException(id);
        }

        return reviewRepository.findReviewsByAuthorId(id, pageable).map(ReviewDTOMapper::map);
    }

    @Override
    @Transactional
    public String updateReview(UpdateReviewDTO dto, String username, Long reviewId) {
        User author = userService.getUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException(reviewId));

        if(!review.getAuthor().equals(author)){
            return HttpStatus.UNAUTHORIZED.getReasonPhrase();
        }

        review.setStars(dto.stars());
        review.setContent(Optional.ofNullable(dto.content()).orElse(""));

        return HttpStatus.OK.getReasonPhrase();
    }

    @Override
    public String deleteReview(String username, Long reviewId) {
        User author = userService.getUserByUsername(username).orElseThrow(() -> new UsernameNotFoundException(username));
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException(reviewId));

        if(!review.getAuthor().equals(author)){
            return HttpStatus.UNAUTHORIZED.getReasonPhrase();
        }

        reviewRepository.delete(review);

        return HttpStatus.OK.getReasonPhrase();
    }

}
