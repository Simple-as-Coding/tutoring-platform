package pl.simpleascoding.tutoringplatform.api.publicResource;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.simpleascoding.tutoringplatform.dto.CreateUpdateReviewDTO;
import pl.simpleascoding.tutoringplatform.dto.ReviewDTO;
import pl.simpleascoding.tutoringplatform.service.review.ReviewService;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api/v1/users/{id}/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping("/received")
    ResponseEntity<String> reviewUser(@PathVariable Long id, @RequestBody @Valid CreateUpdateReviewDTO dto, Principal principal) {
        return new ResponseEntity<>(reviewService.createReview(dto, principal.getName(), id), HttpStatus.CREATED);
    }

    @GetMapping("/received")
    ResponseEntity<Page<ReviewDTO>> getReceivedReviewsForUser(@PathVariable Long id, Pageable pageable) {
        return new ResponseEntity<>(reviewService.getReceivedReviewsForUser(id, pageable), HttpStatus.OK);
    }

    @GetMapping("/posted")
    ResponseEntity<Page<ReviewDTO>> getPostedReviewsForUser(@PathVariable Long id, Pageable pageable) {
        return new ResponseEntity<>(reviewService.getPostedReviewsForUser(id, pageable), HttpStatus.OK);
    }

}
