package pl.simpleascoding.tutoringplatform.api;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.simpleascoding.tutoringplatform.dto.AdvertisementDTO;
import pl.simpleascoding.tutoringplatform.dto.CreateAdvertisementDTO;
import pl.simpleascoding.tutoringplatform.dto.RscpDTO;
import pl.simpleascoding.tutoringplatform.service.advertisement.AdvertisementService;
import pl.simpleascoding.tutoringplatform.util.ControllerUtils;

@RestController
@RequestMapping("api/v1/advertisement")
@RequiredArgsConstructor
public class AdvertisementController {

    private final AdvertisementService advertisementService;

    @PostMapping("/add")
    public ResponseEntity<AdvertisementDTO> createAdvertisement(@RequestBody CreateAdvertisementDTO requestDTO) {
        RscpDTO<AdvertisementDTO> rscpDTO = advertisementService.createAdvertisement(requestDTO);

        return ControllerUtils.transformRscpDTOToResponseEntity(rscpDTO);
    }

    @GetMapping("/history/{username}")
    public ResponseEntity<Page<AdvertisementDTO>> getUsersAdvertisements(@PathVariable String username, Pageable pageable) {
        RscpDTO<Page<AdvertisementDTO>> rscpDTO = advertisementService.getUsersAdvertisements(username, pageable);
        Page<AdvertisementDTO> responseBody = rscpDTO.body();

        //Message
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", rscpDTO.message());

        //Status
        HttpStatus httpStatus = HttpStatus.resolve(rscpDTO.status().value());

        return new ResponseEntity<>(responseBody, headers, httpStatus);
    }
}
