package com.sapo.shipping.repository;

import com.sapo.shipping.entity.ShippingOrder;
import com.sapo.shipping.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

    @Query("SELECT so " +
            "FROM ShippingOrder so " +
            "JOIN OrderStatus os ON os.shippingOrder.id = so.id " +
            "WHERE ( " +
            "    CASE :statusFilter " +
            "       WHEN 'successful' THEN ( " +
            "          EXISTS (SELECT 1 FROM OrderStatus os2 WHERE os2.status IN ('Giao hàng thành công','Đơn hủy') AND os2.shipper.id = :shipperId)" +
            "       ) " +
            "       WHEN 'unSuccessful' THEN " +
            "           (os.status IN ('Đang lấy hàng','Lấy hàng thành công','Đang giao hàng') AND os.shipper.id = :shipperId AND os.id IN (SELECT MAX(os2.id) FROM OrderStatus os2 GROUP BY os2.shippingOrder.id))" +
            "    END " +
            ")")
    List<ShippingOrder> getFilteredShippingOrders(@Param("shipperId") Integer shipperId, @Param("statusFilter") String statusFilter);

    Optional<User> findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.role = 'SHIPPER' AND u.workingStatus = true")
    List<User> getAllAvailableShippers();

    @Query("SELECT u FROM User u WHERE u.role = 'SHIPPER' AND (:keyWord IS NULL OR u.fullName LIKE %:keyWord% OR u.phone LIKE %:keyWord% OR String(u.id) LIKE %:keyWord%)")
    Page<User> getAllShippers(PageRequest pageRequest, @Param("keyWord") String keyWord);

}
