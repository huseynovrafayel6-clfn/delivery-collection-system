package com.webperside.deliverycollectionsystem.services.security;

import com.webperside.deliverycollectionsystem.exception.BaseException;
import com.webperside.deliverycollectionsystem.model.dto.RefreshTokenDto;
import com.webperside.deliverycollectionsystem.model.dto.UserInfo;
import com.webperside.deliverycollectionsystem.model.entity.Role;
import com.webperside.deliverycollectionsystem.model.entity.User;
import com.webperside.deliverycollectionsystem.model.mappers.UserEntityMapper;
import com.webperside.deliverycollectionsystem.model.payload.auth.LoginPayload;
import com.webperside.deliverycollectionsystem.model.payload.auth.RefreshTokenPayload;
import com.webperside.deliverycollectionsystem.model.payload.auth.SignUpPayload;
import com.webperside.deliverycollectionsystem.model.response.login.LoginResponse;

import com.webperside.deliverycollectionsystem.model.security.LoggedInUserDetails;

import com.webperside.deliverycollectionsystem.services.roles.RoleService;
import com.webperside.deliverycollectionsystem.services.token.AccessTokenManager;
import com.webperside.deliverycollectionsystem.services.token.RefreshTokenManager;
import com.webperside.deliverycollectionsystem.services.user.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.Set;

import static com.webperside.deliverycollectionsystem.model.enums.response.ErrorResponseMessages.EMAIL_ALREADY_REGISTERED;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthBusinessServiceImpl implements AuthBusinessService{

     UserService userService;
     AuthenticationManager authenticationManager;
     AccessTokenManager accessTokenManager;
     RefreshTokenManager refreshTokenManager;
     RoleService roleService;
     PasswordEncoder passwordEncoder;


    @Override
    public LoginResponse login(LoginPayload payload) {
        authenticate(payload);
        return prepareLoginResponse(payload.getEmail(), payload.isRememberMe());
    }

    @Override
    public void logout() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder
                .getContext()
                .getAuthentication()
                .getPrincipal();

        log.info("{} user logout succeed", userDetails.getUsername());

        SecurityContextHolder.clearContext();
    }

    @Override
    public LoginResponse refresh(RefreshTokenPayload payload) {
        return prepareLoginResponse(
                refreshTokenManager.getEmail(payload.getRefreshToken()),
                payload.isRememberMe()
        );
    }

    @Override
    public void signUp(SignUpPayload payload) {
        if (userService.checkByEmail(payload.getEmail())) {
            throw BaseException.of(EMAIL_ALREADY_REGISTERED);
        }

        Role defaultRole = roleService.getDefaultRole();

        User user = UserEntityMapper.INSTANCE.fromSignUpPayloadToUser(
                payload,
                passwordEncoder.encode(payload.getPassword())
        );

        Set<Role> defaultRoles = new HashSet<>();
        defaultRoles.add(defaultRole);
        user.setRoles(defaultRoles);



        userService.insert(user);
    }

    @Override
    public UserInfo getCurrentUser() {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (!(principal instanceof LoggedInUserDetails loggedInUserDetails)) {
            throw BaseException.unauthorized();
        }

        User user = userService.getByEmail(loggedInUserDetails.getUsername());
        UserInfo userInfo = new UserInfo();
        userInfo.setId(user.getId());
        userInfo.setName(user.getName());
        userInfo.setSurname(user.getSurname());
        userInfo.setEmail(user.getEmail());
        userInfo.setPhoneNumber(user.getPhoneNumber());

        return userInfo;
    }


    private void authenticate(LoginPayload request){
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (AuthenticationException e) {
            throw e.getCause() instanceof BaseException ?
                    (BaseException) e.getCause() :
                    BaseException.unexpected();
        }
    }

    private LoginResponse prepareLoginResponse(String email, boolean rememberMe) {
        User user = userService.getByEmail(email);

        return LoginResponse.builder()
                .accessToken(accessTokenManager.generate(user))
                .refreshToken(refreshTokenManager.generate(
                        RefreshTokenDto.builder()
                                .user(user)
                                .rememberMe(rememberMe)
                                .build()
                ))
                .build();
    }

    }

