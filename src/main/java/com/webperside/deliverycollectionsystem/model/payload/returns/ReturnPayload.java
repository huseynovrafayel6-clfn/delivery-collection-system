package com.webperside.deliverycollectionsystem.model.payload.returns;

import com.webperside.deliverycollectionsystem.model.entity.User;
import com.webperside.deliverycollectionsystem.model.enums.returns.ReturnReason;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ReturnPayload {

    Long shipmentId;
    ReturnReason reason;
    String notes;
}
