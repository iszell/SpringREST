package hu.siz.framework.root.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * A simple, generic wrapper for an identifier
 *
 * @param <I> the type of the identifier
 * @author siz
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Schema(name = "id", description = "Identifier wrapper")
public class IdentifierWrapper<I> {
    @Schema(description = "Unique identifier")
    private I id;
}
