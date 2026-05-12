package com.webperside.deliverycollectionsystem.controller;

import com.fasterxml.jackson.databind.ser.Serializers;
import com.webperside.deliverycollectionsystem.model.payload.user.UserForAdminPayload;
import com.webperside.deliverycollectionsystem.model.payload.user.UserPayload;
import com.webperside.deliverycollectionsystem.model.response.BaseResponse;
import com.webperside.deliverycollectionsystem.model.response.user.UserResponse;
import com.webperside.deliverycollectionsystem.services.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping("/get-all")
    public BaseResponse<Page<UserResponse>> getAll(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size) {
        return BaseResponse.success(userService.getAll(page, size));
    }

    @GetMapping("/get/{id}")
    public BaseResponse<UserResponse> getById(@PathVariable Long id) {
        return BaseResponse.success(userService.getByIdForApi(id));
    }

    @PutMapping("/update/{id}")
    public BaseResponse<Long> updateById(@RequestBody UserPayload payload, @PathVariable Long id) {
        return BaseResponse.success(userService.updateById(payload, id));
    }

    @PatchMapping("/update/status/roles/{id}")
    public BaseResponse<Long> updateUserForAdmin(@RequestBody UserForAdminPayload payload, @PathVariable Long id) {
        return BaseResponse.success(userService.updateByIdForAdmin(payload, id));
    }

    @DeleteMapping("/delete/soft/{id}")
    public BaseResponse<Long> softDeleteById(@PathVariable Long id) {
        return BaseResponse.success(userService.softDeleteById(id));
    }

    @DeleteMapping("/delete/hard/{id}")
    public BaseResponse<Long> hardDeleteById(@PathVariable Long id) {
        return BaseResponse.success(userService.hardDeleteById(id));
    }

    @GetMapping("/get/couriers")
    public BaseResponse<List<UserResponse>> getCouriers() {
        return BaseResponse.success(userService.getCouriers());
    }


}
