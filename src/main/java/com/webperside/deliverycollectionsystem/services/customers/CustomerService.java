package com.webperside.deliverycollectionsystem.services.customers;

import com.webperside.deliverycollectionsystem.model.response.customers.CustomerShipmentResponse;

import java.util.List;

public interface CustomerService {

    List<CustomerShipmentResponse> getShipmentsBySenderId(Long senderId);

}
