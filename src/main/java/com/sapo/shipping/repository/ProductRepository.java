package com.sapo.shipping.repository;

import com.sapo.shipping.entity.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product,Integer> {
    @Query("SELECT SUM(p.quantity) FROM Product p JOIN OrderStatus os ON p.shippingOrder.id = os.shippingOrder.id WHERE p.shippingOrder.shopOwner.id = :shopOwnerId AND os.id IN (SELECT MAX(os2.id) FROM OrderStatus os2 GROUP BY os2.shippingOrder.id) AND os.status IN ('Giao hàng thành công','Quản lý đã nhận tiền','Đã đưa tiền cho chủ shop') AND p.productCode = :productCode GROUP BY p.productCode")
    Integer getSumSoldProduct(@Param("shopOwnerId") Integer shopOwnerId,@Param("productCode") String productCode);
}
