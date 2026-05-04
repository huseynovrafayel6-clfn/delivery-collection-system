package com.webperside.deliverycollectionsystem.controller;


import com.webperside.deliverycollectionsystem.model.payload.role.RolePayload;
import com.webperside.deliverycollectionsystem.model.response.BaseResponse;
import com.webperside.deliverycollectionsystem.model.response.role.RoleResponse;
import com.webperside.deliverycollectionsystem.services.roles.RoleService;
import lombok.RequiredArgsConstructor;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping("/get-all")
//    @PreAuthorize("hasRole('customer')")
    public BaseResponse<List<RoleResponse>> getRoles() {
        return BaseResponse.success(roleService.getAll());
    }

    @PostMapping("/add")
    public BaseResponse<Long> addRole(@RequestBody RolePayload payload) {
        return BaseResponse.success(roleService.insert(payload));

    }

    @PutMapping("/update/{id}")
    public BaseResponse<Long> updateById(@RequestBody RolePayload payload, @PathVariable Long id) {
        return BaseResponse.success(roleService.updateById(payload, id));

    }

    @DeleteMapping("/delete/soft/{id}")
    public BaseResponse<Long> softDeleteById(@PathVariable Long id) {
        return BaseResponse.success(roleService.softDeleteById(id));
    }

    @DeleteMapping("/delete/hard/{id}")
    public BaseResponse<Long> hardDeleteById(@PathVariable Long id) {
        return BaseResponse.success(roleService.hardDeleteById(id));
    }

    @GetMapping("/get/{id}")
    public BaseResponse<RoleResponse> getRoleById(@PathVariable Long id) {
        return BaseResponse.success(roleService.getById(id));
    }
}
