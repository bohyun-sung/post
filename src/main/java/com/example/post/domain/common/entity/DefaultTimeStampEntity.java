package com.example.post.domain.common.entity;

import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.EntityListeners;
import javax.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Getter
@ToString(callSuper = true)
@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
public abstract class DefaultTimeStampEntity {

    @CreatedDate
    @Column(name = "rgdt", updatable = false, columnDefinition = "TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP COMMENT '생성시간'")
    protected LocalDateTime rgdt;

    @LastModifiedDate
    @Column(name = "updt", columnDefinition = "TIMESTAMP NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '변경시간'")
    protected LocalDateTime updt;
}
