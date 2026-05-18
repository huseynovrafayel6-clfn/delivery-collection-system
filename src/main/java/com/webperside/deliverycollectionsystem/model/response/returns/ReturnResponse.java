package com.webperside.deliverycollectionsystem.model.response.returns;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.webperside.deliverycollectionsystem.model.enums.returns.ReturnReason;
import com.webperside.deliverycollectionsystem.model.enums.returns.ReturnStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReturnResponse {

    Long id;
    Long shipmentId;
    ReturnReason reason;
    String notes;
    ReturnStatus status;
    LocalDateTime initiatedAt;
    LocalDateTime approvedAt;
    LocalDateTime deliveredToSenderAt;
    LocalDateTime completedAt;
    Long initiatedBy;
    Long approvedBy;
    LocalDateTime createdAt;
    LocalDateTime updatedAt;
}
