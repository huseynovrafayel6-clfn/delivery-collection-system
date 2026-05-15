package com.webperside.deliverycollectionsystem.services.deliveries.business;

import com.webperside.deliverycollectionsystem.model.enums.delivery.DeliveryFailureReason;
import com.webperside.deliverycollectionsystem.model.response.deliveries.DeliveryResponse;

import java.time.LocalDateTime;

public interface DeliveryBusinessService {

    DeliveryResponse startDelivery(Long deliveryId);

    DeliveryResponse confirmDelivery(Long deliveryId);

    DeliveryResponse failDelivery(Long deliveryId, DeliveryFailureReason deliveryFailureReason);

    DeliveryResponse rescheduleDelivery(Long deliveryId, LocalDateTime rescheduledAt);
}
