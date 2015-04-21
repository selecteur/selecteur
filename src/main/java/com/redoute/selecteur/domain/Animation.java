package com.redoute.selecteur.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Cache;
import org.hibernate.annotations.CacheConcurrencyStrategy;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.Objects;

/**
 * A Animation.
 */
@Entity
@Table(name = "T_ANIMATION")
@Cache(usage = CacheConcurrencyStrategy.NONSTRICT_READ_WRITE)
public class Animation implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(name = "provider_animation_code")
    private String providerAnimationCode;

    @Column(name = "provider_list_code")
    private String providerListCode;

    @ManyToOne
    private OffersSerie offersSerie;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProviderAnimationCode() {
        return providerAnimationCode;
    }

    public void setProviderAnimationCode(String providerAnimationCode) {
        this.providerAnimationCode = providerAnimationCode;
    }

    public String getProviderListCode() {
        return providerListCode;
    }

    public void setProviderListCode(String providerListCode) {
        this.providerListCode = providerListCode;
    }

    public OffersSerie getOffersSerie() {
        return offersSerie;
    }

    public void setOffersSerie(OffersSerie offersSerie) {
        this.offersSerie = offersSerie;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Animation animation = (Animation) o;

        if ( ! Objects.equals(id, animation.id)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "Animation{" +
                "id=" + id +
                ", providerAnimationCode='" + providerAnimationCode + "'" +
                ", providerListCode='" + providerListCode + "'" +
                '}';
    }
}
