package com.webperside.deliverycollectionsystem.services.user;

import com.webperside.deliverycollectionsystem.exception.BaseException;
import com.webperside.deliverycollectionsystem.model.dto.RoleDto;
import com.webperside.deliverycollectionsystem.model.entity.User;
import com.webperside.deliverycollectionsystem.model.payload.user.UserForAdminPayload;
import com.webperside.deliverycollectionsystem.model.payload.user.UserPayload;

import com.webperside.deliverycollectionsystem.model.response.user.UserResponse;

import com.webperside.deliverycollectionsystem.repository.UserRepository;
import com.webperside.deliverycollectionsystem.services.roles.RoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;

import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void insert(User user) {
        userRepository.save(user);
    }

    @Override
    public User getById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> BaseException.notFound(User.class.getSimpleName(), "id", id)
        );
        if (user.isDeleted()){
            throw BaseException.notFound(User.class.getSimpleName(), "id", id);
        }
        return user;
    }

    @Override
    public UserResponse getByIdForApi(Long id) {
         User user = getById(id);
         UserResponse userResponse = new UserResponse();
         userResponse.setId(id);
         userResponse.setName(user.getName());
         userResponse.setSurname(user.getSurname());
         userResponse.setStatus(user.getStatus());
         userResponse.setEmail(user.getEmail());
         userResponse.setPhoneNumber(user.getPhoneNumber());
         return userResponse;
    }

    @Override
    public User getByEmail(String email) {
        return userRepository.findByEmail(email).orElseThrow(
                () -> BaseException.notFound(User.class.getSimpleName(), "email", email)
        );
    }

    @Override
    public boolean checkByEmail(String email) {
        return userRepository.findByEmail(email).isPresent();
    }

    @Override
    public Long updateById(UserPayload payload, Long id) {
        User user = getById(id);
        user.setName(payload.getName());
        user.setSurname(payload.getSurname());
        user.setStatus(payload.getStatus());
        user.setEmail(payload.getEmail());
        user.setPhoneNumber(payload.getPhoneNumber());
        if (!passwordEncoder.matches(payload.getPassword(), user.getPassword())) {
            user.setPassword(passwordEncoder.encode(payload.getPassword()));
        }
        User saved = userRepository.save(user);
        return saved.getId();
    }

    @Override
    public Long updateByIdForAdmin(UserForAdminPayload payload, Long id) {
        User user = getById(id);
        user.setStatus(payload.getStatus());
        user.setRoles(roleService.getAllRolesByIds(payload.getIds()));
        User saved = userRepository.save(user);
        return saved.getId();
    }

    @Override
    public List<UserResponse> getCouriers() {
        String defaultRole = "courier";
        List<User> users = userRepository.findAllByRoles_Name(defaultRole);
        List<UserResponse> userResponses = new ArrayList<>();
        for (User user : users) {
            if (user.isDeleted()) {
                continue;
            }
            UserResponse userResponse = new UserResponse();
            userResponse.setId(user.getId());
            userResponse.setName(user.getName());
            userResponse.setSurname(user.getSurname());
            userResponse.setStatus(user.getStatus());
            userResponse.setEmail(user.getEmail());
            userResponse.setPhoneNumber(user.getPhoneNumber());
            userResponses.add(userResponse);
        }
        return userResponses;
    }

    @Override
    public List<RoleDto> getRolesByEmail(String email) {
        return userRepository.findRolesByEmail(email);
    }

    @Override
    public Long softDeleteById(Long id) {
        User user = getById(id);
        user.setDeleted(true);
        userRepository.save(user);
        return user.getId();
    }

    @Override
    public Long hardDeleteById(Long id) {
        User user = getById(id);
        userRepository.delete(user);
        return user.getId();
    }

    @Override
    public Page<UserResponse> getAll(int page, int size) {

        Pageable pageable = PageRequest.of(page, size);

        Page<User> userPage = userRepository.findByIsDeletedFalse(pageable);

        return userPage.map(user -> {
            UserResponse dto = new UserResponse();
            dto.setId(user.getId());
            dto.setName(user.getName());
            dto.setSurname(user.getSurname());
            dto.setStatus(user.getStatus());
            dto.setEmail(user.getEmail());
            dto.setPhoneNumber(user.getPhoneNumber());
            return dto;
        });
    }


}
