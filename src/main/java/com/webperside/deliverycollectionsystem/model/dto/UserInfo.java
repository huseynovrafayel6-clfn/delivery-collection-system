package com.webperside.deliverycollectionsystem.model.dto;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserInfo {

    Long id;
    String name;
    String surname;
    String email;
    String phoneNumber;
}
