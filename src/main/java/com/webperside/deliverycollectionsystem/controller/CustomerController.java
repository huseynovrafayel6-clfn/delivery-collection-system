package com.webperside.deliverycollectionsystem.controller;

import com.webperside.deliverycollectionsystem.model.response.BaseResponse;
import com.webperside.deliverycollectionsystem.model.response.customers.CustomerShipmentResponse;
import com.webperside.deliverycollectionsystem.services.customers.CustomerService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
@RequiredArgsConstructor
public class CustomerController {

    private final CustomerService customerService;

    @GetMapping("/{senderId}/shipments")
    public BaseResponse<List<CustomerShipmentResponse>> getShipmentsBySenderId(@PathVariable Long senderId) {
        return BaseResponse.success(customerService.getShipmentsBySenderId(senderId));
    }

}
