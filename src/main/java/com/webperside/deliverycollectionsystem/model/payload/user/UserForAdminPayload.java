package com.webperside.deliverycollectionsystem.model.payload.user;


import lombok.*;
import lombok.experimental.FieldDefaults;

import java.util.Set;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserForAdminPayload {

    String status;
    Set<Long> ids;

}
