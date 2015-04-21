package com.redoute.selecteur.repository;

import com.redoute.selecteur.domain.OffersSerie;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the OffersSerie entity.
 */
public interface OffersSerieRepository extends JpaRepository<OffersSerie,Long> {

}
