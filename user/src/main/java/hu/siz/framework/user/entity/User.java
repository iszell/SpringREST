package hu.siz.framework.user.entity;

import hu.siz.framework.core.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Entity
@Table(name = "user_")
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
public class User extends BaseEntity<UUID> {
    private String email;
    private String firstName;
    private String lastName;
    private int level;
    private BigDecimal amount;
    private LocalDateTime entryDate;
}
