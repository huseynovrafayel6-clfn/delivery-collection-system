package com.webperside.deliverycollectionsystem.controller;

import com.webperside.deliverycollectionsystem.model.enums.delivery.DeliveryFailureReason;
import com.webperside.deliverycollectionsystem.model.payload.deliveries.DeliveryPayload;
import com.webperside.deliverycollectionsystem.model.response.BaseResponse;
import com.webperside.deliverycollectionsystem.model.response.deliveries.DeliveryResponse;
import com.webperside.deliverycollectionsystem.services.deliveries.DeliveryService;
import com.webperside.deliverycollectionsystem.services.deliveries.business.DeliveryBusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/deliveries")
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;
    private final DeliveryBusinessService deliveryBusinessService;

    @PostMapping("/add")
    public BaseResponse<DeliveryResponse> addDelivery(DeliveryPayload payload){
        return BaseResponse.success(deliveryService.addDelivery(payload));
    }

    @PostMapping("/{deliveryId}/start")
    public BaseResponse<DeliveryResponse> startDelivery(@PathVariable Long deliveryId){
        return BaseResponse.success(deliveryBusinessService.startDelivery(deliveryId));
    }

    @PostMapping("/{deliveryId}/confirm")
    public BaseResponse<DeliveryResponse> confirmDelivery(@PathVariable Long deliveryId){
        return BaseResponse.success(deliveryBusinessService.confirmDelivery(deliveryId));
    }

    @PostMapping("/{deliveryId}/fail")
    public BaseResponse<DeliveryResponse> failDelivery(@PathVariable Long deliveryId,
                                                       @RequestBody DeliveryFailureReason deliveryFailureReason){
        return BaseResponse.success(deliveryBusinessService.failDelivery(deliveryId, deliveryFailureReason));
    }

    @PostMapping("/{deliveryId}/reschedule")
    public BaseResponse<DeliveryResponse> rescheduleDelivery(@PathVariable Long deliveryId,
                                                             @RequestBody LocalDateTime rescheduledAt){
        return BaseResponse.success(deliveryBusinessService.rescheduleDelivery(deliveryId, rescheduledAt));
    }

    @GetMapping("/{id}")
    public BaseResponse<DeliveryResponse> getById(@PathVariable Long id){
        return BaseResponse.success(deliveryService.getById(id));
    }

}
