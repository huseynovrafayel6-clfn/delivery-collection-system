package com.webperside.deliverycollectionsystem.services.deliveries;

import com.webperside.deliverycollectionsystem.model.entity.Delivery;
import com.webperside.deliverycollectionsystem.model.payload.deliveries.DeliveryPayload;
import com.webperside.deliverycollectionsystem.model.response.deliveries.DeliveryResponse;

public interface DeliveryService {

    DeliveryResponse addDelivery(DeliveryPayload payload);

    DeliveryResponse getById(Long id);

    void save(Delivery delivery);

    Delivery findById(Long id);

}
