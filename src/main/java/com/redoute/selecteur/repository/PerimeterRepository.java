package com.redoute.selecteur.repository;

import com.redoute.selecteur.domain.Perimeter;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Perimeter entity.
 */
public interface PerimeterRepository extends JpaRepository<Perimeter,Long> {

}
