package com.webperside.deliverycollectionsystem.model.payload.deliveries;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class DeliveryPayload {

    Long shipmentId;
    Long courierId;
    String receiverName;

}
