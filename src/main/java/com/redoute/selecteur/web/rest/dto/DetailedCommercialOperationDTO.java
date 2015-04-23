package com.redoute.selecteur.web.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.redoute.selecteur.domain.DetailedCommercialOperation;
import com.redoute.selecteur.domain.util.CustomDateTimeDeserializer;
import com.redoute.selecteur.domain.util.CustomDateTimeSerializer;
import org.joda.time.DateTime;

public class DetailedCommercialOperationDTO {

    private Long id;

    private String providerCode;

    private DomainDTO domain;

    private String description;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    private DateTime startDate;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    private DateTime endDate;

    public DetailedCommercialOperationDTO() {
        // Default constructor for deserialization
    }

    public DetailedCommercialOperationDTO(DetailedCommercialOperation detailedCommercialOperation) {
        this.id = detailedCommercialOperation.getId();
        this.providerCode = detailedCommercialOperation.getProviderCode();
        this.domain = new DomainDTO(detailedCommercialOperation.getDomain());
        this.description = detailedCommercialOperation.getDescription();
        this.startDate = detailedCommercialOperation.getStartDate();
        this.endDate = detailedCommercialOperation.getEndDate();
    }

    public Long getId() {
        return id;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public DomainDTO getDomain() {
        return domain;
    }

    public String getDescription() {
        return description;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    @JsonIgnore
    public DetailedCommercialOperation getEntity() {
        DetailedCommercialOperation entity = new DetailedCommercialOperation();

        entity.setId(this.id);
        entity.setProviderCode(this.providerCode);

        if (this.domain != null) {
            entity.setDomain(this.domain.getEntity());
        }

        entity.setDescription(this.description);
        entity.setStartDate(this.startDate);
        entity.setEndDate(this.endDate);

        return entity;
    }
}

