package com.webperside.deliverycollectionsystem.services.shipments;

import com.webperside.deliverycollectionsystem.exception.BaseException;
import com.webperside.deliverycollectionsystem.model.dto.UserInfo;
import com.webperside.deliverycollectionsystem.model.entity.Shipment;
import com.webperside.deliverycollectionsystem.model.entity.User;
import com.webperside.deliverycollectionsystem.model.enums.shipments.ShipmentStatus;
import com.webperside.deliverycollectionsystem.model.payload.shipment.ShipmentPayload;
import com.webperside.deliverycollectionsystem.model.response.shipment.ShipmentResponse;
import com.webperside.deliverycollectionsystem.repository.ShipmentRepository;
import com.webperside.deliverycollectionsystem.services.security.AuthBusinessService;
import com.webperside.deliverycollectionsystem.services.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Slf4j
public class ShipmentServiceImpl implements ShipmentService {

    private final AuthBusinessService authBusinessService;
    private final UserService userService;
    private final ShipmentRepository shipmentRepository;

    @Override
    public Long addShipment(ShipmentPayload payload) {
        Shipment shipment = fromPayloadToShipment(payload, new Shipment());
        shipmentRepository.save(shipment);
        return shipment.getId();
    }

    @Override
    public Page<ShipmentResponse> getShipments(int page, int size) {

        Pageable pageable = PageRequest.of(page, size, Sort.by("createdAt").descending());
        Page<Shipment> shipmentPage = shipmentRepository.findAll(pageable);
        return shipmentPage.map(shipment -> {
            ShipmentResponse shipmentResponse = new ShipmentResponse();
            shipmentResponse.setId(shipment.getId());
            shipmentResponse.setTrackingNumber(shipment.getTrackingNumber());
            shipmentResponse.setSender(fromUserToUserInfo(shipment.getSender()));
            shipmentResponse.setReceiverName(shipment.getReceiverName());
            shipmentResponse.setReceiverEmail(shipment.getReceiverEmail());
            shipmentResponse.setReceiverPhoneNumber(shipment.getReceiverPhoneNumber());
            shipmentResponse.setDeliveryAddress(shipment.getDeliveryAddress());
            shipmentResponse.setWeight(shipment.getWeight());
            shipmentResponse.setDimensions(shipment.getDimensions());
            shipmentResponse.setServiceType(shipment.getServiceType());
            shipmentResponse.setDeliveryType(shipment.getDeliveryType());
            shipmentResponse.setNotes(shipment.getNotes());
            shipmentResponse.setStatus(shipment.getStatus());
            shipmentResponse.setCreatedAt(shipment.getCreatedAt());
            shipmentResponse.setUpdatedAt(shipment.getUpdatedAt());
            return shipmentResponse;
        });
    }

    @Override
    public ShipmentResponse getShipmentById(Long id) {

        Shipment shipment = shipmentRepository.findById(id).orElseThrow(()->BaseException.notFound(Shipment.class.getSimpleName(), "id", id));
        return fromShipmentToResponse(shipment);

    }

    @Override
    public ShipmentResponse getShipmentByTrackingNumber(String trackingNumber) {
        Shipment shipment = shipmentRepository.findByTrackingNumber(trackingNumber).orElseThrow(()->BaseException.notFound(Shipment.class.getSimpleName(), "trackingNumber", trackingNumber));
        return fromShipmentToResponse(shipment);

    }

    @Override
    public Long updateShipmentById(Long id, ShipmentPayload payload) {
        Shipment shipment = fromPayloadToShipment(payload, shipmentRepository.findById(id).orElseThrow(
                ()->BaseException.notFound(Shipment.class.getSimpleName(), "id", id))
        );
        shipmentRepository.save(shipment);
        return shipment.getId();
    }

    @Override
    public Long cancelShipmentById( Long id) {

        Shipment shipment = shipmentRepository.findById(id).orElseThrow(()->BaseException.notFound(Shipment.class.getSimpleName(), "id", id));
        shipment.setStatus(ShipmentStatus.CANCELLED);
        shipmentRepository.save(shipment);

        return shipment.getId();
    }

    @Override
    public Long softDeleteShipmentById(Long id) {
        Shipment shipment = shipmentRepository.findById(id).orElseThrow(()->BaseException.notFound(Shipment.class.getSimpleName(), "id", id));
        shipment.setDeleted(true);
        return shipmentRepository.save(shipment).getId();
    }

    @Override
    public Long hardDeleteShipmentById(Long id) {
        Shipment shipment = shipmentRepository.findById(id).orElseThrow(()->BaseException.notFound(Shipment.class.getSimpleName(), "id", id));
        shipmentRepository.deleteById(id);
        return shipment.getId();
    }

    private UserInfo fromUserToUserInfo(User user) {
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setName(user.getName());
        userInfo.setSurname(user.getSurname());
        userInfo.setEmail(user.getEmail());
        userInfo.setPhoneNumber(user.getPhoneNumber());
        return userInfo;
    }

    private Shipment fromPayloadToShipment(ShipmentPayload payload, Shipment shipment) {

        UserInfo userInfo = authBusinessService.getCurrentUser();
        User sender = userService.getById(userInfo.getId());
        shipment.setSender(sender);
        shipment.setReceiverName(payload.getReceiverName());
        shipment.setReceiverEmail(payload.getReceiverEmail());
        shipment.setReceiverPhoneNumber(payload.getReceiverPhoneNumber());
        shipment.setDeliveryAddress(payload.getDeliveryAddress());
        shipment.setWeight(payload.getWeight());
        shipment.setDimensions(payload.getDimensions());
        shipment.setServiceType(payload.getServiceType());
        shipment.setDeliveryType(payload.getDeliveryType());
        shipment.setNotes(payload.getNotes());
        shipment.setStatus(payload.getStatus());


        return shipment;
    }

    private ShipmentResponse fromShipmentToResponse(Shipment shipment) {
        ShipmentResponse shipmentResponse = new ShipmentResponse();
        shipmentResponse.setId(shipment.getId());
        shipmentResponse.setTrackingNumber(shipment.getTrackingNumber());
        shipmentResponse.setSender(fromUserToUserInfo(shipment.getSender()));
        shipmentResponse.setReceiverName(shipment.getReceiverName());
        shipmentResponse.setReceiverEmail(shipment.getReceiverEmail());
        shipmentResponse.setReceiverPhoneNumber(shipment.getReceiverPhoneNumber());
        shipmentResponse.setDeliveryAddress(shipment.getDeliveryAddress());
        shipmentResponse.setWeight(shipment.getWeight());
        shipmentResponse.setDimensions(shipment.getDimensions());
        shipmentResponse.setServiceType(shipment.getServiceType());
        shipmentResponse.setDeliveryType(shipment.getDeliveryType());
        shipmentResponse.setNotes(shipment.getNotes());
        shipmentResponse.setStatus(shipment.getStatus());
        shipmentResponse.setCreatedAt(shipment.getCreatedAt());
        shipmentResponse.setUpdatedAt(shipment.getUpdatedAt());
        return shipmentResponse;
    }



}
