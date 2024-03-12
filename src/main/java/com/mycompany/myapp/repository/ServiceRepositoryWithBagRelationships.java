package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Service;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;

public interface ServiceRepositoryWithBagRelationships {
    Optional<Service> fetchBagRelationships(Optional<Service> service);

    List<Service> fetchBagRelationships(List<Service> services);

    Page<Service> fetchBagRelationships(Page<Service> services);
}
