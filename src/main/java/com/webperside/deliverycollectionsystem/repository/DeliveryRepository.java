package com.webperside.deliverycollectionsystem.repository;

import com.webperside.deliverycollectionsystem.model.entity.Delivery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {
}
