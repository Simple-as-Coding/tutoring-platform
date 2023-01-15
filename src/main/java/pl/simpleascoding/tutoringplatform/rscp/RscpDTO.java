package pl.simpleascoding.tutoringplatform.rscp;

import org.springframework.lang.Nullable;
import pl.simpleascoding.tutoringplatform.rscp.RscpStatus;


/**
 * {@code RscpDTO} record used to transfer date between service and controller. Contain a status, message and
 * body.
 *
 * @param status  the status code
 * @param message the status message
 * @param body    the response body
 */
public record RscpDTO<T>(RscpStatus status, @Nullable String message, @Nullable T body) {
}