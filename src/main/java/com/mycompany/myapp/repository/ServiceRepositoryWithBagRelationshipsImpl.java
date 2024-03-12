package com.mycompany.myapp.repository;

import com.mycompany.myapp.domain.Service;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.IntStream;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

/**
 * Utility repository to load bag relationships based on https://vladmihalcea.com/hibernate-multiplebagfetchexception/
 */
public class ServiceRepositoryWithBagRelationshipsImpl implements ServiceRepositoryWithBagRelationships {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Optional<Service> fetchBagRelationships(Optional<Service> service) {
        return service.map(this::fetchProjects);
    }

    @Override
    public Page<Service> fetchBagRelationships(Page<Service> services) {
        return new PageImpl<>(fetchBagRelationships(services.getContent()), services.getPageable(), services.getTotalElements());
    }

    @Override
    public List<Service> fetchBagRelationships(List<Service> services) {
        return Optional.of(services).map(this::fetchProjects).orElse(Collections.emptyList());
    }

    Service fetchProjects(Service result) {
        return entityManager
            .createQuery("select service from Service service left join fetch service.projects where service.id = :id", Service.class)
            .setParameter("id", result.getId())
            .getSingleResult();
    }

    List<Service> fetchProjects(List<Service> services) {
        HashMap<Object, Integer> order = new HashMap<>();
        IntStream.range(0, services.size()).forEach(index -> order.put(services.get(index).getId(), index));
        List<Service> result = entityManager
            .createQuery("select service from Service service left join fetch service.projects where service in :services", Service.class)
            .setParameter("services", services)
            .getResultList();
        Collections.sort(result, (o1, o2) -> Integer.compare(order.get(o1.getId()), order.get(o2.getId())));
        return result;
    }
}
