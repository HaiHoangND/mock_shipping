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

    @Query("SELECT p FROM Product p WHERE p.shopOwner.id = :shopOwnerId")
    Page<Product> getProductsByShopOwnerId(@Param("shopOwnerId") Integer shopOwnerId, PageRequest pageRequest);
}
