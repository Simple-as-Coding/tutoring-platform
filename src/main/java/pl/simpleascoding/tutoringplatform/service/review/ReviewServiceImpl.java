package pl.simpleascoding.tutoringplatform.service.review;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.simpleascoding.tutoringplatform.domain.review.Review;
import pl.simpleascoding.tutoringplatform.domain.user.User;
import pl.simpleascoding.tutoringplatform.dto.CreateReviewDTO;
import pl.simpleascoding.tutoringplatform.dto.ReviewDTO;
import pl.simpleascoding.tutoringplatform.dto.UpdateReviewDTO;
import pl.simpleascoding.tutoringplatform.exception.ReviewNotFoundException;
import pl.simpleascoding.tutoringplatform.exception.UserNotFoundException;
import pl.simpleascoding.tutoringplatform.mapper.ReviewModelMapper;
import pl.simpleascoding.tutoringplatform.repository.ReviewRepository;
import pl.simpleascoding.tutoringplatform.service.user.UserService;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final UserService userService;
    private final ReviewRepository reviewRepository;

    private final ReviewModelMapper reviewModelMapper;

    @Override
    public String createReview(CreateReviewDTO dto, String authorUsername) {
        User receiver = userService.getUserById(dto.receiverId()).orElseThrow(() -> new UserNotFoundException(dto.receiverId()));
        User author = userService.getUserByUsername(authorUsername).orElseThrow(() -> new UserNotFoundException(authorUsername));

        Review review = new Review(dto.content(), dto.stars());

        review.setAuthor(author);
        review.setReceiver(receiver);

        reviewRepository.save(review);

        return HttpStatus.CREATED.getReasonPhrase();
    }

    @Override
    public Page<ReviewDTO> getReceivedReviewsForUser(long id, Pageable pageable) {
        if (!userService.checkUserExists(id)) {
            throw new UserNotFoundException(id);
        }

        return reviewRepository.findReviewsByReceiverId(id, pageable).map(reviewModelMapper::mapReviewToDto);
    }

    @Override
    public Page<ReviewDTO> getPostedReviewsForUser(long id, Pageable pageable) {
        if (!userService.checkUserExists(id)) {
            throw new UserNotFoundException(id);
        }

        return reviewRepository.findReviewsByAuthorId(id, pageable).map(reviewModelMapper::mapReviewToDto);
    }

    @Override
    @Transactional
    public String updateReview(UpdateReviewDTO dto, String username, long reviewId) {
        User author = userService.getUserByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException(reviewId));

        if (!review.getAuthor().equals(author)) {
            return HttpStatus.UNAUTHORIZED.getReasonPhrase();
        }

        review.setStars(dto.stars());
        review.setContent(dto.content());

        return HttpStatus.OK.getReasonPhrase();
    }

    @Override
    public String deleteReview(String username, long reviewId) {
        User author = userService.getUserByUsername(username).orElseThrow(() -> new UserNotFoundException(username));
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException(reviewId));

        if (!review.getAuthor().equals(author)) {
            return HttpStatus.UNAUTHORIZED.getReasonPhrase();
        }

        reviewRepository.delete(review);

        return HttpStatus.OK.getReasonPhrase();
    }

}
