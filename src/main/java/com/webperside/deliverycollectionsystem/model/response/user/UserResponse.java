package com.webperside.deliverycollectionsystem.model.response.user;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {

    Long id;
    String name;
    String surname;
    String status;
    String email;
    String phoneNumber;


}
