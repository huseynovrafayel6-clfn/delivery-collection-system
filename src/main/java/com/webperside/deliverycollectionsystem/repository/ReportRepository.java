package com.webperside.deliverycollectionsystem.repository;

import com.webperside.deliverycollectionsystem.model.dto.CourierPerformanceProjection;
import com.webperside.deliverycollectionsystem.model.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReportRepository extends JpaRepository<Assignment, Long> {

    @Query(value = """
SELECT new com.webperside.deliverycollectionsystem.model.dto.CourierPerformanceProjection(
    c.id,
    c.name,
    COUNT(DISTINCT a.id),
    COALESCE(SUM(CASE WHEN s.status = com.webperside.deliverycollectionsystem.model.enums.shipments.ShipmentStatus.DELIVERED THEN 1L ELSE 0L END), 0L),
    COALESCE(SUM(CASE WHEN s.status = com.webperside.deliverycollectionsystem.model.enums.shipments.ShipmentStatus.DELIVERY_ATTEMPT_FAILED THEN 1L ELSE 0L END), 0L)
)
FROM Assignment a
JOIN a.courier c
JOIN a.shipment s
GROUP BY c.id, c.name
""")
    List<CourierPerformanceProjection> getCourierPerformance();

//
//    @Query(value = "")
//    List<CourierPerformanceProjection> getCourierPerformance2();

}
