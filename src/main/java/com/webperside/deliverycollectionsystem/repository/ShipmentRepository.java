package com.webperside.deliverycollectionsystem.repository;

import com.webperside.deliverycollectionsystem.model.dto.ShipmentProjection;
import com.webperside.deliverycollectionsystem.model.entity.Shipment;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ShipmentRepository  extends JpaRepository<Shipment, Long> {

    Optional<Shipment> findByTrackingNumber(String trackingNumber);

    @Query("""
      select new com.webperside.deliverycollectionsystem.model.dto.ShipmentProjection (
    s.id,
    s.sender.id,
    s.deliveryAddress,
    s.weight,
    s.dimensions,
    s.serviceType,
    s.deliveryType,
    s.notes,
    s.trackingNumber,
    s.receiverName,
    s.receiverEmail,
    s.receiverPhoneNumber,
    s.status
    )
from Shipment s
where s.id = :id
""")
    Optional<ShipmentProjection> findProjectionById(Long id);

    @EntityGraph(attributePaths = {"sender"})
    List<Shipment> findAllByIdInAndIsDeletedFalse(Iterable<Long> ids);

    List<Shipment> findAllBySenderIdAndIsDeletedFalseOrderByCreatedAtDesc(
            Long senderId
    );

}


