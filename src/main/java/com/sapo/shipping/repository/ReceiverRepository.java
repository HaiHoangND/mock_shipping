package com.sapo.shipping.repository;

import com.sapo.shipping.entity.Product;
import com.sapo.shipping.entity.Receiver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReceiverRepository extends JpaRepository<Receiver,Integer> {
}
