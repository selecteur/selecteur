package com.redoute.selecteur.repository;

import com.redoute.selecteur.domain.Animation;
import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Animation entity.
 */
public interface AnimationRepository extends JpaRepository<Animation,Long> {

}
