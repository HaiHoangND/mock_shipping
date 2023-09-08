package com.sapo.shipping.repository;

import com.sapo.shipping.auth.permission.Role;
import com.sapo.shipping.entity.ShippingOrder;
import com.sapo.shipping.entity.User;
import com.sapo.shipping.entity.Warehouse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WarehouseRepository extends JpaRepository<Warehouse, Integer> {

    @Query("SELECT wh FROM Warehouse wh WHERE wh.id >= 4")
    @Override
    List<Warehouse> findAll();

    @Query("SELECT u FROM User u " +
            "WHERE (:warehouseId IS NULL OR u.warehouseId = :warehouseId) " +
            "AND (:role IS NULL OR u.role = :role ) " +
            "AND u.workingStatus = true "
    )
    List<User> getAllUsersByWarehouseId(@Param("warehouseId") Integer warehouseId, @Param("role") Role role);

    @Query("SELECT COUNT(so) " +
            "FROM ShippingOrder so " +
            "JOIN OrderStatus os ON os.shippingOrder.id = so.id " +
            "JOIN OrderRoute oro ON os.orderRoute.id = oro.id " +
            "WHERE oro.warehouse.id = :warehouseId " +
            "AND os.isArriving = TRUE " +
            "AND os.id IN (" +
            "    SELECT MAX(os2.id) " +
            "    FROM OrderStatus os2 " +
            "    GROUP BY os2.shippingOrder.id " +
            ")"
    )
    Long countShippingOrdersBeingDelivered(@Param("warehouseId") Integer warehouseId);

    @Query("SELECT so " +
            "FROM ShippingOrder so " +
            "JOIN OrderStatus os ON os.shippingOrder.id = so.id " +
            "JOIN OrderRoute oro ON os.orderRoute.id = oro.id " +
            "WHERE (oro.warehouse.id = :warehouseId OR " +
            "(os.orderRoute.warehouse.id = 2 AND " +
            "os.status IN ('Lấy hàng thành công', 'Đang giao hàng') AND " +
            "(SELECT oro2.warehouse.id FROM OrderRoute oro2 WHERE " +
            "oro2.routeId = 2 AND oro2.shippingOrder.id = so.id) = :warehouseId) OR " +
            "((SELECT oro3.warehouse.id FROM OrderRoute oro3 WHERE " +
            "oro3.shippingOrder.id = os.shippingOrder.id " +
            "ORDER BY oro3.routeId DESC LIMIT 1 OFFSET 1) = :warehouseId AND " +
            "os.status IN ('Giao hàng thành công', 'Đơn hủy'))) " +
            "AND os.id IN (" +
            "SELECT MAX(os2.id) " +
            "FROM OrderStatus os2 " +
            "GROUP BY os2.shippingOrder.id" +
            ")")
    List<ShippingOrder> getAllShippingOrdersByWarehouseId(@Param("warehouseId") Integer warehouseId);

    @Query("SELECT so FROM ShippingOrder so WHERE NOT EXISTS (SELECT 1 FROM OrderStatus os WHERE os.shippingOrder.id = so.id)")
    List<ShippingOrder> getAllShippingOrdersNoStatus();
}
