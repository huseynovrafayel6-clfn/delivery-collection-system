package com.webperside.deliverycollectionsystem.model.payload.role;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class RolePayload {

    String name;
    String description;

}
