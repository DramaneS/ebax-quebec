package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Custumer;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;

/**
 * Spring Data JPA repository for the Custumer entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CustumerRepository extends JpaRepository<Custumer, Long> {}
