package pl.simpleascoding.tutoringplatform.api.publicResource;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.simpleascoding.tutoringplatform.dto.CreateReviewDTO;
import pl.simpleascoding.tutoringplatform.dto.UpdateReviewDTO;
import pl.simpleascoding.tutoringplatform.service.review.ReviewService;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    ResponseEntity<String> createReview(@RequestBody @Valid CreateReviewDTO dto, Principal principal) {
        return new ResponseEntity<>(reviewService.createReview(dto, principal.getName()), HttpStatus.CREATED);
    }

    @PutMapping("/{id}")
    ResponseEntity<String> updateReview(@RequestBody @Valid UpdateReviewDTO dto, @PathVariable long id, Principal principal){
        return new ResponseEntity<>(reviewService.updateReview(dto, principal.getName(), id), HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<String> deleteReview(@PathVariable long id, Principal principal){
        return new ResponseEntity<>(reviewService.deleteReview(principal.getName(), id), HttpStatus.OK);
    }



}
