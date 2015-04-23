package com.redoute.selecteur.domain;

import org.joda.time.DateTime;

import java.io.Serializable;
import java.util.Objects;

public class DetailedCommercialOperation implements Serializable {

    private Long id;

    private String providerCode;

    private Domain domain;

    private String description;

    private DateTime startDate;

    private DateTime endDate;

    public DetailedCommercialOperation() {

    }

    public DetailedCommercialOperation(CommercialOperation commercialOperation) {
        if (commercialOperation != null) {
            this.id = commercialOperation.getId();
            this.providerCode = commercialOperation.getProviderCode();
            this.domain = commercialOperation.getDomain();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProviderCode() {
        return providerCode;
    }

    public void setProviderCode(String providerCode) {
        this.providerCode = providerCode;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public DateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(DateTime startDate) {
        this.startDate = startDate;
    }

    public DateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(DateTime endDate) {
        this.endDate = endDate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        DetailedCommercialOperation detailedCommercialOperation = (DetailedCommercialOperation) o;

        if ( ! Objects.equals(id, detailedCommercialOperation.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "DetailedCommercialOperation{" +
                "id=" + id +
                ", providerCode='" + providerCode + "'" +
                ", description='" + description + "'" +
                ", startDate='" + startDate + "'" +
                ", endDate='" + endDate + "'" +
                '}';
    }
}
