package com.redoute.selecteur.web.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.redoute.selecteur.domain.Domain;
import com.redoute.selecteur.domain.util.CustomDateTimeDeserializer;
import com.redoute.selecteur.domain.util.CustomDateTimeSerializer;
import org.joda.time.DateTime;

public class DomainDTO {

    private String author;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    private DateTime updateTime;

    public DomainDTO() {
        // Default constructor for deserialization
    }

    public DomainDTO(Domain domain) {
        if (domain != null) {
            this.author = domain.getAuthor();
            this.updateTime = domain.getUpdateTime();
        }
    }

    public String getAuthor() {
        return author;
    }

    public DateTime getUpdateTime() {
        return updateTime;
    }

    @JsonIgnore
    public Domain getEntity() {
        Domain entity = new Domain();
        entity.setAuthor(this.author);
        entity.setUpdateTime(this.updateTime);

        return entity;
    }
}
