package com.sapo.shipping.repository;

import com.sapo.shipping.entity.ShippingOrder;
import com.sapo.shipping.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
//    @Query("SELECT so FROM ShippingOrder so " +
//            "JOIN OrderStatus os ON os.shippingOrder.id = so.id " +
//            "WHERE os.shipper.id = :shipperId " +
//            "AND (:statusFilter = 'all' OR (" +
//            "    (:statusFilter = 'arriving' AND os.isArriving = true) OR " +
//            "    (:statusFilter = 'successful' AND os.status IN ('Giao hàng thành công', 'Quản lý đã nhận tiền', 'Đã thanh toán cho chủ shop'))" +
//            ")) " +
//            "AND os.id IN (" +
//            "    SELECT MAX(os2.id) " +
//            "    FROM OrderStatus os2 " +
//            "    GROUP BY os2.shippingOrder.id " +
//            ")")
//    List<ShippingOrder> getFilteredShippingOrders(@Param("shipperId") Integer shipperId, @Param("statusFilter") String statusFilter);

    @Query("SELECT so " +
            "FROM ShippingOrder so " +
            "JOIN OrderStatus os ON os.shippingOrder.id = so.id " +
            "WHERE os.shipper.id = :shipperId " +
            "AND ( " +
            "    CASE :statusFilter " +
            "       WHEN 'successful' THEN ( " +
            "           (SELECT COUNT(os2)  FROM OrderStatus os2 WHERE os2.shipper.id = :shipperId AND os2.shippingOrder.id = os.shippingOrder.id AND os2.status = 'Đang giao hàng') IN (2,4) OR EXISTS (SELECT 1 FROM OrderStatus os2 WHERE os2.shipper.id = :shipperId AND os2.shippingOrder.id = os.shippingOrder.id AND os2.status IN ('Giao hàng thành công','Đơn hàng bị hủy') )" +
            "       ) " +
            "       WHEN 'unSuccessful' THEN " +
            "            ( " +
            "                (os.status IN ('Đang lấy hàng', 'Lấy hàng thành công') AND os.id IN (" +
            "                    SELECT MAX(os2.id) " +
            "                    FROM OrderStatus os2 " +
            "                    GROUP BY os2.shippingOrder.id" +
            "                )) " +
            "                OR " +
            "                ( " +
            "                    (SELECT COUNT(os2) FROM OrderStatus os2 WHERE os2.shipper.id = :shipperId AND os2.shippingOrder.id = os.shippingOrder.id AND os2.status = 'Đang giao hàng') IN (1,3)" +
            "                ) " +
            "           ) " +
            "    END " +
            ")")
    List<ShippingOrder> getFilteredShippingOrders(@Param("shipperId") Integer shipperId, @Param("statusFilter") String statusFilter);

    Optional<User> findByEmail(String email);

}
