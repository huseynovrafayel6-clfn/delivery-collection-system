package com.webperside.deliverycollectionsystem.model.response.tracking_events;

import com.webperside.deliverycollectionsystem.model.enums.shipments.ShipmentStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrackingEventResponse {

    Long id;
    Long shipmentId;
    ShipmentStatus status;
    String description;
    LocalDateTime createdAt;


}
