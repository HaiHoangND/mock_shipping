package com.sapo.shipping.repository;

import com.sapo.shipping.dto.MonthProfit;
import com.sapo.shipping.entity.ShippingOrder;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ShippingOrderRepository extends JpaRepository<ShippingOrder,Integer> {
    @Query("SELECT so FROM ShippingOrder so where so.orderCode LIKE %:orderCode% ORDER BY so.updatedAt DESC")
    Page<ShippingOrder> findByOrderCode(@Param("orderCode") String orderCode, PageRequest pageRequest);

    @Query("SELECT so FROM ShippingOrder so where so.orderCode = :orderCode ORDER BY so.updatedAt DESC")
    ShippingOrder findByCode(@Param("orderCode") String orderCode);

    @Query("SELECT so FROM ShippingOrder so ORDER BY so.updatedAt DESC")
    Page<ShippingOrder> findAll(PageRequest pageRequest);

    @Query("SELECT COUNT(so) " +
            "FROM ShippingOrder so " +
            "JOIN OrderStatus os ON os.shippingOrder.id = so.id " +
            "WHERE os.isArriving = TRUE " +
            "AND (:shopOwnerId IS NULL OR so.shopOwner.id = :shopOwnerId)" +
            "AND os.id IN (" +
            "    SELECT MAX(os2.id) " +
            "    FROM OrderStatus os2 " +
            "    GROUP BY os2.shippingOrder.id " +
            ")"
    )
    Long countShippingOrdersAreDelivering(@Param("shopOwnerId") Integer shopOwnerId);

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

//    @Query("SELECT NEW com.sapo.shipping.dto.MonthProfit(DATE(so.updatedAt), SUM(so.serviceFee)) " +
//            "FROM ShippingOrder so " +
//            "JOIN OrderStatus os ON os.shippingOrder.id = so.id " +
//            "WHERE os.id IN (" +
//            "    SELECT MAX(os2.id) " +
//            "    FROM OrderStatus os2 " +
//            "    GROUP BY os2.shippingOrder.id " +
//            ")" +
//            "AND os.status = 'Đã đưa tiền cho chủ shop' " +
//            "AND YEAR(so.updatedAt) = :year " +
//            "AND MONTH(so.updatedAt) = :month " +
//            "GROUP BY DATE(so.updatedAt)")
//    List<MonthProfit> statisticRevenueOfYear(@Param("month") Integer month, @Param("year") Integer year);

    @Query("SELECT so FROM ShippingOrder so WHERE so.shopOwner.id = :shopOwnerId AND (:orderCode IS NULL OR so.orderCode LIKE %:orderCode%) ORDER BY so.updatedAt DESC")
    Page<ShippingOrder> getShippingOrderByShopOwner(@Param("shopOwnerId") Integer shopOwnerId, PageRequest pageRequest, @Param("orderCode") String orderCode);

    @Query("SELECT so FROM ShippingOrder so " +
            "JOIN OrderStatus os ON os.shippingOrder.id = so.id " +
            "WHERE os.id IN (SELECT MAX(os2.id) FROM OrderStatus os2 GROUP BY os2.shippingOrder.id) " +
            "AND os.status = 'Đã đưa tiền cho chủ shop' " +
            "AND so.shopOwner.id = :shopOwnerId")
    List<ShippingOrder> getAccountedShippingOrdersByShopOwnerId(@Param("shopOwnerId") Integer shopOwnerId);

    @Query("SELECT so FROM ShippingOrder so " +
            "JOIN OrderStatus os ON os.shippingOrder.id = so.id " +
            "WHERE os.id IN (SELECT MAX(os2.id) FROM OrderStatus os2 GROUP BY os2.shippingOrder.id) " +
            "AND os.status = 'Đã đưa tiền cho chủ shop' " +
            "AND so.shopOwner.id = :shopOwnerId " +
            "AND DAY(so.updatedAt) = :day " +
            "AND MONTH(so.updatedAt) = :month " +
            "AND YEAR(so.updatedAt) = :year " +
            "GROUP BY DATE(so.updatedAt)"
    )
    List<ShippingOrder> getAccountedShippingOrdersByShopOwnerIdByDay(@Param("day") Integer day, @Param("month") Integer month, @Param("year") Integer year, @Param("shopOwnerId") Integer shopOwnerId);

    @Query("SELECT COUNT(so) FROM ShippingOrder so " +
            "JOIN OrderStatus os ON os.shippingOrder.id = so.id " +
            "WHERE os.id IN (SELECT MAX(os2.id) FROM OrderStatus os2 GROUP BY os2.shippingOrder.id) " +
            "AND os.status IN ('Giao hàng thành công','Quản lý đã nhận tiền','Đã đưa tiền cho chủ shop') " +
            "AND so.receiver.id = :receiverId")
    Integer countSuccessfulShippingOrdersByReceiverId(@Param("receiverId") Integer receiverId);

    @Query("SELECT COUNT(so) FROM ShippingOrder so " +
            "JOIN OrderStatus os ON os.shippingOrder.id = so.id " +
            "WHERE os.id IN (SELECT MAX(os2.id) FROM OrderStatus os2 GROUP BY os2.shippingOrder.id) " +
            "AND so.receiver.id = :receiverId")
    Integer countShippingOrdersByReceiverId(@Param("receiverId") Integer receiverId);
}
