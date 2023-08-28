package com.sapo.shipping.repository;

import com.sapo.shipping.entity.Sender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SenderRepository extends JpaRepository<Sender,Integer> {
}
