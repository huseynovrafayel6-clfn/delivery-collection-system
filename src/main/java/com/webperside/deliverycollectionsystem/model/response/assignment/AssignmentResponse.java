package com.webperside.deliverycollectionsystem.model.response.assignment;

import com.webperside.deliverycollectionsystem.model.entity.Shipment;
import com.webperside.deliverycollectionsystem.model.entity.User;
import com.webperside.deliverycollectionsystem.model.enums.assignments.AssignmentStatus;
import com.webperside.deliverycollectionsystem.model.enums.assignments.AssignmentType;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AssignmentResponse {

    Long id;
    Shipment shipment;
    User courier;
    AssignmentStatus status;
    AssignmentType assignmentType;


}
