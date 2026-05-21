package com.webperside.deliverycollectionsystem.repository;

import com.webperside.deliverycollectionsystem.model.entity.Delivery;
import com.webperside.deliverycollectionsystem.model.enums.delivery.DeliveryFailureReason;
import com.webperside.deliverycollectionsystem.model.enums.delivery.DeliveryStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

    long countByStatus(DeliveryStatus status);

    long countByFailureReason(DeliveryFailureReason reason);

}
