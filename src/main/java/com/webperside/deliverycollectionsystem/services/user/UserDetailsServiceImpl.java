package com.webperside.deliverycollectionsystem.services.user;

import com.webperside.deliverycollectionsystem.exception.BaseException;
import com.webperside.deliverycollectionsystem.model.dto.RoleDto;
import com.webperside.deliverycollectionsystem.model.entity.User;
import com.webperside.deliverycollectionsystem.model.enums.response.ErrorResponseMessages;
import com.webperside.deliverycollectionsystem.model.enums.user.UserStatus;
import com.webperside.deliverycollectionsystem.model.security.LoggedInUserDetails;
import com.webperside.deliverycollectionsystem.services.roles.RoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userService.getByEmail(username);
        if (user.isDeleted())
            throw BaseException.notFound(User.class.getSimpleName(), "email", username);
        List<RoleDto> roles = userService.getRolesByEmail(username);

        List<SimpleGrantedAuthority> authorities = roles.stream()
                .map(r -> new SimpleGrantedAuthority(r.getName()))
                .toList();

        if (!UserStatus.ACTIVE.toString().equals(user.getStatus())) {
            throw BaseException.of(ErrorResponseMessages.USER_NOT_ACTIVE);
        }

        return new LoggedInUserDetails(
                user.getEmail(), user.getPassword(), authorities
        );
    }
}
