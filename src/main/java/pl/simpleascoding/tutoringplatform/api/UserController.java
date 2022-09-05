package pl.simpleascoding.tutoringplatform.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.simpleascoding.tutoringplatform.dto.*;
import pl.simpleascoding.tutoringplatform.service.review.ReviewService;
import pl.simpleascoding.tutoringplatform.service.user.UserService;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserService userService;
    private final ReviewService reviewService;

    @PostMapping
    ResponseEntity<?> createUser(@RequestBody CreateUserDTO dto, HttpServletRequest request) {
        RscpDTO<?> rscpDTO = userService.createUser(dto, request.getRequestURL().toString());
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", rscpDTO.message());
        HttpStatus httpStatus = HttpStatus.resolve(rscpDTO.status().value());

        return new ResponseEntity<>(headers, httpStatus);
    }

    @GetMapping("/confirm-registration")
    ResponseEntity<?> confirmRegistration(@RequestParam String tokenValue) {
        RscpDTO<?> rscpDTO = userService.confirmUserRegistration(tokenValue);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", rscpDTO.message());
        HttpStatus httpStatus = HttpStatus.resolve(rscpDTO.status().value());

        return new ResponseEntity<>(headers, httpStatus);
    }

    @PostMapping("/change-password")
    ResponseEntity<?> changeUserPassword(@RequestBody ChangeUserPasswordDTO dto,
                                         Principal principal, HttpServletRequest request) {
        RscpDTO<?> rscpDTO = userService.changeUserPassword(dto, principal.getName(), request.getRequestURL().toString());
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", rscpDTO.message());
        HttpStatus httpStatus = HttpStatus.resolve(rscpDTO.status().value());

        return new ResponseEntity<>(headers, httpStatus);
    }

    @GetMapping("/confirm-change-password")
    ResponseEntity<?> confirmChangeUserPassword(@RequestParam String tokenValue) {
        RscpDTO<?> rscpDTO = userService.confirmChangeUserPassword(tokenValue);
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", rscpDTO.message());
        HttpStatus httpStatus = HttpStatus.resolve(rscpDTO.status().value());

        return new ResponseEntity<>(headers, httpStatus);
    }

    @PatchMapping
    ResponseEntity<UserDTO> modifyUser(@RequestBody ModifyUserDTO dto, Principal principal) {
        RscpDTO<UserDTO> rscpDTO = userService.modifyUser(dto, principal.getName());
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", rscpDTO.message());
        HttpStatus httpStatus = HttpStatus.resolve(rscpDTO.status().value());
        UserDTO body = rscpDTO.body();

        return new ResponseEntity<>(body, headers, httpStatus);
    }

    @GetMapping("/{id}/reviews/received")
    ResponseEntity<Page<ReviewDTO>> getReceivedReviewsForUser(@PathVariable long id, Pageable pageable) {
        return new ResponseEntity<>(reviewService.getReceivedReviewsForUser(id, pageable), HttpStatus.OK);
    }

    @GetMapping("/{id}/reviews/posted")
    ResponseEntity<Page<ReviewDTO>> getPostedReviewsForUser(@PathVariable long id, Pageable pageable) {
        return new ResponseEntity<>(reviewService.getPostedReviewsForUser(id, pageable), HttpStatus.OK);
    }

}
