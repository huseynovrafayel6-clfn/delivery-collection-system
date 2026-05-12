package com.webperside.deliverycollectionsystem.model.dto;

import com.webperside.deliverycollectionsystem.model.enums.shipments.DeliveryType;
import com.webperside.deliverycollectionsystem.model.enums.shipments.ServiceType;
import com.webperside.deliverycollectionsystem.model.enums.shipments.ShipmentStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ShipmentProjection(

        Long id,
        Long senderId,
        String deliveryAddress,
        BigDecimal weight,
        String dimensions,
        ServiceType serviceType,
        DeliveryType deliveryType,
        String notes,
        String trackingNumber,
        String receiverName,
        String receiverEmail,
        String receiverPhoneNumber,
        ShipmentStatus status
) {
}