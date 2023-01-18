package pl.simpleascoding.tutoringplatform.util.rscp;

import org.springframework.lang.Nullable;

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