package com.sapo.shipping.repository;

import com.sapo.shipping.entity.ShippingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ShippingOrderRepository extends JpaRepository<ShippingOrder,Integer> {
    ShippingOrder findByOrderCode( String orderCode);

    @Query("SELECT COUNT(so) " +
            "FROM ShippingOrder so " +
            "JOIN OrderStatus os ON os.shippingOrder.id = so.id " +
            "WHERE os.isArriving = TRUE " +
            "AND os.id IN (" +
            "    SELECT MAX(os2.id) " +
            "    FROM OrderStatus os2 " +
            "    GROUP BY os2.shippingOrder.id " +
            ")"
    )
    Long countShippingOrdersAreDelivering();

    @Query("SELECT COUNT(so) " +
            "FROM ShippingOrder so " +
            "JOIN OrderStatus os ON os.shippingOrder.id = so.id " +
            "WHERE ( os.status = 'Giao hàng thành công' OR os.status = 'Quản lý đã nhận tiền' OR os.status = 'Đã thanh toán cho chủ shop' ) " +
            "AND os.id IN (" +
            "    SELECT MAX(os2.id) " +
            "    FROM OrderStatus os2 " +
            "    GROUP BY os2.shippingOrder.id " +
            ")"
    )
    Long countSuccessfulDeliveredShippingOrders();
}
