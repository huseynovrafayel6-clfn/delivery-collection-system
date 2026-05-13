package com.webperside.deliverycollectionsystem.repository;

import com.webperside.deliverycollectionsystem.model.entity.TrackingEvent;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TrackingEventRepository extends JpaRepository<TrackingEvent, Long> {

    List<TrackingEvent> findAllByShipmentId(Long shipmentId);

}
