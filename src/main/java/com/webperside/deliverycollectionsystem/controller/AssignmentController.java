package com.webperside.deliverycollectionsystem.controller;

import com.webperside.deliverycollectionsystem.model.dto.AssignmentDto;
import com.webperside.deliverycollectionsystem.model.dto.ShipmentProjection;
import com.webperside.deliverycollectionsystem.model.payload.assignment.AssignmentPayload;
import com.webperside.deliverycollectionsystem.model.response.BaseResponse;
import com.webperside.deliverycollectionsystem.model.response.shipment.ShipmentResponse;
import com.webperside.deliverycollectionsystem.services.assignments.AssignmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController
@RequestMapping("/assignments")
@RequiredArgsConstructor
public class AssignmentController {

    private final AssignmentService assignmentService;

    @PostMapping("/add")
    public BaseResponse<Long> addAssignment(@RequestBody AssignmentPayload payload) {
        return BaseResponse.success(assignmentService.addAssignment(payload));
    }

    @PutMapping("/update/{id}")
    public BaseResponse<Long> updateAssignment(@PathVariable("id") Long id, @RequestBody AssignmentPayload payload) {
        return BaseResponse.success(assignmentService.updateAssignmentById(id, payload));
    }

    @GetMapping("/get/{id}")
    public BaseResponse<AssignmentDto> getAssignmentById(@PathVariable("id") Long id) {
        return BaseResponse.success(assignmentService.getAssignmentById(id));
    }

    @GetMapping("/courier/get/{courierId}")
    public BaseResponse<List<ShipmentResponse>> getShipmentByCourierId(@PathVariable("courierId") Long courierId) {
        return BaseResponse.success(assignmentService.getShipmentByCourierId(courierId));
    }

    @GetMapping("/my")
    public BaseResponse<List<AssignmentDto>> getShipmentByCourierId() {
        return BaseResponse.success(assignmentService.getAssignmentsByCourier());
    }
}
