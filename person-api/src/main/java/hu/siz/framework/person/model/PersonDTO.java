package hu.siz.framework.person.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.core.Relation;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * A transfer object for a Person
 *
 * @author siz
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Relation(collectionRelation = "persons", itemRelation = "person")
public class PersonDTO extends RepresentationModel<PersonDTO> {
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
