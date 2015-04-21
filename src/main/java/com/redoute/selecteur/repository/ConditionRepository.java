package com.redoute.selecteur.repository;

import com.redoute.selecteur.domain.Condition;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Condition entity.
 */
public interface ConditionRepository extends JpaRepository<Condition,Long> {

}
