package pl.simpleascoding.tutoringplatform.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
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
    ResponseEntity<String> createUser(@RequestBody CreateUserDTO dto, HttpServletRequest request) {
        return new ResponseEntity<>(userService.createUser(dto, request.getRequestURL().toString()), HttpStatus.OK);
    }

    @GetMapping("/confirm-registration")
    ResponseEntity<String> confirmRegistration(@RequestParam String tokenValue) {
        return new ResponseEntity<>(userService.confirmUserRegistration(tokenValue), HttpStatus.OK);
    }

    @PostMapping("/change-password")
    ResponseEntity<String> changeUserPassword(@RequestBody ChangeUserPasswordDTO dto, Principal principal) {

        return new ResponseEntity<>(userService.changeUserPassword(dto, principal.getName()), HttpStatus.OK);

    }

    @PatchMapping
    ResponseEntity<RscpDTO<UserDTO>> modifyUser(@RequestBody ModifyUserDTO dto, Principal principal){
        return new ResponseEntity<>(userService.modifyUser(dto, principal.getName()), HttpStatus.OK);
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
