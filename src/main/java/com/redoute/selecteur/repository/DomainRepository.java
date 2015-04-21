package com.redoute.selecteur.repository;

import com.redoute.selecteur.domain.Domain;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Domain entity.
 */
public interface DomainRepository extends JpaRepository<Domain,Long> {

}
