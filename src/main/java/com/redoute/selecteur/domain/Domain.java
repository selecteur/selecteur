package com.redoute.selecteur.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.redoute.selecteur.domain.util.CustomDateTimeDeserializer;
import com.redoute.selecteur.domain.util.CustomDateTimeSerializer;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;
import org.hibernate.annotations.Type;
import org.joda.time.DateTime;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Domain.
 */
@Entity
@Table(name = "T_DOMAIN")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Domain implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "author")
    private String author;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "update_time")
    private DateTime updateTime;

    @OneToOne(mappedBy = "domain")
    private CommercialOperation commercialOperation;

    @OneToMany(mappedBy = "domain")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Perimeter> perimeters = new HashSet<>();

    @OneToMany(mappedBy = "domain")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<OffersSerie> offersSeries = new HashSet<>();

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public DateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(DateTime updateTime) {
        this.updateTime = updateTime;
    }

    public CommercialOperation getCommercialOperation() {
        return commercialOperation;
    }

    public void setCommercialOperation(CommercialOperation commercialOperation) {
        this.commercialOperation = commercialOperation;
    }

    public Set<Perimeter> getPerimeters() {
        return perimeters;
    }

    public void setPerimeters(Set<Perimeter> perimeters) {
        this.perimeters = perimeters;
    }

    public Set<OffersSerie> getOffersSeries() {
        return offersSeries;
    }

    public void setOffersSeries(Set<OffersSerie> offersSeries) {
        this.offersSeries = offersSeries;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Domain domain = (Domain) o;

        if ( ! Objects.equals(id, domain.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Domain{" +
                "id=" + id +
                ", author='" + author + "'" +
                ", updateTime='" + updateTime + "'" +
                '}';
    }
}
