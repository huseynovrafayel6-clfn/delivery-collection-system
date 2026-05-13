package com.webperside.deliverycollectionsystem.model.payload.tracking_events;

import com.webperside.deliverycollectionsystem.model.enums.shipments.ShipmentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrackingEventPayload {

    Long shipmentId;
    ShipmentStatus status;

}
