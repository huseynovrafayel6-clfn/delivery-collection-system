package com.webperside.deliverycollectionsystem.controller;


import com.webperside.deliverycollectionsystem.model.dto.UserInfo;
import com.webperside.deliverycollectionsystem.model.payload.auth.LoginPayload;
import com.webperside.deliverycollectionsystem.model.payload.auth.RefreshTokenPayload;
import com.webperside.deliverycollectionsystem.model.payload.auth.SignUpPayload;
import com.webperside.deliverycollectionsystem.model.response.BaseResponse;
import com.webperside.deliverycollectionsystem.model.response.login.LoginResponse;
import com.webperside.deliverycollectionsystem.services.security.AuthBusinessService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthBusinessService authBusinessService;

    @PostMapping("/login")
    public BaseResponse<LoginResponse> login(@RequestBody LoginPayload payload) {
        return BaseResponse.success(authBusinessService.login(payload));
    }

    @PostMapping("/logout")
    public BaseResponse<Void> logout(@RequestBody RefreshTokenPayload payload) {
        authBusinessService.logout(payload);
        return BaseResponse.success();
    }

    @PostMapping("/refresh")
    public BaseResponse<LoginResponse> refresh(@RequestBody RefreshTokenPayload payload) {
        return BaseResponse.success(authBusinessService.refresh(payload));
    }

    @PostMapping("/sign-up")
    public BaseResponse<Void> signUp(@RequestBody SignUpPayload payload) {
        authBusinessService.signUp(payload);
        return BaseResponse.success();
    }

    @GetMapping("/me")
    public BaseResponse<UserInfo> getCurrentUser() {
        return BaseResponse.success(authBusinessService.getCurrentUser());
    }

}
