package pl.simpleascoding.tutoringplatform.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.simpleascoding.tutoringplatform.dto.CreateReviewDTO;
import pl.simpleascoding.tutoringplatform.dto.ReviewDTO;
import pl.simpleascoding.tutoringplatform.dto.RscpDTO;
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
    ResponseEntity<ReviewDTO> updateReview(@RequestBody @Valid UpdateReviewDTO dto, @PathVariable long id,
                                                    Principal principal){
        RscpDTO<ReviewDTO> rscpDTO = reviewService.updateReview(dto, principal.getName(), id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", rscpDTO.message());
        HttpStatus httpStatus = HttpStatus.resolve(rscpDTO.status().value());
        ReviewDTO body = rscpDTO.body();
        return new ResponseEntity<>(body, headers, httpStatus);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteReview(@PathVariable long id, Principal principal){
        RscpDTO<?> rscpDTO = reviewService.deleteReview(principal.getName(), id);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", rscpDTO.message());
        HttpStatus httpStatus = HttpStatus.resolve(rscpDTO.status().value());

        return new ResponseEntity<>(headers, httpStatus);
    }

}
