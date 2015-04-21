package com.redoute.selecteur.repository;

import com.redoute.selecteur.domain.Exposition;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Exposition entity.
 */
public interface ExpositionRepository extends JpaRepository<Exposition,Long> {

}
