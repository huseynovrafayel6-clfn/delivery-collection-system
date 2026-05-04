package com.webperside.deliverycollectionsystem.model.payload.auth;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class SignUpPayload {

    String name;
    String surname;
    String email;
    String phoneNumber;
    String password;
    Set<Long> roleIds;
}
