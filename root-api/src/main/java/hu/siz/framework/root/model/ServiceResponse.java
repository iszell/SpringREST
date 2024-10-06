package hu.siz.framework.root.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;

/**
 * Wrapper class for service response messages
 *
 * @author siz
 */
@Data
@Builder
public class ServiceResponse {
    private List<ErrorDetail> errors;
}
