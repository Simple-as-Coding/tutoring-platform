package pl.simpleascoding.tutoringplatform.advertisement;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.simpleascoding.tutoringplatform.advertisement.dto.Advertisement;
import pl.simpleascoding.tutoringplatform.advertisement.dto.CreateAdvertisement;
import pl.simpleascoding.tutoringplatform.util.rscp.RscpDTO;
import pl.simpleascoding.tutoringplatform.util.ControllerUtils;

@RestController
@RequestMapping("api/v1/advertisement")
@RequiredArgsConstructor
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @PostMapping("/add")
    public ResponseEntity<Advertisement> createAdvertisement(@RequestBody CreateAdvertisement requestDTO) {
        RscpDTO<Advertisement> rscpDTO = advertisementService.createAdvertisement(requestDTO);
        return ControllerUtils.transformRscpDTOToResponseEntity(rscpDTO);
    }

    @GetMapping("/history/{username}")
    public ResponseEntity<Page<Advertisement>> getUsersAdvertisements(@PathVariable String username, Pageable pageable) {
        RscpDTO<Page<Advertisement>> rscpDTO = advertisementService.getUsersAdvertisements(username, pageable);
        Page<Advertisement> responseBody = rscpDTO.body();
        //Message
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", rscpDTO.message());
        //Status
        HttpStatus httpStatus = HttpStatus.resolve(rscpDTO.status().value());
        return new ResponseEntity<>(responseBody, headers, httpStatus);
    }

}
