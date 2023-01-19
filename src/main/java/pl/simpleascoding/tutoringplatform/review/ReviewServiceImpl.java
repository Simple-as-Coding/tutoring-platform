package pl.simpleascoding.tutoringplatform.review;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pl.simpleascoding.tutoringplatform.review.dto.CreateReview;
import pl.simpleascoding.tutoringplatform.review.dto.Review;
import pl.simpleascoding.tutoringplatform.review.dto.UpdateReview;
import pl.simpleascoding.tutoringplatform.review.exception.ReviewNotFoundException;
import pl.simpleascoding.tutoringplatform.user.User;
import pl.simpleascoding.tutoringplatform.util.rscp.RscpDTO;
import pl.simpleascoding.tutoringplatform.user.exception.UserNotFoundException;
import pl.simpleascoding.tutoringplatform.util.rscp.RscpStatus;
import pl.simpleascoding.tutoringplatform.user.UserService;

@Service
@Primary
@RequiredArgsConstructor
class ReviewServiceImpl implements ReviewService {

    private final UserService userService;
    private final ReviewRepository reviewRepository;

    private final ReviewModelMapper reviewModelMapper;

    @Override
    public RscpDTO<Review> createReview(CreateReview dto, String authorUsername) {
        User receiver = userService.getUserById(dto.receiverId());
        User author = userService.getUserByUsername(authorUsername);

        pl.simpleascoding.tutoringplatform.review.Review review = new pl.simpleascoding.tutoringplatform.review.Review(dto.content(), dto.stars());

        review.setAuthor(author);
        review.setReceiver(receiver);

        reviewRepository.save(review);

        Review reviewDTO = reviewModelMapper.mapReviewToDto(review);

        return new RscpDTO<>(RscpStatus.CREATED, "Review created.", reviewDTO);
    }

    @Override
    public RscpDTO<Page<Review>> getReceivedReviewsForUser(long id, Pageable pageable) {
        if (!userService.checkUserExists(id)) {
            throw new UserNotFoundException(id);
        }

        Page<Review> reviewPage = reviewRepository.findReviewsByReceiverId(id, pageable)
                .map(reviewModelMapper::mapReviewToDto);

        return new RscpDTO<>(RscpStatus.OK, "Reviews returned.", reviewPage);
    }

    @Override
    public RscpDTO<Page<Review>> getPostedReviewsForUser(long id, Pageable pageable) {
        if (!userService.checkUserExists(id)) {
            throw new UserNotFoundException(id);
        }
        Page<Review> reviewPage = reviewRepository.findReviewsByAuthorId(id, pageable)
                .map(reviewModelMapper::mapReviewToDto);

        return new RscpDTO<>(RscpStatus.OK, "Reviews returned.", reviewPage);
    }

    @Override
    @Transactional
    public RscpDTO<Review> updateReview(UpdateReview dto, String username, long reviewId) {
        User author = userService.getUserByUsername(username);
        pl.simpleascoding.tutoringplatform.review.Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException(reviewId));

        if (!review.getAuthor().equals(author)) {
            return new RscpDTO<>(RscpStatus.UNAUTHORIZED, null, null);
        }

        review.setStars(dto.stars());
        review.setContent(dto.content());


        return new RscpDTO<Review>(RscpStatus.OK, "Review updated successfully",
                reviewModelMapper.mapReviewToDto(review));
    }

    @Override
    public RscpDTO<?> deleteReview(String username, long reviewId) {
        User author = userService.getUserByUsername(username);
        pl.simpleascoding.tutoringplatform.review.Review review = reviewRepository.findById(reviewId).orElseThrow(() -> new ReviewNotFoundException(reviewId));

        if (!review.getAuthor().equals(author)) {
            return new RscpDTO<>(RscpStatus.UNAUTHORIZED, null, null);
        }

        reviewRepository.delete(review);

        return new RscpDTO<>(RscpStatus.OK, "Review deleted successfully", null);
    }

}
