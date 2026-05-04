package com.webperside.deliverycollectionsystem.services.user;

import com.webperside.deliverycollectionsystem.model.dto.RoleDto;
import com.webperside.deliverycollectionsystem.model.entity.User;
import com.webperside.deliverycollectionsystem.model.payload.user.UserForAdminPayload;
import com.webperside.deliverycollectionsystem.model.payload.user.UserPayload;
import com.webperside.deliverycollectionsystem.model.response.user.UserResponse;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Set;

public interface UserService {

    void insert(User user);

    User getById(Long id);

    UserResponse getByIdForApi(Long id);

    User getByEmail(String email);

    boolean checkByEmail(String email);

    Long updateById(UserPayload payload, Long id);

    Long updateByIdForAdmin(UserForAdminPayload payload, Long id);

    List<UserResponse> getCouriers();

    List<RoleDto> getRolesByEmail(String email);

    Long hardDeleteById(Long id);

    Long softDeleteById(Long id);

    Page<UserResponse> getAll(int page, int size);
}
