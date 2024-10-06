package hu.siz.framework.root.model;

import io.swagger.v3.oas.annotations.media.Schema;

/**
 * Enum to represent the status of a record
 */
@Schema(description = "Record status marker enumeration", example = "ACTIVE")
public enum RecordStatus {
    /**
     * Record is inserted but not activated yet
     */
    PENDING,
    /**
     * Record is active; usable
     */
    ACTIVE,
    /**
     * Record is deleted; inactive/unusable for new entries
     */
    DELETED;
}
