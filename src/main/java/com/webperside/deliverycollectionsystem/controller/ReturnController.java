package com.webperside.deliverycollectionsystem.controller;

import com.webperside.deliverycollectionsystem.model.payload.returns.ReturnPayload;
import com.webperside.deliverycollectionsystem.model.response.BaseResponse;
import com.webperside.deliverycollectionsystem.model.response.returns.ReturnResponse;
import com.webperside.deliverycollectionsystem.services.returns.ReturnService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/returns")
@RequiredArgsConstructor
public class ReturnController {

    private final ReturnService returnService;

    @PostMapping("/initiate")
    public BaseResponse<Long> initiateReturn(ReturnPayload payload){
        return BaseResponse.success(returnService.initiateReturn(payload));
    }

    @PostMapping("/{id}/approve")
    public BaseResponse<Long> approveReturn(@PathVariable Long id) {
        return BaseResponse.success(returnService.approveReturn(id));
    }

    @PostMapping("/{id}/in-transit")
    public BaseResponse<Long> inTransitReturn(@PathVariable Long id) {
        return BaseResponse.success(returnService.inTransitReturn(id));
    }

    @PostMapping("/{id}/complete")
    public BaseResponse<Long> completeReturn(@PathVariable Long id) {
        return BaseResponse.success(returnService.completeReturn(id));
    }

    @PostMapping("/{id}/cancel")
    public BaseResponse<Long> cancelReturn(@PathVariable Long id) {
        return BaseResponse.success(returnService.cancelReturn(id));
    }

    @GetMapping("/{id}")
    public BaseResponse<ReturnResponse> getById(@PathVariable Long id){
        return BaseResponse.success(returnService.getById(id));
    }

    @GetMapping
    public BaseResponse<Page<ReturnResponse>> getAll(@RequestParam(defaultValue = "0") int page,
                                                     @RequestParam(defaultValue = "10") int size){
        return BaseResponse.success(returnService.getAll(page,size));
    }
}
