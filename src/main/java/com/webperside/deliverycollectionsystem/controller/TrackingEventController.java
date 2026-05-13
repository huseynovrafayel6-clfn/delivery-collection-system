package com.webperside.deliverycollectionsystem.controller;

import com.fasterxml.jackson.annotation.JsonRawValue;
import com.webperside.deliverycollectionsystem.model.enums.shipments.ShipmentStatus;
import com.webperside.deliverycollectionsystem.model.payload.tracking_events.TrackingEventPayload;
import com.webperside.deliverycollectionsystem.model.response.BaseResponse;
import com.webperside.deliverycollectionsystem.model.response.tracking_events.TrackEventWithShipmentResponse;
import com.webperside.deliverycollectionsystem.model.response.tracking_events.TrackingEventResponse;
import com.webperside.deliverycollectionsystem.services.tracking_events.TrackingEventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tracking-events")
@RequiredArgsConstructor
public class TrackingEventController {

    private final TrackingEventService trackingEventService;

    @PatchMapping("/shipments/{shipmentId}/status")
    public BaseResponse<Long> changeStatusByShipmentId(@PathVariable Long shipmentId,
                                                       @RequestBody ShipmentStatus status){
        return BaseResponse.success(trackingEventService.changeStatusByShipmentId(shipmentId, status));
    }

    @GetMapping("/shipments/{shipmentId}/status-history")
    public BaseResponse<List<TrackingEventResponse>> getAllByShipmentId(@PathVariable Long shipmentId){
        return BaseResponse.success(trackingEventService.getAllByShipmentId(shipmentId));
    }

    @GetMapping("/{trackingNumber}")
    public BaseResponse<TrackEventWithShipmentResponse> getTrackEventWithShipment(@PathVariable String trackingNumber){
        return BaseResponse.success(trackingEventService.getTrackEventWithShipmentByTrackingNumber(trackingNumber));
    }

    @PostMapping("/add")
    public BaseResponse<Long> addTrackingEvent(TrackingEventPayload payload){
        return BaseResponse.success(trackingEventService.addTrackingEvents(payload));
    }
}
