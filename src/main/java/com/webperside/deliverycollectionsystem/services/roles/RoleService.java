package com.webperside.deliverycollectionsystem.services.roles;

import com.webperside.deliverycollectionsystem.model.entity.Role;
import com.webperside.deliverycollectionsystem.model.payload.role.RolePayload;
import com.webperside.deliverycollectionsystem.model.response.role.RoleResponse;

import java.util.List;
import java.util.Set;

public interface RoleService {

    Long insert(RolePayload payload);

    Role getDefaultRole();

    List<RoleResponse> getAll();

    RoleResponse getById(Long id);

    Set<Role> getAllRolesByIds(Iterable<Long> ids);

    Long softDeleteById(Long id);

    Long hardDeleteById(Long id);

    Long updateById(RolePayload payload, Long id);
}
