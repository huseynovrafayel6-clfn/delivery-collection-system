package com.webperside.deliverycollectionsystem.model.response.login;

import com.webperside.deliverycollectionsystem.model.dto.UserInfo;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class LoginResponse {

    String accessToken;
    String refreshToken;
    UserInfo userInfo;

}
