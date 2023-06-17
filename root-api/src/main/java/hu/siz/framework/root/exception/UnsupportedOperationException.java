package hu.siz.framework.root.exception;

import lombok.experimental.StandardException;

/**
 * An exception signaling that the request operation is not implemented. Results in an 501 error.
 */
@StandardException
public class UnsupportedOperationException extends RuntimeException {
}
