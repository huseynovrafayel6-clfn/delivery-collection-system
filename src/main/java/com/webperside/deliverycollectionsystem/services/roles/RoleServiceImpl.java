package com.webperside.deliverycollectionsystem.services.roles;

import com.webperside.deliverycollectionsystem.exception.BaseException;
import com.webperside.deliverycollectionsystem.model.entity.Role;
import com.webperside.deliverycollectionsystem.model.payload.role.RolePayload;
import com.webperside.deliverycollectionsystem.model.response.role.RoleResponse;
import com.webperside.deliverycollectionsystem.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class RoleServiceImpl implements RoleService {

    private final static String customer = "customer";
    private final RoleRepository roleRepository;

    @Override
    public Long insert(RolePayload payload) {
        Role role = new Role();
        role.setName(payload.getName());
        role.setDescription(payload.getDescription());

        Role saved = roleRepository.save(role);
        return saved.getId();
    }

    @Override
    public Role getDefaultRole() {
        return roleRepository.findByName(customer).orElseThrow(() -> BaseException.notFound(Role.class.getSimpleName(), "customer", customer));
    }

    @Override
    public List<RoleResponse> getAll() {

        List<Role> roles = roleRepository.findByIsDeletedFalse();
        System.out.println(roles);
        List<RoleResponse> roleResponses = new ArrayList<>();


        for (Role role : roles) {
            RoleResponse roleResponse = new RoleResponse();
            roleResponse.setId(role.getId());
            roleResponse.setName(role.getName());
            roleResponse.setDescription(role.getDescription());
            roleResponses.add(roleResponse);
        }
        return roleResponses;
    }

    @Override
    public RoleResponse getById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> BaseException.notFound(Role.class.getSimpleName(), "id", id));
        RoleResponse roleResponse = new RoleResponse();
        roleResponse.setId(role.getId());
        roleResponse.setName(role.getName());
        roleResponse.setDescription(role.getDescription());
        return roleResponse;
    }

    @Override
    public Set<Role> getAllRolesByIds(Iterable<Long> ids) {
        List<Role> roles = roleRepository.findAllById(ids);

        if (roles.size() != ((Collection<?>) ids).size()) {
            throw BaseException.notFound(Role.class.getSimpleName(), "ids", ids);
        }

        for (Role role : roles) {
            if (role.isDeleted()) {
                throw BaseException.notFound(Role.class.getSimpleName(), "ids", ids);
            }
        }
        return new HashSet<>(roles);
    }

    @Override
    public Long softDeleteById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> BaseException.notFound(Role.class.getSimpleName(), "id", id));
        role.setDeleted(true);
        roleRepository.save(role);
        return role.getId();
    }

    @Override
    public Long hardDeleteById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> BaseException.notFound(Role.class.getSimpleName(), "id", id));
        roleRepository.delete(role);

        return role.getId();
    }

    @Override
    public Long updateById(RolePayload payload, Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() -> BaseException.notFound(Role.class.getSimpleName(), "id", id));
        role.setId(id);
        role.setName(payload.getName());
        role.setDescription(payload.getDescription());

        Role saved = roleRepository.save(role);
        return saved.getId();
    }

}
