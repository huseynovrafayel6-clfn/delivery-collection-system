package com.webperside.deliverycollectionsystem.services.assignments;


import com.webperside.deliverycollectionsystem.model.dto.AssignmentDto;
import com.webperside.deliverycollectionsystem.model.dto.ShipmentProjection;
import com.webperside.deliverycollectionsystem.model.payload.assignment.AssignmentPayload;
import com.webperside.deliverycollectionsystem.model.response.assignment.AssignmentResponse;
import com.webperside.deliverycollectionsystem.model.response.shipment.ShipmentResponse;


import java.util.List;

public interface AssignmentService {

    Long addAssignment(AssignmentPayload payload);

    Long updateAssignmentById(Long id, AssignmentPayload payload);

    AssignmentDto getAssignmentById(Long id);

    List<ShipmentResponse> getShipmentByCourierId(Long courierId);

    List<AssignmentDto> getAssignmentsByCourier();
}
