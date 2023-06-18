package hu.siz.framework.root.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * An exception signaling that the requested object cannot be found. It will result in a 404 error
 *
 * @author siz
 */
@Getter
@AllArgsConstructor
public class ObjectNotFoundException extends RuntimeException {
    private final Class<?> type;
    private final Object id;
}
