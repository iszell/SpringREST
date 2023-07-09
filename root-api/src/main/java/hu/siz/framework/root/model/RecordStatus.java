package hu.siz.framework.root.model;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
@Schema(description = "Record status marker enumeration", example = "ACTIVE")
public enum RecordStatus {
    PENDING("p"), ACTIVE("a"), DELETED("d");

    private final String dataBaseRepresentation;
}
