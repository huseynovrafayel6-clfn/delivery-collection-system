package com.webperside.deliverycollectionsystem.model.payload.shipment;

import com.webperside.deliverycollectionsystem.model.enums.shipments.DeliveryType;
import com.webperside.deliverycollectionsystem.model.enums.shipments.ServiceType;
import com.webperside.deliverycollectionsystem.model.enums.shipments.ShipmentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ShipmentPayload {

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

}
