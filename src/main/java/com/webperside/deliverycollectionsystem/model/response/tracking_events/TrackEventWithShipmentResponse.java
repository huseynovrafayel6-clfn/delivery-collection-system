package com.webperside.deliverycollectionsystem.model.response.tracking_events;

import com.webperside.deliverycollectionsystem.model.response.shipment.ShipmentResponse;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrackEventWithShipmentResponse {

    ShipmentResponse shipmentResponse;
    List<TrackingEventResponse> trackingEvents;

}
