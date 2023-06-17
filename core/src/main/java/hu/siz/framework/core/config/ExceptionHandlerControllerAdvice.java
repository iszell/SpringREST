package hu.siz.framework.core.config;

import hu.siz.framework.root.exception.ObjectNotFoundException;
import hu.siz.framework.root.exception.UnsupportedOperationException;
import hu.siz.framework.root.model.ErrorDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

/**
 * A controller advice to turn exceptions into default HTTP responses
 *
 * @author siz
 */
@Slf4j
@ControllerAdvice
public class ExceptionHandlerControllerAdvice {

    /**
     * Convert validation errors to HTTP response
     *
     * @param e the validation exception
     * @return {@link FieldError} list in the body of an HTTP 400 response
     */
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    @RequestMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<ErrorDetail>> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return ResponseEntity
                .badRequest()
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .body(e.getBindingResult()
                        .getAllErrors()
                        .stream()
                        .map(error -> new ErrorDetail(ErrorDetail.Severity.ERROR,
                                ((FieldError) error).getField(),
                                error.getCode(),
                                error.getDefaultMessage()))
                        .toList());
    }

    /**
     * Convert {@link UnsupportedOperationException} to HTTP response
     *
     * @param e the exception
     * @return an HTTP 501 response
     */
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    @ExceptionHandler(UnsupportedOperationException.class)
    public ResponseEntity<Void> handleUnsupportedOperationException(UnsupportedOperationException e) {
        log.error(e.getMessage(), e);
        return ResponseEntity
                .status(HttpStatus.NOT_IMPLEMENTED)
                .build();
    }

    /**
     * Convert {@link ObjectNotFoundException} to HTTP response
     *
     * @param e the exception
     * @return an HTTP 404 response
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ObjectNotFoundException.class)
    public ResponseEntity<Void> handleObjectNotFoundException(ObjectNotFoundException e) {
        log.info("Object not found for type {} and id {}",
                e.getType() != null ? e.getType().getSimpleName() : null,
                e.getId());
        return ResponseEntity
                .notFound()
                .build();
    }
}
