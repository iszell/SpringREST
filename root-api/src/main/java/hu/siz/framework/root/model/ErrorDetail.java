package hu.siz.framework.root.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * A field error descriptor object returned for validation errors.
 *
 * @param severity     the severity of the error
 * @param field        the name of the field in error
 * @param errorCode    the code of the validation error
 * @param errorMessage the localized message of the validation error
 * @author siz
 * @see Severity
 */
public record ErrorDetail(
        @Schema(description = "Error severity", example = "ERROR")
        Severity severity,
        @Schema(description = "The field the error refers to", example = "name")
        String field,
        @Schema(description = "The error code", example = "NotEmpty")
        String errorCode,
        @Schema(description = "The field the error refers to", example = "can't be empty")
        String errorMessage
) {

    /**
     * Severity constants for {@link ErrorDetail}. {@link #WARNING} means that the processing of the object is done but
     * there were some problems that should be noted. {@link #ERROR} means that processing was aborted
     *
     * @author siz
     */
    public enum Severity {
        WARNING, ERROR
    }
}
