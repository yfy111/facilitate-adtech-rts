package com.adtech.rts.model.entity;

import com.adtech.rts.model.entity.base.BaseEntity;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection="OrgInfo")
public class OrgInfo extends BaseEntity {
    private String orignalId;
    private String orignalOrganizationCode;
    private Boolean deleted;
    private String orignalCode;
    private String orignalName;
    private String parentOrganizationCode;
    private String partialObject;
}
