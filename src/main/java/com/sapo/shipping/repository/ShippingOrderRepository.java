package com.sapo.shipping.repository;

import com.sapo.shipping.dto.MonthProfit;
import com.sapo.shipping.entity.ShippingOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShippingOrderRepository extends JpaRepository<ShippingOrder,Integer> {
    @Query("SELECT so FROM ShippingOrder so where so.orderCode = :orderCode")
    ShippingOrder findByOrderCode(@Param("orderCode") String orderCode);

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
            "WHERE ( os.status = 'Giao hàng thành công' OR os.status = 'Quản lý đã nhận tiền' OR os.status = 'Đã đưa tiền cho chủ shop' ) " +
            "AND os.id IN (" +
            "    SELECT MAX(os2.id) " +
            "    FROM OrderStatus os2 " +
            "    GROUP BY os2.shippingOrder.id " +
            ")"
    )
    Long countSuccessfulDeliveredShippingOrders();

    @Query("SELECT SUM(so.serviceFee) " +
            "FROM ShippingOrder so " +
            "JOIN OrderStatus os ON os.shippingOrder.id = so.id " +
            "WHERE os.id IN (" +
            "    SELECT MAX(os2.id) " +
            "    FROM OrderStatus os2 " +
            "    GROUP BY os2.shippingOrder.id " +
            ")" +
            "AND os.status = 'Đã đưa tiền cho chủ shop' " +
            "AND DAY(so.updatedAt) = :day " +
            "AND MONTH(so.updatedAt) = :month " +
            "AND YEAR(so.updatedAt) = :year " +
            "GROUP BY DATE(so.updatedAt)"
    )
    Double getTotalRevenueForDay(@Param("day") Integer day, @Param("month") Integer month, @Param("year") Integer year);

    @Query("SELECT NEW com.sapo.shipping.dto.MonthProfit(MONTH(so.updatedAt), SUM(so.serviceFee)) " +
            "FROM ShippingOrder so " +
            "JOIN OrderStatus os ON os.shippingOrder.id = so.id " +
            "WHERE os.id IN (" +
            "    SELECT MAX(os2.id) " +
            "    FROM OrderStatus os2 " +
            "    GROUP BY os2.shippingOrder.id " +
            ")" +
            "AND os.status = 'Đã đưa tiền cho chủ shop' " +
            "AND YEAR(so.updatedAt) = :year " +
            "GROUP BY MONTH(so.updatedAt)")
    List<MonthProfit> statisticRevenueOfYear(@Param("year") Integer year);

    @Query("SELECT so FROM ShippingOrder so WHERE so.shopOwner.id = :shopOwnerId")
    List<ShippingOrder> getShippingOrderByShopOwner(@Param("shopOwnerId") Integer shopOwnerId);
}
