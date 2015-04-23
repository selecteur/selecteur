package com.redoute.selecteur.repository;

import com.redoute.selecteur.domain.Perimeter;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.*;

/**
 * Spring Data JPA repository for the Perimeter entity.
 */
public interface PerimeterRepository extends JpaRepository<Perimeter,Long> {

    Page<Perimeter> findByDomainCommercialOperationId(Long commercialOperationId, Pageable pageable);
}
