package com.redoute.selecteur.web.rest.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.redoute.selecteur.domain.Perimeter;
import com.redoute.selecteur.domain.util.CustomDateTimeDeserializer;
import com.redoute.selecteur.domain.util.CustomDateTimeSerializer;
import org.joda.time.DateTime;

public class PerimeterDTO {

    private Long id;

    private String author;

    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    private DateTime updateTime;

    private String name;

    private String state;

    private Boolean isTemplate;

    private Integer domainIndex;

    public PerimeterDTO() {
        // Default constructor for deserialization
    }

    public PerimeterDTO(Perimeter perimeter) {
        if (perimeter != null) {
            this.id = perimeter.getId();
            this.author = perimeter.getAuthor();
            this.updateTime = perimeter.getUpdateTime();
            this.name = perimeter.getName();
            this.state = perimeter.getState();
            this.isTemplate = perimeter.getIsTemplate();
            this.domainIndex = perimeter.getDomainIndex();
        }
    }

    public Long getId() {
        return id;
    }

    public String getAuthor() {
        return author;
    }

    public DateTime getUpdateTime() {
        return updateTime;
    }

    public String getName() {
        return name;
    }

    public String getState() {
        return state;
    }

    public Boolean isTemplate() {
        return isTemplate;
    }

    public Integer getDomainIndex() {
        return domainIndex;
    }

    @JsonIgnore
    public Perimeter getEntity() {
        Perimeter entity = new Perimeter();
        entity.setId(this.id);
        entity.setAuthor(this.author);
        entity.setUpdateTime(this.updateTime);
        entity.setName(this.name);
        entity.setState(this.state);
        entity.setIsTemplate(this.isTemplate);
        entity.setDomainIndex(this.domainIndex);

        return entity;
    }
}
