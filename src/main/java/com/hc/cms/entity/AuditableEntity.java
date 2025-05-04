package com.hc.cms.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.Id;

import java.time.OffsetDateTime;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class AuditableEntity {

    @Id
    private String id;

    private String createdBy;

    private OffsetDateTime createdDate;

    private String modifiedBy;

    private OffsetDateTime modifiedDate;

    private boolean deleted = false;


}
