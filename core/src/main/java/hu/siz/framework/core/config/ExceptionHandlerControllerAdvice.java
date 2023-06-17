package hu.siz.framework.core.config;

import hu.siz.framework.root.exception.ObjectNotFoundException;
import hu.siz.framework.root.exception.UnsupportedOperationException;
import hu.siz.framework.root.model.ErrorDetail;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
    public List<ErrorDetail> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        return e.getBindingResult()
                .getAllErrors()
                .stream()
                .map(error -> new ErrorDetail(ErrorDetail.Severity.ERROR,
                        ((FieldError) error).getField(),
                        error.getCode(),
                        error.getDefaultMessage()))
                .toList();
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
}
