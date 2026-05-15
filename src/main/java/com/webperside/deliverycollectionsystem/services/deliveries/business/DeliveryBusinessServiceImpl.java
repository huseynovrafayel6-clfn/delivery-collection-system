package com.webperside.deliverycollectionsystem.services.deliveries.business;

import com.webperside.deliverycollectionsystem.model.entity.Delivery;
import com.webperside.deliverycollectionsystem.model.enums.delivery.DeliveryFailureReason;
import com.webperside.deliverycollectionsystem.model.enums.delivery.DeliveryStatus;
import com.webperside.deliverycollectionsystem.model.response.deliveries.DeliveryResponse;
import com.webperside.deliverycollectionsystem.services.deliveries.DeliveryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryBusinessServiceImpl implements DeliveryBusinessService {

    private final DeliveryService deliveryService;

    @Override
    public DeliveryResponse startDelivery(Long deliveryId) {

        Delivery delivery = deliveryService.findById(deliveryId);
        delivery.setStatus(DeliveryStatus.IN_PROGRESS);
        delivery.setStartedAt(LocalDateTime.now());
        delivery.setNotes(DeliveryStatus.IN_PROGRESS.getDescription());
        deliveryService.save(delivery);

        return DeliveryResponse.builder()
                .id(delivery.getId())
                .status(delivery.getStatus())
                .startedAt(delivery.getStartedAt())
                .notes(delivery.getNotes())
                .build();
    }

    @Override
    public DeliveryResponse confirmDelivery(Long deliveryId) {
        Delivery delivery = deliveryService.findById(deliveryId);
        delivery.setStatus(DeliveryStatus.DELIVERED);
        delivery.setDeliveredAt(LocalDateTime.now());
        delivery.setNotes(DeliveryStatus.DELIVERED.getDescription());
        deliveryService.save(delivery);

        return DeliveryResponse.builder()
                .id(delivery.getId())
                .status(delivery.getStatus())
                .deliveredAt(delivery.getDeliveredAt())
                .notes(delivery.getNotes())
                .build();
    }

    @Override
    public DeliveryResponse failDelivery(Long deliveryId, DeliveryFailureReason deliveryFailureReason) {
        Delivery delivery = deliveryService.findById(deliveryId);
        delivery.setStatus(DeliveryStatus.FAILED);
        delivery.setFailureReason(deliveryFailureReason);
        delivery.setNotes(DeliveryStatus.FAILED.getDescription());
        deliveryService.save(delivery);

        return DeliveryResponse.builder()
                .id(delivery.getId())
                .status(delivery.getStatus())
                .failureReason(delivery.getFailureReason())
                .notes(delivery.getNotes())
                .build();
    }

    @Override
    public DeliveryResponse rescheduleDelivery(Long deliveryId, LocalDateTime rescheduledAt) {
        Delivery delivery = deliveryService.findById(deliveryId);
        delivery.setStatus(DeliveryStatus.RESCHEDULED);
        delivery.setRescheduledAt(rescheduledAt);
        delivery.setNotes(DeliveryStatus.RESCHEDULED.getDescription());
        deliveryService.save(delivery);

        return DeliveryResponse.builder()
                .id(delivery.getId())
                .status(delivery.getStatus())
                .rescheduledAt(delivery.getRescheduledAt())
                .notes(delivery.getNotes())
                .build();
    }

}
