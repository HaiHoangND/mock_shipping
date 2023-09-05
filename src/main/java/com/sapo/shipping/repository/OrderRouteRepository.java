package com.sapo.shipping.repository;

import com.sapo.shipping.entity.OrderRoute;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRouteRepository extends JpaRepository<OrderRoute,Integer> {
    @Query("SELECT oro FROM OrderRoute oro WHERE oro.shippingOrder.id = :orderId AND oro.routeId = :routeId")
    OrderRoute getRouteByOrderIdAndRouteId(@Param("orderId") Integer orderId, @Param("routeId") Integer routeId);
}
