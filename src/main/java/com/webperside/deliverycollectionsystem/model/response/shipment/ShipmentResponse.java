package com.webperside.deliverycollectionsystem.model.response.shipment;

import com.webperside.deliverycollectionsystem.model.dto.UserInfo;
import com.webperside.deliverycollectionsystem.model.enums.shipments.DeliveryType;
import com.webperside.deliverycollectionsystem.model.enums.shipments.ServiceType;
import com.webperside.deliverycollectionsystem.model.enums.shipments.ShipmentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShipmentResponse {

    Long id;
    String trackingNumber;
    UserInfo sender;
    String receiverName;
    String receiverEmail;
    String receiverPhoneNumber;
    String deliveryAddress;
    BigDecimal weight;
    String dimensions;
    ServiceType serviceType;
    DeliveryType deliveryType;
    String notes;
    ShipmentStatus status;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
