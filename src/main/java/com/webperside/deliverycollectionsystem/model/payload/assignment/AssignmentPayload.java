package com.webperside.deliverycollectionsystem.model.payload.assignment;

import com.webperside.deliverycollectionsystem.model.enums.assignments.AssignmentStatus;
import com.webperside.deliverycollectionsystem.model.enums.assignments.AssignmentType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssignmentPayload {

    Long shipmentId;
    Long courierId;
    AssignmentStatus status;
    AssignmentType assignmentType;

}
