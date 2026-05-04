package com.webperside.deliverycollectionsystem.controller;

import com.webperside.deliverycollectionsystem.model.payload.shipment.ShipmentPayload;
import com.webperside.deliverycollectionsystem.model.response.BaseResponse;
import com.webperside.deliverycollectionsystem.model.response.shipment.ShipmentResponse;
import com.webperside.deliverycollectionsystem.services.shipments.ShipmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/shipments")
@RequiredArgsConstructor
public class ShipmentController {

    private final ShipmentService shipmentService;

    @PostMapping("/add")
    public BaseResponse<Long> addShipment(@RequestBody ShipmentPayload payload) {
        return BaseResponse.success(shipmentService.addShipment(payload));
    }

    @GetMapping("/get-all")
    public BaseResponse<Page<ShipmentResponse>> getShipments(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        return BaseResponse.success(shipmentService.getShipments(page, size));
    }

    @GetMapping("/get/{id}")
    public BaseResponse<ShipmentResponse> getShipmentById(@PathVariable Long id) {
        return BaseResponse.success(shipmentService.getShipmentById(id));
    }

    @GetMapping("/get/tracking/{trackingNumber}")
    public BaseResponse<ShipmentResponse> getShipmentByTrackingNumber(@PathVariable String trackingNumber) {
        return BaseResponse.success(shipmentService.getShipmentByTrackingNumber(trackingNumber));
    }

    @PutMapping("/update/{id}")
    public BaseResponse<Long> updateShipmentById(@PathVariable Long id, @RequestBody ShipmentPayload payload) {
        return BaseResponse.success(shipmentService.updateShipmentById(id, payload));
    }

    @PatchMapping("/cancel/{id}")
    public BaseResponse<Long> cancelShipmentById(@PathVariable Long id) {
        return BaseResponse.success(shipmentService.cancelShipmentById(id));
    }

    @DeleteMapping("/delete/soft/{id}")
    public BaseResponse<Long> softDeleteShipmentById(@PathVariable Long id) {
        return BaseResponse.success(shipmentService.softDeleteShipmentById(id));
    }

    @DeleteMapping("/delete/hard/{id}")
    public BaseResponse<Long> hardDeleteShipmentById(@PathVariable Long id) {
        return BaseResponse.success(shipmentService.hardDeleteShipmentById(id));
    }
}
