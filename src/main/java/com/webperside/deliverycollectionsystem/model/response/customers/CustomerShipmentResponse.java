package com.webperside.deliverycollectionsystem.model.response.customers;

import com.webperside.deliverycollectionsystem.model.enums.shipments.ShipmentStatus;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class CustomerShipmentResponse {

    private Long shipmentId;

    private String trackingNumber;

    private ShipmentStatus status;

    private String deliveryAddress;

    private LocalDateTime createdAt;

}
