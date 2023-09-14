package com.sapo.shipping.repository;


import com.sapo.shipping.entity.Receiver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiverRepository extends JpaRepository<Receiver,Integer> {
    @Query("SELECT r FROM Receiver r WHERE r.phone = :phone and r.address = :address and r.shopOwner.id = :shopOwnerId")
    Receiver findByPhoneAndAddress(@Param("phone") String phone, @Param("address") String address, @Param("shopOwnerId") int shopOwnerId);

    @Query("SELECT r FROM Receiver r WHERE r.shopOwner.id = :shopOwnerId AND (:keyWord IS NULL OR CONCAT(r.name, r.address, r.phone) LIKE %:keyWord%)")
    Page<Receiver> getReceiversByShopOwnerId(@Param("shopOwnerId") Integer shopOwnerId, PageRequest pageRequest, @Param("keyWord") String keyWord);
}
