package com.webperside.deliverycollectionsystem.services.security;

import com.webperside.deliverycollectionsystem.model.dto.UserInfo;
import com.webperside.deliverycollectionsystem.model.payload.auth.*;
import com.webperside.deliverycollectionsystem.model.response.login.LoginResponse;


public interface AuthBusinessService {

    LoginResponse login(LoginPayload payload);

    void logout(TokenPayload tokenPayload);

    LoginResponse refresh(RefreshTokenPayload payload);

    void signUp(SignUpPayload payload);

    UserInfo getCurrentUser();

}
