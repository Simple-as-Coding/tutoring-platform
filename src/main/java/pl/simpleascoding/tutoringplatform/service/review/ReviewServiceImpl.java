package pl.simpleascoding.tutoringplatform.service.review;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.simpleascoding.tutoringplatform.domain.review.Review;
import pl.simpleascoding.tutoringplatform.domain.user.User;
import pl.simpleascoding.tutoringplatform.dto.CreateReviewDTO;
import pl.simpleascoding.tutoringplatform.dto.ReviewDTO;
import pl.simpleascoding.tutoringplatform.dto.RscpDTO;
import pl.simpleascoding.tutoringplatform.dto.UpdateReviewDTO;
import pl.simpleascoding.tutoringplatform.exception.ReviewNotFoundException;
import pl.simpleascoding.tutoringplatform.exception.UserNotFoundException;
import pl.simpleascoding.tutoringplatform.repository.ReviewRepository;
import pl.simpleascoding.tutoringplatform.rscp.RscpStatus;
import pl.simpleascoding.tutoringplatform.service.user.UserService;

@Service
@Primary
@RequiredArgsConstructor
class ReviewServiceImpl implements ReviewService {

    private final UserService userService;
    private final ReviewRepository reviewRepository;

    private final ReviewModelMapper reviewModelMapper;

    @Override
    public RscpDTO<?> createReview(CreateReviewDTO dto, String authorUsername) {
        User receiver = userService.getUserById(dto.receiverId());
        User author = userService.getUserByUsername(authorUsername);

        Review review = new Review(dto.content(), dto.stars());

        review.setAuthor(author);
        review.setReceiver(receiver);

        reviewRepository.save(review);

        return new RscpDTO<>(RscpStatus.CREATED, "review created", null);
    }

    @Override
    public RscpDTO<Page<ReviewDTO>> getReceivedReviewsForUser(long id, Pageable pageable) {
        if (!userService.checkUserExists(id)) {
            throw new UserNotFoundException(id);
        }

        Page<ReviewDTO> reviewPage = reviewRepository.findReviewsByReceiverId(id, pageable)
                .map(reviewModelMapper::mapReviewToDto);

        return new RscpDTO<>(RscpStatus.OK, "reviews returned", reviewPage);
    }

    @Override
    public RscpDTO<Page<ReviewDTO>> getPostedReviewsForUser(long id, Pageable pageable) {
        if (!userService.checkUserExists(id)) {
            throw new UserNotFoundException(id);
        }
        Page<ReviewDTO> reviewPage = reviewRepository.findReviewsByAuthorId(id, pageable)
                .map(reviewModelMapper::mapReviewToDto);

        return new RscpDTO<>(RscpStatus.OK, "reviews returned", reviewPage);
    }

    @Override
    @Transactional
    public RscpDTO<ReviewDTO> updateReview(UpdateReviewDTO dto, String username, long reviewId) {
        User author = userService.getUserByUsername(username);
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException(reviewId));

        if (!review.getAuthor().equals(author)) {
            return new RscpDTO<>(RscpStatus.UNAUTHORIZED, null, null);
        }

        review.setStars(dto.stars());
        review.setContent(dto.content());


        return new RscpDTO<ReviewDTO>(RscpStatus.OK, "Review updated successfully",
                reviewModelMapper.mapReviewToDto(review));
    }

    @Override
    public RscpDTO<?> deleteReview(String username, long reviewId) {
        User author = userService.getUserByUsername(username);
        Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException(reviewId));

        if (!review.getAuthor().equals(author)) {
            return new RscpDTO<>(RscpStatus.UNAUTHORIZED, null, null);
        }

        reviewRepository.delete(review);

        return new RscpDTO<>(RscpStatus.OK, "Review deleted successfully", null);
    }

}
