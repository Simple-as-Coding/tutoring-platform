package pl.simpleascoding.tutoringplatform.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import pl.simpleascoding.tutoringplatform.util.rscp.RscpDTO;

public class ControllerUtils {

    public static <T> ResponseEntity<T> transformRscpDTOToResponseEntity(RscpDTO<T> rscpDTO) {
        T body = rscpDTO.body();
        HttpHeaders headers = new HttpHeaders();
        headers.add("message", rscpDTO.message());
        HttpStatus httpStatus = HttpStatus.resolve(rscpDTO.status().value());

        return new ResponseEntity<>(body, headers, httpStatus);
    }
}
