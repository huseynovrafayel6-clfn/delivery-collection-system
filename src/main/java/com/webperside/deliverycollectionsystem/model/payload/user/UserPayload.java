package com.webperside.deliverycollectionsystem.model.payload.user;

import com.webperside.deliverycollectionsystem.model.entity.Role;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserPayload {

    String name;
    String surname;
    String status;
    String email;
    String phoneNumber;
    String password;

}
