package com.webperside.deliverycollectionsystem.services.customers;

import com.webperside.deliverycollectionsystem.model.entity.Shipment;
import com.webperside.deliverycollectionsystem.model.response.customers.CustomerShipmentResponse;
import com.webperside.deliverycollectionsystem.repository.ShipmentRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class CustomerServiceImpl implements CustomerService{

    private final ShipmentRepository shipmentRepository;

    @Override
    public List<CustomerShipmentResponse> getShipmentsBySenderId(Long senderId) {
        List<Shipment> shipments = shipmentRepository.findAllBySenderIdAndIsDeletedFalseOrderByCreatedAtDesc(senderId);
        return shipments.stream()
                .map(shipment -> {

                    CustomerShipmentResponse response =
                            new CustomerShipmentResponse();

                    response.setShipmentId(shipment.getId());
                    response.setTrackingNumber(shipment.getTrackingNumber());
                    response.setStatus(shipment.getStatus());
                    response.setDeliveryAddress(shipment.getDeliveryAddress());
                    response.setCreatedAt(shipment.getCreatedAt());

                    return response;
                })
                .toList();
    }
}
