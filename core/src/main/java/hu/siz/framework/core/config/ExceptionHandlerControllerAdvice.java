package hu.siz.framework.core.config;

import hu.siz.framework.root.exception.ObjectNotFoundException;
import hu.siz.framework.root.exception.UnsupportedOperationException;
import hu.siz.framework.root.model.ErrorDetail;
import hu.siz.framework.root.model.ServiceResponse;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.StaleObjectStateException;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * A controller advice to turn exceptions into default HTTP responses
 *
 * @author siz
 */
@Slf4j
@RestControllerAdvice
public class ExceptionHandlerControllerAdvice extends ResponseEntityExceptionHandler {

    /**
     * Convert validation errors to HTTP response
     *
     * @param e the validation exception
     * @return {@link FieldError} list in the body of an HTTP 400 response
     */
    @Override
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @RequestMapping(produces = {MediaTypes.HAL_JSON_VALUE})
    @ApiResponse(responseCode = "400", description = "Bad content",
            content = @Content(schema = @Schema(implementation = ServiceResponse.class)))
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e,
                                                                  HttpHeaders headers, HttpStatusCode status,
                                                                  WebRequest request) {
        return new ResponseEntity<>(
                ServiceResponse.builder()
                        .errors(e.getBindingResult()
                                .getAllErrors()
                                .stream()
                                .map(error -> new ErrorDetail(ErrorDetail.Severity.ERROR,
                                        ((FieldError) error).getField(),
                                        error.getCode(),
                                        error.getDefaultMessage()))
                                .toList())
                        .build(), HttpStatus.BAD_REQUEST);
    }

    /**
     * Convert {@link UnsupportedOperationException} to HTTP response
     *
     * @param e the exception
     */
    @ResponseStatus(HttpStatus.NOT_IMPLEMENTED)
    @ExceptionHandler(UnsupportedOperationException.class)
    public void handleUnsupportedOperationException(UnsupportedOperationException e) {
        log.error(e.getMessage(), e);
    }

    /**
     * Convert {@link ObjectNotFoundException} to HTTP response
     *
     * @param e the exception
     */
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ObjectNotFoundException.class)
    public void handleObjectNotFoundException(ObjectNotFoundException e) {
        log.info("Object not found for type {} and id {}",
                e.getType() != null ? e.getType().getSimpleName() : null,
                e.getId());
    }

    /**
     * Handle optimistic locking exceptions
     *
     * @param e the exception containing the conflicting object reference
     */
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(StaleObjectStateException.class)
    public void handleStaleObjectStateException(StaleObjectStateException e) {
        log.info("Record version mismatch for {} with id {}", e.getEntityName(), e.getIdentifier());
    }
}
