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
import pl.simpleascoding.tutoringplatform.dto.CreateUpdateReviewDTO;
import pl.simpleascoding.tutoringplatform.dto.ReviewDTO;
import pl.simpleascoding.tutoringplatform.exception.UserNotFoundException;
import pl.simpleascoding.tutoringplatform.repository.ReviewRepository;
import pl.simpleascoding.tutoringplatform.service.user.UserService;

@Service
@RequiredArgsConstructor
public class ReviewServiceImpl implements ReviewService {

    private final UserService userService;
    private final ReviewRepository reviewRepository;

    @Override
    @Transactional
    public String createReview(CreateUpdateReviewDTO dto, String authorUsername, Long receiverId) {
        User receiver = userService.getUserById(receiverId).orElseThrow(() -> new UserNotFoundException(receiverId));
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

}
