package com.webperside.deliverycollectionsystem.model.payload.auth;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TokenPayload {

    String accessToken;
    String refreshToken;
    boolean rememberMe;

}
