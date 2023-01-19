package pl.simpleascoding.tutoringplatform.user;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.simpleascoding.tutoringplatform.review.ReviewService;
import pl.simpleascoding.tutoringplatform.review.dto.Review;
import pl.simpleascoding.tutoringplatform.user.dto.ChangeUserPasswordDTO;
import pl.simpleascoding.tutoringplatform.user.dto.CreateUserDTO;
import pl.simpleascoding.tutoringplatform.user.dto.ModifyUserDTO;
import pl.simpleascoding.tutoringplatform.user.dto.UserDTO;
import pl.simpleascoding.tutoringplatform.util.ControllerUtils;
import pl.simpleascoding.tutoringplatform.util.rscp.RscpDTO;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@RestController
@RequestMapping("api/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserService userService;
    private final ReviewService reviewService;

    @PostMapping
    ResponseEntity<UserDTO> createUser(@RequestBody CreateUserDTO dto, HttpServletRequest request) {
        RscpDTO<UserDTO> rscpDTO = userService.createUser(dto, request.getRequestURL().toString());

        return ControllerUtils.transformRscpDTOToResponseEntity(rscpDTO);
    }

    @GetMapping("/confirm-registration")
    ResponseEntity<?> confirmRegistration(@RequestParam String tokenValue) {
        RscpDTO<?> rscpDTO = userService.confirmUserRegistration(tokenValue);

        return ControllerUtils.transformRscpDTOToResponseEntity(rscpDTO);
    }

    @PostMapping("/change-password")
    ResponseEntity<?> changeUserPassword(@RequestBody ChangeUserPasswordDTO dto,
                                         Principal principal, HttpServletRequest request) {
        RscpDTO<?> rscpDTO = userService.changeUserPassword(dto, principal.getName(), request.getRequestURL().toString());

        return ControllerUtils.transformRscpDTOToResponseEntity(rscpDTO);
    }

    @GetMapping("/confirm-change-password")
    ResponseEntity<?> confirmChangeUserPassword(@RequestParam String tokenValue) {
        RscpDTO<?> rscpDTO = userService.confirmChangeUserPassword(tokenValue);

        return ControllerUtils.transformRscpDTOToResponseEntity(rscpDTO);
    }

    @PatchMapping
    ResponseEntity<UserDTO> modifyUser(@RequestBody ModifyUserDTO dto, Principal principal) {
        RscpDTO<UserDTO> rscpDTO = userService.modifyUser(dto, principal.getName());

        return ControllerUtils.transformRscpDTOToResponseEntity(rscpDTO);
    }

    @GetMapping("/{id}/reviews/received")
    ResponseEntity<Page<Review>> getReceivedReviewsForUser(@PathVariable long id, Pageable pageable) {
        RscpDTO<Page<Review>> rscpDTO = reviewService.getReceivedReviewsForUser(id, pageable);

        return ControllerUtils.transformRscpDTOToResponseEntity(rscpDTO);
    }

    @GetMapping("/{id}/reviews/posted")
    ResponseEntity<Page<Review>> getPostedReviewsForUser(@PathVariable long id, Pageable pageable) {
        RscpDTO<Page<Review>> rscpDTO = reviewService.getPostedReviewsForUser(id, pageable);

        return ControllerUtils.transformRscpDTOToResponseEntity(rscpDTO);
    }

}
