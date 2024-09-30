package hu.siz.framework.core.entity;

import hu.siz.framework.root.model.RecordStatus;
import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import jakarta.persistence.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.io.Serializable;
import java.time.ZonedDateTime;

@Data
@MappedSuperclass
@EqualsAndHashCode(callSuper = true, onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
public abstract class AuditableEntity<I extends Serializable> extends BaseEntity<I> {
    @CreatedDate
    @Column(updatable = false)
    private ZonedDateTime createTime;
    @LastModifiedDate
    @Column(insertable = false)
    private ZonedDateTime updateTime;
    private RecordStatus status;
    @Version
    private int version;
}
