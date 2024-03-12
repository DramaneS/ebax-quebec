package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.HydroQuebecRate;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the HydroQuebecRate entity.
 */
@SuppressWarnings("unused")
@Repository
public interface HydroQuebecRateRepository extends JpaRepository<HydroQuebecRate, Long> {}
