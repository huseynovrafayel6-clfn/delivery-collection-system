package com.webperside.deliverycollectionsystem.services.shipments;

import com.webperside.deliverycollectionsystem.model.payload.shipment.ShipmentPayload;
import com.webperside.deliverycollectionsystem.model.response.shipment.ShipmentResponse;
import org.springframework.data.domain.Page;

public interface ShipmentService {

    Long addShipment(ShipmentPayload payload);

    Page<ShipmentResponse> getShipments(int page, int size);

    ShipmentResponse getShipmentById(Long id);

    ShipmentResponse getShipmentByTrackingNumber(String trackingNumber);

    Long updateShipmentById(Long id, ShipmentPayload payload);

    Long cancelShipmentById(Long id);

    Long softDeleteShipmentById(Long id);

    Long hardDeleteShipmentById(Long id);

}
