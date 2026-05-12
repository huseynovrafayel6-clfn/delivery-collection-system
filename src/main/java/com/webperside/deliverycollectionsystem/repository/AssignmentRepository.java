package com.webperside.deliverycollectionsystem.repository;

import com.webperside.deliverycollectionsystem.model.dto.AssignmentDto;
import com.webperside.deliverycollectionsystem.model.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {

    @Query("""
            select new com.webperside.deliverycollectionsystem.model.dto.AssignmentDto (
    a.id,
    a.shipment.id,
    a.courier.id,
    a.status,
    a.assignmentType
)
from Assignment a
where a.id = :id
""")
    Optional<AssignmentDto> findAssignmentDtoById(Long id);

    @Query("""
            select new com.webperside.deliverycollectionsystem.model.dto.AssignmentDto (
    a.id,
    a.shipment.id,
    a.courier.id,
    a.status,
    a.assignmentType
)
from Assignment a
where a.courier.id = :courierId
""")
    List<AssignmentDto> findAssignmentsDtoByCourierId(Long courierId);

}
