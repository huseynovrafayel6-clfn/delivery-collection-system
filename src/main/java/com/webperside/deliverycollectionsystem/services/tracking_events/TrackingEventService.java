package com.webperside.deliverycollectionsystem.services.tracking_events;

import com.webperside.deliverycollectionsystem.model.enums.shipments.ShipmentStatus;
import com.webperside.deliverycollectionsystem.model.payload.tracking_events.TrackingEventPayload;
import com.webperside.deliverycollectionsystem.model.response.tracking_events.TrackEventWithShipmentResponse;
import com.webperside.deliverycollectionsystem.model.response.tracking_events.TrackingEventResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface TrackingEventService {

    Long changeStatusByShipmentId(Long shipmentId, ShipmentStatus status);

    List<TrackingEventResponse> getAllByShipmentId(Long shipmentId);

    TrackEventWithShipmentResponse getTrackEventWithShipmentByTrackingNumber(String trackingNumber);

    Long addTrackingEvents(TrackingEventPayload payload);
}
