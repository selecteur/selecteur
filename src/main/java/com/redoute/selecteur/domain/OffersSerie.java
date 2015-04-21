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
 * A OffersSerie.
 */
@Entity
@Table(name = "T_OFFERSSERIE")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class OffersSerie implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "author")
    private String author;

    @Type(type = "org.jadira.usertype.dateandtime.joda.PersistentDateTime")
    @JsonSerialize(using = CustomDateTimeSerializer.class)
    @JsonDeserialize(using = CustomDateTimeDeserializer.class)
    @Column(name = "update_date")
    private DateTime updateDate;

    @Column(name = "query")
    private String query;

    @Column(name = "name")
    private String name;

    @Column(name = "offers_ids")
    private Long offersIds;

    @Column(name = "input_action")
    private String inputAction;

    @Column(name = "output_action")
    private String outputAction;

    @Column(name = "state")
    private String state;

    @ManyToOne
    private Domain domain;

    @OneToMany(mappedBy = "offersSerie")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Animation> animations = new HashSet<>();

    @OneToMany(mappedBy = "offersSerie")
    @JsonIgnore
    @Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
    private Set<Exposition> expositions = new HashSet<>();

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

    public DateTime getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(DateTime updateDate) {
        this.updateDate = updateDate;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOffersIds() {
        return offersIds;
    }

    public void setOffersIds(Long offersIds) {
        this.offersIds = offersIds;
    }

    public String getInputAction() {
        return inputAction;
    }

    public void setInputAction(String inputAction) {
        this.inputAction = inputAction;
    }

    public String getOutputAction() {
        return outputAction;
    }

    public void setOutputAction(String outputAction) {
        this.outputAction = outputAction;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Domain getDomain() {
        return domain;
    }

    public void setDomain(Domain domain) {
        this.domain = domain;
    }

    public Set<Animation> getAnimations() {
        return animations;
    }

    public void setAnimations(Set<Animation> animations) {
        this.animations = animations;
    }

    public Set<Exposition> getExpositions() {
        return expositions;
    }

    public void setExpositions(Set<Exposition> expositions) {
        this.expositions = expositions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        OffersSerie offersSerie = (OffersSerie) o;

        if ( ! Objects.equals(id, offersSerie.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "OffersSerie{" +
                "id=" + id +
                ", author='" + author + "'" +
                ", updateDate='" + updateDate + "'" +
                ", query='" + query + "'" +
                ", name='" + name + "'" +
                ", offersIds='" + offersIds + "'" +
                ", inputAction='" + inputAction + "'" +
                ", outputAction='" + outputAction + "'" +
                ", state='" + state + "'" +
                '}';
    }
}
