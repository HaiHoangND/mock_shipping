package com.sapo.shipping.repository;

import com.sapo.shipping.entity.OrderRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRouteRepository extends JpaRepository<OrderRoute,Integer> {
}
