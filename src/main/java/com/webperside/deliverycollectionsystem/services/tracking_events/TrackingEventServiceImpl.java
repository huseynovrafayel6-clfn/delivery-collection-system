package com.webperside.deliverycollectionsystem.services.tracking_events;

import com.webperside.deliverycollectionsystem.exception.BaseException;
import com.webperside.deliverycollectionsystem.model.entity.Shipment;
import com.webperside.deliverycollectionsystem.model.entity.TrackingEvent;
import com.webperside.deliverycollectionsystem.model.enums.shipments.ShipmentStatus;
import com.webperside.deliverycollectionsystem.model.payload.tracking_events.TrackingEventPayload;
import com.webperside.deliverycollectionsystem.model.response.shipment.ShipmentResponse;
import com.webperside.deliverycollectionsystem.model.response.tracking_events.TrackEventWithShipmentResponse;
import com.webperside.deliverycollectionsystem.model.response.tracking_events.TrackingEventResponse;
import com.webperside.deliverycollectionsystem.repository.ShipmentRepository;
import com.webperside.deliverycollectionsystem.repository.TrackingEventRepository;
import com.webperside.deliverycollectionsystem.services.shipments.ShipmentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class TrackingEventServiceImpl implements TrackingEventService {

    private final ShipmentRepository shipmentRepository;
    private final TrackingEventRepository trackingEventRepository;
    private final ShipmentService shipmentService;

    @Override
    public Long changeStatusByShipmentId(Long shipmentId, ShipmentStatus status) {

        Shipment shipment = shipmentRepository.findById(shipmentId).orElseThrow(
                () -> BaseException.notFound(Shipment.class.getSimpleName(), "shipmentId", shipmentId)
        );
        if (shipment.isDeleted()){
            throw BaseException.notFound(Shipment.class.getSimpleName(), "shipmentId", shipmentId);
        }
        shipment.setStatus(status);
        shipmentRepository.save(shipment);
        TrackingEvent trackingEvent = TrackingEvent.builder()
                .shipment(shipment)
                .status(status)
                .description(status.getDescription())
                .build();
        trackingEventRepository.save(trackingEvent);

        return shipment.getId();
    }

    @Override
    public List<TrackingEventResponse> getAllByShipmentId(Long shipmentId) {

        List<TrackingEvent> trackingEvents = trackingEventRepository.findAllByShipmentId(shipmentId);
        List<TrackingEventResponse> trackingEventResponses =
                trackingEvents.stream()
                        .map(trackingEvent -> TrackingEventResponse.builder()
                                .id(trackingEvent.getId())
                                .shipmentId(trackingEvent.getShipment().getId())
                                .status(trackingEvent.getStatus())
                                .description(trackingEvent.getDescription())
                                .createdAt(trackingEvent.getCreatedAt())
                                .build())
                        .toList();
        return trackingEventResponses;
    }

    @Override
    public TrackEventWithShipmentResponse getTrackEventWithShipmentByTrackingNumber(String trackingNumber) {

        ShipmentResponse shipmentResponse = shipmentService.getShipmentByTrackingNumber(trackingNumber);
        List<TrackingEventResponse> trackingEventResponses = getAllByShipmentId(shipmentResponse.getId());
        TrackEventWithShipmentResponse trackEventWithShipmentResponse = TrackEventWithShipmentResponse.builder()
                .shipmentResponse(shipmentResponse)
                .trackingEvents(trackingEventResponses)
                .build();

        return trackEventWithShipmentResponse;
    }

    @Override
    public Long addTrackingEvents(TrackingEventPayload payload) {

        TrackingEvent trackingEvent = TrackingEvent.builder()
                .shipment(
                        shipmentRepository.findById(payload.getShipmentId()).orElseThrow(
                                () -> BaseException.notFound(Shipment.class.getSimpleName(), "shipmentId", payload.getShipmentId())
                        )
                )
                .status(payload.getStatus())
                .description(payload.getStatus().getDescription())
                .build();

        return trackingEventRepository.save(trackingEvent).getId();
    }
}
