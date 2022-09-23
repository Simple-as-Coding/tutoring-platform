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
import pl.simpleascoding.tutoringplatform.util.ControllerUtils;

import javax.validation.Valid;
import java.security.Principal;

@RestController
@RequestMapping("api/v1/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;

    @PostMapping
    ResponseEntity<?> createReview(@RequestBody @Valid CreateReviewDTO dto, Principal principal) {
        RscpDTO<ReviewDTO> rscpDTO = reviewService.createReview(dto, principal.getName());

        return ControllerUtils.transformRscpDTOToResponseEntity(rscpDTO);
    }

    @PutMapping("/{id}")
    ResponseEntity<ReviewDTO> updateReview(@RequestBody @Valid UpdateReviewDTO dto, @PathVariable long id,
                                           Principal principal) {
        RscpDTO<ReviewDTO> rscpDTO = reviewService.updateReview(dto, principal.getName(), id);

        return ControllerUtils.transformRscpDTOToResponseEntity(rscpDTO);
    }

    @DeleteMapping("/{id}")
    ResponseEntity<?> deleteReview(@PathVariable long id, Principal principal) {
        RscpDTO<?> rscpDTO = reviewService.deleteReview(principal.getName(), id);

        return ControllerUtils.transformRscpDTOToResponseEntity(rscpDTO);
    }

}
