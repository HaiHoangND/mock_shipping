package com.sapo.shipping.repository;


import com.sapo.shipping.entity.Receiver;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface ReceiverRepository extends JpaRepository<Receiver,Integer> {
    @Query("SELECT r FROM Receiver r WHERE r.phone = :phone and r.address = :address")
    Receiver findByPhoneAndAddress(@Param("phone") String phone, @Param("address") String address);
}
