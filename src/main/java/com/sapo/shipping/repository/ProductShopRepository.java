package com.sapo.shipping.repository;

import com.sapo.shipping.entity.Product;
import com.sapo.shipping.entity.ProductShop;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ProductShopRepository extends JpaRepository<ProductShop,Integer> {
    @Query("SELECT ps FROM ProductShop ps WHERE ps.shopOwner.id = :shopOwnerId AND (:keyWord IS NULL OR CONCAT(ps.name,CAST(ps.quantity AS STRING),CAST(ps.price AS STRING),CAST(ps.weight AS STRING), ps.description, ps.productCode) LIKE %:keyWord%)")
    Page<ProductShop> getProductShopsByShopOwnerId(@Param("shopOwnerId") Integer shopOwnerId, PageRequest pageRequest, @Param("keyWord") String keyWord);
}
