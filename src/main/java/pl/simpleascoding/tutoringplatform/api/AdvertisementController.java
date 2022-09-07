package pl.simpleascoding.tutoringplatform.api;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import pl.simpleascoding.tutoringplatform.dto.AdvertisementDTO;
import pl.simpleascoding.tutoringplatform.dto.CreateAdvertisementDTO;
import pl.simpleascoding.tutoringplatform.dto.RscpDTO;
import pl.simpleascoding.tutoringplatform.service.advertisement.AdvertisementService;

@RestController
@RequestMapping("api/v1/advertisement")
@RequiredArgsConstructor
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @PostMapping("/add")
    public ResponseEntity<AdvertisementDTO> createAdvertisement(@RequestBody CreateAdvertisementDTO requestDTO) {
        RscpDTO<AdvertisementDTO> rscpDTO = advertisementService.createAdvertisement(requestDTO);
        AdvertisementDTO advDTO = rscpDTO.body();

        // Message
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", rscpDTO.message());

        // Status
        HttpStatus httpStatus = HttpStatus.resolve(rscpDTO.status().value());

        return new ResponseEntity<>(advDTO, headers, httpStatus);
    }
}
