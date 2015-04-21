package com.redoute.selecteur.repository;

import com.redoute.selecteur.domain.CommercialOperation;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the CommercialOperation entity.
 */
public interface CommercialOperationRepository extends JpaRepository<CommercialOperation,Long> {

}
