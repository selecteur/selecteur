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
 * A Condition.
 */
@Entity
@Table(name = "T_CONDITION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Condition implements Serializable {

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

    @Column(name = "state")
    private String state;

    @Column(name = "query")
    private String query;

    @Column(name = "nb_offer")
    private Integer nbOffer;

    @Column(name = "nb_item")
    private Integer nbItem;

    @Column(name = "nb_product")
    private Integer nbProduct;

    @ManyToOne
    private Perimeter perimeter;

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

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public Integer getNbOffer() {
        return nbOffer;
    }

    public void setNbOffer(Integer nbOffer) {
        this.nbOffer = nbOffer;
    }

    public Integer getNbItem() {
        return nbItem;
    }

    public void setNbItem(Integer nbItem) {
        this.nbItem = nbItem;
    }

    public Integer getNbProduct() {
        return nbProduct;
    }

    public void setNbProduct(Integer nbProduct) {
        this.nbProduct = nbProduct;
    }

    public Perimeter getPerimeter() {
        return perimeter;
    }

    public void setPerimeter(Perimeter perimeter) {
        this.perimeter = perimeter;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Condition condition = (Condition) o;

        if ( ! Objects.equals(id, condition.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Condition{" +
                "id=" + id +
                ", author='" + author + "'" +
                ", updateDate='" + updateDate + "'" +
                ", state='" + state + "'" +
                ", query='" + query + "'" +
                ", nbOffer='" + nbOffer + "'" +
                ", nbItem='" + nbItem + "'" +
                ", nbProduct='" + nbProduct + "'" +
                '}';
    }
}
