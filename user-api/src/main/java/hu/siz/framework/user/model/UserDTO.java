package hu.siz.framework.user.model;

import hu.siz.framework.root.model.IdentifierWrapper;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = true)
public class UserDTO extends IdentifierWrapper<UUID> {
    @Email
    @Schema(format = "email", requiredMode = Schema.RequiredMode.REQUIRED)
    String email;
    @NotEmpty
    @Schema(example = "John", requiredMode = Schema.RequiredMode.REQUIRED)
    String firstName;
    @NotEmpty
    @Schema(example = "Doe", requiredMode = Schema.RequiredMode.REQUIRED)
    String lastName;
    @Schema(example = "3")
    private int level;
    @Schema(example = "12.34")
    private BigDecimal amount;
    @Schema(example = "2023-06-16T21:08:03")
    private LocalDateTime entryDate;

}
