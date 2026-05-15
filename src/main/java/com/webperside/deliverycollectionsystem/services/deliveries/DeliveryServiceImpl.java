package com.webperside.deliverycollectionsystem.services.deliveries;

import com.webperside.deliverycollectionsystem.exception.BaseException;
import com.webperside.deliverycollectionsystem.model.entity.Delivery;
import com.webperside.deliverycollectionsystem.model.entity.Role;
import com.webperside.deliverycollectionsystem.model.entity.Shipment;
import com.webperside.deliverycollectionsystem.model.entity.User;
import com.webperside.deliverycollectionsystem.model.enums.delivery.DeliveryStatus;
import com.webperside.deliverycollectionsystem.model.payload.deliveries.DeliveryPayload;
import com.webperside.deliverycollectionsystem.model.response.deliveries.DeliveryResponse;
import com.webperside.deliverycollectionsystem.repository.DeliveryRepository;
import com.webperside.deliverycollectionsystem.repository.ShipmentRepository;
import com.webperside.deliverycollectionsystem.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class DeliveryServiceImpl implements DeliveryService{

    private final DeliveryRepository deliveryRepository;
    private final ShipmentRepository shipmentRepository;
    private final UserRepository userRepository;

    @Override
    public DeliveryResponse addDelivery(DeliveryPayload payload) {

        Shipment shipment = shipmentRepository.findById(payload.getShipmentId()).orElseThrow(
                () -> BaseException.notFound(Shipment.class.getSimpleName(), "shipmentId", payload.getShipmentId())
        );
        if (shipment.isDeleted())
            throw BaseException.notFound(Shipment.class.getSimpleName(), "shipmentId", payload.getShipmentId());

        User courier = userRepository.findById(payload.getCourierId()).orElseThrow(
                () -> BaseException.notFound(User.class.getSimpleName(), "courierId", payload.getCourierId())
        );
        if (courier.isDeleted())
            throw BaseException.notFound(User.class.getSimpleName(), "courierId", payload.getCourierId());
        for (Role role : courier.getRoles())
            if (!role.getName().equalsIgnoreCase("courier"))
                throw BaseException.notFound("Courier", "courierId", payload.getCourierId());

        Delivery delivery = Delivery.builder()
                .shipment(shipment)
                .courier(courier)
                .status(DeliveryStatus.PENDING)
                .receiverName(payload.getReceiverName())
                .notes(DeliveryStatus.PENDING.getDescription())
                .build();

        Delivery saved = deliveryRepository.save(delivery);

        return DeliveryResponse.builder()
                .id(saved.getId())
                .status(saved.getStatus())
                .receiverName(saved.getReceiverName())
                .notes(saved.getNotes())
                .build();
    }

    @Override
    public DeliveryResponse getById(Long id) {
        Delivery delivery = findById(id);
        return DeliveryResponse.builder()
                .id(delivery.getId())
                .shipmentId(delivery.getShipment().getId())
                .courierId(delivery.getCourier().getId())
                .status(delivery.getStatus())
                .startedAt(delivery.getStartedAt())
                .deliveredAt(delivery.getDeliveredAt())
                .failureReason(delivery.getFailureReason())
                .rescheduledAt(delivery.getRescheduledAt())
                .receiverName(delivery.getReceiverName())
                .notes(delivery.getNotes())
                .build();
    }

    @Override
    public void save(Delivery delivery) {
        deliveryRepository.save(delivery);
    }

    @Override
    public Delivery findById(Long id) {
        Delivery delivery =  deliveryRepository.findById(id).orElseThrow(
                () -> BaseException.notFound(Delivery.class.getSimpleName(), "id", id)
        );
        if (delivery.isDeleted())
            throw BaseException.notFound(Delivery.class.getSimpleName(), "id", id);
        return delivery;
    }


}
