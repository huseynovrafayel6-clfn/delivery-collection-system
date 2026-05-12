package com.webperside.deliverycollectionsystem.model.dto;


import com.webperside.deliverycollectionsystem.model.enums.assignments.AssignmentStatus;
import com.webperside.deliverycollectionsystem.model.enums.assignments.AssignmentType;



public record AssignmentDto(

        Long id,
        Long shipmentId,
        Long courierId,
        AssignmentStatus status,
        AssignmentType assignmentType

) {
}